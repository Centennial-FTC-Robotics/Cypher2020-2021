package org.cypher.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.cypher.Subsystem;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera2;
import org.openftc.easyopencv.OpenCvPipeline;

public class OpenCVVision implements Subsystem {
    OpenCvCamera camera;
    //TODO: do it luqman
    private OpMode opMode;

    private int rings = -1;

    @Override
    public void initialize(OpMode opMode) {
        this.opMode = opMode;
        int cameraMonitorViewId = opMode.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", opMode.hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createInternalCamera2(OpenCvInternalCamera2.CameraDirection.BACK, cameraMonitorViewId);

        camera.openCameraDevice();

        camera.setPipeline(new RingDetectorPipeline());
        
        camera.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);

        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(320,240, OpenCvCameraRotation.SIDEWAYS_LEFT);
            }
        });
    }
    


    public int getRings() {
        return rings;
    }

    //TODO: currently a copy of wizards, make own version later
    class RingDetectorPipeline extends OpenCvPipeline {

        final Scalar BLUE = new Scalar(0, 0, 255);
        final Scalar GREEN = new Scalar(0, 255, 0);

        final Point REGION1_TOPLEFT_ANCHOR_POINT = new Point(320/2, 130);

        static final int REGION_WIDTH = 50;
        static final int REGION_HEIGHT = 50;

        final int FOUR_RING_THRESHOLD = 150;
        final int ONE_RING_THRESHOLD = 135;

        Point region1_pointA = new Point(
                REGION1_TOPLEFT_ANCHOR_POINT.x,
                REGION1_TOPLEFT_ANCHOR_POINT.y);
        Point region1_pointB = new Point(
                REGION1_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
                REGION1_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);

        Mat region1_Cb;
        Mat YCrCb = new Mat();
        Mat Cb = new Mat();
        int avg1;

        void inputToCb(Mat input) {
            Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb);
            Core.extractChannel(YCrCb, Cb, 1);
        }

        @Override
        public void init(Mat firstFrame) {
            inputToCb(firstFrame);

            region1_Cb = Cb.submat(new Rect(region1_pointA, region1_pointB));
        }

        @Override
        public Mat processFrame(Mat input) {
            inputToCb(input);

            avg1 = (int) Core.mean(region1_Cb).val[0];

            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region1_pointA, // First point which defines the rectangle
                    region1_pointB, // Second point which defines the rectangle
                    BLUE, // The color the rectangle is drawn in
                    2); // Thickness of the rectangle lines


            rings = -1; // Record our analysis
            if(avg1 > FOUR_RING_THRESHOLD) {
                rings = 4;
            } else if (avg1 > ONE_RING_THRESHOLD) {
                rings = 1;
            } else{
                rings = 0;
            }

            opMode.telemetry.addData("value",avg1 );
            opMode.telemetry.addData("rings", rings);
            opMode.telemetry.update();


            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region1_pointA, // First point which defines the rectangle
                    region1_pointB, // Second point which defines the rectangle
                    GREEN, // The color the rectangle is drawn in
                    -1); // Negative thickness means solid fill

            return input;
        }
    }
}
