package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Config
@Autonomous(name = "Basket", group = "Autonomous")
public class Basket extends LinearOpMode {


    DcMotor rightFront, leftFront, leftBack, rightBack, grabExtend, grabPivot, pullExtend, pullPivot;
    Servo wrist, grabby, twisty;

    String grabArmPosition;
    int speedIndex;
    float[] speed;


    // Predefined positions of the motors:
    int pullPivotRest;
    int pullPivotPull;
    int pullExtendIn;
    int pullExtendOut;
    int grabPivotRest;
    int grabPivotGrab;
    int grabPivotGrabFar;
    int grabPivotScore;
    int grabExtendIn;
    int grabExtendMid;
    int grabExtendOut;
    double wristRest;
    double wristGrab;
    double wristParallel;
    double wristScore;
    double grabbyOpen;
    double grabbyClosed;
    double twistyParallel;
    double twistyPerpendicular;
    long preTime;
    boolean driverMode;
    boolean isWaitingForMotors;
    boolean motorsBusy;
    boolean isResting;
    boolean isScoring;
    boolean isGrabbing;
    boolean isHanging;
    boolean isTwistyParallel;
    boolean isGrabbyOpen;
    boolean isHoldingGrabExtend;
    boolean isHoldingGrabPivot;
    double initTime;

    @Override
    public void runOpMode() {
        // Initialize hardware values
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");              // Control Hub 0
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");                // Control Hub 1
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");                // Control Hub 2
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");                  // Control Hub 3

        grabExtend = hardwareMap.get(DcMotor.class, "grabExtend");              // Expansion Hub 0
        grabPivot = hardwareMap.get(DcMotor.class, "grabPivot");                // Expansion Hub 1
        pullPivot = hardwareMap.get(DcMotor.class, "pullPivot");                // Expansion Hub 2
        pullExtend = hardwareMap.get(DcMotor.class, "pullExtend");              // Expansion Hub 3

        wrist = hardwareMap.get(Servo.class, "wrist");                          // Control Hub 0
        grabby = hardwareMap.get(Servo.class, "grabby");                        // Control Hub 1
        twisty = hardwareMap.get(Servo.class, "twisty");                        // Control Hub 2

        driverMode = false;
        isTwistyParallel = false;
        isGrabbyOpen = false;
        isHoldingGrabExtend = false;
        isHoldingGrabPivot = false;

        // Predefined motor positions:
        pullPivotRest = 5075;
        pullPivotPull = 6000;
        pullExtendIn = 0;
        pullExtendOut = 6000;
        grabPivotRest = 1700;
        grabPivotGrab = 920;
        grabPivotScore = 2500;
        grabExtendIn = 100;
        grabExtendMid = 1080;
        grabExtendOut = 2100;

        wristRest = 0;
        wristGrab = 0.2;
        wristParallel = 0.6;
        wristScore = 0.8;
        grabbyOpen = 0.54;
        grabbyClosed = 0.46;
        twistyParallel = 0.46;
        twistyPerpendicular = 0.15;

        grabArmPosition = "rest";
        twisty.setPosition(twistyPerpendicular);
        grabby.setPosition(grabbyClosed);

        switchToAuto();
        rest();


        Pose2d initialPose = new Pose2d(33, 61.7, Math.PI * 1.5);
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);


        TrajectoryActionBuilder tab1 = drive.actionBuilder(initialPose)
                .splineToLinearHeading(new Pose2d(49, 57, Math.PI * 1.33), Math.PI * 0.25);
        // Drop block

        TrajectoryActionBuilder tab2 = drive.actionBuilder(new Pose2d(49, 57, Math.PI * 1.33))
                .setTangent(Math.PI * 1.25)
                .splineToLinearHeading(new Pose2d(45, 37, Math.PI * 1.5), Math.PI * 1.5);
        // Grab block

        TrajectoryActionBuilder tab3 = drive.actionBuilder(new Pose2d(45, 37, Math.PI * 1.5))
                .setTangent(Math.PI * 0.25)
                .splineToLinearHeading(new Pose2d(49, 57, Math.PI * 1.33), Math.PI * 0.25);
        // Drop block

        TrajectoryActionBuilder tab4 = drive.actionBuilder(new Pose2d(49, 57, Math.PI * 1.33))
                .setTangent(Math.PI * 1.5)
                .splineToLinearHeading(new Pose2d(54, 37, Math.PI * 1.5), Math.PI * 1.5);
        // Grab block
        TrajectoryActionBuilder tab5 = drive.actionBuilder(new Pose2d(54, 37, Math.PI * 1.5))
                .setTangent(Math.PI * 0.6)
                .splineToLinearHeading(new Pose2d(49, 57, Math.PI * 1.33), Math.PI * 0.6);
        // Drop block

        TrajectoryActionBuilder tab6 = drive.actionBuilder(new Pose2d(49, 57, Math.PI * 1.33))
                .setTangent(Math.PI * 1.5)
                .splineToLinearHeading(new Pose2d(50, 22, Math.PI * 0), Math.PI * 1.5);
        // Grab block

        TrajectoryActionBuilder tab7 = drive.actionBuilder(new Pose2d(50, 22, Math.PI * 0))
                .setTangent(Math.PI * 0.5)
                .splineToLinearHeading(new Pose2d(49, 57, Math.PI * 1.33), Math.PI * 0.5);
        // Drop block

        TrajectoryActionBuilder tab8 = drive.actionBuilder(new Pose2d(49, 57, Math.PI * 1.33))
                .setTangent(Math.PI * 1.5)
                .splineToLinearHeading(new Pose2d(26, 12, Math.PI * 1), Math.PI * 1);

        waitForStart();

        if (isStopRequested()) return;

        // Scores preloaded block
        score();
        Actions.runBlocking(new SequentialAction(tab1.build()));
        wait(1.0);
        grabby.setPosition(grabbyOpen);
        wait(0.5);

        // Drives to and grabs block 1
        grab();
        Actions.runBlocking(new SequentialAction(tab2.build()));
        grabPivot.setTargetPosition(grabPivot.getCurrentPosition() - 300);
        wait(0.5);
        grabby.setPosition(grabbyClosed);
        wait(0.5);

        // Scores block 1
        score();
        Actions.runBlocking(new SequentialAction(tab3.build()));
        wait(1.0);
        grabby.setPosition(grabbyOpen);
        wait(0.5);

        // Drives to and grabs block 2
        grab();
        Actions.runBlocking(new SequentialAction(tab4.build()));
        grabPivot.setTargetPosition(grabPivot.getCurrentPosition() - 300);
        wait(0.5);
        grabby.setPosition(grabbyClosed);
        wait(0.5);

        // Scores block 2
        score();
        Actions.runBlocking(new SequentialAction(tab5.build()));
        wait(1.0);
        grabby.setPosition(grabbyOpen);
        wait(0.5);

        // Drives to and grabs block 3
        grab();
        twisty.setPosition(twistyPerpendicular);
        Actions.runBlocking(new SequentialAction(tab6.build()));
        grabPivot.setTargetPosition(grabPivot.getCurrentPosition() - 300);
        wait(0.5);
        grabby.setPosition(grabbyClosed);
        wait(0.5);

        // Scores block 3
        score();
        Actions.runBlocking(new SequentialAction(tab7.build()));
        wait(1.0);
        grabby.setPosition(grabbyOpen);
        wait(0.5);
        // Parks in position for TeleOp
        grab();
        Actions.runBlocking(new SequentialAction(tab8.build()));
    }

    private void score() {
        switchToAuto();
        grabPivot.setPower(1.0);
        grabExtend.setPower(1.0);

        grabExtend.setTargetPosition(200);
        wrist.setPosition(wristParallel);
        grabPivot.setTargetPosition(grabPivotScore);
        waitForMotors();
        twisty.setPosition(twistyParallel);
        wrist.setPosition(wristScore);
        grabExtend.setTargetPosition(grabExtendOut);
    }

    public void rest() {
        switchToAuto();

        pullPivot.setPower(0.5);
        pullExtend.setPower(0.5);
        grabPivot.setPower(0.2);
        grabExtend.setPower(0.5);

        wrist.setPosition(wristParallel);
        pullPivot.setTargetPosition(pullPivotRest);
        pullExtend.setTargetPosition(0);
        grabPivot.setTargetPosition(grabPivotRest);
        grabExtend.setTargetPosition(grabExtendIn);

        waitForMotors();

        wrist.setPosition(wristRest);
    }

    public void grab() {
        switchToAuto();

        wrist.setPosition(wristParallel);
        grabby.setPosition(grabbyOpen);

        grabPivot.setPower(0.5);
        grabExtend.setPower(1.0);

        grabPivot.setTargetPosition(grabPivotGrab);
        grabExtend.setTargetPosition(grabExtendIn);

        waitForMotors();

        wrist.setPosition(wristGrab);
    }

    private void switchToAuto() {
        pullPivot.setTargetPosition(pullPivot.getCurrentPosition());
        grabPivot.setTargetPosition(grabPivot.getCurrentPosition());
        pullExtend.setTargetPosition(pullExtend.getCurrentPosition());
        grabExtend.setTargetPosition(grabExtend.getCurrentPosition());

        pullPivot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        grabPivot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        pullExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        grabExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        grabPivot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        grabExtend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        pullPivot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        pullExtend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void waitForMotors() {
        double initTime = System.currentTimeMillis();
        boolean motorsBusy = true;
        while (motorsBusy) {
            // Check if all motors have reached their target positions
            motorsBusy = Math.abs(pullPivot.getCurrentPosition() - pullPivot.getTargetPosition()) > 64 ||
                    Math.abs(pullExtend.getCurrentPosition() - pullExtend.getTargetPosition()) > 64 ||
                    Math.abs(grabPivot.getCurrentPosition() - grabPivot.getTargetPosition()) > 64 ||
                    Math.abs(grabExtend.getCurrentPosition() - grabExtend.getTargetPosition()) > 64;

            if (System.currentTimeMillis() - initTime > 3000) {
                break;
            }

            if (isStopRequested()) {
                break;
            }
        }
    }

    public void wait(double time) {
        double initTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - initTime < time * 1000);
    }
}