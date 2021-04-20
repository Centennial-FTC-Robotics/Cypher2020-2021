package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.cypher.Kryptos;

public class BetterOdoCal extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        Kryptos.init(this);
        waitForStart();
        while (!gamepad1.a && opModeIsActive()) {
            Kryptos.odometry.updatePos();
        }

        double leftRightDelta = Kryptos.odometry.getLPos() - Kryptos.odometry.getRPos();
        double diameter = (Kryptos.odometry.getLPos() - Kryptos.odometry.getRPos()) / (Math.toRadians(Kryptos.imu.getAngle()));
        double offset = Kryptos.odometry.getBPos() / Math.toRadians(Kryptos.imu.getAngle());
        while (!opModeIsActive()) {
            telemetry.addData("diameter", diameter);
            telemetry.addData("offset", offset);
            telemetry.update();
        }
    }
}
