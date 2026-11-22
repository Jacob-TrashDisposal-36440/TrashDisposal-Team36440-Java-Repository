
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagClusterDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

@Autonomous(name = "webcam", group = "Vision")
public class webcam extends LinearOpMode {

    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;

    @Override
    public void runOpMode() {
        //imu = hardwareMap.get(IMU.class, "imu");
        aprilTag = new AprilTagProcessor.Builder().build();
        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam"))
                .addProcessor(aprilTag)
                .build();

        telemetry.addLine("Camera Initialized");
        telemetry.addLine("IMU Initialized");

        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            List<AprilTagDetection> detections =
                    aprilTag.getDetections();

            int clusterCount = 0;

            telemetry.addData("Total Detections", detections.size());

            for (AprilTagDetection detection : detections) {

                if (detection instanceof AprilTagClusterDetection) {

                    clusterCount++;

                    AprilTagClusterDetection cluster =
                            (AprilTagClusterDetection) detection;

                    telemetry.addLine("========================");
                    if (cluster.metadata != null) {
                        telemetry.addData("Cluster Name",
                                cluster.metadata.name);

                        telemetry.addData("Short Name",
                                cluster.metadata.shortName);
                    } else {
                        telemetry.addLine("Cluster: Unknown");
                    }

                    telemetry.addData("Tags Visible",
                            cluster.percentClusterFound + "%");

                    boolean isRightSideUp =
                            Math.abs(cluster.ftcPose.roll) < 90.0;

                    telemetry.addData("Right-Side Up",
                            isRightSideUp);

                    telemetry.addLine("--- Position ---");

                    telemetry.addData("X (in)",
                            "%.2f", cluster.ftcPose.x);

                    telemetry.addData("Y (in)",
                            "%.2f", cluster.ftcPose.y);

                    telemetry.addData("Z (in)",
                            "%.2f", cluster.ftcPose.z);

                    telemetry.addLine("--- Rotation ---");

                    telemetry.addData("Pitch (deg)",
                            "%.2f", cluster.ftcPose.pitch);

                    telemetry.addData("Roll (deg)",
                            "%.2f", cluster.ftcPose.roll);

                    telemetry.addData("Yaw (deg)",
                            "%.2f", cluster.ftcPose.yaw);

                    telemetry.addLine("--- Camera Position ---");

                    telemetry.addData("Range (in)",
                            "%.2f", cluster.ftcPose.range);

                    telemetry.addData("Bearing (deg)",
                            "%.2f", cluster.ftcPose.bearing);

                    telemetry.addData("Elevation (deg)",
                            "%.2f", cluster.ftcPose.elevation);
                }
            }

            if (clusterCount == 0) {
                telemetry.addLine("No AprilTag Clusters in view");
            }

            telemetry.addData("Clusters Detected", clusterCount);

            telemetry.update();

            sleep(20);
        }

        visionPortal.close();
    }
}
