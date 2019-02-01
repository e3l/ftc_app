package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp

public class MainTeleOp extends LinearOpMode {
    // Motors
    protected DcMotor rightDriveMotor;
    protected DcMotor leftDriveMotor;
    protected DcMotor rightLowerJoint;
    protected DcMotor leftLowerJoint;
    protected DcMotor rightUpperJoint;
    protected DcMotor leftUpperJoint;
    protected DcMotor liftMotor;

    // Servos
    protected Servo sampleServo;
    protected Servo markerServo;
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
        rightLowerJoint = hardwareMap.get(DcMotor.class,"rightLowerJoint");
        leftLowerJoint = hardwareMap.get(DcMotor.class,"leftLowerJoint");
        rightUpperJoint = hardwareMap.get(DcMotor.class,"rightUpperJoint");
        leftUpperJoint = hardwareMap.get(DcMotor.class,"leftUpperJoint");
        liftMotor =  hardwareMap.get(DcMotor.class,"liftMotor");

        rightDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightLowerJoint.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftLowerJoint.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightUpperJoint.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftUpperJoint.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightLowerJoint.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftLowerJoint.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightUpperJoint.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftUpperJoint.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rightLowerJoint.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftLowerJoint.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightUpperJoint.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftUpperJoint.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //initialize the servos
        sampleServo = hardwareMap.get(Servo.class,"sampleServo");
        markerServo = hardwareMap.get(Servo.class,"markerServo");
        intakeServo = hardwareMap.get(CRServo.class, "intakeServo");
    }

    public void runOpMode() {
        initOpMode();
        waitForStart();
        while (opModeIsActive()) {
            drive();
            intake();
            lift();
            joint();
            servos();
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
    // S for stop, I for intake, P for push
    private final static int STOP = 0;
    private final static int SPIT = 1;
    private final static int SUCK = -1;
    private int intakePower = STOP;
    private boolean intakeServoChangingState = false;

    //controller 2
    private void intake() {
        if (gamepad2.right_bumper && !intakeServoChangingState) {
            if (intakePower == SUCK) {
                intakePower = STOP;
            } else {
                intakePower = SUCK;
            }
            intakeServoChangingState = true;
        } else if (gamepad2.left_bumper && !intakeServoChangingState) {
            if (intakePower == SPIT) {
                intakePower = STOP;
            } else {
                intakePower = SPIT;
            }
            intakeServoChangingState = true;
        } else if (!gamepad2.left_bumper && !gamepad2.right_bumper) {
            intakeServoChangingState = false;
        }

        intakeServo.setPower(intakePower);
    }

    private void joint() {
        if (gamepad2.dpad_down) { //dpad_up on gamepad2 will run the motors at the same time
            rightLowerJoint.setPower(-1);
            leftLowerJoint.setPower(-1);

            rightUpperJoint.setPower(-1);
            leftUpperJoint.setPower(-1);
        } else if (gamepad2.dpad_up) {
            rightLowerJoint.setPower(1);
            leftLowerJoint.setPower(1);

            rightUpperJoint.setPower(1);
            leftUpperJoint.setPower(1);
        } else if (gamepad2.left_stick_y != 0 || gamepad2.right_stick_y != 0) {
            rightLowerJoint.setPower(-gamepad2.right_stick_y);
            leftLowerJoint.setPower(-gamepad2.right_stick_y);

            rightUpperJoint.setPower(-gamepad2.left_stick_y);
            leftUpperJoint.setPower(-gamepad2.left_stick_y);
        } else {
            rightLowerJoint.setPower(0);
            leftLowerJoint.setPower(0);

            rightUpperJoint.setPower(0);
            leftUpperJoint.setPower(0);
        }
    }

    //controller 2
    private void lift() {
        if (gamepad2.right_trigger != 0) {
            liftMotor.setPower(gamepad2.right_trigger);
        } else if (gamepad2.left_trigger != 0) {
            liftMotor.setPower(-gamepad2.left_trigger);
        } else {
            liftMotor.setPower(0);
        }
    }

    private void servos() {
        if (gamepad2.x) {
            markerServo.setPosition(.9);
        } else if (gamepad2.y) {
            sampleServo.setPosition(.7);
        }
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
