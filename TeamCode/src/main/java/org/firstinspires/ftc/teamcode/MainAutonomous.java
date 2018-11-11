package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

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
    protected DcMotor driveTrainMotorLeft;
    protected DcMotor driveTrainMotorRight;
    protected DcMotor jointMotor;
    protected DcMotor liftMotor;

    // 180 degree Servos
    protected Servo sampleServo;

    // Continuous rotation servos
    protected CRServo intakeServo;

    // Color sensor
    protected LynxI2cColorRangeSensor sampleColor;

    // Multiplier Constants
    protected static final int FORWARD_MULTIPLIER = 88;
    protected static final double TURN_MULTIPLIER = 12.8;
    protected static final int JOINT_EXTENDED = 0; // TODO: Find position
    protected static final int JOINT_FOLDED = 0; // TODO: Find position

    // More Constants - Field Numbers
    protected static final int SAMPLE_LENGTH = 54;
    protected static final int LANDER_TO_NS_WALL_DEGREES = -135;
    protected static final int LANDER_TO_SAMPLE_START_INCHES = 36;
    protected static final int NS_WALL_TO_SAMPLE_START_DEGREES = -135;
    protected static final int SAMPLE_END_TO_PARALLEL_WALL_DEGREES = 45;
    protected static final int SAMPLE_END_TO_SAMPLE_START_INCHES = 48;
    protected static final int PARALLEL_WALL_TO_SAMPLE_START_DEGREES = -45;
    protected static final int TURN_AROUND = 180;
    protected static final int SAMPLE_END_TO_EW_WALL_CENTER_INCHES = 60;
    protected static final int EW_WALL_CENTER_TO_DEPOT_DEGREES = -135;
    protected static final int EW_WALL_CENTER_TO_DEPOT_INCHES = 48;
    protected static final int DEPOT_TO_CRATER_INCHES = 72;
    protected static final int NSAMPLE_TO_NWALL_INCHES = 24;
    protected static final int DEPOT_TO_SSAMPLE_INCHES = 180;

    protected void initOpMode() {
        driveTrainMotorLeft = hardwareMap.get(DcMotor.class, "driveTrainMotorLeft");
        driveTrainMotorRight = hardwareMap.get(DcMotor.class, "driveTrainMotorRight");
        jointMotor = hardwareMap.get(DcMotor.class, "jointMotor");
        liftMotor = hardwareMap.get(DcMotor.class, "liftMotor");

        jointMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        jointMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        sampleServo = hardwareMap.get(Servo.class, "sampleServo");
        intakeServo = hardwareMap.get(CRServo.class, "intakeServo");

        sampleColor = hardwareMap.get(LynxI2cColorRangeSensor.class, "sampleColor");
    }

    protected void jointPosition(String position) {
        if (position == "extended") {
            jointMotor.setTargetPosition(JOINT_EXTENDED); // TODO: Add position
        } else {
            jointMotor.setTargetPosition(JOINT_FOLDED); // TODO: Add position
        }
    }
    protected void intake() {
        intakeServo.setPower(1.0);
    }

    protected void lower() {
        // write code for lowering and
        // raising the robot here
    }

    /**
     *this method makes the robot turn left or right
     * For ex: to turn left you call the method turn(-90)
     * To turn right you call the method turn(90)
     * 
     * @param degrees
     */
    protected void turn(int degrees, double speed) {

        driveTrainMotorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveTrainMotorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveTrainMotorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveTrainMotorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Multiply degrees input by a determined constant to tell motor how far to turn
        driveTrainMotorLeft.setTargetPosition((int) (degrees * TURN_MULTIPLIER));
        driveTrainMotorRight.setTargetPosition((int) (degrees * TURN_MULTIPLIER));

        // Maximum speed
        driveTrainMotorLeft.setPower(speed);
        driveTrainMotorRight.setPower(speed);

        // Loop until motors are no longer busy
        while (driveTrainMotorLeft.isBusy() || driveTrainMotorRight.isBusy());

        // Shut off motors
        driveTrainMotorLeft.setPower(0);
        driveTrainMotorRight.setPower(0);

    }


    /**
     * This method helps move forward or backwords.
     * For ex: to move forward 5 inches, you call the method moveInch(5)
     * To move backwards 5 inches, you call the method moveInch(-5)
     *
     * @param inches
     */
    protected void moveInch(int inches, double speed) {

        driveTrainMotorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveTrainMotorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveTrainMotorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveTrainMotorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Multiply the distance we require by a determined constant to tell the motors how far to turn
        driveTrainMotorLeft.setTargetPosition((int) (inches * -FORWARD_MULTIPLIER));
        driveTrainMotorRight.setTargetPosition((int) (inches * FORWARD_MULTIPLIER));

        // The maximum speed of the motors.
        driveTrainMotorLeft.setPower(speed);
        driveTrainMotorRight.setPower(speed);

        // Loop until both motors are no longer busy.
        while (driveTrainMotorLeft.isBusy() || driveTrainMotorRight.isBusy()) ;
        driveTrainMotorLeft.setPower(0);
        driveTrainMotorRight.setPower(0);
    }

    protected void sample() {
        driveTrainMotorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveTrainMotorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveTrainMotorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveTrainMotorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Multiply the distance we require by a determined constant to tell the motors how far to turn
        driveTrainMotorLeft.setTargetPosition((int) (SAMPLE_LENGTH * -FORWARD_MULTIPLIER));
        driveTrainMotorRight.setTargetPosition((int) (SAMPLE_LENGTH * FORWARD_MULTIPLIER));

        // The maximum speed of the motors.
        driveTrainMotorLeft.setPower(.15);
        driveTrainMotorRight.setPower(.15);
        // Loop until both motors are no longer busy.
        while (driveTrainMotorLeft.isBusy() || driveTrainMotorRight.isBusy()) {
            if (sampleColor.red() > sampleColor.green() && sampleColor.green() > sampleColor.blue()) {
                sampleServo.setPosition(.25);
                sleep(100);
                sampleServo.setPosition(0);
                telemetry.addData("sample", "knock");
                telemetry.update();
            }
        }
        driveTrainMotorLeft.setPower(0);
        driveTrainMotorRight.setPower(0);
    }

}