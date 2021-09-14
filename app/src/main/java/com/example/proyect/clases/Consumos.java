package com.example.proyect.clases;

import java.util.Map;

public class Consumos {
    private double consumo;

    public Consumos(double consumo, String fechaHora) {
        this.consumo = consumo;
        this.fechaHora = fechaHora;
    }

    private String fechaHora;


    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public double getConsumo() {
        return consumo;
    }

    public void setConsumo(double consumo) {
        this.consumo = consumo;
    }
}
