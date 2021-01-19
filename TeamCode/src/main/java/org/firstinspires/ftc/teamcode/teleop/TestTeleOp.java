package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.cypher.Robot;
import org.cypher.util.Vector;

@TeleOp(name="Test TeleOp")
public class TestTeleOp extends LinearOpMode {
    private Vector leftStick, motorSpeeds;

    //
    @Override
    public void runOpMode() throws InterruptedException {
        Robot.init(this);
        waitForStart();
        Robot.imu.setInitAngle(180);
        double rotate;
        leftStick = new Vector(0, 0);

        while(opModeIsActive()) {
           findMotorPowers();
           rotate = gamepad1.right_stick_x * .8;
           Robot.driveTrain.setMotorPowers(motorSpeeds.getX(), motorSpeeds.getY(), rotate);

        }
    }

    public void findMotorPowers() {
        double leftX = gamepad1.left_stick_x * .8;
        double leftY = -gamepad1.left_stick_y * .9;
        leftStick.setComponents(leftX, leftY);
        double scalar = Math.max(Math.abs(leftY - leftX), Math.abs(leftX+leftY));
        double magnitude = leftStick.magnitude();

        leftStick.rotate(Robot.imu.getInitAngle() - Robot.imu.getAngle());

        motorSpeeds = new Vector((leftX+leftX)*magnitude/scalar, (leftY-leftX)*magnitude/scalar);
    }
}
