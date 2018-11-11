package com.guia7.guia7Ivonne.Modelo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private String crearCategoria ="create table categoria"+
            "("+
            "id_categoria INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nombre VARCHAR(250)" +
            ")";

    private String crearProducto ="create table producto"+
            "("+
            "id_producto INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nombre VARCHAR(250)," +
            "descripcion TEXT," +
            "id_categoria INTEGER," +
            "foreign key(id_categoria) references categoria(id_categoria)" +
            ")";
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //primero se crean las tablas maestros (sin Foreign Key)
        db.execSQL(crearCategoria);
        db.execSQL(crearProducto);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //primero borrar las maestros-detalle
        db.execSQL("DROP TABLE IF EXISTS producto");
        db.execSQL("DROP TABLE IF EXISTS categoria");
    }
}
