package com.example.sunrisesystem.mybmiapp;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

        //計算結果表示の文字　装飾用グラデーション
        TextView Answer = findViewById(R.id.outputView);
        Shader shader = new LinearGradient(0, 0, 0, Answer.getTextSize(), Color.RED, Color.BLUE,
                Shader.TileMode.CLAMP);
        Answer.getPaint().setShader(shader);
    }

    //BMI・適正体重・適正体重との差の計算
    public void keisan(double data[]) {

        Intent intent = getIntent();
        data[0] = intent.getDoubleExtra("Height", 0);
        data[1] = intent.getDoubleExtra("Weight", 0);

        //入力身長(cm)から入力身長(m)へと変換
        BigDecimal num1 = BigDecimal.valueOf(data[0]);
        BigDecimal num2 = BigDecimal.valueOf(0.01);

        //計算準備
        BigDecimal bdHeight = num1.multiply(num2);
        BigDecimal bdWeight = BigDecimal.valueOf(data[1]);

        //BMI計算式　BigDecimal使用
        BigDecimal bdbmi1 = bdHeight.multiply(bdHeight);
        BigDecimal bdbmi2 = bdWeight.divide(bdbmi1, 2, BigDecimal.ROUND_HALF_UP);
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
        textBmi.setText(String.format("%.2f", bmi));
        textBmi.setTextColor(Color.parseColor("#3399ff"));

        //適正体重
        TextView textPbWeight = findViewById(R.id.pbWeightText);
        textPbWeight.setText(String.format("%.2f", pbWeight));
        textPbWeight.setTextColor(Color.parseColor("#3333ff"));


        //適正体重との差
        TextView textDiff = findViewById(R.id.difText);
        textDiff.setText(String.format("%.2f", difWeight));
        textDiff.setTextColor(Color.parseColor("#9933ff"));

        //コメント
        TextView textCom = findViewById(R.id.commentText);
        TextView textCom2 = findViewById(R.id.commentText2);
        startMeasure();


        //算出されたBMIの数値によって分岐
        if (bmi <= 15.99) {
            textCom.setText("痩せすぎ");
            textCom.setTextColor(Color.parseColor("#000000"));
            textCom2.setText("しっかりと栄養を取りましょう！");

        } else if ((bmi >= 16.00) && (bmi <= 16.99)) {
            textCom.setText("痩身");
            textCom.setTextColor(Color.parseColor("#191970"));
            textCom2.setText("健康とのバランスに気を付けてください");

        } else if ((bmi >= 17.00) && (bmi <= 18.49)) {
            textCom.setText("痩せ気味");
            textCom.setTextColor(Color.parseColor("#0000ff"));
            textCom2.setText("ダイエット中なら程々に");

        } else if ((bmi >= 18.50) && (bmi <= 21.99) || (bmi >= 22.01) && (bmi <= 24.99)) {
            textCom.setText("標準体型");
            textCom.setTextColor(Color.parseColor("#008000"));
            textCom2.setText("このあたりを維持しましょう");

        } else if(bmi == 22.00) {
            textCom.setText("BMI基準体型");
            Shader shader = new LinearGradient(0, 0, 0, textCom.getTextSize(),
                    Color.parseColor("#00ff7f"), Color.parseColor("#4169e1"),
                    Shader.TileMode.CLAMP);
            textCom.getPaint().setShader(shader);
            textCom2.setText("");

        } else if ((bmi >= 25.00) && (bmi <= 29.99)) {
            textCom.setText("太り気味");
            textCom.setTextColor(Color.parseColor("#ffa500"));
            textCom2.setText("ダイエットを始めてみましょう！");

        } else if ((bmi >= 30.00) && (bmi <= 34.99)) {
            textCom.setText("肥満");
            textCom.setTextColor(Color.parseColor("#ff4500"));
            textCom2.setText("今すぐにダイエットを始めましょう！");

        } else if ((bmi >=35.00) && (bmi <= 39.99)){
            textCom.setText("超肥満");
            textCom.setTextColor(Color.parseColor("#ff0000"));
            textCom2.setText("医師の指導の下、健康管理を行ってください！");

        } else if (bmi >=40.00){
            textCom.setText("極大肥満");
            Shader shader = new LinearGradient(0, 0, 0, textCom.getTextSize(),
                    Color.BLACK, Color.RED, Shader.TileMode.CLAMP);
            textCom.getPaint().setShader(shader);
            textCom2.setText("命の危険があります。今すぐに医療機関へ！");
        }
    }


    //コメント用アニメーション
    private Handler mHandler = new Handler();
    private ScheduledExecutorService mScheduledExecutor;
    private TextView mLblMeasuring;

    private void startMeasure() {


        /**
         * 点滅させたいView
         * TextViewじゃなくてもよい。
         */
        mLblMeasuring = findViewById(R.id.commentText);

        /**
         * 第一引数: 繰り返し実行したい処理
         * 第二引数: 指定時間後に第一引数の処理を開始
         * 第三引数: 第一引数の処理完了後、指定時間後に再実行
         * 第四引数: 第二、第三引数の単位
         *
         * new Runnable（無名オブジェクト）をすぐに（0秒後に）実行し、完了後1700ミリ秒ごとに繰り返す。
         * （ただしアニメーションの完了からではない。Handler#postが即時実行だから？？）
         */
        mScheduledExecutor = Executors.newScheduledThreadPool(2);

        mScheduledExecutor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mLblMeasuring.setVisibility(View.VISIBLE);

                        // HONEYCOMBより前のAndroid SDKがProperty Animation非対応のため
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                            animateAlpha();
                        }
                    }
                });
            }


            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            private void animateAlpha() {

                // 実行するAnimatorのリスト
                List<Animator> animatorList = new ArrayList<Animator>();

                // alpha値を0から1へ1000ミリ秒かけて変化させる。
                ObjectAnimator animeFadeIn = ObjectAnimator.ofFloat(mLblMeasuring, "alpha", 0f, 1f);
                animeFadeIn.setDuration(2000);

                // alpha値を1から0へ600ミリ秒かけて変化させる。
                ObjectAnimator animeFadeOut = ObjectAnimator.ofFloat(mLblMeasuring, "alpha", 1f, 0f);
                animeFadeOut.setDuration(1200);

                // 実行対象Animatorリストに追加。
                animatorList.add(animeFadeIn);
                animatorList.add(animeFadeOut);

                final AnimatorSet animatorSet = new AnimatorSet();

                // リストの順番に実行
                animatorSet.playSequentially(animatorList);

                animatorSet.start();
            }
        }, 0, 3400, TimeUnit.MILLISECONDS);
    }



    //“やり直す”、“終了する”を選択
    public void onClick(View view) {
        if (view == returnBt) {
            Intent intent = new Intent(this, BMI_Input_Activity.class);
            startActivity(intent);
        } else if (view == endButton) {
            moveTaskToBack(true);
        }

    }
}




