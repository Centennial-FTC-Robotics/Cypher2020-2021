package org.cypher.subsystems;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.cypher.Subsystem;

public class IMU implements Subsystem {
    private double angle, initAngle = 0;
    private BNO055IMU imu;

    @Override
    public void initialize(OpMode opMode) {
        initAngle = 0;
        opMode.telemetry.addLine("initing imu");
        opMode.telemetry.update();

        imu = opMode.hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "AdafruitIMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu.initialize(parameters);
        LinearOpMode linOpMode = (LinearOpMode) opMode;

        while (!linOpMode.isStopRequested() && !imu.isGyroCalibrated()) ;

//        initAngle = imu.getAngularOrientation().firstAngle;

        opMode.telemetry.addLine("imu inited");
        opMode.telemetry.update();
    }

    public void setInitAngle(float initAngle) {
        this.initAngle = initAngle;
    }

    public double getInitAngle() {
        return initAngle;
    }


    public void normalize() {
        angle = imu.getAngularOrientation().firstAngle + initAngle;
    }

    public double getAngle() {
        normalize();
        return angle;
    }
}