package com.craps.myapplication.ControllerFormato;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.view.View;
import android.widget.Toast;

import com.craps.myapplication.DAO.Db.DAOFavoritosDatabase;
import com.craps.myapplication.DAO.File.DAOArchivo;
import com.craps.myapplication.DAO.Db.DAOFormatoDatabase;
import com.craps.myapplication.DAO.Inet.DAOFormatoInternet;
import com.craps.myapplication.Model.Actor;
import com.craps.myapplication.Model.Formato;
import com.craps.myapplication.R;
import com.craps.myapplication.Utils.HTTPConnectionManager;
import com.craps.myapplication.Utils.ResultListener;
import com.craps.myapplication.View.Activities.ActivityMain;
import com.craps.myapplication.View.Fragments.FragmentFavoritos;

import java.util.List;

/**
 * Created by elmar on 3/6/2017.
 */

public class ControllerFormato {

    private Context context;

    public ControllerFormato(Context context) {
        this.context = context;
    }

    public List<String> recibirListaFormatos() {
        DAOArchivo DAOArchivo = new DAOArchivo();
        if (HTTPConnectionManager.isNetworkingOnline(context)){
            return DAOArchivo.cargarTipoFormato();
        }
        else {
            return DAOArchivo.cargarTipoFormatoOffline();
        }
    }

    public List<String> recibirCategorias() {
        DAOArchivo DAOArchivo = new DAOArchivo();
        return DAOArchivo.cargarCategorias();
    }

    public void obtenerActores(final ResultListener<List<Actor>> listenerFromView, String queBuscoEnInet){

        if (HTTPConnectionManager.isNetworkingOnline(context)) {

            //SI HAY CONEXION PIDO A INTERNET LOS DATOS Y LOS ALMACENO EN LA BASE DE DATOS. PARA PEDIR LOS DATOS A INTERNET
            //UTILIZO EL DAOPersonasInternet que es el encargado de realizar esa tarea.

            DAOFormatoInternet daoFormatoInternet = new DAOFormatoInternet();
            daoFormatoInternet.obtenerActoresInet(new ResultListener<List<Actor>>() {
                @Override
                public void finish(List<Actor> resultado) {

                    listenerFromView.finish(resultado);
                }
            }, queBuscoEnInet);
        }

        else {
            Toast.makeText(context, "no hay resultados", Toast.LENGTH_SHORT).show();
        }

    }

    public void obtenerFormatos(final ResultListener<List<Formato>> listenerFromView, String queBuscoEnInet) {

        if (HTTPConnectionManager.isNetworkingOnline(context)) {

            //SI HAY CONEXION PIDO A INTERNET LOS DATOS Y LOS ALMACENO EN LA BASE DE DATOS. PARA PEDIR LOS DATOS A INTERNET
            //UTILIZO EL DAOPersonasInternet que es el encargado de realizar esa tarea.

            DAOFormatoInternet daoFormatoInternet = new DAOFormatoInternet();
            daoFormatoInternet.obtenerFormatosDesdeInternet(new ResultListener<List<Formato>>() {
                @Override
                public void finish(List<Formato> resultado) {
                    //TENGO QUE DECIR QUE VA A HACER EL CONTROLLER CUANDO EL DAO LE AVISE QUE TERMINO

                    //EL RESULTADO DE LA LISTA DE PERSONAS LA TENEMOS QUE GUARDAR EN LA BASE DE DATOS
                    DAOFormatoDatabase daoFormatoDatabase = new DAOFormatoDatabase(context);
                    daoFormatoDatabase.addFormatos(resultado);
                    listenerFromView.finish(resultado);
                }
            }, queBuscoEnInet);
        }

        else {
            //SI NO HAY CONEXION UTILIZO LOS DATOS ALMACENADOS EN LA BASE DE DATOS. Para pedir los datos almacenados en la
            // Base de datos utilizo el DAOPersonDatabase.
            DAOFormatoDatabase daoFormatoDatabase = new DAOFormatoDatabase(context);
            List<Formato> formatos = daoFormatoDatabase.getFormatosConFiltro(queBuscoEnInet);
            listenerFromView.finish(formatos);
        }


    }

    public void obtenerFavoritos(final ResultListener<List<Formato>> listenerFromView, String usuario) {
            //SI NO HAY CONEXION UTILIZO LOS DATOS ALMACENADOS EN LA BASE DE DATOS. Para pedir los datos almacenados en la
            // Base de datos utilizo el DAOPersonDatabase.
            DAOFavoritosDatabase daoFavoritosDatabase = new DAOFavoritosDatabase(context);
            List<Formato> formatos = daoFavoritosDatabase.getAllFormatos(usuario);
            listenerFromView.finish(formatos);
    }

    public void agregarFavorito(Formato unFormato, String unString){
        final DAOFavoritosDatabase daoFavoritosDatabase= new DAOFavoritosDatabase(context);
        final Formato elFormato=unFormato;
        if (daoFavoritosDatabase.checkIfExist(unFormato.getId(), ActivityMain.usuario)){
            Toast.makeText(context, "El formato ya figura entre los favoritos", Toast.LENGTH_SHORT).show();
        }
        else {
            daoFavoritosDatabase.addFormato(unFormato, unString);

            Activity unaActivity = (Activity) context;
            View view = (View) unaActivity.findViewById(R.id.detalle_contenedor_fragment);

            Snackbar.make(view, "Formato Agregado a Favoritos",Snackbar.LENGTH_SHORT)
                    .setAction("Deshacer?", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            daoFavoritosDatabase.borrarFavorito(elFormato,ActivityMain.usuario);
                        }
                    })
                    .show();

        }
    }
    
    public void eliminarFavorito(Formato unFormato, String unString){
        final Formato elFormato;
        final DAOFavoritosDatabase daoFavoritosDatabase= new DAOFavoritosDatabase(context);
        if(daoFavoritosDatabase.checkIfExist(unFormato.getId(), ActivityMain.usuario)){
            elFormato=daoFavoritosDatabase.getFormato(unFormato.getId(), unString);
            daoFavoritosDatabase.borrarFavorito(unFormato, unString);
            Activity unaActivity = (Activity) context;
            View view = (View) unaActivity.findViewById(R.id.detalle_contenedor_fragment);

            Snackbar.make(view, "Formato eliminado",Snackbar.LENGTH_LONG)
                    .setAction("Deshacer?", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            daoFavoritosDatabase.addFormato(elFormato,ActivityMain.usuario);
                        }
                    })
                    .show();

        }
        else{
            Toast.makeText(context, "El formato, no se encuentra en la lista de favoritos", Toast.LENGTH_SHORT).show();
        }
    }

}