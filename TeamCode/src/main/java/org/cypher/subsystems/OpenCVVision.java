package org.cypher.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.cypher.Subsystem;
import org.opencv.core.Mat;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvInternalCamera2;
import org.openftc.easyopencv.OpenCvPipeline;

public class OpenCVVision implements Subsystem {
    OpenCvCamera camera;

    private int rings = -1;
    @Override
    public void initialize(OpMode opMode) {
        int cameraMonitorViewId = opMode.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", opMode.hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createInternalCamera2(OpenCvInternalCamera2.CameraDirection.BACK, cameraMonitorViewId);

        camera.openCameraDevice();

        camera.setPipeline(new RingDetectorPipeline());
    }

    public int getRings() {
        return rings;
    }

    class RingDetectorPipeline extends OpenCvPipeline {

        @Override
        public Mat processFrame(Mat input) {

            //do stuffs


            return input;
        }
    }
}
