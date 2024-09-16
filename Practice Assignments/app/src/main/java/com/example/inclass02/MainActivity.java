package com.example.inclass02;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText editTextNumberDecimal2;
    TextView textViewDiscountedPercentage;
    TextView textViewDiscountedPrice;

    public static final String TAG = "Demo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextNumberDecimal2 = findViewById(R.id.editTextNumberDecimal2);
        textViewDiscountedPercentage =findViewById(R.id.textViewDiscountedPercentage);
        textViewDiscountedPrice =  findViewById(R.id.textViewDiscountedPrice);

        findViewById(R.id.buttonClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextNumberDecimal2.setText("");
                textViewDiscountedPercentage.setText("");
                textViewDiscountedPrice.setText("");
            }
        });

        findViewById(R.id.button5Percent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
                String enteredAmount = editTextNumberDecimal2.getText().toString();


                double amount = 0;
                try {
                    amount = Double.valueOf(enteredAmount);

                    double discountedPrice = 0.95 * amount;
                    textViewDiscountedPercentage.setText("5%");
                    textViewDiscountedPrice.setText(String.valueOf(discountedPrice));
                } catch (NumberFormatException exception) {
                    Log.d(TAG, "Invalid Number");
                }

                Log.d(TAG, "onClick: " + amount);
                //get the ticket price

                // discount is 5%

                //calculate 5%
            }
        });

        findViewById(R.id.button10Percent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredAmount = editTextNumberDecimal2.getText().toString();
                double amount = 0;
                try {
                    amount = Double.valueOf(enteredAmount);
                    double discountedPrice = 0.90 * amount;

                    textViewDiscountedPercentage.setText("10%");
                    textViewDiscountedPrice.setText(String.valueOf(discountedPrice));

                }catch (NumberFormatException exception){
                    Log.d(TAG, "Invalid Number");
                }
            }
        });

        findViewById(R.id.button15Percent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredAmount = editTextNumberDecimal2.getText().toString();
                double amount = 0;

                try {
                    amount = Double.valueOf(enteredAmount);

                    double discountedPrice = 0.85 * amount;

                    textViewDiscountedPercentage.setText("15%");
                    textViewDiscountedPrice.setText(String.valueOf(discountedPrice));

                }catch (NumberFormatException exception){
                    Log.d(TAG, "Invalid Number");
                }
            }
        });

        findViewById(R.id.button20Percent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredAmount = editTextNumberDecimal2.getText().toString();

                double amount = 0;

                try {
                    amount = Double.valueOf(enteredAmount);

                    double discountedPrice = 0.80 * amount;

                    textViewDiscountedPercentage.setText("20%");
                    textViewDiscountedPrice.setText(String.valueOf(discountedPrice));

                }catch (NumberFormatException exception){
                    Log.d(TAG, "Invalid Number");
                }
            }
        });

        findViewById(R.id.button50Percent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredAmount = editTextNumberDecimal2.getText().toString();

                double amount = 0;

                try {
                    amount = Double.valueOf(enteredAmount);

                    double discountedPrice = 0.50 * amount;

                    textViewDiscountedPercentage.setText("50%");
                    textViewDiscountedPrice.setText(String.valueOf(discountedPrice));

                }catch (NumberFormatException exception){
                    Log.d(TAG, "Invalid Number");
                }
            }
        });

    }
}