package com.craps.myapplication.DAO.File;

import com.craps.myapplication.Model.Formato;
import com.craps.myapplication.R;
import com.craps.myapplication.Utils.HTTPConnectionManager;
import com.craps.myapplication.Utils.TMDBHelper;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

/**
 * Created by Martirio on 01/06/2017.
 */

public class DAOArchivo {

    //CREO UNA LISTA DE PELICULAS PARA QUE SE CREEN VARIOS FRAGMENTS UTILIZANDO ESTA INFORMACIÓN

    List<Formato> listaFormatos;

    public List<Formato> cargarFormatos() {
        listaFormatos = new ArrayList<>();


        return listaFormatos;
    }

    public List<String> cargarTipoFormato(){
        List<String> listaFragmentsMain = new ArrayList<>();
        listaFragmentsMain.add("peliculas");
        listaFragmentsMain.add("series");
        listaFragmentsMain.add("favoritos");
        listaFragmentsMain.add("trivia");
         return listaFragmentsMain;
    }


    public List<String> cargarTipoFormatoOffline(){
        List<String> listaFragmentsMain = new ArrayList<>();
        listaFragmentsMain.add("favoritos");
        listaFragmentsMain.add("peliculas");
        listaFragmentsMain.add("series");
        return listaFragmentsMain;
    }



    public List<String> cargarCategorias(){
        List<String> listaCategorias = new ArrayList<>();
        listaCategorias.add(TMDBHelper.MOVIE_GENRE_DRAMA);
        listaCategorias.add(TMDBHelper.MOVIE_GENRE_COMEDY);
        listaCategorias.add(null);
        return listaCategorias;
    }


}
