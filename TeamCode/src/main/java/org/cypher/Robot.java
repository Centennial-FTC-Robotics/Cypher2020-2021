package org.cypher;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.cypher.subsystems.Conveyor;
import org.cypher.subsystems.DriveTrain;
import org.cypher.subsystems.IMU;
import org.cypher.subsystems.Intake;
import org.cypher.subsystems.Odometry;
import org.cypher.subsystems.Shooter;
import org.cypher.subsystems.WobbleGoal;

public class Robot {
    public static DriveTrain driveTrain = new DriveTrain();
    public static Conveyor conveyor = new Conveyor();
    public static IMU imu  = new IMU();
    public static Intake intake = new Intake();
    public static Odometry odometry = new Odometry();
    public static Shooter shooter = new Shooter();
    public static WobbleGoal wobbleGoal = new WobbleGoal();

    protected static OpMode opMode;

    private static Subsystem[] subsystems = {driveTrain, conveyor, imu, intake, odometry, shooter, wobbleGoal};

    public static void init(OpMode opMode) {
        Robot.opMode = opMode;
        for(Subsystem subsystem : subsystems) {
            subsystem.initialize(opMode);
        }
    }

}
