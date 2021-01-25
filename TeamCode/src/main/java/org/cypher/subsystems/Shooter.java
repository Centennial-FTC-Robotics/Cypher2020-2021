package org.cypher.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.cypher.Subsystem;

public class  Shooter implements Subsystem {
    private DcMotor shooter;
    private Servo storage;
    private Servo aligner;
    public static final double NOT_SHOOTING = .3;
    public static final double SHOOT_ONE =  .49;
    public static final double SHOOT_TWO = .58;
    public static final double SHOOT_THREE = .72;
    public static final double[] POSITIONS = {SHOOT_ONE, SHOOT_TWO, SHOOT_THREE};

    private static final double PUSH_POSITION =.7;
    private static final double REST_POSITION = .5;


    private boolean isShooting = false;

    private LinearOpMode opMode;

    //start shooter motor at .4 speed
    //wait like .5 of a second
    //move servo up to SHOOT_ONE
    //wait like .25 of a second
    //move servo to SHOOT_TWO
    //wait like .25 of a second
    //move servo to SHOOT_THREE
    //wait like .25 of a second
    //move servo down to NOT_SHOOTING
    //stop motor

    @Override
    public void initialize(OpMode opMode) {
        this.opMode = (LinearOpMode) opMode;
        shooter = opMode.hardwareMap.dcMotor.get("shooter");
        shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooter.setDirection(DcMotorSimple.Direction.REVERSE);
        storage = opMode.hardwareMap.servo.get("shooterServo");
        aligner = opMode.hardwareMap.servo.get("aligner");

        storage.setPosition(NOT_SHOOTING);
        aligner.setPosition(REST_POSITION);

    }

    public void setPower(double power) {
        shooter.setPower(power);
    }

    public void shoot(boolean useThread) {
        if(!isShooting) {
            isShooting = true;
            if(useThread) {
                ShootingThread thread = new ShootingThread();
                thread.start();
            } else {
                actuallyShoot();
            }
        }

    }

    private void actuallyShoot() {
        setPower(.9);
        ElapsedTime time = new ElapsedTime();
        while(time.seconds() < 1 && opMode.opModeIsActive());
        for(double pos : POSITIONS) {
            storage.setPosition(pos);
            time.reset();
            while(time.seconds() < .3 && opMode.opModeIsActive());
            aligner.setPosition(PUSH_POSITION);
            time.reset();
            while (time.seconds() < .3 && opMode.opModeIsActive());
            aligner.setPosition(REST_POSITION);
            time.reset();
            while (time.seconds() < .3 && opMode.opModeIsActive());
        }

        setPower(0);
        storage.setPosition(NOT_SHOOTING);
        isShooting = false;
    }

    public void moveServo(double pos)  {
        storage.setPosition(pos);
    }

    private class ShootingThread extends Thread {
        @Override
        public void run() {
            actuallyShoot();
        }
    }
}
