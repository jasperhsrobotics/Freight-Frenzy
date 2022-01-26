package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "TeleOp ONE CONTROLLER USE THIS ONE")
public class TeleOpOneController extends OpMode {
    Robot robot;

    @Override
    public void init() {
        robot = new Robot();

        robot.initializeBot(hardwareMap);
    }

    @Override
    public void loop() {
        /////////////// MOVEMENT /////////////////
        robot.moveBasedJoystick(-gamepad1.left_stick_y, gamepad1.left_stick_x*1.2, gamepad1.right_stick_x, 0.5);

        /////////////// ARM /////////////////
        if (gamepad1.dpad_up) {
            robot.arm.setDirection(DcMotorSimple.Direction.FORWARD);
            robot.arm.setPower(1);
        } else if (gamepad1.dpad_down) {
            robot.arm.setDirection(DcMotorSimple.Direction.REVERSE);
            robot.arm.setPower(1);
        } else {
            robot.arm.setDirection(DcMotorSimple.Direction.FORWARD);
            robot.arm.setPower(0);
        }

        /////////////// INTAKE /////////////////
        if (gamepad1.left_bumper) {
            robot.intake.setDirection(DcMotorSimple.Direction.REVERSE);
            robot.intake.setPower(0.5);
        } else if (gamepad1.left_trigger > 0) {
            robot.intake.setDirection(DcMotorSimple.Direction.REVERSE);
            robot.intake.setPower(0.5);
        } else if (gamepad1.right_bumper) {
            robot.intake.setDirection(DcMotorSimple.Direction.FORWARD);
            robot.intake.setPower(0.9);
        } else {
            robot.intake.setPower(0);
        }

        /////////////// CAROUSEL ///////////////
        if (gamepad1.x) {
            robot.carouselLeft.setDirection(DcMotorSimple.Direction.FORWARD);
            robot.carouselRight.setDirection(DcMotorSimple.Direction.FORWARD);
            robot.carouselLeft.setPower(1);
            robot.carouselRight.setPower(1);
        } else if (gamepad1.b) {
            robot.carouselLeft.setDirection(DcMotorSimple.Direction.REVERSE);
            robot.carouselRight.setDirection(DcMotorSimple.Direction.REVERSE);
            robot.carouselLeft.setPower(1);
            robot.carouselRight.setPower(1);
        } else {
            robot.carouselRight.setPower(0);
            robot.carouselLeft.setPower(0);
        }

        /////////////// EMERGENCY STOP ///////////////
        if (gamepad1.a) {
            robot.stopAll();
        }
        /////////////// TELEMETRY ///////////////
        outputTelemetry();

    }

    public void outputTelemetry() {
        telemetry.addData("Status", "Run Time: " + getRuntime());
        telemetry.addData("Motors", "front left (%.2f), front right (%.2f), back left (%.2f), back right (%.2f)", robot.frontLeft.getPower(), robot.frontRight.getPower(), robot.backLeft.getPower(), robot.backRight.getPower());
        telemetry.addData("Arm", robot.arm.getPower());
        telemetry.addData("Intake", robot.intake.getPower());
    }
}
