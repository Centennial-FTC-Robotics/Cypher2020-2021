package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.cypher.Kryptos;
import org.cypher.util.Vector;

@TeleOp(name = "Odo Position Saving", group = "Test")
public class OdoPosSavingTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Kryptos.initWithVision(this);
        while (opModeIsActive()) {
            Kryptos.driveTrain.updatePos();
            Vector pos = Kryptos.odometry.getPos();
            telemetry.addData("x coord", pos.getX());
            telemetry.addData("y coord", pos.getY());
            telemetry.addData("current heading", Math.toDegrees(Kryptos.odometry.getHeading()));
            telemetry.update();
        }
        Kryptos.saveOdoData();
    }
}
