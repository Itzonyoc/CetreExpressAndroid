package com.mydomain.cetreexpresv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Objects;

public class BuscadorPedidos extends AppCompatActivity {

    Spinner spinner;
    ArrayList<String> Nombre = new ArrayList<>();
    ArrayList<Integer> Id = new ArrayList<>();
    ArrayList<String> Avatar = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador_pedidos);
        Id= Objects.requireNonNull(getIntent().getExtras()).getIntegerArrayList("AL_ID");
        Nombre=getIntent().getExtras().getStringArrayList("AL_DSC");
        Avatar=getIntent().getExtras().getStringArrayList("AL_Av");
        spinner = findViewById(R.id.spinnerSeleccionarCategoria);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(BuscadorPedidos.this,android.
                R.layout.simple_list_item_1,getResources().getStringArray(R.array.Options));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
