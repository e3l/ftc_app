package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Eva on 9/16/18.
 */

@Autonomous
public abstract class MainAutonomous extends LinearOpMode {

    // Motors
    protected DcMotor rightDriveMotor;
    protected DcMotor leftDriveMotor;
    protected DcMotor rightLowerJoint;
    protected DcMotor leftLowerJoint;
    protected DcMotor rightUpperJoint;
    protected DcMotor leftUpperJoint;
    protected DcMotor liftMotor;

    // Continuous rotation servos
    protected CRServo intakeServo;

    // 180 degree Servos
    protected Servo sampleServo;
    protected Servo markerServo;

    // Color sensor
    protected LynxI2cColorRangeSensor sampleSensor;

    // Multiplier Constants
    protected static final int FORWARD_MULTIPLIER = 120;
    protected static final double TURN_MULTIPLIER = 22.5;

    // More Constants - Field Numbers
    protected static final int SAMPLE_LENGTH_INCHES = 58;
    protected static final int LANDER_TO_SAMPLE_START_DEGREES = 30;
    protected static final int LANDER_TO_SAMPLE_START_INCHES = 20;
    protected static final int TURN_TO_SAMPLE_DEGREES = 65;
    protected static final int SAMPLE_END_TO_PARALLEL_WALL_DEGREES = -45;
    protected static final int SAMPLE_END_TO_SAMPLE_START_INCHES = 48;
    protected static final int PARALLEL_WALL_TO_SAMPLE_START_DEGREES = -45;
    protected static final int TURN_AROUND_DEGREES = 180;
    protected static final int SAMPLE_END_TO_DEPOT = 72;
    protected static final int DEPOT_TO_CRATER_INCHES = 84;

    protected void initOpMode() {
        leftDriveMotor = hardwareMap.get(DcMotor.class, "leftDriveMotor");
        rightDriveMotor = hardwareMap.get(DcMotor.class, "rightDriveMotor");
        rightLowerJoint = hardwareMap.get(DcMotor.class,"rightLowerJoint");
        leftLowerJoint = hardwareMap.get(DcMotor.class,"leftLowerJoint");
        rightUpperJoint = hardwareMap.get(DcMotor.class,"rightUpperJoint");
        leftUpperJoint = hardwareMap.get(DcMotor.class,"leftUpperJoint");
        liftMotor = hardwareMap.get(DcMotor.class, "liftMotor");

        rightLowerJoint.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftLowerJoint.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightUpperJoint.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftUpperJoint.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intakeServo = hardwareMap.get(CRServo.class, "intakeServo");

        sampleServo = hardwareMap.get(Servo.class, "sampleServo");
        markerServo = hardwareMap.get(Servo.class,"markerServo");

        sampleSensor = hardwareMap.get(LynxI2cColorRangeSensor.class, "sampleSensor");

        sampleServo.setPosition(.7);
        markerServo.setPosition(.9);
    }

    protected void jointExtend() {
        rightUpperJoint.setPower(1);
        leftUpperJoint.setPower(1);

        sleep(500);

        rightLowerJoint.setPower(1);
        leftLowerJoint.setPower(1);

        rightUpperJoint.setPower(0);
        leftUpperJoint.setPower(0);

        sleep(850);

        rightLowerJoint.setPower(0);
        leftLowerJoint.setPower(0);
    }

    protected void lower() {
        liftMotor.setPower(-1);
        sleep(2950);
        liftMotor.setPower(0);
    }

    /**
     *this method makes the robot turn left or right
     * For ex: to turn left you call the method turn(-90)
     * To turn right you call the method turn(90)
     * 
     * @param degrees
     */
    protected void turn(int degrees, double speed) {

        leftDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Multiply degrees input by a determined constant to tell motor how far to turn
        leftDriveMotor.setTargetPosition((int) (degrees * TURN_MULTIPLIER));
        rightDriveMotor.setTargetPosition((int) (degrees * TURN_MULTIPLIER));

        // Maximum speed

        leftDriveMotor.setPower(speed);
        rightDriveMotor.setPower(speed);


        // Loop until motors are no longer busy
        while (leftDriveMotor.isBusy() || rightDriveMotor.isBusy());

        // Shut off motors
        leftDriveMotor.setPower(0);
        rightDriveMotor.setPower(0);
    }


    /**
     * This method helps move forward or backwords.
     * For ex: to move forward 5 inches, you call the method moveInch(5)
     * To move backwards 5 inches, you call the method moveInch(-5)
     *
     * @param inches
     */
    protected void moveInch(int inches, double speed) {

        leftDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Multiply the distance we require by a determined constant to tell the motors how far to turn
        leftDriveMotor.setTargetPosition((int) (inches * -FORWARD_MULTIPLIER));
        rightDriveMotor.setTargetPosition((int) (inches * FORWARD_MULTIPLIER));

        // The maximum speed of the motors.
        leftDriveMotor.setPower(speed);
        rightDriveMotor.setPower(speed);

        // Loop until both motors are no longer busy.
        while (leftDriveMotor.isBusy() || rightDriveMotor.isBusy()) ;
        leftDriveMotor.setPower(0);
        rightDriveMotor.setPower(0);
    }

    protected void sample() {
        leftDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Multiply the distance we require by a determined constant to tell the motors how far to turn
        leftDriveMotor.setTargetPosition((int) (SAMPLE_LENGTH_INCHES * -FORWARD_MULTIPLIER));
        rightDriveMotor.setTargetPosition((int) (SAMPLE_LENGTH_INCHES * FORWARD_MULTIPLIER));

        // The maximum speed of the motors.
        leftDriveMotor.setPower(.15);
        rightDriveMotor.setPower(.15);
        // Loop until both motors are no longer busy.
        while (leftDriveMotor.isBusy() || rightDriveMotor.isBusy()) {
            if (sampleSensor.red() > sampleSensor.green() && sampleSensor.green() > sampleSensor.blue()) {
                sleep(100);
                sampleServo.setPosition(1);
                sleep(100);
                sampleServo.setPosition(.7);
                telemetry.addData("sample", "knock");
                telemetry.update();
            }
        }
        leftDriveMotor.setPower(0);
        rightDriveMotor.setPower(0);
    }

    protected void marker() {
        markerServo.setPosition(.5);
        sleep(1000);
        markerServo.setPosition(.9);
    }
}