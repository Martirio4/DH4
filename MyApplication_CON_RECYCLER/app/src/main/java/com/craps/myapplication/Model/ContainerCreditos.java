package com.craps.myapplication.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Martirio on 22/06/2017.
 */

public class ContainerCreditos {
        @SerializedName("cast")
        private List<Creditos> creditosList;

    public List<Creditos> getCreditosList() {
        return creditosList;
    }

    public void setActoresList(List<Creditos> unaLista) {
        creditosList = unaLista;
    }
}
