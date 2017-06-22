package com.craps.myapplication.Model;

import com.craps.myapplication.Utils.TMDBHelper;

/**
 * Created by elmar on 20/6/2017.
 */

public class Usuario {
    private String nombre;
    private String contraseña;
    private String idoma;
    private String pais;
    private String fechanacimiento;

    private String mail;

    public Usuario(String nombre, String contraseña, String idoma, String pais, String fechanacimiento, Integer id, String mail) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.idoma = TMDBHelper.language_SPANISH;
        this.pais = "Argentina";
        this.fechanacimiento = "02/02/1983";

        this.mail = mail;
    }

    public Usuario() {
        this.nombre = null;
        this.contraseña = null;
        this.idoma = null;
        this.pais = pais;
        this.fechanacimiento = null;
        this.mail = null;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getIdoma() {
        return idoma;
    }

    public void setIdoma(String idoma) {
        this.idoma = idoma;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(String fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }


}
