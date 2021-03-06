package org.cypher.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.cypher.Kryptos;
import org.cypher.Subsystem;
import org.cypher.util.PIDController;
import org.cypher.util.Vector;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class DriveTrain implements Subsystem {
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor[] motors;

    public int odoLoopCount = 0;
    private static final int IMU_ANGLE_SYNC_RATE = 3;

    private LinearOpMode opMode;

    private final double ticksPerRotation = 537.6;
    private final double wheelDiameter = 3.937;
    private final double ticksPerWheelRotation = ticksPerRotation;
    private final double distanceInWheelRotation = wheelDiameter * Math.PI;
    private final double ticksPerInch = distanceInWheelRotation / ticksPerWheelRotation;

    private final PIDController xController = new PIDController(.12f, .01f, 0f);
    private final PIDController yController = new PIDController(.12f, .007f, 0.01f);
    private final PIDController angleController = new PIDController(.06f, 0.008f, 0.12f);

    private final PIDController turnController = new PIDController(1/150f,0.006f,0.001f);

    //254

    private Vector currentPos;
    private Vector robotCentric;
    private Vector power;
    private double anglePower;

    private Telemetry.Item item = null;


    @Override
    public void initialize(OpMode opMode) {
        this.opMode = (LinearOpMode) opMode;
        frontLeft = opMode.hardwareMap.dcMotor.get("frontLeft");
        frontRight = opMode.hardwareMap.dcMotor.get("frontRight");
        backLeft = opMode.hardwareMap.dcMotor.get("backLeft");
        backRight = opMode.hardwareMap.dcMotor.get("backRight");

        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motors = new DcMotor[]{frontLeft, frontRight, backLeft, backRight};
        odoLoopCount = 0;

        angleController.setMaxI(1.7);
    }

    public void updatePos() {
        if (odoLoopCount % IMU_ANGLE_SYNC_RATE == 0) {
            double angle = Kryptos.imu.getAngle();
            Kryptos.odometry.setAngleCorrection(Math.toRadians(angle));
        }
        Kryptos.odometry.updatePos();
        odoLoopCount++;
    }

    public void setPowers(double frontLeft, double frontRight, double backLeft, double backRight) {
        this.frontLeft.setPower(frontLeft);
        this.frontRight.setPower(frontRight);
        this.backLeft.setPower(backLeft);
        this.backRight.setPower(backRight);
    }

    public void setPowers(double frontLeft, double frontRight, double backLeft, double backRight, float factor) {
        this.frontLeft.setPower(frontLeft * factor);
        this.frontRight.setPower(frontRight * factor);
        this.backLeft.setPower(backLeft * factor);
        this.backRight.setPower(backRight * factor);

    }


    private int convertInchToEncoder(double inches) {
        return (int) (inches / ticksPerInch);
    }

    private double convertEncoderToInch(int encoder) {
        return ticksPerInch / encoder;
    }

//    public void move(double foward, double left) {
//        move(foward, left, 1d / 1000, 0, 0, 1d / 100);
//    }

//    public void move(double forward, double left, double P, double I, double D, double turnP) {
//        int forwardEncoder = (int) convertInchToEncoder(forward);
//        int leftEncoder = convertInchToEncoder(left);
//        int tolerance = convertInchToEncoder(.8);
//
//        double minSpeed = 0.05;
//        double maxSpeed = .3;
//
//        double diag1I = 0;
//        double diag2I = 0;
//
//        double diag1Speed, diag2Speed;
//        int diag1Pos, diag2Pos;
//        int diag1Error = Integer.MAX_VALUE, diag2Error = Integer.MAX_VALUE;
//        int diag1Target = forwardEncoder - leftEncoder;
//        int diag2Target = forwardEncoder + leftEncoder;
//        int olddiag1Error = diag1Target, olddiag2Error = diag2Target;
//
//        double angleTolerance = .5;
//        double angleMax = .8;
//        double anglePower;
//        double angleDiff = Integer.MAX_VALUE;
//        double startAngle;
//        double currentAngle;
//
//        resetEncoders();
//        Kryptos.setCacheMode(LynxModule.BulkCachingMode.MANUAL);
//        ElapsedTime time = new ElapsedTime();
//        startAngle = Kryptos.imu.getAngle();
//        while (opMode.opModeIsActive() && (Math.abs(diag1Error) > tolerance || Math.abs(diag2Error) > tolerance || Math.abs(angleDiff) > angleTolerance)) {
//            Kryptos.clearCache();
//            diag1Pos = (frontLeft.getCurrentPosition() + backRight.getCurrentdiag2ition()) / 2;
//            diag2Pos = (frontRight.getCurrentdiag2ition() + backLeft.getCurrentdiag2ition()) / 2;
//
//            diag1Error = diag1Target - diag1Pos;
//            diag2Error = diag2Target - diag2Pos;
//
//            double currentTime = time.seconds();
//            time.reset();
//
//            diag1I += diag1Error * currentTime;
//            diag2I += diag2Error * currentTime;
//
////            diag1Speed = clip((P * diag1Error) + (D * ((diag1Error  - olddiag1Error)/currentTime)) + (I * diag1I), minSpeed, maxSpeed);
////            diag2Speed = clip((P * diag2Error) + (D * ((diag2Error - olddiag2Error)/currentTime)) + (I * diag2I), minSpeed, maxSpeed);
//            diag1Speed = P * diag1Error;
//            diag2Speed = P * diag2Error;
//            diag1Speed = clip(diag1Speed, maxSpeed, minSpeed);
//            diag2Speed = clip(diag2Speed, maxSpeed, minSpeed);
//
//            olddiag1Error = diag1Error;
//            olddiag2Error = diag2Error;
//            currentAngle = Kryptos.imu.getAngle();
//
//            angleDiff = getAngleDist(startAngle, currentAngle);
//            if (Math.abs(angleDiff) > angleTolerance) {
//                anglePower = -turnP * angleDiff * getAngleDir(startAngle, currentAngle);
//                anglePower = clip(anglePower, angleMax, .02);
//            } else {
//                anglePower = 0;
//            }
//
//            opMode.telemetry.addData("diag1 target", diag1Target);
//            opMode.telemetry.addData("diag2 target", diag2Target);
//            opMode.telemetry.addData("diag1 diag2", diag1Pos);
//            opMode.telemetry.addData("diag2 diag2", diag2Pos);
//            opMode.telemetry.addData("diag1 error", diag1Error);
//            opMode.telemetry.addData("diag2 error", diag2Error);
//            opMode.telemetry.addData("diag1 speed", diag1Speed);
//            opMode.telemetry.addData("diag2 speed", diag2Speed);
//            opMode.telemetry.addData("angle diff", angleDiff);
//            opMode.telemetry.addData("angle dir", getAngleDir(startAngle, currentAngle));
//            opMode.telemetry.update();
//
//            setMotorPowers(diag1Speed, diag2Speed, anglePower);
//
//
//        }
//        while (opMode.opModeIsActive() && (Math.abs(diag1Error) > tolerance || Math.abs(diag2Error) > tolerance))
//            ;
//        setMotorPowers(0, 0, 0);
//    }

    public void turnRelative(double targetAngle) {
        turnAbsolute(AngleUnit.normalizeDegrees(targetAngle + Math.toDegrees(Kryptos.odometry.getHeading())));
    }

    public void turnAbsolute(double targetAngle) {
        double currentAngle;
        int direction;
        double turnRate;
        double minSpeed = 0.1;
        double maxSpeed = 0.5;
        double tolerance = .4;
        double error = Double.MAX_VALUE;

        turnController.reset();
        while (opMode.opModeIsActive() && (Math.abs(error) > tolerance)) {
            updatePos();
            currentAngle = Math.toDegrees(Kryptos.odometry.getHeading());
            error = getAngleDist(currentAngle, targetAngle);
            direction = getAngleDir(currentAngle, targetAngle);
            turnRate = turnController.getPower((float) error);
            turnRate = clip(turnRate, maxSpeed, minSpeed);
            opMode.telemetry.addData("error", error);
            opMode.telemetry.addData("turnRate", turnRate);
            opMode.telemetry.addData("current", currentAngle);
            opMode.telemetry.addData("dir", direction);
            opMode.telemetry.update();

            setMotorPowers(0, 0, turnRate * direction);
        }
        setMotorPowers(0, 0, 0);
    }

    private double getAngleDist(double targetAngle, double currentAngle) {
        double angleDifference = currentAngle - targetAngle;

        if (Math.abs(angleDifference) > 180) {
            angleDifference = 360 - Math.abs(angleDifference);
        } else {
            angleDifference = Math.abs(angleDifference);
        }

        return angleDifference;
    }


    private int getAngleDir(double targetAngle, double currentAngle) {
        double angleDifference = targetAngle - currentAngle;
        int angleDir = (int) (angleDifference / Math.abs(angleDifference));

        if (Math.abs(angleDifference) > 180) {
            angleDir *= -1;
        }

        return angleDir;
    }


    public static double clip(double val, double max, double min) {
        int sign;
        if (val < 0)
            sign = -1;
        else
            sign = 1;
        if (Math.abs(val) < min)
            return min * sign;
        else if (Math.abs(val) > max)
            return max * sign;
        else
            return val;
    }

    public void resetEncoders() {
        for (DcMotor motor : motors) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    public void loopFollowPath(Vector[] targets, double[] headings) {
        for(int i = 0; i < targets.length; i++) {
            loopMoveToPos(targets[i], headings[i]);
        }
    }

    public void loopMoveToPos(Vector target, double heading) {
        while (opMode.opModeIsActive() && moveToPosition(target,heading));
    }

    public boolean moveToPosition(Vector targetPos, double heading) {
        return moveToPosition(targetPos, heading, .5, .05, .3, 1, 1.5);
    }

    public boolean moveToPosition(Vector targetPos, double heading, double maxSpeed, double minSpeed, double tolerance, double headingTolerance) {
        return moveToPosition(targetPos,heading,maxSpeed,minSpeed,.2,tolerance,headingTolerance);
    }

    public boolean moveToPosition(Vector targetPos, double heading, double maxSpeed, double minSpeed, double maxAngleSpeed, double tolerance, double headingTolerance) {

        updatePos();

        currentPos = Kryptos.odometry.getPos();

        opMode.telemetry.addData("target", targetPos);
        opMode.telemetry.addData("current", currentPos);

        double ydiff = Math.abs(targetPos.getY() - currentPos.getY());
        double xdiff = Math.abs(targetPos.getX() - currentPos.getX());

        PIDController.setUseI(Math.hypot(ydiff, xdiff) <= 5);

        getMotorPowers(targetPos, heading);

        double diag1 = power.getComponent(0);
        double diag2 = power.getComponent(1);
        double headingDiff = getAngleDist(heading, Math.toDegrees(Kryptos.odometry.getHeading()));

        double maxValue = Math.max(diag1, diag2);
        if (maxValue > maxSpeed) {
            diag1 = (diag1 / maxValue) * maxSpeed;
            diag2 = (diag2 / maxValue) * maxSpeed;
        }

        diag1 = clip(diag1, maxSpeed, minSpeed);
        diag2 = clip(diag2, maxSpeed, minSpeed);

        opMode.telemetry.addData("raw angle power", anglePower);


        anglePower = clip(anglePower, maxAngleSpeed, 0.1);
        if (Math.abs(headingDiff) <= headingTolerance) {
            anglePower = 0;
        }

        opMode.telemetry.addData("mod angle power", anglePower);

        anglePower *= -getAngleDir(heading, Math.toDegrees(Kryptos.odometry.getHeading()));


        setMotorPowers(diag1, diag2, anglePower);

        Vector error = Vector.sub(targetPos, currentPos);

        double xDiff = error.getX();
        double yDiff = error.getY();
//        headingDiff = 0;
//        xDiff = 0;
//        opMode.telemetry.addData("diag1", diag1);
//        opMode.telemetry.addData("diag2", diag2);
        opMode.telemetry.addData("xdiff", xDiff);
        if (item == null) {
            item = opMode.telemetry.addData("ydiff", yDiff);
        } else {
            item.setRetained(false);
            item = opMode.telemetry.addData("ydiff", yDiff);
        }
        item.setRetained(true);
        opMode.telemetry.addData("angle diff", headingDiff);


        opMode.telemetry.addData("x I", xController.getI());
        opMode.telemetry.addData("y I", yController.getI());

        opMode.telemetry.update();
        if ((Math.abs(xDiff) < tolerance && Math.abs(yDiff) < tolerance && Math.abs(headingDiff) < headingTolerance) || !opMode.opModeIsActive()) {
            setMotorPowers(0, 0, 0);
            xController.reset();
            yController.reset();
            angleController.reset();
            return false;
        }
        return true;
    }

    public void getMotorPowers(Vector targetPosition, double targetAngle) {

        Vector error = Vector.sub(targetPosition, currentPos);

        opMode.telemetry.addData("field centric error", error);

        double heading  = Kryptos.odometry.getHeading();
        error.rotate(heading);


        opMode.telemetry.addData("robot centric error", error);

        opMode.telemetry.addData("current angle", Math.toDegrees(Kryptos.odometry.getHeading()));
        opMode.telemetry.addData("target angle", targetAngle);



        robotCentric = new Vector((double) -xController.getPower((float) error.getComponent(0).doubleValue()),
                (double) yController.getPower((float) error.getComponent(1).doubleValue()));

        opMode.telemetry.addData("robot centric powers", robotCentric);

        anglePower = angleController.getPower((float) getAngleDist(targetAngle, Math.toDegrees(Kryptos.odometry.getHeading())));

        double leftx = robotCentric.getComponent(0);
        double lefty = robotCentric.getComponent(1);
        double scalar = Math.max(Math.abs(lefty - leftx), Math.abs(lefty + leftx)); //scalar and magnitude scale the motor powers based on distance from joystick origin
        double magnitude = Math.sqrt(Math.pow(lefty, 2) + Math.pow(leftx, 2));

        power = new Vector((lefty + leftx) * magnitude / scalar, (lefty - leftx) * magnitude / scalar);
//
        opMode.telemetry.addData("x power", leftx);
        opMode.telemetry.addData("y power", lefty);
    }


    //0 - leftup, 1 - rightup, 2 - rightdown, 3 - leftdown
    //double frontLeft,
    // double frontRight,
    // double backLeft,
    // double backRight
    public double[] findMotorPowers(double leftX, double leftY, double rightX) {
        double magnitude = Math.sqrt(leftX * leftX + leftY * leftY + rightX * rightX);
        double[] powers = new double[4];
        if (magnitude > 1) {
            powers[0] = (leftX + leftY - rightX) / magnitude;
            powers[1] = (-leftX + leftY + rightX) / magnitude;
            powers[2] = (-leftX + leftY - rightX) / magnitude;
            powers[3] = (leftX + leftY + rightX) / magnitude;
        } else {
            powers[0] = (leftX + leftY - rightX);
            powers[1] = (-leftX + leftY + rightX);
            powers[2] = (-leftX + leftY - rightX);
            powers[3] = (leftX + leftY + rightX);
        }

        return powers;
    }

    public void setMotorPowers(double diag1, double diag2, double rotate) {
        double upleft, upright, downleft, downright;


        upleft = diag1 + rotate;
        downleft = diag2 + rotate;
        upright = diag2 - rotate;
        downright = diag1 - rotate;

        double max = Math.abs(Math.max(Math.max(Math.max(upleft, upright), downleft), downright));
        if (max > 1) {
            upleft /= max;
            upright /= max;
            downleft /= max;
            downright /= max;
        }

        frontLeft.setPower(upleft);
        frontRight.setPower(upright);
        backLeft.setPower(downleft);
        backRight.setPower(downright);


    }

}