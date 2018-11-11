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

public class AdaptadorCategoria extends ArrayAdapter<Categoria>{
    public AdaptadorCategoria(Context context, List<Categoria> objects){
        super(context,0,objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Categoria categoria = getItem(position);
        if(convertView ==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_categoria,parent,false);
        }
        TextView lblNombre = convertView.findViewById(R.id.lblNombre_cat);
        TextView lblId_Cat = convertView.findViewById(R.id.lblId_cat);

        lblNombre.setText(categoria.getNombre());
        lblId_Cat.setText(categoria.getId_categoria());
        return convertView;
    }
}
