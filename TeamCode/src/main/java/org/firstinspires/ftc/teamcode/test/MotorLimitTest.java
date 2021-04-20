package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.cypher.Kryptos;

@TeleOp(name = "Motor Limit Tester", group = "Test")
public class MotorLimitTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Kryptos.init(this);
        int pos;
        double power;
        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("WARNING", "the encoder limit is disabled for this opmode so pls be careful and dont break something lmao");
            telemetry.addData("current pos", Kryptos.shooter.getStoragePos());
            telemetry.update();

            power = -gamepad1.left_stick_y / 4;
            Kryptos.shooter.setStoragePower(power);
        }
    }
}
