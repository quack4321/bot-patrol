/*
Copyright 2023 FIRST Tech Challenge Team 9505

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute,
sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp

public class Arm extends OpMode {
    /* Declare OpMode members. */

    DcMotor grabExtend;
    DcMotor grabPivot;
    DcMotor pullPivot;
    DcMotor pullExtend;

    Servo wrist;
    CRServo wheel1;
    CRServo wheel2;

    // Predefined positions of the motors:
    String grabArmPosition;
    int pullPivotRest;
    int pullPivotHang;
    int pullExtendIn;
    int pullExtendOut;
    int grabPivotRest;
    int grabPivotGrab;
    int grabPivotScore;
    int grabExtendIn;
    int grabExtendMid;
    int grabExtendOut;
    double wristRest;
    double wristGrab;
    double wristParallel;
    double wristScore;
    double wristHang;
    long preTime;
    boolean driverMode;

    // Controller 1 Variables:
    boolean rightBumperLastTime;
    boolean leftBumperLastTime;
    boolean aLastTime;
    boolean yLastTime;
    boolean xLastTime;
    boolean bLastTime;
    boolean dpadUpLastTime;
    boolean dpadDownLastTime;
    boolean dpadLeftLastTime;
    boolean dpadRightLastTime;
    boolean rightStickButtonLastTime;
    boolean leftStickButtonLastTime;

    // Controller 2 Variables:
    boolean rightBumperLastTime2;
    boolean leftBumperLastTime2;
    boolean aLastTime2;
    boolean yLastTime2;
    boolean xLastTime2;
    boolean bLastTime2;
    private boolean dpadUpLastTime2;
    boolean dpadDownLastTime2;
    boolean dpadLeftLastTime2;
    boolean dpadRightLastTime2;
    boolean rightStickButtonLastTime2;
    boolean leftStickButtonLastTime2;

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");


        // Initialize hardware values

        grabExtend = hardwareMap.get(DcMotor.class, "grabExtend");              // Expansion Hub 0
        grabPivot = hardwareMap.get(DcMotor.class, "grabPivot");                // Expansion Hub 1
        pullPivot = hardwareMap.get(DcMotor.class, "pullPivot");                // Expansion Hub 2
        pullExtend = hardwareMap.get(DcMotor.class, "pullExtend");              // Expansion Hub 3

        wrist = hardwareMap.get(Servo.class, "wrist");                          // Control Hub 0
        wheel1 = hardwareMap.get(CRServo.class, "wheel1");                      // Control Hub 1
        wheel2 = hardwareMap.get(CRServo.class, "wheel2");                      // Control Hub 2

        // Set motors to brake upon zero power:

        pullExtend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        grabExtend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        pullPivot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        grabPivot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        driverMode = false;

        // Predefined motor positions:
        pullPivotHang = -1450;
        pullPivotRest = -2400;
        pullExtendIn = -100;
        pullExtendOut = -7777;
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
        wristHang = 1.0;

        grabArmPosition = "start";

    }


    @Override
    public void start() {
        preTime = System.currentTimeMillis();
        switchToDriver();
    }


    @Override
    public void loop() {
        // Displays info on Driver Hub:

        telemetry.addData("Wrist Position", wrist.getPosition());

        telemetry.addData("grabExtend Target Position: ", grabExtend.getTargetPosition());
        telemetry.addData("pullExtend Target Position: ", pullExtend.getTargetPosition());
        telemetry.addData("grabPivot Target Position: ", grabPivot.getTargetPosition());
        telemetry.addData("pullPivot Target Position: ", pullPivot.getTargetPosition());

        telemetry.addData("grabExtend Current Position: ", grabExtend.getCurrentPosition());
        telemetry.addData("pullExtend Current Position: ", pullExtend.getCurrentPosition());
        telemetry.addData("grabPivot Current Position: ", grabPivot.getCurrentPosition());
        telemetry.addData("pullPivot Current Position: ", pullPivot.getCurrentPosition());


        pullExtend.setPower(gamepad1.left_stick_y * 0.5 + gamepad2.left_stick_y * 0.5);
        pullPivot.setPower(gamepad1.left_stick_x * 0.5 + gamepad2.left_stick_x * 0.5);
        grabExtend.setPower(gamepad1.right_stick_y * 0.5 + gamepad2.right_stick_y * 0.5);
        grabPivot.setPower(gamepad1.right_stick_x * 0.5 + gamepad2.right_stick_x * 0.5);


        if (gamepad1.x && !xLastTime) {
            wrist.getController().pwmDisable();
        }

        if (gamepad1.y && !yLastTime) {
            wrist.getController().pwmEnable();
            wrist.setPosition(wristParallel);
        }

        if (gamepad1.dpad_up && !dpadUpLastTime) {
            wrist.setPosition(wrist.getPosition() + 0.05);
        }

        if (gamepad1.dpad_down && !dpadDownLastTime) {
            wrist.setPosition(wrist.getPosition() - 0.05);
        }


        if (gamepad1.left_stick_button && !leftStickButtonLastTime) {
            resetPositions();
        }

        if (gamepad1.right_stick_button && !rightStickButtonLastTime) {
            resetPositions();
        }

        if (gamepad2.left_stick_button && !leftStickButtonLastTime2) {
            resetPositions();
        }

        if (gamepad2.right_stick_button && !rightStickButtonLastTime2) {
            resetPositions();
        }

        aLastTime = gamepad1.a;
        bLastTime = gamepad1.b;
        xLastTime = gamepad1.x;
        yLastTime = gamepad1.y;
        dpadUpLastTime = gamepad1.dpad_up;
        dpadDownLastTime = gamepad1.dpad_down;
        dpadLeftLastTime = gamepad1.dpad_left;
        dpadRightLastTime = gamepad1.dpad_right;
        leftBumperLastTime = gamepad1.left_bumper;
        rightBumperLastTime = gamepad1.right_bumper;
        leftStickButtonLastTime = gamepad1.left_stick_button;
        rightStickButtonLastTime = gamepad1.right_stick_button;

        aLastTime2 = gamepad2.a;
        bLastTime2 = gamepad2.b;
        xLastTime2 = gamepad2.x;
        yLastTime2 = gamepad2.y;
        dpadUpLastTime2 = gamepad2.dpad_up;
        dpadDownLastTime2 = gamepad2.dpad_down;
        dpadLeftLastTime2 = gamepad2.dpad_left;
        dpadRightLastTime2 = gamepad2.dpad_right;
        leftBumperLastTime2 = gamepad2.left_bumper;
        rightBumperLastTime2 = gamepad2.right_bumper;
        leftStickButtonLastTime2 = gamepad2.left_stick_button;
        rightStickButtonLastTime2 = gamepad2.right_stick_button;
    }


    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {

    }

    public void switchToDriver() {
        pullPivot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        grabPivot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pullExtend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        grabExtend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        driverMode = true;
    }

    private void resetPositions() {
        grabExtend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pullExtend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        grabPivot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pullPivot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        grabExtend.setTargetPosition(grabExtend.getCurrentPosition());
        pullExtend.setTargetPosition(grabExtend.getCurrentPosition());
        grabPivot.setTargetPosition(grabExtend.getCurrentPosition());
        pullPivot.setTargetPosition(grabExtend.getCurrentPosition());

        grabExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        pullExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        grabPivot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        pullPivot.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        grabPivot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        grabExtend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        pullPivot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        pullExtend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
}
