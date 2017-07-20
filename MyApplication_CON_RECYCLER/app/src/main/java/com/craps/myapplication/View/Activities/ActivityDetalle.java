package com.craps.myapplication.View.Activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.craps.myapplication.ControllerFormato.ControllerFormato;
import com.craps.myapplication.Model.Actor;
import com.craps.myapplication.Model.Formato;
import com.craps.myapplication.R;
import com.craps.myapplication.Utils.ResultListener;
import com.craps.myapplication.Utils.TMDBHelper;
import com.craps.myapplication.View.Adapters.AdapterFormato;
import com.craps.myapplication.View.Adapters.AdapterPagerFormato;
import com.craps.myapplication.View.Fragments.FragmentDetalle;

import java.util.ArrayList;
import java.util.List;

public class ActivityDetalle extends AppCompatActivity implements FragmentDetalle.Actorable, FragmentDetalle.Notificable, ControllerFormato.Registrable, AdapterFormato.Favoritable, FragmentDetalle.FavoritableFav {

    public static final String IDFORMATO = "IDFORMATO";
    public static final String ORIGEN = "ORIGEN";
    public static final String PAGINA = "PAGINA";
    public static final String TIPOFORMATO = "TIPOFORMATO";
    public static final String STRINGBUSQUEDA = "STRINGBUSQUEDA";
    public static final String DRAWERID = "DRAWERID";
    public static final String CLAVEYOUTUBE = "CLAVEYOUTUBE";

    private Boolean isLoading = false;
    private List<Formato> listaFormatos;
    private Integer idActual;
    private String origenFormato;
    private Integer numeroPagina;
    private Integer idFormato;
    private String tipoFormato;
    private String stringABuscar;
    private Integer drawerId;
    private String claveYoutube;

    private ViewPager viewPagerDetalle;
    private AdapterPagerFormato adapterPagerDetalle;
    private ControllerFormato controllerDetalle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);

        // PABLO 1/1A
        // CREO EL APPBAR.
        Toolbar toolbar = (Toolbar) findViewById(R.id.ABappBar);
        setSupportActionBar(toolbar);
        // MUESTRO EL BOTON DE VOLVER ATRAS.
        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.marfil), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        // PABLO 1/1C
        //RECIBO BUNDLE Y BUSCO PELICULA CLICKEADA
        Intent unIntent = getIntent();
        Bundle unBundle = unIntent.getExtras();
        idFormato = unBundle.getInt(IDFORMATO);
        origenFormato = unBundle.getString(ORIGEN);
        numeroPagina = unBundle.getInt(PAGINA);
        tipoFormato = unBundle.getString(TIPOFORMATO);
        stringABuscar = unBundle.getString(STRINGBUSQUEDA);
        drawerId = unBundle.getInt(DRAWERID);
        claveYoutube = unBundle.getString(CLAVEYOUTUBE);

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
                if ((position >= (adapterPagerDetalle.getCount() - 2)) && (!origenFormato.equals("actores"))) {
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

    public void pedirListaSegunOrigen() {

        switch (origenFormato) {
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
                pedirPaginaDrawer();
                break;
            case "self":
                pedirPaginaSimilares();
                break;
            case "favoritos":
                pedirPaginaFavoritos();
                break;
            case "actores":
                pedirSimilaresCreditoActor();
                break;
        }
    }


    public void pedirPaginaFavoritos() {
        controllerDetalle.obtenerFavoritos(new ResultListener<List<Formato>>() {
            @Override
            public void finish(List<Formato> resultado) {
                adapterPagerDetalle.setListaFormatos(resultado);
                listaFormatos = resultado;
                for (Formato unFormato : listaFormatos
                        ) {
                    if (unFormato.getId().equals(idFormato)) {
                        viewPagerDetalle.setCurrentItem(listaFormatos.indexOf(unFormato), false);


                    }

                }

            }
        });
    }


    public void pedirPaginaSimilares() {
        if (controllerDetalle.isPageAvailable()) {
            isLoading = true;

            if (tipoFormato.equals("peliculas")) {
                controllerDetalle.obtenerPeliculasRelacionadas(new ResultListener<List<Formato>>() {
                    @Override
                    public void finish(List<Formato> resultado) {
                        adapterPagerDetalle.addListaFormatos(resultado);
                        listaFormatos = resultado;
                        for (final Formato unFormato : listaFormatos) {

                            if (unFormato.getId().equals(idFormato)) {
                                viewPagerDetalle.setCurrentItem(listaFormatos.indexOf(unFormato), false);

                            }
                        }
                        adapterPagerDetalle.notifyDataSetChanged();
                        isLoading = false;
                    }
                }, idFormato);

            } else {
                controllerDetalle.obtenerSeriesRelacionadas(new ResultListener<List<Formato>>() {
                    @Override
                    public void finish(List<Formato> resultado) {

                        adapterPagerDetalle.addListaFormatos(resultado);
                        listaFormatos = resultado;
                        for (final Formato unFormato : listaFormatos) {

                            if (unFormato.getId().equals(idFormato)) {
                                viewPagerDetalle.setCurrentItem(listaFormatos.indexOf(unFormato), false
                                );

                            }
                        }
                        adapterPagerDetalle.notifyDataSetChanged();
                        isLoading = false;
                    }
                }, idFormato);
            }
        }
    }


    public void pedirPaginaSimilarclickeado(final Formato unFormato, String origen, Integer pagina, String tipoDeFormato) {
        if (controllerDetalle.isPageAvailable()) {

            isLoading = true;

            if (tipoDeFormato.equals("peliculas")) {
                controllerDetalle.obtenerPeliculasRelacionadas(new ResultListener<List<Formato>>() {
                    @Override
                    public void finish(List<Formato> resultado) {
                        resultado.add(0, unFormato);
                        adapterPagerDetalle.addListaFormatos(resultado);
                        viewPagerDetalle.setAdapter(adapterPagerDetalle);
                        viewPagerDetalle.setCurrentItem(0, false);
                        isLoading = false;
                        adapterPagerDetalle.notifyDataSetChanged();

                    }
                }, unFormato.getId());


            } else {
                controllerDetalle.obtenerSeriesRelacionadas(new ResultListener<List<Formato>>() {
                    @Override
                    public void finish(List<Formato> resultado) {
                        resultado.add(0, unFormato);
                        adapterPagerDetalle.addListaFormatos(resultado);
                        viewPagerDetalle.setAdapter(adapterPagerDetalle);
                        viewPagerDetalle.setCurrentItem(0, false);
                        isLoading = false;
                        adapterPagerDetalle.notifyDataSetChanged();

                    }
                }, unFormato.getId());

            }
        }

    }

    public void pedirPaginaRecyclerSuperior() {
        if (controllerDetalle.isPageAvailable()) {
            isLoading = true;


            if (tipoFormato.equals("peliculas")) {
                controllerDetalle.obtenerPeliculasPopulares(new ResultListener<List<Formato>>() {
                    @Override
                    public void finish(List<Formato> resultado) {
                        adapterPagerDetalle.addListaFormatos(resultado);
                        listaFormatos = resultado;
                        for (final Formato unFormato : listaFormatos) {

                            if (unFormato.getId().equals(idFormato)) {
                                viewPagerDetalle.setCurrentItem(listaFormatos.indexOf(unFormato), false);

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
                        listaFormatos = resultado;
                        for (final Formato unFormato : listaFormatos) {

                            if (unFormato.getId().equals(idFormato)) {
                                viewPagerDetalle.setCurrentItem(listaFormatos.indexOf(unFormato), false);

                            }
                        }
                        adapterPagerDetalle.notifyDataSetChanged();
                        isLoading = false;
                    }
                });
            }
        }
    }

    public void pedirPaginaRecyclerMedio() {
        if (controllerDetalle.isPageAvailable()) {
            isLoading = true;

            if (tipoFormato.equals("peliculas")) {
                controllerDetalle.obtenerPeliculasPorGenero(new ResultListener<List<Formato>>() {
                    @Override
                    public void finish(List<Formato> resultado) {
                        adapterPagerDetalle.addListaFormatos(resultado);
                        listaFormatos = resultado;
                        for (final Formato unFormato : listaFormatos) {

                            if (unFormato.getId().equals(idFormato)) {
                                viewPagerDetalle.setCurrentItem(listaFormatos.indexOf(unFormato), false);

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
                        listaFormatos = resultado;
                        for (final Formato unFormato : listaFormatos) {

                            if (unFormato.getId().equals(idFormato)) {
                                viewPagerDetalle.setCurrentItem(listaFormatos.indexOf(unFormato), false);

                            }
                        }
                        adapterPagerDetalle.notifyDataSetChanged();
                        isLoading = false;
                    }
                }, TMDBHelper.MOVIE_GENRE_COMEDIA);
            }
        }
    }

    public void pedirPaginaRecyclerInferior() {
        if (controllerDetalle.isPageAvailable()) {
            isLoading = true;

            if (tipoFormato.equals("peliculas")) {
                controllerDetalle.obtenerPeliculasPorGenero(new ResultListener<List<Formato>>() {
                    @Override
                    public void finish(List<Formato> resultado) {
                        adapterPagerDetalle.addListaFormatos(resultado);
                        listaFormatos = resultado;
                        for (final Formato unFormato : listaFormatos) {

                            if (unFormato.getId().equals(idFormato)) {
                                viewPagerDetalle.setCurrentItem(listaFormatos.indexOf(unFormato), false);

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
                        listaFormatos = resultado;
                        for (final Formato unFormato : listaFormatos) {

                            if (unFormato.getId().equals(idFormato)) {
                                viewPagerDetalle.setCurrentItem(listaFormatos.indexOf(unFormato), false);

                            }
                        }
                        adapterPagerDetalle.notifyDataSetChanged();
                        isLoading = false;
                    }
                }, TMDBHelper.MOVIE_GENRE_DRAMA);
            }
        }
    }

    public void pedirPaginaDrawer() {

        if (controllerDetalle.isPageAvailable()) {
            isLoading = true;

            controllerDetalle.traerBusquedaDrawer(new ResultListener<List<Formato>>() {
                @Override
                public void finish(List<Formato> resultado) {
                    adapterPagerDetalle.addListaFormatos(resultado);
                    listaFormatos = resultado;
                    for (final Formato unFormato : listaFormatos) {

                        if (unFormato.getId().equals(idFormato)) {
                            viewPagerDetalle.setCurrentItem(listaFormatos.indexOf(unFormato), false);

                        }
                    }
                    adapterPagerDetalle.notifyDataSetChanged();
                    isLoading = false;
                }
            }, drawerId);
        }

    }

    public void pedirPaginaBuscador() {
        if (controllerDetalle.isPageAvailable()) {
            isLoading = true;

            switch (tipoFormato) {
                case "peliculas":
                    controllerDetalle.buscarPelicula(new ResultListener<List<Formato>>() {
                        @Override
                        public void finish(List<Formato> resultado) {

                            adapterPagerDetalle.addListaFormatos(resultado);
                            listaFormatos = resultado;
                            for (final Formato unFormato : listaFormatos) {

                                if (unFormato.getId().equals(idFormato)) {
                                    viewPagerDetalle.setCurrentItem(listaFormatos.indexOf(unFormato), false);

                                }
                            }
                            adapterPagerDetalle.notifyDataSetChanged();
                            isLoading = false;
                        }
                    }, stringABuscar);
                    break;

                case "series":
                    controllerDetalle.buscarSerie(new ResultListener<List<Formato>>() {
                        @Override
                        public void finish(List<Formato> resultado) {

                            adapterPagerDetalle.addListaFormatos(resultado);
                            listaFormatos = resultado;
                            for (final Formato unFormato : listaFormatos) {

                                if (unFormato.getId().equals(idFormato)) {
                                    viewPagerDetalle.setCurrentItem(listaFormatos.indexOf(unFormato), false);

                                }
                            }
                            adapterPagerDetalle.notifyDataSetChanged();
                            isLoading = false;
                        }
                    }, stringABuscar);
                    break;
                case "favoritos":
                    controllerDetalle.buscarFavoritos(new ResultListener<List<Formato>>() {
                        @Override
                        public void finish(List<Formato> resultado) {
                            adapterPagerDetalle.addListaFormatos(resultado);
                            listaFormatos = resultado;
                            for (final Formato unFormato : listaFormatos) {

                                if (unFormato.getId().equals(idFormato)) {
                                    viewPagerDetalle.setCurrentItem(listaFormatos.indexOf(unFormato), false);

                                }
                            }
                            adapterPagerDetalle.notifyDataSetChanged();
                            isLoading = false;

                        }
                    }, stringABuscar);
                    break;
            }
        }
    }


    @Override
    public void recibirFormatoFavorito(Formato unFormato) {

        ControllerFormato controllerFormato = new ControllerFormato(this);
        controllerFormato.agregarFavorito(unFormato, ActivityMain.usuario);


    }

    @Override
    public void eliminarFormatoFavorito(Formato unFormato) {
        if (ActivityMain.login == true) {
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
        adapterPagerDetalle = new AdapterPagerFormato(getSupportFragmentManager());
        controllerDetalle = new ControllerFormato(ActivityDetalle.this);
        String unTipoDeFormato;
        if (formato.getTitle() == null || formato.getTitle().isEmpty()) {
            unTipoDeFormato = "series";
        } else {
            unTipoDeFormato = "peliculas";
        }

        pedirPaginaSimilarclickeado(formato, origen, pagina, unTipoDeFormato);

    }

    @Override
    public void recibirActorClickeado(Actor unActor, Integer idFormato, String queFormato) {
        Intent unIntent = new Intent(this, ActivityActores.class);
        Bundle bundle = new Bundle();

        bundle.putInt(ActivityActores.ACTORID, unActor.getId());
        bundle.putInt(ActivityActores.FORMATOID, idFormato);
        bundle.putString(ActivityActores.QUEFORMATO, queFormato);

        unIntent.putExtras(bundle);
        startActivity(unIntent);
    }

    public void pedirSimilaresCreditoActor() {
        listaFormatos = new ArrayList<>();

        isLoading = true;
        controllerDetalle.traerUnFormato(new ResultListener<Formato>() {
            @Override
            public void finish(final Formato resultado1) {
                listaFormatos.add(0, resultado1);

                if (resultado1.getTitle() == null || resultado1.getTitle().isEmpty()) {
                    controllerDetalle.obtenerSeriesRelacionadas(new ResultListener<List<Formato>>() {
                        @Override
                        public void finish(List<Formato> resultado) {
                            listaFormatos.addAll(resultado);
                            adapterPagerDetalle.setListaFormatos(listaFormatos);
                            viewPagerDetalle.setCurrentItem(0, false);
                            adapterPagerDetalle.notifyDataSetChanged();
                            isLoading = false;
                        }
                    }, resultado1.getId());
                } else {
                    controllerDetalle.obtenerPeliculasRelacionadas(new ResultListener<List<Formato>>() {
                        @Override
                        public void finish(List<Formato> resultado) {
                            listaFormatos.addAll(resultado);
                            adapterPagerDetalle.setListaFormatos(listaFormatos);
                            viewPagerDetalle.setCurrentItem(0, false);
                            adapterPagerDetalle.notifyDataSetChanged();
                            isLoading = false;


                        }
                    }, resultado1.getId());
                }

            }
        }, idFormato, tipoFormato);

    }


}
