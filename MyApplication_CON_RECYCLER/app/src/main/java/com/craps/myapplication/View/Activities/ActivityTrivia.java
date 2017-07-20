package com.craps.myapplication.View.Activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.craps.myapplication.ControllerFormato.ControllerFormato;
import com.craps.myapplication.R;
import com.craps.myapplication.View.Fragments.FragmentJugarTrivia;
import com.craps.myapplication.View.Fragments.FragmentSubirPregunta;
import com.google.firebase.database.DatabaseReference;

public class ActivityTrivia extends AppCompatActivity implements ControllerFormato.Registrable {

    public static final String QUEMUESTRO = "QUEMUESTRO";
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        Intent unIntent = getIntent();
        Bundle unBundle = unIntent.getExtras();
        Integer queMuestro = unBundle.getInt(QUEMUESTRO);

        switch (queMuestro) {
            case 0:
                cargarFragmentTrivia();
                break;
            case 1:
                cargarFragmentSubirPregunta();
                break;
        }


    }

    public void cargarFragmentTrivia() {
        FragmentJugarTrivia fragmentJugarTrivia = new FragmentJugarTrivia();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_trivia, fragmentJugarTrivia);
        fragmentTransaction.commit();

    }

    public void cargarFragmentSubirPregunta() {
        FragmentSubirPregunta fragmentSubirPregunta = new FragmentSubirPregunta();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_trivia, fragmentSubirPregunta);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void solicitarRegistro() {

    }
}
