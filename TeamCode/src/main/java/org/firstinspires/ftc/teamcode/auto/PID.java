package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.cypher.Robot;

@Autonomous
public class PID extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Robot.init(this);
        waitForStart();

        double val = 1000;
        double P = 1/val;
        ElapsedTime time = new ElapsedTime();
        int dir = -1;
        while (opModeIsActive()) {
            telemetry.addData("val", val);
            telemetry.addData("p", P);
            telemetry.update();
            if(time.milliseconds() > 100) {
                if (gamepad1.dpad_up) {
                    val += 100;
                } else if (gamepad1.dpad_down) {
                    val -= 100;
                } else if (gamepad1.a && !gamepad1.start) {
                    if(dir == -1)
                        dir = 1;
                    else
                        dir = -1;
                    Robot.driveTrain.move(22.75 * 3* dir, 0, P, 0, 0,1d/200);
                }
                P = 1d/val;
                time.reset();
            }


        }
    }
}
