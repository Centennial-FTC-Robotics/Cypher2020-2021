package org.cypher.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.cypher.Subsystem;

public class Shooter implements Subsystem {
    private DcMotor shooter;
    private Servo servo;
    private static final double OPEN_POSITION = 0;
    private static final double CLOSE_POSITION = 0;

    //TODO: move servo, then shoot, then move it back


    @Override
    public void initialize(OpMode opMode) {
        shooter = opMode.hardwareMap.dcMotor.get("shooter");
        servo = opMode.hardwareMap.servo.get("shooterServo");

        servo.setPosition(CLOSE_POSITION);
    }

    public void setPower(double power) {
        shooter.setPower(power);
    }

    public void shoot() {
        servo.setPosition(OPEN_POSITION);
        ElapsedTime time = new ElapsedTime();
        while(time.seconds() < .5); //wait .5 secs
        servo.setPosition(CLOSE_POSITION);
    }
}
