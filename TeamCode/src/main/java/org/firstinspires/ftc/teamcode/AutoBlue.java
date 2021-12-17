package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;

@Autonomous(name = "Auto Blue")
public class AutoBlue extends LinearOpMode {
    private final ElapsedTime runtime = new ElapsedTime();

    // Initializes drive motors to null
    private static DcMotor frontLeft = null;
    private static DcMotor frontRight = null;
    private static DcMotor backLeft = null;
    private static DcMotor backRight = null;
    private static DcMotor carouselLeft = null;
    private static DcMotor carouselRight = null;

    // measure
    static final double COUNTS_PER_MOTOR_REV = 1120;    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 1;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * Math.PI);
    public static double TICK_ANGLE = 15;
    static final double DRIVE_SPEED = 0.6;
    static final double TURN_SPEED = 0.5;

    static Location duckLocation = null;

    DcMotor[] wheels = new DcMotor[4];

    OpenCvWebcam webcam;

    Mat mat = new Mat();

    public enum Location {
        LEFT,
        RIGHT,
        MIDDLE,
        NOT_FOUND
    }

    // 1280, 800
    static final Rect LEFT_ROI = new Rect(
            new Point(0, 200),
            new Point(400, 600));

    static final Rect MIDDLE_ROI = new Rect(
            new Point(450, 200),
            new Point(850, 600));

    static final Rect RIGHT_ROI = new Rect(
            new Point(900, 200),
            new Point(1280, 600));
    static double PERCENT_COLOR_THRESHOLD = 0.4;

    /**
     * Code to run
     */
    @Override
    public void runOpMode() {
        frontLeft = hardwareMap.get(DcMotor.class, "fldrive");
        frontRight = hardwareMap.get(DcMotor.class, "frdrive");
        backLeft = hardwareMap.get(DcMotor.class, "bldrive");
        backRight = hardwareMap.get(DcMotor.class, "brdrive");
        carouselLeft = hardwareMap.get(DcMotor.class, "carouselL");
        carouselRight = hardwareMap.get(DcMotor.class, "carouselR");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        carouselLeft.setDirection(DcMotor.Direction.REVERSE);
        carouselRight.setDirection(DcMotor.Direction.FORWARD);

        wheels[0] = frontLeft;
        wheels[1] = frontRight;
        wheels[2] = backLeft;
        wheels[3] = backRight;

        // opencv

//        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
//        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
//
//        webcam.setPipeline(new AutoRed.SamplePipeline());
//        webcam.setMillisecondsPermissionTimeout(2500);
//
//        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
//        {
//            @Override
//            public void onOpened()
//            {
//                webcam.startStreaming(1280, 800, OpenCvCameraRotation.UPRIGHT);
//            }
//            @Override
//            public void onError(int errorCode)
//            {
//                // lmao wtf is this error message
//                telemetry.addData("Status", "bad bad so bad how could u mess up this catastrophically");
//            }
//        });
//
//
//
        waitForStart();
//
//
//        // IDK if java passes by reference or by value sooooo
//        Location loc;
//        if (duckLocation == Location.LEFT)
//        {
//            loc = Location.LEFT;
//        }
//        else if (duckLocation == Location.MIDDLE)
//        {
//            loc = Location.MIDDLE;
//        }
//        else
//        {
//            loc = Location.RIGHT;
//        }
//
//        // see if this works, if it works then use the duckLocation variable to put preloaded box on correct level
//        telemetry.addData("location", loc);

        // move code
        strafeRight(900, 0.6);
        strafeRight(600, 0.4);
        moveForward(150, 0.4);
        strafeRight(300, 0.4);
        carouselRight.setPower(0.6);
        sleep(4000);
        carouselRight.setPower(0);

//        if (loc == Location.LEFT)
//            strafeRight(900, 0.6);

    }

    public void moveForward(int milliseconds, double speed) {

        frontLeft.setPower(speed);
        backLeft.setPower(speed);
        backRight.setPower(speed);
        frontRight.setPower(speed);

        sleep(milliseconds);

        for (DcMotor wheel : wheels) {
            // Stops motors after motors have reached target position
            wheel.setPower(0);
        }
    }

    public void strafeRight(int milliseconds, double speed) {
        backLeft.setPower(speed * -1 * 0.8);
        frontLeft.setPower(speed);
        frontRight.setPower(speed * -1 * 0.8);
        backRight.setPower(speed * 0.7); // got rid of the parabola-like movement :)

        sleep(milliseconds);

        for (DcMotor wheel : wheels) {
            // Stops motors after motors have reached target position
            wheel.setPower(0);
        }
    }

    public void strafeLeft(int milliseconds, double speed) {
        frontLeft.setPower(speed * -1 * 0.8);
        backLeft.setPower(speed);
        backRight.setPower(speed * -1 * 0.8);
        frontRight.setPower(speed * 0.7); // got rid of the parabola-like movement :)

        sleep(milliseconds);

        for (DcMotor wheel : wheels) {
            // Stops motors after motors have reached target position
            wheel.setPower(0);
        }
    }
//
//    class SamplePipeline extends OpenCvPipeline {
//        boolean viewportPaused;
//        AutoRed.Location location;
//        @Override
//        public Mat processFrame(Mat input) {
//            Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);
//            Scalar lowHSV = new Scalar(23, 50, 70);
//            Scalar highHSV = new Scalar(32, 255, 255);
//
//            Core.inRange(mat, lowHSV, highHSV, mat);
//
//            Mat left = mat.submat(LEFT_ROI);
//            Mat right = mat.submat(RIGHT_ROI);
//            Mat middle = mat.submat(MIDDLE_ROI);
//
//            double leftValue = Core.sumElems(left).val[0] / LEFT_ROI.area() / 255;
//            double rightValue = Core.sumElems(right).val[0] / RIGHT_ROI.area() / 255;
//            double middleValue = Core.sumElems(middle).val[0] / MIDDLE_ROI.area() / 255;
//
//            left.release();
//            right.release();
//            middle.release();
//
//            telemetry.addData("Left raw value", (int) Core.sumElems(left).val[0]);
//            telemetry.addData("Right raw value", (int) Core.sumElems(right).val[0]);
//            telemetry.addData("Left percentage", Math.round(leftValue * 100) + "%");
//            telemetry.addData("Right percentage", Math.round(rightValue * 100) + "%");
//            double rightPercentage = Math.round(rightValue * 100);
//            double leftPercentage = Math.round(leftValue * 100);
//            double middlePercentage = Math.round(middleValue * 100);
//            boolean stoneLeft = leftValue > PERCENT_COLOR_THRESHOLD;
//            boolean stoneRight = rightValue > PERCENT_COLOR_THRESHOLD;
//
//
//            if (rightPercentage > leftPercentage && rightPercentage > middlePercentage) {
//                location = Location.RIGHT;
//                duckLocation = Location.RIGHT;
//                telemetry.addData("Skystone Location", "right");
//            }
//            else if (leftPercentage > rightPercentage && leftPercentage > middlePercentage) {
//                location = Location.LEFT;
//                duckLocation = Location.LEFT;
//                telemetry.addData("Skystone Location", "left");
//            }
//            else {
//                location = Location.MIDDLE;
//                duckLocation = Location.MIDDLE;
//                telemetry.addData("Skystone Location", "center");
//            }
//            telemetry.update();
//
//            Imgproc.cvtColor(mat, mat, Imgproc.COLOR_GRAY2RGB);
//
//            Scalar colorStone = new Scalar(255, 0, 0);
//            Scalar colorSkystone = new Scalar(0, 255, 0);
//
//            Imgproc.rectangle(mat, LEFT_ROI, location == Location.LEFT? colorSkystone:colorStone);
//            Imgproc.rectangle(mat, RIGHT_ROI, location == Location.RIGHT? colorSkystone:colorStone);
//            Imgproc.rectangle(mat, MIDDLE_ROI, location == Location.MIDDLE? colorSkystone:colorStone);
//            return mat;
//        }

//        @Override
//        public void onViewportTapped(){
//
//            viewportPaused = !viewportPaused;
//
//            if(viewportPaused){
//                webcam.pauseViewport();
//            } else {
//                webcam.resumeViewport();
//            }
//        }
//    }
}