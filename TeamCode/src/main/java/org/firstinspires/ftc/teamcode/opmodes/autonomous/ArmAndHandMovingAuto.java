package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "Auto-Test")
public class ArmAndHandMovingAuto extends LinearOpMode {

    private final static double SERVO_90_DEGREES = 0.5;
    private final static double SERVO_0_DEGREES = 0.0;
    private final static double DC_MOTOR_POWER_VALUE = 0.5;
    private final static int DC_MOTOR_ENCODER_TARGET_POSITION = 500;
    private final static long TIME_TO_SLEEP = 100;
    private DcMotor arm;
    private Servo pitch; // наклон
    private Servo hand; // клешня (рука)

    @Override
    public void runOpMode() throws InterruptedException {
        arm = hardwareMap.get(DcMotor.class, "arm");
        pitch = hardwareMap.get(Servo.class, "pitch");
        hand = hardwareMap.get(Servo.class, "hand");

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        telemetry.addData("Status: ", "Running");
        telemetry.update();

        moveArm(DC_MOTOR_ENCODER_TARGET_POSITION, DC_MOTOR_POWER_VALUE);
        moveHand(SERVO_90_DEGREES, SERVO_90_DEGREES);

        moveHand(SERVO_0_DEGREES, SERVO_0_DEGREES);
        moveArm(DC_MOTOR_ENCODER_TARGET_POSITION, -DC_MOTOR_POWER_VALUE);

        telemetry.addData("Status: ", "Stop");
        telemetry.update();
    }

    private void moveArm(int encoderTargetPosition, double powerValue) {
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setTargetPosition(encoderTargetPosition);
        arm.setPower(powerValue);
        telemetry.addData("DcMotor Power: ", arm.getPower());
        telemetry.addData("DcMotor Encoder Target Position: ", arm.getTargetPosition());
        telemetry.update();

        while (arm.getCurrentPosition() < arm.getTargetPosition()) {
            telemetry.addData("DcMotor Encoder Current Position: ", arm.getCurrentPosition());
            telemetry.update();
            try {
                Thread.sleep(TIME_TO_SLEEP);
            } catch (Exception e) {
                telemetry.addData("Error: ", "Thread sleep");
                telemetry.update();
            }
        }

        arm.setPower(0);
        telemetry.addData("DcMotor Power: ", arm.getPower());
        telemetry.update();
    }

    private void moveHand(double pitchPosition, double handPosition) {
        pitch.setPosition(pitchPosition);
        hand.setPosition(handPosition);
        telemetry.addData("Servo Position (pitch): ", pitch.getPosition());
        telemetry.addData("Servo Position  (hand): ", hand.getPosition());
        telemetry.update();
    }
}
