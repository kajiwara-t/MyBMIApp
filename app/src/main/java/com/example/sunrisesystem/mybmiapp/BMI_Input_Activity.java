package com.example.sunrisesystem.mybmiapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BMI_Input_Activity extends Activity implements View.OnClickListener {

    private Button calcButton;
    private double height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_input);
        calcButton = (Button) findViewById(R.id.Calculation);
        calcButton.setOnClickListener(this);
    }

    public void onClick(View view) {

        final EditText heightText = findViewById(R.id.height);
        final EditText weightText = findViewById(R.id.weight);
        //double height = Double.parseDouble(heightText.getText().toString());
        //double weight = Double.parseDouble(weightText.getText().toString());

        //身長・体重のいずれかが未入力の場合
        if (heightText.getText().toString().equals("") && weightText.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "未入力です", Toast.LENGTH_SHORT).show();

            //身長・体重のいずれかが範囲外の場合
        } else if((heightText.getText().toString().length() <=0 && heightText.getText().toString().length() > 999
                && weightText.getText().toString().length() <=0 && weightText.getText().toString().length() > 999)) {
                Toast.makeText(getApplicationContext(), "範囲外です", Toast.LENGTH_SHORT).show();
        }else{
            //身長・体重ともに正常に入力されている場合
            Intent intent = new Intent(this, BMI_Output_Activity.class);
            double height = Double.parseDouble(heightText.getText().toString());
            double weight = Double.parseDouble(weightText.getText().toString());
                intent.putExtra("Height", height);
                intent.putExtra("Weight", weight);
                startActivity(intent);
            }
        }
    }

