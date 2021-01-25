package org.cypher;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.cypher.subsystems.DriveTrain;
import org.cypher.subsystems.IMU;
import org.cypher.subsystems.Intake;
import org.cypher.subsystems.OpenCVVision;
import org.cypher.subsystems.Shooter;
import org.cypher.subsystems.WobbleGoalGrabber;

import java.util.List;

public class Robot {
    public static DriveTrain driveTrain = new DriveTrain();
    public static IMU imu  = new IMU();
    public static Intake intake = new Intake();
//    public static Odometry odometry = new Odometry();
    public static Shooter shooter = new Shooter();
    public static WobbleGoalGrabber wobbleGoalGrabber = new WobbleGoalGrabber();
//    public static WobbleGoalMover wobbleGoalMover = new WobbleGoalMover();
    public static OpenCVVision openCV = new OpenCVVision();
    private static List<LynxModule> hubs;


    protected static OpMode opMode;

    private static Subsystem[] subsystems = { intake, imu, shooter, driveTrain,wobbleGoalGrabber/*, conveyor, odometry,*/};

    public static void initWithVision(OpMode opMode) {
        openCV.initialize(opMode);
        init(opMode);
        Robot.wobbleGoalGrabber.grab();
    }

    public static void init(OpMode opMode) {
        Robot.opMode = opMode;
        for(Subsystem subsystem : subsystems) {
                subsystem.initialize(opMode);
        }

        hubs = opMode.hardwareMap.getAll(LynxModule.class);
        setCacheMode(LynxModule.BulkCachingMode.AUTO);
    }

    public static OpMode getOpMode() {
        return opMode;
    }

    public static void setCacheMode(LynxModule.BulkCachingMode mode) {
        for(LynxModule hub : hubs) {
            hub.setBulkCachingMode(mode);
        }
    }

    public static void clearCache() {
        for(LynxModule hub : hubs) {
            hub.clearBulkCache();
        }
    }
}
