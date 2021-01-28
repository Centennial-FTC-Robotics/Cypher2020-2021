package org.cypher.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.cypher.Subsystem;

public class WobbleGoalGrabber implements Subsystem {
    private static final double OPEN_POSITION = .14;
    private static final double CLOSE_POSITION = .48; //nice
    //TODO: do these ^^
    private Servo grab;
    private CRServo backHinge;
    private CRServo frontHinge;
    @Override
    public void initialize(OpMode opMode) {
        grab    = opMode.hardwareMap.servo.get("grabber");
        backHinge = opMode.hardwareMap.crservo.get("back");
        frontHinge = opMode.hardwareMap.crservo.get("front");
        frontHinge.setDirection(DcMotorSimple.Direction.REVERSE);
//        grab.setDirection(Servo.Direction.REVERSE);

        grab();
    }

    public void grab() {
        grab.setPosition(CLOSE_POSITION);
    }

    public void release() {
        grab.setPosition(OPEN_POSITION);
    }

    public void moveHinge(double power) {
        backHinge.setPower(power);
        frontHinge.setPower(power);
    }
}
