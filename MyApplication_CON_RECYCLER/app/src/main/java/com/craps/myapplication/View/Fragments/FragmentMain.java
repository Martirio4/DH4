package com.craps.myapplication.View.Fragments;



import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
public class FragmentMain extends Fragment {

    private List < Formato > listaFormatos;

    private Notificable notificable;
    private Boolean isLoading=false;


    public static final String QUEFORMATOMUESTRO = "peliculas";
    public static final String QUEFILTROAPLICOR1 = "quefiltroaplicor1";
    public static final String QUEFILTROAPLICOR2 = "quefiltroaplicor2";
    public static final String QUEFILTROAPLICOR3 = "quefiltroaplicor3";

    private String formatoAMostrar;

    private String filtroRecyclerSuperior;
    private String filtroRecyclerMedio;
    private String filtroRecyclerInferior;

    private AdapterFormato adapterRecyclerSuperior;
    private AdapterFormato adapterRecyclerMedio;
    private AdapterFormato adapterRecyclerInferior;

    private RecyclerView recyclerSuperior;
    private RecyclerView recyclerMedio;
    private RecyclerView recyclerInferior;

    private LinearLayoutManager layoutManagerSuperior;
    private LinearLayoutManager layoutManagerMedio;
    private LinearLayoutManager layoutManagerInferior;

    private ControllerFormato controllerRecyclerSuperior;
    private ControllerFormato controllerRecyclerMedio;
    private ControllerFormato controllerRecyclerInferior;








    //DECLARO INTERFAZ
    public interface Notificable {
        public void recibirFormatoClickeado(Formato formato,String origen, Integer pagina);
    }

    //ON CREATE
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //INFLAR LAYOUT



        final View view = inflater.inflate(R.layout.fragment_contenedor_maestro, container, false);

        //RECIBIR EL BUNDLE

        Bundle unbundle = getArguments();

        formatoAMostrar = unbundle.getString(QUEFORMATOMUESTRO);
        filtroRecyclerInferior = unbundle.getString(QUEFILTROAPLICOR1);
        filtroRecyclerMedio = unbundle.getString(QUEFILTROAPLICOR2);
        filtroRecyclerSuperior = unbundle.getString(QUEFILTROAPLICOR3);


        //HARCODEO LOS TITULOS DE LOS RECYCLER - REEMPLAZAR CON TMDBHELPER




        //SETEAR EL ADAPTER


        //-----------------//RECYCLER SUPERIOR//-------------------//

        //DECLARO Y CASTEO EL RECYCLER
        recyclerSuperior = (RecyclerView) view.findViewById(R.id.recycler1);
        recyclerSuperior.setHasFixedSize(true);
        layoutManagerSuperior=new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerSuperior.setLayoutManager(layoutManagerSuperior);
        adapterRecyclerSuperior = new AdapterFormato();
        adapterRecyclerSuperior.setContext(view.getContext());
        adapterRecyclerSuperior.setListaFormatosOriginales(new ArrayList<Formato>());

        //AGREGO LISTENER DE CLICKEO DE PELICULAS
        View.OnClickListener listenerRecyclerSuperior = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ESTO SE UTILIZA PARA OBTENER LA POSITION DE LO QUE FUE CLICKEADO.
                Integer posicion = recyclerSuperior.getChildAdapterPosition(view);
                Integer numeroPagina= (int) Math.ceil((posicion+1)/20.0);
                List < Formato > listaPeliculasOriginales = adapterRecyclerSuperior.getListaFormatosOriginales();
                Formato formatoClickeado = listaPeliculasOriginales.get(posicion);
                notificable.recibirFormatoClickeado(formatoClickeado, "superior", numeroPagina);
            }
        };

        //CARGAR DATOS
        adapterRecyclerSuperior.setListener(listenerRecyclerSuperior);
        recyclerSuperior.setAdapter(adapterRecyclerSuperior);
        controllerRecyclerSuperior= new ControllerFormato(view.getContext());

        pedirPaginaRecyclerSuperior();

        recyclerSuperior.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Integer ultimaPosicionVisible=layoutManagerSuperior.findLastVisibleItemPosition();
                Integer cantidadItems=layoutManagerSuperior.getItemCount();
                if (!isLoading){
                    if (ultimaPosicionVisible>= cantidadItems-2){
                        pedirPaginaRecyclerSuperior();
                    }
                }
            }
        });


        //-----------------//RECYCLER MEDIO//-------------------//

        //DECLARO Y CASTEO EL RECYCLER
        recyclerMedio = (RecyclerView) view.findViewById(R.id.recycler2);
        recyclerMedio.setHasFixedSize(true);
        layoutManagerMedio=new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerMedio.setLayoutManager(layoutManagerMedio);
        adapterRecyclerMedio = new AdapterFormato();
        adapterRecyclerMedio.setContext(view.getContext());
        adapterRecyclerMedio.setListaFormatosOriginales(new ArrayList<Formato>());
        //AGREGO LISTENER DE CLICKEO DE PELICULAS
        View.OnClickListener listenerRecyclerMedio = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ESTO SE UTILIZA PARA OBTENER LA POSITION DE LO QUE FUE CLICKEADO.
                Integer posicion = recyclerMedio.getChildAdapterPosition(view);
                Integer numeroPagina= (int) Math.ceil((posicion+1)/20.0);
                List < Formato > listaPeliculasOriginales = adapterRecyclerMedio.getListaFormatosOriginales();
                Formato formatoClickeado = listaPeliculasOriginales.get(posicion);
                notificable.recibirFormatoClickeado(formatoClickeado, "medio", numeroPagina);
            }
        };
        //CARGAR DATOS
        adapterRecyclerMedio.setListener(listenerRecyclerMedio);
        recyclerMedio.setAdapter(adapterRecyclerMedio);
        controllerRecyclerMedio= new ControllerFormato(view.getContext());

        pedirPaginaRecyclerMedio();

        recyclerMedio.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Integer ultimaPosicionVisible=layoutManagerMedio.findLastVisibleItemPosition();
                Integer cantidadItems=layoutManagerMedio.getItemCount();
                if (!isLoading){
                    if (ultimaPosicionVisible>= cantidadItems-2){
                        pedirPaginaRecyclerMedio();
                    }
                }
            }
        });


        //-----------------//RECYCLER INFERIOR//-------------------//

        //DECLARO Y CASTEO EL RECYCLER
        recyclerInferior = (RecyclerView) view.findViewById(R.id.recycler3);
        recyclerInferior.setHasFixedSize(true);
        layoutManagerInferior=new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerInferior.setLayoutManager(layoutManagerInferior);
        adapterRecyclerInferior = new AdapterFormato();
        adapterRecyclerInferior.setContext(view.getContext());
        adapterRecyclerInferior.setListaFormatosOriginales(new ArrayList<Formato>());
        //AGREGO LISTENER DE CLICKEO DE PELICULAS
        View.OnClickListener listenerRecyclerInferior = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ESTO SE UTILIZA PARA OBTENER LA POSITION DE LO QUE FUE CLICKEADO.
                Integer posicion = recyclerMedio.getChildAdapterPosition(view);
                Integer numeroPagina= (int) Math.ceil((posicion+1)/20.0);
                List < Formato > listaPeliculasOriginales = adapterRecyclerInferior.getListaFormatosOriginales();
                Formato formatoClickeado = listaPeliculasOriginales.get(posicion);
                notificable.recibirFormatoClickeado(formatoClickeado, "inferior",numeroPagina);
            }
        };
        //CARGAR DATOS
        adapterRecyclerInferior.setListener(listenerRecyclerInferior);
        recyclerInferior.setAdapter(adapterRecyclerInferior);
        controllerRecyclerInferior= new ControllerFormato(view.getContext());

        pedirPaginaRecyclerInferior();

        recyclerInferior.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Integer ultimaPosicionVisible=layoutManagerInferior.findLastVisibleItemPosition();
                Integer cantidadItems=layoutManagerInferior.getItemCount();
                if (!isLoading){
                    if (ultimaPosicionVisible>= cantidadItems-2){
                        pedirPaginaRecyclerInferior();
                    }
                }
            }
        });


        TextView tituloRecyclerSuperior = (TextView) view.findViewById(R.id.titulo_recycler_1);
        TextView tituloRecyclerMedio = (TextView) view.findViewById(R.id.titulo_recycler_2);
        TextView tituloRecyclerInferior = (TextView) view.findViewById(R.id.titulo_recycler_3);

        Typeface roboto = Typeface.createFromAsset(getContext().getAssets(),"fonts/Roboto-Light.ttf");
        tituloRecyclerSuperior.setTypeface(roboto);
        tituloRecyclerMedio.setTypeface(roboto);
        tituloRecyclerInferior.setTypeface(roboto);


        //HARCODEO LOS TITULOS HASTA QUE LOS PODAMOS TRAER CON FIREBASE Y JSON

        tituloRecyclerSuperior.setText("POPULAR");
        tituloRecyclerMedio.setText("COMEDIA");
        tituloRecyclerInferior.setText("DRAMA");


        /* //TITULOS PARAMETRIZADOS NO BORRAR
        tituloRecyclerSuperior.setText(filtroRecyclerSuperior);
        tituloRecyclerMedio.setText(filtroRecyclerMedio);
        tituloRecyclerInferior.setText(filtroRecyclerInferior);
        */
        return view;
    }

    public static FragmentMain crearFragmentMaestro(String queMostrar){
        FragmentMain contenedorMaestroFragment = new FragmentMain();
        ControllerFormato controllerFormato = new ControllerFormato(contenedorMaestroFragment.getContext());
        List<String> listaCategoria= controllerFormato.recibirCategorias();

        Bundle bundle = new Bundle();
        bundle.putString(FragmentMain.QUEFILTROAPLICOR1, listaCategoria.get(0));
        bundle.putString(FragmentMain.QUEFILTROAPLICOR2, listaCategoria.get(1));
        bundle.putString(FragmentMain.QUEFILTROAPLICOR3, listaCategoria.get(2));
        bundle.putString(FragmentMain.QUEFORMATOMUESTRO, queMostrar);

        contenedorMaestroFragment.setArguments(bundle);
        return contenedorMaestroFragment;

    }

    public void pedirPaginaRecyclerSuperior(){
        if (controllerRecyclerSuperior.isPageAvailable()) {
            isLoading = true;

            if (formatoAMostrar.equals("peliculas")) {
                controllerRecyclerSuperior.obtenerPeliculasPopulares(new ResultListener<List<Formato>>() {
                    @Override
                    public void finish(List<Formato> resultado) {

                        adapterRecyclerSuperior.addListaFormatosOriginales(resultado);
                        adapterRecyclerSuperior.notifyDataSetChanged();
                        isLoading = false;
                    }
                });
            } else {
                controllerRecyclerSuperior.obtenerSeriesPopulares(new ResultListener<List<Formato>>() {
                    @Override
                    public void finish(List<Formato> resultado) {

                        adapterRecyclerSuperior.addListaFormatosOriginales(resultado);
                        adapterRecyclerSuperior.notifyDataSetChanged();
                        isLoading = false;

                    }
                });
            }
        }
    }

    public void pedirPaginaRecyclerMedio(){
        if (controllerRecyclerMedio.isPageAvailable()) {
            isLoading = true;

            if (formatoAMostrar.equals("peliculas")) {
                controllerRecyclerMedio.obtenerPeliculasPorGenero(new ResultListener<List<Formato>>() {
                    @Override
                    public void finish(List<Formato> resultado) {

                        adapterRecyclerMedio.addListaFormatosOriginales(resultado);
                        adapterRecyclerMedio.notifyDataSetChanged();
                        isLoading = false;
                    }
                }, filtroRecyclerMedio);
            } else {
                controllerRecyclerMedio.obtenerSeriesPorGenero(new ResultListener<List<Formato>>() {
                    @Override
                    public void finish(List<Formato> resultado) {

                        adapterRecyclerMedio.addListaFormatosOriginales(resultado);
                        adapterRecyclerMedio.notifyDataSetChanged();
                        isLoading = false;
                    }
                }, TMDBHelper.MOVIE_GENRE_COMEDIA);
            }
        }
    }

    public void pedirPaginaRecyclerInferior(){
        if (controllerRecyclerInferior.isPageAvailable()) {
            isLoading=true;
            
            if (formatoAMostrar.equals("peliculas")) {
                controllerRecyclerInferior.obtenerPeliculasPorGenero(new ResultListener<List<Formato>>() {
                    @Override
                    public void finish(List<Formato> resultado) {

                        adapterRecyclerInferior.addListaFormatosOriginales(resultado);
                        adapterRecyclerInferior.notifyDataSetChanged();
                        isLoading = false;

                    }
                }, filtroRecyclerInferior);
            } else {
                controllerRecyclerInferior.obtenerSeriesPorGenero(new ResultListener<List<Formato>>() {
                    @Override
                    public void finish(List<Formato> resultado) {

                        adapterRecyclerInferior.addListaFormatosOriginales(resultado);
                        adapterRecyclerInferior.notifyDataSetChanged();
                        isLoading = false;

                    }
                }, TMDBHelper.MOVIE_GENRE_DRAMA);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.notificable = (Notificable) context;
    }



}
