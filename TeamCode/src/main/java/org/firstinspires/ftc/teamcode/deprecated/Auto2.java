package org.firstinspires.ftc.teamcode.deprecated;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Disabled
@Autonomous(name = "Auto2")
public class Auto2 extends LinearOpMode {
    private final ElapsedTime runtime = new ElapsedTime();

    // Initializes drive motors to null
    private static DcMotor frontLeft = null;
    private static DcMotor frontRight = null;
    private static DcMotor backLeft = null;
    private static DcMotor backRight = null;
    private static DcMotor carouselMotor = null;
    private static ColorSensor color = null;
    private static DcMotor arm = null;
    private static Servo claw = null;
    // measure
    private static final double COUNTS_PER_REV = 1120.0;
    private static final double WHEEL_DIAMETER = 4.0;
    private static final double FORWARD_COUNTS_PER_INCH = (COUNTS_PER_REV) / (WHEEL_DIAMETER * 3.1415);
    private static final double SIDE_COUNTS_PER_INCH = FORWARD_COUNTS_PER_INCH * 1.5;
    private static final double PIVOT_COUNTS_PER_DEGREE = FORWARD_COUNTS_PER_INCH / 4.5;
    private static final double SPEED = 0.5;
    private static final double CAROUSEL_SPEED = 1;
    private static final double TIMEOUT = 30; // seconds, used in printing in move()

    /**
     * Code to run
     */
    @Override
    public void runOpMode() {
        frontLeft = hardwareMap.get(DcMotor.class, "fldrive");
        frontRight = hardwareMap.get(DcMotor.class, "frdrive");
        backLeft = hardwareMap.get(DcMotor.class, "bldrive");
        backRight = hardwareMap.get(DcMotor.class, "brdrive");
        carouselMotor = hardwareMap.get(DcMotor.class, "carousel");
        arm = hardwareMap.get(DcMotor.class, "arm");
        claw = hardwareMap.get(Servo.class, "claw");

//        color = hardwareMap.get(ColorSensor.class, "colorSensor");
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        carouselMotor.setDirection(DcMotor.Direction.FORWARD);
        arm.setDirection(DcMotor.Direction.FORWARD);
        claw.setDirection(Servo.Direction.REVERSE);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        waitForStart();
        start();

        //Parks from third square to warehouse

        moveForward(500);
        while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
            telemetry.addData("stat", "running");
        }
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        setSpeed(0);

        pivotRight(500);
        while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
            telemetry.addData("stat", "running");
        }
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

    public void setSpeed(double speed) {
        frontLeft.setPower(speed);
        frontRight.setPower(speed);
        backLeft.setPower(speed);
        backRight.setPower(speed);
    }

    public void spinCarousel(int milliseconds) {
        carouselMotor.setPower(CAROUSEL_SPEED);
        sleep(milliseconds);
        carouselMotor.setPower(0);
    }

    public void moveForward(int ticks){
        frontLeft.setTargetPosition(ticks);
        frontLeft.setPower(1);
        frontRight.setTargetPosition(ticks);
        frontRight.setPower(1);
        backLeft.setTargetPosition(ticks);
        backLeft.setPower(1);
        backRight.setTargetPosition(ticks);
        backRight.setPower(1);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void moveBack(int ticks){
        frontLeft.setTargetPosition(ticks);
        frontLeft.setPower(-1);
        frontRight.setTargetPosition(ticks);
        frontRight.setPower(-1);
        backLeft.setTargetPosition(ticks);
        backLeft.setPower(-1);
        backRight.setTargetPosition(ticks);
        backRight.setPower(-1);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void moveLeft(int ticks){
        frontLeft.setTargetPosition(ticks*-1);
        frontLeft.setPower(-1);
        frontRight.setTargetPosition(ticks);
        frontRight.setPower(1);
        backLeft.setTargetPosition(ticks);
        backLeft.setPower(1);
        backRight.setTargetPosition(ticks*-1);
        backRight.setPower(-1);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void moveRight(int ticks){
        frontLeft.setTargetPosition(ticks);
        frontLeft.setPower(1);
        frontRight.setTargetPosition(ticks*-1);
        frontRight.setPower(-1);
        backLeft.setTargetPosition(ticks*-1);
        backLeft.setPower(-1);
        backRight.setTargetPosition(ticks);
        backRight.setPower(1);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void pivotLeft(int ticks){
        frontLeft.setTargetPosition(ticks);
        frontLeft.setPower(1);
        frontRight.setTargetPosition(ticks);
        frontRight.setPower(-1);
        backLeft.setTargetPosition(ticks);
        backLeft.setPower(1);
        backRight.setTargetPosition(ticks);
        backRight.setPower(-1);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void pivotRight(int ticks){
        frontLeft.setTargetPosition(-ticks);

        //frontRight.setTargetPosition(-ticks);

        backLeft.setTargetPosition(ticks);

        //backRight.setTargetPosition(-ticks);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setPower(1);
        frontRight.setPower(0);
        backLeft.setPower(1);
        backRight.setPower(0);
    }
}