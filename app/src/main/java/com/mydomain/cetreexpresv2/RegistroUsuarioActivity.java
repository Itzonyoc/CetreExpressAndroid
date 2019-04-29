package com.mydomain.cetreexpresv2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaCas;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.se.omapi.Session;
import android.service.textservice.SpellCheckerService;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.BoringLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Response;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class RegistroUsuarioActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    EditText Nombre, Numero_de_cedula, Contrasena;
    private static RegistroUsuarioActivity instance;
    Boolean Registro=true;
    Integer ID = 0;
    private static final int REQCODE =9001;
    private GoogleApiClient googleApiClient;
    Button fb,gglsi;
    LoginButton singupFbButton;
    SignInButton signInButtonGoogle;
    CallbackManager callbackManager;
    String first_name;
    String last_name;
    String name;
    String email;
    String fb_id;
    String image_url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        instance = this;
        ID = Objects.requireNonNull(getIntent().getExtras()).getInt("ID");
        setContentView(R.layout.activity_registro_usuario);
        Nombre = findViewById(R.id.Et_Nombre);
        Numero_de_cedula = findViewById(R.id.Et_Numero_de_cedula);
        Contrasena = findViewById(R.id.Et_Contrasena);
        fb = findViewById(R.id.fb);
        signInButtonGoogle = findViewById(R.id.GoogleSIBtn);
        gglsi=findViewById(R.id.GoogleSU);
        singupFbButton=findViewById(R.id.FB_singup_button);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==REQCODE){
            super.onActivityResult(requestCode, resultCode, data);
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }else{
            if(callbackManager.onActivityResult(requestCode, resultCode, data)) {

                return;
            }
        }
    }




    public static RegistroUsuarioActivity getInstance() {
        return instance;
    }
    public void alPresionarBotonRegistro(View view) {
        switch(view.getId()){
            case R.id.Btn_Registro:

                if(Nombre.length() == 0 && Numero_de_cedula.length() == 0 && Contrasena.length() == 0 ){

                    Nombre.setError("Ingresa un nombre");
                    Numero_de_cedula.setError("Ingresa tu número de cédula");
                    Contrasena.setError("Ingresa una contraseña");

                }else if(Nombre.length() == 0){

                    Nombre.setError("Ingresa un nombre");

                }else if(Numero_de_cedula.length() == 0){

                    Numero_de_cedula.setError("Ingresa tu número de cédula");

                }else if(Contrasena.length() == 0){

                    Contrasena.setError("Ingresa una contraseña");
                }else{
                    if( Numero_de_cedula.length() >= 3 && Contrasena.length() >= 6 && Nombre.length() != 0){
                        String nom = Nombre.getText().toString();
                        String N_cedula = Numero_de_cedula.getText().toString();
                        String contrasena = Contrasena.getText().toString();

                        UsuariosService usuariosService = new UsuariosService("POST",nom,N_cedula,contrasena,ID,RegistroUsuarioActivity.this);
                       usuariosService.execute();
                    }else if(Numero_de_cedula.length() < 3){
                        Numero_de_cedula.setError("El número de cédula debe ser de 3 letras/numeros minimos de longitud");
                    }else if(Contrasena.length() < 6){
                        Contrasena.setError("La contraseña debe ser de 6 letras/numeros minimos de longitud");
                    }
                }
                break;
            case R.id.Btn_IniciarSesion:

                Intent i = new Intent(this,LoginUsuarioActivity.class);
                i.putExtra("ID", ID);
                i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(i);
                finish();
                break;
            default:
                break;
        }

    }
    public void onClickFacebookButton(View view) {
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logInWithReadPermissions(RegistroUsuarioActivity.this, Arrays.asList("public_profile"));

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i("REGISTRO", "@@@onSuccess");
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.i("Registro", "@@@response: " + response.toString());

                                try {
                                    name = object.getString("name");
                                    email = object.getString("email");
                                    Log.i("DATOS:", name+"/"+email);
                                    LoginManager.getInstance().logOut();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                registrarFacebook(name, email);
                            }
                            private void registrarFacebook(String name, String email) {
                                UsuariosService usuariosService = new UsuariosService("POST",name,email,"FacebookCetreExpressUser",ID,RegistroUsuarioActivity.this);
                                usuariosService.execute();
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.i("REGISTRO", "@@@onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.i("REGISTRO", "@@@onError: "+ error.getMessage());
            }
        });
    }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    public void onClickGoogleButton(View view) {
        signUpGoogle(view);
    }
    public void signUpGoogle(View v) {
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(RegistroUsuarioActivity.this)
                .enableAutoManage(RegistroUsuarioActivity.this,RegistroUsuarioActivity.this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, REQCODE);
    }
    private void handleResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            String name = Objects.requireNonNull(account).getDisplayName();
            String email = account.getEmail();
            // String imgUrl = Objects.requireNonNull(account.getPhotoUrl()).toString();
            UsuariosService usuariosService = new UsuariosService("POST",name,email,"GMAIL",ID,RegistroUsuarioActivity.this);
            usuariosService.execute();
            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                }
            });
        }else{
            Toast.makeText(RegistroUsuarioActivity.this,"ERROR",Toast.LENGTH_LONG).show();
        }
    }


}
