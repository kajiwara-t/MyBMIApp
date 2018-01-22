package com.example.sunrisesystem.mybmiapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BMI_Output_Activity extends Activity implements View.OnClickListener {

    private Button returnBt;
    private Button endButton;

    double data[] = new double[2];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_output);

        returnBt = (Button)findViewById(R.id.outReturn1);
        returnBt.setOnClickListener(this);

        endButton=(Button)findViewById(R.id.outEnd);
        endButton.setOnClickListener(this);

        keisan(data);

    }

    public void keisan (double data[]){

        Intent intent = getIntent();
        data[0] = intent.getDoubleExtra("Height",0);
        data[1] = intent.getDoubleExtra("Weight",0);

        double num1 = data[0] * 0.01;

        double bmi = data[1] / (num1 * num1);

        double pbWeight = (num1 * num1) * 22;

        double difWeight = data[1] - pbWeight;

        TextView textHeight = (TextView) findViewById(R.id.heightText);
        textHeight.setText(String.valueOf(data[0]));

        TextView textWeight = (TextView) findViewById(R.id.weightText);
        textWeight.setText(String.valueOf(data[1]));

        TextView textbmi = (TextView) findViewById(R.id.bmiText);
        textbmi.setText(String.format("%.2f",bmi));

        TextView textpbWeight = (TextView) findViewById(R.id.pbWeightText);
        textpbWeight.setText(String.format("%.2f",pbWeight));

        TextView textdiff = (TextView) findViewById(R.id.difText);
        textdiff.setText(String.format("%.2f",difWeight));

        TextView textCom = (TextView) findViewById(R.id.commentText);
        if(bmi <= 15.99){
            textCom.setText("痩せすぎ");
            textCom.setTextColor(Color.parseColor("#000000"));

        } else if ((bmi >= 16.00) && (bmi <= 16.99)){
            textCom.setText("痩せている");
            textCom.setTextColor(Color.parseColor("#191970"));

        } else if ((bmi >= 17.00) && (bmi <= 18.49)){
            textCom.setText("痩せ気味");
            textCom.setTextColor(Color.parseColor("#0000ff"));

        } else if ((bmi >= 18.50) && (bmi <= 24.99)){
            textCom.setText("標準体型");
            textCom.setTextColor(Color.parseColor("#008000"));

        } else if ((bmi >= 25.00) && (bmi<= 29.99)){
            textCom.setText("太り気味");
            textCom.setTextColor(Color.parseColor("#ffa500"));

        } else if (bmi >= 30.00){
            textCom.setText("肥満");
            textCom.setTextColor(Color.parseColor("#ff0000"));
        }



    }

    public void onClick (View view){
        if(view == returnBt){
            Intent intent = new Intent(this,BMI_Input_Activity.class);
            startActivity(intent);
        } else if(view == endButton){
            moveTaskToBack(true);
        }

    }
}
