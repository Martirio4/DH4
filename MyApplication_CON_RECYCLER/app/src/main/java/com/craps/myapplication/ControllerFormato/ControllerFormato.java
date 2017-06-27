package com.craps.myapplication.ControllerFormato;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
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
import com.craps.myapplication.View.Activities.ActivityRegister;

import java.util.ArrayList;
import java.util.List;

import static com.craps.myapplication.R.id.peliMasVista;


/**
 * Created by elmar on 3/6/2017.
 */

public class ControllerFormato {

    private Context context;
    private Boolean endPaging =false;
    private Integer numeroPagina=1;
    private Registrable registrable;

    public interface Registrable{
        public void solicitarRegistro();
    }

    public Integer getNumeroPagina() {
        return numeroPagina;
    }

    public void setNumeroPagina(Integer numeroPagina) {
        this.numeroPagina = numeroPagina;
    }
    /*  public void getNextPostPage(final ResultListener<List<Post>> listenerFromView, Context context) {

        PostDAO postDAO = new PostDAO();
        postDAO.getPostPaginated(new ResultListener<List<Post>>() {
            @Override
            public void finish(List<Post> unaPagina) {

                if(unaPagina == null || unaPagina.isEmpty()){
                    endPaging = true;
                }
                else{
                    //TENGO QUE CAMBIAR EL OFFSET
                    offset = offset + LIMIT;

                    //LE AVISO A LA VISTA
                    listenerFromView.finish(unaPagina);
                }
            }
        }, offset, LIMIT);
    }

    public Boolean isPageAvailable() {
        return !endPaging;
    }*/

    public ControllerFormato(Context context) {
        this.context = context;
        this.registrable=(Registrable) context;
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

    public void obtenerActoresPelicula(final ResultListener<List<Actor>> listenerFromView, Integer id){

        if (HTTPConnectionManager.isNetworkingOnline(context)) {

            //SI HAY CONEXION PIDO A INTERNET LOS DATOS Y LOS ALMACENO EN LA BASE DE DATOS. PARA PEDIR LOS DATOS A INTERNET
            //UTILIZO EL DAOPersonasInternet que es el encargado de realizar esa tarea.

            DAOFormatoInternet daoFormatoInternet = new DAOFormatoInternet();
            daoFormatoInternet.obtenerActoresPelicula(new ResultListener<List<Actor>>() {
                @Override
                public void finish(List<Actor> resultado) {
                    listenerFromView.finish(resultado);
                }
            },id);
        }

        else {
            Toast.makeText(context, "no hay resultados", Toast.LENGTH_SHORT).show();
        }

    }
    public void obtenerActoresSerie(final ResultListener<List<Actor>> listenerFromView, Integer id){

        if (HTTPConnectionManager.isNetworkingOnline(context)) {

            //SI HAY CONEXION PIDO A INTERNET LOS DATOS Y LOS ALMACENO EN LA BASE DE DATOS. PARA PEDIR LOS DATOS A INTERNET
            //UTILIZO EL DAOPersonasInternet que es el encargado de realizar esa tarea.

            DAOFormatoInternet daoFormatoInternet = new DAOFormatoInternet();
            daoFormatoInternet.obtenerActoresSerie(new ResultListener<List<Actor>>() {
                @Override
                public void finish(List<Actor> resultado) {
                    listenerFromView.finish(resultado);
                }
            },id);
        }

        else {
            Toast.makeText(context, "no hay resultados", Toast.LENGTH_SHORT).show();
        }

    }






    public void obtenerPeliculasPopulares(final ResultListener<List<Formato>> listenerFromView) {

        if (HTTPConnectionManager.isNetworkingOnline(context)) {
            DAOFormatoInternet daoFormatoInternet = new DAOFormatoInternet();
            daoFormatoInternet.obtenerPeliculasPopulares(new ResultListener<List<Formato>>() {
                @Override
                public void finish(List<Formato> unaPagina) {

                    if (unaPagina == null || unaPagina.isEmpty()) {
                        endPaging = true;
                    } else {
                        DAOFormatoDatabase daoFormatoDatabase = new DAOFormatoDatabase(context);
                        daoFormatoDatabase.addFormatos(unaPagina);
                        //TENGO QUE CAMBIAR EL OFFSET
                        numeroPagina = numeroPagina + 1;

                        //LE AVISO A LA VISTA
                        listenerFromView.finish(unaPagina);
                    }
                }
            }, numeroPagina);
        }
        else{
            DAOFormatoDatabase daoFormatoDatabase = new DAOFormatoDatabase(context);
            List<Formato> listaFormatosOffline=daoFormatoDatabase.getAllFormatos();
            listenerFromView.finish(listaFormatosOffline);
        }
    }

    public void obtenerSeriesPopulares(final ResultListener<List<Formato>> listenerFromView) {

        if (HTTPConnectionManager.isNetworkingOnline(context)) {
            DAOFormatoInternet daoFormatoInternet = new DAOFormatoInternet();
            daoFormatoInternet.obtenerSeriesPopulares(new ResultListener<List<Formato>>() {
                @Override
                public void finish(List<Formato> unaPagina) {

                    if (unaPagina == null || unaPagina.isEmpty()) {
                        endPaging = true;
                    } else {
                        DAOFormatoDatabase daoFormatoDatabase = new DAOFormatoDatabase(context);
                        daoFormatoDatabase.addFormatos(unaPagina);
                        //TENGO QUE CAMBIAR EL OFFSET
                        numeroPagina = numeroPagina + 1;

                        //LE AVISO A LA VISTA
                        listenerFromView.finish(unaPagina);
                    }
                }
            }, numeroPagina);
        }
        else{
            DAOFormatoDatabase daoFormatoDatabase = new DAOFormatoDatabase(context);
            List<Formato> listaFormatosOffline=daoFormatoDatabase.getAllFormatos();
            listenerFromView.finish(listaFormatosOffline);
        }
    }

    public void obtenerPeliculasEnCartel(final ResultListener<List<Formato>> listenerFromView) {

        if (HTTPConnectionManager.isNetworkingOnline(context)) {
            DAOFormatoInternet daoFormatoInternet = new DAOFormatoInternet();
            daoFormatoInternet.obtenerPeliculasEnCartelInternet(new ResultListener<List<Formato>>() {
                @Override
                public void finish(List<Formato> unaPagina) {

                    if (unaPagina == null || unaPagina.isEmpty()) {
                        endPaging = true;
                    } else {
                        DAOFormatoDatabase daoFormatoDatabase = new DAOFormatoDatabase(context);
                        daoFormatoDatabase.addFormatos(unaPagina);
                        //TENGO QUE CAMBIAR EL OFFSET
                        numeroPagina = numeroPagina + 1;

                        //LE AVISO A LA VISTA
                        listenerFromView.finish(unaPagina);
                    }
                }
            }, numeroPagina);
        }
        else{
            DAOFormatoDatabase daoFormatoDatabase = new DAOFormatoDatabase(context);
            List<Formato> listaFormatosOffline=daoFormatoDatabase.getAllFormatos();
            listenerFromView.finish(listaFormatosOffline);
        }
    }

    public void obtenerPeliculasPorGenero(final ResultListener<List<Formato>> listenerFromView, String genero) {

        if (HTTPConnectionManager.isNetworkingOnline(context)) {
            DAOFormatoInternet daoFormatoInternet = new DAOFormatoInternet();
            daoFormatoInternet.obtenerPeliculasPorGenero(new ResultListener<List<Formato>>() {
                @Override
                public void finish(List<Formato> unaPagina) {

                    if (unaPagina == null || unaPagina.isEmpty()) {
                        endPaging = true;
                    } else {
                        DAOFormatoDatabase daoFormatoDatabase = new DAOFormatoDatabase(context);
                        daoFormatoDatabase.addFormatos(unaPagina);
                        //TENGO QUE CAMBIAR EL OFFSET
                        numeroPagina = numeroPagina + 1;

                        //LE AVISO A LA VISTA
                        listenerFromView.finish(unaPagina);
                    }
                }
            },genero, numeroPagina);
        }
        else{
            DAOFormatoDatabase daoFormatoDatabase = new DAOFormatoDatabase(context);
            List<Formato> listaFormatosOffline=daoFormatoDatabase.getAllFormatos();
            listenerFromView.finish(listaFormatosOffline);
        }
    }

    public void obtenerSeriesPorGenero(final ResultListener<List<Formato>> listenerFromView, String genero) {

        if (HTTPConnectionManager.isNetworkingOnline(context)) {
            DAOFormatoInternet daoFormatoInternet = new DAOFormatoInternet();
            daoFormatoInternet.obtenerSeriesPorGenero(new ResultListener<List<Formato>>() {
                @Override
                public void finish(List<Formato> unaPagina) {

                    if (unaPagina == null || unaPagina.isEmpty()) {
                        endPaging = true;
                    } else {
                        DAOFormatoDatabase daoFormatoDatabase = new DAOFormatoDatabase(context);
                        daoFormatoDatabase.addFormatos(unaPagina);
                        //TENGO QUE CAMBIAR EL OFFSET
                        numeroPagina = numeroPagina + 1;

                        //LE AVISO A LA VISTA
                        listenerFromView.finish(unaPagina);
                    }
                }
            },genero, numeroPagina);
        }
        else{
            DAOFormatoDatabase daoFormatoDatabase = new DAOFormatoDatabase(context);
            List<Formato> listaFormatosOffline=daoFormatoDatabase.getAllFormatos();
            listenerFromView.finish(listaFormatosOffline);
        }
    }

    public void obtenerSeriesMasValoradas(final ResultListener<List<Formato>> listenerFromView) {

        if (HTTPConnectionManager.isNetworkingOnline(context)) {
            DAOFormatoInternet daoFormatoInternet = new DAOFormatoInternet();
            daoFormatoInternet.obtenerSeriesMasValoradas(new ResultListener<List<Formato>>() {
                @Override
                public void finish(List<Formato> unaPagina) {

                    if (unaPagina == null || unaPagina.isEmpty()) {
                        endPaging = true;
                    } else {
                        DAOFormatoDatabase daoFormatoDatabase = new DAOFormatoDatabase(context);
                        daoFormatoDatabase.addFormatos(unaPagina);
                        //TENGO QUE CAMBIAR EL OFFSET
                        numeroPagina = numeroPagina + 1;

                        //LE AVISO A LA VISTA
                        listenerFromView.finish(unaPagina);
                    }
                }
            },numeroPagina);
        }
        else{
            DAOFormatoDatabase daoFormatoDatabase = new DAOFormatoDatabase(context);
            List<Formato> listaFormatosOffline=daoFormatoDatabase.getAllFormatos();
            listenerFromView.finish(listaFormatosOffline);
        }
    }

    public void obtenerSeriesEnCartel(final ResultListener<List<Formato>> listenerFromView) {

        if (HTTPConnectionManager.isNetworkingOnline(context)) {
            DAOFormatoInternet daoFormatoInternet = new DAOFormatoInternet();
            daoFormatoInternet.obtenerSeriesEnCartel(new ResultListener<List<Formato>>() {
                @Override
                public void finish(List<Formato> unaPagina) {

                    if (unaPagina == null || unaPagina.isEmpty()) {
                        endPaging = true;
                    } else {
                        DAOFormatoDatabase daoFormatoDatabase = new DAOFormatoDatabase(context);
                        daoFormatoDatabase.addFormatos(unaPagina);
                        //TENGO QUE CAMBIAR EL OFFSET
                        numeroPagina = numeroPagina + 1;

                        //LE AVISO A LA VISTA
                        listenerFromView.finish(unaPagina);
                    }
                }
            },numeroPagina);
        }
        else{
            DAOFormatoDatabase daoFormatoDatabase = new DAOFormatoDatabase(context);
            List<Formato> listaFormatosOffline=daoFormatoDatabase.getAllFormatos();
            listenerFromView.finish(listaFormatosOffline);
        }
    }

    public void buscarPelicula(final ResultListener<List<Formato>> listenerFromView, String stringABuscar) {

        if (HTTPConnectionManager.isNetworkingOnline(context)) {
            DAOFormatoInternet daoFormatoInternet = new DAOFormatoInternet();
            daoFormatoInternet.buscarPelicula(new ResultListener<List<Formato>>() {
                @Override
                public void finish(List<Formato> unaPagina) {

                    if (unaPagina == null || unaPagina.isEmpty()) {
                        endPaging = true;
                    } else {
                        //TENGO QUE CAMBIAR EL OFFSET
                        numeroPagina = numeroPagina + 1;

                        //LE AVISO A LA VISTA
                        listenerFromView.finish(unaPagina);
                    }
                }
            },stringABuscar, numeroPagina);
        }
        else{
            DAOFormatoDatabase daoFormatoDatabase = new DAOFormatoDatabase(context);
            List<Formato> listaFormatosOffline=daoFormatoDatabase.getAllFormatos();
            listenerFromView.finish(listaFormatosOffline);
        }
    }

    public void buscarSerie(final ResultListener<List<Formato>> listenerFromView, String stringABuscar) {

        if (HTTPConnectionManager.isNetworkingOnline(context)) {
            DAOFormatoInternet daoFormatoInternet = new DAOFormatoInternet();
            daoFormatoInternet.buscarSerie(new ResultListener<List<Formato>>() {
                @Override
                public void finish(List<Formato> unaPagina) {

                    if (unaPagina == null || unaPagina.isEmpty()) {
                        endPaging = true;
                    } else {
                        //TENGO QUE CAMBIAR EL OFFSET
                        numeroPagina = numeroPagina + 1;

                        //LE AVISO A LA VISTA
                        listenerFromView.finish(unaPagina);
                    }
                }
            },stringABuscar, numeroPagina);
        }
        else{
            DAOFormatoDatabase daoFormatoDatabase = new DAOFormatoDatabase(context);
            List<Formato> listaFormatosOffline=daoFormatoDatabase.getAllFormatos();
            listenerFromView.finish(listaFormatosOffline);
        }
    }

    public void obtenerPeliculasRelacionadas(final ResultListener<List<Formato>> listenerFromView, Integer id) {

        if (HTTPConnectionManager.isNetworkingOnline(context)) {
            DAOFormatoInternet daoFormatoInternet = new DAOFormatoInternet();
            daoFormatoInternet.ObtenerPeliculasRelacionadas(new ResultListener<List<Formato>>() {
                @Override
                public void finish(List<Formato> unaPagina) {

                    if (unaPagina == null || unaPagina.isEmpty()) {
                        DAOFormatoDatabase daoFormatoDatabase = new DAOFormatoDatabase(context);
                        daoFormatoDatabase.addFormatos(unaPagina);
                        endPaging = true;
                    } else {
                        //TENGO QUE CAMBIAR EL OFFSET
                        numeroPagina = numeroPagina + 1;

                        //LE AVISO A LA VISTA
                        listenerFromView.finish(unaPagina);
                    }
                }
            },id, numeroPagina);
        }
        else{
            DAOFormatoDatabase daoFormatoDatabase = new DAOFormatoDatabase(context);
            List<Formato> listaFormatosOffline=daoFormatoDatabase.getAllFormatos();
            listenerFromView.finish(listaFormatosOffline);
        }
    }

    public void obtenerSeriesRelacionadas(final ResultListener<List<Formato>> listenerFromView, Integer id) {

        if (HTTPConnectionManager.isNetworkingOnline(context)) {
            DAOFormatoInternet daoFormatoInternet = new DAOFormatoInternet();
            daoFormatoInternet.ObtenerPeliculasRelacionadas(new ResultListener<List<Formato>>() {
                @Override
                public void finish(List<Formato> unaPagina) {

                    if (unaPagina == null || unaPagina.isEmpty()) {
                        endPaging = true;
                    } else {
                        DAOFormatoDatabase daoFormatoDatabase = new DAOFormatoDatabase(context);
                        daoFormatoDatabase.addFormatos(unaPagina);
                        //TENGO QUE CAMBIAR EL OFFSET
                        numeroPagina = numeroPagina + 1;

                        //LE AVISO A LA VISTA
                        listenerFromView.finish(unaPagina);
                    }
                }
            },id, numeroPagina);
        }
        else{
            DAOFormatoDatabase daoFormatoDatabase = new DAOFormatoDatabase(context);
            List<Formato> listaFormatosOffline=daoFormatoDatabase.getAllFormatos();
            listenerFromView.finish(listaFormatosOffline);
        }
    }

    //HAY PAGINA DISPONIBLE
    public Boolean isPageAvailable() {
        return !endPaging;
    }















/*
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
            }, queBuscoEnInet,numeroPagina);
        }

        else {
            //SI NO HAY CONEXION UTILIZO LOS DATOS ALMACENADOS EN LA BASE DE DATOS. Para pedir los datos almacenados en la
            // Base de datos utilizo el DAOPersonDatabase.
            DAOFormatoDatabase daoFormatoDatabase = new DAOFormatoDatabase(context);
            List<Formato> formatos = daoFormatoDatabase.getFormatosConFiltro(queBuscoEnInet);
            listenerFromView.finish(formatos);
        }


    }*/

    public void obtenerFavoritos(final ResultListener<List<Formato>> listenerFromView, String usuario) {
            //SI NO HAY CONEXION UTILIZO LOS DATOS ALMACENADOS EN LA BASE DE DATOS. Para pedir los datos almacenados en la
            // Base de datos utilizo el DAOPersonDatabase.
            DAOFavoritosDatabase daoFavoritosDatabase = new DAOFavoritosDatabase(context);
            List<Formato> formatos = daoFavoritosDatabase.getAllFormatos(usuario);
            listenerFromView.finish(formatos);
    }

    public void agregarFavorito(Formato unFormato, String unString){
        if (ActivityMain.login) {
            final DAOFavoritosDatabase daoFavoritosDatabase = new DAOFavoritosDatabase(context);
            final Formato elFormato = unFormato;
            if (daoFavoritosDatabase.checkIfExist(unFormato.getId(), ActivityMain.usuario)) {

                Toast.makeText(context, "El formato ya figura entre los favoritos", Toast.LENGTH_SHORT).show();
            } else {
                daoFavoritosDatabase.addFormato(unFormato, unString);

                Activity unaActivity = (Activity) context;
                View view = (View) unaActivity.findViewById(R.id.detalle_contenedor_fragment);

                Snackbar.make(view, "Formato Agregado a Favoritos", Snackbar.LENGTH_SHORT)
                        .setAction("Deshacer?", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                daoFavoritosDatabase.borrarFavorito(elFormato, ActivityMain.usuario);
                            }
                        })
                        .show();

            }
        }
        else{
            Activity unaActivity = (Activity) context;
            View view = (View) unaActivity.findViewById(R.id.detalle_contenedor_fragment);

            Snackbar.make(view, "Funcion exclusiva para usuarios Registrados", Snackbar.LENGTH_SHORT)
                    .setAction("Ir a Registro", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            registrable.solicitarRegistro();

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

    public void traerBusquedaDrawer(Integer id){
        switch (id){
            case R.id.peliMasVista:
            case R.id.serieMasVista:
            case R.id.animacion:
            case R.id.documentales:
            case R.id.seriesHoy:
        }
    }

    public void buscarFavoritos(final ResultListener<List<Formato>> listenerFromView,String unString){
        if (ActivityMain.login) {
            final DAOFavoritosDatabase daoFavoritosDatabase = new DAOFavoritosDatabase(context);
            List<Formato> resultado =new ArrayList<>();
            resultado= daoFavoritosDatabase.getFormatosConFiltro(unString, ActivityMain.usuario);
            listenerFromView.finish(resultado);

            }

        else{
            Activity unaActivity = (Activity) context;
            View view = unaActivity.findViewById(R.id.contenedor_fragment_maestro);

            Snackbar.make(view, "Funcion exclusiva para usuarios Registrados", Snackbar.LENGTH_SHORT)
                    .setAction("Ir a Registro", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            registrable.solicitarRegistro();

                        }
                    })
                    .show();
        }
    }

}