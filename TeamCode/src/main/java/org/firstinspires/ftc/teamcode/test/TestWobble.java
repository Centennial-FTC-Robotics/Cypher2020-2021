package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.cypher.Kryptos;

@TeleOp
public class TestWobble extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Kryptos.initWithVision(this);
        waitForStart();

        while(opModeIsActive()) {
            if(gamepad1.a) {
                Kryptos.wobbleGoal.depositWobble();
            }
            if (gamepad1.b) {
                Kryptos.wobbleGoal.grabWobble();
            }
        }
    }
}
