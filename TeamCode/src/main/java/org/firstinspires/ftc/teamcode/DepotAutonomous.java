package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class DepotAutonomous extends MainAutonomous {

    public void runOpMode() {
        initOpMode();
        initVuforia();
        initTfod();
        waitForStart();

        //lower the robot from the lander
        lower();

        //move to the crater sample and get in position
        turn(LANDER_TO_SAMPLE_START_DEGREES, 0.35);
        moveInch(LANDER_TO_SAMPLE_START_INCHES, 0.55);
        turn(TURN_TO_SAMPLE_DEGREES + 10, 0.35);
        moveInch(-38,.55);

        //do the sample stuff
        sample();

        //move to marker
        turn(-90, 0.35);
        moveInch(40, 0.55);
        turn(-130,.35);

        marker();

        moveInch(DEPOT_TO_CRATER_INCHES, 0.55);
        jointExtend();
    }
}