package com.craps.myapplication.View.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.craps.myapplication.ControllerFormato.ControllerFormato;
import com.craps.myapplication.Model.Formato;
import com.craps.myapplication.R;
import com.craps.myapplication.Utils.ResultListener;
import com.craps.myapplication.View.Activities.ActivityTrivia;
import com.github.clans.fab.FloatingActionButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTrivia extends Fragment {

    private Triviable triviable;
   private ControllerFormato controllerTrivia;
    private TextView puntaje;
    private FloatingActionButton compartir;

    public FragmentTrivia() {
        // Required empty public constructor
    }

    public interface Triviable{
        public void irATrivia(Integer tipoFragment);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.trivia_fragment, container, false);

        puntaje=(TextView)view.findViewById(R.id.puntajePortadaTrivia) ;

        Button botonEmpezar= (Button) view.findViewById(R.id.btn_empezarTrivia);
        botonEmpezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                triviable.irATrivia(0);
            }
        });


        Button botonSubirPregunta= (Button) view.findViewById(R.id.btn_subirPregunta);
        botonSubirPregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                triviable.irATrivia(1);
            }
        });

        controllerTrivia=new ControllerFormato(view.getContext());
        controllerTrivia.obtenerPuntajeFirebase(new ResultListener<String>() {
            @Override
            public void finish(String resultado) {
                puntaje.setText(resultado);
            }
        });

        compartir = (FloatingActionButton) view.findViewById(R.id.compartirPuntajeInicial);
        compartir.setButtonSize(FloatingActionButton.SIZE_NORMAL);
        compartir.setImageResource(R.drawable.ic_share_black_241dp);
        compartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareNativo(puntaje.getText().toString());
            }
        });




        return view;
    }
    public static FragmentTrivia crearFragmentMaestro() {
        FragmentTrivia fragmentTrivia = new FragmentTrivia();
        return fragmentTrivia;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.triviable=(Triviable) context;
    }

    @Override
    public void onResume() {
        controllerTrivia.obtenerPuntajeFirebase(new ResultListener<String>() {
            @Override
            public void finish(String resultado) {
                puntaje.setText(resultado);
            }
        });
        super.onResume();
    }
    public void shareNativo(String Puntaje){

        //Creamos un share de tipo ACTION_SENT
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
//Indicamos que voy a compartir texto
        share.setType("text/plain");
//Le agrego un título
        share.putExtra(Intent.EXTRA_SUBJECT, "Comparti Reelshot");
//Le agrego el texto a compartir (Puede ser un link tambien)
        share.putExtra(Intent.EXTRA_TEXT, "Obtuve un puntaje de "+Puntaje+" en la trivia de Reelshot!"+"\n"+"Podés superarme?");
//Hacemos un start para que comparta el contenido.
        startActivity(Intent.createChooser(share, "Compartí con tus amigos"));


    }
}
