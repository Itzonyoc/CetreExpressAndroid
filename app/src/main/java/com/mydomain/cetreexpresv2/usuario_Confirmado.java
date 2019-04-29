package com.mydomain.cetreexpresv2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Objects;

public class usuario_Confirmado extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mapView;
    private GoogleMap gmap;
    private static final String MAP_VIEW_BUNDLE_KEY = "AIzaSyAaCp__QNhiV-b7V8cTc73wAdOMotzxctA";

    String cabecera;
    int _ID;
    int boton = 0;

    ArrayList<String> Nombre = new ArrayList<>();
    ArrayList<Integer> Id = new ArrayList<>();
    ArrayList<String> Avatar = new ArrayList<>();
    String LatR,LatE,LongR,LongE,DireccionEntrega,DireccionRecibo,Total;
    TextView TV_TotalEstimado,Drecibo,Dentrega;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario__confirmado);
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        LatR= Objects.requireNonNull(getIntent().getExtras()).getString("LaRECIBO");
        LatE=getIntent().getExtras().getString("LaENTREGA");
        LongR=getIntent().getExtras().getString("LongRECIBO");
        LongE=getIntent().getExtras().getString("LongENTREGA");
        DireccionEntrega=getIntent().getExtras().getString("DEntrega");
        Total="$ "+ getIntent().getExtras().getString("TOTAL");
        Id= Objects.requireNonNull(getIntent().getExtras()).getIntegerArrayList("AL_ID");
        Nombre=getIntent().getExtras().getStringArrayList("AL_DSC");
        Avatar=getIntent().getExtras().getStringArrayList("AL_Av");
        TV_TotalEstimado=findViewById(R.id.Tv_PTE);
        Dentrega=findViewById(R.id.TV_DireccionEntrega);
        mapView = findViewById(R.id.GoogleMV);
        Dentrega.setText(DireccionEntrega);
        TV_TotalEstimado.setText(Total);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
    }

    public void BackButtonConfirmado(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
      Intent i = new Intent(usuario_Confirmado.this, HomeUsuario2.class);
        Bundle bundle = new Bundle();
        bundle.putInt("Boton", boton);
        for (int x = 0; x < Nombre.size(); x++) {
            if (Nombre.get(x).equals(cabecera)) {
                bundle.putInt("_ID", Id.get(x));
            }
        }
        bundle.putIntegerArrayList("AL_ID", Id);
        bundle.putStringArrayList("AL_DSC", Nombre);
        bundle.putStringArrayList("AL_Av", Avatar);
        i.putExtras(bundle);
        startActivity(i);
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
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
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
        LatLng E = new LatLng(LatitudEntrega, LongitudEntrega);
        gmap.addMarker(new MarkerOptions().position(E).title("Entrega"));
        gmap.animateCamera(CameraUpdateFactory.newLatLng(E));
        LatLng R = new LatLng(LatitudRecibo, LongitudRecibo);
        gmap.addMarker(new MarkerOptions().position(R).title("Recibo"));
        gmap.animateCamera(CameraUpdateFactory.newLatLng(R));
    }
}
