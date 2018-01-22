package com.example.sunrisesystem.mybmiapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button startBt;
    private Button endBt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startBt = (Button)findViewById(R.id.Start);
        startBt.setOnClickListener(this);
        endBt = (Button)findViewById(R.id.End);
        endBt.setOnClickListener(this);
    }

    public void onClick (View view) {
        if(view == startBt){
            Intent intent = new Intent(this,BMI_Input_Activity.class);
            startActivity(intent);
        } else if(view == endBt){
            moveTaskToBack(true);
        }
    }
}
