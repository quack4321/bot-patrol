package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class Auto {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(720);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        Pose2d initialPose = new Pose2d(-7.5, 61.7, Math.PI * 1.5);
        Pose2d pose1 = new Pose2d(-7.5, 36, Math.PI * 1.5);
        Pose2d pose2 = new Pose2d(-36, 30, Math.PI * 0.5);
        Pose2d pose3 = new Pose2d(-45, 15, Math.PI * 0.5);
        Pose2d pose4 = new Pose2d(-45, 55, Math.PI * 0.5);
        Pose2d pose5 = new Pose2d(-45, 15, Math.PI * 0.5);
        Pose2d pose6 = new Pose2d(-53, 55, Math.PI * 0.5);
        Pose2d pose7 = new Pose2d(-5, pose1.position.y, Math.PI * 1.5);


        myBot.runAction(myBot.getDrive().actionBuilder(initialPose)
                // tab1
                .setTangent(Math.PI * 1.5)
                .splineToLinearHeading(pose1, Math.PI * 1.5)

                // tab2
                .setTangent(Math.PI * 0.5)
                .splineToConstantHeading(pose2.position, Math.PI * 1.5)

                // tab3
                .setTangent(Math.PI * 1.5)
                .splineToLinearHeading(pose3, Math.PI * 1.0)
                .setTangent(Math.PI * 1.5)
                .splineToLinearHeading(pose3, Math.PI * 0.5)
                .setTangent(Math.PI * 0.5)
                .splineToLinearHeading(pose4, Math.PI * 0.5)
                .setTangent(Math.PI * 1.5)
                .splineToLinearHeading(pose5, Math.PI * 1.5)
                .setTangent(Math.PI * 1.0)
                .splineToLinearHeading(pose6, Math.PI * 0.5)
                .setTangent(Math.PI * 1.75)
                .splineToLinearHeading(pose7, Math.PI * 1.5)

                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}