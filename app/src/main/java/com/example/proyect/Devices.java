package com.example.proyect;

import java.sql.Timestamp;

public class Devices {
    private String nSerie;
    private String nombre;
    private String uidUsuario;
    private String pass;
    private int estado=1;
    private double corriente=0;

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Timestamp getFechaAct() {
        return fechaAct;
    }

    public void setFechaAct(Timestamp fechaAct) {
        this.fechaAct = fechaAct;
    }

    private Timestamp fechaRegistro;
    private Timestamp fechaAct;

    public String getnSerie() {
        return nSerie;
    }

    public void setnSerie(String nSerie) {
        this.nSerie = nSerie;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUidUsuario() {
        return uidUsuario;
    }

    public void setUidUsuario(String uidUsuario) {
        this.uidUsuario = uidUsuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public double getCorriente() {
        return corriente;
    }

    public void setCorriente(double corriente) {
        this.corriente = corriente;
    }


}
