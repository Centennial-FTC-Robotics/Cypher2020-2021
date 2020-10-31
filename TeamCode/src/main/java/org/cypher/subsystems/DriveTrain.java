package org.cypher.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.cypher.Robot;
import org.cypher.Subsystem;

public class DriveTrain implements Subsystem {
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    private int odoLoopCount = 0;
    private static final int IMU_ANGLE_SYNC_RATE = 50;

    @Override
    public void initialize(OpMode opMode) {
        frontLeft = opMode.hardwareMap.dcMotor.get("frontLeft");
        frontRight = opMode.hardwareMap.dcMotor.get("frontRight");
        backLeft = opMode.hardwareMap.dcMotor.get("backLeft");
        backRight = opMode.hardwareMap.dcMotor.get("backRight");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void updatePos() {
        if(odoLoopCount % IMU_ANGLE_SYNC_RATE == 1) {
            double angle = Robot.imu.getAngle();
            Robot.odometry.setAngleCorrection(angle);
        }
        Robot.odometry.updatePos();
        odoLoopCount++;
    }

    public void setPowers(double frontLeft, double frontRight, double backLeft, double backRight) {
        this.frontLeft.setPower(frontLeft);
        this.frontRight.setPower(frontRight);
        this.backLeft.setPower(backLeft);
        this.backRight.setPower(backRight);
    }

    //TODO: finish these two
    public void setPowers(double frontLeft, double frontRight, double backLeft, double backRight, double anglePower) {

        frontLeft+= anglePower;
        frontRight+= anglePower;
        backLeft-= anglePower;
        backRight-= anglePower;

        double maxPower = Math.abs(Math.max(Math.max(Math.max(frontLeft,frontRight),backLeft),backRight));
        if(maxPower > 1) {
            frontLeft = frontLeft / maxPower;
            frontRight = frontRight / maxPower;
            backLeft = backLeft / maxPower;
            backRight = backRight / maxPower;
        }

        setPowers(frontLeft,frontRight,backLeft,backRight);

    }

    public void goToPos(double x, double y, double angle, double maxSpeed, double tolerance, double angleTolerance) {
        updatePos();

    }

}