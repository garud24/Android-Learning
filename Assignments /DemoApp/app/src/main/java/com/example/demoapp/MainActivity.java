// Group 26
// Srushti Sanjay Samant and Himanshu Kiran Garud
// MainActivity.java file
// Assignment 02

package com.example.demoapp;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView hexValue, rgbValue, redValue, greenValue, blueValue;
    private SeekBar seekBarRed, seekBarGreen, seekBarBlue;
    private Button buttonWhite, buttonBlack, buttonBlue, buttonReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hexValue = findViewById(R.id.hexValue);
        rgbValue = findViewById(R.id.rgbValue);
        redValue = findViewById(R.id.redValue);
        greenValue = findViewById(R.id.greenValue);
        blueValue = findViewById(R.id.blueValue);
        seekBarRed = findViewById(R.id.seekBarRed);
        seekBarGreen = findViewById(R.id.seekBarGreen);
        seekBarBlue = findViewById(R.id.seekBarBlue);
        buttonWhite = findViewById(R.id.buttonWhite);
        buttonBlack = findViewById(R.id.buttonBlack);
        buttonBlue = findViewById(R.id.buttonBlue);
        buttonReset = findViewById(R.id.buttonReset);

        updateColorView();

        seekBarRed.setOnSeekBarChangeListener(colorSeekBarChangeListener);
        seekBarGreen.setOnSeekBarChangeListener(colorSeekBarChangeListener);
        seekBarBlue.setOnSeekBarChangeListener(colorSeekBarChangeListener);

        buttonWhite.setOnClickListener(v -> setColor(255, 255, 255));
        buttonBlack.setOnClickListener(v -> setColor(0, 0, 0));
        buttonBlue.setOnClickListener(v -> setColor(0, 0, 255));
        buttonReset.setOnClickListener(v -> resetColor());
    }

    private final SeekBar.OnSeekBarChangeListener colorSeekBarChangeListener =
            new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    updateColorView();
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
            };

    private void updateColorView() {
        int red = seekBarRed.getProgress();
        int green = seekBarGreen.getProgress();
        int blue = seekBarBlue.getProgress();

        int color = Color.rgb(red, green, blue);
        findViewById(R.id.colorView).setBackgroundColor(color);

        String hex = String.format("#%06X", (0xFFFFFF & color));
        String rgb = "Color RGB: (" + red + ", " + green + ", " + blue + ")";

        hexValue.setText("Color HEX: " + hex);
        rgbValue.setText(rgb);

        redValue.setText(String.valueOf(red));
        greenValue.setText(String.valueOf(green));
        blueValue.setText(String.valueOf(blue));
    }

    private void setColor(int red, int green, int blue) {
        seekBarRed.setProgress(red);
        seekBarGreen.setProgress(green);
        seekBarBlue.setProgress(blue);
        updateColorView();
    }

    private void resetColor() {
        setColor(64, 128, 0);
    }
}
