package com.example.sunrisesystem.mybmiapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;

public class BMI_Output_Activity extends Activity implements View.OnClickListener {

    private Button returnBt;
    private Button endButton;

    double data[] = new double[2];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_output);

        returnBt = findViewById(R.id.outReturn1);
        returnBt.setOnClickListener(this);

        endButton = findViewById(R.id.outEnd);
        endButton.setOnClickListener(this);

        keisan(data);

    }

    //BMI・適正体重・適正体重との差の計算
    public void keisan (double data[]){

        Intent intent = getIntent();
        data[0] = intent.getDoubleExtra("Height",0);
        data[1] = intent.getDoubleExtra("Weight",0);

        //入力身長(cm)から入力身長(m)へと変換
        BigDecimal num1 = BigDecimal.valueOf(data[0]);
        BigDecimal num2 = BigDecimal.valueOf(0.01);

        //計算準備
        BigDecimal bdHeight = num1.multiply(num2);
        BigDecimal bdWeight = BigDecimal.valueOf(data[1]);

        //BMI計算式　BigDecimal使用
        BigDecimal bdbmi1 = bdHeight.multiply(bdHeight);
        BigDecimal bdbmi2 = bdWeight.divide(bdbmi1,2,BigDecimal.ROUND_HALF_UP);
        //BMI表示用 double変換
        double bmi = bdbmi2.doubleValue();


        //適正体重計算式
        BigDecimal num22 = BigDecimal.valueOf(22);
        BigDecimal pbWeight1 = bdbmi1.multiply(num22);
        //適正体重表示用　double変換
        double pbWeight = pbWeight1.doubleValue();

        //現在体重と適正体重差の計算式
        BigDecimal difWeight1 = bdWeight.subtract(pbWeight1);
        //体重差表示用　double変換
        double difWeight = difWeight1.doubleValue();

        //身長
        TextView textHeight = findViewById(R.id.heightText);
        textHeight.setText(String.valueOf(data[0]));
        textHeight.setTextColor(Color.parseColor("#9933ff"));

        //体重
        TextView textWeight = findViewById(R.id.weightText);
        textWeight.setText(String.valueOf(data[1]));
        textWeight.setTextColor(Color.parseColor("#3333ff"));

        //BMI
        TextView textBmi = findViewById(R.id.bmiText);
        textBmi.setText(String.format("%.2f",bmi));
        textBmi.setTextColor(Color.parseColor("#3399ff"));

        //適正体重
        TextView textPbWeight = findViewById(R.id.pbWeightText);
        textPbWeight.setText(String.format("%.2f",pbWeight));
        textPbWeight.setTextColor(Color.parseColor("#33ffff"));


        //適正体重との差
        TextView textDiff = findViewById(R.id.difText);
        textDiff.setText(String.format("%.2f",difWeight));
        textDiff.setTextColor(Color.parseColor("#33ff99"));

        //コメント
        TextView textCom = findViewById(R.id.commentText);
        textCom.setTextColor(Color.parseColor("#33ff33"));

        //算出されたBMIの数値によって分岐
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

    //“やり直す”、“終了する”を選択
    public void onClick (View view){
        if(view == returnBt){
            Intent intent = new Intent(this,BMI_Input_Activity.class);
            startActivity(intent);
        } else if(view == endButton){
            moveTaskToBack(true);
        }

    }
}
