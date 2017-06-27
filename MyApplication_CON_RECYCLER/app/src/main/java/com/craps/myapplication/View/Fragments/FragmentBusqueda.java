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
import android.widget.Toast;

import com.craps.myapplication.ControllerFormato.ControllerFormato;
import com.craps.myapplication.Model.Formato;
import com.craps.myapplication.R;
import com.craps.myapplication.Utils.ResultListener;
import com.craps.myapplication.View.Activities.ActivityMain;
import com.craps.myapplication.View.Adapters.AdapterFormato;

import java.util.ArrayList;
import java.util.List;

import static com.craps.myapplication.View.Activities.ActivityMain.login;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentBusqueda extends Fragment {

    private AdapterFormato adapterBusquedaFormatos;
    private Notificable Notificable;
    private Context elContext;

    public static final String QUEBUSCO="QUEBUSCO";
    public static final String DRAWERID="DRAWERID";

    private String stringABuscar;
    private Integer drawerId;
    ControllerFormato controllerBusquedaFormatos;
    private Boolean isLoading=false;

    private Integer formatoAMostrar;
    private RecyclerView recyclerBusqueda;
    private LinearLayoutManager layoutManagerBusqueda;


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
        drawerId = unbundle.getInt(DRAWERID);

        //CASTEAR RECYCLER Y SETEAR ADAPTER
        final RecyclerView recyclerBusqueda = (RecyclerView) view.findViewById(R.id.recycler1);
        recyclerBusqueda.setHasFixedSize(true);
        layoutManagerBusqueda= new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerBusqueda.setLayoutManager(layoutManagerBusqueda);
        adapterBusquedaFormatos = new AdapterFormato();
        adapterBusquedaFormatos.setContext(view.getContext());
        recyclerBusqueda.setAdapter(adapterBusquedaFormatos);

        adapterBusquedaFormatos.setListaFormatosOriginales(new ArrayList<Formato>());
        controllerBusquedaFormatos = new ControllerFormato(view.getContext());

        ActivityMain activity=(ActivityMain) elContext;
        formatoAMostrar= activity.obtenerTab();

        //CARGAR DATOS
        pedirPaginaBuscador();

        //AGREGO LISTENER DE CLICKEO DE PELICULAS
        View.OnClickListener listener1 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ESTO SE UTILIZA PARA OBTENER LA POSITION DE LO QUE FUE CLICKEADO.
                Integer posicion = recyclerBusqueda.getChildAdapterPosition(view);
                List<Formato> listaFormatosOriginales = adapterBusquedaFormatos.getListaFormatosOriginales();
                Formato formatoClickeado = listaFormatosOriginales.get(posicion);
                String nombre = formatoClickeado.getTitle();
                String tipoFormato;
                if (nombre == null || nombre.isEmpty()) {
                    tipoFormato = "serie";
                } else {
                    tipoFormato = "pelicula";
                }
                Notificable.recibirFormatoClickeado(formatoClickeado, controllerBusquedaFormatos.getNumeroPagina());
            }
        };

        adapterBusquedaFormatos.setListener(listener1);

        recyclerBusqueda.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                Integer ultimaPosicionVisible=layoutManagerBusqueda.findLastVisibleItemPosition();
                Integer cantidadItems=layoutManagerBusqueda.getItemCount();
                if (!isLoading) {
                    if (ultimaPosicionVisible >= cantidadItems - 2) {
                        pedirPaginaBuscador();
                    }
                }
                }
            });







        return view;

    }

    public void pedirPaginaBuscador() {
        if (drawerId == 0) {
            if (controllerBusquedaFormatos.isPageAvailable()) {
                isLoading = true;

                switch (formatoAMostrar) {
                    case 0:
                        controllerBusquedaFormatos.buscarPelicula(new ResultListener<List<Formato>>() {
                            @Override
                            public void finish(List<Formato> resultado) {

                                adapterBusquedaFormatos.addListaFormatosOriginales(resultado);
                                adapterBusquedaFormatos.notifyDataSetChanged();
                                isLoading = false;
                            }
                        }, stringABuscar);

                    case 1:
                        controllerBusquedaFormatos.buscarSerie(new ResultListener<List<Formato>>() {
                            @Override
                            public void finish(List<Formato> resultado) {

                                adapterBusquedaFormatos.addListaFormatosOriginales(resultado);
                                adapterBusquedaFormatos.notifyDataSetChanged();
                                isLoading = false;
                            }
                        }, stringABuscar);

                    case 2:
                        controllerBusquedaFormatos.buscarFavoritos(new ResultListener<List<Formato>>() {
                            @Override
                            public void finish(List<Formato> resultado) {
                                adapterBusquedaFormatos.addListaFormatosOriginales(resultado);
                                adapterBusquedaFormatos.notifyDataSetChanged();
                                isLoading = false;

                            }
                        }, stringABuscar);

                }



            }
            else {
                controllerBusquedaFormatos.traerBusquedaDrawer(drawerId);
            }
        }
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.elContext=context;
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
