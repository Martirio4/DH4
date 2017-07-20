package com.craps.myapplication.View.Adapters;


import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.Switch;

import com.craps.myapplication.Utils.HTTPConnectionManager;
import com.craps.myapplication.View.Activities.ActivityMain;
import com.craps.myapplication.View.Fragments.FragmentFavoritos;
import com.craps.myapplication.View.Fragments.FragmentFavoritosSinUsuario;
import com.craps.myapplication.View.Fragments.FragmentMain;
import com.craps.myapplication.View.Fragments.FragmentSinConexion;
import com.craps.myapplication.View.Fragments.FragmentTrivia;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.HTTP;

/**
 * Created by elmar on 3/6/2017.
 */

public class AdapterPagerMaestro extends FragmentStatePagerAdapter {

    private List<android.support.v4.app.Fragment> listaContenedoresMaestros;
    private List<String> listaTitulos = new ArrayList<>();
    protected Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public AdapterPagerMaestro(FragmentManager fm, List<String> listaFragmentMain, Context context) {
        super(fm);
        //guardo los titulos
        listaTitulos = listaFragmentMain;

        //INICIALIZO LA LISTA DE FRAGMENT
        listaContenedoresMaestros = new ArrayList<>();

        //LE CARGO LOS FRAGMENTS QUE QUIERO. UTILIZO LA LISTA DE PELICULAS Y SERIES PARA CREAR LOS FRAGMENTS.

        //if (HTTPConnectionManager.isNetworkingOnline(context)) {
        for (String unString : listaFragmentMain) {
            switch (unString) {
                case "favoritos":
                    if (ActivityMain.login == true) {
                        listaContenedoresMaestros.add(FragmentFavoritos.crearFragmentMaestro());
                    } else {
                        listaContenedoresMaestros.add(FragmentFavoritosSinUsuario.crearFragmentMaestro());
                    }
                    break;
                case "trivia":
                    if (ActivityMain.login == true) {
                        listaContenedoresMaestros.add(FragmentTrivia.crearFragmentMaestro());
                    } else {
                        listaContenedoresMaestros.add(FragmentFavoritosSinUsuario.crearFragmentMaestro());
                    }

                    break;
                default:
                    if (HTTPConnectionManager.isNetworkingOnline(context)) {
                        listaContenedoresMaestros.add((FragmentMain.crearFragmentMaestro(unString)));
                    } else {
                        listaContenedoresMaestros.add(FragmentSinConexion.crearFragmentMaestro());
                    }
                    break;
            }
        }
        //}
        //else{
        // listaContenedoresMaestros.add(FragmentSinConexion.crearFragmentMaestro());
        // listaContenedoresMaestros.add(FragmentFavoritos.crearFragmentMaestro());
        //}

        //LE AVISO AL ADAPTER QUE CAMBIO SU LISTA DE FRAGMENTS.
        notifyDataSetChanged();
    }


    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return listaContenedoresMaestros.get(position);
    }

    @Override
    public int getCount() {
        return listaContenedoresMaestros.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listaTitulos.get(position);
    }
}

