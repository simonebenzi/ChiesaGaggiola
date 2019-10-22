package com.simo.appchiesa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;


public class Intro extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);

        new CountDownTimer(1000, 1000) {
            public void onFinish() {
                Intent i = new Intent();
                i.setClass(Intro.this, ScenariosActivity.class);
                startActivity(i);
                finish();
            }
            public void onTick(long millisUntilFinished) {
            }
        }.start();
    }
}
