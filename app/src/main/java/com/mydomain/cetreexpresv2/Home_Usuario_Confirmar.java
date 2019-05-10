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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
//**********************************************************CLASE PANTALLA CONFIRMAR PEDIDO
//**********************************************************Esta clase da funcionamiento a la pantalla de confirmar pedido

public class Home_Usuario_Confirmar extends AppCompatActivity implements DireccionFragment.OnFragmentInteractionListener{

    //----------------------------------------------Variables de la clase
    int _ID,Actualizar=0;
    int categoriaID=0;
    String cabecera,Nombre,Precio,KM,KG,Hasta,Direccion = "Ingrese Dirección de entrega",DireccionR,DireccionE,Hora,detalles;
    TextView compra,precioCompra,PDS,KMA,Total,DireccionEntrega;
    Double latitudRecibo,longitudRecibo,latitudEntrega,longitudEntrega;
    DireccionFragment direccionFragment;
    CheckBox checkBox;
    EditText ETdetalles;
    ArrayList<String> ANombre = new ArrayList<>();
    ArrayList<Integer> AId = new ArrayList<>();
    ArrayList<String> AAvatar = new ArrayList<>();
    String LatR,LatE,LongR,LongE;
    String address,articulo,addressR,addressE;
    int validacion=0;
    Button BTNAct;


    //---------------------------------------metodo onCreate
    @SuppressLint("CommitTransaction")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__usuario__confirmar);
        saveLLDU();// Se leen los datos guardados anteriormente

        //Se revisan los permisos requeridos para el funcionamiento de la aplicación
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck==PackageManager.PERMISSION_DENIED){
            if( ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){

            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
        //Se declaran los objetos de la pantalla
        AId= Objects.requireNonNull(getIntent().getExtras()).getIntegerArrayList("AL_ID");
        ANombre=getIntent().getExtras().getStringArrayList("AL_DSC");
        AAvatar=getIntent().getExtras().getStringArrayList("AL_Av");
        LatR=getIntent().getExtras().getString("LaRECIBO");
        LatE=getIntent().getExtras().getString("LaENTREGA");
        LongR=getIntent().getExtras().getString("LongRECIBO");
        LongE=getIntent().getExtras().getString("LongENTREGA");
        articulo=getIntent().getExtras().getString("ARTICULO","_");
        categoriaID= Objects.requireNonNull(getIntent().getExtras()).getInt("Boton");
        _ID=getIntent().getExtras().getInt("_ID");
        Nombre=getIntent().getExtras().getString("Nombre");
        Precio=getIntent().getExtras().getString("Precio");
        KM=getIntent().getExtras().getString("porKM");
        KG=getIntent().getExtras().getString("porKG");
        Hasta=getIntent().getExtras().getString("Hasta");
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
        DireccionEntrega=findViewById(R.id.TV_DireccionEntrega);
        compra=findViewById(R.id.Tv_Compra);
        BTNAct=findViewById(R.id.Btn_Actualizar);
        precioCompra=findViewById(R.id.Tv_Precio);
        String precio = "$199";
        //Se obtiene la localización del dispositivo si es que la ubicación de recibo es MI UBICACIÓN
        Geocoder geocoder;
        List<Address> direccion;
        geocoder = new Geocoder(this, Locale.getDefault());

        double lat = Double.parseDouble(LatE);
        double longi = Double.parseDouble(LongE);

        try {
            //Obtiene la dirección
            direccion = geocoder.getFromLocation(lat, longi, 1); // 1 representa la cantidad de resultados a obtener
            address = direccion.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = direccion.get(0).getLocality();
            String state = direccion.get(0).getAdminArea();
            String country = direccion.get(0).getCountryName();
            String postalCode = direccion.get(0).getPostalCode();
            DireccionEntrega.setText(address);
            compra.setText(articulo);
            precioCompra.setText(precio);
            validacion = 1;

        } catch (IOException e) {
            e.printStackTrace();
            validacion = 0;
        }
        direccionFragment=new DireccionFragment(Home_Usuario_Confirmar.this);
        getSupportFragmentManager().beginTransaction().add(R.id.ContenedorFragment,direccionFragment);
        LocationManager locationManager = (LocationManager)Home_Usuario_Confirmar.this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                //Se obtiene la latitud y longitud de la ubicación del dispositivo
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
        BTNAct.performClick();
    }
    //--------------------------------------------Metodo que guarda datos
    public void saveLLDU(){
        SharedPreferences prefs = Objects.requireNonNull(getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE));
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat("latitud", 0);
        editor.putFloat("longitud", 0);
        editor.putString("direccion", Direccion);
        editor.putInt("Actualizar", -1);
        editor.apply();
    }
    //--------------------------------------------Metodo que guarda lee almacenados anteriormente
    public void loadLLDU(){
        SharedPreferences prefs = getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
        Actualizar=prefs.getInt("Actualizar", -1);
        if(address.contentEquals(DireccionEntrega.getText())){
            Direccion=DireccionEntrega.getText().toString();
            latitudEntrega= Double.valueOf(LatE);
            longitudEntrega= Double.valueOf(LongE);
            latitudRecibo= Double.valueOf(LatR);
            longitudRecibo= Double.valueOf(LongR);
        }else{
            latitudEntrega=(double)prefs.getFloat("latitud", -1);
            longitudEntrega= (double) prefs.getFloat("longitud", -1);
            Direccion=prefs.getString("direccion", "S");
            DireccionEntrega.setText(Direccion);
        }
    }

    //-----------------------------------------Metodo al clickear algún boton de esta pantalla
    public void BotonConfirmar(View view){
        switch (view.getId()){

            case R.id.Boton_Confirmar: // Si se da click al boton de confirmar lee las direcciones de entrega y recibo las manda al servicio correcpondiente
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
                    Geocoder geocoder1;
                    List<Address> direccion1;
                    geocoder1 = new Geocoder(this, Locale.getDefault());

                    double latR = Double.parseDouble(LatR);
                    double longiR = Double.parseDouble(LongR);
                    double latE = Double.parseDouble(LatE);
                    double longiE = Double.parseDouble(LongE);

                    try {
                        direccion1 = geocoder1.getFromLocation(latR, longiR, 1); // 1 representa la cantidad de resultados a obtener
                        addressR = direccion1.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        DireccionR=addressR;
                        direccion1 = geocoder1.getFromLocation(latE, longiE, 1); // 1 representa la cantidad de resultados a obtener
                        addressE = direccion1.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        DireccionE=addressE;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    detalles = ETdetalles.getText().toString();
                    //Se manda a llamar el servicio que activaría el servicio de envio
                    ServicioService servicioService = new ServicioService("POST",categoriaID,DireccionE,DireccionR,
                            latitudRecibo,longitudRecibo,latitudEntrega,longitudEntrega,Hora,detalles,Home_Usuario_Confirmar.this, view,PDS,KMA,Total,AId,ANombre,AAvatar);
                    servicioService.execute();
                }
                break;
            case R.id.Btn_EditarLocalizacion: // Abre un fragmento para cambiar la ubicación
                DireccionFragment dialog = new DireccionFragment(Home_Usuario_Confirmar.this);
                dialog.setCancelable(true);
                dialog.show(getSupportFragmentManager(), "tag");
                break;
                default:
                    break;
        }

    }
    //-----------------------------------------Metodo al clickear el boton de actualizar
    //actualiza los datos de la pantalla
    public void Actualizar(View view){
        switch (view.getId()){

            case R.id.Btn_Actualizar:
                    loadLLDU();
                if(validacion==1){
                    Direccion=DireccionEntrega.getText().toString();
                    latitudEntrega= Double.valueOf(LatE);
                    longitudEntrega= Double.valueOf(LongE);
                    latitudRecibo= Double.valueOf(LatR);
                    longitudRecibo= Double.valueOf(LongR);
                    Actualizar=1;
                }
                    if(Actualizar!=-1){
                        if(checkBox.isChecked()){
                            Hora = "Lo antes posible";
                        }
                        saveLLDU();
                        detalles = ETdetalles.getText().toString();
                        ServicioService servicioService = new ServicioService("PATCH",categoriaID,Direccion, addressR, latitudRecibo,longitudRecibo,latitudEntrega,longitudEntrega,Hora,detalles,Home_Usuario_Confirmar.this,view, PDS, KMA, Total, AId, ANombre, AAvatar);
                        servicioService.execute();
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
