/*
a. Assignment 01a.
b. MainActivity.java.
c. Himanshu Kiran Garud, Srushti Sanjay Samant (Group 26).
*/

package com.example.conversionapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText inputTemperature;
    private TextView outputConversion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputTemperature = findViewById(R.id.inputTemperature);
        outputConversion = findViewById(R.id.outputConversion);

        Button buttonCtoF = findViewById(R.id.buttonCtoF);
        buttonCtoF.setBackgroundColor(Color.GRAY);
        Button buttonFtoC = findViewById(R.id.buttonFtoC);
        buttonFtoC.setBackgroundColor(Color.GRAY);
        Button buttonReset = findViewById(R.id.buttonReset);
        buttonReset.setBackgroundColor(Color.GRAY);

        buttonCtoF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertCtoF();
            }
        });

        buttonFtoC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertFtoC();
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
            }
        });
    }

    private void convertCtoF() {
        String input = inputTemperature.getText().toString();
        if (input.isEmpty()) {
            Toast.makeText(this,"Please enter a valid temperature.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!input.matches("^[0-9]*\\.?[0-9]+$")) {
            Toast.makeText(this,"Please enter a valid temperature.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double celsius = Double.parseDouble(input);
            double fahrenheit = (celsius * 9/5) + 32;
            outputConversion.setText(String.format("%.2f F", fahrenheit));
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input. Please enter a numeric value.", Toast.LENGTH_SHORT).show();
        }
    }

    private void convertFtoC() {
        String input = inputTemperature.getText().toString();
        if (input.isEmpty()) {

            Toast.makeText(this, "Please enter a valid temperature.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!input.matches("^[0-9]*\\.?[0-9]+$")) {
            Toast.makeText(this,"Please enter a valid temperature.", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            double fahrenheit = Double.parseDouble(input);
            double celsius = (fahrenheit - 32) * 5/9;
            outputConversion.setText(String.format("%.2f C", celsius));
        } catch (NumberFormatException e) {

            Toast.makeText(this, "Invalid input. Please enter a numeric value.", Toast.LENGTH_SHORT).show();

        }
    }

    private void resetFields() {
        inputTemperature.setText("");
        outputConversion.setText("");
    }
}
