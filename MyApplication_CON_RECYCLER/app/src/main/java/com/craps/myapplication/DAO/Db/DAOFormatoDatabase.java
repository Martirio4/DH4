package com.craps.myapplication.DAO.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.craps.myapplication.Model.Formato;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by digitalhouse on 10/06/17.
 */

public class DAOFormatoDatabase extends DatabaseHelper {

   public static final String TITLE = "TITLE";
   public static final String RELEASE_DATE = "RELEASE_DATE";
   public static final String OVERVIEW = "OVERVIEW";
   public static final String VOTE_AVERAGE = "VOTE_AVERAGE";
   public static final String TIPO_FORMATO = "TIPO_FORMATO";
   public static final String POSTER_PATH = "POSTER_PATH";
   public static final String ID = "ID";
   public static final String BACKDROP_PATH = "BACKDROP_PATH";
   public static final String NAME = "NAME";
   public static final String FIRST_AIR_DATE = "FIRST_AIR_DATE";
   public static final String TAGLINE = "TAGLINE";
   public static final String NUMBER_OF_SEASONS = "NUMBER_OF_SEASONS";
   public static final String NUMBER_OF_EPISODES = "NUMBER_OF_EPISODES";
   public static final String BUDGET = "BUDGET";
   public static final String REVENUE = "REVENUE";
   public static final String TABLE_FORMATOS="TABLE_FORMATOS";




    public DAOFormatoDatabase(Context context) {
        super(context);
    }


    public void addFormato (Formato unFormato, String tipoFormato){

        if(!checkIfExist(unFormato.getId())) {

            SQLiteDatabase database = getWritableDatabase();

            //CREO LA FILA Y LE CARGO LOS DATOS
            ContentValues row = new ContentValues();
            row.put(TITLE, unFormato.getTitle());
            row.put(RELEASE_DATE, unFormato.getRelease_date());
            row.put(OVERVIEW, unFormato.getOverview());
            row.put(VOTE_AVERAGE, unFormato.getVote_average());
            row.put(TIPO_FORMATO,tipoFormato);
            row.put(POSTER_PATH, unFormato.getPoster_path());
            row.put(ID, unFormato.getId());
            row.put(BACKDROP_PATH, unFormato.getBackdrop_path());
            row.put(NAME, unFormato.getName());
            row.put(FIRST_AIR_DATE, unFormato.getFirst_air_date());
            row.put(TAGLINE, unFormato.getTagline());
            row.put(NUMBER_OF_SEASONS, unFormato.getNumber_of_seasons());
            row.put(NUMBER_OF_EPISODES, unFormato.getNumber_of_episodes());
            row.put(BUDGET, unFormato.getBudget());
            row.put(REVENUE, unFormato.getRevenue());


            //LE DIGO A LA BD QUE CARGUE LA FILA EN LA TABLA
            database.insert(TABLE_FORMATOS, null, row);

            database.close();
        }
    }

    public void addFormatos(List<Formato> formatosList,String tipoFormato){

        for(Formato unFormato : formatosList){
            addFormato(unFormato, tipoFormato);
        }
    }


    public List<Formato> getAllFormatosPorTipo(String tipoFormato){

        List<Formato> formatos  = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        String select = "SELECT * FROM " + TABLE_FORMATOS+" WHERE TIPO_FORMATO = '"+tipoFormato+"'";

        Cursor cursor = database.rawQuery(select, null);
        while(cursor.moveToNext()){

            //LEER CADA FILA DE LA TABLA RESULTADO
            Formato unFormato = new Formato();
            unFormato.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
            unFormato.setRelease_date(cursor.getString(cursor.getColumnIndex(RELEASE_DATE)));
            unFormato.setOverview(cursor.getString(cursor.getColumnIndex(OVERVIEW)));
            unFormato.setVote_average(cursor.getFloat(cursor.getColumnIndex(VOTE_AVERAGE)));
            unFormato.setTipoFormato(cursor.getString(cursor.getColumnIndex(TIPO_FORMATO)));
            unFormato.setPoster_path(cursor.getString(cursor.getColumnIndex(POSTER_PATH)));
            unFormato.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            unFormato.setBackdrop_path(cursor.getString(cursor.getColumnIndex(BACKDROP_PATH)));
            unFormato.setName(cursor.getString(cursor.getColumnIndex(NAME)));
            unFormato.setFirst_air_date(cursor.getString(cursor.getColumnIndex(FIRST_AIR_DATE)));
            unFormato.setTagline(cursor.getString(cursor.getColumnIndex(TAGLINE)));
            unFormato.setNumber_of_seasons(cursor.getInt(cursor.getColumnIndex(NUMBER_OF_SEASONS)));
            unFormato.setNumber_of_episodes(cursor.getInt(cursor.getColumnIndex(NUMBER_OF_EPISODES)));
            unFormato.setBudget(cursor.getInt(cursor.getColumnIndex(BUDGET)));
            unFormato.setRevenue(cursor.getInt(cursor.getColumnIndex(REVENUE)));

            formatos.add(unFormato);
        }

        //CERRAR
        cursor.close();
        database.close();

        return formatos;
    }


    public List<Formato> getFormatosConFiltro(String queBuscoEnInet, String tipoFormato){
        String select;

        List<Formato> formatos  = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();

        if (queBuscoEnInet==null ||queBuscoEnInet.startsWith("https://")) {
            select = "SELECT * FROM " + TABLE_FORMATOS;
        }
        else{
            select = "SELECT * FROM " + TABLE_FORMATOS+" WHERE NAME LIKE '%"+queBuscoEnInet+"%' AND TIPO_FORMATO LIKE "+"'%"+tipoFormato+"%'";
        }


        Cursor cursor = database.rawQuery(select, null);
        while(cursor.moveToNext()){

            //LEER CADA FILA DE LA TABLA RESULTADO
            Formato unFormato = new Formato();
            unFormato.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
            unFormato.setRelease_date(cursor.getString(cursor.getColumnIndex(RELEASE_DATE)));
            unFormato.setOverview(cursor.getString(cursor.getColumnIndex(OVERVIEW)));
            unFormato.setVote_average(cursor.getFloat(cursor.getColumnIndex(VOTE_AVERAGE)));
            unFormato.setTipoFormato(cursor.getString(cursor.getColumnIndex(TIPO_FORMATO)));
            unFormato.setPoster_path(cursor.getString(cursor.getColumnIndex(POSTER_PATH)));
            unFormato.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            unFormato.setBackdrop_path(cursor.getString(cursor.getColumnIndex(BACKDROP_PATH)));
            unFormato.setName(cursor.getString(cursor.getColumnIndex(NAME)));
            unFormato.setFirst_air_date(cursor.getString(cursor.getColumnIndex(FIRST_AIR_DATE)));
            unFormato.setTagline(cursor.getString(cursor.getColumnIndex(TAGLINE)));
            unFormato.setNumber_of_seasons(cursor.getInt(cursor.getColumnIndex(NUMBER_OF_SEASONS)));
            unFormato.setNumber_of_episodes(cursor.getInt(cursor.getColumnIndex(NUMBER_OF_EPISODES)));
            unFormato.setBudget(cursor.getInt(cursor.getColumnIndex(BUDGET)));
            unFormato.setRevenue(cursor.getInt(cursor.getColumnIndex(REVENUE)));

            formatos.add(unFormato);
        }

        //CERRAR
        cursor.close();
        database.close();

        return formatos;
    }





    public Formato getFormato(Integer id){

        SQLiteDatabase database = getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_FORMATOS +
                        " WHERE ID=" + id;

        Cursor cursor = database.rawQuery(query, null);
        Formato unFormato = null;
        if(cursor.moveToNext()){

            //LEER CADA FILA DE LA TABLA RESULTADO

            unFormato = new Formato();
            unFormato.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
            unFormato.setRelease_date(cursor.getString(cursor.getColumnIndex(RELEASE_DATE)));
            unFormato.setOverview(cursor.getString(cursor.getColumnIndex(OVERVIEW)));
            unFormato.setVote_average(cursor.getFloat(cursor.getColumnIndex(VOTE_AVERAGE)));
            unFormato.setTipoFormato(cursor.getString(cursor.getColumnIndex(TIPO_FORMATO)));
            unFormato.setPoster_path(cursor.getString(cursor.getColumnIndex(POSTER_PATH)));
            unFormato.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            unFormato.setBackdrop_path(cursor.getString(cursor.getColumnIndex(BACKDROP_PATH)));
            unFormato.setName(cursor.getString(cursor.getColumnIndex(NAME)));
            unFormato.setFirst_air_date(cursor.getString(cursor.getColumnIndex(FIRST_AIR_DATE)));
            unFormato.setTagline(cursor.getString(cursor.getColumnIndex(TAGLINE)));
            unFormato.setNumber_of_seasons(cursor.getInt(cursor.getColumnIndex(NUMBER_OF_SEASONS)));
            unFormato.setNumber_of_episodes(cursor.getInt(cursor.getColumnIndex(NUMBER_OF_EPISODES)));
            unFormato.setBudget(cursor.getInt(cursor.getColumnIndex(BUDGET)));
            unFormato.setRevenue(cursor.getInt(cursor.getColumnIndex(REVENUE)));
        }

        cursor.close();
        database.close();

        return unFormato;
    }

    public Boolean checkIfExist(Integer id){
        Formato unFormato = getFormato(id);
        return !(unFormato == null);
    }

    public List<Formato> busquedaPorVotoPeliculas(){
        String select;

        List<Formato> formatos  = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();



        select = "SELECT * FROM " + TABLE_FORMATOS+" WHERE TITLE IS NOT NULL ORDER BY VOTE_AVERAGE ASC";



        Cursor cursor = database.rawQuery(select, null);
        while(cursor.moveToNext()){

            //LEER CADA FILA DE LA TABLA RESULTADO
            Formato unFormato = new Formato();
            unFormato.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
            unFormato.setRelease_date(cursor.getString(cursor.getColumnIndex(RELEASE_DATE)));
            unFormato.setOverview(cursor.getString(cursor.getColumnIndex(OVERVIEW)));
            unFormato.setVote_average(cursor.getFloat(cursor.getColumnIndex(VOTE_AVERAGE)));
            unFormato.setTipoFormato(cursor.getString(cursor.getColumnIndex(TIPO_FORMATO)));
            unFormato.setPoster_path(cursor.getString(cursor.getColumnIndex(POSTER_PATH)));
            unFormato.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            unFormato.setBackdrop_path(cursor.getString(cursor.getColumnIndex(BACKDROP_PATH)));
            unFormato.setName(cursor.getString(cursor.getColumnIndex(NAME)));
            unFormato.setFirst_air_date(cursor.getString(cursor.getColumnIndex(FIRST_AIR_DATE)));
            unFormato.setTagline(cursor.getString(cursor.getColumnIndex(TAGLINE)));
            unFormato.setNumber_of_seasons(cursor.getInt(cursor.getColumnIndex(NUMBER_OF_SEASONS)));
            unFormato.setNumber_of_episodes(cursor.getInt(cursor.getColumnIndex(NUMBER_OF_EPISODES)));
            unFormato.setBudget(cursor.getInt(cursor.getColumnIndex(BUDGET)));
            unFormato.setRevenue(cursor.getInt(cursor.getColumnIndex(REVENUE)));

            formatos.add(unFormato);
        }

        //CERRAR
        cursor.close();
        database.close();

        return formatos;
    }

    public List<Formato> busquedaPorPresupuestoPeliculas(){
        String select;

        List<Formato> formatos  = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();



        select = "SELECT * FROM " + TABLE_FORMATOS+" WHERE TITLE IS NOT NULL ORDER BY BUDGET ASC";



        Cursor cursor = database.rawQuery(select, null);
        while(cursor.moveToNext()){

            //LEER CADA FILA DE LA TABLA RESULTADO
            Formato unFormato = new Formato();
            unFormato.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
            unFormato.setRelease_date(cursor.getString(cursor.getColumnIndex(RELEASE_DATE)));
            unFormato.setOverview(cursor.getString(cursor.getColumnIndex(OVERVIEW)));
            unFormato.setVote_average(cursor.getFloat(cursor.getColumnIndex(VOTE_AVERAGE)));
            unFormato.setTipoFormato(cursor.getString(cursor.getColumnIndex(TIPO_FORMATO)));
            unFormato.setPoster_path(cursor.getString(cursor.getColumnIndex(POSTER_PATH)));
            unFormato.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            unFormato.setBackdrop_path(cursor.getString(cursor.getColumnIndex(BACKDROP_PATH)));
            unFormato.setName(cursor.getString(cursor.getColumnIndex(NAME)));
            unFormato.setFirst_air_date(cursor.getString(cursor.getColumnIndex(FIRST_AIR_DATE)));
            unFormato.setTagline(cursor.getString(cursor.getColumnIndex(TAGLINE)));
            unFormato.setNumber_of_seasons(cursor.getInt(cursor.getColumnIndex(NUMBER_OF_SEASONS)));
            unFormato.setNumber_of_episodes(cursor.getInt(cursor.getColumnIndex(NUMBER_OF_EPISODES)));
            unFormato.setBudget(cursor.getInt(cursor.getColumnIndex(BUDGET)));
            unFormato.setRevenue(cursor.getInt(cursor.getColumnIndex(REVENUE)));

            formatos.add(unFormato);
        }

        //CERRAR
        cursor.close();
        database.close();

        return formatos;
    }


    public List<Formato> busquedaPorVotoSerie(){
        String select;

        List<Formato> formatos  = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();



        select = "SELECT * FROM " + TABLE_FORMATOS+" WHERE NAME IS NOT NULL ORDER BY VOTE_AVERAGE ASC";



        Cursor cursor = database.rawQuery(select, null);
        while(cursor.moveToNext()){

            //LEER CADA FILA DE LA TABLA RESULTADO
            Formato unFormato = new Formato();
            unFormato.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
            unFormato.setRelease_date(cursor.getString(cursor.getColumnIndex(RELEASE_DATE)));
            unFormato.setOverview(cursor.getString(cursor.getColumnIndex(OVERVIEW)));
            unFormato.setVote_average(cursor.getFloat(cursor.getColumnIndex(VOTE_AVERAGE)));
            unFormato.setTipoFormato(cursor.getString(cursor.getColumnIndex(TIPO_FORMATO)));
            unFormato.setPoster_path(cursor.getString(cursor.getColumnIndex(POSTER_PATH)));
            unFormato.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            unFormato.setBackdrop_path(cursor.getString(cursor.getColumnIndex(BACKDROP_PATH)));
            unFormato.setName(cursor.getString(cursor.getColumnIndex(NAME)));
            unFormato.setFirst_air_date(cursor.getString(cursor.getColumnIndex(FIRST_AIR_DATE)));
            unFormato.setTagline(cursor.getString(cursor.getColumnIndex(TAGLINE)));
            unFormato.setNumber_of_seasons(cursor.getInt(cursor.getColumnIndex(NUMBER_OF_SEASONS)));
            unFormato.setNumber_of_episodes(cursor.getInt(cursor.getColumnIndex(NUMBER_OF_EPISODES)));
            unFormato.setBudget(cursor.getInt(cursor.getColumnIndex(BUDGET)));
            unFormato.setRevenue(cursor.getInt(cursor.getColumnIndex(REVENUE)));

            formatos.add(unFormato);
        }

        //CERRAR
        cursor.close();
        database.close();

        return formatos;
    }

    public List<Formato> busquedaPorPresupuestoSerie(){
        String select;

        List<Formato> formatos  = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();



        select = "SELECT * FROM " + TABLE_FORMATOS+" WHERE NAME IS NOT NULL ORDER BY BUDGET ASC";



        Cursor cursor = database.rawQuery(select, null);
        while(cursor.moveToNext()){

            //LEER CADA FILA DE LA TABLA RESULTADO
            Formato unFormato = new Formato();
            unFormato.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
            unFormato.setRelease_date(cursor.getString(cursor.getColumnIndex(RELEASE_DATE)));
            unFormato.setOverview(cursor.getString(cursor.getColumnIndex(OVERVIEW)));
            unFormato.setVote_average(cursor.getFloat(cursor.getColumnIndex(VOTE_AVERAGE)));
            unFormato.setTipoFormato(cursor.getString(cursor.getColumnIndex(TIPO_FORMATO)));
            unFormato.setPoster_path(cursor.getString(cursor.getColumnIndex(POSTER_PATH)));
            unFormato.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            unFormato.setBackdrop_path(cursor.getString(cursor.getColumnIndex(BACKDROP_PATH)));
            unFormato.setName(cursor.getString(cursor.getColumnIndex(NAME)));
            unFormato.setFirst_air_date(cursor.getString(cursor.getColumnIndex(FIRST_AIR_DATE)));
            unFormato.setTagline(cursor.getString(cursor.getColumnIndex(TAGLINE)));
            unFormato.setNumber_of_seasons(cursor.getInt(cursor.getColumnIndex(NUMBER_OF_SEASONS)));
            unFormato.setNumber_of_episodes(cursor.getInt(cursor.getColumnIndex(NUMBER_OF_EPISODES)));
            unFormato.setBudget(cursor.getInt(cursor.getColumnIndex(BUDGET)));
            unFormato.setRevenue(cursor.getInt(cursor.getColumnIndex(REVENUE)));

            formatos.add(unFormato);
        }

        //CERRAR
        cursor.close();
        database.close();

        return formatos;
    }
}
