package com.craps.myapplication.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dh3 on 28/06/17.
 */

public class ContainerTrailer {


    @SerializedName("results")
    private List<Trailer> trailerList;

    public void setTrailerList(List<Trailer> unaLista) {
    this.trailerList = unaLista;
    }

    public List<Trailer> getTrailerList() {
    return trailerList;
    }

}


