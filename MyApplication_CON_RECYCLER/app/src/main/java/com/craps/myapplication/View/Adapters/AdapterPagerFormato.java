package com.craps.myapplication.View.Adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.craps.myapplication.Model.Formato;
import com.craps.myapplication.View.Fragments.FragmentDetalle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pablo on 31/5/2017.
 */

public class AdapterPagerFormato extends FragmentStatePagerAdapter {

    //EL ADAPTER NECESITA SIEMPRE UNA LISTA DE FRAGMENTS PARA MOSTRAR
    private List<Fragment> listaFragments;
    private List<Formato> listaFormatos=new ArrayList<>();


    public AdapterPagerFormato(FragmentManager fm) {
        super(fm);



        //INICIALIZO LA LISTA DE FRAGMENT
        listaFragments = new ArrayList<>();

        //LE CARGO LOS FRAGMENTS QUE QUIERO. UTILIZO LA LISTA DE PELICULAS Y SERIES PARA CREAR LOS FRAGMENTS.

        for(Formato unFormato: listaFormatos){
            listaFragments.add(FragmentDetalle.fragmentDetalleCreator(unFormato));
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

    public void setListaFormatos(List<Formato> listaFormatos) {
        this.listaFormatos = listaFormatos;
        for(Formato unFormato: listaFormatos){
            listaFragments.add(FragmentDetalle.fragmentDetalleCreator(unFormato));
        }
        notifyDataSetChanged();
    }

    public void addListaFormatos(List<Formato> listaFormatos){
        this.listaFormatos.addAll(listaFormatos);
        for(Formato unFormato: listaFormatos){
            listaFragments.add(FragmentDetalle.fragmentDetalleCreator(unFormato));
        }
        notifyDataSetChanged();
    }
}
