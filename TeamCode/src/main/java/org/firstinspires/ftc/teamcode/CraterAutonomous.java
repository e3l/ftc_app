package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class CraterAutonomous extends MainAutonomous {


    public void runOpMode() {
        //lower the robot from the lander
        lower();

        //move to the crater sample and get in position
        turn(LANDER_TO_NS_WALL_DEGREES);
        moveInch(LANDER_TO_SAMPLE_START_INCHES);
        turn(NS_WALL_TO_SAMPLE_START_DEGREES);

        //sample
        sample();

        //move to the other sample
        turn(SAMPLE_END_TO_PARALLEL_WALL_DEGREES);
        moveInch(SAMPLE_END_TO_SAMPLE_START_INCHES);
        turn(PARALLEL_WALL_TO_SAMPLE_START_DEGREES);

        //sample again
        sample();

        //go to depot to place team market
        turn(TURN_AROUND);
        moveInch(SAMPLE_END_TO_EW_WALL_CENTER_INCHES);
        turn(EW_WALL_CENTER_TO_DEPOT_DEGREES_CA);
        moveInch(EW_WALL_CENTER_TO_DEPOT_INCHES);
        //TODO: team marker

        //go and partially park in crater
        turn(TURN_AROUND);
        moveInch(DEPOT_TO_CRATER_INCHES);
        jointPosition("extended");





    }
}
