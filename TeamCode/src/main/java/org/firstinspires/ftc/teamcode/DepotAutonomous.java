package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class DepotAutonomous extends MainAutonomous {

    public void runOpMode() {

        //lower the robot from the lander
        lower();

        //move to the crater sample and get in position
        turn(-LANDER_TO_NS_WALL_DEGREES, 0.4);
        moveInch(LANDER_TO_SAMPLE_START_INCHES, 0.4);
        turn(-NS_WALL_TO_SAMPLE_START_DEGREES, 0.4);

        //do the sample stuff
        sample();

        //move to the other sample
        turn(-SAMPLE_END_TO_PARALLEL_WALL_DEGREES, 0.4);
        moveInch(SAMPLE_END_TO_SAMPLE_START_INCHES, 0.4);
        turn(-PARALLEL_WALL_TO_SAMPLE_START_DEGREES, 0.4);

        //do the sample stuff 2.0
        sample();

        //go to depot to place team market
        turn(TURN_AROUND, 0.4);
        moveInch(SAMPLE_END_TO_EW_WALL_CENTER_INCHES, 0.4);
        turn(EW_WALL_CENTER_TO_DEPOT_DEGREES_DA, 0.4);
        moveInch(EW_WALL_CENTER_TO_DEPOT_INCHES, 0.4);


        //go and partially park in crater
        turn(TURN_AROUND, 0.4);
        moveInch(DEPOT_TO_CRATER_INCHES, 0.4);
        jointPosition("extended");
    }
}
