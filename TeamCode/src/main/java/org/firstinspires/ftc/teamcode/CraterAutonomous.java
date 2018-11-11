package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class CraterAutonomous extends MainAutonomous {

    public void runOpMode() {
        //lower the robot from the lander
        lower();

        //move to the crater sample and get in position
        turn(LANDER_TO_NS_WALL_DEGREES, .15);
        moveInch(LANDER_TO_SAMPLE_START_INCHES,.35);
        turn(NS_WALL_TO_SAMPLE_START_DEGREES, .15);

        // Knock the sample
        sample();

        //move to the other sample
        turn(SAMPLE_END_TO_PARALLEL_WALL_DEGREES, .15);
        moveInch(SAMPLE_END_TO_SAMPLE_START_INCHES,.35);
        turn(PARALLEL_WALL_TO_SAMPLE_START_DEGREES, .15);

        // Knock the sample
        sample();

        //go to depot to place team market
        turn(TURN_AROUND, .15);
        moveInch(SAMPLE_END_TO_EW_WALL_CENTER_INCHES,.35);
        turn(EW_WALL_CENTER_TO_DEPOT_DEGREES, .15);
        moveInch(EW_WALL_CENTER_TO_DEPOT_INCHES,.35);

        //TODO: team marker

        //go and partially park in crater
        turn(TURN_AROUND, .15);
        moveInch(DEPOT_TO_CRATER_INCHES,.35);
        jointPosition("extended");
    }
}
