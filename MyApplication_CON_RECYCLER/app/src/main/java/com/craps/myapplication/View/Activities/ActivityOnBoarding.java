// PABLO 1/1A

package com.craps.myapplication.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.craps.myapplication.R;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntro2Fragment;

public class ActivityOnBoarding extends AppIntro2 {
    private Bundle bundle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent unIntent = getIntent();
        bundle= unIntent.getExtras();

        addSlide(AppIntro2Fragment.newInstance("", "BIENVENIDO A REELSHOT", R.drawable.ob_splash, getColor(R.color.paleta4)));
        addSlide(AppIntro2Fragment.newInstance("", "REGISTRATE PARA DISFRUTAR AL MAXIMO DE TU EXPERIENCIA", R.drawable.ob_login, getColor(R.color.paleta4)));
        addSlide(AppIntro2Fragment.newInstance("", "BUSCA Y SELECCIONA TUS PELICULAS Y SERIES FAVORITAS", R.drawable.ob_home, getColor(R.color.paleta4)));
        addSlide(AppIntro2Fragment.newInstance("", "ACCEDE AL TRAILER Y TODA LA INFORMACION QUE TE INTERESA", R.drawable.ob_detalle, getColor(R.color.paleta4)));

        setBarColor(getColor(R.color.paleta0));

    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        Intent unItent = new Intent(this, ActivityMain.class);
        unItent.putExtras(bundle);
        startActivity(unItent);

    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent unItent = new Intent(this, ActivityMain.class);
        unItent.putExtras(bundle);
        startActivity(unItent);

    }

}
// PABLO 1/1C