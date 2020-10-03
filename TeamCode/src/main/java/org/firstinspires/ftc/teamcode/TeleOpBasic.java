package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="TeleOpBasic", group="TeleOp")
public class TeleOpBasic extends OpMode {

    DcMotor frontLeft, backLeft, frontRight, backRight;

    @Override
    public void init() {


        frontLeft = hardwareMap.get(DcMotor.class, "Front Left");
        frontRight = hardwareMap.get(DcMotor.class, "Front Right");
        backLeft = hardwareMap.get(DcMotor.class, "Back Left");
        backRight = hardwareMap.get(DcMotor.class, "Back Right");

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        }

    @Override
    public void loop() {

        boolean right = gamepad1.dpad_right;
        boolean left = gamepad1.dpad_left;
        double pos = -gamepad1.left_stick_y;

        frontLeft.setPower(pos);
        frontRight.setPower(pos);
        backLeft.setPower(pos);
        backRight.setPower(pos);

        if (left) {

            frontLeft.setPower(1);
            //backRight.setPower(1);

        }

        if (right) {

            frontRight.setPower(1);
            //backLeft.setPower(1);

        }


    }




}
