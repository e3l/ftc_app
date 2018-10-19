package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class craterAutonomous extends MainAutonomous {
    public void runOpMode() {
        initOpMode();

        lower();

        // move to crater sample
        turn(-125);
        moveInch(0 ); // power is .35
        turn(-125);

        // sample();

        turn(125);
        moveInch(0); // power is .35





    }





}
