package org.firstinspires.ftc.teamcode.auto;

import com.google.gson.internal.$Gson$Preconditions;
import com.qualcomm.robotcore.eventloop.EventLoop;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.cypher.Kryptos;
import org.cypher.util.Vector;


@Autonomous(name = "Redside Auto", group = "Red Auto")
public class RedSideAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        /*
        this path is weird
        it requires turning 90 degrees to face the goals
        and then delivering the wobble goal
        shooting
        picking up the 2nd wobble goal
        delivering the 2nd wobblw goal
        park

        all without ever turning again
        there should be literally 0 turns after that first turn in the beginning
        if u want an example of what im talking abt look at expo auto its basically the same thing
        anyway the movement positions need to be changed
        so it can work
        so yea

        ALSO the reaosn why u cant turn is it messes up the position
        as u see right after we turn we then tell it "yo this is where ur at ignore where u think u are"
        its jank but idc anymore


        also also 0 ring auto worked one time so im not gonna try it again im done
        4 ring auto should work if not
         */
        Kryptos.initWithVision(this);
        Kryptos.odometry.setbDir(-1);
        Kryptos.odometry.setStartPos(13, 8  , 270);
        Kryptos.imu.setInitAngle(-90);

        waitForStart();

        ElapsedTime timeTaken = new ElapsedTime();
//
        int rings = Kryptos.openCV.getRings();
//
        //deliver first wobble goal
        Vector targetZone;

        Vector[] targetZones;
        double[] headings;
        if (rings == 0) {
            targetZone = new Vector(16,24 * 3.2);
            targetZones = new Vector[]{targetZone};
            headings = new double[]{0};
        } else if (rings == 1) {
            targetZone = new Vector(30, 24 * 4);
            targetZones = new Vector[]{targetZone, new Vector(36, 24 * 3.7)};
            headings = new double[]{0, 0};
        } else {
            targetZone = new Vector(16, 24 * 5);
            targetZones = new Vector[]{targetZone};
            headings = new double[]{0};
        }

//      while(opModeIsActive() && Kryptos.driveTrain.moveToPosition(new Vector(13, 10), 270));
        Kryptos.driveTrain.turnAbsolute(0);
        Kryptos.odometry.setPosition(16, 0, Kryptos.odometry.getHeading());


//        Kryptos.driveTrain.loopFollowPath(targetZones, headings);

        while(opModeIsActive() && Kryptos.driveTrain.moveToPosition(targetZones[0],headings[0]));
        if(rings == 1)
            while(opModeIsActive() && Kryptos.driveTrain.moveToPosition(targetZones[1],headings[1]));
        Kryptos.wobbleGoal.depositWobble();

        //shoot
        while(opModeIsActive() && Kryptos.driveTrain.moveToPosition(new Vector(20, 48), 0,.5, .05, .3, 1, .5));
//        Kryptos.shooter.shoot(false);
        ElapsedTime timer = new ElapsedTime();
        while(opModeIsActive() && timer.seconds() < 3 );


        //get second wobble
        while(opModeIsActive() && Kryptos.driveTrain.moveToPosition(new Vector(52, 48), 0));
        while(opModeIsActive() && Kryptos.driveTrain.moveToPosition(new Vector(52, 15), 0));
        while(opModeIsActive() && Kryptos.driveTrain.moveToPosition(new Vector(48.4 , 15), 0));
        Kryptos.wobbleGoal.grabWobble();

        //deliver wobble
        targetZone = new Vector(targetZone.getX(), targetZone.getY() - 5);
        while(opModeIsActive() && Kryptos.driveTrain.moveToPosition(targetZone, 0));
        Kryptos.wobbleGoal.depositWobble();

        //park
        Vector current = Kryptos.odometry.getPos();
        while(opModeIsActive() && Kryptos.driveTrain.moveToPosition(new Vector(36, 24 * 3), 0));

        telemetry.addData("time taken", timeTaken.seconds());
        telemetry.update();
        while (opModeIsActive()) ;


//        while(opModeIsActive() && Kryptos.driveTrain.moveToPosition(targetZone,270);
//        Kryptos.wobbleGoal.depositWobble();
//
//        //shoot
//        while(opModeIsActive() && Kryptos.driveTrain.moveToPosition(new Vector(36,48),0);
//        Kryptos.shooter.shoot(false);
//
//        //get second wobble goal
//        while(opModeIsActive() && Kryptos.driveTrain.moveToPosition(new Vector(40,24),90);
//        Kryptos.wobbleGoal.grabWobble();
//
//        //deposit second wobble goal
//        while(opModeIsActive() && Kryptos.driveTrain.moveToPosition(targetZone,270);
//        Kryptos.wobbleGoal.depositWobble();
//
//        //park
//        Vector current = Kryptos.odometry.getPos();
//        while(opModeIsActive() && Kryptos.driveTrain.moveToPosition(new Vector(current.getX(), 24 * 3), 0);
//
//        telemetry.addData("time taken", timeTaken.seconds());
//        telemetry.update();
//        while(opModeIsActive()());
        Kryptos.saveOdoData();
    }


}
