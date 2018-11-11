package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class DepotAutonomous extends MainAutonomous {
    
    public void runOpMode() {
        // lower the robot from the lander
        lower();

        // move to the crater sample and get in position
        turn(LANDER_TO_NS_WALL_DEGREES, .15);
        moveInch(LANDER_TO_SAMPLE_START_INCHES,.35);
        turn(NS_WALL_TO_SAMPLE_START_DEGREES, .15);

        // Knock the sample
        sample();

        //go to depot to place team marker
        moveInch(NSAMPLE_TO_NWALL_INCHES,.35);
        turn(90, .15);
        moveInch(60,.35);
        //TODO: team marker

        //move to the other sample
        turn(90, .15);
        moveInch(DEPOT_TO_SSAMPLE_INCHES,.5);
        turn(-SAMPLE_END_TO_PARALLEL_WALL_DEGREES,.15);

        // Knock the sample
        sample();

        // go and partially park in crater (Spot closest to depot)
        moveInch(-SAMPLE_LENGTH, .35);
        turn(SAMPLE_END_TO_PARALLEL_WALL_DEGREES,.15);
        moveInch(12,.35);
        jointPosition("extended");
    }
}
