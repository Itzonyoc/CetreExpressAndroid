package com.mydomain.cetreexpresv2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */

//**********************************************FRAGMENTO DE MAPA********************************************

    //En este fragmento se intgresa una dirección y se guarda.


@SuppressLint("ValidFragment")
public class DireccionFragment extends DialogFragment implements OnMapReadyCallback{
    private MapView mapView;
    private GoogleMap gmap;
    Context _ctx;
    View view;
    EditText Buscar;
    ImageButton BtnBuscar;
    Button Aceptar,Cancelar;
    Double Latitud,Longitud;
    String Direccion;
    TextView tv;
    int validacion=0,boton=0;

    //-----------------------------------------------------------Metodo que busca la dirección
    public void SearchDirections(View view){

        List<Address> list=null;
        Geocoder geocoder = new Geocoder(_ctx);
        String location = Buscar.getText().toString();
        try {
            list =  geocoder.getFromLocationName(location, 1);
            assert list != null;
            Address address = list.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            gmap.addMarker(new MarkerOptions().position(latLng).title("Entrega"));
            gmap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            Latitud=address.getLatitude();
            Longitud=address.getLongitude();
            Direccion=location;
            validacion=1;
        } catch (IOException e) {
            e.printStackTrace();
            validacion=0;
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
            Toast.makeText(_ctx,"Dirección no valida",Toast.LENGTH_LONG).show();
            validacion=0;

        }

    }


    private static final String MAP_VIEW_BUNDLE_KEY = "AIzaSyC4znjtsuSN9zI24KbDDbzZi4-Q3xqGF_s";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DireccionFragment(Context home_usuario_confirmar) {
        this._ctx=home_usuario_confirmar;
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLLDU();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    //------------------------------lee los datos guardados anteriormente
    public void loadLLDU(){
        SharedPreferences prefs = Objects.requireNonNull(getContext()).getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
        boton = prefs.getInt("TIPO", -1);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_direccion, container, false);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
    //Se declaran los objetos del fragment
        super.onViewCreated(view, savedInstanceState);
        mapView = view.findViewById(R.id.mapViewFragment);
        BtnBuscar=view.findViewById(R.id.Btn_Buscar);
        Buscar=view.findViewById(R.id.SearchET);
        BtnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchDirections(v);
            }
        });

        Cancelar=view.findViewById(R.id.Btn_Cancelar);
        Cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        Aceptar=view.findViewById(R.id.Btn_Aceptar);
        //Si se presiona el boton de aceptar
        Aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validacion!=0){
                    saveLLDU();
                    Toast.makeText(getActivity(), "Ubicacion agregada, presione el boton de ACTUALIZAR", Toast.LENGTH_LONG).show();
                    dismiss();
                }else{
                    Toast.makeText(getActivity(), "No se ha agregado ninguna ubicación. Presione el boton para agregar dirección",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        if(mapView!=null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }
    //Salva los valores obtenidos
    public void saveLLDU(){
        SharedPreferences prefs = Objects.requireNonNull(getActivity()).getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        try{
            editor.putFloat("latitud", Latitud.floatValue());
            editor.putFloat("longitud", Longitud.floatValue());
            editor.putString("direccion", Direccion);
            editor.putInt("Actualizar", 1);
            editor.putInt("TIPO", boton);
            editor.apply();
        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    public ArrayList<String> Regresar_direccion(){
        ArrayList<String> datos = new ArrayList<>();

        datos.add(0,String.valueOf(Latitud));
        datos.add(1,String.valueOf(Longitud));
        datos.add(2,Direccion);
        return datos;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this._ctx=context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(Objects.requireNonNull(getContext()));
        gmap = googleMap;
        gmap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        gmap.setMyLocationEnabled(false);
        gmap.setMinZoomPreference(12);
        LatLng ny = new LatLng( -0.225219, -78.5248);
        gmap.moveCamera(CameraUpdateFactory.newLatLng(ny));
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}
