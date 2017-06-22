package com.craps.myapplication.View.Activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.craps.myapplication.ControllerFormato.ControllerFormato;
import com.craps.myapplication.Model.Formato;
import com.craps.myapplication.R;
import com.craps.myapplication.Utils.ResultListener;
import com.craps.myapplication.Utils.TMDBHelper;
import com.craps.myapplication.View.Adapters.AdapterFormato;
import com.craps.myapplication.View.Adapters.AdapterPagerFormato;
import com.craps.myapplication.View.Fragments.FragmentBusqueda;
import com.craps.myapplication.View.Fragments.FragmentDetalle;
import com.craps.myapplication.View.Fragments.FragmentFavoritos;

import java.util.ArrayList;
import java.util.List;

public class ActivitySegunda extends AppCompatActivity implements AdapterFormato.Favoritable, FragmentDetalle.FavoritableFav {

    public static final String IDFORMATO = "IDFORMATO";
    public static final String URLBUSQUEDA = "URLBUSQUEDA";

    private Integer idFormato;

    List<Formato> listaFormatos=new ArrayList<>();
    private String stringUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);




        //RECIBO BUNDLE Y BUSCO PELICULA CLICKEADA
        Intent unIntent = getIntent();
        Bundle unBundle = unIntent.getExtras();
        idFormato=unBundle.getInt(IDFORMATO);
        stringUrl=unBundle.getString(URLBUSQUEDA);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPagerDetalle);
        //LE SETEO EL ADAPTER AL VIEW PAGER, EL ADAPTER UTILIZA EL FRAGMENT MANAGER PARA CARGAR FRAGMENT Y LA LISTA DE PELICULAS PARA CREAR LOS FRAGMENTS CORRESPONDIENTES
        final AdapterPagerFormato adapterPagerFormato = new AdapterPagerFormato(getSupportFragmentManager());
        viewPager.setAdapter(adapterPagerFormato);

        //BUSCO EL VIEW PAGER DE PELICULAS
        ControllerFormato controllerFormato = new ControllerFormato(this);


        // agregar: Sivengo de la 3 activity buscador

            if (stringUrl==null){
                controllerFormato.obtenerFavoritos(new ResultListener<List<Formato>>() {
                    @Override
                    public void finish(List<Formato> resultado) {
                        adapterPagerFormato.setListaFormatos(resultado);
                        listaFormatos = resultado;

                        for (final Formato unFormato : listaFormatos) {

                            if (unFormato.getId().equals(idFormato)) {
                                viewPager.setCurrentItem(listaFormatos.indexOf(unFormato));
                            }
                        }

                        adapterPagerFormato.notifyDataSetChanged();
                    }
                },ActivityMain.usuario);
            }
            else {


                controllerFormato.obtenerFormatos(new ResultListener<List<Formato>>() {
                    @Override
                    public void finish(List<Formato> resultado) {
                        adapterPagerFormato.setListaFormatos(resultado);
                        listaFormatos = resultado;

                        for (final Formato unFormato : listaFormatos) {

                            if (unFormato.getId().equals(idFormato)) {
                                viewPager.setCurrentItem(listaFormatos.indexOf(unFormato));
                            }
                        }

                        adapterPagerFormato.notifyDataSetChanged();
                    }
                }, stringUrl);
            }

        //@Override
        //public void recibirPeliFavorita (String unString){
        //    Toast.makeText(this, "soy la activity detalle, recibi un favorito: " + unString, Toast.LENGTH_SHORT).show();
        //}
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
