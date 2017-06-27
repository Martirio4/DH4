package com.craps.myapplication.View.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.Toast;
import com.craps.myapplication.ControllerFormato.ControllerFormato;
import com.craps.myapplication.Model.Formato;
import com.craps.myapplication.R;
import com.craps.myapplication.Utils.ResultListener;
import com.craps.myapplication.Utils.TMDBHelper;
import com.craps.myapplication.View.Adapters.AdapterFormato;
import com.craps.myapplication.View.Adapters.AdapterPagerFormato;
import com.craps.myapplication.View.Fragments.FragmentDetalle;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class ActivitySegunda extends AppCompatActivity implements FragmentDetalle.Notificable,ControllerFormato.Registrable,AdapterFormato.Favoritable, FragmentDetalle.FavoritableFav {

    public static final String IDFORMATO = "IDFORMATO";
    public static final String ORIGEN = "ORIGEN";
    public static final String PAGINA = "PAGINA";
    public static final String TIPOFORMATO = "TIPOFORMATO";
    public static final String STRINGBUSQUEDA="STRINGBUSQUEDA";
    public static final String DRAWERID="DRAWERID";

    private Boolean isLoading=false;
    private List<Formato> listaFormatos;


    private String origenFormato;
    private Integer numeroPagina;
    private Integer idFormato;
    private String tipoFormato;
    private String stringABuscar;
    private Integer drawerId;

    ViewPager viewPagerDetalle;
    AdapterPagerFormato adapterPagerDetalle;
    ControllerFormato controllerDetalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);

        //RECIBO BUNDLE Y BUSCO PELICULA CLICKEADA
        Intent unIntent = getIntent();
        Bundle unBundle = unIntent.getExtras();
        idFormato=unBundle.getInt(IDFORMATO);
        origenFormato =unBundle.getString(ORIGEN);
        numeroPagina =unBundle.getInt(PAGINA);
        tipoFormato=unBundle.getString(TIPOFORMATO);
        stringABuscar=unBundle.getString(STRINGBUSQUEDA);
        drawerId=unBundle.getInt(DRAWERID);

        viewPagerDetalle = (ViewPager) findViewById(R.id.viewPagerDetalle);
        //LE SETEO EL ADAPTER AL VIEW PAGER, EL ADAPTER UTILIZA EL FRAGMENT MANAGER PARA CARGAR FRAGMENT Y LA LISTA DE PELICULAS PARA CREAR LOS FRAGMENTS CORRESPONDIENTES
        adapterPagerDetalle = new AdapterPagerFormato(getSupportFragmentManager());
        viewPagerDetalle.setAdapter(adapterPagerDetalle);

        controllerDetalle = new ControllerFormato(this);

        controllerDetalle.setNumeroPagina(numeroPagina);
        pedirListaSegunOrigen();

        viewPagerDetalle.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position>=(adapterPagerDetalle.getCount()-2)){
                    pedirListaSegunOrigen();
                }

                adapterPagerDetalle.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                adapterPagerDetalle.notifyDataSetChanged();
            }
        });



    }

    public void pedirListaSegunOrigen(){
        switch (origenFormato){
            case "superior":
                pedirPaginaRecyclerSuperior();
                break;
            case "medio":
                pedirPaginaRecyclerMedio();
                break;
            case "inferior":
                pedirPaginaRecyclerInferior();
                break;
            case "busqueda":
                pedirPaginaBuscador();
                break;
            case "drawer":
                break;
            case"self":
                pedirPaginaSimilares();
                break;
        }
    }

    public void pedirPaginaSimilares(){


        if (controllerDetalle.isPageAvailable()) {
            isLoading = true;

            if (tipoFormato.equals("peliculas")) {
                controllerDetalle.obtenerPeliculasRelacionadas(new ResultListener<List<Formato>>() {
                    @Override
                    public void finish(List<Formato> resultado) {
                        adapterPagerDetalle.addListaFormatos(resultado);
                        adapterPagerDetalle.notifyDataSetChanged();
                        isLoading = false;
                    }
                },idFormato);

            } else {
                controllerDetalle.obtenerSeriesRelacionadas(new ResultListener<List<Formato>>() {
                    @Override
                    public void finish(List<Formato> resultado) {

                        adapterPagerDetalle.addListaFormatos(resultado);
                        adapterPagerDetalle.notifyDataSetChanged();
                        isLoading = false;
                    }
                },idFormato);
            }
        }
    }

    public void pedirPaginaRecyclerSuperior(){
        if (controllerDetalle.isPageAvailable()) {
            isLoading = true;


            if (tipoFormato.equals("peliculas")) {
                controllerDetalle.obtenerPeliculasPopulares(new ResultListener<List<Formato>>() {
                    @Override
                    public void finish(List<Formato> resultado) {
                        adapterPagerDetalle.addListaFormatos(resultado);
                        listaFormatos=resultado;
                        for (final Formato unFormato : listaFormatos) {

                            if (unFormato.getId().equals(idFormato)) {
                                viewPagerDetalle.setCurrentItem(listaFormatos.indexOf(unFormato));
                            }
                        }
                        adapterPagerDetalle.notifyDataSetChanged();
                        isLoading = false;
                    }
                });
            } else {
                controllerDetalle.obtenerSeriesPopulares(new ResultListener<List<Formato>>() {
                    @Override
                    public void finish(List<Formato> resultado) {
                        adapterPagerDetalle.addListaFormatos(resultado);
                        listaFormatos=resultado;
                        for (final Formato unFormato : listaFormatos) {

                            if (unFormato.getId().equals(idFormato)) {
                                viewPagerDetalle.setCurrentItem(listaFormatos.indexOf(unFormato));
                            }
                        }
                        adapterPagerDetalle.notifyDataSetChanged();
                        isLoading = false;
                    }
                });
            }
        }
    }

    public void pedirPaginaRecyclerMedio(){
        if (controllerDetalle.isPageAvailable()) {
            isLoading = true;

            if (tipoFormato.equals("peliculas")) {
                controllerDetalle.obtenerPeliculasPorGenero(new ResultListener<List<Formato>>() {
                    @Override
                    public void finish(List<Formato> resultado) {
                        adapterPagerDetalle.addListaFormatos(resultado);
                        listaFormatos=resultado;
                        for (final Formato unFormato : listaFormatos) {

                            if (unFormato.getId().equals(idFormato)) {
                                viewPagerDetalle.setCurrentItem(listaFormatos.indexOf(unFormato));
                            }
                        }
                        adapterPagerDetalle.notifyDataSetChanged();
                        isLoading = false;
                    }
                }, TMDBHelper.MOVIE_GENRE_COMEDIA);
            } else {
                controllerDetalle.obtenerSeriesPorGenero(new ResultListener<List<Formato>>() {
                    @Override
                    public void finish(List<Formato> resultado) {
                        adapterPagerDetalle.addListaFormatos(resultado);
                        listaFormatos=resultado;
                        for (final Formato unFormato : listaFormatos) {

                            if (unFormato.getId().equals(idFormato)) {
                                viewPagerDetalle.setCurrentItem(listaFormatos.indexOf(unFormato));
                            }
                        }
                        adapterPagerDetalle.notifyDataSetChanged();
                        isLoading = false;
                    }
                },TMDBHelper.MOVIE_GENRE_COMEDIA);
            }
        }
    }

    public void pedirPaginaRecyclerInferior(){
        if (controllerDetalle.isPageAvailable()) {
            isLoading = true;

            if (tipoFormato.equals("peliculas")) {
                controllerDetalle.obtenerPeliculasPorGenero(new ResultListener<List<Formato>>() {
                    @Override
                    public void finish(List<Formato> resultado) {
                        adapterPagerDetalle.addListaFormatos(resultado);
                        listaFormatos=resultado;
                        for (final Formato unFormato : listaFormatos) {

                            if (unFormato.getId().equals(idFormato)) {
                                viewPagerDetalle.setCurrentItem(listaFormatos.indexOf(unFormato));
                            }
                        }
                        adapterPagerDetalle.notifyDataSetChanged();
                        isLoading = false;
                    }
                }, TMDBHelper.MOVIE_GENRE_DRAMA);
            } else {
                controllerDetalle.obtenerSeriesPorGenero(new ResultListener<List<Formato>>() {
                    @Override
                    public void finish(List<Formato> resultado) {
                        adapterPagerDetalle.addListaFormatos(resultado);
                        listaFormatos=resultado;
                        for (final Formato unFormato : listaFormatos) {

                            if (unFormato.getId().equals(idFormato)) {
                                viewPagerDetalle.setCurrentItem(listaFormatos.indexOf(unFormato));
                            }
                        }
                        adapterPagerDetalle.notifyDataSetChanged();
                        isLoading = false;
                    }
                },TMDBHelper.MOVIE_GENRE_DRAMA);
            }
        }
    }

    public void pedirPaginaBuscador(){
        if (controllerDetalle.isPageAvailable()) {
            isLoading = true;

            switch (tipoFormato) {
                case "peliculas":
                    controllerDetalle.buscarPelicula(new ResultListener<List<Formato>>() {
                        @Override
                        public void finish(List<Formato> resultado) {

                            adapterPagerDetalle.addListaFormatos(resultado);
                            adapterPagerDetalle.notifyDataSetChanged();
                            isLoading = false;
                        }
                    },stringABuscar);

                case "series":
                    controllerDetalle.buscarSerie(new ResultListener<List<Formato>>() {
                        @Override
                        public void finish(List<Formato> resultado) {

                            adapterPagerDetalle.addListaFormatos(resultado);
                            adapterPagerDetalle.notifyDataSetChanged();
                            isLoading = false;
                        }
                    },stringABuscar);

                case "favoritos":
                    controllerDetalle.buscarFavoritos(new ResultListener<List<Formato>>() {
                        @Override
                        public void finish(List<Formato> resultado) {
                            adapterPagerDetalle.addListaFormatos(resultado);
                            adapterPagerDetalle.notifyDataSetChanged();
                            isLoading = false;

                        }
                    }, stringABuscar);
            }
        }
    }


    public void reemplazarFragmentDetalle(Formato formato, String origen, Integer numeroPagina,String stringABuscar, Integer drawerId) {
        Intent unIntent = new Intent(this, ActivitySegunda.class);
        Bundle unBundle = new Bundle();
        unBundle.putString(ActivitySegunda.ORIGEN, origen);
        unBundle.putInt(ActivitySegunda.IDFORMATO, formato.getId());
        unBundle.putInt(ActivitySegunda.PAGINA, numeroPagina);
        unBundle.putString(ActivitySegunda.STRINGBUSQUEDA, stringABuscar);
        unBundle.putInt(ActivitySegunda.DRAWERID, drawerId);
        if (formato.getTitle()==null||formato.getTitle().isEmpty()){
            unBundle.putString(ActivitySegunda.TIPOFORMATO, "series");
        }
        else{
            unBundle.putString(ActivitySegunda.TIPOFORMATO, "peliculas");
        }
        unIntent.putExtras(unBundle);
        startActivity(unIntent);
    }


    @Override
    public void recibirFormatoFavorito(Formato unFormato) {

            ControllerFormato controllerFormato= new ControllerFormato(this);
            controllerFormato.agregarFavorito(unFormato, ActivityMain.usuario);


    }

    @Override
    public void eliminarFormatoFavorito(Formato unFormato) {
        if (ActivityMain.login==true){
            ControllerFormato controllerFormato = new ControllerFormato(this);
            controllerFormato.eliminarFavorito(unFormato, ActivityMain.usuario);
        }
    }
    @Override
    public void solicitarRegistro() {
        Intent unIntent = new Intent(this, ActivityRegister.class);
        startActivity(unIntent);
    }
    @Override
    public void recibirFormatoClickeado(Formato formato, String origen, Integer pagina, String elString, Integer drawerId) {

    }
}
