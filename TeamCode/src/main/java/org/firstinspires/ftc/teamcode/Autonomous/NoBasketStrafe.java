package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
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
@Autonomous(name = "NoBasketStrafe", group = "Autonomous")
public class NoBasketStrafe extends LinearOpMode {
    DcMotor rightFront, leftFront, leftBack, rightBack, grabExtend, grabPivot, pullExtend, pullPivot;
    Servo wrist, grabby, twisty;

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
        grabby = hardwareMap.get(Servo.class, "grabby");                        // Control Hub 1
        twisty = hardwareMap.get(Servo.class, "twisty");                        // Control Hub 2

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
        grabPivotGrab = 920;
        grabPivotDown = 580;
        grabPivotScore = 2500;
        grabExtendIn = 100;
        grabExtendMid = 1080;
        grabExtendOut = 2100;
        wristRest = 0.05;
        wristGrab = 0.25;
        wristParallel = 0.6;
        wristScore = 0.8;
        grabbyOpen = 0.54;
        grabbyClosed = 0.455;
        twistyParallel = 0.46;
        twistyPerpendicular = 0.15;

        grabArmPosition = "rest";
        twisty.setPosition(twistyPerpendicular);
        grabby.setPosition(grabbyClosed);

        rest();


        Pose2d initialPose = new Pose2d(-14, 61.7, Math.PI * 1.5);
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);


        TrajectoryActionBuilder tab1 = drive.actionBuilder(initialPose)
                .splineToLinearHeading(new Pose2d(-55, 60, Math.PI * 1.5), Math.PI * 1);

        // actions that need to happen on init; for instance, a claw tightening.
        //  Actions.runBlocking(claw.closeClaw());

        waitForStart();

        retract();

        if (isStopRequested()) return;

        Action trajectoryActionChosen;

        trajectoryActionChosen = tab1.build();

        Actions.runBlocking(new SequentialAction(trajectoryActionChosen));
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
    private void retract() {
        switchToAuto();

        grabPivot.setPower(1.0);
        grabExtend.setPower(1.0);

        wrist.setPosition(wristParallel);

        wait(1.0);

        grabPivot.setTargetPosition(grabPivotGrab - 100);

        waitForMotors();

        wrist.setPosition(wristParallel);
        grabExtend.setTargetPosition(grabExtendIn);
    }

    public void wait(double time) {
        double initTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - initTime < time * 1000);
    }
}