package com.craps.myapplication.Model;

import android.content.Intent;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Martirio on 22/06/2017.
 */

public class Actor {



    @SerializedName("character")
    private String personaje;

    private Integer id;
    @SerializedName("name")
    private String nombreActor;
    @SerializedName("profile_path")
    private String fotoPerfilActor;
    @SerializedName("biography")
    private String biografia;
    @SerializedName("birthday")
    private String cumpleaños;
    @SerializedName("deathday")
    private String fechaMuerte;
    @SerializedName("gender")
    private String genero;
    @SerializedName("homepage")
    private String paginaWeb;
    @SerializedName("place_of_birth")
    private String lugarDeNacimiento;
    @SerializedName("popularity")
    private Float popularidad;

    public Float getPopularidad() {
        return popularidad;
    }

    public void setPopularidad(Float popularidad) {
        this.popularidad = popularidad;
    }




    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getCumpleaños() {
        return cumpleaños;
    }

    public void setCumpleaños(String cumpleaños) {
        this.cumpleaños = cumpleaños;
    }

    public String getFechaMuerte() {
        return fechaMuerte;
    }

    public void setFechaMuerte(String fechaMuerte) {
        this.fechaMuerte = fechaMuerte;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    public String getLugarDeNacimiento() {
        return lugarDeNacimiento;
    }

    public void setLugarDeNacimiento(String lugarDeNacimiento) {
        this.lugarDeNacimiento = lugarDeNacimiento;
    }

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
