package com.craps.myapplication.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Martirio on 22/06/2017.
 */

public class ContainerActores {
        @SerializedName("cast")
        private List<Actor> ActoresList;

    public List<Actor> getActoresList() {
        return ActoresList;
    }

    public void setActoresList(List<Actor> actoresList) {
        ActoresList = actoresList;
    }
}
