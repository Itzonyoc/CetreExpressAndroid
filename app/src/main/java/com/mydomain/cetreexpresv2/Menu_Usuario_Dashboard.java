package com.mydomain.cetreexpresv2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class Menu_Usuario_Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    int _ID;
    ArrayList<String> Nombre = new ArrayList<>();
    ArrayList<Integer> Id = new ArrayList<>();
    ArrayList<String> Avatar = new ArrayList<>();
    Button userBtn;
    TextView nombreDrawer,cedulaOcorreoDrawer;
    ImageView imagenPerfil;
    String nombre,cedula;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__usuario__dashboard);
        userBtn = findViewById(R.id.UserButton);
        imagenPerfil=findViewById(R.id.fotoUsuarioDrawer);

        Id= Objects.requireNonNull(getIntent().getExtras()).getIntegerArrayList("AL_ID");
        Nombre=getIntent().getExtras().getStringArrayList("AL_DSC");
        Avatar=getIntent().getExtras().getStringArrayList("AL_Av");
        nombre=getIntent().getExtras().getString("NOMBRE");
        cedula=getIntent().getExtras().getString("CEDULA");
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        cedulaOcorreoDrawer=headerView.findViewById(R.id.CedulaOCorreoDrawer);
        nombreDrawer=headerView.findViewById(R.id.usuarioDrawer);
        nombreDrawer.setText(nombre);
        cedulaOcorreoDrawer.setText(cedula);
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(Menu_Usuario_Dashboard.this);
            builder.setMessage("¿Estas seguro de cerrar sesión?")
                    .setPositiveButton("Cerrar Sesión", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(Menu_Usuario_Dashboard.this,LoginUsuarioActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("ID", 1);
                            i.putExtras(bundle);
                            i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            startActivity(i);
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Toast.makeText(Menu_Usuario_Dashboard.this,"cencelado",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setCancelable(false);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
    public void pressButton(View v){
        Intent i;
        int size = Nombre.size();

        switch (v.getId()){
            case R.id.CetreGoButton:
                i = new Intent(this,Cetre_Go_Usuario.class);
                Bundle bundle1 = new Bundle();
                for(int x=0;x<size;x++){
                    if(Nombre.get(x).equals("Go")){
                        bundle1.putInt("_ID", Id.get(x));
                    }
                }
                bundle1.putInt("Boton", 1);
                bundle1.putIntegerArrayList("AL_ID", Id);
                bundle1.putStringArrayList("AL_DSC", Nombre);
                bundle1.putStringArrayList("AL_Av", Avatar);
                i.putExtras(bundle1);
                startActivity(i);
                break;
            case R.id.GestionButton:
                i = new Intent(this,Cetre_Go_Usuario.class);
                Bundle bundle2 = new Bundle();
                for(int x=0;x<size;x++){
                    if(Nombre.get(x).equals("Gestión")){
                        bundle2.putInt("_ID", Id.get(x));
                    }
                }
                bundle2.putInt("Boton", 2);
                bundle2.putIntegerArrayList("AL_ID", Id);
                bundle2.putStringArrayList("AL_DSC", Nombre);
                bundle2.putStringArrayList("AL_Av", Avatar);
                i.putExtras(bundle2);
                startActivity(i);
                break;
            case R.id.HomeButton:
                i = new Intent(this,Cetre_Go_Usuario.class);
                Bundle bundle3 = new Bundle();
                for(int x=0;x<size;x++){
                    if(Nombre.get(x).equals("Home")){
                        bundle3.putInt("_ID", Id.get(x));
                    }
                }
                bundle3.putInt("Boton", 3);
                bundle3.putIntegerArrayList("AL_ID", Id);
                bundle3.putStringArrayList("AL_DSC", Nombre);
                bundle3.putStringArrayList("AL_Av", Avatar);
                i.putExtras(bundle3);
                startActivity(i);
                break;
            case R.id.ShopButton:
                i = new Intent(this,Cetre_Go_Usuario.class);
                Bundle bundle4 = new Bundle();
                for(int x=0;x<size;x++){
                    if(Nombre.get(x).equals("Shop")){
                        bundle4.putInt("_ID", Id.get(x));
                    }
                }
                bundle4.putInt("Boton", 4);
                bundle4.putIntegerArrayList("AL_ID", Id);
                bundle4.putStringArrayList("AL_DSC", Nombre);
                bundle4.putStringArrayList("AL_Av", Avatar);
                i.putExtras(bundle4);
                startActivity(i);
                break;
            case R.id.UbicacionActual:
                break;
            case R.id.UserButton:
                final DrawerLayout drawer =  findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
                break;
            case R.id.MisPedidosButton:
                i = new Intent(this,BuscadorPedidos.class);
                Bundle bundle5 = new Bundle();
                bundle5.putInt("Boton", 4);
                bundle5.putIntegerArrayList("AL_ID", Id);
                bundle5.putStringArrayList("AL_DSC", Nombre);
                bundle5.putStringArrayList("AL_Av", Avatar);
                i.putExtras(bundle5);
                startActivity(i);
                break;
                default:
                    break;
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        if (id == R.id.dat_personales) {
            // Handle the camera action
        } else if (id == R.id.met_pago) {

        } else if (id == R.id.mis_domicilios) {

        } else if (id == R.id.pedidos) {
            Intent i = new Intent(this,BuscadorPedidos.class);
            Bundle bundle5 = new Bundle();
            bundle5.putInt("Boton", 4);
            bundle5.putIntegerArrayList("AL_ID", Id);
            bundle5.putStringArrayList("AL_DSC", Nombre);
            bundle5.putStringArrayList("AL_Av", Avatar);
            i.putExtras(bundle5);
            startActivity(i);
        } else if (id == R.id.contacto) {

        } else if (id == R.id.cerrar_sesion) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(Menu_Usuario_Dashboard.this);
            builder.setMessage("¿Estas seguro de cerrar sesión?")
                    .setPositiveButton("Cerrar Sesión", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(Menu_Usuario_Dashboard.this,LoginUsuarioActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("ID", 1);
                            i.putExtras(bundle);
                            i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            startActivity(i);
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Toast.makeText(Menu_Usuario_Dashboard.this,"cencelado",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setCancelable(false);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
