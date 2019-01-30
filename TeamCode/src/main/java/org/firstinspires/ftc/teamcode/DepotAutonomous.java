package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class DepotAutonomous extends MainAutonomous {

    public void runOpMode() {
        initOpMode();
        waitForStart();

        //lower the robot from the lander
        lower();

        //move to the crater sample and get in position
        turn(-LANDER_TO_SAMPLE_START_DEGREES, 0.15);
        moveInch(LANDER_TO_SAMPLE_START_INCHES, 0.35);
        turn(-TURN_TO_SAMPLE_DEGREES, 0.15);

        //do the sample stuff
        sample();

        //move to the other sample
        turn(TURN_TO_SAMPLE_DEGREES, 0.15);
        moveInch(36, 0.35);
        turn(90, 0.15);

        marker();

        //go and partially park in crater
        turn(90, 0.15);
        moveInch(DEPOT_TO_CRATER_INCHES, 0.45);
        jointExtend();
    }
}
