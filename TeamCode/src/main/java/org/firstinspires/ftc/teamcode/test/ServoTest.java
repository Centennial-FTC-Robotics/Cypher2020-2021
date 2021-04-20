package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.cypher.Kryptos;
import org.cypher.subsystems.Shooter;
import org.cypher.subsystems.WobbleGoal;

public class ServoTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Kryptos.init(this);
        waitForStart();
        ElapsedTime time = new ElapsedTime();
        double pos = 0; //set this to the base pos for whatever servo ur testing
        while (opModeIsActive()) {
            telemetry.addData("current pos", pos);
            telemetry.update();
            if (time.milliseconds() > 200) {
                if (gamepad1.dpad_up) {
                    pos += .02;
                    time.reset();
                } else if (gamepad1.dpad_down) {
                    pos -= .02;
                    time.reset();
                }
            }
            //make the servo ur testing move here
        }
    }
}
