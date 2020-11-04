package org.cypher.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.cypher.Subsystem;

public class WobbleGoalMover implements Subsystem {
    private Servo leftHinge;
    private Servo rightHinge;

    private static final double OPEN_POS = 0;
    private static final double CLOSE_POS = 0;


    @Override
    public void initialize(OpMode opMode) {
        leftHinge = opMode.hardwareMap.servo.get("leftHinge");
        rightHinge = opMode.hardwareMap.servo.get("rightHinge");

        //might need to do left hinge instead
        rightHinge.setDirection(Servo.Direction.REVERSE);

        leftHinge.setPosition(OPEN_POS);
        rightHinge.setPosition(OPEN_POS);
    }

    public void rotateHinge() {
        if(leftHinge.getPosition() == OPEN_POS) {
            leftHinge.setPosition(CLOSE_POS);
            rightHinge.setPosition(CLOSE_POS);
        } else {
            leftHinge.setPosition(OPEN_POS);
            rightHinge.setPosition(OPEN_POS);
        }

    }
}
