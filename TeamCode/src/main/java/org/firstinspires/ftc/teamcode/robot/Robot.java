package org.firstinspires.ftc.teamcode.robot;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.robot.subsystems.Carousel;

public class Robot {
    public Carousel carousel;
    public SampleMecanumDrive drive;

    public Robot(HardwareMap hardwareMap) {
        carousel = new Carousel(hardwareMap);
        drive = new SampleMecanumDrive(hardwareMap);
    }
}
