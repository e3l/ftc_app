package org.firstinspires.ftc.teamcode;

public class CraterAutonomous extends MainAutonomous {


    public void runOpMode() {
        //lower the robot from the lander
        lower();

        //move to the crater sample and get in position
        turn(LANDER_TO_NS_WALL_DEGREES);
        moveInch(LANDER_TO_SAMPLE_START_INCHES);
        turn(NS_WALL_TO_SAMPLE_START_DEGREES);

        //TODO: do the sample stuff

        //move to the other sample
        turn(SAMPLE_END_TO_PARALLEL_WALL_DEGREES);
        moveInch(SAMPLE_END_TO_SAMPLE_START_INCHES);
        turn(PARALLEL_WALL_TO_SAMPLE_START_DEGREES);

        //TODO: do the sample stuff 2.0

        //go to depot to place team market
        turn(TURN_AROUND);
        moveInch(SAMPLE_END_TO_EW_WALL_CENTER_INCHES);
        turn(EW_WALL_CENTER_TO_DEPOT_DEGREES);
        moveInch(EW_WALL_CENTER_TO_DEPOT_INCHES);
        //TODO: team marker

        //go and partially park in crater
        turn(TURN_AROUND);
        moveInch(DEPOT_TO_CRATER_INCHES);
        jointPosition("extended");





    }
}
