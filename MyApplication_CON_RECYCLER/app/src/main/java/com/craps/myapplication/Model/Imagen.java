package com.craps.myapplication.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Martirio on 07/07/2017.
 */

public class Imagen {

    @SerializedName("height")
    private Integer alturaImagen;

    @SerializedName("width")
    private Integer anchoImagen;

    @SerializedName("file_path")
    private String rutaImagen;

    public Integer getAlturaImagen() {
        return alturaImagen;
    }

    public void setAlturaImagen(Integer alturaImagen) {
        this.alturaImagen = alturaImagen;
    }

    public Integer getAnchoImagen() {
        return anchoImagen;
    }

    public void setAnchoImagen(Integer anchoImagen) {
        this.anchoImagen = anchoImagen;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }
}
