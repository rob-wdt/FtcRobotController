package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "TeleOp-Test")
public class ArmAndHandMovingTeleOp extends LinearOpMode {

    private final static double SERVO_90_DEGREES = 0.5;
    private final static double SERVO_0_DEGREES = 0.0;
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

        // run until the end of the match (driver presses STOP)
        double tgtPower = 0;
        while (opModeIsActive()) {
            tgtPower = -this.gamepad1.left_stick_y;
            arm.setPower(tgtPower);
            telemetry.addData("Target Power: ", tgtPower);
            telemetry.addData("Motor  Power: ", arm.getPower());

            // check to see if we need to move the servo
            if(gamepad1.y) {
                // move to 90 degrees
                pitch.setPosition(SERVO_90_DEGREES);
            } else if (gamepad1.x) {
                // move to 90 degrees
                hand.setPosition(SERVO_90_DEGREES);
            } else if (gamepad1.b) {
                // move to 0 degrees.
                hand.setPosition(SERVO_0_DEGREES);
            } else if (gamepad1.a) {
                // move to 0 degrees.
                pitch.setPosition(SERVO_0_DEGREES);
            }
            telemetry.addData("Servo Position (pitch): ", pitch.getPosition());
            telemetry.addData("Servo Position  (hand): ", hand.getPosition());
            telemetry.addData("Status: ", "Running");
            telemetry.update();
        }
    }
}
