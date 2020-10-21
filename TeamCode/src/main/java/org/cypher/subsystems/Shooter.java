package org.cypher.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.cypher.Subsystem;

public class Shooter implements Subsystem {
    private DcMotor shooter;

    @Override
    public void initialize(OpMode opMode) {
        shooter = opMode.hardwareMap.dcMotor.get("shooter");
    }

    public void setPower(double power) {
        shooter.setPower(power);
    }
}
