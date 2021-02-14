package org.firstinspires.ftc.teamcode.test;
//Coleleco deseometri dopecod  tri latic bros
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.cypher.Kryptos;
import org.cypher.purepursuit.Line;
import org.cypher.util.PIDController;
import org.cypher.util.Vector;

@Autonomous(name = "PID Test", group = "Test")
public class PID extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Kryptos.init(this);
        waitForStart();

        float P = 0.05f;
        PIDController controller = new PIDController(P, 0, .03f);
        ElapsedTime time = new ElapsedTime();
        int dir = -1;
        Kryptos.odometry.setStartPos(0, 0, 270);
        Kryptos.imu.setInitAngle(-90);
        Kryptos.odometry.setbDir(-1);
        while (opModeIsActive()) {
            controller.editConstants(P, 0, 0);
            telemetry.addData("p", P);
            Kryptos.driveTrain.updatePos();
            Vector pos = Kryptos.odometry.getPos();
            telemetry.addData("x coord", pos.getX());
            telemetry.addData("y coord", pos.getY());
            telemetry.addData("current heading", Math.toDegrees(Kryptos.odometry.getHeading()));
            telemetry.update();
            if (time.milliseconds() > 100) {
                if (gamepad1.dpad_up) {
                    P += .02d;
                } else if (gamepad1.dpad_down) {
                    P -= .02f;
                } else if (gamepad1.a && !gamepad1.start) {
                    if (dir == -1) {
                        while (Kryptos.driveTrain.moveToPosition(new Vector(0,12), 270, .5, .05, 1, 1))
                            ;
                        dir = 1;
                    } else {
                        dir = -1;
                        while (Kryptos.driveTrain.moveToPosition(new Vector(0 , 0), 270, .5, .05, 1, 1))
                            ;
                    }

                }
                time.reset();
            }


        }
    }
}
