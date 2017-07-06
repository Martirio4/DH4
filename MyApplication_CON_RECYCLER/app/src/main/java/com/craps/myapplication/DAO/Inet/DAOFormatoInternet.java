package com.craps.myapplication.DAO.Inet;

import android.os.AsyncTask;


import com.craps.myapplication.Model.Actor;
import com.craps.myapplication.Model.ContainerActores;
import com.craps.myapplication.Model.ContainerCreditos;
import com.craps.myapplication.Model.ContainerFormatos;
import com.craps.myapplication.Model.ContainerTrailer;
import com.craps.myapplication.Model.Credito;
import com.craps.myapplication.Model.Formato;
import com.craps.myapplication.Model.Trailer;
import com.craps.myapplication.Utils.HTTPConnectionManager;
import com.craps.myapplication.Utils.ResultListener;
import com.craps.myapplication.Utils.TMDBHelper;
import com.google.gson.Gson;

import java.util.List;

import static com.craps.myapplication.View.Activities.ActivityMain.idiomaDeLaSesion;

/**
 * Created by digitalhouse on 03/06/17.
 */

public class DAOFormatoInternet {

    private String urlParaAsyncTask;

    private List<Formato> laListaFormatos;
    private List<Actor>laListaActores;
    private List<Credito>laListaCreditos;
    private List<Trailer> laListaTrailer;
    private Formato elFormato;
    private Actor elActor;

    //PELICULAS POPULARES
    public void obtenerPeliculasPopulares(ResultListener<List<Formato>> listenerFromController,Integer numeroPagina){

        urlParaAsyncTask= TMDBHelper.getPopularMovies(idiomaDeLaSesion,numeroPagina);
        //LE ESTOY INDICANDO AL DAO QUE EJECUTE LA TAREA EN SEGUNDO PLANO
        ObtenerListadoDeFormatosTask tarea = new ObtenerListadoDeFormatosTask();
        tarea.setListenerFromController(listenerFromController);
        tarea.execute();
    }

    //SERIES POPULARES
    public void obtenerSeriesPopulares(ResultListener<List<Formato>> listenerFromController,Integer numeroPagina){

        urlParaAsyncTask= TMDBHelper.getTVPopular(idiomaDeLaSesion,numeroPagina);
        //LE ESTOY INDICANDO AL DAO QUE EJECUTE LA TAREA EN SEGUNDO PLANO
        ObtenerListadoDeFormatosTask tarea = new ObtenerListadoDeFormatosTask();
        tarea.setListenerFromController(listenerFromController);
        tarea.execute();
    }

    //PELICULAS EN CARTEL
    public void obtenerPeliculasEnCartelInternet(ResultListener<List<Formato>> listenerFromController,Integer numeroPagina){

        urlParaAsyncTask= TMDBHelper.getTVTopRated(idiomaDeLaSesion, numeroPagina);
        //LE ESTOY INDICANDO AL DAO QUE EJECUTE LA TAREA EN SEGUNDO PLANO
        ObtenerListadoDeFormatosTask tarea = new ObtenerListadoDeFormatosTask();
        tarea.setListenerFromController(listenerFromController);
        tarea.execute();
    }

    //PELICULAS FILTRADAS POR GENERO
    public void obtenerPeliculasPorGenero(ResultListener<List<Formato>> listenerFromController,String genero, Integer numeroPagina){

        urlParaAsyncTask= TMDBHelper.getMoviesByGenre(genero, idiomaDeLaSesion, numeroPagina);
        //LE ESTOY INDICANDO AL DAO QUE EJECUTE LA TAREA EN SEGUNDO PLANO
        ObtenerListadoDeFormatosTask tarea = new ObtenerListadoDeFormatosTask();
        tarea.setListenerFromController(listenerFromController);
        tarea.execute();
    }

    //SERIES FILTRADAS POR GENERO
    public void obtenerSeriesPorGenero(ResultListener<List<Formato>> listenerFromController,String genero, Integer numeroPagina){

        urlParaAsyncTask= TMDBHelper.getTVByGenre(genero, idiomaDeLaSesion, numeroPagina);
        //LE ESTOY INDICANDO AL DAO QUE EJECUTE LA TAREA EN SEGUNDO PLANO
        ObtenerListadoDeFormatosTask tarea = new ObtenerListadoDeFormatosTask();
        tarea.setListenerFromController(listenerFromController);
        tarea.execute();
    }

    //SERIES MAS VALORADAS
    public void obtenerSeriesMasValoradas(ResultListener<List<Formato>> listenerFromController, Integer numeroPagina){

        urlParaAsyncTask= TMDBHelper.getTVTopRated( idiomaDeLaSesion, numeroPagina);
        //LE ESTOY INDICANDO AL DAO QUE EJECUTE LA TAREA EN SEGUNDO PLANO
        ObtenerListadoDeFormatosTask tarea = new ObtenerListadoDeFormatosTask();
        tarea.setListenerFromController(listenerFromController);
        tarea.execute();
    }

    //SERIES "EN CARTEL"
    public void obtenerSeriesEnCartel(ResultListener<List<Formato>> listenerFromController,Integer numeroPagina){

        urlParaAsyncTask= TMDBHelper.getTVAiringToday(idiomaDeLaSesion, numeroPagina);
        //LE ESTOY INDICANDO AL DAO QUE EJECUTE LA TAREA EN SEGUNDO PLANO
        ObtenerListadoDeFormatosTask tarea = new ObtenerListadoDeFormatosTask();
        tarea.setListenerFromController(listenerFromController);
        tarea.execute();
    }

    //BUSCAR PELICULA CON STRING
    public void buscarPelicula(ResultListener<List<Formato>> listenerFromController,String stringABuscar, Integer numeroPagina){

        urlParaAsyncTask= TMDBHelper.searchMovie(stringABuscar, idiomaDeLaSesion, numeroPagina);
        //LE ESTOY INDICANDO AL DAO QUE EJECUTE LA TAREA EN SEGUNDO PLANO
        ObtenerListadoDeFormatosTask tarea = new ObtenerListadoDeFormatosTask();
        tarea.setListenerFromController(listenerFromController);
        tarea.execute();
    }

    //BUSCAR SERIE CON STRING
    public void buscarSerie(ResultListener<List<Formato>> listenerFromController,String stringABuscar, Integer numeroPagina){

        urlParaAsyncTask= TMDBHelper.searchTv(stringABuscar, idiomaDeLaSesion, numeroPagina);
        //LE ESTOY INDICANDO AL DAO QUE EJECUTE LA TAREA EN SEGUNDO PLANO
        ObtenerListadoDeFormatosTask tarea = new ObtenerListadoDeFormatosTask();
        tarea.setListenerFromController(listenerFromController);
        tarea.execute();
    }

    // OBTENER LISTADO DE ACTORES DE PELICULA
    public void obtenerActoresPelicula(ResultListener<List<Actor>> listenerFromController,Integer idFormato){

        this.urlParaAsyncTask =TMDBHelper.getCastMovie(idFormato, idiomaDeLaSesion);
        //LE ESTOY INDICANDO AL DAO QUE EJECUTE LA TAREA EN SEGUNDO PLANO
        ObtenerListadoDeActoresTask tarea = new ObtenerListadoDeActoresTask();
        tarea.setListenerFromController(listenerFromController);
        tarea.execute();
    }

   // OBTENER LISTADO DE ACTORES DE SERIES
    public void obtenerActoresSerie(ResultListener<List<Actor>> listenerFromController,Integer idFormato) {

        this.urlParaAsyncTask = TMDBHelper.getCastTv(idFormato, idiomaDeLaSesion);
        //LE ESTOY INDICANDO AL DAO QUE EJECUTE LA TAREA EN SEGUNDO PLANO
        ObtenerListadoDeActoresTask tarea = new ObtenerListadoDeActoresTask();
        tarea.setListenerFromController(listenerFromController);
        tarea.execute();
    }

    //BUSCAR PELICULAS RELACIONADAS
    public void ObtenerPeliculasRelacionadas(ResultListener<List<Formato>> listenerFromController,Integer idPelicula, Integer numeroPagina){

        urlParaAsyncTask= TMDBHelper.getSimilarMovies(idPelicula,idiomaDeLaSesion,numeroPagina);
        //LE ESTOY INDICANDO AL DAO QUE EJECUTE LA TAREA EN SEGUNDO PLANO
        ObtenerListadoDeFormatosTask tarea = new ObtenerListadoDeFormatosTask();
        tarea.setListenerFromController(listenerFromController);
        tarea.execute();
    }

    //BUSCAR PELICULAS RELACIONADAS
    public void ObtenerSeriesRelacionadas(ResultListener<List<Formato>> listenerFromController,Integer idSerie, Integer numeroPagina){

        urlParaAsyncTask= TMDBHelper.getTVShowRecomendedForTVShow(idSerie,idiomaDeLaSesion,numeroPagina);
        //LE ESTOY INDICANDO AL DAO QUE EJECUTE LA TAREA EN SEGUNDO PLANO
        ObtenerListadoDeFormatosTask tarea = new ObtenerListadoDeFormatosTask();
        tarea.setListenerFromController(listenerFromController);
        tarea.execute();
    }






































    public void obtenerFormatosDesdeInternet(ResultListener<List<Formato>> listenerFromController,String queBuscoEnInet,Integer numeroPagina){

        //cargo el string de la busqueda con la que le pego al api
        this.urlParaAsyncTask =queBuscoEnInet;

        //LE ESTOY INDICANDO AL DAO QUE EJECUTE LA TAREA EN SEGUNDO PLANO
        ObtenerListadoDeFormatosTask tarea = new ObtenerListadoDeFormatosTask();
        tarea.setListenerFromController(listenerFromController);
        tarea.execute();
    }

    private class ObtenerListadoDeFormatosTask extends AsyncTask<String,Void,List<Formato>>{

        private ResultListener<List<Formato>> listenerFromController;
        public void setListenerFromController(ResultListener<List<Formato>> listenerFromController) {
            this.listenerFromController = listenerFromController;
        }

        @Override
        protected List<Formato> doInBackground(String... params) {

            List<Formato> formatos = null;
            try {
                //PEDIR A INTERNET USANDO UNA URL EL ARCHIVO JSON
                HTTPConnectionManager httpConnectionManager = new HTTPConnectionManager();
                String json = httpConnectionManager.getRequestString(urlParaAsyncTask);

                //USAR GSON PARA PARSEAR EL ARCHIVO Y CONVERTIRLO A LA LISTA DE NOTICIAS
                Gson gson = new Gson();
                ContainerFormatos containerFormatos = gson.fromJson(json, ContainerFormatos.class);
                formatos = containerFormatos.getFormatoList();
                laListaFormatos =formatos;


            }
            catch (Exception e){
                e.printStackTrace();
            }


            //DEVOLVER LA LISTA
            return formatos;
        }

        @Override
        protected void onPostExecute(List<Formato> formatos) {

            //AVISARLE AL CONTROLLER QUE SU LISTA DE NOTICIAS ESTA CARGADA
            listenerFromController.finish(formatos);
        }
    }

    public void obtenerPeliculaDesdeInternet(ResultListener<Formato> listenerFromController,Integer idFormato){

        //cargo el string de la busqueda con la que le pego al api
        this.urlParaAsyncTask =TMDBHelper.getMovieDetailURL(idFormato, idiomaDeLaSesion);

        //LE ESTOY INDICANDO AL DAO QUE EJECUTE LA TAREA EN SEGUNDO PLANO
        ObtenerUnFormatoTask tarea = new ObtenerUnFormatoTask();
        tarea.setListenerFromController(listenerFromController);
        tarea.execute();
    }
    public void obtenerSerieDesdeInternet(ResultListener<Formato> listenerFromController,Integer idFormato){

        //cargo el string de la busqueda con la que le pego al api
        this.urlParaAsyncTask =TMDBHelper.getTVShowDetail(idFormato,idiomaDeLaSesion);

        //LE ESTOY INDICANDO AL DAO QUE EJECUTE LA TAREA EN SEGUNDO PLANO
        ObtenerUnFormatoTask tarea = new ObtenerUnFormatoTask();
        tarea.setListenerFromController(listenerFromController);
        tarea.execute();
    }


    private class ObtenerUnFormatoTask extends AsyncTask<String,Void,Formato>{

        private ResultListener<Formato> listenerFromController;

        public void setListenerFromController(ResultListener<Formato> listenerFromController) {
            this.listenerFromController = listenerFromController;
        }

        @Override
        protected Formato doInBackground(String... params) {

            Formato formatos = null;
            try {
                //PEDIR A INTERNET USANDO UNA URL EL ARCHIVO JSON
                HTTPConnectionManager httpConnectionManager = new HTTPConnectionManager();
                String json = httpConnectionManager.getRequestString(urlParaAsyncTask);

                //USAR GSON PARA PARSEAR EL ARCHIVO Y CONVERTIRLO A LA LISTA DE NOTICIAS
                Gson gson = new Gson();
                Formato formato = gson.fromJson(json, Formato.class);
                formatos = formato;
                elFormato=formatos;


            }
            catch (Exception e){
                e.printStackTrace();
            }


            //DEVOLVER LA LISTA
            return formatos;
        }

        @Override
        protected void onPostExecute(Formato formatos) {

            //AVISARLE AL CONTROLLER QUE SU LISTA DE NOTICIAS ESTA CARGADA
            listenerFromController.finish(formatos);
        }
    }






    private class ObtenerListadoDeActoresTask extends AsyncTask<String,Void,List<Actor>>{

        private ResultListener<List<Actor>> listenerFromController;
        public void setListenerFromController(ResultListener<List<Actor>> listenerFromController) {
            this.listenerFromController = listenerFromController;
        }

        @Override
        protected List<Actor> doInBackground(String... params) {

            List<Actor> actores = null;
            try {
                //PEDIR A INTERNET USANDO UNA URL EL ARCHIVO JSON
                HTTPConnectionManager httpConnectionManager = new HTTPConnectionManager();
                String json = httpConnectionManager.getRequestString(urlParaAsyncTask);

                //USAR GSON PARA PARSEAR EL ARCHIVO Y CONVERTIRLO A LA LISTA DE NOTICIAS
                Gson gson = new Gson();
                ContainerActores containerActores = gson.fromJson(json, ContainerActores.class);
                actores = containerActores.getActoresList();
                laListaActores =actores;


            }
            catch (Exception e){
                e.printStackTrace();
            }


            //DEVOLVER LA LISTA
            return actores;
        }

        @Override
        protected void onPostExecute(List<Actor> actores) {

            //AVISARLE AL CONTROLLER QUE SU LISTA DE NOTICIAS ESTA CARGADA
            listenerFromController.finish(actores);
        }
    }


    public void obtenerdetalleActor(ResultListener<Actor> listenerFromController,Integer actorId){

        //cargo el string de la busqueda con la que le pego al api
        this.urlParaAsyncTask =TMDBHelper.getPerson(actorId, TMDBHelper.language_ENGLISH);

        //LE ESTOY INDICANDO AL DAO QUE EJECUTE LA TAREA EN SEGUNDO PLANO
        ObtenerDetalleActor tarea = new ObtenerDetalleActor();
        tarea.setListenerFromController(listenerFromController);
        tarea.execute();
    }
    private class ObtenerDetalleActor extends AsyncTask<String,Void,Actor>{

        private ResultListener<Actor> listenerFromController;

        public void setListenerFromController(ResultListener<Actor> listenerFromController) {
            this.listenerFromController = listenerFromController;
        }

        @Override
        protected Actor doInBackground(String... params) {

            Actor formatos = null;
            try {
                //PEDIR A INTERNET USANDO UNA URL EL ARCHIVO JSON
                HTTPConnectionManager httpConnectionManager = new HTTPConnectionManager();
                String json = httpConnectionManager.getRequestString(urlParaAsyncTask);

                //USAR GSON PARA PARSEAR EL ARCHIVO Y CONVERTIRLO A LA LISTA DE NOTICIAS
                Gson gson = new Gson();
                Actor formato = gson.fromJson(json, Actor.class);
                formatos = formato;
                elActor=formatos;


            }
            catch (Exception e){
                e.printStackTrace();
            }


            //DEVOLVER LA LISTA
            return formatos;
        }

        @Override
        protected void onPostExecute(Actor formatos) {

            //AVISARLE AL CONTROLLER QUE SU LISTA DE NOTICIAS ESTA CARGADA
            listenerFromController.finish(formatos);
        }
    }

    public void obtenerCreditosActor(ResultListener <List<Credito>> listenerFromController, Integer actorId){

        //cargo el string de la busqueda con la que le pego al api
        this.urlParaAsyncTask =TMDBHelper.getCredits(actorId, idiomaDeLaSesion);

        //LE ESTOY INDICANDO AL DAO QUE EJECUTE LA TAREA EN SEGUNDO PLANO
        ObtenerCreditosParaPersona tarea = new ObtenerCreditosParaPersona();
        tarea.setListenerFromController(listenerFromController);
        tarea.execute();
    }

    private class ObtenerCreditosParaPersona extends AsyncTask<String,Void,List<Credito>>{

        private ResultListener<List<Credito>> listenerFromController;
        public void setListenerFromController(ResultListener<List<Credito>> listenerFromController) {
            this.listenerFromController = listenerFromController;
        }

        @Override
        protected List<Credito> doInBackground(String... params) {

            List<Credito> actores = null;
            try {
                //PEDIR A INTERNET USANDO UNA URL EL ARCHIVO JSON
                HTTPConnectionManager httpConnectionManager = new HTTPConnectionManager();
                String json = httpConnectionManager.getRequestString(urlParaAsyncTask);

                //USAR GSON PARA PARSEAR EL ARCHIVO Y CONVERTIRLO A LA LISTA DE NOTICIAS
                Gson gson = new Gson();
                ContainerCreditos containerCreditos = gson.fromJson(json, ContainerCreditos.class);
                actores = containerCreditos.getCreditosList();
                laListaCreditos =actores;


            }
            catch (Exception e){
                e.printStackTrace();
            }


            //DEVOLVER LA LISTA
            return actores;
        }

        @Override
        protected void onPostExecute(List<Credito> actores) {

            //AVISARLE AL CONTROLLER QUE SU LISTA DE NOTICIAS ESTA CARGADA
            listenerFromController.finish(actores);
        }
    }

    // TRAILER PELICULAS
    public void obtenerTrailerPeliculas(ResultListener<List<Trailer>> listenerFromController,Integer formatoId){

        urlParaAsyncTask= TMDBHelper.getTrailerURL(formatoId,TMDBHelper.language_ENGLISH);
        //LE ESTOY INDICANDO AL DAO QUE EJECUTE LA TAREA EN SEGUNDO PLANO
        ObtenerTrailersTask tarea = new ObtenerTrailersTask();
        tarea.setListenerFromController(listenerFromController);
        tarea.execute();
    }

    //TRAER TRAILER SERIES
    public void obtenerTrailerSeries(ResultListener<List<Trailer>> listenerFromController,Integer formatoId){

        urlParaAsyncTask= TMDBHelper.getTVShowVideo(TMDBHelper.language_ENGLISH,formatoId);
        //LE ESTOY INDICANDO AL DAO QUE EJECUTE LA TAREA EN SEGUNDO PLANO
        ObtenerTrailersTask tarea = new ObtenerTrailersTask();
        tarea.setListenerFromController(listenerFromController);
        tarea.execute();
    }

    private class ObtenerTrailersTask extends AsyncTask<String,Void,List<Trailer>>{

        private ResultListener<List<Trailer>> listenerFromController;
        public void setListenerFromController(ResultListener<List<Trailer>> listenerFromController) {
            this.listenerFromController = listenerFromController;
        }

        @Override
        protected List<Trailer> doInBackground(String... params) {

            List<Trailer> trailers = null;
            try {
                //PEDIR A INTERNET USANDO UNA URL EL ARCHIVO JSON
                HTTPConnectionManager httpConnectionManager = new HTTPConnectionManager();
                String json = httpConnectionManager.getRequestString(urlParaAsyncTask);

                //USAR GSON PARA PARSEAR EL ARCHIVO Y CONVERTIRLO A LA LISTA DE NOTICIAS
                Gson gson = new Gson();
                ContainerTrailer containerTrailer = gson.fromJson(json, ContainerTrailer.class);
                trailers = containerTrailer.getTrailerList();
                laListaTrailer =trailers;


            }
            catch (Exception e){
                e.printStackTrace();
            }


            //DEVOLVER LA LISTA
            return trailers;
        }

        @Override
        protected void onPostExecute(List<Trailer> trailers) {

            //AVISARLE AL CONTROLLER QUE SU LISTA DE NOTICIAS ESTA CARGADA
            listenerFromController.finish(trailers);
        }
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    






}
