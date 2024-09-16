/*
a. Assignment 01a.
b. MainActivity.java.
c. Himanshu Kiran Garud, Srushti Sanjay Samant (Group 26).
*/

package com.example.conversionapp2;

import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTemperature;
    private TextView textViewConversion;
    private RadioGroup radioGroupConversion;
    private RadioButton radioButtonCtoF, radioButtonFtoC;
    private Button buttonReset, buttonCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTemperature = findViewById(R.id.inputTemperature);
        textViewConversion = findViewById(R.id.outputConversion);
        radioGroupConversion = findViewById(R.id.radioGroupConversion);
        radioButtonCtoF = findViewById(R.id.radioButtonCtoF);
        radioButtonFtoC = findViewById(R.id.radioButtonFtoC);
        buttonReset = findViewById(R.id.buttonReset);
        buttonReset.setBackgroundColor(Color.GRAY);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        buttonCalculate.setBackgroundColor(Color.GRAY);

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateConversion();
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
            }
        });
    }

    private void calculateConversion() {
        String tempStr = editTextTemperature.getText().toString();
        if (TextUtils.isEmpty(tempStr)) {
            Toast.makeText(this, R.string.error_invalid_input, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!tempStr.matches("^[0-9]*\\.?[0-9]+$")) {
            Toast.makeText(this, R.string.error_invalid_input, Toast.LENGTH_SHORT).show();
            return;
        }
        double temperature = Double.parseDouble(tempStr);
        double convertedTemperature = 0.0;

        int checkedId = radioGroupConversion.getCheckedRadioButtonId();

        if (checkedId == R.id.radioButtonCtoF) {
            convertedTemperature = (temperature * 9/5) + 32;
            textViewConversion.setText(String.format("%.2f F", convertedTemperature));
        } else if (checkedId == R.id.radioButtonFtoC) {
            convertedTemperature = (temperature - 32) * 5/9;
            textViewConversion.setText(String.format("%.2f C", convertedTemperature));
        } else {
            Toast.makeText(this, R.string.error_invalid_input, Toast.LENGTH_SHORT).show();
        }
    }

    private void resetFields() {
        editTextTemperature.setText("");
        textViewConversion.setText(R.string.conversionHint);
        radioGroupConversion.clearCheck();
    }
}
