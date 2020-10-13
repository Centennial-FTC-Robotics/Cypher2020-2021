package org.cypher.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.cypher.Subsystem;

public class Odometry implements Subsystem {
    //TODO: do this whole thing

    DcMotor leftE, rightE, sideE; //onmiwheels need motor names as pointers

    double leftPos, rightPos, sidePos;

    int leftEncCounts, rightEncCounts, sideEncCounts;

    double leftPosPrev, rightPosPrev, sidePosPrev;

    //change according to how we should position wheels
    int leftEPosDirection = 1;
    int rightEPosDirection = 1;
    int sideEPosDirection = 1;


    //double centricPositionx, centricPositiony; maybe useful maybe not to help recalibrate robot

    //final static double ENCODER_COUNTS_PER_INCH = needed so we can convert the rotations of the omniwheels into inches

    Odometry(DcMotor leftE, DcMotor rightE, DcMotor sideE) {

        this.leftE = leftE;
        this.rightE = rightE;
        this.sideE = sideE;

    }


    @Override
    public void initialize(OpMode opMode) {

        resetAllEncoders();
        while (waitIfBusy()) {

        }
        setWithoutEncoders();

    }

    public void resetAllEncoders() {
        leftE.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightE.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sideE.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public boolean waitIfBusy() {
        return leftE.isBusy() || rightE.isBusy() || sideE.isBusy();
    }

    public void setWithoutEncoders() {
        leftE.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightE.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        sideE.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void updateEncoders() {
        leftEncCounts = leftE.getCurrentPosition();
        rightEncCounts = rightE.getCurrentPosition();
        sideEncCounts = sideE.getCurrentPosition();
    }

    public void updatePos() {

        leftPos = getLPos() - leftPosPrev;
        rightPos = getRPos() - rightPosPrev;
        sidePos = getSPos() - sidePosPrev;

        //other eqs here


    }

    public void getCentricPosition() {

        //do we even need this maybe

    }

    //update later
    int getLPos() {

        return leftEncCounts * leftEPosDirection;

    }

    int getRPos() {

        return rightEncCounts * rightEPosDirection;

    }

    int getSPos() {

        return sideEncCounts * sideEPosDirection;

    }

    //retains encoder counts after end
    void finalPos() {

        leftPosPrev = leftPos;
        rightPosPrev = rightPos;
        sidePosPrev = sidePos;

    }

}
