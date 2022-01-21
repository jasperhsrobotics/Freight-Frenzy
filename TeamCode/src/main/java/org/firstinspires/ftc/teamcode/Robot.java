package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Robot {

    public enum Direction {
        FORWARD, LEFT, RIGHT, REVERSE,
        PIVOT_RIGHT, PIVOT_LEFT
    }

    public DcMotor frontLeft = null;
    public DcMotor frontRight = null;
    public DcMotor backLeft = null;
    public DcMotor backRight = null;

    public DcMotor carouselLeft = null;
    public DcMotor carouselRight = null;

    public DcMotor arm = null;
    public DcMotor intake = null;
    ElapsedTime time = new ElapsedTime();

    public void initializeBot(HardwareMap hardwareMap, ElapsedTime time) {
        this.time = time;
        initializeBot(hardwareMap);
    }

    public void initializeBot(HardwareMap hardwareMap) {
        this.frontLeft = hardwareMap.get(DcMotor.class, "fldrive");
        this.frontRight = hardwareMap.get(DcMotor.class, "frdrive");
        this.backLeft = hardwareMap.get(DcMotor.class, "bldrive");
        this.backRight = hardwareMap.get(DcMotor.class, "brdrive");
        this.carouselLeft = hardwareMap.get(DcMotor.class, "carouselL");
        this.carouselRight = hardwareMap.get(DcMotor.class, "carouselR");
        this.intake = hardwareMap.get(DcMotor.class, "intake");
        this.arm = hardwareMap.get(DcMotor.class, "arm");

        this.frontLeft.setDirection(DcMotor.Direction.REVERSE);
        this.frontRight.setDirection(DcMotor.Direction.FORWARD);
        this.backLeft.setDirection(DcMotor.Direction.REVERSE);
        this.backRight.setDirection(DcMotor.Direction.FORWARD);

        this.carouselLeft.setDirection(DcMotor.Direction.FORWARD);
        this.carouselRight.setDirection(DcMotor.Direction.REVERSE);

        this.intake.setDirection(DcMotor.Direction.REVERSE);
        this.arm.setDirection(DcMotor.Direction.FORWARD);

        this.frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void stopAll() {
        this.frontLeft.setPower(0);
        this.frontRight.setPower(0);
        this.backLeft.setPower(0);
        this.backRight.setPower(0);
        this.carouselLeft.setPower(0);
        this.carouselRight.setPower(0);
        this.intake.setPower(0);
        this.arm.setPower(0);
    }

    public void moveBasedJoystick(double y, double x, double rx, double speed) {
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        this.frontLeft.setPower(frontLeftPower*speed);
        this.backLeft.setPower(backLeftPower*speed);
        this.frontRight.setPower(frontRightPower*speed);
        this.backRight.setPower(backRightPower*speed);
    }

    public void armUpTicks(int ticks, double speed) {
        int target = ticks;
        this.arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.arm.setTargetPosition(arm.getCurrentPosition() + target);
        this.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.arm.setPower(speed);

        while (arm.isBusy()) {

        }
        this.arm.setPower(0);
        this.arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void armDownTicks(int ticks, double speed) {
        int target = -ticks;
        this.arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.arm.setTargetPosition(arm.getCurrentPosition() + target);
        this.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.arm.setPower(speed);

        while (arm.isBusy()) {
        }
        this.arm.setPower(0);
        this.arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void strafeLeft(double ticks, double speed) {
        int target = (int)(Math.round(ticks));

        this.frontLeft.setTargetPosition(this.frontLeft.getCurrentPosition() - target);
        this.frontRight.setTargetPosition(this.frontRight.getCurrentPosition() + target);
        this.backLeft.setTargetPosition(this.backLeft.getCurrentPosition() + target);
        this.backRight.setTargetPosition(this.backRight.getCurrentPosition() - target);

        this.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //        runtime.reset();

        this.frontLeft.setPower(speed);
        this.backLeft.setPower(speed);
        this.backRight.setPower(speed);
        this.frontRight.setPower(speed);

        while (frontLeft.isBusy() && backLeft.isBusy() && backRight.isBusy() && frontRight.isBusy()) {
//            telemetry.addData("Path1",  "Running to %7d :%7d :%7d :%7d", newWheelTarget[0],  newWheelTarget[1], newWheelTarget[2], newWheelTarget[3]);
            telemetry.addData("Path2",  "Running at %7d :%7d :%7d :%7d", frontLeft.getCurrentPosition(), frontRight.getCurrentPosition(), backRight.getCurrentPosition(), backLeft.getCurrentPosition());
            telemetry.update();
        }
        // Stops motors after motors have reached target position
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);

        // Resets encoders
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void strafeRight(double ticks, double speed) {
        int target = (int)(Math.round(ticks));

        this.frontLeft.setTargetPosition(this.frontLeft.getCurrentPosition() + target);
        this.frontRight.setTargetPosition(this.frontRight.getCurrentPosition() - target);
        this.backLeft.setTargetPosition(this.backLeft.getCurrentPosition() - target);
        this.backRight.setTargetPosition(this.backRight.getCurrentPosition() + target);

        this.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //        runtime.reset();

        this.frontLeft.setPower(speed);
        this.backLeft.setPower(speed);
        this.backRight.setPower(speed);
        this.frontRight.setPower(speed);

        while (frontLeft.isBusy() && backLeft.isBusy() && backRight.isBusy() && frontRight.isBusy()) {
            telemetry.addData("Path2",  "Running at %7d :%7d :%7d :%7d", frontLeft.getCurrentPosition(), frontRight.getCurrentPosition(), backRight.getCurrentPosition(), backLeft.getCurrentPosition());
            telemetry.update();
        }
        // Stops motors after motors have reached target position
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);

        // Resets encoders
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public int moveTicks(int ticks, double speed, Direction dir) {
        int target = ticks;
        int frontLeftTarget;
        int frontRightTarget;
        int backLeftTarget;
        int backRightTarget;

        if (dir == Direction.FORWARD) {
            frontLeftTarget = target;
            frontRightTarget = target;
            backLeftTarget = target;
            backRightTarget = target;
        }
        else if (dir == Direction.REVERSE) {
            frontLeftTarget = -target;
            frontRightTarget = -target;
            backLeftTarget = -target;
            backRightTarget = -target;
        }
        else if (dir == Direction.RIGHT) {
            frontLeftTarget = target;
            frontRightTarget = -target;
            backLeftTarget = -target;
            backRightTarget = target;
        }
        else if (dir == Direction.LEFT) {
            frontLeftTarget = -target;
            frontRightTarget = target;
            backLeftTarget = target;
            backRightTarget = -target;
        }
        else if (dir == Direction.PIVOT_LEFT) {
            frontLeftTarget = -target;
            frontRightTarget = target;
            backLeftTarget = -target;
            backRightTarget = target;
        }
        else if (dir == Direction.PIVOT_RIGHT) {
            frontLeftTarget = target;
            frontRightTarget = -target;
            backLeftTarget = target;
            backRightTarget = -target;
        }
        else {
            return 1;
        }

        this.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        this.frontLeft.setTargetPosition(this.frontLeft.getCurrentPosition() + frontLeftTarget);
        this.frontRight.setTargetPosition(this.frontRight.getCurrentPosition() + frontRightTarget);
        this.backLeft.setTargetPosition(this.backLeft.getCurrentPosition() + backLeftTarget);
        this.backRight.setTargetPosition(this.backRight.getCurrentPosition() + backRightTarget);

        this.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setPower(speed);
        frontRight.setPower(speed);
        backLeft.setPower(speed);
        frontRight.setPower(speed);

        while (frontLeft.isBusy() && backLeft.isBusy() && backRight.isBusy() && frontRight.isBusy()) {

        }

        this.frontLeft.setPower(0);
        this.frontRight.setPower(0);
        this.backLeft.setPower(0);
        this.backRight.setPower(0);

        this.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        this.frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        return 0;
    }


    public void moveTime(int mills, double power, Direction dir) {
        if (dir == Direction.FORWARD)
            moveForward(mills, power);
        else if (dir == Direction.REVERSE)
            moveBackward(mills, power);
        else if (dir == Direction.PIVOT_LEFT)
            pivotLeft(mills, power);
        else if (dir == Direction.PIVOT_RIGHT)
            pivotRight(mills, power);
    }

    public void setPower(double power) {
        this.backLeft.setPower(power);
        this.backRight.setPower(power);
        this.frontLeft.setPower(power);
        this.frontRight.setPower(power);
    }

    protected void sleep(int milliseconds) {
        this.time.reset();

        while (time.milliseconds() < milliseconds) {

        }
    }

    public void moveForward(int mills, double power) {
        setPower(power);
        sleep(mills);
        setPower(0);
    }

    public void moveBackward(int mills, double power) {
        setPower(-power);
        sleep(mills);
        setPower(0);
    }

    public void pivotRight(int mills, double power) {
        this.backRight.setPower(-power);
        this.frontRight.setPower(-power);
        this.backLeft.setPower(power);
        this.frontLeft.setPower(power);
        sleep(mills);
        setPower(0);
    }

    public void pivotLeft(int mills, double power) {
        this.backRight.setPower(-power);
        this.frontRight.setPower(-power);
        this.backLeft.setPower(power);
        this.frontLeft.setPower(power);
        sleep(mills);
        setPower(0);
    }

    public int spinCarousel(int time, int power, Direction dir) {
        if (dir == Direction.REVERSE) {
            power = -power;
        }
        else if (dir == Direction.FORWARD) {

        }
        else {
            return 1;
        }

        this.carouselRight.setPower(power);
        this.carouselLeft.setPower(power);
        sleep(time);
        this.carouselRight.setPower(0);
        this.carouselLeft.setPower(0);
        return 0;
    }
}