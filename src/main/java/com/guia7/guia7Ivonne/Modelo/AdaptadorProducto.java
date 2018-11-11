package com.guia7.guia7Ivonne.Modelo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.guia7.guia7Ivonne.R;

import java.util.List;

public class AdaptadorProducto extends ArrayAdapter<Producto>{
    public AdaptadorProducto(Context context, List<Producto> objects){
        super(context,0,objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Producto producto = getItem(position);
        if(convertView ==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_producto,parent,false);
        }
        TextView lblNombre = convertView.findViewById(R.id.lblNombre_prod);
        TextView lblId_pro = convertView.findViewById(R.id.lblId_prod);
        TextView lbldescripcion = convertView.findViewById(R.id.lbldescripcion);
        lblNombre.setText(producto.getNombre());
        lblId_pro.setText(producto.getId_producto());
        lbldescripcion.setText(producto.getDescripcion());
        return convertView;
    }
}
