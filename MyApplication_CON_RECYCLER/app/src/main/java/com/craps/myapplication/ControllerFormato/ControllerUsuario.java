package com.craps.myapplication.ControllerFormato;

import android.content.Context;
import android.widget.Toast;
import com.craps.myapplication.DAO.Db.DAOUsuarioDatabase;
import com.craps.myapplication.Model.Usuario;

/**
 * Created by elmar on 3/6/2017.
 */

public class ControllerUsuario {

    private Context context;

    public ControllerUsuario(Context context) {
        this.context = context;
    }

    public Boolean RegistrarUsuario(Usuario unUsuario){
        DAOUsuarioDatabase daoUsuarioDatabase= new DAOUsuarioDatabase(context);

        if (daoUsuarioDatabase.addUsuario(unUsuario)){
            return true;
        }
        else{

            return false;
        }
    }

    public Boolean existeUsuario(Usuario unUsuario){
        DAOUsuarioDatabase daoUsuarioDatabase = new DAOUsuarioDatabase(context);
        return daoUsuarioDatabase.checkIfUserExist(unUsuario);
    }

    public Boolean loguearUsuario(String mail, String contraseña){
        DAOUsuarioDatabase daoUsuarioDatabase= new DAOUsuarioDatabase(context);
        Usuario unUsuario = new Usuario();
        unUsuario.setMail(mail);
         if (daoUsuarioDatabase.checkIfUserExist(unUsuario)){
             String contraseñaDB=daoUsuarioDatabase.getUsuario(unUsuario).getContraseña();
             String nombreDB= daoUsuarioDatabase.getUsuario(unUsuario).getNombre();

             if (contraseñaDB.equals(contraseña)){
                 Toast.makeText(context, "Bienvenido "+nombreDB, Toast.LENGTH_SHORT).show();
                 return true;
             }
             else{
                 Toast.makeText(context, "La contraseña ingresada es incorrecta", Toast.LENGTH_SHORT).show();
                 return false;
             }
         }
        Toast.makeText(context, "El usuario no existe", Toast.LENGTH_SHORT).show();
         return false;
    }
}