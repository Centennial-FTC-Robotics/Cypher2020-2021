package org.cypher.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.cypher.Subsystem;

public class WobbleGoalGrabber implements Subsystem {
    private static final double OPEN_POSITION = .3;
    private static final double CLOSE_POSITION = .55;
    //TODO: do these ^^
    private Servo grab;
    @Override
    public void initialize(OpMode opMode) {
        grab = opMode.hardwareMap.servo.get("grabber");
//        grab.setDirection(Servo.Direction.REVERSE);

        release();
    }

    public void grab() {
        grab.setPosition(CLOSE_POSITION);
    }

    public void release() {
        grab.setPosition(OPEN_POSITION);
    }
}
