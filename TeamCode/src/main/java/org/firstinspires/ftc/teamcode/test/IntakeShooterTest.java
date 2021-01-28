package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.cypher.Kryptos;


@TeleOp(name="Intake and Shooter")
public class IntakeShooterTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Kryptos.init(this);
        waitForStart();
        while(opModeIsActive()) {
            if(gamepad1.a) {
                Kryptos.intake.setIntakePower(.7);
            }

            if(gamepad1.b) {
                Kryptos.intake.setIntakePower(0);
            }

            if(gamepad1.x) {
                Kryptos.shooter.setPower(1) ;
            }

            if(gamepad1.y) {
                Kryptos.shooter.setPower(0);
            }
        }
    }
}
