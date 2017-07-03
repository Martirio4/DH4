package com.craps.myapplication.Model;


import java.util.List;

/**
 * Created by Martirio on 01/06/2017.
 */

public class Formato {

    //    ATRIBUTOS
    private String title=null;
    private String release_date=null;
    private String overview=null;
    private Float vote_average=null;
    private String tipoFormato=null;
    private String poster_path=null;
    private Integer id=null;
    private String backdrop_path=null;
    private String name =null;
    private String first_air_date=null;
    private String tagline=null;
    private Integer number_of_seasons=null;
    private Integer number_of_episodes=null;
    private Integer budget=null;
    private Integer revenue=null;
    private String genero="";

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public Integer getRevenue() {
        return revenue;
    }

    public void setRevenue(Integer revenue) {
        this.revenue = revenue;
    }

    public Integer getNumber_of_episodes() {
        return number_of_episodes;
    }

    public void setNumber_of_episodes(Integer number_of_episodes) {
        this.number_of_episodes = number_of_episodes;
    }

    public String getName() {
        return name;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getTitle() {
        return title;
    }

    public String getRelease_date() {
        return release_date;
    }



    public String getOverview() {
        return overview;
    }


    public Integer getId() {
        return id;
    }

    public Float getVote_average() {
        return vote_average;
    }

    public String getTipoFormato() {
        return tipoFormato;
    }

    //CONSTRUCTOR

    public Formato(String nombre, String año, String director, String actor, String actriz, String sinopsis, String genero, Integer imagen, Float calificacion, String tipoFormato, Integer id) {
        this.title = nombre;
        this.release_date = año;

        this.overview = sinopsis;

        this.vote_average = calificacion;
        this.tipoFormato = tipoFormato;
        this.id = id;
    }

    public Formato(){}

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }


    public void setOverview(String overview) {
        this.overview = overview;
    }



    public void setVote_average(Float vote_average) {
        this.vote_average = vote_average;
    }

    public void setTipoFormato(String tipoFormato) {
        this.tipoFormato = tipoFormato;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }




    public void setId(Integer id) {
        this.id = id;
    }



    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }







    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tag_line) {
        this.tagline = tag_line;
    }

    public Integer getNumber_of_seasons() {
        return number_of_seasons;
    }

    public void setNumber_of_seasons(Integer number_of_seasons) {
        this.number_of_seasons = number_of_seasons;
    }
}
