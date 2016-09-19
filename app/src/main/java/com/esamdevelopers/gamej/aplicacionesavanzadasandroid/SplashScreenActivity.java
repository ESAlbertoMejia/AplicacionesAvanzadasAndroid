package com.esamdevelopers.gamej.aplicacionesavanzadasandroid;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by gamej on 18/9/2016.
 */
public class SplashScreenActivity extends AppCompatActivity {

    //Set duration of the Splash Screen
    private static final long SPLASH_SCREEN_DELAY = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Hide Title Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Set Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splash_screen);

        TimerTask tt = new TimerTask() {
            @Override
            public void run() {

                //Start the next Activity
                Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(i);

                //Close the Activity so the user won´t able to go back this
                //Activity pressing Back button

                finish();
            }
        };

        //Simulate a long loading process on application startup
        Timer t = new Timer();
        t.schedule(tt, SPLASH_SCREEN_DELAY);
    }
}