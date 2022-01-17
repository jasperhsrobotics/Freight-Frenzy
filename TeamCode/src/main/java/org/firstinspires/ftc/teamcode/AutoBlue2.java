package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Blue Right(Carousel+sudo SU)")
public class AutoBlue2 extends LinearOpMode {
    private final ElapsedTime runtime = new ElapsedTime();

    // Initializes drive motors to null
    private static DcMotor frontLeft = null;
    private static DcMotor frontRight = null;
    private static DcMotor backLeft = null;
    private static DcMotor backRight = null;
    private static DcMotor carouselMotorLeft = null;
    private static DcMotor carouselMotorRight = null;
    private static DcMotor arm = null;
    private static DcMotor intake = null;
//    private static ColorSensor color = null;

    DcMotor[] wheels = new DcMotor[4];

    /**
     * Code to run
     */
    @Override
    public void runOpMode() {
        frontLeft = hardwareMap.get(DcMotor.class, "fldrive");
        frontRight = hardwareMap.get(DcMotor.class, "frdrive");
        backLeft = hardwareMap.get(DcMotor.class, "bldrive");
        backRight = hardwareMap.get(DcMotor.class, "brdrive");
        carouselMotorLeft = hardwareMap.get(DcMotor.class, "carouselL");
        carouselMotorRight = hardwareMap.get(DcMotor.class, "carouselR");
        intake = hardwareMap.get(DcMotor.class, "intake");
        arm = hardwareMap.get(DcMotor.class, "arm");
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        carouselMotorRight.setDirection(DcMotor.Direction.REVERSE);
        intake.setDirection(DcMotor.Direction.REVERSE);
        arm.setDirection(DcMotor.Direction.FORWARD);

        wheels[0] = frontLeft;
        wheels[1] = frontRight;
        wheels[2] = backLeft;
        wheels[3] = backRight;

        for (DcMotor wheel : wheels){
            wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            wheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            wheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        telemetry.addData("Path0",  "Starting at %7d :%7d :%7d :%7d", frontLeft.getCurrentPosition(), frontRight.getCurrentPosition(), backLeft.getCurrentPosition(), backRight.getCurrentPosition());
        telemetry.update();

        waitForStart();

        moveForward(50, 0.1);
        strafeRight(150, 0.7);
        strafeRight(150, 0.3);
        moveBackward(20, 0.1);

        frontLeft.setPower(-0.1);
        frontRight.setPower(-0.1);
        backLeft.setPower(-0.1);
        backRight.setPower(-0.1);
        spinCarousel(4500, -1);
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);


        moveForward(120, 0.1);
    }

    public void spinCarousel(int time, int power)
    {
        carouselMotorRight.setPower(power);
        carouselMotorLeft.setPower(power);
        sleep(time);
        carouselMotorRight.setPower(0);
        carouselMotorLeft.setPower(0);
    }

    public void pivotLeft(int ticks, double speed) {
        //int target = (int)(Math.round(inches * COUNTS_PER_INCH));
        int target = ticks;

        wheels[0].setTargetPosition(wheels[0].getCurrentPosition() + target);
        wheels[1].setTargetPosition(wheels[1].getCurrentPosition() - target);
        wheels[2].setTargetPosition(wheels[2].getCurrentPosition() + target);
        wheels[3].setTargetPosition(wheels[3].getCurrentPosition() - target);

        for (int i = 0; i < 4; i++) {

            // Tells the motor to drive until they reach the target position
            wheels[i].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
//        runtime.reset();

        frontLeft.setPower(speed);
        backLeft.setPower(speed);
        backRight.setPower(speed);
        frontRight.setPower(speed);

        while (opModeIsActive() && frontLeft.isBusy() && backLeft.isBusy() && backRight.isBusy() && frontRight.isBusy()) {
//            telemetry.addData("Path1",  "Running to %7d :%7d :%7d :%7d", newWheelTarget[0],  newWheelTarget[1], newWheelTarget[2], newWheelTarget[3]);
            telemetry.addData("Path2",  "Running at %7d :%7d :%7d :%7d", frontLeft.getCurrentPosition(), frontRight.getCurrentPosition(), backRight.getCurrentPosition(), backLeft.getCurrentPosition());
            telemetry.update();
        }

        for (DcMotor wheel : wheels){
            // Stops motors after motors have reached target position
            wheel.setPower(0);

            // Resets encoders
            wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            wheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    public void pivotRight(int ticks, double speed) {
        //int target = (int)(Math.round(inches * COUNTS_PER_INCH));
        int target = ticks;

        wheels[0].setTargetPosition(wheels[0].getCurrentPosition() - target);
        wheels[1].setTargetPosition(wheels[1].getCurrentPosition() + target);
        wheels[2].setTargetPosition(wheels[2].getCurrentPosition() - target);
        wheels[3].setTargetPosition(wheels[3].getCurrentPosition() + target);

        for (int i = 0; i < 4; i++) {

            // Tells the motor to drive until they reach the target position
            wheels[i].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
//        runtime.reset();

        frontLeft.setPower(speed);
        backLeft.setPower(speed);
        backRight.setPower(speed);
        frontRight.setPower(speed);

        while (opModeIsActive() && frontLeft.isBusy() && backLeft.isBusy() && backRight.isBusy() && frontRight.isBusy()) {
//            telemetry.addData("Path1",  "Running to %7d :%7d :%7d :%7d", newWheelTarget[0],  newWheelTarget[1], newWheelTarget[2], newWheelTarget[3]);
            telemetry.addData("Path2",  "Running at %7d :%7d :%7d :%7d", frontLeft.getCurrentPosition(), frontRight.getCurrentPosition(), backRight.getCurrentPosition(), backLeft.getCurrentPosition());
            telemetry.update();
        }

        for (DcMotor wheel : wheels){
            // Stops motors after motors have reached target position
            wheel.setPower(0);

            // Resets encoders
            wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            wheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    public void moveForward(double ticks, double speed) {
        int target = (int)(Math.round(ticks));

        for (int i = 0; i < 4; i++) {
            // Sets the target position for the motors
            wheels[i].setTargetPosition(wheels[i].getCurrentPosition() + target);

            // Tells the motor to drive until they reach the target position
            wheels[i].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
//        runtime.reset();

        frontLeft.setPower(speed);
        backLeft.setPower(speed);
        backRight.setPower(speed);
        frontRight.setPower(speed);

        while (opModeIsActive() && frontLeft.isBusy() && backLeft.isBusy() && backRight.isBusy() && frontRight.isBusy()) {
//            telemetry.addData("Path1",  "Running to %7d :%7d :%7d :%7d", newWheelTarget[0],  newWheelTarget[1], newWheelTarget[2], newWheelTarget[3]);
            telemetry.addData("Path2",  "Running at %7d :%7d :%7d :%7d", frontLeft.getCurrentPosition(), frontRight.getCurrentPosition(), backRight.getCurrentPosition(), backLeft.getCurrentPosition());
            telemetry.update();
        }

        for (DcMotor wheel : wheels){
            // Stops motors after motors have reached target position
            wheel.setPower(0);

            // Resets encoders
            wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            wheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    public void moveBackward(double ticks, double speed) {
        int target = (int)(Math.round(ticks));

        for (int i = 0; i < 4; i++) {
            // Sets the target position for the motors
            wheels[i].setTargetPosition(wheels[i].getCurrentPosition() - target);

            // Tells the motor to drive until they reach the target position
            wheels[i].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
//        runtime.reset();

        frontLeft.setPower(speed);
        backLeft.setPower(speed);
        backRight.setPower(speed);
        frontRight.setPower(speed);

        while (opModeIsActive() && frontLeft.isBusy() && backLeft.isBusy() && backRight.isBusy() && frontRight.isBusy()) {
//            telemetry.addData("Path1",  "Running to %7d :%7d :%7d :%7d", newWheelTarget[0],  newWheelTarget[1], newWheelTarget[2], newWheelTarget[3]);
            telemetry.addData("Path2",  "Running at %7d :%7d :%7d :%7d", frontLeft.getCurrentPosition(), frontRight.getCurrentPosition(), backRight.getCurrentPosition(), backLeft.getCurrentPosition());
            telemetry.update();
        }

        for (DcMotor wheel : wheels){
            // Stops motors after motors have reached target position
            wheel.setPower(0);

            // Resets encoders
            wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            wheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    public void strafeLeft(double ticks, double speed) {
        int target = (int)(Math.round(ticks));

        wheels[0].setTargetPosition(wheels[0].getCurrentPosition() - target);
        wheels[1].setTargetPosition(wheels[1].getCurrentPosition() + target);
        wheels[2].setTargetPosition(wheels[2].getCurrentPosition() + target);
        wheels[3].setTargetPosition(wheels[3].getCurrentPosition() - target);

        for (int i = 0; i < 4; i++) {
            // Sets the target position for the motors
//            wheels[i].setTargetPosition(wheels[i].getCurrentPosition() - target);

            // Tells the motor to drive until they reach the target position
            wheels[i].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
//        runtime.reset();

        frontLeft.setPower(speed);
        backLeft.setPower(speed);
        backRight.setPower(speed);
        frontRight.setPower(speed);

        while (opModeIsActive() && frontLeft.isBusy() && backLeft.isBusy() && backRight.isBusy() && frontRight.isBusy()) {
//            telemetry.addData("Path1",  "Running to %7d :%7d :%7d :%7d", newWheelTarget[0],  newWheelTarget[1], newWheelTarget[2], newWheelTarget[3]);
            telemetry.addData("Path2",  "Running at %7d :%7d :%7d :%7d", frontLeft.getCurrentPosition(), frontRight.getCurrentPosition(), backRight.getCurrentPosition(), backLeft.getCurrentPosition());
            telemetry.update();
        }

        for (DcMotor wheel : wheels){
            // Stops motors after motors have reached target position
            wheel.setPower(0);

            // Resets encoders
            wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            wheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    public void strafeRight(double ticks, double speed) {
        int target = (int)(Math.round(ticks));

        wheels[0].setTargetPosition(wheels[0].getCurrentPosition() + target);
        wheels[1].setTargetPosition(wheels[1].getCurrentPosition() - target);
        wheels[2].setTargetPosition(wheels[2].getCurrentPosition() - target);
        wheels[3].setTargetPosition(wheels[3].getCurrentPosition() + target);

        for (int i = 0; i < 4; i++) {
            // Sets the target position for the motors
//            wheels[i].setTargetPosition(wheels[i].getCurrentPosition() - target);

            // Tells the motor to drive until they reach the target position
            wheels[i].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
//        runtime.reset();

        frontLeft.setPower(speed);
        backLeft.setPower(speed);
        backRight.setPower(speed);
        frontRight.setPower(speed);

        while (opModeIsActive() && frontLeft.isBusy() && backLeft.isBusy() && backRight.isBusy() && frontRight.isBusy()) {
//            telemetry.addData("Path1",  "Running to %7d :%7d :%7d :%7d", newWheelTarget[0],  newWheelTarget[1], newWheelTarget[2], newWheelTarget[3]);
            telemetry.addData("Path2",  "Running at %7d :%7d :%7d :%7d", frontLeft.getCurrentPosition(), frontRight.getCurrentPosition(), backRight.getCurrentPosition(), backLeft.getCurrentPosition());
            telemetry.update();
        }

        for (DcMotor wheel : wheels){
            // Stops motors after motors have reached target position
            wheel.setPower(0);

            // Resets encoders
            wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            wheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

//    public void encoderDrive(double speed, double front_left, double front_right, double back_left, double back_right, double timeoutS) {
//        int []newWheelTarget = new int[4];
//        // Ensure that the opmode is still active
//        if (opModeIsActive()) {
//            // Determine new target position, and pass to motor controller
//            newWheelTarget[0] = wheels[0].getCurrentPosition() + (int)(front_left * COUNTS_PER_INCH);
//            newWheelTarget[1] = wheels[1].getCurrentPosition() + (int)(front_right * COUNTS_PER_INCH);
//            newWheelTarget[2] = wheels[2].getCurrentPosition() + (int)(back_left * COUNTS_PER_INCH);
//            newWheelTarget[3] = wheels[3].getCurrentPosition() + (int)(back_right * COUNTS_PER_INCH);
//
//            for (int i = 0; i < 4; i++) {
//                // Sets the target position for the motors
//                wheels[i].setTargetPosition(newWheelTarget[i]);
//
//                // Tells the motor to drive until they reach the target position
//                wheels[i].setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            }
//            // reset the timeout time and start motion.
//            runtime.reset();
//
//            frontLeft.setPower(Math.abs(speed));
//            backLeft.setPower(Math.abs(speed));
//            backRight.setPower(Math.abs(speed));
//            frontRight.setPower(Math.abs(speed));
//
//            // keep looping while we are still active, and there is time left, and both motors are running.
//            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
//            // its target position, the motion will stop.  This is "safer" in the event that the robot will
//            // always end the motion as soon as possible.
//            // However, if you require that BOTH motors have finished their moves before the robot continues
//            // onto the next step, use (isBusy() || isBusy()) in the loop test.
//            while (opModeIsActive() && (runtime.seconds() < timeoutS) && frontLeft.isBusy() && backLeft.isBusy() && backRight.isBusy() && frontRight.isBusy()) {
//                telemetry.addData("Path1",  "Running to %7d :%7d :%7d :%7d", newWheelTarget[0],  newWheelTarget[1], newWheelTarget[2], newWheelTarget[3]);
//                telemetry.addData("Path2",  "Running at %7d :%7d :%7d :%7d", frontLeft.getCurrentPosition(), frontRight.getCurrentPosition(), backRight.getCurrentPosition(), backLeft.getCurrentPosition());
//                telemetry.update();
//            }
//
//            for (DcMotor wheel : wheels){
//                // Stops motors after motors have reached target position
//                wheel.setPower(0);
//
//                // Resets encoders
//                wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//                wheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            }
//
//            //  sleep(250);   // optional pause after each move
//        }
//    }
}