package org.cypher.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.cypher.Subsystem;

public class Odometry implements Subsystem {
    private DcMotorEx leftEncoder;
    private DcMotorEx rightEncoder;
    private DcMotorEx backEncoder;

    private static final int L_DIR = 1;
    private static final int R_DIR = 1;
    private static final int B_DIR = 1;

    private int lPos;
    private int rPos;
    private int bPos;

    private int oldLPos = 0;
    private int oldRPos = 0;
    private int oldBPos = 0;

    private int deltaLPos;
    private int deltaRPos;
    private int deltaBPos;

    private final static double ENCODER_COUNTS_PER_INCH = 1;
    private final static double LR_RADIUS = 1;
    private final static double B_RADIUS = 1;

    private double angle = 0;
    private double deltaAngle = 0;

    public double deltax;
    public double deltay;

    public double xPos;
    public double yPos;

    @Override
    public void initialize(OpMode opMode) {
        leftEncoder = opMode.hardwareMap.get(DcMotorEx.class, "leftOdo");
        rightEncoder = opMode.hardwareMap.get(DcMotorEx.class, "rightOdo");
        backEncoder = opMode.hardwareMap.get(DcMotorEx.class, "backOdo");

        resetEncoders();
    }

    public void setStartPos(double xPos, double yPos, double angle) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.angle = angle;
    }

    public void updateEncoders() {
        lPos = leftEncoder.getCurrentPosition();
        rPos = rightEncoder.getCurrentPosition();
        bPos = backEncoder.getCurrentPosition();
    }

    public int getLPos() {
        return lPos * L_DIR;
    }

    public int getRPos() {
        return rPos * R_DIR;
    }

    public int getBPos() {
        return bPos * B_DIR;
    }

    public void resetEncoders() {
        leftEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public double[] convertFieldCentric(double xPos, double yPos) {

        double newX = xPos * Math.cos(angle - Math.PI / 2) - yPos * Math.sin(angle - Math.PI / 2);
        double newY = yPos * Math.cos(angle - Math.PI / 2) + xPos * Math.sin(angle - Math.PI / 2);

        return new double[] {newX, newY};
    }

    public void updatePos() {
        updateEncoders();

        deltaLPos = getLPos() - oldLPos;
        deltaRPos = getRPos() - oldRPos;
        deltaBPos = getBPos() - oldBPos;

        oldLPos = getLPos();
        oldRPos = getRPos();
        oldBPos = getBPos();

        deltaAngle = (deltaRPos - deltaLPos)/(2*LR_RADIUS*ENCODER_COUNTS_PER_INCH);
        angle = normalizeRadians(angle + deltaAngle);
        if(deltaAngle == 0) {
            deltax = deltaBPos;
            deltay = (deltax + deltay)/2;
        } else {
            double turnRad = LR_RADIUS*ENCODER_COUNTS_PER_INCH*(deltaLPos + deltaRPos)/(deltaRPos - deltaLPos);
            double strafeRad = deltaBPos/deltaAngle - B_RADIUS * ENCODER_COUNTS_PER_INCH;

            deltax = turnRad*(Math.cos(deltaAngle) - 1) + strafeRad * Math.sin(deltaAngle);
            deltay = turnRad*Math.sin(deltaAngle) + strafeRad*(1 - Math.cos(deltaAngle));
        }

        double[] fieldCentricValues = convertFieldCentric(deltax, deltay);

        xPos += fieldCentricValues[0];
        yPos += fieldCentricValues[1];
    }

    private double normalizeRadians(double angle){
        while(angle >= 2*Math.PI) {
            angle -= 2*Math.PI;
        }

        while(angle < 0.0) {
            angle += 2*Math.PI;
        }

        return angle;
    }

}
