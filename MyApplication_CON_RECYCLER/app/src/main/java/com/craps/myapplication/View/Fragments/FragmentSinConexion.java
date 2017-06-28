package com.craps.myapplication.View.Fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.craps.myapplication.ControllerFormato.ControllerFormato;
import com.craps.myapplication.Model.Formato;
import com.craps.myapplication.R;
import com.craps.myapplication.Utils.HTTPConnectionManager;
import com.craps.myapplication.Utils.ResultListener;
import com.craps.myapplication.Utils.TMDBHelper;
import com.craps.myapplication.View.Activities.ActivityMain;
import com.craps.myapplication.View.Adapters.AdapterFormato;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSinConexion extends Fragment {

   private AdapterFormato unadapter1;
    private Notificable notificable;
    private List<Formato>lista1;

    //DECLARO INTERFAZ
    public interface Notificable {
        public void recibirFormatoClickeado(Formato formato, String url);

    }


    public FragmentSinConexion() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sin_conexion, container, false);

        Bundle unbundle = getArguments();

        //SETEAR EL ADAPTER
        final RecyclerView recycler1 = (RecyclerView) view.findViewById(R.id.recycler1);


        recycler1.setHasFixedSize(true);
        recycler1.setLayoutManager(new GridLayoutManager(view.getContext(),3));
        unadapter1 = new AdapterFormato();
        unadapter1.setContext(view.getContext());

        //AGREGO LISTENER DE CLICKEO DE PELICULAS
        View.OnClickListener listener1 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ESTO SE UTILIZA PARA OBTENER LA POSITION DE LO QUE FUE CLICKEADO.
                Integer posicion = recycler1.getChildAdapterPosition(view);
                List< Formato > listaPeliculasOriginales = unadapter1.getListaFormatosOriginales();
                Formato formatoClickeado = listaPeliculasOriginales.get(posicion);
                notificable.recibirFormatoClickeado(formatoClickeado, null);
            }
        };


        unadapter1.setListener(listener1);
        recycler1.setAdapter(unadapter1);

        //cargar datos

        final ControllerFormato controllerFormato = new ControllerFormato(view.getContext());
        lista1 =new ArrayList<>();
        /*
        controllerFormato.obtenerFormatos(new ResultListener<List<Formato>>() {
            @Override
            public void finish(List<Formato> resultado) {

                unadapter1.setListaCreditosOriginales(resultado);
                lista1=resultado;
                unadapter1.notifyDataSetChanged();
            }
        }, null);
*/
        unadapter1.setListaFormatosOriginales(lista1);
        unadapter1.notifyDataSetChanged();


        TextView tituloR1 = (TextView) view.findViewById(R.id.texto_titulo_sinconexion);


        Typeface roboto = Typeface.createFromAsset(getContext().getAssets(),"fonts/Roboto-Light.ttf");
        tituloR1.setTypeface(roboto);
        tituloR1.setText("SIN ACCESO A INTERNET");


        //CASTEO EL FAB REFRESH
        FloatingActionButton fabrefresh=(FloatingActionButton) view.findViewById(R.id.fab_refresh);

        fabrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HTTPConnectionManager.isNetworkingOnline(v.getContext())){
                    Intent unIntent= new Intent(v.getContext(), ActivityMain.class);
                    startActivity(unIntent);
                }
                else{
                    Toast.makeText(v.getContext(), "Sin conexion a Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });




        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.notificable = (Notificable) context;
    }
    public static FragmentSinConexion crearFragmentMaestro(){
        FragmentSinConexion fragmentSinConexion=new FragmentSinConexion();
        return fragmentSinConexion;
    }
}
