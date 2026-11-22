package org.firstinspires.ftc.teamcode;

import android.util.Size;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

@Autonomous(name = "AprilTag Test")
public class cameraTest extends LinearOpMode {

    @Override
    public void runOpMode() {
        DcMotor leftHex = hardwareMap.get(DcMotor.class, "leftHex");
        DcMotor rightHex = hardwareMap.get(DcMotor.class, "rightHex");
        WebcamName Webcam = hardwareMap.get(WebcamName.class, "Webcam");
        AprilTagProcessor aprilTag = new AprilTagProcessor.Builder().build();
        VisionPortal visionPortal = new VisionPortal.Builder()
                .setCamera(Webcam)
                .setCameraResolution(new Size(640, 480))
                .addProcessor(aprilTag)
                .build();

        double DESIRED_DISTANCE = 12.0;
        double TURN_SPEED = 0.02;
        double DRIVE_SPEED = 0.03;

        waitForStart();

        while (opModeIsActive()) {

            List<AprilTagDetection> detections = aprilTag.getDetections();

            telemetry.addData("Tags Detected", detections.size());

            if (!detections.isEmpty()) {
                AprilTagDetection tag = detections.get(0);
                if (tag.ftcPose != null) {
                    double distanceError = tag.ftcPose.range - DESIRED_DISTANCE;

                    double bearingError = tag.ftcPose.bearing;
                    double drive = distanceError * DRIVE_SPEED;
                    double turn = bearingError * TURN_SPEED;

                    leftHex.setPower(
                            Range.clip(drive + turn, -1.0, 1.0)
                    );

                    rightHex.setPower(
                            Range.clip(drive - turn, -1.0, 1.0)
                    );

                    telemetry.addData("Distance (in)", tag.ftcPose.range);
                    telemetry.addData("Bearing (deg)", tag.ftcPose.bearing);

                } else {

                    leftHex.setPower(0);
                    rightHex.setPower(0);
                }

            } else {

                leftHex.setPower(0);
                rightHex.setPower(0);
            }

            telemetry.update();
        }

        leftHex.setPower(0);
        rightHex.setPower(0);

        visionPortal.close();
    }
}
