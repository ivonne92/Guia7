package com.guia7.guia7Ivonne.Modelo;

public class Producto {
    String id_producto;
    String nombre;
    String descripcion;
    String id_categoria;

    public Producto(String id_producto, String nombre, String descripcion, String id_categoria) {
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id_categoria = id_categoria;
    }

    public String getId_producto() {
        return id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getId_categoria() {
        return id_categoria;
    }
}
