package org.cypher.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.cypher.Subsystem;
import org.cypher.util.Vector;

public class Odometry implements Subsystem {
    private DcMotorEx leftEncoder;
    private DcMotorEx rightEncoder;
    private DcMotorEx backEncoder;

    private static final int L_DIR = 1;
    private static final int R_DIR = 1;
    private static final int B_DIR = -1;

    private int lPos;
    private int rPos;
    private int bPos;

    private int oldLPos = 0;
    private int oldRPos = 0;
    private int oldBPos = 0;

    private int deltaLPos;
    private int deltaRPos;
    private int deltaBPos;
    
//    for the new encoders
//    public final static double ENCODER_COUNTS_PER_INCH = 8192.0 / (2.0 * Math.PI * 1.0);
    public final static double ENCODER_COUNTS_PER_INCH = 4096.0 / (2.0 * Math.PI * 1.0);


    private final static double LR_RADIUS = 17.37831805019305;
    private final static double B_RADIUS = LR_RADIUS;

    private double angle = 0;
    private double angleCorrection = 0;
    private double deltaAngle = 0;
    private double startAngle = 0;

    public double deltax = 0;
    public double deltay = 0;

    public double xPos = 0;
    public double yPos = 0;

    private LinearOpMode opMode;

    @Override
    public void initialize(OpMode opMode) {
        this.opMode = (LinearOpMode) opMode;

        leftEncoder = opMode.hardwareMap.get(DcMotorEx.class, "backRight");
        rightEncoder = opMode.hardwareMap.get(DcMotorEx.class, "frontRight");
        backEncoder = opMode.hardwareMap.get(DcMotorEx.class, "intake");

        resetEncoders();

    }

    public void setStartPos(double xPos, double yPos, double angle) {
        this.xPos = xPos;
        this.yPos = yPos;
        startAngle = Math.toRadians(angle);
    }

    public void setAngleCorrection(double angleCorrection) {
        this.angleCorrection = normalizeRadians(angleCorrection - angle + this.angleCorrection);
    }

    public void updateEncoders() {
        lPos = leftEncoder.getCurrentPosition();
        rPos = rightEncoder.getCurrentPosition();
        bPos = backEncoder.getCurrentPosition();
    }

    public int getLPos() {
        return (int) (lPos * L_DIR);
    }

    public int getRPos() {
        return rPos * R_DIR;
    }

    public int getBPos() {
        return (int) (bPos * B_DIR);
    }

    public void resetEncoders() {
        leftEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        startAngle = 0;
        angle = 0;
        angleCorrection = 0;
        xPos = 0;
        oldBPos = 0;
        oldLPos = 0;
        oldRPos = 0;
        deltay = 0;
        deltax = 0;
        yPos = 0;
        deltaAngle = 0;
        deltaLPos = 0;
        deltaRPos = 0;
        deltaBPos = 0;
    }

    public double[] convertFieldCentric(double xPos, double yPos) {

        double newX = xPos * Math.cos(angle - Math.PI / 2) - yPos * Math.sin(angle - Math.PI / 2);
        double newY = yPos * Math.cos(angle - Math.PI / 2) + xPos * Math.sin(angle - Math.PI / 2);

        return new double[]{newX, newY};
    }

    public void updatePos() {
        updateEncoders();

        deltaLPos = getLPos() - oldLPos;
        deltaRPos = getRPos() - oldRPos;
        deltaBPos = getBPos() - oldBPos;

        oldLPos = getLPos();
        oldRPos = getRPos();
        oldBPos = getBPos();

        deltaAngle = (deltaRPos - deltaLPos) / (2.0 * LR_RADIUS * ENCODER_COUNTS_PER_INCH); //it's in radians
        angle = normalizeRadians((getRPos() - getLPos()) / (2.0 * LR_RADIUS * ENCODER_COUNTS_PER_INCH) + startAngle + angleCorrection);

        if (Math.abs(deltaAngle) == 0) {
            deltax = deltaBPos;
            deltay = (deltaLPos + deltaRPos) / 2d;
        } else {
            double turnRadius = LR_RADIUS * ENCODER_COUNTS_PER_INCH * (deltaLPos + deltaRPos) / (deltaRPos - deltaLPos);
            double strafeRadius = deltaBPos / deltaAngle - B_RADIUS * ENCODER_COUNTS_PER_INCH;


            deltax = turnRadius * (Math.cos(deltaAngle) - 1) + strafeRadius * Math.sin(deltaAngle);
            deltay = turnRadius * Math.sin(deltaAngle) + strafeRadius * (1 - Math.cos(deltaAngle));
        }


        double[] fieldCentricValues = convertFieldCentric(encoderToInch(deltax), encoderToInch(deltay));

        xPos += fieldCentricValues[1];
        yPos += fieldCentricValues[0];
    }


    public float encoderToInch(double encoder) {
        return (float) (encoder / ENCODER_COUNTS_PER_INCH);
    }

    private double normalizeRadians(double angle) {
        while (opMode.opModeIsActive() && angle >= 2 * Math.PI) {
            angle -= 2 * Math.PI;
        }

        while (opMode.opModeIsActive() && angle < 0.0) {
            angle += 2 * Math.PI;
        }

        return angle;
    }

    public double getHeading() {
        return angle;
    }


    public Vector getPos() {
        return new Vector(xPos, yPos);
    }

}
