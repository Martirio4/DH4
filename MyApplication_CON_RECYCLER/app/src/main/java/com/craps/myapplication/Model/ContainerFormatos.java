package com.craps.myapplication.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by elmar on 10/6/2017.
 */

public class ContainerFormatos {
    @SerializedName("results")
    private List<Formato> formatoList;

    public ContainerFormatos() {
    }

    public void setFormatoList(List<Formato> formatoList) {
        this.formatoList = formatoList;
    }

    public List<Formato> getFormatoList() {
        return formatoList;
    }
}

