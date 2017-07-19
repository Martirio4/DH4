package com.craps.myapplication.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dh3 on 28/06/17.
 */

public class Trailer {

    private String id;

    @SerializedName("key")
    private String claveVideoYouTube;

    @SerializedName("name")
    private String nombreTrailer;

    @SerializedName("site")
    private String origenVideo;

    @SerializedName("size")
    private Integer tamaño;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClaveVideoYouTube() {
        return claveVideoYouTube;
    }

    public void setClaveVideoYouTube(String claveVideoYouTube) {
        this.claveVideoYouTube = claveVideoYouTube;
    }

    public String getNombreTrailer() {
        return nombreTrailer;
    }

    public void setNombreTrailer(String nombreTrailer) {
        this.nombreTrailer = nombreTrailer;
    }

    public String getOrigenVideo() {
        return origenVideo;
    }

    public void setOrigenVideo(String origenVideo) {
        this.origenVideo = origenVideo;
    }

    public Integer getTamaño() {
        return tamaño;
    }

    public void setTamaño(Integer tamaño) {
        this.tamaño = tamaño;
    }
}


