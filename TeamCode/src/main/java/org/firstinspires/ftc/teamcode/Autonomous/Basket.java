package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Config
@Autonomous(name = "Basket", group = "Autonomous")
public class Basket extends LinearOpMode {


    DcMotor rightFront, leftFront, leftBack, rightBack, grabExtend, grabPivot, pullExtend, pullPivot;
    Servo wrist;
    CRServo wheel1, wheel2;

    String grabArmPosition;
    int speedIndex;
    float[] speed;


    // Predefined positions of the motors:
    int pullPivotRest;
    int pullPivotHang;
    int pullExtendIn;
    int pullExtendOut;
    int grabPivotRest;
    int grabPivotGrab;
    int grabPivotDown;
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
        wheel1 = hardwareMap.get(CRServo.class, "wheel1");                      // Control Hub 1
        wheel2 = hardwareMap.get(CRServo.class, "wheel2");                      // Control Hub 2

        driverMode = false;
        isTwistyParallel = false;
        isGrabbyOpen = false;
        isHoldingGrabExtend = false;
        isHoldingGrabPivot = false;

        // Predefined motor positions:
        pullPivotRest = -2400;
        pullPivotHang = -1160;
        pullExtendIn = -100;
        pullExtendOut = -7777;
        grabPivotRest = 1610;
        grabPivotGrab = 580; // was 920
        grabPivotDown = 100; // was 580
        grabPivotScore = 2800;
        grabExtendIn = 100;
        grabExtendMid = 1080;
        grabExtendOut = 2100;
        wristRest = 0.15;
        wristGrab = 0.7;
        wristParallel = 0.6;
        wristScore = 0.65;
        grabbyOpen = 0.54;
        grabbyClosed = 0.455;
        twistyParallel = 0.46;
        twistyPerpendicular = 0.15;

        grabArmPosition = "rest";

        rest();


        Pose2d initialPose = new Pose2d(33, 61.7, Math.PI * 1.5);
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        Pose2d pose1 = new Pose2d(47.5, 54.5, Math.PI * 1.25);
        TrajectoryActionBuilder tab1 = drive.actionBuilder(initialPose)
                .splineToLinearHeading(pose1, Math.PI * 0.25);
        // Score preloaded block

        Pose2d pose2 = new Pose2d(44.25, 43.5, Math.PI * 1.48);
        TrajectoryActionBuilder tab2 = drive.actionBuilder(pose1)
                .setTangent(Math.PI * 1.25)
                .splineToLinearHeading(pose2, Math.PI * 1.5);
        // Grab block 1

        Pose2d pose3 = new Pose2d(48.5, 52.5, Math.PI * 1.25);
        TrajectoryActionBuilder tab3 = drive.actionBuilder(pose2)
                .setTangent(Math.PI * 0.25)
                .splineToLinearHeading(pose3, Math.PI * 0.25);
        // Score block 1

        Pose2d pose4 = new Pose2d(55.5, 43.5, Math.PI * 1.56);
        TrajectoryActionBuilder tab4 = drive.actionBuilder(pose3)
                .setTangent(Math.PI * 1.5)
                .splineToLinearHeading(pose4, Math.PI * 1.5);
        // Grab block 2
 
        Pose2d pose5 = new Pose2d(47, 54, Math.PI * 1.25);
        TrajectoryActionBuilder tab5 = drive.actionBuilder(pose4)
                .setTangent(Math.PI * 0.6)
                .splineToLinearHeading(pose5, Math.PI * 0.6);
        // Score block 2

        Pose2d pose6 = new Pose2d(62, 44, Math.PI * 1.52);
        TrajectoryActionBuilder tab6 = drive.slowerActionBuilder(pose5)
                .setTangent(Math.PI * 1.5)
                .splineToLinearHeading(pose6, Math.PI * 1.5);
        // Grab block 3

        Pose2d pose7 = new Pose2d(47, 55, Math.PI * 1.25);
        TrajectoryActionBuilder tab7 = drive.actionBuilder(pose6)
                .setTangent(Math.PI * 0.75)
                .splineToLinearHeading(pose7, Math.PI * 0.25);
        // Score block 3

        Pose2d pose8 = new Pose2d(26, 12, Math.PI * 1);
        TrajectoryActionBuilder tab8 = drive.actionBuilder(pose7)
                .setTangent(Math.PI * 1.5)
                .splineToLinearHeading(pose8, Math.PI * 1);

        waitForStart();

        if (isStopRequested()) return;

        // Scores preloaded block
        score();
        Actions.runBlocking(new SequentialAction(tab1.build()));
        wrist.setPosition(wristScore);
        wait(0.1);
        dispense();
        wrist.setPosition(wristParallel);
        wait(0.1);

        // Drives to and grabs block 1
        grab();
        Actions.runBlocking(new SequentialAction(tab2.build()));
        wrist.setPosition(wristGrab);
        grabPivot.setTargetPosition(grabPivotDown);
        wait(0.5);
        capture();

        // Scores block 1
        score();
        Actions.runBlocking(new SequentialAction(tab3.build()));
        wrist.setPosition(wristScore);
        wait(0.1);
        dispense();
        wrist.setPosition(wristParallel);
        wait(0.1);

        // Drives to and grabs block 2
        grab();
        Actions.runBlocking(new SequentialAction(tab4.build()));
        wrist.setPosition(wristGrab);
        grabPivot.setTargetPosition(grabPivotDown);
        wait(0.5);
        capture();
        wait(0.1);

        // Scores block 2
        score();
        Actions.runBlocking(new SequentialAction(tab5.build()));
        wrist.setPosition(wristScore);
        wait(0.1);
        dispense();
        wrist.setPosition(wristParallel);
        wait(0.1);

        // Drives to and grabs block 3
        grab();
        Actions.runBlocking(new SequentialAction(tab6.build()));
        wrist.setPosition(wristGrab);

        grabPivot.setTargetPosition(grabPivotDown);
        wheel1.setPower(-1.0);
        wheel2.setPower(1.0);
        wait(0.5);
        capture();
        grabPivot.setTargetPosition(grabPivotScore);
        wait(0.2);
        wrist.setPosition(wristGrab - 0.1);


        // Scores block 3
        score();
        Actions.runBlocking(new SequentialAction(tab7.build()));
        wrist.setPosition(wristScore);
        wait(0.1);
        dispense();
        wrist.setPosition(wristParallel);
        wait(0.1);

        // Parks in position for TeleOp

        grabPivot.setTargetPosition(1575);
        grabExtend.setTargetPosition(800);
        wrist.setPosition(wristParallel);
        Actions.runBlocking(new SequentialAction(tab8.build()));
    }

    private void score() {
        switchToAuto();
        grabPivot.setPower(1.0);
        grabExtend.setPower(1.0);

        grabExtend.setTargetPosition(200);
        grabPivot.setTargetPosition(grabPivotScore);
        waitForMotors(); // grabPivot 150
        wrist.setPosition(wristParallel);
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
        pullExtend.setTargetPosition(pullExtendIn);
        grabPivot.setTargetPosition(grabPivotRest);
        grabExtend.setTargetPosition(grabExtendIn);

        waitForMotors();

        wrist.setPosition(wristRest);
    }

    public void grab() {
        switchToAuto();

        grabPivot.setPower(0.6);
        grabExtend.setPower(1.0);

        grabPivot.setTargetPosition(grabPivotGrab);
        wait(0.1);
        grabExtend.setTargetPosition(grabExtendIn);
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

    public void capture() {
        wheel1.setPower(-1.0);
        wheel2.setPower(1.0);
        wait(0.4);
        wheel1.setPower(0);
        wheel2.setPower(0);
    }

    public void dispense() {
        // because the robot flips the arm around, the dispense is actually the same direction as the capture :P
        wheel1.setPower(-1.0);
        wheel2.setPower(1.0);
        wait(0.75);
        wheel1.setPower(0);
        wheel2.setPower(0);
    }

    public void wait(double time) {
        double initTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - initTime < time * 1000);
    }
}