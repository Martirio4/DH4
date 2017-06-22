package com.craps.myapplication.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Martirio on 22/06/2017.
 */

public class Actor {



    private Integer id;

    @SerializedName("also_known_as")
            private String aliasActor;
    @SerializedName("biography")
            private String biografiaActor;
    @SerializedName("birthdate")
            private String fechaNacimientoActor;
    @SerializedName("place_of_birth")
            private String lugarNacimientoActor;
    @SerializedName("deathday")
            private String fechaDecesoActor;
    @SerializedName("gender")
            private String sexoActor;
    @SerializedName("homepage")
            private String paginaPersonalActor;
    @SerializedName("name")
            private String nombreActor;
    @SerializedName("imdb_id")
            private Integer idActorImdb;
    @SerializedName("popularity")
            private Float popularidad;
    @SerializedName("profile_path")
            private String fotoPerfilActor;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAliasActor() {
        return aliasActor;
    }

    public void setAliasActor(String aliasActor) {
        this.aliasActor = aliasActor;
    }

    public String getBiografiaActor() {
        return biografiaActor;
    }

    public void setBiografiaActor(String biografiaActor) {
        this.biografiaActor = biografiaActor;
    }

    public String getFechaNacimientoActor() {
        return fechaNacimientoActor;
    }

    public void setFechaNacimientoActor(String fechaNacimientoActor) {
        this.fechaNacimientoActor = fechaNacimientoActor;
    }

    public String getLugarNacimientoActor() {
        return lugarNacimientoActor;
    }

    public void setLugarNacimientoActor(String lugarNacimientoActor) {
        this.lugarNacimientoActor = lugarNacimientoActor;
    }

    public String getFechaDecesoActor() {
        return fechaDecesoActor;
    }

    public void setFechaDecesoActor(String fechaDecesoActor) {
        this.fechaDecesoActor = fechaDecesoActor;
    }

    public String getSexoActor() {
        return sexoActor;
    }

    public void setSexoActor(String sexoActor) {
        this.sexoActor = sexoActor;
    }

    public String getPaginaPersonalActor() {
        return paginaPersonalActor;
    }

    public void setPaginaPersonalActor(String paginaPersonalActor) {
        this.paginaPersonalActor = paginaPersonalActor;
    }

    public String getNombreActor() {
        return nombreActor;
    }

    public void setNombreActor(String nombreActor) {
        this.nombreActor = nombreActor;
    }

    public Integer getIdActorImdb() {
        return idActorImdb;
    }

    public void setIdActorImdb(Integer idActorImdb) {
        this.idActorImdb = idActorImdb;
    }

    public Float getPopularidad() {
        return popularidad;
    }

    public void setPopularidad(Float popularidad) {
        this.popularidad = popularidad;
    }

    public String getFotoPerfilActor() {
        return fotoPerfilActor;
    }

    public void setFotoPerfilActor(String fotoPerfilActor) {
        this.fotoPerfilActor = fotoPerfilActor;
    }
}
