package com.craps.myapplication.View.Adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.craps.myapplication.Model.Actor;
import com.craps.myapplication.View.Fragments.FragmentActores;
import com.craps.myapplication.View.Fragments.FragmentDetalle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pablo on 31/5/2017.
 */

public class AdapterPagerActores extends FragmentStatePagerAdapter {

    //EL ADAPTER NECESITA SIEMPRE UNA LISTA DE FRAGMENTS PARA MOSTRAR
    private List<Fragment> listaFragments;
    private List<Actor> listaActores = new ArrayList<>();


    public AdapterPagerActores(FragmentManager fm) {
        super(fm);

        //INICIALIZO LA LISTA DE FRAGMENT
        listaFragments = new ArrayList<>();

        //LE CARGO LOS FRAGMENTS QUE QUIERO. UTILIZO LA LISTA DE PELICULAS Y SERIES PARA CREAR LOS FRAGMENTS.

        for (Actor unActor : listaActores) {
            listaFragments.add(FragmentActores.fragmentActoresCreator(unActor.getId()));
        }

        //LE AVISO AL ADAPTER QUE CAMBIO SU LISTA DE FRAGMENTS.
        notifyDataSetChanged();
    }


    @Override
    public Fragment getItem(int position) {
        return listaFragments.get(position);
    }

    @Override
    public int getCount() {
        return listaFragments.size();
    }

    public void setListaFormatos(List<Actor> listaActores) {
        this.listaActores = listaActores;
        for (Actor unActor : listaActores) {
            listaFragments.add(FragmentActores.fragmentActoresCreator(unActor.getId()));
        }
        notifyDataSetChanged();
    }

    public void addListaFormatos(List<Actor> listaActores) {
        this.listaActores.addAll(listaActores);
        for (Actor unActor : listaActores) {
            listaFragments.add(FragmentActores.fragmentActoresCreator(unActor.getId()));
        }
        notifyDataSetChanged();
    }


}
