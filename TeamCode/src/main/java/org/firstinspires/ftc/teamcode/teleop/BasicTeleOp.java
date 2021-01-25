package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.cypher.Robot;
import org.cypher.subsystems.WobbleGoalGrabber;
import org.cypher.util.Vector;
import org.firstinspires.ftc.robotcore.internal.webserver.WebObserver;

@TeleOp(name="Basic TeleOp")
public class BasicTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Robot.init(this);
        Robot.imu.setInitAngle(-90);
        waitForStart();
        double leftX, leftY, rightX;
        double[] powers;
        boolean intakeOn = false;
        int intakeDir = 1;
        boolean isGrabbed = false;
        int wobbleGoalDir = -1;
        boolean isWobbleGoalMoving = false;
        ElapsedTime wobbleGoalTime = new ElapsedTime();
        double intakePower = 0;
        ElapsedTime time = new ElapsedTime();
        double wobbleGoalPower = 0;
        float factor = 1;

        double hingeTimer = 1.5;
        ElapsedTime endGameTime = new ElapsedTime();

        boolean endGame = false;

        while(opModeIsActive()) {
            if(time.milliseconds() > 250) {

                if(gamepad1.a && !gamepad1.start) {
                    intakeOn = !intakeOn;
                    if(intakeOn)
                        intakePower = .7;
                    else
                        intakePower = 0;
                    telemetry.addData("intake on", intakeOn);
                    telemetry.update();
                    time.reset();
                }

                if(gamepad1.right_bumper) {
                    time.reset();
                    intakeDir *= -1;
                }

                if(gamepad1.left_trigger > 0) {
                    factor = .2f;
                }

                if(gamepad2.a && !gamepad2.start) {
                    Robot.shooter.shoot(true);
                    time.reset();
                }

                if(gamepad2.x) {
                    if(isGrabbed)
                        Robot.wobbleGoalGrabber.release();
                    else
                        Robot.wobbleGoalGrabber.grab();

                    isGrabbed = !isGrabbed;
                    time.reset();
                }

                if(gamepad2.b && !gamepad2.start && !isWobbleGoalMoving) {
                    if(wobbleGoalDir == 1)
                        hingeTimer = 2.5;
                    else
                        hingeTimer = 2.5 ;
                    wobbleGoalPower = wobbleGoalDir;
                    wobbleGoalDir*= -1;
                    wobbleGoalTime.reset();
                    isWobbleGoalMoving = true;
                    time.reset();
                }
            }

            if(wobbleGoalTime.seconds() > hingeTimer && isWobbleGoalMoving ) {
                wobbleGoalPower = 0;
                wobbleGoalTime.reset();
                isWobbleGoalMoving = false;
            }


            Robot.wobbleGoalGrabber.moveHinge(wobbleGoalPower);
            Robot.intake.setIntakePower(intakePower * intakeDir);

            if(!(gamepad1.left_trigger > 0)) {
                factor = 1;
            }
            leftX = gamepad1.left_stick_x;
            leftY = -gamepad1.left_stick_y * .9;
            rightX = -gamepad1.right_stick_x * .8;

            powers = Robot.driveTrain.findMotorPowers(leftX, leftY, rightX);
            Robot.driveTrain.setPowers(powers[0], powers[1], powers[2], powers[3], factor);
            telemetry.addData("wobble goal power", wobbleGoalPower);
            telemetry.addData("angle", Robot.imu.getAngle());
            telemetry.addData("game time", endGameTime.seconds());
            telemetry.update();

            if(endGameTime.seconds() >= 90 && !endGame) {
                telemetry.speak("were in the endgame now");
                endGame = true;
            }
          }
    }
}
