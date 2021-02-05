package org.cypher;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.cypher.subsystems.DriveTrain;
import org.cypher.subsystems.IMU;
import org.cypher.subsystems.Intake;
import org.cypher.subsystems.Odometry;
import org.cypher.subsystems.OpenCVVision;
import org.cypher.subsystems.Shooter;
import org.cypher.subsystems.WobbleGoalGrabber;
import org.cypher.util.Vector;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.File;
import java.util.List;

public class Kryptos {
    public static DriveTrain driveTrain = new DriveTrain();
    public static IMU imu = new IMU();
    public static Intake intake = new Intake();
    public static Odometry odometry = new Odometry();
    public static Shooter shooter = new Shooter();
    public static WobbleGoalGrabber wobbleGoalGrabber = new WobbleGoalGrabber();
    public static OpenCVVision openCV = new OpenCVVision();
    private static List<LynxModule> hubs;

    private static File odoData = AppUtil.getInstance().getSettingsFile("odoData.txt");


    protected static OpMode opMode;

    private static Subsystem[] subsystems = {intake, imu, shooter, driveTrain, wobbleGoalGrabber, odometry};

    public static void initWithVision(OpMode opMode) {
        openCV.initialize(opMode);
        init(opMode);
    }

    public static void init(OpMode opMode) {
        Kryptos.opMode = opMode;
        for (Subsystem subsystem : subsystems) {
            subsystem.initialize(opMode);
        }

        hubs = opMode.hardwareMap.getAll(LynxModule.class);
        setCacheMode(LynxModule.BulkCachingMode.AUTO);
    }

    public static OpMode getOpMode() {
        return opMode;
    }

    public static void setCacheMode(LynxModule.BulkCachingMode mode) {
        for (LynxModule hub : hubs) {
            hub.setBulkCachingMode(mode);
        }
    }

    public static void saveOdoData() {
        String data = "";
        Vector pos = odometry.getPos();
        data += pos.getComponent(0) + "," + pos.getComponent(1) + "," + Math.toDegrees(odometry.getHeading());
        ReadWriteFile.writeFile(odoData, data);
    }

    public static void readOdoData() {
        String rawData = ReadWriteFile.readFile(odoData);
        String[] rawDataArr = rawData.split(",");
        try {
            double x = Double.parseDouble(rawDataArr[0]);
            double y = Double.parseDouble(rawDataArr[1]);
            float heading = Float.parseFloat(rawDataArr[2]);
            odometry.setStartPos(x, y, heading);
            if (heading > 180) {
                float diff = heading - 180;
                diff = -180 + diff;
                imu.setInitAngle(diff);
            } else {
                imu.setInitAngle(heading);
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            odometry.setStartPos(0, 0, 0);
            imu.setInitAngle(0);
        }

    }

    public static void clearCache() {
        for (LynxModule hub : hubs) {
            hub.clearBulkCache();
        }
    }
}
