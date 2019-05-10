package com.mydomain.cetreexpresv2;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.Objects;
//***************************************************************************NUEVA PANTALLA**********************************************

//Pantalla que remplaza al menu anterior
//Nueva pantalla donde se elige el servicio que se requiere

//***************************************************************************NUEVA PANTALLA**********************************************

public class HomeUsuario2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, com.google.android.gms.location.LocationListener {

    //--------------------------------------------------Variables de la clase
    private MapView mapView;
    private GoogleMap gmap;
    private static final String MAP_VIEW_BUNDLE_KEY = "AIzaSyC4znjtsuSN9zI24KbDDbzZi4-Q3xqGF_s";
    ArrayList<String> Nombre = new ArrayList<>();
    ArrayList<Integer> Id = new ArrayList<>();
    ArrayList<String> Avatar = new ArrayList<>();
    String nombre, cedula;
    LatLng E;
    GoogleApiClient _googleApiClient;
    Location _Location;
    LocationRequest mLocationRequest;
    TextView nombreDrawer,cedulaOcorreoDrawer;
    ActionBarDrawerToggle mDrawerToggle;

    //-------------------------------------------------------Metodo onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Se declaran los objetos a utlizar
        setContentView(R.layout.activity_home_usuario2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //Se revisan los permisos de la aplicación
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
        Id = Objects.requireNonNull(getIntent().getExtras()).getIntegerArrayList("AL_ID");
        Nombre = getIntent().getExtras().getStringArrayList("AL_DSC");
        Avatar = getIntent().getExtras().getStringArrayList("AL_Av");
        nombre = getIntent().getExtras().getString("NOMBRE");
        cedula = getIntent().getExtras().getString("CEDULA");
        setSupportActionBar(toolbar);
        mapView = findViewById(R.id.mapView2);
        //Se declara el menu lateral
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //Se inicializa el mapa
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
        View headerView = navigationView.getHeaderView(0);
        cedulaOcorreoDrawer=headerView.findViewById(R.id.CedulaOCorreoDrawer);
        nombreDrawer=headerView.findViewById(R.id.usuarioDrawer);
        nombreDrawer.setText(nombre);
        cedulaOcorreoDrawer.setText(cedula);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        //Se declara el boton de cuando se presiona abre el menu lateral
        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        mDrawerToggle.setHomeAsUpIndicator(R.drawable.menu_user_icon);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_usuario2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    //---------------------------------------------------------------------Botones del menu lateral
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.dat_personales) {
            // Handle the camera action
        } else if (id == R.id.met_pago) {

        } else if (id == R.id.mis_domicilios) {

        } else if (id == R.id.pedidos) {
            Intent i = new Intent(this, BuscadorPedidos.class);
            Bundle bundle5 = new Bundle();
            bundle5.putInt("Boton", 4);
            bundle5.putIntegerArrayList("AL_ID", Id);
            bundle5.putStringArrayList("AL_DSC", Nombre);
            bundle5.putStringArrayList("AL_Av", Avatar);
            i.putExtras(bundle5);
            startActivity(i);
        } else if (id == R.id.contacto) {

        } else if (id == R.id.cerrar_sesion) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(HomeUsuario2.this);
            builder.setMessage("¿Estas seguro de cerrar sesión?")
                    .setPositiveButton("Cerrar Sesión", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(HomeUsuario2.this, LoginUsuarioActivity.class);
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

                            Toast.makeText(HomeUsuario2.this, "cencelado", Toast.LENGTH_SHORT).show();
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
    //--------------------------------------------------------------------Al presionar boton atras
    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(HomeUsuario2.this);
            builder.setMessage("¿Estas seguro de cerrar sesión?")
                    .setPositiveButton("Cerrar Sesión", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(HomeUsuario2.this,LoginUsuarioActivity.class);
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

                            Toast.makeText(HomeUsuario2.this,"cencelado",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setCancelable(false);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
    //-------------------------------------------------------Al presionar alguno de los servicios
    public void pressButton(View view) {
        Intent i;

        switch (view.getId()){
            case R.id.AdondeVamosBTN:
                i = new Intent(this,Home_Usuarios_Domicilios.class);
                Bundle bundle = new Bundle();
                bundle.putInt("ID", 1);//Cada servicio tiene un ID diferente
                bundle.putIntegerArrayList("AL_ID", Id);
                bundle.putStringArrayList("AL_DSC", Nombre);
                bundle.putStringArrayList("AL_Av", Avatar);
                bundle.putString("NOMBRE", nombre);
                bundle.putString("CEDULA", cedula);
                i.putExtras(bundle);
                startActivity(i);
                break;
                case R.id.misPedidosButton2:
                i = new Intent(this,BuscadorPedidos.class);
                Bundle bundle5 = new Bundle();
                bundle5.putInt("Boton", 4);
                bundle5.putIntegerArrayList("AL_ID", Id);
                bundle5.putStringArrayList("AL_DSC", Nombre);
                bundle5.putStringArrayList("AL_Av", Avatar);
                bundle5.putString("NOMBRE", nombre);
                bundle5.putString("CEDULA", cedula);
                i.putExtras(bundle5);
                startActivity(i);
                    break;

                default:
                    break;
        }
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
    public void onMapReady(GoogleMap googleMap) {

        gmap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        buildGoogleApiClient();
        gmap.setMyLocationEnabled(true);
        gmap.setMinZoomPreference(17);
    }

    protected synchronized  void buildGoogleApiClient(){
        _googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        _googleApiClient.connect();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) { }

    @Override
    public void onLocationChanged(Location location) {
        _Location = location;
        E = new LatLng(_Location.getLatitude(), _Location.getLongitude());
        gmap.moveCamera(CameraUpdateFactory.newLatLng(E));
        gmap.animateCamera(CameraUpdateFactory.newLatLng(E));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) { }

    @Override
    public void onProviderEnabled(String provider) { }

    @Override
    public void onProviderDisabled(String provider) { }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(_googleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) { }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) { }
}
