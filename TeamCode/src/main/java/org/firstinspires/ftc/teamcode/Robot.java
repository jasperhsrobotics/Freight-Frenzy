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
    protected DcMotor[] wheels = new DcMotor[4];
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


        wheels[0] = frontLeft;
        wheels[1] = frontRight;
        wheels[2] = backLeft;
        wheels[3] = backRight;
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

    public void strafeLeftTicks(double ticks, double speed) {
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

        while (frontLeft.isBusy() && backLeft.isBusy() && backRight.isBusy() && frontRight.isBusy()) {

        }

        for (DcMotor wheel : wheels){
            // Stops motors after motors have reached target position
            wheel.setPower(0);

            // Resets encoders
            wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            wheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    public void strafeRightTicks(double ticks, double speed) {
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

        while (frontLeft.isBusy() && backLeft.isBusy() && backRight.isBusy() && frontRight.isBusy()) {

        }

        for (DcMotor wheel : wheels){
            // Stops motors after motors have reached target position
            wheel.setPower(0);

            // Resets encoders
            wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            wheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
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
            strafeRightTicks(target, speed);
            return 0;
        }
        else if (dir == Direction.LEFT) {
            strafeLeftTicks(target, speed);
            return 0;
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
            moveForwardTime(mills, power);
        else if (dir == Direction.REVERSE)
            moveBackwardTime(mills, power);
        else if (dir == Direction.PIVOT_LEFT)
            pivotLeftTime(mills, power);
        else if (dir == Direction.PIVOT_RIGHT)
            pivotRightTime(mills, power);
        else if (dir == Direction.LEFT)
            strafeLeftTime(mills, power);
        else if (dir == Direction.RIGHT)
            strafeRightTime(mills, power);
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

    public void moveForwardTime(int mills, double power) {
        setPower(power);
        sleep(mills);
        setPower(0);
    }

    public void moveBackwardTime(int mills, double power) {
        setPower(-power);
        sleep(mills);
        setPower(0);
    }

    public void strafeLeftTime(int mills, double power) {
        this.backRight.setPower(-power);
        this.frontRight.setPower(power);
        this.backLeft.setPower(power);
        this.frontLeft.setPower(-power);
        sleep(mills);
        setPower(0);
    }

    public void strafeRightTime(int mills, double power) {
        this.backRight.setPower(power);
        this.frontRight.setPower(-power);
        this.backLeft.setPower(-power);
        this.frontLeft.setPower(power);
        sleep(mills);
        setPower(0);
    }

    public void pivotRightTime(int mills, double power) {
        this.backRight.setPower(-power);
        this.frontRight.setPower(-power);
        this.backLeft.setPower(power);
        this.frontLeft.setPower(power);
        sleep(mills);
        setPower(0);
    }

    public void pivotLeftTime(int mills, double power) {
        this.backRight.setPower(-power);
        this.frontRight.setPower(-power);
        this.backLeft.setPower(power);
        this.frontLeft.setPower(power);
        sleep(mills);
        setPower(0);
    }

    public int spinCarouselTime(int time, int power, Direction dir) {
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
