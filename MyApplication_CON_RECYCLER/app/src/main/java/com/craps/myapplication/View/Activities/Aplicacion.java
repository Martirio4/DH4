package com.craps.myapplication.View.Activities;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Martirio on 19/07/2017.
 */

public class Aplicacion extends android.app.Application {


    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
