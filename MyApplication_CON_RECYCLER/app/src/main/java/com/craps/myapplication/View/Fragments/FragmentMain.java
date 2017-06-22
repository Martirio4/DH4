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
import com.craps.myapplication.View.Activities.ActivityMain;
import com.craps.myapplication.View.Adapters.AdapterFormato;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMain extends Fragment {

    private List < Formato > listaFormatos;
    private AdapterFormato unadapter1;
    private AdapterFormato unadapter2;
    private AdapterFormato unadapter3;
    private Notificable notificable;


    public static final String QUEFORMATOMUESTRO = "peliculas";
    public static final String QUEFILTROAPLICOR1 = "quefiltroaplicor1";
    public static final String QUEFILTROAPLICOR2 = "quefiltroaplicor2";
    public static final String QUEFILTROAPLICOR3 = "quefiltroaplicor3";

    private String formatoAMostrar;
    private String filtroR1;
    private String filtroR2;
    private String filtroR3;
    private List<Formato> lista1;
    private List<Formato> lista2;
    private List<Formato> lista3;

    String stringUrl;
    String stringUr2;
    String stringUr3;





    //DECLARO INTERFAZ
    public interface Notificable {
        public void recibirFormatoClickeado(Formato formato, String url);
        public void recibirFormatoFavorito(Formato unFormato);

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
        filtroR3= unbundle.getString(QUEFILTROAPLICOR1);
        filtroR2= unbundle.getString(QUEFILTROAPLICOR2);
        filtroR1= unbundle.getString(QUEFILTROAPLICOR3);

        //SETEAR EL ADAPTER
        final RecyclerView recycler1 = (RecyclerView) view.findViewById(R.id.recycler1);
        final RecyclerView recycler2 = (RecyclerView) view.findViewById(R.id.recycler2);
        final RecyclerView recycler3 = (RecyclerView) view.findViewById(R.id.recycler3);

        recycler1.setHasFixedSize(true);
        recycler2.setHasFixedSize(true);
        recycler3.setHasFixedSize(true);

        recycler1.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        recycler2.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        recycler3.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));

        unadapter1 = new AdapterFormato();
        unadapter2 = new AdapterFormato();
        unadapter3 = new AdapterFormato();

        unadapter1.setContext(view.getContext());
        unadapter2.setContext(view.getContext());
        unadapter3.setContext(view.getContext());




        //AGREGO LISTENER DE CLICKEO DE PELICULAS
        View.OnClickListener listener1 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ESTO SE UTILIZA PARA OBTENER LA POSITION DE LO QUE FUE CLICKEADO.
                Integer posicion = recycler1.getChildAdapterPosition(view);
                List < Formato > listaPeliculasOriginales = unadapter1.getListaFormatosOriginales();
                Formato formatoClickeado = listaPeliculasOriginales.get(posicion);
                notificable.recibirFormatoClickeado(formatoClickeado, stringUrl);
            }
        };


        //AGREGO LISTENER DE CLICKEO DE PELICULAS
        View.OnClickListener listener2 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ESTO SE UTILIZA PARA OBTENER LA POSITION DE LO QUE FUE CLICKEADO.
                Integer posicion = recycler2.getChildAdapterPosition(view);
                List < Formato > listaPeliculasOriginales = unadapter2.getListaFormatosOriginales();
                Formato formatoClickeado = listaPeliculasOriginales.get(posicion);
                notificable.recibirFormatoClickeado(formatoClickeado, stringUr2);
            }
        };



        //AGREGO LISTENER DE CLICKEO DE PELICULAS
        View.OnClickListener listener3 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ESTO SE UTILIZA PARA OBTENER LA POSITION DE LO QUE FUE CLICKEADO.
                Integer posicion = recycler3.getChildAdapterPosition(view);
                List < Formato > listaPeliculasOriginales = unadapter3.getListaFormatosOriginales();
                Formato formatoClickeado = listaPeliculasOriginales.get(posicion);
                notificable.recibirFormatoClickeado(formatoClickeado, stringUr3);
            }
        };



        unadapter1.setListener(listener1);

        unadapter2.setListener(listener2);

        unadapter3.setListener(listener3);


         recycler1.setAdapter(unadapter1);
         recycler2.setAdapter(unadapter2);
         recycler3.setAdapter(unadapter3);

        //cargar datos


        //aca tengo que cambiar para que tome datos de inet
        final ControllerFormato controllerFormato = new ControllerFormato(view.getContext());


        //cuales son las url que voy a pegar en el api

        if (formatoAMostrar.toLowerCase().equals("peliculas")) {
            stringUrl = TMDBHelper.getPopularMovies(TMDBHelper.language_SPANISH, 1);
            stringUr2 = TMDBHelper.getMoviesByGenre(filtroR2, 1, TMDBHelper.language_SPANISH);
            stringUr3 = TMDBHelper.getMoviesByGenre(filtroR3, 1, TMDBHelper.language_SPANISH);
        }
        else{
            stringUrl= TMDBHelper.getTVPopular(TMDBHelper.language_SPANISH, 1);
            stringUr2= TMDBHelper.getTVByGenre(filtroR2, 1, TMDBHelper.language_SPANISH);
            stringUr3=TMDBHelper.getTVByGenre(filtroR3, 1, TMDBHelper.language_SPANISH);
        }


        lista1 =new ArrayList<>();
        lista2 =new ArrayList<>();
        lista3 =new ArrayList<>();


        controllerFormato.obtenerFormatos(new ResultListener<List<Formato>>() {
            @Override
            public void finish(List<Formato> resultado) {

                unadapter1.setListaFormatosOriginales(resultado);
                lista1=resultado;
                unadapter1.notifyDataSetChanged();
            }
        }, stringUrl);

        controllerFormato.obtenerFormatos(new ResultListener<List<Formato>>() {
                @Override
                public void finish(List<Formato> resultado) {
                    unadapter2.setListaFormatosOriginales(resultado);
                    lista2=resultado;
                    unadapter2.notifyDataSetChanged();
                }
        }, stringUr2);

        controllerFormato.obtenerFormatos(new ResultListener<List<Formato>>() {
                @Override
                public void finish(List<Formato> resultado) {

                    unadapter3.setListaFormatosOriginales(resultado);
                    lista3=resultado;
                    unadapter3.notifyDataSetChanged();
                }
        }, stringUr3);

        unadapter1.setListaFormatosOriginales(lista1);
        unadapter2.setListaFormatosOriginales(lista2);
        unadapter3.setListaFormatosOriginales(lista3);

        unadapter1.notifyDataSetChanged();
        unadapter2.notifyDataSetChanged();
        unadapter3.notifyDataSetChanged();

        TextView tituloR1 = (TextView) view.findViewById(R.id.titulo_recycler_1);
        TextView tituloR2 = (TextView) view.findViewById(R.id.titulo_recycler_2);
        TextView tituloR3 = (TextView) view.findViewById(R.id.titulo_recycler_3);

        Typeface roboto = Typeface.createFromAsset(getContext().getAssets(),"fonts/Roboto-Light.ttf");
        tituloR1.setTypeface(roboto);
        tituloR2.setTypeface(roboto);
        tituloR3.setTypeface(roboto);



        tituloR3.setText("DRAMA");
        tituloR1.setText("POPULARES");
        tituloR2.setText("COMEDIA");



        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.notificable = (Notificable) context;
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


    public void cargarLista1 (List<Formato> unaLista){
        lista1=new ArrayList<>();
        lista1=unaLista;
    }
    public void cargarLista2 (List<Formato> unaLista){
        lista2=new ArrayList<>();
        lista2=unaLista;
    }
    public void cargarLista3 (List<Formato> unaLista){
        lista3=new ArrayList<>();
        lista3=unaLista;
    }

}
