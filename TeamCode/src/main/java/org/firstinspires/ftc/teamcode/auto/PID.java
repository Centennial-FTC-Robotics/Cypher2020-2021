package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.cypher.Kryptos;
import org.cypher.util.PIDController;

@Autonomous
public class PID extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Kryptos.init(this);
        waitForStart();

        float val = 1000;
        float P = 1/val;

        PIDController controller = new PIDController(P,0,0);
        ElapsedTime time = new ElapsedTime();
        int dir = -1;
        while (opModeIsActive()) {
            controller.editConstants(P,0,0);
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
                    while(Kryptos.driveTrain.goToPos(0,22.75 * 3,0,.8,1,1) && opModeIsActive());
                }
                P = 1f/val;
                time.reset();
            }


        }
    }
}
