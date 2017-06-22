package com.craps.myapplication.View.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.craps.myapplication.R;
import com.craps.myapplication.View.Activities.ActivityLogin;
import com.craps.myapplication.View.Activities.ActivityRegister;

import org.w3c.dom.Text;


public class FragmentFavoritosSinUsuario extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_fragment_favoritos_sin_usuario, container, false);
        Typeface roboto = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/Roboto-Light.ttf");
        Button botonLogin = (Button)view.findViewById(R.id.buttonLogin);
        Button botonRegister = (Button)view.findViewById(R.id.buttonRegister);
        TextView textViewLogin = (TextView)view.findViewById(R.id.textosinlogin);
        textViewLogin.setTypeface(roboto);

        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent unIntent = new Intent(v.getContext(), ActivityLogin.class);
                Activity unaActivity =(Activity)v.getContext();
                unaActivity.finish();
                startActivity(unIntent);

            }
        });

        botonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent unIntent = new Intent(view.getContext(), ActivityRegister.class);
                Activity unaActivity =(Activity)v.getContext();
                unaActivity.finish();
                startActivity(unIntent);
            }
        });





        return view;
    }

    public static FragmentFavoritosSinUsuario crearFragmentMaestro(){
        FragmentFavoritosSinUsuario fragmentFavoritosSinUsuario=new FragmentFavoritosSinUsuario();
        return fragmentFavoritosSinUsuario;
    }



}
