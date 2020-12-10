package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.cypher.Robot;

@TeleOp()
public class UseThisTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Robot.init(this);
        waitForStart();

        double leftX, leftY, rightX;
        double[] powers;
        while(opModeIsActive()) {
            leftX = gamepad1.left_stick_x * .8;
            leftY = gamepad1.left_stick_y * .9;
            rightX = gamepad1.right_stick_x * .8;

            powers = Robot.driveTrain.findMotorPowers(leftX, leftY, rightX);
            Robot.driveTrain.setPowers(powers[0], powers[1], powers[3], powers[2]);
        }
    }
}
