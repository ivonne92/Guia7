package com.guia7.guia7Ivonne;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.guia7.guia7Ivonne.Modelo.AdaptadorProducto;
import com.guia7.guia7Ivonne.Modelo.DB;
import com.guia7.guia7Ivonne.Modelo.Producto;

import java.util.ArrayList;

public class ListaProductos extends AppCompatActivity {
    private DB db;
    private AdaptadorProducto adaptadorProducto;
    private ListView listView;
    private TextView lblId_Cat,lblId_Prod;
    private EditText txtNombre_prod,txtdescripcion;
    private Button btnGuardar,btnEliminar;
    private static String IDCategoria ="";
    //lista de datos (Producto)
    private ArrayList<Producto> lstProducto;
    //sirve para manejar la eliminacion
    private Producto producto_temp=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);
        //Inicizalicacion de Los Controles
        lblId_Prod = findViewById(R.id.lblId_prod_main);
        txtNombre_prod = findViewById(R.id.txtproducto);
        lblId_Cat = findViewById(R.id.lblID_categoria);
        txtdescripcion = findViewById(R.id.txtdescripcion);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnEliminar = findViewById(R.id.btnEliminar);
        listView = findViewById(R.id.lstProducto);

        //inicializacion de la lista y la base de datos
        IDCategoria = getIntent().getExtras().getString("Categoria");
        lblId_Cat.setText(IDCategoria);
        db= new DB(this);
        lstProducto = db.getArrayProducto(db.getCursorProducto(IDCategoria));
        //adaptadorProducto = new AdaptadorProducto(this,lstProducto);
        if(lstProducto==null)//Si no hay Datos
            lstProducto = new ArrayList<>();
            adaptadorProducto = new AdaptadorProducto(this,lstProducto);
            listView.setAdapter(adaptadorProducto);
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
                seleccionar(lstProducto.get(position));
            }
        });
        limpiar();
    }

    private void guardar() {
        Producto producto = new Producto(lblId_Prod.getText().toString(),
                txtNombre_prod.getText().toString(),
                txtdescripcion.getText().toString(),
                lblId_Cat.getText().toString());
        producto_temp = null;
        if(db.guardar_O_ActualizarProducto(producto)){
            Toast.makeText(this,"Se guardo Con Exito El Producto: " +producto.getNombre(),Toast.LENGTH_SHORT).show();
            //Todo lismpiar los que existen y agregar los nuevos
            lstProducto.clear();
            lstProducto.addAll(db.getArrayProducto(db.getCursorProducto(IDCategoria)));
            adaptadorProducto.notifyDataSetChanged();
            limpiar();
        }else{
            Toast.makeText(this,"Ocurrio un error al guardar",Toast.LENGTH_SHORT).show();
        }
    }

    private  void eliminar(){
        if(producto_temp!=null){
            db.borrarProducto(producto_temp.getId_producto());
            lstProducto.remove(producto_temp);
            adaptadorProducto.notifyDataSetChanged();
            producto_temp=null;
            Toast.makeText(this,"Se elimino El Producto: "+producto_temp.getNombre(),Toast.LENGTH_SHORT).show();
            limpiar();
        }
    }
    private void seleccionar(Producto producto){
        producto_temp = producto;
        lblId_Prod.setText(producto_temp.getId_producto());
        txtNombre_prod.setText(producto_temp.getNombre());
        txtdescripcion.setText(producto_temp.getDescripcion());
    }

    private void limpiar() {
        lblId_Prod.setText(null);
        txtNombre_prod.setText(null);
        txtdescripcion.setText(null);
    }
}
