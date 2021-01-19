package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.cypher.Robot;

@TeleOp
public class ServoTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Robot.init(this);
        waitForStart();
        ElapsedTime time = new ElapsedTime();
        double pos = 0;
        while (opModeIsActive()) {
            if(time.milliseconds() > 100) {
                if(gamepad1.dpad_up && pos < 1) {
                    pos+=.05;
                    time.reset();
                } else if(gamepad1.dpad_down && pos > 0) {
                    pos-=.05;
                    time.reset();
                }
            }
            Robot.shooter.moveServo(pos);
            telemetry.addData("current pos", pos);
            telemetry.update();
        }
    }
}
