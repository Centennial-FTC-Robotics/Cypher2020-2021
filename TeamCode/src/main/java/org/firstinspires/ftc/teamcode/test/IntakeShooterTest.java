package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.cypher.Robot;


@TeleOp(name="Intake and Shooter")
public class IntakeShooterTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Robot.init(this);
        waitForStart();
        while(opModeIsActive()) {
            if(gamepad1.a) {
                Robot.intake.setIntakePower(.7);
            }

            if(gamepad1.b) {
                Robot.intake.setIntakePower(0);
            }

            if(gamepad1.x) {
                Robot.shooter.setPower(1) ;
            }

            if(gamepad1.y) {
                Robot.shooter.setPower(0);
            }
        }
    }
}
