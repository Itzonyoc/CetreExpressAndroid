package com.mydomain.cetreexpresv2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;
import java.util.Objects;
//******************************************************************CLASE DONDE SE SELECCIONA EL SERIVICO A PEDIR
public class HomeUsuarioSeleccionar extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    //-------------------------------------------------Variables de la clase
    ArrayList<String> Nombre = new ArrayList<>();
    ArrayList<Integer> Id = new ArrayList<>();
    ArrayList<String> Avatar = new ArrayList<>();
    String nombre,cedula;
    private MapView mapView;
    private GoogleMap gmap;
    private static final String MAP_VIEW_BUNDLE_KEY = "AIzaSyC4znjtsuSN9zI24KbDDbzZi4-Q3xqGF_s";//Key de google maps
    String LatR,LatE,LongR,LongE;

    //----------------------------------------------------Metodo onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_usuario_seleccionar);
        //Se declaran los objetos de la pantalla de diseño
        Id= Objects.requireNonNull(getIntent().getExtras()).getIntegerArrayList("AL_ID");
        Nombre=getIntent().getExtras().getStringArrayList("AL_DSC");
        Avatar=getIntent().getExtras().getStringArrayList("AL_Av");
        nombre=getIntent().getExtras().getString("NOMBRE");
        cedula=getIntent().getExtras().getString("CEDULA");
        LatR=getIntent().getExtras().getString("LaRECIBO");
        LatE=getIntent().getExtras().getString("LaENTREGA");
        LongR=getIntent().getExtras().getString("LongRECIBO");
        LongE=getIntent().getExtras().getString("LongENTREGA");
        //Se revisan los permisos necesarios
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
        //Se inicializa el mapa
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        //Crea el mapa de google
        mapView = findViewById(R.id.mapViewMenu);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
    }

    //-----------------------------------Al dar click en alguna opción manda los datos necesarios de
    // esa opción e inicia la pantalla de confirmar con los datos que se envian
    public void option(View view){

        Intent i;
        int size = Nombre.size();

        switch (view.getId()){

            case R.id.homeBtn:
                i = new Intent(this,Home_Usuario_Confirmar.class);
                Bundle bundle3 = new Bundle();
                for(int x=0;x<size;x++){
                    if(Nombre.get(x).equals("Home")){
                        bundle3.putString("ARTICULO", Nombre.get(x));
                        bundle3.putInt("_ID", Id.get(x));
                    }
                }
                bundle3.putInt("Boton", 3);
                bundle3.putIntegerArrayList("AL_ID", Id);
                bundle3.putStringArrayList("AL_DSC", Nombre);
                bundle3.putStringArrayList("AL_Av", Avatar);
                bundle3.putString("LaRECIBO",LatR);
                bundle3.putString("LongRECIBO",LongR);
                bundle3.putString("LaENTREGA",LatE);
                bundle3.putString("LongENTREGA",LongE);
                i.putExtras(bundle3);
                startActivity(i);
                break;
            case R.id.cetreGoBtn:
                i = new Intent(this,Home_Usuario_Confirmar.class);
                Bundle bundle1 = new Bundle();
                for(int x=0;x<size;x++){
                    if(Nombre.get(x).equals("Go")){
                        bundle1.putString("ARTICULO", Nombre.get(x));
                        bundle1.putInt("_ID", Id.get(x));
                    }
                }
                bundle1.putInt("Boton", 1);
                bundle1.putIntegerArrayList("AL_ID", Id);
                bundle1.putStringArrayList("AL_DSC", Nombre);
                bundle1.putStringArrayList("AL_Av", Avatar);
                bundle1.putString("LaRECIBO",LatR);
                bundle1.putString("LongRECIBO",LongR);
                bundle1.putString("LaENTREGA",LatE);
                bundle1.putString("LongENTREGA",LongE);
                i.putExtras(bundle1);
                startActivity(i);
                break;
            case R.id.shopBtn:
                i = new Intent(this,Home_Usuario_Confirmar.class);
                Bundle bundle4 = new Bundle();
                for(int x=0;x<size;x++){
                    if(Nombre.get(x).equals("Shop")){
                        bundle4.putString("ARTICULO", Nombre.get(x));
                        bundle4.putInt("_ID", Id.get(x));
                    }
                }
                bundle4.putInt("Boton", 4);
                bundle4.putIntegerArrayList("AL_ID", Id);
                bundle4.putStringArrayList("AL_DSC", Nombre);
                bundle4.putStringArrayList("AL_Av", Avatar);
                bundle4.putString("LaRECIBO",LatR);
                bundle4.putString("LongRECIBO",LongR);
                bundle4.putString("LaENTREGA",LatE);
                bundle4.putString("LongENTREGA",LongE);
                i.putExtras(bundle4);
                startActivity(i);
                break;
            case R.id.gestionBtn:
                i = new Intent(this,Home_Usuario_Confirmar.class);
                Bundle bundle2 = new Bundle();
                for(int x=0;x<size;x++){

                    if(Nombre.get(x).equals("Gestión")){
                        bundle2.putString("ARTICULO", Nombre.get(x));
                        bundle2.putInt("_ID", Id.get(x));
                    }
                }
                bundle2.putInt("Boton", 2);
                bundle2.putIntegerArrayList("AL_ID", Id);
                bundle2.putStringArrayList("AL_DSC", Nombre);
                bundle2.putStringArrayList("AL_Av", Avatar);
                bundle2.putString("LaRECIBO",LatR);
                bundle2.putString("LongRECIBO",LongR);
                bundle2.putString("LaENTREGA",LatE);
                bundle2.putString("LongENTREGA",LongE);
                i.putExtras(bundle2);
                startActivity(i);
                break;
            case R.id.Regresar:
                onBackPressed();
                        break;
                default:
                    break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this,Home_Usuarios_Domicilios.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ID", 1);
        bundle.putIntegerArrayList("AL_ID", Id);
        bundle.putStringArrayList("AL_DSC", Nombre);
        bundle.putStringArrayList("AL_Av", Avatar);
        bundle.putString("NOMBRE", nombre);
        bundle.putString("CEDULA", cedula);
        i.putExtras(bundle);
        startActivity(i);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() { super.onResume();mapView.onResume(); }

    @Override
    protected void onStart() { super.onStart();mapView.onStart(); }

    @Override
    protected void onStop() { super.onStop();mapView.onStop(); }

    @Override
    protected void onPause() { mapView.onPause();super.onPause(); }

    @Override
    protected void onDestroy() { mapView.onDestroy();super.onDestroy(); }

    @Override
    public void onLowMemory() { super.onLowMemory();mapView.onLowMemory(); }



    @Override
    public void onMapReady(GoogleMap googleMap) {//Cuando se inicia el mapa se setean las direcciones de entrega y recibo en el mapa

        Double LatitudEntrega = Double.valueOf(LatE);
        Double LatitudRecibo = Double.valueOf(LatR);
        Double LongitudEntrega = Double.valueOf(LongE);
        Double LongitudRecibo = Double.valueOf(LongR);

        gmap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        gmap.setMyLocationEnabled(true);
        gmap.setMinZoomPreference(12);
        LatLng R = new LatLng(LatitudRecibo, LongitudRecibo);
        gmap.addMarker(new MarkerOptions().position(R).title("Recibo"));
        gmap.animateCamera(CameraUpdateFactory.newLatLng(R));
        LatLng E = new LatLng(LatitudEntrega, LongitudEntrega);
        gmap.addMarker(new MarkerOptions().position(E).title("Entrega"));
        gmap.animateCamera(CameraUpdateFactory.newLatLng(E));
    }
}
