package com.craps.myapplication.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Martirio on 07/07/2017.
 */

public class ContainerImagenes {


    @SerializedName("profiles")
    private List<Imagen> imagenesList;

    public void setImagenesList(List<Imagen> unaLista) {
        this.imagenesList = unaLista;
    }

    public List<Imagen> getImagenesList() {
        return imagenesList;
    }
}
