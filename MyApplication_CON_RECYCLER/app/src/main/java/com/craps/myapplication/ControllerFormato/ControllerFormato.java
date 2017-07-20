package com.craps.myapplication.ControllerFormato;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.IntegerRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.craps.myapplication.DAO.Db.DAOFavoritosDatabase;
import com.craps.myapplication.DAO.File.DAOArchivo;
import com.craps.myapplication.DAO.Db.DAOFormatoDatabase;
import com.craps.myapplication.DAO.Inet.DAOFormatoInternet;

import com.craps.myapplication.Model.Actor;
import com.craps.myapplication.Model.Credito;
import com.craps.myapplication.Model.Formato;
import com.craps.myapplication.Model.Imagen;
import com.craps.myapplication.Model.Pregunta;
import com.craps.myapplication.Model.Trailer;
import com.craps.myapplication.R;
import com.craps.myapplication.Utils.HTTPConnectionManager;
import com.craps.myapplication.Utils.ResultListener;
import com.craps.myapplication.Utils.TMDBHelper;
import com.craps.myapplication.View.Activities.ActivityMain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;




/**
 * Created by elmar on 3/6/2017.
 */

public class ControllerFormato {

    private Context context;
    private Boolean endPaging =false;
    private Integer numeroPagina=1;
    private Registrable registrable;

    private DatabaseReference mDatabase;


    public void setEndPaging(Boolean endPaging) {
        this.endPaging = endPaging;
    }

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
            else{
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
            Activity unaActivity = (Activity) context;
            View view = (View) unaActivity.findViewById(R.id.detalle_contenedor_fragment);
            Snackbar.make(view, "No se encontraron actores, revise su conexión a internet", Snackbar.LENGTH_SHORT)
                    .show();
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
            Activity unaActivity = (Activity) context;
            View view = (View) unaActivity.findViewById(R.id.detalle_contenedor_fragment);
            Snackbar.make(view, "No se encontraron actores, revise su conexión a internet", Snackbar.LENGTH_SHORT)
                    .show();
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
                    }
                    else {
                        DAOFormatoDatabase daoFormatoDatabase = new DAOFormatoDatabase(context);
                        daoFormatoDatabase.addFormatos(unaPagina,"peliculas");
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
            List<Formato> listaFormatosOffline=daoFormatoDatabase.getFormatosConFiltro("a","peliculas");
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
                        daoFormatoDatabase.addFormatos(unaPagina,"series");
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
            List<Formato> listaFormatosOffline=daoFormatoDatabase.getFormatosConFiltro("a","series");
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
                        daoFormatoDatabase.addFormatos(unaPagina,"peliculas");
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
            List<Formato> listaFormatosOffline=daoFormatoDatabase.getAllFormatosPorTipo("peliculas");
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
                        daoFormatoDatabase.addFormatos(unaPagina,"peliculas");
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
            List<Formato> listaFormatosOffline=daoFormatoDatabase.getFormatosConFiltro("e","peliculas");
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
                        daoFormatoDatabase.addFormatos(unaPagina,"series");
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
            List<Formato> listaFormatosOffline=daoFormatoDatabase.getFormatosConFiltro("e","series");
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
                        daoFormatoDatabase.addFormatos(unaPagina,"series");
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
            List<Formato> listaFormatosOffline=daoFormatoDatabase.busquedaPorVotoSerie();
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
                        daoFormatoDatabase.addFormatos(unaPagina,"series");
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
            List<Formato> listaFormatosOffline=daoFormatoDatabase.getAllFormatosPorTipo("series");
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
            List<Formato> listaFormatosOffline=daoFormatoDatabase.getAllFormatosPorTipo("peliculas");
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
            List<Formato> listaFormatosOffline=daoFormatoDatabase.getAllFormatosPorTipo("series");
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

                        endPaging = true;
                    } else {

                        numeroPagina = numeroPagina + 1;

                        //LE AVISO A LA VISTA
                        listenerFromView.finish(unaPagina);
                    }
                }
            },id, numeroPagina);
        }
        else{
            DAOFormatoDatabase daoFormatoDatabase = new DAOFormatoDatabase(context);
            List<Formato> listaFormatosOffline=daoFormatoDatabase.getAllFormatosPorTipo("peliculas");
            listenerFromView.finish(listaFormatosOffline);
        }
    }

    public void obtenerSeriesRelacionadas(final ResultListener<List<Formato>> listenerFromView, Integer id) {

        if (HTTPConnectionManager.isNetworkingOnline(context)) {
            DAOFormatoInternet daoFormatoInternet = new DAOFormatoInternet();
            daoFormatoInternet.ObtenerSeriesRelacionadas(new ResultListener<List<Formato>>() {
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
            },id, numeroPagina);
        }
        else{
            DAOFormatoDatabase daoFormatoDatabase = new DAOFormatoDatabase(context);
            List<Formato> listaFormatosOffline=daoFormatoDatabase.getAllFormatosPorTipo("series");
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

    public void obtenerFavoritos(final ResultListener<List<Formato>> listenerFromView) {
            //SI NO HAY CONEXION UTILIZO LOS DATOS ALMACENADOS EN LA BASE DE DATOS. Para pedir los datos almacenados en la
            // Base de datos utilizo el DAOPersonDatabase.
          //DAOFavoritosDatabase daoFavoritosDatabase = new DAOFavoritosDatabase(context);
           // List<Formato> formatos = daoFavoritosDatabase.getAllFormatos(ActivityMain.usuario);

        mDatabase=FirebaseDatabase.getInstance().getReference();
        DatabaseReference reference = mDatabase.child("favoritos").child(FirebaseAuth.getInstance().getCurrentUser().getUid());


        final List<Formato>listaFinal=new ArrayList<>();
        //QUIERO ESCUCHAR LA LISTA DE DATOS CADA VEZ QUE SE MODIFICA

        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Formato> listaFormatos = new ArrayList<>();
                for (DataSnapshot artistSnaptshot: dataSnapshot.child("peliculas").getChildren()) {
                    Formato unFormato = artistSnaptshot.getValue(Formato.class);
                    listaFormatos.add(unFormato);
                }
                for (DataSnapshot artistSnaptshot: dataSnapshot.child("series").getChildren()) {
                    Formato unFormato = artistSnaptshot.getValue(Formato.class);
                    listaFormatos.add(unFormato);
                }


                listenerFromView.finish(listaFormatos);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        });



    }

    public void busquedaFavoritos(final ResultListener<List<Formato>> listenerFromView, final String busquedaIngresada) {
        //SI NO HAY CONEXION UTILIZO LOS DATOS ALMACENADOS EN LA BASE DE DATOS. Para pedir los datos almacenados en la
        // Base de datos utilizo el DAOPersonDatabase.
        //DAOFavoritosDatabase daoFavoritosDatabase = new DAOFavoritosDatabase(context);
        // List<Formato> formatos = daoFavoritosDatabase.getAllFormatos(ActivityMain.usuario);

        mDatabase=FirebaseDatabase.getInstance().getReference();
        DatabaseReference reference = mDatabase.child("favoritos").child(FirebaseAuth.getInstance().getCurrentUser().getUid());


        final List<Formato>listaFinal=new ArrayList<>();
        //QUIERO ESCUCHAR LA LISTA DE DATOS CADA VEZ QUE SE MODIFICA

        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Formato> listaFormatos = new ArrayList<>();
                Integer contador=0;
                for (DataSnapshot artistSnaptshot: dataSnapshot.child("peliculas").getChildren()) {
                    Formato unFormato = artistSnaptshot.getValue(Formato.class);
                    if (unFormato.getTitle().toLowerCase().contains(busquedaIngresada.toLowerCase())){
                        listaFormatos.add(contador,unFormato);
                        contador=contador+1;
                    }
                    else{
                        listaFormatos.add(unFormato);
                    }

                }
                for (DataSnapshot artistSnaptshot: dataSnapshot.child("series").getChildren()) {
                    Formato unFormato = artistSnaptshot.getValue(Formato.class);
                    if (unFormato.getName().toLowerCase().contains(busquedaIngresada.toLowerCase())){
                        listaFormatos.add(contador, unFormato);
                        contador=contador+1;
                    }
                    else{
                        listaFormatos.add(unFormato);
                    }
                    listaFormatos.add(unFormato);
                }
                listenerFromView.finish(listaFormatos);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        });



    }









    public void agregarFavorito(final Formato unFormato, final String unString){
        if (ActivityMain.login) {

            final DAOFavoritosDatabase daoFavoritosDatabase = new DAOFavoritosDatabase(context);
            final Formato elFormato = unFormato;
            if (daoFavoritosDatabase.checkIfExist(unFormato.getId(), ActivityMain.usuario)) {
                Toast.makeText(context, "El formato ya figura entre los favoritos", Toast.LENGTH_SHORT).show();
            }
            else {
                daoFavoritosDatabase.addFormato(unFormato, unString);
                Activity unaActivity = (Activity) context;
                View view = (View) unaActivity.findViewById(R.id.detalle_contenedor_fragment);
                subirFavoritoAFirebase(unFormato, FirebaseAuth.getInstance().getCurrentUser().getUid());

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
    
    public void eliminarFavorito(final Formato unFormato, final String unString){



        final DAOFavoritosDatabase daoFavoritosDatabase= new DAOFavoritosDatabase(context);
        String nombre;
        String tipo;
        if (unFormato.getTitle()==null || unFormato.getTitle().isEmpty()){
            nombre=unFormato.getName();
            tipo="serie";
        }
        else{
            nombre=unFormato.getTitle();
            tipo="pelicula";
        }
        if(daoFavoritosDatabase.checkIfExist(unFormato.getId(), ActivityMain.usuario)){

            crearDialogoBorrado(unFormato, unString,nombre);

        }
        else{
            Toast.makeText(context, "El formato, no se encuentra en la lista de favoritos", Toast.LENGTH_SHORT).show();
        }
    }

    public void traerBusquedaDrawer(final ResultListener<List<Formato>> listenerFromView, Integer id){
        switch (id){
            case R.id.peliMasVista:
               obtenerPeliculasPopulares(listenerFromView);
            break;
            case R.id.serieMasVista:
                obtenerSeriesMasValoradas(listenerFromView);
            break;
            case R.id.animacion:
                obtenerPeliculasPorGenero(listenerFromView, TMDBHelper.MOVIE_GENRE_ANIMATION);
            break;
            case R.id.documentales:
                obtenerSeriesPorGenero(listenerFromView,TMDBHelper.TV_GENRE_DOCUMENTARY);
            break;
            case R.id.seriesHoy:
                obtenerSeriesEnCartel(listenerFromView);
            break;
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

    public Boolean existeUnFavoritoDeterminado(final ResultListener<Boolean> listenerFromView,Integer unId) {
        if (ActivityMain.login) {
            final DAOFavoritosDatabase daoFavoritosDatabase = new DAOFavoritosDatabase(context);
            if (daoFavoritosDatabase.checkIfExist(unId, ActivityMain.usuario)) {
                listenerFromView.finish(true);
                return true;
            } else {
                listenerFromView.finish(false);
                return false;
            }
        }
        else {
            return false;
        }
    }


    public void traerDetallesPersona(final ResultListener<Actor> listenerFromView, Integer actorId){
        if (HTTPConnectionManager.isNetworkingOnline(context)) {
            DAOFormatoInternet daoFormatoInternet = new DAOFormatoInternet();
            daoFormatoInternet.obtenerdetalleActor (new ResultListener<Actor>() {
                @Override
                public void finish(Actor unaPagina) {

                    if (unaPagina == null ) {

                    } else {

                        listenerFromView.finish(unaPagina);
                    }
                }
            },actorId);
        }
        else{

        }
    }
    public void traerCreditosPersona(final ResultListener<List<Credito>> listenerFromView, Integer actorId){
        if (HTTPConnectionManager.isNetworkingOnline(context)) {
            DAOFormatoInternet daoFormatoInternet = new DAOFormatoInternet();
            daoFormatoInternet.obtenerCreditosActor (new ResultListener<List<Credito>>() {
                @Override
                public void finish(List<Credito> unaPagina) {


                        listenerFromView.finish(unaPagina);

                }
            },actorId);
        }
        else{

        }
    }

    public void traerTrailers(final ResultListener<List<Trailer>> listenerFromView, Integer formatoId, String tipoFormato){
        if (HTTPConnectionManager.isNetworkingOnline(context)) {
            DAOFormatoInternet daoFormatoInternet = new DAOFormatoInternet();
            if (tipoFormato.equals("series")) {
                daoFormatoInternet.obtenerTrailerSeries(new ResultListener<List<Trailer>>() {
                    @Override
                    public void finish(List<Trailer> unaPagina) {


                        listenerFromView.finish(unaPagina);

                    }
                }, formatoId);
            }
            else{
                daoFormatoInternet.obtenerTrailerPeliculas(new ResultListener<List<Trailer>>() {
                    @Override
                    public void finish(List<Trailer> unaPagina) {


                        listenerFromView.finish(unaPagina);

                    }
                }, formatoId);
            }
        }
        else{

        }
    }

    public void traerUnFormato(final ResultListener<Formato> listenerFromView, Integer formatoId, String tipoFormato){
        if (HTTPConnectionManager.isNetworkingOnline(context)) {
            DAOFormatoInternet daoFormatoInternet = new DAOFormatoInternet();
            if (tipoFormato.equals("series")) {
                daoFormatoInternet.obtenerSerieDesdeInternet(new ResultListener<Formato>() {
                    @Override
                    public void finish(Formato unaPagina) {


                        listenerFromView.finish(unaPagina);

                    }
                }, formatoId);
            }
            else{
                daoFormatoInternet.obtenerPeliculaDesdeInternet(new ResultListener<Formato>() {
                    @Override
                    public void finish(Formato unaPagina) {

                        listenerFromView.finish(unaPagina);

                    }
                }, formatoId);
            }
        }
        else{

        }
    }
    
    public void traerImagenesAdicionales(final ResultListener<List<Imagen>> listenerFromView, Integer personaId){
        if (HTTPConnectionManager.isNetworkingOnline(context)) {
            DAOFormatoInternet daoFormatoInternet = new DAOFormatoInternet();

                daoFormatoInternet.traerImagenesAdicionales(new ResultListener<List<Imagen>>() {
                    @Override
                    public void finish(List<Imagen> unaPagina) {
                        listenerFromView.finish(unaPagina);
                    }
                }, personaId);
        }
    }



    public void subirFavoritoAFirebase(Formato unFormato, String unUsuario){
        mDatabase=FirebaseDatabase.getInstance().getReference();
        String tipoFormato;
        if (unFormato.getTitle()==null || unFormato.getTitle().isEmpty()){
             tipoFormato="series";
        }
        else {
             tipoFormato="peliculas";
        }
        DatabaseReference databaseReference = mDatabase.child("favoritos").child(unUsuario).child(tipoFormato).child(unFormato.getId().toString());
        databaseReference.setValue(unFormato);

    }

    public void eliminarFavoritoFirebase(Formato unFormato, String unUsuario){
        mDatabase=FirebaseDatabase.getInstance().getReference();
        String tipoFormato;
        if (unFormato.getTitle()==null || unFormato.getTitle().isEmpty()){
            tipoFormato="series";
        }
        else {
            tipoFormato="peliculas";
        }
        DatabaseReference databaseReference = mDatabase.child("favoritos").child(unUsuario).child(tipoFormato).child(unFormato.getId().toString());
        databaseReference.removeValue();
    }

    public void crearDialogoBorrado(final Formato unFormato, final String unString, final String nombre) {


        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.Dialog);
        final DAOFavoritosDatabase daoFavoritosDatabase= new DAOFavoritosDatabase(context);
        final Activity unaActivity = (Activity) context;


        builder.setTitle("Borrar un Favorito?")
                .setMessage("Esta seguro que quiere eliminar:"+"\n"+nombre+"\n"+"de sus favoritos?")
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        daoFavoritosDatabase.borrarFavorito(unFormato, unString);
                        eliminarFavoritoFirebase(unFormato,FirebaseAuth.getInstance().getCurrentUser().getUid());
                        unaActivity.finish();

                    }
                })
                .setNegativeButton("Conservar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AlertDialog unDialogo= builder.create();
        unDialogo.show();

        final Button positiveButton = unDialogo.getButton(AlertDialog.BUTTON_POSITIVE);
        final Button negativeButton = unDialogo.getButton(AlertDialog.BUTTON_NEGATIVE);
        positiveButton.setTextColor(context.getResources().getColor(R.color.paleta4));
        negativeButton.setTextColor(context.getResources().getColor(R.color.paleta4));

        LinearLayout.LayoutParams positiveButtonLL = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
        positiveButtonLL.width = ViewGroup.LayoutParams.MATCH_PARENT;
        positiveButton.setLayoutParams(positiveButtonLL);
    }
    
    public void subirPreguntaFirebase(Pregunta unaPregunta){
        mDatabase=FirebaseDatabase.getInstance().getReference();
        String posicion= UUID.randomUUID().toString();
        DatabaseReference reference = mDatabase.child("Preguntas").child(posicion);
        reference.setValue(unaPregunta);
    }



    public void traerPreguntaFirebase(final ResultListener<Pregunta> listenerFromView) {
        //SI NO HAY CONEXION UTILIZO LOS DATOS ALMACENADOS EN LA BASE DE DATOS. Para pedir los datos almacenados en la
        // Base de datos utilizo el DAOPersonDatabase.
        //DAOFavoritosDatabase daoFavoritosDatabase = new DAOFavoritosDatabase(context);
        // List<Pregunta> formatos = daoFavoritosDatabase.getAllFormatos(ActivityMain.usuario);

        mDatabase=FirebaseDatabase.getInstance().getReference();
        DatabaseReference reference = mDatabase.child("Preguntas");


        //QUIERO ESCUCHAR LA LISTA DE DATOS CADA VEZ QUE SE MODIFICA

        reference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Pregunta>listaPregunta=new ArrayList<>();

                for (DataSnapshot preguntasSnapshot: dataSnapshot.getChildren()) {
                    Pregunta unaPregunta = preguntasSnapshot.getValue(Pregunta.class);
                    listaPregunta.add(unaPregunta);
                }

                int randomNum = ThreadLocalRandom.current().nextInt(0, listaPregunta.size());
                Pregunta controlPregunta=listaPregunta.get(randomNum);
                while (controlPregunta.getAutor().equals(FirebaseAuth.getInstance().getCurrentUser().getDisplayName())){
                    randomNum = ThreadLocalRandom.current().nextInt(0, listaPregunta.size());
                    controlPregunta=listaPregunta.get(randomNum);
                }

                listenerFromView.finish(listaPregunta.get(randomNum));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        });



    }

    public void sumarPuntosFirebase(final ResultListener<String> listenerFromView){
        mDatabase=FirebaseDatabase.getInstance().getReference();
        final DatabaseReference reference = mDatabase.child("Puntajes").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        reference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String puntajeUsuario;
                if (dataSnapshot.getValue()==null) {
                    puntajeUsuario = "0";
                }
                else {
                    puntajeUsuario=dataSnapshot.getValue().toString();
                }
                Integer unInteger=Integer.parseInt(puntajeUsuario)+100;
                 puntajeUsuario=unInteger.toString();
                reference.setValue(puntajeUsuario);
                listenerFromView.finish(puntajeUsuario);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        });

    }
    public void restarPuntosFirebase(final ResultListener<String> listenerFromView){
        mDatabase=FirebaseDatabase.getInstance().getReference();
        final DatabaseReference reference = mDatabase.child("Puntajes").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        reference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String puntajeUsuario;
                if (dataSnapshot.getValue()==null) {
                    puntajeUsuario = "0";
                }
                else {
                    if (Integer.parseInt(dataSnapshot.getValue().toString()) < 30) {
                        puntajeUsuario = "0";
                    } else {
                        Integer unInteger = Integer.parseInt(dataSnapshot.getValue().toString())-30;
                        puntajeUsuario=unInteger.toString();
                    }
                }

                reference.setValue(puntajeUsuario);
                listenerFromView.finish(puntajeUsuario);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        });

    }
    public void obtenerPuntajeFirebase(final ResultListener<String> listenerFromView){
        mDatabase=FirebaseDatabase.getInstance().getReference();
        final DatabaseReference reference = mDatabase.child("Puntajes").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        reference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String puntajeUsuario;
                if (dataSnapshot.getValue()==null) {
                    puntajeUsuario = "0";
                }
                else {
                    puntajeUsuario=dataSnapshot.getValue().toString();
                }

                listenerFromView.finish(puntajeUsuario);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        });
    }



}
