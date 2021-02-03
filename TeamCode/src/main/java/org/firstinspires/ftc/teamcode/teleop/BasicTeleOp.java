package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.cypher.Kryptos;
import org.cypher.subsystems.Odometry;
import org.cypher.util.Vector;

@TeleOp(name = "Basic TeleOp")
public class BasicTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Kryptos.init(this);
        Kryptos.imu.setInitAngle(0);

        //TODO: read pos data from file
        waitForStart();
        double leftX, leftY, rightX;
        double[] powers;
        boolean intakeOn = false;
        int intakeDir = 1;
        boolean isGrabbed = false;

        double intakePower = 0;
        ElapsedTime time = new ElapsedTime();
        float factor = 1;

        double hingeTimer = 1.5;
        ElapsedTime gameTime = new ElapsedTime();

        boolean endGame = false;
        Kryptos.odometry.setStartPos(0, 0, 270);
        Kryptos.imu.setInitAngle(-90);
        Kryptos.wobbleGoalGrabber.setHingeIn(false);
        while (opModeIsActive()) {
            Kryptos.driveTrain.updatePos();
//
            Vector pos = Kryptos.odometry.getPos();
            telemetry.addData("x coord", pos.getX());
            telemetry.addData("y coord", pos.getY());
            telemetry.addData("current heading", Math.toDegrees(Kryptos.odometry.getHeading()));
            telemetry.update();

            if (time.milliseconds() > 250) {

                if (gamepad1.a && !gamepad1.start) {
                    intakeOn = !intakeOn;
                    if (intakeOn)
                        intakePower = .7;
                    else
                        intakePower = 0;

                    time.reset();
                }

                if (gamepad1.right_bumper) {
                    time.reset();
                    intakeDir *= -1;
                }

                if (gamepad1.left_trigger > 0) {
                    factor = .2f;
                }

                if (gamepad2.a && !gamepad2.start) {
                    Kryptos.shooter.shoot(true);
                    time.reset();
                }

                if (gamepad2.x) {
                    if (isGrabbed)
                        Kryptos.wobbleGoalGrabber.release();
                    else
                        Kryptos.wobbleGoalGrabber.grab();

                    isGrabbed = !isGrabbed;
                    time.reset();
                }

                if (gamepad2.b && !gamepad2.start) {
                    Kryptos.wobbleGoalGrabber.flipHinge();
                }

            }

            Kryptos.intake.setIntakePower(intakePower * intakeDir);

            if (!(gamepad1.left_trigger > 0)) {
                factor = 1;
            }
            leftX = gamepad1.left_stick_x;
            leftY = -gamepad1.left_stick_y * .9;
            rightX = -gamepad1.right_stick_x * .8;

            telemetry.addData("rightx", rightX);

            powers = Kryptos.driveTrain.findMotorPowers(leftX, leftY, rightX);
            Kryptos.driveTrain.setPowers(powers[0], powers[1], powers[2], powers[3], factor);


            if (gameTime.seconds() >= 90 && !endGame) {
                telemetry.speak("were in the endgame now");
                endGame = true;
            }

            if (gameTime.seconds() >= 120) {
                telemetry.speak("game over");
                requestOpModeStop();
            }
        }
    }
}
