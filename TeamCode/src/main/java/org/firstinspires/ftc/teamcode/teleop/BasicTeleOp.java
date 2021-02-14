package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.cypher.Kryptos;

import org.cypher.util.Vector;

@TeleOp(name = "Basic TeleOp", group = "Tele-Op")
public class BasicTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Kryptos.init(this);
        Kryptos.readOdoData();
        waitForStart();
        double leftX, leftY, rightX;
        double[] powers;
        float factor = 1;

        double intakePower = 0;
        boolean intakeOn = false;
        int intakeDir = 1;

        boolean isGrabbed = true;

        ElapsedTime time = new ElapsedTime();
        ElapsedTime gameTime = new ElapsedTime();

        boolean endGame = false;

        Kryptos.readOdoData();
//
//        Kryptos.imu.setInitAngle(-90);
//        Kryptos.odometry.setStartPos(0, 0, 270 );
//        Kryptos.odometry.setbDir(-1);
        Kryptos.wobbleGoal.setHingeIn(false);

        while (opModeIsActive()) {
            Kryptos.driveTrain.updatePos();
            Vector pos = Kryptos.odometry.getPos();

            telemetry.addData("right", Kryptos.odometry.encoderToInch(Kryptos.odometry.getRPos()));
            telemetry.addData("left", Kryptos.odometry.encoderToInch(Kryptos.odometry.getLPos()));
            telemetry.addData("back", Kryptos.odometry.encoderToInch(Kryptos.odometry.getBPos()));
            telemetry.addData("imu rot", Kryptos.imu.getAngle());
            telemetry.addData("x coord", pos.getX());
            telemetry.addData("y coord", pos.getY());
            telemetry.addData("current heading", Math.toDegrees(Kryptos.odometry.getHeading()));
            telemetry.addData("storage pos", Kryptos.shooter.getStoragePos());
            telemetry.update();

            if (time.milliseconds() > 250) {

                if (gamepad1.a && !gamepad1.start) {
                    intakeOn = !intakeOn;
                    if (intakeOn)
                        intakePower = 1;
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

                if(gamepad2.y) {
                    Kryptos.shooter.shootOne(true);
                    time.reset();
                }

                if (gamepad2.x) {
                    if (isGrabbed)
                        Kryptos.wobbleGoal.release();
                    else
                        Kryptos.wobbleGoal.grab();

                    isGrabbed = !isGrabbed;
                    time.reset();
                }

                if (gamepad2.b && !gamepad2.start) {
                    Kryptos.wobbleGoal.flipHinge();
                    time.reset();
                }


            }

            Kryptos.intake.setIntakePower(intakePower * intakeDir);

            if (!(gamepad1.left_trigger > 0)) {
                factor = 1;
            }
            leftX = gamepad1.left_stick_x;
            leftY = -gamepad1.left_stick_y * .9;
            rightX = -gamepad1.right_stick_x * .7;

            Vector controlVector = new Vector(leftX, leftY);
            controlVector.rotate(-Kryptos.odometry.getHeading());

            telemetry.addData("rightx", rightX);

            powers = Kryptos.driveTrain.findMotorPowers(controlVector.getX(), controlVector.getY(), rightX);
            Kryptos.driveTrain.setPowers(powers[0], powers[1], powers[2], powers[3], factor);

        }

        Kryptos.saveOdoData(0,0,0);
    }
}
