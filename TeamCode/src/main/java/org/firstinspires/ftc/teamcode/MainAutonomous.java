
package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

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
    protected static final int SAMPLE_TO_NEXT_STOP_INCHES = 63;
    protected static final int LANDER_TO_SAMPLE_START_DEGREES = 35;
    protected static final int TURN_TO_SAMPLE_DEGREES = 65;
    protected static final int LANDER_TO_SAMPLE_START_INCHES = 20;
    protected static final int DEPOT_TO_CRATER_INCHES = 75;

    protected void initOpMode() {
        leftDriveMotor = hardwareMap.get(DcMotor.class, "leftDriveMotor");
        rightDriveMotor = hardwareMap.get(DcMotor.class, "rightDriveMotor");
        rightLowerJoint = hardwareMap.get(DcMotor.class, "rightLowerJoint");
        leftLowerJoint = hardwareMap.get(DcMotor.class, "leftLowerJoint");
        rightUpperJoint = hardwareMap.get(DcMotor.class, "rightUpperJoint");
        leftUpperJoint = hardwareMap.get(DcMotor.class, "leftUpperJoint");
        liftMotor = hardwareMap.get(DcMotor.class, "liftMotor");

        rightLowerJoint.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftLowerJoint.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightUpperJoint.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftUpperJoint.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intakeServo = hardwareMap.get(CRServo.class, "intakeServo");

        sampleServo = hardwareMap.get(Servo.class, "sampleServo");
        markerServo = hardwareMap.get(Servo.class, "markerServo");

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
        sleep(3050);
        liftMotor.setPower(0);
    }

    /**
     * this method makes the robot turn left or right
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
        while (leftDriveMotor.isBusy() || rightDriveMotor.isBusy()) ;

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

    protected void marker() {
        markerServo.setPosition(.5);
        sleep(1000);
        markerServo.setPosition(.9);
    }

    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    private static final String VUFORIA_KEY = "AXOZJVv/////AAAAGb2y8LIaVE3fi65/6/TwcCMDDJlAw0RjWXtTZPLR" +
            "Q2o5U7/aeLkF3wRej13hegHVoLuP3QCE5STcnpT0ajAMqa4ObqC3n6R6hWGUCPl5ZjtGeNLghtVE5pswlpc8/8" +
            "Z4GwJEE65mmAwo6tfS54FIpfVq7qLKF3rByohYrwwKZ1mQM6STF1t8IsbeXrBEtfCQN5fSX2wLMPSJE34Iz0Ig" +
            "VlSAVbJfdxKkX8JONhqeAOWseLUkG+fI+Da71V4eMzfHarfuN7Nltbd+3zNE7DIwFQs5/PDIotbVVpYrpS4wiH" +
            "1lNPNxWLSyv/ArSyCyNi9Ygi5W/UqIlQ+7Mweg6f16d6nZpMN1Ejv1o0s7L4L0aXny";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the Tensor Flow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    /**
     * Initialize the Vuforia localization engine.
     */
    protected void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
    protected void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }

    protected void knockSample() {
        sampleServo.setPosition(1);
        sleep(500);
        sampleServo.setPosition(.7);
    }

    protected void sample() {
        tfod.activate();

        final char goldPosition = findGoldPosition();

        tfod.shutdown();

        if (goldPosition == 'R') {
            moveInch(14,.55);
            knockSample();
        } else if (goldPosition == 'C') {
            moveInch(26,.55);
            knockSample();
            moveInch(2,.55);
        } else if (goldPosition == 'L') {
            moveInch(40,.55);
            knockSample();
        } else {
            telemetry.addData("tensor","failed");
            telemetry.update();
            moveInch(SAMPLE_TO_NEXT_STOP_INCHES,.55);
            turn(-45,.35);
        }
    }

    private char findGoldPosition() {
        for (int x = 0; x < 40000000; x++) {
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                telemetry.addData("# Object Detected", updatedRecognitions.size());
                if (updatedRecognitions.size() == 3) {
                    int goldMineralX = -1;
                    int silverMineral1X = -1;
                    int silverMineral2X = -1;
                    for (Recognition recognition : updatedRecognitions) {
                        if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                            goldMineralX = (int) recognition.getLeft();
                        } else if (silverMineral1X == -1) {
                            silverMineral1X = (int) recognition.getLeft();
                        } else {
                            silverMineral2X = (int) recognition.getLeft();
                        }
                    }
                    if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                        telemetry.addData("X", x);
                        telemetry.update();
                        if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                            telemetry.addData("Gold Mineral Position", "Left");
                            return 'L';
                        } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                            telemetry.addData("Gold Mineral Position", "Right");
                            return 'R';
                        } else {
                            telemetry.addData("Gold Mineral Position", "Center");
                            return 'C';
                        }
                    }
                }
                telemetry.update();
            }
        }
        return 'F';
    }
}