package com.craps.myapplication.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.craps.myapplication.R;
import com.craps.myapplication.View.Activities.ActivityLogin;
import com.craps.myapplication.View.Activities.ActivityOnBoarding;


/**
 * Created by ignac on 19/5/2017.
 */

public class Splash extends Activity {

    //DURACION DE DE LA ESPERA

    private final int SPLASH_DISPLAY_LENGHT = 1500;

    /*@Override
    protected void onCreate(Bundle splash) {
        super.onCreate(splash);
        setContentView(R.layout.splash_layout);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(Splash.this, ActivityOnBoarding.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGHT);
    }*/


   @Override
    protected void onCreate(Bundle splash) {
        super.onCreate(splash);
        setContentView(R.layout.splash_layout);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(Splash.this, ActivityLogin.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGHT);
    }


}
