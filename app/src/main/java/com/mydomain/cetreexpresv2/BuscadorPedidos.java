package com.mydomain.cetreexpresv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.Objects;
//**************************************************************CLASE QUE BUSCA LOS PEDIDOS EN CURSO
public class BuscadorPedidos extends AppCompatActivity {
    //---------------------------------------------Variables de la clase
    Spinner spinner;
    ArrayList<String> Nombre = new ArrayList<>();
    ArrayList<Integer> Id = new ArrayList<>();
    ArrayList<String> Avatar = new ArrayList<>();
    String nombre, cedula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //------------------------------------------Se declaran los elementos a utilizar en la clase
        setContentView(R.layout.activity_buscador_pedidos);
        Id= Objects.requireNonNull(getIntent().getExtras()).getIntegerArrayList("AL_ID");
        Nombre=getIntent().getExtras().getStringArrayList("AL_DSC");
        Avatar=getIntent().getExtras().getStringArrayList("AL_Av");
        nombre = getIntent().getExtras().getString("NOMBRE");
        cedula = getIntent().getExtras().getString("CEDULA");
        spinner = findViewById(R.id.spinnerSeleccionarCategoria);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(BuscadorPedidos.this,android.
                R.layout.simple_list_item_1,getResources().getStringArray(R.array.Options));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }

    //----------------------------------------------------------------------------------Boton atrás
    @Override
    public void onBackPressed() {
        super.onBackPressed();
       Intent i = new Intent(this,HomeUsuario2.class);
        Bundle bundle5 = new Bundle();
        bundle5.putInt("Boton", 4);
        bundle5.putIntegerArrayList("AL_ID", Id);
        bundle5.putStringArrayList("AL_DSC", Nombre);
        bundle5.putStringArrayList("AL_Av", Avatar);
        bundle5.putString("NOMBRE", nombre);
        bundle5.putString("CEDULA", cedula);
        i.putExtras(bundle5);
        startActivity(i);
    }
}
