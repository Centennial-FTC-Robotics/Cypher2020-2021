package org.cypher.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.cypher.Kryptos;
import org.cypher.Subsystem;
import org.cypher.purepursuit.Line;

public class WobbleGoal implements Subsystem {
    public static final double OPEN_POSITION = 0;//nice
    public static final double CLOSE_POSITION = 1;

    private static final double HINGE_IN = 0.1;
    private static final double HINGE_OUT = .95;

    private Servo grab;
    private Servo backHinge;
    private Servo frontHinge;

    private boolean isHingeIn = true;

    private LinearOpMode opMode;
    @Override
    public void initialize(OpMode opMode) {
        this.opMode = (LinearOpMode) opMode;
        grab = opMode.hardwareMap.servo.get("grabber");
        backHinge = opMode.hardwareMap.servo.get("back");
        frontHinge = opMode.hardwareMap.servo.get("front");
        grab();
        isHingeIn = true;
    }

    public void  controllerGrabber(double pos) {
        grab.setPosition(pos);
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

    public void controlHinge(double pos) {
        backHinge.setPosition(pos);
        frontHinge.setPosition(pos);
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


    public void depositWobble() {
        if(isHingeIn)
            flipHinge();
        ElapsedTime time = new ElapsedTime();
        while ((opMode.opModeIsActive()) && time.seconds() < 1);
        release();
        while ((opMode.opModeIsActive()) && time.seconds() < .4);

    }

    public void grabWobble() {
        if(isHingeIn)
            flipHinge();
        grab();

        ElapsedTime time = new ElapsedTime();
        while (opMode.opModeIsActive() && time.seconds() < .5);

        flipHinge();

        time.reset();
        while (opMode.opModeIsActive() && time.seconds() < .8);
    }
}
