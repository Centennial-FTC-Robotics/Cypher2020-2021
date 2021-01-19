package org.cypher;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.cypher.subsystems.DriveTrain;
import org.cypher.subsystems.IMU;
import org.cypher.subsystems.Intake;
import org.cypher.subsystems.Odometry;
import org.cypher.subsystems.OpenCVVision;
import org.cypher.subsystems.Shooter;
import org.cypher.subsystems.WobbleGoalGrabber;
import org.cypher.subsystems.WobbleGoalMover;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.List;

public class Robot {
    public static DriveTrain driveTrain = new DriveTrain();
    public static IMU imu  = new IMU();
    public static Intake intake = new Intake();
//    public static Odometry odometry = new Odometry();
    public static Shooter shooter = new Shooter();
//    public static WobbleGoalGrabber wobbleGoalGrabber = new WobbleGoalGrabber();
//    public static WobbleGoalMover wobbleGoalMover = new WobbleGoalMover();
    public static OpenCVVision openCV = new OpenCVVision();
    private static List<LynxModule> hubs;


    protected static OpMode opMode;

    private static Subsystem[] subsystems = { intake, imu, shooter, driveTrain/*, conveyor, odometry, wobbleGoalGrabber*/};

    public static void initWithVision(OpMode opMode) {
        openCV.initialize(opMode);
        init(opMode);
    }
    public static void init(OpMode opMode) {
        Robot.opMode = opMode;
        for(Subsystem subsystem : subsystems) {
                subsystem.initialize(opMode);
        }

        hubs = opMode.hardwareMap.getAll(LynxModule.class);
        for(LynxModule hub : hubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }
    }

    public static OpMode getOpMode() {
        return opMode;
    }
}
