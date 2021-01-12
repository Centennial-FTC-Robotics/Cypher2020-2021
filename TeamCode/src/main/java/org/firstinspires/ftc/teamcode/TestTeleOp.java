package org.firstinspires.ftc.teamcode;

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
        Robot.imu.setInitAngle(90);
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
        double lefyY = -gamepad1.left_stick_y * .9;
        leftStick.setComponents(leftX, lefyY);
        double scalar = Math.max(Math.abs(lefyY - leftX), Math.abs(leftX+lefyY));
        double magnitude = leftStick.magnitude();

        leftStick.rotate(Robot.imu.getInitAngle() - Robot.imu.getAngle());

        motorSpeeds = new Vector((leftX+leftX)*magnitude/scalar, (lefyY-leftX)*magnitude/scalar);
    }
}
