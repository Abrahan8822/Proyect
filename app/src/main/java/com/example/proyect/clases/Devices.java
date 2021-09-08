package com.example.proyect.clases;

import android.widget.ArrayAdapter;

import java.sql.Timestamp;
import java.util.Map;

public class Devices {
    private String nSerie;
    private String nombre;
    private String uidUsuario;
    private String pass;
    private int estado=1;
    private double corriente=0;
    private Map fechaRegistro;
    private Map fechaAct;

    public ArrayAdapter getListaDevices() {
        return ListaDevices;
    }

    public void setListaDevices(ArrayAdapter listaDevices) {
        ListaDevices = listaDevices;
    }

    private ArrayAdapter ListaDevices;

    public Devices() {
    }
    //Este constructor para get
    public Devices(String nSerie, String nombre, String uidUsuario, String pass, int estado, double corriente) {
        this.nSerie = nSerie;
        this.nombre = nombre;
        this.uidUsuario = uidUsuario;
        this.pass = pass;
        this.estado = estado;
        this.corriente = corriente;
    }
//contructor obtener lista
    public Devices(String nSerie, String nombre) {
        this.nSerie = nSerie;
        this.nombre = nombre;
    }
    //constructor update
    public Devices(int estado,Map fechaAct,String nSerie, String nombre,String pass) {
        this.estado = estado;
        this.fechaAct = fechaAct;
        this.nSerie = nSerie;
        this.nombre = nombre;
        this.pass = pass;
    }
//Este constructor para set
    public Devices(String nSerie, String nombre, String uidUsuario, String pass, int estado, double corriente, Map fechaRegistro, Map fechaAct) {
        this.nSerie = nSerie;
        this.nombre = nombre;
        this.uidUsuario = uidUsuario;
        this.pass = pass;
        this.estado = estado;
        this.corriente = corriente;
        this.fechaRegistro = fechaRegistro;
        this.fechaAct = fechaAct;
    }


    public Map getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Map fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Map getFechaAct() {
        return fechaAct;
    }

    public void setFechaAct(Map fechaAct) {
        this.fechaAct = fechaAct;
    }

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

    @Override
    public String toString() {
        return nombre;
    }
}
