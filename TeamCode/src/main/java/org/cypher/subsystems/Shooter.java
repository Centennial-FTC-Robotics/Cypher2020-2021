package org.cypher.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.cypher.Subsystem;

public class Shooter implements Subsystem {
    private DcMotor shooter;
    private Servo servo;
    private static final double NOT_SHOOTING = .56;
    private static final double SHOOT_ONE =  .7;
    private static final double SHOOT_TWO = .8;
    private static final double SHOOT_THREE = 85;

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
        shooter = opMode.hardwareMap.dcMotor.get("shooter");
        shooter.setDirection(DcMotorSimple.Direction.REVERSE);
        servo = opMode.hardwareMap.servo.get("shooterServo");

        servo.setPosition(NOT_SHOOTING);
    }

    public void setPower(double power) {
        shooter.setPower(power);
    }

    public void shoot() {
        double[] positions = {SHOOT_ONE, SHOOT_TWO, SHOOT_THREE};
        setPower(.4);
        ElapsedTime time = new ElapsedTime();
        while(time.seconds() < .5);
        for(double pos : positions) {
            servo.setPosition(pos);
            time.reset();
            while(time.seconds() < .25);
        }

        setPower(0);
        servo.setPosition(NOT_SHOOTING);
    }

    public void moveServo(double pos)  {
        servo.setPosition(pos);
    }
}
