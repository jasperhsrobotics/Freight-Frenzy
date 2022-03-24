package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.robot.Robot;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequenceBuilder;

@Autonomous(name = "Blue Carousel")
public class Test extends LinearOpMode {
    Robot robot;

    @Override
    public void runOpMode() {
        //SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        robot = new Robot(hardwareMap);

        Pose2d startPos = new Pose2d(-30, 62, Math.toRadians(270));

        robot.drive.setPoseEstimate(startPos);

        TrajectorySequence goToCarousel1 = robot.drive.trajectorySequenceBuilder(startPos)
                .lineToLinearHeading(new Pose2d(-53, 50, Math.toRadians(300)))
                .build();

        TrajectorySequence goToCarousel2 = robot.drive.trajectorySequenceBuilder(goToCarousel1.end())
                .lineToConstantHeading(new Vector2d(-56, 56))
                .build();

        waitForStart();
        if(isStopRequested()) return;

        //robot.drive.followTrajectorySequence(positionForCarousel);
        robot.drive.followTrajectorySequence(goToCarousel1);
        robot.drive.followTrajectorySequenceAsync(goToCarousel2);
        robot.carousel.spinTimeUpdateDrive(1, 5000, robot.drive);
    }
}
