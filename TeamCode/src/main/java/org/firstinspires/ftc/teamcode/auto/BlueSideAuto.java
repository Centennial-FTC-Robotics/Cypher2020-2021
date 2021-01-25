package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.cypher.Robot;

@Autonomous
public class BlueSideAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Robot.initWithVision(this);
        Robot.imu.setInitAngle(-90);
        waitForStart();
        Robot.openCV.saveRingCount();
        Robot.openCV.stop();
        double sidewaysDistFromFirst;
        double forwardDistFromFirst;


        if(Robot.openCV.getRings() == 0 ) {
            sidewaysDistFromFirst = 0;
            forwardDistFromFirst = 0;
        } else if(Robot.openCV.getRings() == 1) {
                forwardDistFromFirst = 22.75;
                sidewaysDistFromFirst = 22.75;
        } else {
            sidewaysDistFromFirst = 22.75 * 2;
            forwardDistFromFirst = 0;
        }
        Robot.driveTrain.move(-22.75 * .5,0);
        Robot.driveTrain.move(0,22.75 * 2 + sidewaysDistFromFirst + 15);
        Robot.driveTrain.move(forwardDistFromFirst,0);

        Robot.wobbleGoalGrabber.moveHinge(1);
        ElapsedTime time = new ElapsedTime();
        while (time.seconds() < 1.3 && opModeIsActive());
        Robot.wobbleGoalGrabber.moveHinge(0);
        Robot.wobbleGoalGrabber.release();
//
//        Robot.driveTrain.turnAbsolute(180);
//        Robot.driveTrain.move(-22.75 - forwardDistFromFirst - 7, 0);
//        Robot.driveTrain.move(0,sidewaysDistFromFirst + (22.75 *2) - 16);
//        Robot.wobbleGoalGrabber.grab();
//
//        time.reset();
//        while (time.seconds() < .5 && opModeIsActive());
//        Robot.wobbleGoalGrabber.moveHinge(-1);
//        time.reset();
//        while (time.seconds() < .5 && opModeIsActive());
//        Robot.wobbleGoalGrabber.moveHinge(0);
//
//
//        Robot.driveTrain.turnAbsolute(0);
//        Robot.driveTrain.move(-(22.75 * 1.5) + forwardDistFromFirst + 5, 0);
//        Robot.driveTrain.move(0,22.75 * 2 + sidewaysDistFromFirst - 15);
//
//        Robot.wobbleGoalGrabber.moveHinge(1);
//        time.reset();
//        while (time.seconds() < 1.3 && opModeIsActive());
//        Robot.wobbleGoalGrabber.moveHinge(0);
//        Robot.wobbleGoalGrabber.release();
//
//        Robot.wobbleGoalGrabber.moveHinge(-1);
//        time.reset();
//        while (time.seconds() < .5 && opModeIsActive());
//        Robot.wobbleGoalGrabber.moveHinge(0);
//
//        Robot.driveTrain.turnAbsolute(0);
//        Robot.driveTrain.move(-10, 0);
//        Robot.driveTrain.move(0,-22.75 * .4 - 15);

        if(Robot.openCV.getRings() == 0) {
            Robot.driveTrain.move(22.75 + 10,0);
            Robot.driveTrain.turnRelative(85);
            Robot.shooter.shoot(false);
        } else if(Robot.openCV.getRings() == 1) {
            Robot.driveTrain.move(0,-22.75 - 15);
            Robot.driveTrain.turnRelative(85);
            Robot.shooter.shoot(false);
        } else {
            Robot.driveTrain.move(22.75,0);
            Robot.driveTrain.move(0,-22.75 - 20);
            Robot.driveTrain.turnRelative(85);
            Robot.shooter.shoot(false);
        }
        Robot.driveTrain.move(25,0);

    }
}
