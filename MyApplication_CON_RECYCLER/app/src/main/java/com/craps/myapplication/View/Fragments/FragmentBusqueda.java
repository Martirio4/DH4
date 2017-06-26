package com.craps.myapplication.View.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.craps.myapplication.ControllerFormato.ControllerFormato;
import com.craps.myapplication.Model.Formato;
import com.craps.myapplication.R;
import com.craps.myapplication.Utils.ResultListener;
import com.craps.myapplication.Utils.TMDBHelper;
import com.craps.myapplication.View.Adapters.AdapterFormato;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentBusqueda extends Fragment {

    private AdapterFormato adapterBusquedaFormatos;
    private Notificable Notificable;

    public static final String QUEBUSCO="QUEBUSCO";

    private String stringABuscar;
    ControllerFormato controllerBusquedaFormatos;



    //DECLARO INTERFAZ
    public interface Notificable {
        public void recibirFormatoClickeado(Formato formato, Integer pagina);

    }

    //ON CREATE
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //INFLAR LAYOUT
           final View view = inflater.inflate(R.layout.fragment_buscador, container, false);

        //RECIBIR EL BUNDLE

        Bundle unbundle = getArguments();
        stringABuscar = unbundle.getString(QUEBUSCO);

        //CASTEAR RECYCLER Y SETEAR ADAPTER
        final RecyclerView recycler1 = (RecyclerView) view.findViewById(R.id.recycler1);
        recycler1.setHasFixedSize(true);
        recycler1.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        adapterBusquedaFormatos = new AdapterFormato();
        adapterBusquedaFormatos.setContext(view.getContext());

        //CARGAR DATOS
        adapterBusquedaFormatos.setListaFormatosOriginales(new ArrayList<Formato>());
        controllerBusquedaFormatos= new ControllerFormato(view.getContext());
        controllerBusquedaFormatos.buscarPelicula(new ResultListener<List<Formato>>() {
            @Override
            public void finish(List<Formato> resultado) {
                if (resultado==null||resultado.size()<1){
                    noSeEncuentranResultados();
                }
                else {
                    adapterBusquedaFormatos.setListaFormatosOriginales(resultado);
                    adapterBusquedaFormatos.notifyDataSetChanged();
                }
            }
        },stringABuscar);



        //AGREGO LISTENER DE CLICKEO DE PELICULAS
        View.OnClickListener listener1 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ESTO SE UTILIZA PARA OBTENER LA POSITION DE LO QUE FUE CLICKEADO.
                Integer posicion = recycler1.getChildAdapterPosition(view);
                List < Formato > listaFormatosOriginales = adapterBusquedaFormatos.getListaFormatosOriginales();
                Formato formatoClickeado = listaFormatosOriginales.get(posicion);
                String nombre=formatoClickeado.getTitle();
                String tipoFormato;
                if(nombre==null||nombre.isEmpty()){
                    tipoFormato="serie";
                }
                else{
                    tipoFormato="pelicula";
                }
                Notificable.recibirFormatoClickeado(formatoClickeado,controllerBusquedaFormatos.getNumeroPagina());
            }
        };
        adapterBusquedaFormatos.setListener(listener1);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.Notificable = (Notificable) context;
    }

    public void noSeEncuentranResultados() {
        //cargo el fragment de sin resultados
        FragmentSinResultados sinCoincidenciasFragment= new FragmentSinResultados();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contenedor_fragment_maestro, sinCoincidenciasFragment);
        fragmentTransaction.commit();


    }



}
