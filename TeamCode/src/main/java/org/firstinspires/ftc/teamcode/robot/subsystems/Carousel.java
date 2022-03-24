package org.firstinspires.ftc.teamcode.robot.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

public class Carousel {
    private DcMotor carouselMotor;
    private ElapsedTime timer;

    public Carousel(HardwareMap hardwareMap) {
        carouselMotor = hardwareMap.get(DcMotor.class, "carouselR");
        timer = new ElapsedTime();
    }

    public void spinTime(double power, int milliseconds) {
        carouselMotor.setPower(power);
        timer.reset();
        while (timer.milliseconds() < milliseconds) {

        }
        carouselMotor.setPower(0);
    }

    public void spinTimeUpdateDrive(double power, int milliseconds, SampleMecanumDrive drive) {
        carouselMotor.setPower(power);
        timer.reset();
        while (timer.milliseconds() < milliseconds) {
            drive.update();
        }
        carouselMotor.setPower(0);
    }

    public void startSpinning(double power) {
        carouselMotor.setPower(power);
    }

    public void stopSpinning() {
        carouselMotor.setPower(0);
    }
}