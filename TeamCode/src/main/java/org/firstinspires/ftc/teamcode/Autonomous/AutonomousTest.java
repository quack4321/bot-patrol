package org.firstinspires.ftc.teamcode.Autonomous;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.teamcode.MecanumDrive;

import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
@Autonomous
public class AutonomousTest extends LinearOpMode {

        DcMotor pullPivot;
        DcMotor grabPivot;
        DcMotor pullExtend;
        DcMotor grabExtend;

        public void initMotors() {
            grabExtend = hardwareMap.get(DcMotor.class, "grabExtend");
            grabPivot = hardwareMap.get(DcMotor.class, "grabPivot");
            pullPivot = hardwareMap.get(DcMotor.class, "pullPivot");
            pullExtend = hardwareMap.get(DcMotor.class, "pullExtend");

            pullExtend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            grabExtend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            pullPivot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            grabPivot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            pullPivot.setTargetPosition(pullPivot.getCurrentPosition());
            grabPivot.setTargetPosition(grabPivot.getCurrentPosition());
            pullExtend.setTargetPosition(pullExtend.getCurrentPosition());
            grabExtend.setTargetPosition(grabExtend.getCurrentPosition());

            pullPivot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            grabPivot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            pullExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            grabExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            pullExtend.setPower(1.0);
            grabExtend.setPower(1.0);
            pullPivot.setPower(1.0);
            grabPivot.setPower(1.0);
        }

    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(11.8, 61.7, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        initMotors();

//        Actions.runBlocking(
//        );

        pullPivot.setTargetPosition(5075);

    }
}