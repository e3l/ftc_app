package org.firstinspires.ftc.teamcode;

public class CraterAutonomous extends MainAutonomous {


    public void runOpMode() {
        //lower the robot from the lander
        lower();

        //move to the crater sample and get in position
        turn(LANDER_TO_WALL_DEGREES);
        moveInch(LANDER_TO_SAMPLE_START_INCHES);
        turn(WALL_TO_SAMPLE_START_DEGREES);

        //TODO: do the sample stuff

        //move to the other sample


    }
}
