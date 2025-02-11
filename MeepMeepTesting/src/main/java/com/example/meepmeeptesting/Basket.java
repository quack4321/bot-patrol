package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class Basket {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(720);
        Pose2d initialPose = new Pose2d(33, 61.7, Math.PI * 1.5);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();



//        TrajectoryActionBuilder tab4 = drive.actionBuilder(new Pose2d(54, 59, Math.PI * 1.25))
//                .setTangent(Math.PI * 1.5)
//                .splineToLinearHeading(new Pose2d(53.75, 44, Math.PI * 1.5), Math.PI * 1.5);
//        // Grab block 2
//
//        TrajectoryActionBuilder tab5 = drive.actionBuilder(new Pose2d(53.75, 44, Math.PI * 1.5))
//                .setTangent(Math.PI * 0.6)
//                .splineToLinearHeading(new Pose2d(53, 58, Math.PI * 1.25), Math.PI * 0.6);
//        // Drop block 2
//
//        TrajectoryActionBuilder tab6 = drive.slowerActionBuilder(new Pose2d(53, 58, Math.PI * 1.25))
//                .setTangent(Math.PI * 1.5)
//                .splineToLinearHeading(new Pose2d(54.25, 37.5, Math.PI * 1.75), Math.PI * 1.75);
//        // Grab block 3
//
//        TrajectoryActionBuilder tab7 = drive.actionBuilder(new Pose2d(54.25, 37.5, Math.PI * 1.75))
//                .setTangent(Math.PI * 0.5)
//                .splineToLinearHeading(new Pose2d(54, 58, Math.PI * 1.25), Math.PI * 0.5);
//        // Drop block 3
//
//        TrajectoryActionBuilder tab8 = drive.actionBuilder(new Pose2d(54, 58, Math.PI * 1.25))
//                .setTangent(Math.PI * 1.5)
//                .splineToLinearHeading(new Pose2d(26, 12, Math.PI * 1), Math.PI * 1);

        myBot.runAction(myBot.getDrive().actionBuilder(initialPose)
                .splineToLinearHeading(new Pose2d(54.5, 58, Math.PI * 1.25), Math.PI * 0.25)
                //drop block
                .setTangent(Math.PI * 1.25)
                .splineToLinearHeading(new Pose2d(43.25, 44, Math.PI * 1.5), Math.PI * 1.5)
                //grab block
                .setTangent(Math.PI * 0.25)
                .splineToLinearHeading(new Pose2d(54, 59, Math.PI * 1.25), Math.PI * 0.25)
                //drop block
                .setTangent(Math.PI * 1.5)
                .splineToLinearHeading(new Pose2d(54, 39, Math.PI * 1.5), Math.PI * 1.5)
                //grab block
                .setTangent(Math.PI * 0.6)
                .splineToLinearHeading(new Pose2d(52, 52, Math.PI * 1.33), Math.PI * 0.6)
                //drop block
                .setTangent(Math.PI * 1.5)
                .splineToLinearHeading(new Pose2d(57, 36 , Math.PI * 1.75), Math.PI * 1.75)
                //grab block
                .setTangent(Math.PI * 0.5)
                .splineToLinearHeading(new Pose2d(52, 52, Math.PI * 1.33), Math.PI * 0.5)
                //drop block
                .setTangent(Math.PI * 1.5)
                .splineToLinearHeading(new Pose2d(26, 12, Math.PI * 1), Math.PI * 1)
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}