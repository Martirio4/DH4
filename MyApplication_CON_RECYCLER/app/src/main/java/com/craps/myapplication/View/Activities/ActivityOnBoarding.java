// PABLO 1/1A

package com.craps.myapplication.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.craps.myapplication.R;
import com.craps.myapplication.View.Fragments.FragmentOB1;
import com.craps.myapplication.View.Fragments.FragmentOB2;
import com.craps.myapplication.View.Fragments.FragmentOB3;
import com.craps.myapplication.View.Fragments.FragmentOB4;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntro2Fragment;

public class ActivityOnBoarding extends AppIntro {
    private Bundle bundle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent unIntent = getIntent();
        bundle= unIntent.getExtras();

        addSlide(new FragmentOB1());
        addSlide(new FragmentOB2());
        addSlide(new FragmentOB3());
        addSlide(new FragmentOB4());


        setBarColor(getColor(R.color.paleta0));

    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        Intent unItent = new Intent(this, ActivityLogin.class);

        startActivity(unItent);

    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent unItent = new Intent(this, ActivityLogin.class);

        startActivity(unItent);

    }

}
// PABLO 1/1C