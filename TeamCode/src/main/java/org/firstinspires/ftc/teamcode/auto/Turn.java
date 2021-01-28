package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.cypher.Kryptos;

@Autonomous
public class Turn extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Kryptos.init(this);
        waitForStart();
        Kryptos.driveTrain.turnRelative(90);
    }
}
