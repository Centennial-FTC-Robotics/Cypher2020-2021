package org.cypher.subsystems;

import android.sax.StartElementListener;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import com.qualcomm.robotcore.util.Range;
import org.cypher.Kryptos;
import org.cypher.Subsystem;
import org.cypher.util.PIDController;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Shooter implements Subsystem {
    private DcMotor shooter;
    private DcMotor storage;
    private Servo aligner;

    public static final double PUSH_POS = 0;
    public static final double REST_POS = 0;

    public static final int MIN_STORAGE = 50;
    public static final int MAX_STORAGE = 1150; //1203

    private LinearOpMode opMode;

    private boolean isShooting;

    private final PIDController storagePID = new PIDController(.08f,0,0);
    @Override
    public void initialize(OpMode opMode) {
        isShooting = false;
        this.opMode = (LinearOpMode) opMode;

        shooter = opMode.hardwareMap.dcMotor.get("shooter");
        storage = opMode.hardwareMap.dcMotor.get("storage");
        aligner = opMode.hardwareMap.servo.get("aligner");

        storage.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        storage.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        storage.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        storage.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public void resetEncoders() {
        storage.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        storage.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void moveServo(double pos) {
        aligner.setPosition(pos);
    }

    public void moveStorage(int pos) {
        //TODO: im too lazy to implement PID for this, if this ends up causing problems then we can use a PID loop for it
        if (!storage.isBusy()) {
            pos = Range.clip(pos, MIN_STORAGE, MAX_STORAGE);
            storage.setTargetPosition(pos);
            storage.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            storage.setPower(.4);
        }

    }

    private void actualMoveStorage(int pos) {
       float error;
        float power;
        do {
            error = storage.getCurrentPosition() - pos;
            power = -storagePID.getPower(error);
            power = (float) DriveTrain.clip(power, .1, .03);
            storage.setPower(power);
        } while (Math.abs(error) > 20 && opMode.opModeIsActive());
        storagePID.reset();
        storage.setPower(0);
    }

    public void shoot(boolean useThread) {
        if(isShooting)
            return;
        if(useThread) {
            ShootThread thread = new ShootThread();
            thread.start();
        } else {
            actuallyShoot();
        }
    }

    public void setStoragePower(double pow) {
        storage.setPower(pow);
    }

    public int getStoragePos() {
        return storage.getCurrentPosition();
    }


    //ensure storage is all the way up - if its not then wait like 2sec
    //start shooter at .4 spd
    //wait like 1sec
    //move aligner
    //wait .3 sec
    //move aligner back
    //wait .3 sec
    //repeat that
    //move storage down
    //turn shooter off
    //cry
    private void actuallyShoot() {
        isShooting = true;
        ElapsedTime time = new ElapsedTime();
        if(MAX_STORAGE - storage.getCurrentPosition() > 10) {
            actualMoveStorage(MAX_STORAGE);
            time.reset();
            while (time.seconds() < .5);
        }

        shooter.setPower(.4);
        time.reset();
        while(time.seconds() < 1 && opMode.opModeIsActive());

        for(int i = 0; i < 3; i++) {
            aligner.setPosition(PUSH_POS);
            time.reset();
            while(time.seconds() < .3 && opMode.opModeIsActive());
            aligner.setPosition(REST_POS);
            time.reset();
            while(time.seconds() < .3 && opMode.opModeIsActive());
        }

        shooter.setPower(0);
        actualMoveStorage(MIN_STORAGE);
        time.reset();
        while (time.seconds() < .5);

//        Telemetry.Item line = opMode.telemetry.addData("crying",null);
//        line.setRetained(true);
//        opMode.telemetry.update();
    }

    private class ShootThread extends Thread {
        @Override
        public void run() {
            actuallyShoot();
        }
    }
}
