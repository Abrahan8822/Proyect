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
    private String compani;
    private Double preciokw;

    public String getCompani() {
        return compani;
    }

    public void setCompani(String compani) {
        this.compani = compani;
    }

    public Double getPreciokw() {
        return preciokw;
    }

    public void setPreciokw(Double preciokw) {
        this.preciokw = preciokw;
    }

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
    /*public Devices(String nSerie, String nombre, String uidUsuario, String pass, int estado, double corriente) {
        this.nSerie = nSerie;
        this.nombre = nombre;
        this.uidUsuario = uidUsuario;
        this.pass = pass;
        this.estado = estado;
        this.corriente = corriente;
    }*/
    //get updat device
    public Devices(String compani, String nSerie, String nombre, double preciokw) {
        this.compani=compani;
        this.nSerie = nSerie;
        this.nombre = nombre;
        this.preciokw=preciokw;

    }
//contructor obtener lista
    public Devices(String nSerie, String nombre) {
        this.nSerie = nSerie;
        this.nombre = nombre;
    }
    //constructor set updateNormal
    public Devices(String compani,Map fechaAct, String nombre,double preciokw) {
        this.compani=compani;
        this.fechaAct = fechaAct;
        this.nombre = nombre;
        this.preciokw = preciokw;
    }
//Este constructor para set
    public Devices(String nSerie, String nombre, String uidUsuario, String pass, int estado, double corriente,String compani,double preciokw, Map fechaRegistro, Map fechaAct) {
        this.nSerie = nSerie;
        this.nombre = nombre;
        this.uidUsuario = uidUsuario;
        this.pass = pass;
        this.estado = estado;
        this.corriente = corriente;
        this.compani=compani;
        this.preciokw=preciokw;
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
