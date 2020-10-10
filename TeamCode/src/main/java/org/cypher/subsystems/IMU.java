package org.cypher.subsystems;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.cypher.Subsystem;

public class IMU implements Subsystem {
    private float angle;
    private BNO055IMU imu;
    //TODO: do it danylo

    @Override
    public void initialize(OpMode opMode) {

    }

    public void normalize() {

    }

    public float getAngle() {
        return angle;
    }


}
