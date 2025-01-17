package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Trajectory;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Config
@Autonomous(name = "Basket", group = "Autonomous")
public class Basket extends LinearOpMode {


    private DcMotorEx grabPivot;
    private DcMotorEx grabExtend;
    private DcMotorEx pullPivot;
    private DcMotorEx pullExtend;
    private Servo wrist;
    private CRServo spinny;

    private int pullPivotStart;
    private int pullPivotRest;
    private int pullPivotPull;
    private int pullPivotClimb;
    private int pullExtendIn;
    private int pullExtendOut;
    private int grabPivotRest;
    private int grabPivotGrab;
    private int grabPivotScore;
    private int grabExtendIn;
    private int grabExtendMid;
    private int grabExtendOut;
    private double wristRest;
    private double wristGrab;
    private double wristParallel;
    private double wristScore;

    @Override
    public void runOpMode() {
        grabPivot = hardwareMap.get(DcMotorEx.class, "grabPivot");
        grabExtend = hardwareMap.get(DcMotorEx.class, "grabExtend");
        pullPivot = hardwareMap.get(DcMotorEx.class, "pullPivot");
        pullExtend = hardwareMap.get(DcMotorEx.class, "pullExtend");
        wrist = hardwareMap.get(Servo.class, "wrist");
        spinny = hardwareMap.get(CRServo.class, "spinny");

        useEncoders();

        pullPivotStart = 6300;
        pullPivotRest = 5075;
        pullPivotPull = 0;
        pullPivotClimb = 0;
        pullExtendIn = 0;
        pullExtendOut = 0;
        grabPivotRest = 1700;
        grabPivotGrab = 600;
        grabPivotScore = 2500;
        grabExtendIn = 0;
        grabExtendMid = 1080;
        grabExtendOut = 2100;
        wristRest = 0.05;
        wristGrab = 0.5;
        wristParallel = 0.6;
        wristScore = 0.8;

        rest();


        Pose2d initialPose = new Pose2d(33, 61.7, Math.PI * 1.5);
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);


        TrajectoryActionBuilder tab1 = drive.actionBuilder(initialPose)
                .splineToLinearHeading(new Pose2d(53, 53, Math.PI * 1.33), Math.PI * 0.25);

        TrajectoryActionBuilder tab2 = drive.actionBuilder(new Pose2d(53, 53, Math.PI * 1.33))
                .setTangent(Math.PI * 1.25)
                .splineToLinearHeading(new Pose2d(37, 30, Math.PI * 0), Math.PI * 1.25);

        TrajectoryActionBuilder tab3 = drive.actionBuilder(new Pose2d(37, 30, Math.PI * 0))
                .setTangent(Math.PI * 1.5)
                .splineToLinearHeading(new Pose2d(37, 22, Math.PI * 0), Math.PI * 1.5)
                .setTangent(Math.PI * 1)
                .splineToLinearHeading(new Pose2d(34, 22, Math.PI * 0), Math.PI * 1);

        TrajectoryActionBuilder tab4 = drive.actionBuilder(new Pose2d(34, 22, Math.PI * 0))
                .setTangent(Math.PI * 0.25)
                .splineToLinearHeading(new Pose2d(53, 53, Math.PI * 1.33), Math.PI * 0.25);

        TrajectoryActionBuilder tab5 = drive.actionBuilder(new Pose2d(53, 53, Math.PI * 1.33))
                .setTangent(Math.PI * 1.25)
                .splineToLinearHeading(new Pose2d(42.5, 22, Math.PI * 0), Math.PI * 1.5);

        TrajectoryActionBuilder tab6 = drive.actionBuilder(new Pose2d(42.5, 22, Math.PI * 0))
                .setTangent(Math.PI * 0.25)
                .splineToLinearHeading(new Pose2d(53, 53, Math.PI * 1.33), Math.PI * 0.25);

        TrajectoryActionBuilder tab7 = drive.actionBuilder(new Pose2d(53, 53, Math.PI * 1.33))
                .setTangent(Math.PI * 1.25)
                .splineToLinearHeading(new Pose2d(53, 22, Math.PI * 0), Math.PI * 1.5);

        TrajectoryActionBuilder tab8 = drive.actionBuilder(new Pose2d(53, 22, Math.PI * 0))
                .setTangent(Math.PI * 0.25)
                .splineToLinearHeading(new Pose2d(53, 53, Math.PI * 1.33), Math.PI * 0.25);

        TrajectoryActionBuilder tab9 = drive.actionBuilder(new Pose2d(53, 53, Math.PI * 1.33))
                .setTangent(Math.PI * 1.5)
                .splineToLinearHeading(new Pose2d(26, 12, Math.PI * 1), Math.PI * 1);

        // actions that need to happen on init; for instance, a claw tightening.
        //  Actions.runBlocking(claw.closeClaw());

        waitForStart();

        if (isStopRequested()) return;

        score(); // Put arm at score position
        Actions.runBlocking(new SequentialAction(tab1.build())); // Drive to basket
        drop(); // Drop preloaded element
        wrist.setPosition(wristParallel); // Get wrist out of the way
        grabClose(); // Begins moving arm to grab position
        Actions.runBlocking(new SequentialAction(tab2.build())); // Moves to position for next element
        suck();
        Actions.runBlocking(new SequentialAction(tab3.build())); // Strafes to position for next element
        grabPivot.setTargetPosition(grabPivotGrab - 500);
        wait(0.5);
        wrist.setPosition(wristGrab + 0.1);
        wait(1.0);
        spinny.setPower(0);
        score();
        Actions.runBlocking(new SequentialAction(tab4.build())); // Drive to basket
        drop();
        wrist.setPosition(wristParallel); // Get wrist out of the way
        grabClose(); // Begins moving arm to grab position
        Actions.runBlocking(new SequentialAction(tab5.build())); // Drive to element
        suck();
        wait(0.5);
        grabPivot.setTargetPosition(grabPivotGrab - 500);
        wait(0.75);
        wrist.setPosition(wristGrab + 0.1);
        wait(1.75);
        spinny.setPower(0);
        score();
        Actions.runBlocking(new SequentialAction(tab6.build())); // Drive to basket
        drop();
        wrist.setPosition(wristParallel); // Get wrist out of the way
        grabClose(); // Begins moving arm to grab position
        Actions.runBlocking(new SequentialAction(tab7.build())); // Drive to block
        suck();
        wait(0.5);
        grabPivot.setTargetPosition(grabPivotGrab - 500);
        wait(0.75);
        wrist.setPosition(wristGrab + 0.1);
        wait(1.75);
        spinny.setPower(0);
//        score();
//        Actions.runBlocking(new SequentialAction(tab8.build())); // Drive to basket
//        drop();
//        Actions.runBlocking(new SequentialAction(tab9.build())); // Park
//        rest();
    }

    private void score() {
        useEncoders();
        grabPivot.setPower(1.0);
        grabExtend.setPower(1.0);

        grabExtend.setTargetPosition(200);
        wrist.setPosition(wristParallel);
        grabPivot.setTargetPosition(grabPivotScore);
        waitForMotors();
        wrist.setPosition(wristScore);
        grabExtend.setTargetPosition(grabExtendOut);
    }

    private void drop() {
        waitForMotors();
        spinny.setPower(1.0);
        wait(1.0);
        spinny.setPower(0);
    }

    public void rest() {
        pullPivot.setPower(0.5);
        pullExtend.setPower(0.5);
        grabPivot.setPower(0.2);
        grabExtend.setPower(0.5);

        wrist.setPosition(wristParallel);
        pullPivot.setTargetPosition(pullPivotRest);
        pullExtend.setTargetPosition(0);
        grabPivot.setTargetPosition(grabPivotRest);
        grabExtend.setTargetPosition(grabExtendIn);


    }

    private void useEncoders() {

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
        while (pullPivot.getCurrentPosition() < pullPivot.getTargetPosition() - 20 ||
                pullPivot.getCurrentPosition() > pullPivot.getTargetPosition() + 20 ||
                pullExtend.getCurrentPosition() < pullExtend.getTargetPosition() - 20 ||
                pullExtend.getCurrentPosition() > pullExtend.getTargetPosition() + 20
                || grabPivot.getCurrentPosition() < grabPivot.getTargetPosition() - 20 ||
                grabPivot.getCurrentPosition() > grabPivot.getTargetPosition() + 20 ||
                grabExtend.getCurrentPosition() < grabExtend.getTargetPosition() - 20 ||
                grabExtend.getCurrentPosition() > grabExtend.getTargetPosition() + 20) ;
    }

    public void grabClose() {
        grabPivot.setPower(0.2);
        grabExtend.setPower(0.4);

        grabPivot.setTargetPosition(grabPivotGrab - 100);
        grabExtend.setTargetPosition(grabExtendIn);
    }

    public void suck() {
        //waitForMotors();
        wrist.setPosition(wristGrab - 0.1);
        spinny.setPower(-1.0);
    }

    public void wait(double time) {
        double initTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - initTime < time * 1000) ;
    }
}