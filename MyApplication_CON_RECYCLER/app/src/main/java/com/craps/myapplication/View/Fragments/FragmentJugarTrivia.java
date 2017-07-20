package com.craps.myapplication.View.Fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.craps.myapplication.ControllerFormato.ControllerFormato;
import com.craps.myapplication.Model.Pregunta;
import com.craps.myapplication.R;
import com.craps.myapplication.Utils.ResultListener;
import com.craps.myapplication.Utils.TMDBHelper;
import com.github.clans.fab.FloatingActionButton;

import java.util.concurrent.ThreadLocalRandom;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentJugarTrivia extends Fragment {

    private ControllerFormato controllerJuegoTrivia;
    private TextView textoEnunciado;
    private RadioButton respuesta1;
    private RadioButton respuesta2;
    private RadioButton respuesta3;
    private RadioButton respuesta4;
    private RadioButton respuesta5;
    private RadioGroup grupoRespuestas;
    private TextView autor;
    private Pregunta preguntaARealizar;
    private TextView puntaje;
    private Button enviarRespuesta;
    private Button volver;
    private FloatingActionButton compartir;

    public FragmentJugarTrivia() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_jugar_trivia, container, false);
        controllerJuegoTrivia = new ControllerFormato(view.getContext());

        //CASTEAR TODOS LOS COMPONENTES DE LA VISTA
        textoEnunciado = (TextView) view.findViewById(R.id.textoDePregunta);
        puntaje = (TextView) view.findViewById(R.id.textPuntaje);
        autor = (TextView) view.findViewById(R.id.textAutor);
        respuesta1 = (RadioButton) view.findViewById(R.id.opcion1);
        respuesta2 = (RadioButton) view.findViewById(R.id.opcion2);
        respuesta3 = (RadioButton) view.findViewById(R.id.opcion3);
        respuesta4 = (RadioButton) view.findViewById(R.id.opcion4);
        respuesta5 = (RadioButton) view.findViewById(R.id.opcion5);
        autor = (TextView) view.findViewById(R.id.textAutor);
        grupoRespuestas = (RadioGroup) view.findViewById(R.id.radioGroup);
        enviarRespuesta = (Button) view.findViewById(R.id.btn_enviarRespuesta);
        volver = (Button) view.findViewById(R.id.btn_volver);
        compartir = (FloatingActionButton) view.findViewById(R.id.compartirPuntaje);

        //SETEAR EL FAB
        compartir.setButtonSize(FloatingActionButton.SIZE_NORMAL);
        compartir.setImageResource(R.drawable.ic_share_black_241dp);
        compartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareNativo(puntaje.getText().toString());
            }
        });

        //BOTON VOLVER
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        //CARGAR DATOS PREGUNTA
        controllerJuegoTrivia.traerPreguntaFirebase(new ResultListener<Pregunta>() {
            @Override
            public void finish(Pregunta resultado) {
                preguntaARealizar = resultado;
                cargarDatosPregunta();
            }
        });

        //BOTON ENVIAR RESPUESTAS
        enviarRespuesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer opcionSeleccionada = grupoRespuestas.getCheckedRadioButtonId();
                grupoRespuestas.clearCheck();
                switch (opcionSeleccionada) {
                    case R.id.opcion1:
                        if (respuesta1.getText().equals(preguntaARealizar.getRespuestaCorrecta())) {
                            preguntaBienRespondida();
                        } else {
                            preguntaMalRespondida();
                        }
                        break;
                    case R.id.opcion2:
                        if (respuesta2.getText().equals(preguntaARealizar.getRespuestaCorrecta())) {
                            preguntaBienRespondida();
                        } else {
                            preguntaMalRespondida();
                        }
                        break;
                    case R.id.opcion3:
                        if (respuesta3.getText().equals(preguntaARealizar.getRespuestaCorrecta())) {
                            preguntaBienRespondida();
                        } else {
                            preguntaMalRespondida();
                        }
                        break;
                    case R.id.opcion4:
                        if (respuesta4.getText().equals(preguntaARealizar.getRespuestaCorrecta())) {
                            preguntaBienRespondida();
                        } else {
                            preguntaMalRespondida();
                        }
                        break;
                    case R.id.opcion5:
                        if (respuesta5.getText().equals(preguntaARealizar.getRespuestaCorrecta())) {
                            preguntaBienRespondida();
                        } else {
                            preguntaMalRespondida();
                        }
                        break;
                    default:
                        break;
                }
            }
        });

        controllerJuegoTrivia.obtenerPuntajeFirebase(new ResultListener<String>() {
            @Override
            public void finish(String resultado) {
                puntaje.setText(resultado);
            }
        });
        return view;
    }

    public void cargarDatosPregunta() {
        textoEnunciado.setText(preguntaARealizar.getEnunciadoPregunta());
        autor.setText("Autor de la pregunta: " + preguntaARealizar.getAutor());
        int randomNum = ThreadLocalRandom.current().nextInt(1, 5 + 1);
        switch (randomNum) {
            case 1:
                respuesta1.setText(preguntaARealizar.getRespuestaCorrecta());
                respuesta2.setText(preguntaARealizar.getRespuestaIncorrecta1());
                respuesta3.setText(preguntaARealizar.getRespuestaIncorrecta2());
                respuesta4.setText(preguntaARealizar.getRespuestaIncorrecta3());
                respuesta5.setText(preguntaARealizar.getRespuestaIncorrecta4());
                break;
            case 2:
                respuesta1.setText(preguntaARealizar.getRespuestaIncorrecta1());
                respuesta2.setText(preguntaARealizar.getRespuestaCorrecta());
                respuesta3.setText(preguntaARealizar.getRespuestaIncorrecta2());
                respuesta4.setText(preguntaARealizar.getRespuestaIncorrecta3());
                respuesta5.setText(preguntaARealizar.getRespuestaIncorrecta4());
                break;
            case 3:
                respuesta1.setText(preguntaARealizar.getRespuestaIncorrecta2());
                respuesta2.setText(preguntaARealizar.getRespuestaIncorrecta1());
                respuesta3.setText(preguntaARealizar.getRespuestaCorrecta());
                respuesta4.setText(preguntaARealizar.getRespuestaIncorrecta3());
                respuesta5.setText(preguntaARealizar.getRespuestaIncorrecta4());
                break;
            case 4:
                respuesta1.setText(preguntaARealizar.getRespuestaIncorrecta3());
                respuesta2.setText(preguntaARealizar.getRespuestaIncorrecta1());
                respuesta3.setText(preguntaARealizar.getRespuestaIncorrecta2());
                respuesta4.setText(preguntaARealizar.getRespuestaCorrecta());
                respuesta5.setText(preguntaARealizar.getRespuestaIncorrecta4());
                break;
            case 5:
                respuesta1.setText(preguntaARealizar.getRespuestaIncorrecta4());
                respuesta2.setText(preguntaARealizar.getRespuestaIncorrecta1());
                respuesta3.setText(preguntaARealizar.getRespuestaIncorrecta2());
                respuesta4.setText(preguntaARealizar.getRespuestaIncorrecta3());
                respuesta5.setText(preguntaARealizar.getRespuestaCorrecta());
                break;
        }
    }

    public void preguntaBienRespondida() {
        controllerJuegoTrivia.sumarPuntosFirebase(new ResultListener<String>() {
            @Override
            public void finish(String resultado) {
                puntaje.setText(resultado);
                dialogoRespuesta("correcto");
            }
        });
    }

    public void preguntaMalRespondida() {
        controllerJuegoTrivia.restarPuntosFirebase(new ResultListener<String>() {
            @Override
            public void finish(String resultado) {
                puntaje.setText(resultado);
                dialogoRespuesta("incorrecto");
            }
        });
    }

    public void dialogoRespuesta(String respuesta) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.Dialog);
        if (respuesta.equals("correcto")) {
            builder.setTitle("Respuesta Correcta!")
                    .setMessage("Respondiste correctamente y ganaste 100 puntos")
                    .setPositiveButton("Otra Pregunta!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            controllerJuegoTrivia.traerPreguntaFirebase(new ResultListener<Pregunta>() {
                                @Override
                                public void finish(Pregunta resultado) {
                                    preguntaARealizar = resultado;
                                    cargarDatosPregunta();
                                }
                            });
                        }
                    })
                    .setNegativeButton("Salir", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getActivity().finish();
                        }
                    });
        }
        else {
            builder.setTitle("Respuesta Incorrecta!")
                    .setMessage("Respondiste mal y perdiste 30 puntos")
                    .setPositiveButton("Otra Pregunta!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            controllerJuegoTrivia.traerPreguntaFirebase(new ResultListener<Pregunta>() {
                                @Override
                                public void finish(Pregunta resultado) {
                                    preguntaARealizar = resultado;
                                    cargarDatosPregunta();
                                }
                            });
                        }
                    })
                    .setNegativeButton("Salir", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            getActivity().finish();
                        }
                    });
        }

        AlertDialog unDialogo = builder.create();
        unDialogo.show();
        final Button positiveButton = unDialogo.getButton(AlertDialog.BUTTON_POSITIVE);
        final Button negativeButton = unDialogo.getButton(AlertDialog.BUTTON_NEGATIVE);
        positiveButton.setTextColor(getResources().getColor(R.color.paleta4));
        negativeButton.setTextColor(getResources().getColor(R.color.paleta4));
        LinearLayout.LayoutParams positiveButtonLL = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
        positiveButtonLL.width = ViewGroup.LayoutParams.MATCH_PARENT;
        positiveButton.setLayoutParams(positiveButtonLL);
    }

    public void shareNativo(String Puntaje) {
        //Creamos un share de tipo ACTION_SENT
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        //Indicamos que voy a compartir texto
        share.setType("text/plain");
        //Le agrego un título
        share.putExtra(Intent.EXTRA_SUBJECT, "Comparti Reelshot");
        //Le agrego el texto a compartir (Puede ser un link tambien)
        share.putExtra(Intent.EXTRA_TEXT, "Obtuve un puntaje de " + Puntaje + " en la trivia de Reelshot!" + "\n" + "Podés superarme?");
        //Hacemos un start para que comparta el contenido.
        startActivity(Intent.createChooser(share, "Compartí con tus amigos"));
    }
}
