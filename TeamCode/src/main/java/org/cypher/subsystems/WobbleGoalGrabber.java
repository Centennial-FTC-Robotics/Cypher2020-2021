package org.cypher.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.cypher.Subsystem;

public class WobbleGoalGrabber implements Subsystem {
    private static final double OPEN_POSITION = .43;
    private static final double CLOSE_POSITION = .8;

    private static final double HINGE_IN = 0;
    private static final double HINGE_OUT = 1;

    private Servo grab;
    private Servo backHinge;
    private Servo frontHinge;

    private boolean isHingeIn = true;

    @Override
    public void initialize(OpMode opMode) {
        grab = opMode.hardwareMap.servo.get("grabber");
        backHinge = opMode.hardwareMap.servo.get("back");
        frontHinge = opMode.hardwareMap.servo.get("front");
        grab();
        isHingeIn = true;
    }

    public void grab() {
        grab.setPosition(CLOSE_POSITION);
    }

    public void release() {
        grab.setPosition(OPEN_POSITION);
    }

    public void setHingeIn(boolean hingeIn) {
        isHingeIn = hingeIn;
    }

    public void flipHinge() {
        if (isHingeIn) {
            backHinge.setPosition(HINGE_OUT);
            frontHinge.setPosition(HINGE_OUT);
        } else {
            backHinge.setPosition(HINGE_IN);
            frontHinge.setPosition(HINGE_IN);
        }
        isHingeIn = !isHingeIn;
    }
}
