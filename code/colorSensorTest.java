package org.firstinspires.ftc.teamcode;

import android.graphics.Color;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;

@TeleOp(name = "colorSensorTest", group = "Sensors")
public class colorSensorTest extends LinearOpMode {

    private NormalizedColorSensor colorSensor;

    @Override
    public void runOpMode() {
        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "colorSensor");

        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight) colorSensor).enableLight(true);
        }

        float[] hsvValues = new float[3];

        waitForStart();

        while (opModeIsActive()) {
            NormalizedRGBA colors = colorSensor.getNormalizedColors();

            Color.RGBToHSV(
                (int) (colors.red * 255),
                (int) (colors.green * 255),
                (int) (colors.blue * 255),
                hsvValues
            );

            float hue = hsvValues[0];
            float saturation = hsvValues[1];
            float value = hsvValues[2];
            String detectedColor = " ";
            if (saturation > 0.4 && value > 0.05) {
                if (hue < 30 || hue >= 330) {
                    detectedColor = "RED";
                } else if (hue >= 30 && hue < 90) {
                    detectedColor = "YELLOW";
                } else if (hue >= 90 && hue < 180) {
                    detectedColor = "GREEN";
                } else if (hue >= 180 && hue < 270) {
                    detectedColor = "BLUE";
                }
            }

            telemetry.addData("Detected Color", detectedColor);
            telemetry.addData("Hue", "%.1f", hue);
            telemetry.addData("Saturation", "%.2f", saturation);
            telemetry.addData("Value (Brightness)", "%.2f", value);
            telemetry.update();
        }
    }
}
