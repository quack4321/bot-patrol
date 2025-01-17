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
@Autonomous(name = "Bucket", group = "Autonomous")
public class Bucket extends LinearOpMode {
    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(34, 61.7, Math.PI * 1.5);
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);


        TrajectoryActionBuilder tab1 = drive.actionBuilder(initialPose)
                .splineToLinearHeading(new Pose2d(55, 55, Math.PI * 1.25), Math.PI * 0.25)
                .setTangent(Math.PI * 1.25)
                .splineToLinearHeading(new Pose2d(37, 30, Math.PI * 0), Math.PI * 1.25)
                .setTangent(Math.PI * 1.5)
                .splineToLinearHeading(new Pose2d(37, 24, Math.PI * 0), Math.PI * 1.5)
                .setTangent(Math.PI * 0.25)
                .splineToLinearHeading(new Pose2d(55, 55, Math.PI * 1.25), Math.PI * 0.25)
                .setTangent(Math.PI * 1.25)
                .splineToLinearHeading(new Pose2d(45, 24, Math.PI * 0), Math.PI * 1.5)
                .setTangent(Math.PI * 0.25)
                .splineToLinearHeading(new Pose2d(55, 55, Math.PI * 1.25), Math.PI * 0.25)
                .setTangent(Math.PI * 1.25)
                .splineToLinearHeading(new Pose2d(55, 24, Math.PI * 0), Math.PI * 1.5)
                .setTangent(Math.PI * 0.25)
                .splineToLinearHeading(new Pose2d(55, 55, Math.PI * 1.25), Math.PI * 0.25)
                .setTangent(Math.PI * 1.5)
                .splineToLinearHeading(new Pose2d(26, 12, Math.PI * 1), Math.PI * 1);

        // actions that need to happen on init; for instance, a claw tightening.
        //  Actions.runBlocking(claw.closeClaw());

        waitForStart();

        if (isStopRequested()) return;

        Action trajectoryActionChosen;

        trajectoryActionChosen = tab1.build();

        Actions.runBlocking(new SequentialAction(trajectoryActionChosen));
    }
}