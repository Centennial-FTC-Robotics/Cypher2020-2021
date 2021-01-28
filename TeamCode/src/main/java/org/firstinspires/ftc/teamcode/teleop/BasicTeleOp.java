package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.cypher.Kryptos;
import org.cypher.subsystems.Odometry;
import org.cypher.util.Vector;

@TeleOp(name="Basic TeleOp")
public class BasicTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Kryptos.init(this);
        Kryptos.imu.setInitAngle(0);
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
        Kryptos.odometry.setStartPos(0,0,0);
        Kryptos.imu.setInitAngle(0);
        while(opModeIsActive()) {
            Kryptos.driveTrain.updatePos();
            telemetry.addData("odo l encoder", Kryptos.odometry.getLPos()/ Odometry.ENCODER_COUNTS_PER_INCH);
            telemetry.addData("odo r encoder", Kryptos.odometry.getRPos()/ Odometry.ENCODER_COUNTS_PER_INCH);
            telemetry.addData("odo b encoder", Kryptos.odometry.getBPos()/ Odometry.ENCODER_COUNTS_PER_INCH);
            telemetry.addData("rot", Math.toDegrees(Kryptos.odometry.getHeading()));
            telemetry.addData("imu rot", Kryptos.imu.getAngle());
            telemetry.addData("imu sync", Kryptos.driveTrain.odoLoopCount);
            telemetry.addData("delta angle", Math.toDegrees(Kryptos.odometry.getDeltaAngle()));


            Vector pos = Kryptos.odometry.getPos();
            telemetry.addData("x",pos.getX() );
            telemetry.addData("y",pos.getY());
            telemetry.update();

            if(time.milliseconds() > 250) {

                if(gamepad1.a && !gamepad1.start) {
                    intakeOn = !intakeOn;
                    if(intakeOn)
                        intakePower = .7;
                    else
                        intakePower = 0;

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
                    Kryptos.shooter.shoot(true);
                    time.reset();
                }

                if(gamepad2.x) {
                    if(isGrabbed)
                        Kryptos.wobbleGoalGrabber.release();
                    else
                        Kryptos.wobbleGoalGrabber.grab();

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


            Kryptos.wobbleGoalGrabber.moveHinge(wobbleGoalPower);
            Kryptos.intake.setIntakePower(intakePower * intakeDir);

            if(!(gamepad1.left_trigger > 0)) {
                factor = 1;
            }
            leftX = gamepad1.left_stick_x;
            leftY = -gamepad1.left_stick_y * .9;
            rightX = -gamepad1.right_stick_x * .8;

            powers = Kryptos.driveTrain.findMotorPowers(leftX, leftY, rightX);
            Kryptos.driveTrain.setPowers(powers[0], powers[1], powers[2], powers[3], factor);


            if(endGameTime.seconds() >= 90 && !endGame) {
                telemetry.speak("were in the endgame now");
                endGame = true;
            }
          }
    }
}
