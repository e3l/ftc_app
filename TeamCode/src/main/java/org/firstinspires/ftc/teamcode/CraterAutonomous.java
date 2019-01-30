package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous
public class CraterAutonomous extends MainAutonomous {

    public void runOpMode() {
        initOpMode();
        initVuforia();
        initTfod();
        waitForStart();

        //lower the robot from the lander
        lower();

        //move to the crater sample and get in position
        turn(LANDER_TO_SAMPLE_START_DEGREES, .25);
        moveInch(LANDER_TO_SAMPLE_START_INCHES,.45);
        turn(TURN_TO_SAMPLE_DEGREES, .25);
        moveInch(-38,.45);

        // Knock the sample
        sample();

//        //move to the other sample
//        turn(-SAMPLE_END_TO_PARALLEL_WALL_DEGREES, .15);
//        moveInch(SAMPLE_END_TO_SAMPLE_START_INCHES,.35);
//        turn(PARALLEL_WALL_TO_SAMPLE_START_DEGREES, .15);
//
//        //go to depot to place team market
//        turn(-180, .15);
//        moveInch(SAMPLE_END_TO_DEPOT,.35);
//
//        marker();

        //go and partially park in crater
        turn(-105,.35);
        moveInch(10,.35);
        jointExtend();
    }
}
