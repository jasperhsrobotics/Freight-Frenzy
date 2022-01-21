package org.firstinspires.ftc.teamcode.deprecated_new;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Disabled
@TeleOp(name = "TeleOp")
public class MainTeleOp extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();

    // Initializes drive motors to null
    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;
    private DcMotor carouselRight = null;
    private DcMotor carouselLeft = null;
    private DcMotor arm = null;
    private DcMotor intake = null;
    private Servo claw = null;
    private boolean carousel = false;
    private boolean braking = false;
    private double speed;
    private boolean spedUp;
    private boolean intakeOn;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        speed = 0.6;
        telemetry.addData("Status", "Initialized");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        frontLeft = hardwareMap.get(DcMotor.class, "fldrive");
        frontRight = hardwareMap.get(DcMotor.class, "frdrive");
        backLeft = hardwareMap.get(DcMotor.class, "bldrive");
        backRight = hardwareMap.get(DcMotor.class, "brdrive");
        intake = hardwareMap.get(DcMotor.class, "intake");
        arm = hardwareMap.get(DcMotor.class, "arm");
        // COMMENT OUT LATER
        carouselLeft = hardwareMap.get(DcMotor.class, "carouselL");
        carouselRight = hardwareMap.get(DcMotor.class, "carouselR");

        //arm = hardwareMap.get(DcMotor.class, "arm");
        //claw = hardwareMap.get(Servo.class, "claw");
        // 2 of the 4 motors are always reversed
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);

        intake.setDirection(DcMotor.Direction.REVERSE);
        arm.setDirection(DcMotor.Direction.FORWARD);

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");

        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        // FUNCTIONS

        // Carousel
        if (gamepad1.x && !carousel) {
            carousel = true;
            carouselRight.setPower(1);
            carouselLeft.setPower(1);
        } else if (gamepad1.x) {
            carousel = false;
            carouselLeft.setPower(0);
            carouselRight.setPower(0);
        }
        if (gamepad1.b && !carousel) {
            carousel = true;
            carouselRight.setPower(-1);
            carouselLeft.setPower(-1);
        } else if (gamepad1.b) {
            carousel = false;
            carouselLeft.setPower(0);
            carouselRight.setPower(0);
        }


        // INTAKE
        if (gamepad1.right_bumper && !intakeOn) {
            intakeOn = true;
            intake.setPower(0.9);
        } else if ((gamepad1.left_bumper || gamepad2.right_bumper) && intakeOn) {
            intakeOn = false;
            intake.setPower(-0.15);
        }
        if (gamepad1.left_bumper && !intakeOn) {
            intakeOn = true;
            intake.setPower(-0.7);
        } else if (gamepad1.left_trigger > 0 && !intakeOn) {
            intakeOn = true;
            intake.setPower(0.4);
        }

        if (gamepad1.a) {
            intakeOn = false;
            intake.setPower(0);
        }

        // EMERGENCY STOP
        if (gamepad1.right_stick_button) {
            intake.setPower(0);
            carouselLeft.setPower(0);
            carouselRight.setPower(0);
        }



        if (gamepad1.dpad_up) {
            arm.setDirection(DcMotor.Direction.FORWARD);
            arm.setPower(0.5);
        }
        else if (gamepad1.dpad_down) {
            arm.setDirection(DcMotor.Direction.REVERSE);
            arm.setPower(0.5);
        }
        else {
            arm.setDirection(DcMotor.Direction.FORWARD);
            arm.setPower(0.1);
        }

        if (gamepad1.y && !spedUp) {
            spedUp = true;
            speed = 1;
        } else if (gamepad1.y && spedUp) {
            spedUp = false;
            speed = 0.5;
        }

        // MOVEMENT

        double y = -gamepad1.left_stick_y; // Remember, this is reversed!
        double x = gamepad1.left_stick_x * 1.2; // Counteract imperfect strafing
        double rx = gamepad1.right_stick_x;

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        // Sets power
        frontLeft.setPower(frontLeftPower*speed);
        backLeft.setPower(backLeftPower*speed);
        frontRight.setPower(frontRightPower*speed);
        backRight.setPower(backRightPower*speed);

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Motors", "front left (%.2f), front right (%.2f), back left (%.2f), back right (%.2f)", frontLeftPower, frontRightPower, backLeftPower, backRightPower);
        telemetry.addData("Speed Modifier (%.1f)", speed);
        telemetry.addData("Intake", intake.getPower());
        //telemetry.addData("Carousel", carousel);
        //telemetry.addData("Arm", arm.getCurrentPosition());
        //telemetry.addData("Claw", claw.getPosition());
    }
}