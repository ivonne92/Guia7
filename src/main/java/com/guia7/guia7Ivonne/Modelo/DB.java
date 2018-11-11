package com.guia7.guia7Ivonne.Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DB {
    private  DBHelper dbHelper;
    public DB(Context context){
        //se define el nombre de la base de datos
        dbHelper = new DBHelper(context,"BD_inventario",null,1);
    }

    //Consulta Simple
    public Cursor getCursorCategoria(){
        return  dbHelper.getReadableDatabase().rawQuery(
                "SELECT * FROM categoria",null );
    }

    //Consulta Multitabla
    public Cursor getCursorProducto(String id_categoria){
        return dbHelper.getReadableDatabase().rawQuery(
                "SELECT p.id_producto,p.nombre,p.descripcion,p.id_categoria FROM producto p , categoria c WHERE p.id_categoria = c.id_categoria and p.id_categoria =?", new String[]{id_categoria});
    }

    public boolean guardar_O_ActualizarCategoria(Categoria categoria){
        ContentValues initialValues = new ContentValues();
        if(!categoria.getId_categoria().isEmpty())
            initialValues.put("id_categoria", Integer.parseInt(categoria.getId_categoria()));
        initialValues.put("nombre",categoria.getNombre());
        int id = (int) dbHelper.getWritableDatabase().insertWithOnConflict(
                "categoria",
                null,
                initialValues,
                SQLiteDatabase.CONFLICT_REPLACE);
        return id>0;
    }

    public boolean guardar_O_ActualizarProducto(Producto producto){
        ContentValues initialvalues = new ContentValues();
        if(!producto.getId_producto().isEmpty())
            initialvalues.put("id_producto",Integer.parseInt(producto.getId_producto()));
            initialvalues.put("nombre",producto.getNombre());
            initialvalues.put("descripcion",producto.getDescripcion());
            initialvalues.put("id_categoria",Integer.parseInt(producto.getId_categoria()));
            int id = (int )dbHelper.getWritableDatabase().insertWithOnConflict(
                    "producto",
                    null,
                    initialvalues,
                    SQLiteDatabase.CONFLICT_REPLACE);
        return id>0;
    }

    public ArrayList<Categoria> getArrayCategoria(Cursor cursor){
        cursor.moveToFirst();//se mueve al principio
        ArrayList<Categoria> lstCategoria = new ArrayList<>();
        if(cursor!=null && cursor.getCount()>0){
            do{
                lstCategoria.add(new Categoria(
                        cursor.getString(0),//id
                        cursor.getString(1)//nombre
                ));
            }while(cursor.moveToNext());
            return lstCategoria;
        }
        return  null;
    }

    public ArrayList<Producto> getArrayProducto(Cursor cursor){
        cursor.moveToFirst();//moverse al principio
        ArrayList<Producto> lstProducto = new ArrayList<>();
        if(cursor != null && cursor.getCount() > 0){//si hay datos
            do{
                lstProducto.add(new Producto(
                        cursor.getString(0),//id
                        cursor.getString(1),//nombre
                        cursor.getString(2),//descripcion
                        cursor.getString(3)//id_categoria
                ));//se agregan a la lista
            }while (cursor.moveToNext());
            return lstProducto;
        }
        return null;//si no entro en el if
    }

    public void borrarCategoria(String id){
        dbHelper.getWritableDatabase().execSQL(String.format("DELETE FROM categoria WHERE id_categoria ='%s'",id));
    }
    public void borrarProducto(String id){
        dbHelper.getWritableDatabase().execSQL(String.format("DELETE FROM producto WHERE id_producto ='%s'",id));
    }
}
