package com.mydomain.cetreexpresv2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Objects;

//***********************************************************CLASE DONDE SE AGREGAN LAS DIRECCIONES
public class Home_Usuarios_Domicilios extends AppCompatActivity implements DireccionFragment.OnFragmentInteractionListener {

        //-------------------------------------------------Variables de la clase
    ArrayList<String> Nombre = new ArrayList<>();
    ArrayList<Integer> Id = new ArrayList<>();
    ArrayList<String> Avatar = new ArrayList<>();
    String nombre, cedula, Direccion, Mdir = "MI UBICACIÓN", Edir = "Dirección de Entrega";
    Double Latitud_Recibo = 0.0;
    Double Longitud_Recibo = 0.0;
    double latitudRecibo, longitudRecibo;
    DireccionFragment direccionFragment;
    int _ID, Actualizar = 0, tipo, boton, val = 0;
    boolean opc1=false,opc2=false;
    Button miUbicacion, miEntrega;
    Double MLat, MLong, ELat, ELong;


    //-------------------------------------------------Metodo onCreate
    @SuppressLint("CommitTransaction")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boton = 0;
        //Se salvan valores a utilizar
        saveLLDU();
        //Se declaran objetos de la pantalla de diseño
        setContentView(R.layout.activity_home__usuarios__domicilios);
        Id = Objects.requireNonNull(getIntent().getExtras()).getIntegerArrayList("AL_ID");
        Nombre = getIntent().getExtras().getStringArrayList("AL_DSC");
        Avatar = getIntent().getExtras().getStringArrayList("AL_Av");
        nombre = getIntent().getExtras().getString("NOMBRE");
        cedula = getIntent().getExtras().getString("CEDULA");
        miUbicacion = findViewById(R.id.MiUbicación);
        miEntrega = findViewById(R.id.miEntrega);
        direccionFragment = new DireccionFragment(Home_Usuarios_Domicilios.this);
        getSupportFragmentManager().beginTransaction().add(R.id.ContenedorFragment, direccionFragment);
        LocationManager locationManager = (LocationManager) Home_Usuarios_Domicilios.this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                //Se obtiene latitud y longitud
                latitudRecibo = location.getLatitude();
                longitudRecibo = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

    }
    //-------------------------------Metodo que salva los valores
    public void saveLLDU(){
        SharedPreferences prefs = Objects.requireNonNull(getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE));
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat("latitud", 0);
        editor.putFloat("longitud", 0);
        editor.putString("direccion", "Ingrese Dirección de entrega");
        editor.putInt("Actualizar", -1);
        editor.putInt("TIPO", boton);
        editor.apply();
    }
    //--------------------------------Metodo que lee los valores guardados anteriormente
    public void loadLLDU(){
        SharedPreferences prefs = getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
        Latitud_Recibo=(double)prefs.getFloat("latitud", -1);
        Longitud_Recibo= (double) prefs.getFloat("longitud", -1);
        Direccion=prefs.getString("direccion", "S");
        tipo=prefs.getInt("TIPO", -1);
        if(tipo == 1){
            MLat = Latitud_Recibo;
            MLong = Longitud_Recibo;
            Mdir = Direccion;
            if(!opc1){
                val=val+1;
            }
            opc1=true;
        }else if(tipo == 2){
            ELat = Latitud_Recibo;
            ELong = Longitud_Recibo;
            Edir = Direccion;
            if(!opc2){
                val=val+2;
            }
            opc2=true;
        }
        if(val==3){
            if(opc1){
                miUbicacion.setText(Mdir);
            }
           if(opc2){
               miEntrega.setText(Edir);
           }
        }else{
            if(opc1){
                miUbicacion.setText(Mdir);
            }else if(opc2){
                miEntrega.setText(Edir);
            }
        }

        Actualizar=prefs.getInt("Actualizar", -1);
    }

    //---------------------------------------------Al presionar algun boton de la pantalla
    public void confirmarDireccion(View v){
        switch (v.getId()){
            case R.id.ConfirmarBTN:
                if(!Edir.equals("Dirección de Entrega")){
                    try{
                        loadLLDU();
                        Intent i = new Intent(this,HomeUsuarioSeleccionar.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("ID", 1);
                        bundle.putIntegerArrayList("AL_ID", Id);
                        bundle.putStringArrayList("AL_DSC", Nombre);
                        bundle.putStringArrayList("AL_Av", Avatar);
                        bundle.putString("NOMBRE", nombre);
                        bundle.putString("CEDULA", cedula);
                        if(val == 2){
                            //utilizar direccion de recibo mi ubicación
                            bundle.putString("LaRECIBO", String.valueOf(latitudRecibo));
                            bundle.putString("LongRECIBO", String.valueOf(longitudRecibo));
                        }else{
                            bundle.putString("LaRECIBO",MLat.toString());
                            bundle.putString("LongRECIBO",MLong.toString());
                        }
                        bundle.putString("LaENTREGA",ELat.toString());
                        bundle.putString("LongENTREGA",ELong.toString());
                        i.putExtras(bundle);
                        startActivity(i);
                    }catch (NullPointerException e){
                        Toast.makeText(this, "Falta dirección de Recibo y/o Entrega", Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(this, "Falta dirección de Recibo y/o Entrega", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.MiCasa:
                break;
            case R.id.MiTrabajo:
                break;
            case R.id.DireccionFrecuente:
                break;
            case R.id.DireccionFrecuente1:
                break;
            case R.id.DireccionFrecuente2:
                break;
            case R.id.DireccionFrecuente3:
                break;
            case R.id.DireccionFrecuente4:
                break;
            case R.id.DireccionFrecuente5:
                break;
            case R.id.Regresar:
                onBackPressed();
                break;
            case R.id.ActualizarBTN:
                loadLLDU();
                break;
            case R.id.MiUbicación:
                boton=1;
                saveLLDU();
                DireccionFragment dialogU = new DireccionFragment(Home_Usuarios_Domicilios.this);
                dialogU.setCancelable(true);
                dialogU.show(getSupportFragmentManager(), "Mi Ubicación");
                break;
            case R.id.miEntrega:
                boton=2;
                saveLLDU();
                DireccionFragment dialogE = new DireccionFragment(Home_Usuarios_Domicilios.this);
                dialogE.setCancelable(true);
                dialogE.show(getSupportFragmentManager(), "Mi Entrega");
                break;
                default:
                    break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       Intent i = new Intent(this,HomeUsuario2.class);
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
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
