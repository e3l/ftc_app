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
        turn(LANDER_TO_SAMPLE_START_DEGREES, 1);
        moveInch(LANDER_TO_SAMPLE_START_INCHES + 1, 0.55);
        turn(TURN_TO_SAMPLE_DEGREES, 0.35);
        moveInch(-41,.55);

        //do the sample stuff
        sample();

        if (goldPosition == 'R') {
            turn(-80,.35);
            moveInch(41, 0.55);
            turn(-150,.35);
        } else if (goldPosition == 'C') {
            turn(-90, 0.35);
            moveInch(43, 0.55);
            turn(-140,.35);
        } else if (goldPosition == 'L') {
            moveInch(10,.55);
            turn(-120,.35);
            moveInch(48, 0.55);
            turn(-105,.35);
        }

        marker();

        moveInch(DEPOT_TO_CRATER_INCHES, 0.75);
        jointExtend();
    }
}