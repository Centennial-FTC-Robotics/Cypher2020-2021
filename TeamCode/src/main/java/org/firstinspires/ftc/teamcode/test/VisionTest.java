package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.cypher.Robot;

@TeleOp
public class VisionTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Robot.initWithVision(this);
        waitForStart();

        while (opModeIsActive()) {

        }
    }
}
