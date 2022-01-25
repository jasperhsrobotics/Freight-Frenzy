package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot.Direction;

@Autonomous(name = "RED (Carousel + SU)")
public class AutoRedWarehouseSU extends LinearOpMode {
    Robot robot;
    ElapsedTime time;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot();
        time = new ElapsedTime();
        robot.initializeBot(hardwareMap, time);

        waitForStart();

        robot.moveTicks(50, 0.1, Direction.FORWARD);
        robot.moveTicks(200, 0.7, Direction.LEFT);
        robot.moveTicks(125, 0.3, Direction.LEFT);
        robot.moveTicks(30, 0.1, Direction.REVERSE);

        robot.setPower(-0.1);
        robot.spinCarouselTime(4000, 1, Direction.FORWARD);
        robot.setPower(0);

        robot.moveTicks(120, 0.1, Direction.FORWARD);
        robot.moveTime(3000, 0.3, Direction.RIGHT);
    }
}
