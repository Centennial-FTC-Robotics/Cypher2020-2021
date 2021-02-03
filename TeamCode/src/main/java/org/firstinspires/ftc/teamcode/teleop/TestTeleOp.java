package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.cypher.Kryptos;
import org.cypher.util.Vector;

@TeleOp(name="Test TeleOp")
public class TestTeleOp extends LinearOpMode {
    private Vector leftStick, motorSpeeds;

    //
    @Override
    public void runOpMode() throws InterruptedException {
        Kryptos.init(this);
        waitForStart();
        Kryptos.imu.setInitAngle(180);
        double rotate;
        leftStick = new Vector(0, 0);

        while(opModeIsActive()) {
           findMotorPowers();
           rotate = gamepad1.right_stick_x * .8;
           Kryptos.driveTrain.setMotorPowers(motorSpeeds.getX(), motorSpeeds.getY(), rotate);

        }
    }

    public void findMotorPowers() {
        double leftX = gamepad1.left_stick_x * .8;
        double leftY = -gamepad1.left_stick_y * .9;
        leftStick = new Vector(leftX, leftY);
        double scalar = Math.max(Math.abs(leftY - leftX), Math.abs(leftX+leftY));
        double magnitude = leftStick.getMag();

        leftStick.rotate(Kryptos.imu.getInitAngle() - Kryptos.imu.getAngle());

        motorSpeeds = new Vector((leftX+leftX)*magnitude/scalar, (leftY-leftX)*magnitude/scalar);
    }
}
