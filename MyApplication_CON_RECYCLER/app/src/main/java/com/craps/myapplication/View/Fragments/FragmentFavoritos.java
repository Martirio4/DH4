package com.craps.myapplication.View.Fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.craps.myapplication.ControllerFormato.ControllerFormato;
import com.craps.myapplication.Model.Formato;
import com.craps.myapplication.R;
import com.craps.myapplication.Utils.ResultListener;
import com.craps.myapplication.View.Activities.ActivityMain;
import com.craps.myapplication.View.Adapters.AdapterFormato;


import java.util.ArrayList;
import java.util.List;


public class FragmentFavoritos extends Fragment {
    private AdapterFormato adapterFavoritos;
    private Notificable notificable;
    private List<Formato> lista1;
    private ControllerFormato controllerFormato;
    //DECLARO INTERFAZ

    public interface Notificable {
        public void recibirFormatoClickeado(Formato formato, String origen, Integer pagina, String StringABuscar, Integer drawerId);


    }


    public FragmentFavoritos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favoritos, container, false);


        //SETEAR EL ADAPTER
        final RecyclerView recycler1 = (RecyclerView) view.findViewById(R.id.recycler1);


        recycler1.setHasFixedSize(true);
        recycler1.setLayoutManager(new GridLayoutManager(view.getContext(), 3));
        adapterFavoritos = new AdapterFormato();
        adapterFavoritos.setContext(view.getContext());

        //AGREGO LISTENER DE CLICKEO DE PELICULAS
        View.OnClickListener listener1 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ESTO SE UTILIZA PARA OBTENER LA POSITION DE LO QUE FUE CLICKEADO.
                Integer posicion = recycler1.getChildAdapterPosition(view);
                List<Formato> listaPeliculasOriginales = adapterFavoritos.getListaFormatosOriginales();
                Formato formatoClickeado = listaPeliculasOriginales.get(posicion);
                notificable.recibirFormatoClickeado(formatoClickeado, "favoritos", 1, "nulo", 0);
            }
        };
        adapterFavoritos.setListener(listener1);
        recycler1.setAdapter(adapterFavoritos);

        //cargar datos
        controllerFormato = new ControllerFormato(view.getContext());
        lista1 = new ArrayList<>();

        controllerFormato.obtenerFavoritos(new ResultListener<List<Formato>>() {
            @Override
            public void finish(List<Formato> resultado) {

                adapterFavoritos.setListaFormatosOriginales(resultado);
                lista1 = resultado;
                adapterFavoritos.notifyDataSetChanged();
            }
        });

        adapterFavoritos.setListaFormatosOriginales(lista1);
        adapterFavoritos.notifyDataSetChanged();


        TextView tituloR1 = (TextView) view.findViewById(R.id.texto_titulo_sinconexion);


        Typeface roboto = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Light.ttf");
        tituloR1.setTypeface(roboto);
        tituloR1.setText("FAVORITOS");


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.notificable = (Notificable) context;
    }

    public static FragmentFavoritos crearFragmentMaestro() {
        FragmentFavoritos fragmentFavoritos = new FragmentFavoritos();
        return fragmentFavoritos;
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            if (isResumed()) {
                controllerFormato.obtenerFavoritos(new ResultListener<List<Formato>>() {
                    @Override
                    public void finish(List<Formato> resultado) {

                        adapterFavoritos.setListaFormatosOriginales(resultado);
                        lista1 = resultado;
                        adapterFavoritos.notifyDataSetChanged();
                    }
                });
            }
        }
    }



    @Override
    public void onResume() {
        controllerFormato.obtenerFavoritos(new ResultListener<List<Formato>>() {
            @Override
            public void finish(List<Formato> resultado) {

                adapterFavoritos.setListaFormatosOriginales(resultado);
                lista1 = resultado;
                adapterFavoritos.notifyDataSetChanged();
            }
        });
        super.onResume();
    }
}



