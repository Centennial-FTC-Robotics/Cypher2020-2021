package org.cypher.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.cypher.Subsystem;

public class Intake implements Subsystem {
    private DcMotor intake;
    @Override
    public void initialize(OpMode opMode) {
        intake = opMode.hardwareMap.dcMotor.get("intake");
//        intake.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    private void setIntakePower(double power) {
        intake.setPower(power);
    }
}
