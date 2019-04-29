package com.mydomain.cetreexpresv2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Home_Usuario_Confirmar extends AppCompatActivity implements DireccionFragment.OnFragmentInteractionListener{
    int _ID,Actualizar=0;
    int categoriaID=0;
    String cabecera,Nombre,Precio,KM,KG,Hasta,direccion,Hora,detalles;
    TextView compra,precioCompra,PDS,KMA,Total,DireccionEntrega;
    Double latitudRecibo,longitudRecibo,latitudEntrega,longitudEntrega;
    DireccionFragment direccionFragment;
    CheckBox checkBox;
    EditText ETdetalles;
    ArrayList<String> ANombre = new ArrayList<>();
    ArrayList<Integer> AId = new ArrayList<>();
    ArrayList<String> AAvatar = new ArrayList<>();
    String LatR,LatE,LongR,LongE;
    String address;
    int validacion=0;

    @SuppressLint("CommitTransaction")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__usuario__confirmar);
        saveLLDU();
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck==PackageManager.PERMISSION_DENIED){
            if( ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){

            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
        AId= Objects.requireNonNull(getIntent().getExtras()).getIntegerArrayList("AL_ID");
        ANombre=getIntent().getExtras().getStringArrayList("AL_DSC");
        AAvatar=getIntent().getExtras().getStringArrayList("AL_Av");
        LatR=getIntent().getExtras().getString("LaRECIBO");
        LatE=getIntent().getExtras().getString("LaENTREGA");
        LongR=getIntent().getExtras().getString("LongRECIBO");
        LongE=getIntent().getExtras().getString("LongENTREGA");
        DireccionEntrega=findViewById(R.id.TV_DireccionEntrega);
        Geocoder geocoder;
        List<Address> direccion;
        geocoder = new Geocoder(this, Locale.getDefault());

        double lat = Double.parseDouble(LatE);
        double longi = Double.parseDouble(LongE);

        try {
            direccion = geocoder.getFromLocation(lat, longi, 1); // 1 representa la cantidad de resultados a obtener
            address = direccion.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = direccion.get(0).getLocality();
            String state = direccion.get(0).getAdminArea();
            String country = direccion.get(0).getCountryName();
            String postalCode = direccion.get(0).getPostalCode();
            DireccionEntrega.setText(address);
            validacion = 1;

        } catch (IOException e) {
            e.printStackTrace();
        }

        direccionFragment=new DireccionFragment(Home_Usuario_Confirmar.this);
        getSupportFragmentManager().beginTransaction().add(R.id.ContenedorFragment,direccionFragment);
        LocationManager locationManager = (LocationManager)Home_Usuario_Confirmar.this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
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
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        categoriaID= Objects.requireNonNull(getIntent().getExtras()).getInt("CategoriaID");
        _ID=getIntent().getExtras().getInt("ID");
        Nombre=getIntent().getExtras().getString("Nombre");
        Precio=getIntent().getExtras().getString("Precio");
        KM=getIntent().getExtras().getString("porKM");
        KG=getIntent().getExtras().getString("porKG");
        Hasta=getIntent().getExtras().getString("Hasta");
        compra=findViewById(R.id.Tv_Compra);
        precioCompra=findViewById(R.id.Tv_Precio);
        PDS=findViewById(R.id.PrecioServicio);
        checkBox=findViewById(R.id.checkBox);
        ETdetalles=findViewById(R.id.ET_detalle);
        KMA=findViewById(R.id.PrecioKMAdicionales);
        Total=findViewById(R.id.PrecioTotal);
        switch (categoriaID) {
            case 2:
                cabecera = "Go";
                break;
            case 1:
                cabecera = "Gestión";
                break;
            case 3:
                cabecera = "Home";
                break;
            case 4:
                cabecera = "Shop";
                break;
            default:
                break;
        }
        String compraText = cabecera + " - " + Nombre;
        compra.setText(compraText);
        String PrecioCompraText = "$"+Precio;
        precioCompra.setText(PrecioCompraText);
    }
    public void saveLLDU(){
        SharedPreferences prefs = Objects.requireNonNull(getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE));
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat("latitud", 0);
        editor.putFloat("longitud", 0);
        editor.putString("direccion", "Ingrese Dirección de entrega");
        editor.putInt("Actualizar", -1);
        editor.apply();
    }
    public void loadLLDU(){
        SharedPreferences prefs = getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
        latitudEntrega=(double)prefs.getFloat("latitud", -1);
        longitudEntrega= (double) prefs.getFloat("longitud", -1);
        direccion=prefs.getString("direccion", "S");
        DireccionEntrega.setText(direccion);
        Actualizar=prefs.getInt("Actualizar", -1);
    }
    public void BotonConfirmar(View view){
        switch (view.getId()){

            case R.id.Boton_Confirmar:
                if(DireccionEntrega.getText().equals("Ingrese Dirección de entrega")){
                    Toast.makeText(this,"INGRESE UNA DIRECCIÓN DE ENTREGA.",Toast.LENGTH_LONG).show();
                }else{
                    loadLLDU();
                    if(checkBox.isChecked()){
                        Hora = "Lo antes posible";
                    }
                    if(validacion==1){
                        latitudEntrega= Double.valueOf(LatE);
                        longitudEntrega= Double.valueOf(LongE);
                        latitudRecibo= Double.valueOf(LatR);
                        longitudRecibo= Double.valueOf(LongR);
                    }
                    detalles = ETdetalles.getText().toString();
                    ServicioService servicioService = new ServicioService("POST",_ID,direccion,
                            latitudRecibo,longitudRecibo,latitudEntrega,longitudEntrega,Hora,detalles,Home_Usuario_Confirmar.this, view,PDS,KMA,Total,AId,ANombre,AAvatar);
                    servicioService.execute();
                }
                break;
            case R.id.Btn_EditarLocalizacion:
                DireccionFragment dialog = new DireccionFragment(Home_Usuario_Confirmar.this);
                dialog.setCancelable(true);
                dialog.show(getSupportFragmentManager(), "tag");
                break;
                default:
                    break;
        }

    }
    public void Actualizar(View view){
        switch (view.getId()){

            case R.id.Btn_Actualizar:
                    loadLLDU();
                if(validacion==1){
                    DireccionEntrega.setText(address);
                    Actualizar=1;
                }
                    if(Actualizar!=-1){
                        if(checkBox.isChecked()){
                            Hora = "Lo antes posible";
                        }

                        detalles = ETdetalles.getText().toString();
                        ServicioService servicioService = new ServicioService("PATCH",_ID,direccion,latitudRecibo,longitudRecibo,latitudEntrega,longitudEntrega,Hora,detalles,Home_Usuario_Confirmar.this,view, PDS, KMA, Total, AId, ANombre, AAvatar);
                        servicioService.execute();
                 /*   _lista = servicioService.Data();
                    if(_lista.get(5).equals("S")){
                        String t1,t2,t3;
                        t1="$ " + _lista.get(1);
                        t2="$ " + _lista.get(3);
                        t3="$ " + _lista.get(4);
                        PDS.setText(t1);
                        KMA.setText(t2);
                        Total.setText(t3);
                    }*/
                    }else{
                        Toast.makeText(this,"Ingrese una dirección de entrega antes de actualizar",Toast.LENGTH_LONG).show();
                    }
                break;
                default:
                    break;
        }
    }
    public void BBconfirmar(View view){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
