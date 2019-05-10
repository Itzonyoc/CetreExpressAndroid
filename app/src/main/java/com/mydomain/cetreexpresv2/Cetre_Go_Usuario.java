package com.mydomain.cetreexpresv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import java.util.Objects;


//************************************************************CLASE SIN USO******************************************************************


//Se descartó esta pantalla por parte del comprador.
//Esta clase mostraba la lista de elementos de cada categoria.
//Mnadaba a llamar al servicio que recibía los datos de cada categoria y los mostraba


//************************************************************CLASE SIN USO******************************************************************



public class Cetre_Go_Usuario extends AppCompatActivity {

    RecyclerView rv;
    RecyclerView.Adapter adapter;
    String cabecera,subcategoria;
    ArrayList<Elemento> lista;
    private ArrayList<Elemento> elementos;
    int _ID;
    int boton=0;
    ArrayList<String> Nombre = new ArrayList<>();
    ArrayList<Integer> Id = new ArrayList<>();
    ArrayList<String> Avatar = new ArrayList<>();
    String LatR,LatE,LongR,LongE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cetre__go__usuario);
        rv=findViewById(R.id.RecyclerView_catalogo);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        _ID= Objects.requireNonNull(getIntent().getExtras()).getInt("_ID");
        boton = Objects.requireNonNull(getIntent().getExtras()).getInt("Boton");
        Id= Objects.requireNonNull(getIntent().getExtras()).getIntegerArrayList("AL_ID");
        Nombre=getIntent().getExtras().getStringArrayList("AL_DSC");
        Avatar=getIntent().getExtras().getStringArrayList("AL_Av");
        LatR=getIntent().getExtras().getString("LaRECIBO");
        LatE=getIntent().getExtras().getString("LaENTREGA");
        LongR=getIntent().getExtras().getString("LongRECIBO");
        LongE=getIntent().getExtras().getString("LongENTREGA");
        switch (boton){
            case 1:
                cabecera="CETRE GO";
                listar_catalogo();
                subcategoria="Go";
                break;
            case 2:
                cabecera="GESTION";
                listar_catalogo();
                subcategoria="Gestión";
                break;
            case 3:
                cabecera="HOME";
                listar_catalogo();
                subcategoria="Home";
                break;
            case 4:
                cabecera="SHOP";
                listar_catalogo();
                subcategoria="Shop";
                break;
                default:
                    break;
        }
        Button btn = findViewById(R.id.BackButton);
        btn.setText(cabecera);


    }
    public void listar_catalogo(){
        SubcategoriaService subcategoriaService = new SubcategoriaService(Cetre_Go_Usuario.this,_ID,rv,getApplication(),adapter,Id,Nombre,Avatar,LatR,LatE,LongR,LongE);
        subcategoriaService.execute();
    }

    public void onPressedBackButton(View view){
        if(view.getId()==R.id.BackButton){
            onBackPressed();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Cetre_Go_Usuario.this,HomeUsuario2.class);
        Bundle bundle = new Bundle();
        int size = Nombre.size();
        for(int x=0;x<size;x++){
            if(Nombre.get(x).equals("Shop")){
                bundle.putInt("_ID", Id.get(x));
            }
        }
        bundle.putInt("Boton", 4);
        bundle.putIntegerArrayList("AL_ID", Id);
        bundle.putStringArrayList("AL_DSC", Nombre);
        bundle.putStringArrayList("AL_Av", Avatar);
        i.putExtras(bundle);
        startActivity(i);
    }
}
