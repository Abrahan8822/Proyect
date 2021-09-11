package com.example.proyect.clases;

import java.util.Map;

public class Usuarios {
    public String nombre,correo,compani;
    int estado;
    Map fechaAct,fechaRegistro;

    public Usuarios()
    {

    }
    public Usuarios(String nombre,String correo,String compani )
    {
        this.nombre=nombre;
        this.correo=correo;
        this.compani=compani;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCompani() {
        return compani;
    }

    public void setCompani(String compani) {
        this.compani = compani;
    }
}
