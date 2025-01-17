package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Config
@Autonomous(name = "Reset", group = "Autonomous")
public class Reset extends LinearOpMode {
    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(26, 12, Math.PI * 1);
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);


        TrajectoryActionBuilder tab1 = drive.actionBuilder(initialPose)
                .setTangent(Math.PI * 0)
                .splineToLinearHeading(new Pose2d(33, 61.7, Math.PI * 1.5), Math.PI * 0.5);

        // actions that need to happen on init; for instance, a claw tightening.
        //  Actions.runBlocking(claw.closeClaw());

        waitForStart();

        if (isStopRequested()) return;

        Action trajectoryActionChosen;

        trajectoryActionChosen = tab1.build();

        Actions.runBlocking(new SequentialAction(trajectoryActionChosen));
    }
}