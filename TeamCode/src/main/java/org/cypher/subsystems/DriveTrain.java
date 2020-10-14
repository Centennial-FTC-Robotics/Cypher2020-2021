package org.cypher.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.cypher.Subsystem;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

public class DriveTrain implements Subsystem {
    //TODO: add motors bill
    @Override
    public void initialize(OpMode opMode) {

    }

    public void setPowers(float frontLeft, float frontRight, float backLeft, float backRight) {

    }

    protected void stopEverything() {
        telemetry.addLine("Stopping");
        telemetry.update();

        /*stop the motors
        for (DcMotorEx : motors) {
            motor.setPower(0);
        } //also slides
        */
    }
}