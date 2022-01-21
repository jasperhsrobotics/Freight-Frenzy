package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot.Direction;

@Autonomous(name = "RED (Warehouse)")
public class AutoRedWarehouse extends LinearOpMode {
    Robot robot;
    ElapsedTime time;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot();
        time = new ElapsedTime();
        robot.initializeBot(hardwareMap, time);

        waitForStart();

        robot.moveTicks(50, 0.1, Direction.RIGHT);
        robot.moveTicks(100, 0.1, Direction.FORWARD);
    }

//    /////////////////////////////
//    // function defs
//    /////////////////////////////
//
//    public void moveTicks(int ticks, double speed, Direction dir) {
//        robot.moveTicks(ticks, speed, dir);
//    }
//
//    public void moveTime(int mills, double power, Direction dir) {
//        if (dir == Direction.FORWARD)
//            moveForward(mills, power);
//        else if (dir == Direction.REVERSE)
//            moveBackward(mills, power);
//        else if (dir == Direction.PIVOT_LEFT)
//            pivotLeft(mills, power);
//        else if (dir == Direction.PIVOT_RIGHT)
//            pivotRight(mills, power);
//    }
//
//    public void setPower(double power) {
//        robot.backLeft.setPower(power);
//        robot.backRight.setPower(power);
//        robot.frontLeft.setPower(power);
//        robot.frontRight.setPower(power);
//    }
//
//    public void moveForward(int mills, double power) {
//        setPower(power);
//        sleep(mills);
//        setPower(0);
//    }
//
//    public void moveBackward(int mills, double power) {
//        setPower(-power);
//        sleep(mills);
//        setPower(0);
//    }
//
//    public void pivotRight(int mills, double power) {
//        robot.backRight.setPower(-power);
//        robot.frontRight.setPower(-power);
//        robot.backLeft.setPower(power);
//        robot.frontLeft.setPower(power);
//        sleep(mills);
//        setPower(0);
//    }
//
//    public void pivotLeft(int mills, double power) {
//        robot.backRight.setPower(-power);
//        robot.frontRight.setPower(-power);
//        robot.backLeft.setPower(power);
//        robot.frontLeft.setPower(power);
//        sleep(mills);
//        setPower(0);
//    }
}
