package com.craps.myapplication.View.Fragments;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.craps.myapplication.ControllerFormato.ControllerFormato;
import com.craps.myapplication.DAO.Db.DAOFavoritosDatabase;
import com.craps.myapplication.Model.Pregunta;
import com.craps.myapplication.R;
import com.craps.myapplication.View.Activities.ActivityMain;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSubirPregunta extends Fragment {

    private TextInputLayout textInputLayout11;
    private TextInputLayout textInputLayout12;
    private TextInputLayout textInputLayout13;
    private TextInputLayout textInputLayout14;
    private TextInputLayout textInputLayout15;
    private TextInputLayout textInputLayout16;
    
    private EditText editEnunciado;
    private EditText respuestaCorrecta;
    private EditText respuestaIncorrecta1;
    private EditText respuestaIncorrecta2;
    private EditText respuestaIncorrecta3;
    private EditText respuestaIncorrecta4;
    
    private Button botonUploadPregunta;
    private Button botonBorrarCampos;
    private Button botonVolverAtras;

    private DatabaseReference mdbase;

    public FragmentSubirPregunta() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.subir_pregunta, container, false);
        
        textInputLayout11= (TextInputLayout)  view.findViewById(R.id.inputLayout11);
        textInputLayout12= (TextInputLayout)  view.findViewById(R.id.inputLayout12);
        textInputLayout13= (TextInputLayout)  view.findViewById(R.id.inputLayout13);
        textInputLayout14= (TextInputLayout)  view.findViewById(R.id.inputLayout14);
        textInputLayout15= (TextInputLayout)  view.findViewById(R.id.inputLayout15);
        textInputLayout16= (TextInputLayout)  view.findViewById(R.id.inputLayout16);

        editEnunciado=(EditText)view.findViewById(R.id.edit11);
        respuestaCorrecta=(EditText)view.findViewById(R.id.edit12);
        respuestaIncorrecta1=(EditText)view.findViewById(R.id.edit13);
        respuestaIncorrecta2=(EditText)view.findViewById(R.id.edit14);
        respuestaIncorrecta3=(EditText)view.findViewById(R.id.edit15);
        respuestaIncorrecta4=(EditText)view.findViewById(R.id.edit16);

        Typeface roboto = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/Roboto-Regular.ttf");

        editEnunciado.setTypeface(roboto);
        respuestaCorrecta.setTypeface(roboto);
        respuestaIncorrecta1.setTypeface(roboto);
        respuestaIncorrecta2.setTypeface(roboto);
        respuestaIncorrecta3.setTypeface(roboto);
        respuestaIncorrecta4.setTypeface(roboto);

        botonUploadPregunta=(Button)view.findViewById(R.id.btn_upload_pregunta);
        botonUploadPregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer control=0;
                textInputLayout11.setError(null);
                textInputLayout12.setError(null);
                textInputLayout13.setError(null);
                textInputLayout14.setError(null);
                textInputLayout15.setError(null);
                textInputLayout16.setError(null);

                if (editEnunciado.getText().toString().isEmpty()){
                    textInputLayout11.setError("Por favor ingrese un enunciado");
                    control=control+1;
                }
                if (respuestaCorrecta.getText().toString().isEmpty()){
                    textInputLayout12.setError("Por favor ingrese una respuesta");
                    control=control+1;
                }
                if (respuestaIncorrecta1.getText().toString().isEmpty()){
                    textInputLayout13.setError("Por favor ingrese una respuesta");
                    control=control+1;
                }
                if (respuestaIncorrecta2.getText().toString().isEmpty()){
                    textInputLayout14.setError("Por favor ingrese una respuesta");
                    control=control+1;
                }
                if (respuestaIncorrecta3.getText().toString().isEmpty()){
                    textInputLayout15.setError("Por favor ingrese una respuesta");
                    control=control+1;
                }
                if (respuestaIncorrecta4.getText().toString().isEmpty()){
                    textInputLayout16.setError("Por favor ingrese una respuesta");
                    control=control+1;
                }
                if (control==0){
                    String nombre;
                    if (FirebaseAuth.getInstance().getCurrentUser().getDisplayName()==(null)||FirebaseAuth.getInstance().getCurrentUser().getDisplayName().isEmpty()){
                        nombre=FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    }
                    else{
                       nombre= FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                    }
                    Pregunta unaPregunta=crearPregunta(editEnunciado.getText().toString(),nombre, respuestaCorrecta.getText().toString(), respuestaIncorrecta1.getText().toString(), respuestaIncorrecta2.getText().toString(), respuestaIncorrecta3.getText().toString(), respuestaIncorrecta4.getText().toString());

                    ControllerFormato controllerPreguntas=new ControllerFormato(v.getContext());
                    controllerPreguntas.subirPreguntaFirebase(unaPregunta);
                    crearDialogoPreguntaSubida();
                }
            }
        });

        botonBorrarCampos=(Button)view.findViewById(R.id.btn_limpiar_campos);
        botonBorrarCampos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textInputLayout11.setError(null);
                textInputLayout12.setError(null);
                textInputLayout13.setError(null);
                textInputLayout14.setError(null);
                textInputLayout15.setError(null);
                textInputLayout16.setError(null);

                editEnunciado.setText("");
                respuestaCorrecta.setText("");
                respuestaIncorrecta1.setText("");
                respuestaIncorrecta2.setText("");
                respuestaIncorrecta3.setText("");
                respuestaIncorrecta4.setText("");


            }
        });










        return  view;
    }

    public Pregunta crearPregunta(String enunciadoPregunta, String autor, String respuestaCorrecta, String respuestaIncorrecta1, String respuestaIncorrecta2, String respuestaIncorrecta3, String respuestaIncorrecta4){
        Pregunta unaPregunta=new Pregunta();
        unaPregunta.setAutor(autor);
        unaPregunta.setEnunciadoPregunta(enunciadoPregunta);
        unaPregunta.setRespuestaCorrecta(respuestaCorrecta);
        unaPregunta.setRespuestaIncorrecta1(respuestaIncorrecta1);
        unaPregunta.setRespuestaIncorrecta2(respuestaIncorrecta2);
        unaPregunta.setRespuestaIncorrecta3(respuestaIncorrecta3);
        unaPregunta.setRespuestaIncorrecta4(respuestaIncorrecta4);
        return unaPregunta;
    }
    public void crearDialogoPreguntaSubida() {


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.Dialog);



        builder.setTitle("ReelShot Trivia")
                .setMessage("Su pregunta fue agregada con éxito"+"\n"+"desea agregar otra pregunta?")
                .setPositiveButton("Crear otra pregunta", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textInputLayout11.setError(null);
                        textInputLayout12.setError(null);
                        textInputLayout13.setError(null);
                        textInputLayout14.setError(null);
                        textInputLayout15.setError(null);
                        textInputLayout16.setError(null);

                        editEnunciado.setText("");
                        respuestaCorrecta.setText("");
                        respuestaIncorrecta1.setText("");
                        respuestaIncorrecta2.setText("");
                        respuestaIncorrecta3.setText("");
                        respuestaIncorrecta4.setText("");

                    }
                })
                .setNegativeButton("Salir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        getActivity().finish();

                    }
                });

        AlertDialog unDialogo= builder.create();
        unDialogo.show();

        final Button positiveButton = unDialogo.getButton(AlertDialog.BUTTON_POSITIVE);
        LinearLayout.LayoutParams positiveButtonLL = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
        positiveButtonLL.width = ViewGroup.LayoutParams.MATCH_PARENT;
        positiveButton.setLayoutParams(positiveButtonLL);
    }
}
