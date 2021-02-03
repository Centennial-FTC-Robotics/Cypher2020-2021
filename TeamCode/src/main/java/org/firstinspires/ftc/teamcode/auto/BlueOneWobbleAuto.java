package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.cypher.Kryptos;
import org.cypher.util.Vector;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@Autonomous(name="Blue one wobble goal auto")
public class BlueOneWobbleAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Kryptos.initWithVision(this);

        waitForStart();

        Kryptos.imu.setInitAngle(0);
        //TODO: think abt this and make it work when brain is working
        Kryptos.odometry.setStartPos(0,16,0);
        waitForStart();


        double ringCount = Kryptos.openCV.getRings();

        Telemetry.Item melody =  telemetry.addData("hello! you got thisss u r stroncc u r smart u can do it :))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))) -Melody Chu",null);
        melody.setRetained(true);
        if(ringCount == 0) {
            while(opModeIsActive() && Kryptos.driveTrain.moveToPosition(new Vector(0,0),0));
            while(opModeIsActive() && Kryptos.driveTrain.moveToPosition(new Vector(22.75 * 3, 0),0));
        }
        else if(ringCount == 1) {
            while (opModeIsActive() && Kryptos.driveTrain.moveToPosition(new Vector(0,0),0));
            while(opModeIsActive() && Kryptos.driveTrain.moveToPosition(new Vector(22.75 * 4,0),0));
            while(opModeIsActive() && Kryptos.driveTrain.moveToPosition(new Vector(22.75 * 4,22),0));

        } else {
            while(opModeIsActive() && Kryptos.driveTrain.moveToPosition(new Vector(0,0),0));
            while(opModeIsActive() && Kryptos.driveTrain.moveToPosition(new Vector(22.75 * 5, 0),0));
        }

//        //move wobble goal
//        while(while(opModeIsActive() && Kryptos.driveTrain.moveToPosition(targetZone,0));
//
        Kryptos.wobbleGoalGrabber.flipHinge();
        Kryptos.wobbleGoalGrabber.release();

        ElapsedTime time = new ElapsedTime();
        while (opModeIsActive() && time.seconds() < 1.5);
        Vector pos = Kryptos.odometry.getPos();
//
//
////        //get other wobble goal
//
//        if(ringCount == 0) {
//            while (opModeIsActive() && Kryptos.driveTrain.moveToPosition(new Vector(26.75, 28), 180));
//        } else  {
//            while (opModeIsActive() && Kryptos.driveTrain.moveToPosition(new Vector(22.75 * 3, 28),180));
//            while (opModeIsActive() && Kryptos.driveTrain.moveToPosition(new Vector(26.75, 28), 180));
//        }
//
//
//
//        Kryptos.wobbleGoalGrabber.grab();
//        Kryptos.wobbleGoalGrabber.flipHinge();
//
//        //deliever other wobble goal
//        if(ringCount == 0) {
//            while(opModeIsActive() && Kryptos.driveTrain.moveToPosition(new Vector(22.75 * 3, 0),0));
//        }
//        else if(ringCount == 1) {
//            while (opModeIsActive() && Kryptos.driveTrain.moveToPosition(new Vector(0,0),0));
//            while(opModeIsActive() && Kryptos.driveTrain.moveToPosition(new Vector(22.75 * 4,0),0));
//        } else {
//            while (opModeIsActive() && Kryptos.driveTrain.moveToPosition(new Vector(0,0),0));
//            while(opModeIsActive() && Kryptos.driveTrain.moveToPosition(new Vector(22.75 * 5, 0),0));
//
//        }
//
//        Kryptos.wobbleGoalGrabber.flipHinge();
//        Kryptos.wobbleGoalGrabber.release();
//
//        time.reset();
//        while (opModeIsActive() && time.seconds() < .5);
////

        while(opModeIsActive() && Kryptos.driveTrain.moveToPosition(new Vector(22.75 * 2.5,32.75), 90));

        Kryptos.shooter.shoot(false);
        Kryptos.shooter.shoot(false);

        while(opModeIsActive() && Kryptos.driveTrain.moveToPosition(new Vector(22.75 * 3.5,22.75),90));

    }
}
