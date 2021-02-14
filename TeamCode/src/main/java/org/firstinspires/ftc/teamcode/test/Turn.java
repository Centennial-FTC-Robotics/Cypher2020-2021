package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.cypher.Kryptos;
import org.cypher.util.Vector;


@Autonomous(name = "Turning Test", group = "Test")
public class Turn extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Kryptos.initWithVision(this);
        Kryptos.odometry.setbDir(-1);
        Kryptos.odometry.setStartPos(13, 0, 270);
        Kryptos.imu.setInitAngle(-90);

        waitForStart();
//        Kryptos.driveTrain.loopMoveToPos(new Vector(13, 10), 270);
        Kryptos.driveTrain.turnAbsolute(0);
    }
}
