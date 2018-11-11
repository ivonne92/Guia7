package com.guia7.guia7Ivonne.Modelo;

public class Categoria {
    String id_categoria;
    String nombre;

    public Categoria(String id_categoria, String nombre) {
        this.id_categoria = id_categoria;
        this.nombre = nombre;
    }

    public String getId_categoria() {
        return id_categoria;
    }

    public String getNombre() {
        return nombre;
    }
}
