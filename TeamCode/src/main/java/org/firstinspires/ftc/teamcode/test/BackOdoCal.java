package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.EventLoop;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.cypher.Kryptos;
import org.cypher.purepursuit.Line;

@Autonomous(name = "Back Odo Cal", group = "Test")
public class BackOdoCal extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Kryptos.init(this);
        waitForStart();

        ElapsedTime time = new ElapsedTime();
        double bDiff = 0;
        Kryptos.imu.setInitAngle(0);
        Kryptos.driveTrain.setPowers(.5, -.5, .5, -.5);
        time.reset();

        while (opModeIsActive() && time.seconds() < 1.5) ;
        Kryptos.driveTrain.setPowers(0, 0, 0, 0);
        time.reset();
        while (opModeIsActive() && time.seconds() < .5) ;

        double angleTurned = Kryptos.imu.getAngle();
        Kryptos.odometry.updateEncoders();

        double horizontalDiff = Kryptos.odometry.getLPos() - Kryptos.odometry.getRPos();

        bDiff += horizontalDiff / angleTurned;
        Kryptos.odometry.resetEncoders();

        telemetry.addData("b diff", bDiff);
        telemetry.update();
        while (!gamepad1.a && opModeIsActive()) ;

    }

}
