package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.cypher.Robot;

public class BasicTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Robot.init(this);
        waitForStart();

        double leftX, leftY, rightX;
        double[] powers;
        boolean shoot;
        boolean grab;
        boolean grabToggle = false;
        while(opModeIsActive()) {
            leftX = gamepad1.left_stick_x;
            leftY = -gamepad1.left_stick_y;
            rightX = gamepad1.right_stick_x;
            shoot = gamepad1.right_bumper;
            grab = gamepad2.a;

            powers = Robot.driveTrain.findMotorPowers(leftX,leftY,rightX);
            Robot.driveTrain.setPowers(powers[0], powers[2],powers[1], powers[3]);

            if(shoot)
                Robot.shooter.shoot();
            if(grab) {
                if(!grabToggle)
                    Robot.wobbleGoalGrabber.grab();
                else
                    Robot.wobbleGoalGrabber.release();
                grabToggle = !grabToggle;
            }
        }
    }
}
