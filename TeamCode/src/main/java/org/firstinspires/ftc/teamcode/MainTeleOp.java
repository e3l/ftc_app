package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.security.DomainCombiner;

@TeleOp

public class MainTeleOp extends LinearOpMode {
    // Motors
    protected DcMotor rightDriveMotor;
    protected DcMotor leftDriveMotor;
    protected DcMotor liftMotor;
    protected DcMotor jointMotor;
    protected DcMotor jointMotor2;
    protected DcMotor intakeSlideMotor;

    // Servos
    protected CRServo intakeServo;

    // Color sensors
    protected LynxI2cColorRangeSensor sampleSensor;

    //constants
    public static final int JOINT_FOLDED = 0;
    public static final int JOINT_EXTENDED = 90;

    private void initOpMode() {
        //initialize all the motors
        rightDriveMotor = hardwareMap.get(DcMotor.class, "rightDriveMotor");
        leftDriveMotor = hardwareMap.get(DcMotor.class, "leftDriveMotor");
        liftMotor = hardwareMap.get(DcMotor.class, "liftMotor");
        jointMotor = hardwareMap.get(DcMotor.class, "jointMotor");
        jointMotor2 = hardwareMap.get(DcMotor.class,"jointMotor2");
        intakeSlideMotor = hardwareMap.get(DcMotor.class, "intakeSlideMotor");

        jointMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        jointMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        jointMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        jointMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        rightDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        jointMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        jointMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //initialize the servos
        intakeServo = hardwareMap.get(CRServo.class, "intakeServo");

        // Sensors initialization
        sampleSensor = hardwareMap.get(LynxI2cColorRangeSensor.class, "sampleSensor");
    }

    public void runOpMode() {
        initOpMode();
        waitForStart();
        while (opModeIsActive()) {
            drive();
            intake();
            lift();
        }
    }

    // slow variable to allow for 'slowmode' - allowing the robot to go slower.
    protected double slow = 1.66;

    //controller 1
    // Direction variable to change 'front' perspective
    protected int direction = 1;

    private void drive() {
        if (gamepad1.dpad_up) {
            direction = 1;
        }
        if (gamepad1.dpad_down) {
            direction = -1;
        }

        if (gamepad1.right_bumper) {
            slow = 1;
        }
        if (gamepad1.left_bumper) {
            slow = 1.66;
        }

        rightDriveMotor.setPower(-1 * direction * gamepad1.left_stick_y / slow);
        leftDriveMotor.setPower(direction * gamepad1.left_stick_y / slow);
        // leftDrive is -1 due to the fact that the motors are orientated differently.

        if (gamepad1.right_stick_x != 0) {
            rightDriveMotor.setPower((-8 * gamepad1.right_stick_x) / slow);
            leftDriveMotor.setPower((-8 * gamepad1.right_stick_x) / slow);
        }
    }

    // A 'direction' variable for toggling intake
    char intakeDirection = 'S'; // S for stop, I for intake, P for push

    //controller 2
    private void intake() {
        if (gamepad2.dpad_up) {
            jointMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            jointMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            jointMotor.setTargetPosition(JOINT_EXTENDED);
            jointMotor2.setTargetPosition(-JOINT_EXTENDED);

            jointMotor.setPower(.5);
            jointMotor2.setPower(.5);

            while (jointMotor.isBusy() || jointMotor2.isBusy()) {}

            jointMotor.setPower(0);
            jointMotor2.setPower(0);
        } else if (gamepad2.dpad_down) {
            jointMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            jointMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            jointMotor.setTargetPosition(JOINT_FOLDED);
            jointMotor2.setTargetPosition(-JOINT_FOLDED);

            jointMotor.setPower(.5);
            jointMotor2.setPower(.5);

            while (jointMotor.isBusy() || jointMotor2.isBusy()) {}

            jointMotor.setPower(0);
            jointMotor2.setPower(0);
        } else if (gamepad2.left_stick_y != 0) {
            jointMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            jointMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            jointMotor.setPower(gamepad2.left_stick_y);
            jointMotor2.setPower(-gamepad2.left_stick_y);
        } else {
            jointMotor.setPower(0);
            jointMotor2.setPower(0);
        }

        if (gamepad2.right_bumper) {
            if (intakeDirection == 'F') {
                intakeDirection = 'S';
            } else {
                intakeDirection = 'F';
            }
        } else if (gamepad2.left_bumper) {
            if (intakeDirection == 'P') {
                intakeDirection = 'S';
            } else {
                intakeDirection = 'P';
            }
        }

        if (intakeDirection == 'S') {
            intakeServo.setPower(0);
        } else if (intakeDirection == 'F') {
            intakeServo.setPower(-1);
        } else if (intakeDirection == 'P') {
            intakeServo.setPower(1);
        } else {
            telemetry.addData("intakeDirection","invalid");
            telemetry.update();
        }

        if (gamepad2.right_trigger != 0) {
            intakeSlideMotor.setPower(-gamepad2.right_trigger);
        } else if (gamepad2.left_trigger != 0) {
            intakeSlideMotor.setPower(gamepad2.left_trigger);
        } else {
            intakeSlideMotor.setPower(0);
        }
    }

    //controller 2
    private void lift() {
        liftMotor.setPower(gamepad2.right_stick_y);
    }
  
/* New design automatically deposits - no method needed
    //controller 2
    private void deposit() {
        if (gamepad2.a) {
            double position = depositServo.getPosition();
            depositServo.setPosition(position + 150);
            depositServo.setPosition(position);
        }
    }
*/
}
