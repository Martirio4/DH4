package com.craps.myapplication.DAO.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.craps.myapplication.Model.Usuario;

/**
 * Created by digitalhouse on 10/06/17.
 */

public class DAOUsuarioDatabase extends DatabaseHelper {


   public static final String TABLE_USUARIOS="TABLE_USUARIOS";
    public static final String USER="USUARIO";
    public static final String PASS="CONTRASEÑA";
    public static final String PAIS="PAIS";
    public static final String FECHANACIMIENTO="FECHANACIMIENTO";
    public static final String IDUSUARIO="IDUSUARIO";
    public static final String MAIL="MAIL";
    public static final String IDIOMA="IDIOMA";
    



    public DAOUsuarioDatabase(Context context) {
        super(context);
    }


    public Boolean addUsuario (Usuario unUsuario){

        if(!checkIfUserExist(unUsuario)) {

            SQLiteDatabase database = getWritableDatabase();

            //CREO LA FILA Y LE CARGO LOS DATOS
            ContentValues row = new ContentValues();
            row.put(USER, unUsuario.getNombre());
            row.put(PASS, unUsuario.getContraseña());
            row.put(PAIS, unUsuario.getPais());
            row.put(IDIOMA, unUsuario.getIdoma());
            row.put(FECHANACIMIENTO, unUsuario.getFechanacimiento());
            row.put(MAIL, unUsuario.getMail());




            //LE DIGO A LA BD QUE CARGUE LA FILA EN LA TABLA
            database.insert(TABLE_USUARIOS, null, row);
            database.close();
            return true;
        }
        return false;
    }




    public Usuario getUsuario(Usuario unUsuario){

        SQLiteDatabase database = getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_USUARIOS +
                " WHERE MAIL= " +"'"+unUsuario.getMail()+"'";

        Cursor cursor = database.rawQuery(query, null);
        Usuario elUsuario = null;
        if(cursor.moveToNext()){

            //LEER CADA FILA DE LA TABLA RESULTADO

            elUsuario=new Usuario();
            elUsuario.setNombre(cursor.getString(cursor.getColumnIndex(USER)));
            elUsuario.setContraseña(cursor.getString(cursor.getColumnIndex(PASS)));
            elUsuario.setPais(cursor.getString(cursor.getColumnIndex(PAIS)));
            elUsuario.setIdoma(cursor.getString(cursor.getColumnIndex(IDIOMA)));
            elUsuario.setMail(cursor.getString(cursor.getColumnIndex(MAIL)));
            elUsuario.setFechanacimiento(cursor.getString(cursor.getColumnIndex(FECHANACIMIENTO)));

            
        }

        cursor.close();
        database.close();

        return elUsuario;
    }





    public Boolean checkIfUserExist(Usuario unUsuario){
        Usuario user = getUsuario(unUsuario);
        return !(user == null);
    }
}
