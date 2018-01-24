package com.example.sunrisesystem.mybmiapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BMI_Input_Activity extends Activity implements View.OnClickListener {

    private Button calcButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_input);
        calcButton = findViewById(R.id.Calculation);
        calcButton.setOnClickListener(this);

    }


    //ボタン押下でBMI_Output_Activityへ遷移
    public void onClick(View view) {

        //身長・体重を入力
        final EditText heightText = findViewById(R.id.height);
        final EditText weightText = findViewById(R.id.weight);
        //double height = Double.parseDouble(heightText.getText().toString());
        //double weight = Double.parseDouble(weightText.getText().toString());

        //身長・体重のいずれかが未入力の場合
        if (heightText.getText().toString().equals("") == false && weightText.getText().toString().equals("") == false) {


            //身長・体重の数値が入力された場合
            Intent intent = new Intent(this, BMI_Output_Activity.class);
            double height = Double.parseDouble(heightText.getText().toString());
            double weight = Double.parseDouble(weightText.getText().toString());

            //　入力身長が 0 以下　999より上の場合
            if ((height <= 0) || (height > 999)) {
                Toast.makeText(getApplicationContext(), "範囲外です", Toast.LENGTH_SHORT).show();

                //入力体重が　0 以下　999より上の場合
            } else if ((weight <= 0) || (weight > 999)) {
                Toast.makeText(getApplicationContext(), "範囲外です", Toast.LENGTH_SHORT).show();

                //入力数値が 1 ～ 999 の範囲内の場合
            } else if ((weight >= 1) && (weight <= 999)) {
                intent.putExtra("Height", height);
                intent.putExtra("Weight", weight);
                startActivity(intent);

            } else {
                //入力数値判定 if文用
            }

            //未入力の場合
        } else {
            Toast.makeText(getApplicationContext(), "未入力です", Toast.LENGTH_SHORT).show();
        }
    }
}



