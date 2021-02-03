package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.cypher.Kryptos;
import org.cypher.subsystems.Odometry;
import org.cypher.util.Vector;


@Autonomous(name="red side")
public class RedSideAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Kryptos.initWithVision(this);
        Kryptos.imu.setInitAngle(-90);
        Kryptos.odometry.setStartPos(0,16,270);

        waitForStart();
        Vector targetZone;
        Vector additional = new Vector(0,0);

        double ringCount = Kryptos.openCV.getRings();
        if(ringCount == 0)
            targetZone = new Vector(0,22.75 * 3);
        else if(ringCount == 1) {
            targetZone = new Vector(0, 22.75 * 4);
            additional = new Vector(22.75,22.75 * 4);
        }
        else
            targetZone = new Vector(0,22.75);

        //move wobble goal
        while(Kryptos.driveTrain.moveToPosition(targetZone,270));
        while (Kryptos.driveTrain.moveToPosition(additional,270));

        Kryptos.wobbleGoalGrabber.flipHinge();
        Kryptos.wobbleGoalGrabber.release();

        //get other wobble goal

        Vector pos = Kryptos.odometry.getPos();
        while (Kryptos.driveTrain.moveToPosition(new Vector(22.75, pos.getComponent(1)),90));
        while (Kryptos.driveTrain.moveToPosition(new Vector(22.75 + 10, 22.75),90));

        Kryptos.wobbleGoalGrabber.grab();
        ElapsedTime time = new ElapsedTime();
        while (time.seconds() < .5 && opModeIsActive());

        Kryptos.driveTrain.moveToPosition(targetZone,270);

        Kryptos.driveTrain.moveToPosition(new Vector(22.75, 22.75 * 2), 0);

        Kryptos.shooter.shoot(false);

        Kryptos.driveTrain.moveToPosition(new Vector(22.75,61),0);

    }
}
