package com.guia7.guia7Ivonne;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.guia7.guia7Ivonne.Modelo.AdaptadorCategoria;
import com.guia7.guia7Ivonne.Modelo.Categoria;
import com.guia7.guia7Ivonne.Modelo.DB;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DB db;
    private AdaptadorCategoria adaptadorCategoria;
    private ListView listView;
    private TextView lblId_Cat;
    private EditText txtNombre_Cat;
    private Button btnGuardar,btnEliminar,btnAcceder;
    //lista de datos (categoria)
    private ArrayList<Categoria> lstCategoria;
    //sirve para manejar la eliminacion
    private Categoria categoria_temp=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicizalicacion de Los Controles
        lblId_Cat = findViewById(R.id.lblId_cat_main);
        txtNombre_Cat = findViewById(R.id.txtCategoria);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnAcceder = findViewById(R.id.btnacceder);
        listView = findViewById(R.id.lstCategoria);
        //inicializacion de la lista y la base de datos
        db= new DB(this);
        lstCategoria = db.getArrayCategoria(db.getCursorCategoria());
        if(lstCategoria==null)//Si no hay Datos
            lstCategoria = new ArrayList<>();
            adaptadorCategoria = new AdaptadorCategoria(this,lstCategoria);
            listView.setAdapter(adaptadorCategoria);
        //Listener
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                seleccionar(lstCategoria.get(position));
            }
        });
        btnAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtNombre_Cat.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Seleccione una Categoria Por Favor: ",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(MainActivity.this,ListaProductos.class);
                    intent.putExtra("Categoria",lblId_Cat.getText().toString());
                    startActivity(intent);
                }
            }
        });
        //limpiando
        limpiar();
    }

    private void guardar() {
        Categoria categoria = new Categoria(lblId_Cat.getText().toString(),txtNombre_Cat.getText().toString());
        categoria_temp = null;
        if(db.guardar_O_ActualizarCategoria(categoria)){
            Toast.makeText(this,"Se guardo Con Exito la Categoria: " +categoria.getNombre(),Toast.LENGTH_SHORT).show();
            //Todo lismpiar los que existen y agregar los nuevos
            lstCategoria.clear();
            lstCategoria.addAll(db.getArrayCategoria(db.getCursorCategoria()));
            adaptadorCategoria.notifyDataSetChanged();
            limpiar();
        }else{
            Toast.makeText(this,"Ocurrio un error al guardar",Toast.LENGTH_SHORT).show();
        }
    }

    private  void eliminar(){
        if(categoria_temp!=null){
            db.borrarCategoria(categoria_temp.getId_categoria());
            lstCategoria.remove(categoria_temp);
            adaptadorCategoria.notifyDataSetChanged();
            categoria_temp=null;
            Toast.makeText(this,"Se elimino categoria: "+categoria_temp.getNombre(),Toast.LENGTH_SHORT).show();
            limpiar();
        }
    }
    private void seleccionar(Categoria categoria){
        categoria_temp = categoria;
        lblId_Cat.setText(categoria_temp.getId_categoria());
        txtNombre_Cat.setText(categoria_temp.getNombre());
    }

    private void limpiar() {
        lblId_Cat.setText(null);
        txtNombre_Cat.setText(null);
    }
}
