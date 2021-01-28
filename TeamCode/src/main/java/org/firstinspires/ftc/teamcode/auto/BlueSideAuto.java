package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.cypher.Kryptos;

@Autonomous
public class BlueSideAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Kryptos.initWithVision(this);
        Kryptos.imu.setInitAngle(-90);
        waitForStart();
        Kryptos.openCV.saveRingCount();
        Kryptos.openCV.stop();
        double sidewaysDistFromFirst;
        double forwardDistFromFirst;


        if(Kryptos.openCV.getRings() == 0 ) {
            sidewaysDistFromFirst = 0;
            forwardDistFromFirst = 0;
        } else if(Kryptos.openCV.getRings() == 1) {
                forwardDistFromFirst = 22.75;
                sidewaysDistFromFirst = 22.75;
        } else {
            sidewaysDistFromFirst = 22.75 * 2;
            forwardDistFromFirst = 0;
        }
        Kryptos.driveTrain.move(-22.75 * .5,0);
        Kryptos.driveTrain.move(0,22.75 * 2 + sidewaysDistFromFirst + 15);
        Kryptos.driveTrain.move(forwardDistFromFirst,0);

        Kryptos.wobbleGoalGrabber.moveHinge(1);
        ElapsedTime time = new ElapsedTime();
        while (time.seconds() < 1.3 && opModeIsActive());
        Kryptos.wobbleGoalGrabber.moveHinge(0);
        Kryptos.wobbleGoalGrabber.release();
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

        if(Kryptos.openCV.getRings() == 0) {
            Kryptos.driveTrain.move(22.75 + 10,0);
            Kryptos.driveTrain.turnRelative(85);
            Kryptos.shooter.shoot(false);
        } else if(Kryptos.openCV.getRings() == 1) {
            Kryptos.driveTrain.move(0,-22.75 - 15);
            Kryptos.driveTrain.turnRelative(85);
            Kryptos.shooter.shoot(false);
        } else {
            Kryptos.driveTrain.move(22.75,0);
            Kryptos.driveTrain.move(0,-22.75 - 20);
            Kryptos.driveTrain.turnRelative(85);
            Kryptos.shooter.shoot(false);
        }
        Kryptos.driveTrain.move(25,0);

    }
}
