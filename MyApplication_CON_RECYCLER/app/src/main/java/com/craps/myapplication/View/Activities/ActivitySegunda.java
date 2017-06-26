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

public class ActivitySegunda extends AppCompatActivity implements AdapterFormato.Favoritable, FragmentDetalle.FavoritableFav {

    public static final String IDFORMATO = "IDFORMATO";
    public static final String ORIGEN = "ORIGEN";
    public static final String PAGINA = "PAGINA";
    public static final String TIPOFORMATO = "TIPOFORMATO";

    private Boolean isLoading=false;
    private List<Formato> listaFormatos;


    private String origenFormato;
    private Integer numeroPagina;
    private Integer idFormato;
    private String tipoFormato;

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
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
                break;
            case "drawer":
                break;
            case"self":
                break;
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









































    @Override
    public void recibirFormatoFavorito(Formato unFormato) {
        if (ActivityMain.login==true){
            ControllerFormato controllerFormato= new ControllerFormato(this);
            controllerFormato.agregarFavorito(unFormato, ActivityMain.usuario);
        }
        else{
            Toast.makeText(this, "Funcion exclusiva para usuarios registrados", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void eliminarFormatoFavorito(Formato unFormato) {
        if (ActivityMain.login==true){
            ControllerFormato controllerFormato = new ControllerFormato(this);
            controllerFormato.eliminarFavorito(unFormato, ActivityMain.usuario);
        }
    }







}
