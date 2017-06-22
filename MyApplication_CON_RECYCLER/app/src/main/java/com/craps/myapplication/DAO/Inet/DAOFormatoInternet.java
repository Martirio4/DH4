package com.craps.myapplication.DAO.Inet;

import android.os.AsyncTask;


import com.craps.myapplication.Model.Actor;
import com.craps.myapplication.Model.ContainerActores;
import com.craps.myapplication.Model.ContainerFormatos;
import com.craps.myapplication.Model.Formato;
import com.craps.myapplication.Utils.HTTPConnectionManager;
import com.craps.myapplication.Utils.ResultListener;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by digitalhouse on 03/06/17.
 */

public class DAOFormatoInternet {

    private String queBuscoEnInet;
    private List<Formato> laListaFormatos;
    private List<Actor>laListaActores;
    private Formato elFormato;
    private Actor elActor;
    


    public void obtenerFormatosDesdeInternet(ResultListener<List<Formato>> listenerFromController,String queBuscoEnInet){

        //cargo el string de la busqueda con la que le pego al api
        this.queBuscoEnInet=queBuscoEnInet;

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
                String json = httpConnectionManager.getRequestString(queBuscoEnInet);

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

    public void obtenerFormatoDesdeInternet(ResultListener<Formato> listenerFromController,String queBuscoEnInet){

        //cargo el string de la busqueda con la que le pego al api
        this.queBuscoEnInet=queBuscoEnInet;

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
                String json = httpConnectionManager.getRequestString(queBuscoEnInet);

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





    public void obtenerActoresInet(ResultListener<List<Actor>> listenerFromController,String queBuscoEnInet){

        //cargo el string de la busqueda con la que le pego al api
        this.queBuscoEnInet=queBuscoEnInet;

        //LE ESTOY INDICANDO AL DAO QUE EJECUTE LA TAREA EN SEGUNDO PLANO
        ObtenerListadoDeActoresTask tarea = new ObtenerListadoDeActoresTask();
        tarea.setListenerFromController(listenerFromController);
        tarea.execute();
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
                String json = httpConnectionManager.getRequestString(queBuscoEnInet);

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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    






}
