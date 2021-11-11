package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Arrays;
import java.util.List;

@Autonomous(name = "Auto")
public class Auto extends LinearOpMode {
    private final ElapsedTime runtime = new ElapsedTime();

    // Initializes drive motors to null
    private static DcMotor frontLeft = null;
    private static DcMotor frontRight = null;
    private static DcMotor backLeft = null;
    private static DcMotor backRight = null;
    private static DcMotor carouselMotor = null;

    // measure
    private static final double COUNTS_PER_REV = 1120.0;
    private static final double WHEEL_DIAMETER = 4.0;
    private static final double FORWARD_COUNTS_PER_INCH = (COUNTS_PER_REV) / (WHEEL_DIAMETER * 3.1415);
    private static final double SIDE_COUNTS_PER_INCH = FORWARD_COUNTS_PER_INCH * 1.5;
    private static final double PIVOT_COUNTS_PER_DEGREE = FORWARD_COUNTS_PER_INCH / 4.5;
    private static final double SPEED = 0.5;
    private static final double CAROUSEL_SPEED = 0.5;
    private static final double TIMEOUT = 5; // seconds, used in printing in move()

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
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        carouselMotor.setDirection(DcMotor.Direction.FORWARD);

        waitForStart();
        start();

        /*
         * current plan: carousel -> turn around -> warehouse
         * total points: 20
         */
        move(Direction.LEFT, 15);
        move(Direction.COUNTERCLOCKWISE, 20);
        spinCarousel(5000);

        move(Direction.CLOCKWISE, 20);
        move(Direction.FORWARD, 15); // check how much room the shipping hub allows
        move(Direction.COUNTERCLOCKWISE, 90);

        move(Direction.BACKWARD, 50, 0.7);
    }

    /**
     * Runs the carousel attachment
     * @param milliseconds - motor run time
     */
    public void spinCarousel(int milliseconds) {
        carouselMotor.setPower(CAROUSEL_SPEED);
        sleep(milliseconds);
        carouselMotor.setPower(0);
    }

    /**
     * Moves the robot using encoder values and omnidirectional pivoting
     * @param direction - the direction to move {FORWARD, BACKWARD, LEFT, RIGHT, CLOCKWISE, COUNTERCLOCKWISE}
     * @param distance - How far to travel in inches or pivot in degrees
     * @param print - Will we display telemetry?
     */
    public void move(Direction direction, double distance, boolean print, double nSpeed) {

        List<Integer> motorSpeeds;
        double driveMult;

        switch (direction) {
            case FORWARD:
                motorSpeeds = Arrays.asList(1,1,1,1);
                driveMult = FORWARD_COUNTS_PER_INCH;
                break;
            case BACKWARD:
                motorSpeeds = Arrays.asList(-1,-1,-1,-1);
                driveMult = FORWARD_COUNTS_PER_INCH;
                break;
            case LEFT:
                motorSpeeds = Arrays.asList(-1,1,-1,1);
                driveMult = SIDE_COUNTS_PER_INCH;
                break;
            case RIGHT:
                motorSpeeds = Arrays.asList(1,-1,1,-1);
                driveMult = SIDE_COUNTS_PER_INCH;
                break;
            case CLOCKWISE:
                motorSpeeds = Arrays.asList(1,1,-1,-1);
                driveMult = PIVOT_COUNTS_PER_DEGREE;
                break;
            case COUNTERCLOCKWISE:
                motorSpeeds = Arrays.asList(-1,-1,1,1);
                driveMult = PIVOT_COUNTS_PER_DEGREE;
                break;
            default:
                motorSpeeds = Arrays.asList(0,0,0,0);
                driveMult = 0;
                break;
        }

        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + (int) (motorSpeeds.get(0) * distance * driveMult));
        backLeft.setTargetPosition(backLeft.getCurrentPosition() + (int) (motorSpeeds.get(1) * distance * driveMult));
        backRight.setTargetPosition(backRight.getCurrentPosition() + (int) (motorSpeeds.get(2) * distance * driveMult));
        frontRight.setTargetPosition(frontRight.getCurrentPosition() + (int) (motorSpeeds.get(3) * distance * driveMult));

        runtime.reset();

        if (nSpeed == 0) {
            nSpeed = SPEED;
        }
        frontLeft.setPower(nSpeed);
        backLeft.setPower(nSpeed);
        backRight.setPower(nSpeed);
        frontRight.setPower(nSpeed);

        while (opModeIsActive() && (runtime.seconds() < TIMEOUT && frontLeft.isBusy() && backLeft.isBusy() && backRight.isBusy() && frontRight.isBusy())) {
            if (print) {
                telemetry.addData("Direction",direction.toString());
                telemetry.addData("Distance",Double.toString(distance));
                telemetry.update();
            }
        }

        frontLeft.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
        frontRight.setPower(0);

    }

    /**
     * Same as move(Direction direction, double distance, boolean print)
     * but print is true
     */
    public void move(Direction direction, double distance) {
        move(direction, distance, true, 0);
    }

    /**
     * For a specific speed other than the default (see SPEED)
    */
    public void move(Direction direction, double distance, double nSpeed) {
        move(direction, distance, true, nSpeed);
    }

    /**
     * For setting print to false
     */
    public void move(Direction direction, double distance, boolean print) {
        move(direction, distance, print, 0);
    }
}