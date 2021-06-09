package com.example.allreceips20.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Receta{
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String titulo;
    public String descripcion;
    public String portada;
    public int lista;


    @Ignore
    public Receta(String titulo, String descripcion, String portada) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.portada = portada;
    }

    public Receta(String titulo, String descripcion, String portada, int lista) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.portada = portada;
        this.lista = lista;
    }
}
