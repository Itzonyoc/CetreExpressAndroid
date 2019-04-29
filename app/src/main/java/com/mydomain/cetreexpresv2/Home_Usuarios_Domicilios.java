package com.mydomain.cetreexpresv2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.renderscript.ScriptGroup;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;




public class Home_Usuarios_Domicilios extends AppCompatActivity implements DireccionFragment.OnFragmentInteractionListener {

    ArrayList<String> Nombre = new ArrayList<>();
    ArrayList<Integer> Id = new ArrayList<>();
    ArrayList<String> Avatar = new ArrayList<>();
    String nombre, cedula,Direccion,Mdir,Edir;
   Double Latitud_Recibo = 0.0;
   Double Longitud_Recibo= 0.0;
   Double Latitud_Entrega= 0.0;
   Double Longitud_Entrega= 0.0;
    private PlacesClient placesClient;
    private List<AutocompletePrediction> predictions;
    DireccionFragment direccionFragment;
    int _ID,Actualizar=0,tipo,boton,val=0;
    Button miUbicacion,miEntrega;
    Double MLat,MLong,ELat,ELong;



    private MaterialSearchBar materialSearchBar;
    private static final String MAP_VIEW_BUNDLE_KEY = "AIzaSyC4znjtsuSN9zI24KbDDbzZi4-Q3xqGF_s";


    @SuppressLint("CommitTransaction")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boton=0;
        saveLLDU();
        setContentView(R.layout.activity_home__usuarios__domicilios);
        Id = Objects.requireNonNull(getIntent().getExtras()).getIntegerArrayList("AL_ID");
        Nombre = getIntent().getExtras().getStringArrayList("AL_DSC");
        Avatar = getIntent().getExtras().getStringArrayList("AL_Av");
        nombre = getIntent().getExtras().getString("NOMBRE");
        cedula = getIntent().getExtras().getString("CEDULA");
        miUbicacion = findViewById(R.id.MiUbicación);
        miEntrega = findViewById(R.id.miEntrega);
        direccionFragment=new DireccionFragment(Home_Usuarios_Domicilios.this);
        getSupportFragmentManager().beginTransaction().add(R.id.ContenedorFragment,direccionFragment);
      /*  Places.initialize(Home_Usuarios_Domicilios.this, MAP_VIEW_BUNDLE_KEY);
        placesClient = Places.createClient(this);
        materialSearchBar = findViewById(R.id.searchBar);
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text.toString(), true, null, true);
            }

            @Override
            public void onButtonClicked(int buttonCode) {
                if(buttonCode == MaterialSearchBar.BUTTON_NAVIGATION){

                }else if(buttonCode == MaterialSearchBar.BUTTON_BACK){
                    materialSearchBar.disableSearch();
                }
            }
        });
        final AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();
        materialSearchBar.addTextChangeListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                FindAutocompletePredictionsRequest predictionsRequest = FindAutocompletePredictionsRequest.builder()
                        .setCountry("mx")
                        .setTypeFilter(TypeFilter.ADDRESS)
                        .setSessionToken(token)
                        .setQuery(s.toString())
                        .build();
                placesClient.findAutocompletePredictions(predictionsRequest).addOnCompleteListener(new OnCompleteListener<FindAutocompletePredictionsResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<FindAutocompletePredictionsResponse> task) {
                        if(task.isSuccessful()){
                            FindAutocompletePredictionsResponse predictionsResponse = task.getResult();
                            if(predictionsResponse != null){
                                predictions = predictionsResponse.getAutocompletePredictions();
                                List<String> suggestionList = new ArrayList<>();
                                for(int i=0; i<predictions.size(); i++){
                                    AutocompletePrediction prediction = predictions.get(i);
                                    suggestionList.add(prediction.getFullText(null).toString());
                                }
                                materialSearchBar.updateLastSuggestions(suggestionList);
                                if(!materialSearchBar.isSuggestionsVisible()){
                                    materialSearchBar.showSuggestionsList();
                                }
                            }
                        }else{
                            Log.i("TAG", "unsuccesfull");
                        }
                    }
                });

                materialSearchBar.setSuggestionsClickListener(new SuggestionsAdapter.OnItemViewClickListener() {
                    @Override
                    public void OnItemClickListener(int position, View v) {
                        if(position >= predictions.size()){
                            return;
                        }
                        AutocompletePrediction selectedPrediction = predictions.get(position);
                        String suggestion = materialSearchBar.getLastSuggestions().get(position).toString();
                        materialSearchBar.setText(suggestion);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                materialSearchBar.clearSuggestions();
                            }
                        },1000);
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        if(inputMethodManager != null){
                            inputMethodManager.hideSoftInputFromWindow(materialSearchBar.getWindowToken(),InputMethodManager.HIDE_IMPLICIT_ONLY );
                            String placeID = selectedPrediction.getPlaceId();
                        List<Place.Field> placeFields = Arrays.asList(Place.Field.LAT_LNG);
                           FetchPlaceRequest fetchPlaceRequest = FetchPlaceRequest.builder(placeID,placeFields).build();
                            placesClient.fetchPlace(fetchPlaceRequest).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                                @Override
                                public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {
                                    Place place = (Place) fetchPlaceResponse.getPlace();
                                    LatLng latLng = place.getLatLng();
                                    if(latLng!=null){
                                        //agregar aubicación
                                    }
                                }
                            });

                        }
                    }

                    @Override
                    public void OnItemDeleteListener(int position, View v) {

                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
*/
        /* <com.mancj.materialsearchbar.MaterialSearchBar
        android:id="@+id/searchBar"
        style="@style/MaterialSearchBarLight"
        app:mt_navIconEnabled="true"
        app:mt_placeholder="Mi Ubicación"
        android:elevation="5dp"
        android:outlineProvider="bounds"

        android:layout_width="match_parent"
        android:layout_height="51dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="1.0"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/appBarLayout"
        app:mt_hint="Custom hint"
        app:mt_maxSuggestionsCount="10"
        app:mt_speechMode="true" />
        implementation files('libs/gson-2.3.1.jar')*/
    }
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
    public void loadLLDU(){
        SharedPreferences prefs = getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
        Latitud_Recibo=(double)prefs.getFloat("latitud", -1);
        Longitud_Recibo= (double) prefs.getFloat("longitud", -1);
        Direccion=prefs.getString("direccion", "S");
        tipo=prefs.getInt("TIPO", -1);
        if(tipo == 1){
            miUbicacion.setText(Direccion);
            MLat = Latitud_Recibo;
            MLong = Longitud_Recibo;
            Mdir = Direccion;
            val=1;
        }else if(tipo == 2){
            miEntrega.setText(Direccion);
            ELat = Latitud_Recibo;
            ELong = Longitud_Recibo;
            Edir = Direccion;
            val=2;
        }
        if(val!=0){
            miUbicacion.setText(Mdir);
            miEntrega.setText(Edir);
        }

        Actualizar=prefs.getInt("Actualizar", -1);
    }

    public void confirmarDireccion(View v){
        switch (v.getId()){
            case R.id.ConfirmarBTN:
                loadLLDU();
                Intent i = new Intent(this,HomeUsuarioSeleccionar.class);
                Bundle bundle = new Bundle();
                bundle.putInt("ID", 1);
                bundle.putIntegerArrayList("AL_ID", Id);
                bundle.putStringArrayList("AL_DSC", Nombre);
                bundle.putStringArrayList("AL_Av", Avatar);
                bundle.putString("NOMBRE", nombre);
                bundle.putString("CEDULA", cedula);
                bundle.putString("LaRECIBO",MLat.toString());
                bundle.putString("LongRECIBO",MLong.toString());
                bundle.putString("LaENTREGA",ELat.toString());
                bundle.putString("LongENTREGA",ELong.toString());
                i.putExtras(bundle);
                startActivity(i);
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
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
