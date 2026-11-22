package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name = "Servo Test")
public class SpinTest extends LinearOpMode {

    private CRServo clawServo;
    private DcMotor leftHex,rightHex;
    private DcMotor intake;


    @Override
    public void runOpMode() {
        // "clawServo" must match the name you gave it in Configure Robot
        intake = hardwareMap.get(DcMotor.class, "intake");

        waitForStart();

        while (opModeIsActive()) {
            intake.setPower(0.5);
            telemetry.addData("Motor Power", leftHex.getPower());
            telemetry.update();

            
            // full one direction (0.0–1.0 range)
            //sleep(1000);
            //clawServo.setPosition(0.0);  // full other direction
            //sleep(1000);
        }
    }
}
