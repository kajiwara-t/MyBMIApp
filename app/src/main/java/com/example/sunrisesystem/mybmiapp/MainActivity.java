package com.example.sunrisesystem.mybmiapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button startBt;
    private Button endBt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startBt = findViewById(R.id.Start);
        startBt.setOnClickListener(this);
        endBt = findViewById(R.id.End);
        endBt.setOnClickListener(this);

        TextView textOpen = findViewById(R.id.BMIopening);

        //オープニング画面文字　装飾用グラデーション
        Shader shader = new LinearGradient(0, 0, 0, textOpen.getTextSize(),
                Color.parseColor("#ff8c00"), Color.parseColor("#dc143c"),
                Shader.TileMode.CLAMP);
        textOpen.getPaint().setShader(shader);
    }

    //スタート・終了の選択ボタン
    public void onClick(View view) {
        if (view == startBt) {
            Intent intent = new Intent(this, BMI_Input_Activity.class);
            startActivity(intent);
        } else if (view == endBt) {
            moveTaskToBack(true);
        }
    }
}
