package com.craps.myapplication.Model;

import android.content.Intent;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Martirio on 22/06/2017.
 */

public class Cast {



    @SerializedName("character")
    private String personaje;
    @SerializedName("gender")
    private String genero;
    private Integer id;
    @SerializedName("name")
    private String nombreActor;
    @SerializedName("profile_path")
    private String fotoPerfilActor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreActor() {
        return nombreActor;
    }

    public void setNombreActor(String nombreActor) {
        this.nombreActor = nombreActor;
    }

    public String getFotoPerfilActor() {
        return fotoPerfilActor;
    }

    public void setFotoPerfilActor(String fotoPerfilActor) {
        this.fotoPerfilActor = fotoPerfilActor;
    }

    public String getPersonaje() {
        return personaje;
    }

    public void setPersonaje(String personaje) {
        this.personaje = personaje;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}
