package com.mydomain.cetreexpresv2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
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
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Arrays;
import java.util.Objects;
 //****************************************************************CLASE DE PANTALLA PARA INICIO DE SESIÓN
public class LoginUsuarioActivity extends AppCompatActivity implements TextWatcher, CompoundButton.OnCheckedChangeListener, GoogleApiClient.OnConnectionFailedListener {

    //----------------------------------------------------Variables de la clase
    EditText Numero_de_cedula_Login, Contrasena_Login;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    CheckBox checkBox;
    private static final String PREF_NAME="prefs";
    private static final String KEY_REMEBER="remember";
    private static final String KEY_USER="user";
    private static final String KEY_PASS="pass";
    private static final int REQCODE =9001;
    private GoogleApiClient googleApiClient;
    CallbackManager callbackManager;
    Integer ID = 0;
    Button Google,fb;
    SignInButton signInButtonGoogle;
    String email;
    String name;


    //---------------------------------------------------------------------------Metodo onCreate
    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_usuario);
        FacebookSdk.sdkInitialize(getApplicationContext());//Se inicializa el boton de Facebook
        AppEventsLogger.activateApp(this);
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        checkBox=findViewById(R.id.CB_RecordarDatos);
        ID = Objects.requireNonNull(getIntent().getExtras()).getInt("ID");
        if(sharedPreferences.getBoolean(KEY_REMEBER, false)){//Revisa si están los datos de inicio de sesión recordados
            checkBox.setChecked(true);
        }else{
            checkBox.setChecked(false);
        }
        //Se declaran los elementos del la pantalla de diseño
        Numero_de_cedula_Login = (EditText) findViewById(R.id.Et_Numero_de_cedula_Login);
        Contrasena_Login = (EditText) findViewById(R.id.Et_Contrasena_Login);
        Numero_de_cedula_Login.setText(sharedPreferences.getString(KEY_USER, ""));
        Contrasena_Login.setText(sharedPreferences.getString(KEY_PASS, ""));
        Numero_de_cedula_Login.addTextChangedListener(this);
        Contrasena_Login.addTextChangedListener(this);
        signInButtonGoogle = findViewById(R.id.GoogleSIBtn);
        Google=findViewById(R.id.Google);
        checkBox.setOnCheckedChangeListener(this);
        fb=findViewById(R.id.fb);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //Despues de presionar el boton de google manda a esta actividad la cual si el REQUESTCODE es el mismo que el inicio de sesión entonce autentifica por google
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

    //--------------------------------------------------------Boton de iniciar sesión
    public void alPresionarBotonLogin(View view){
        switch(view.getId()){
            case R.id.Btn_Contraseña_Olvidada:
                //mandar a restablecer contraseña
                break;
            case R.id.Btn_IniciarSesion_LoginActivity://Inicia sesión de manera normal

                //Realiza verificaciones de longitud de contraseña, de tipo de usuario,etc.
                if(Numero_de_cedula_Login.length() == 0 && Contrasena_Login.length() == 0){

                    Numero_de_cedula_Login.setError("Ingresa tu numero de cedula");
                    Contrasena_Login.setError("Ingresa una contraseña");

                }else if(Numero_de_cedula_Login.length() == 0){

                    Numero_de_cedula_Login.setError("Ingresa tu numero de cedula");

                }else if(Contrasena_Login.length() == 0){

                    Contrasena_Login.setError("Ingresa una contraseña");

                }else{
                    if(Numero_de_cedula_Login.length() >= 3 && Contrasena_Login.length() >= 6){
                        String _Cedula = Numero_de_cedula_Login.getText().toString();
                        String _Contrasena = Contrasena_Login.getText().toString();

                        //verificar datos de inicio de sesion
                        UsuariosService usuariosService = new UsuariosService("GET","0",_Cedula,_Contrasena,ID,LoginUsuarioActivity.this);
                       usuariosService.execute();
                    }else{
                        if(Numero_de_cedula_Login.length() < 3 && Contrasena_Login.length() < 6){
                            Numero_de_cedula_Login.setError("El numero de cédula debe ser de 3 letras/numeros minimos de longitud");
                            Contrasena_Login.setError("La contraseña debe ser de 6 letras/numeros minimos de longitud");
                        }else if(Numero_de_cedula_Login.length() < 3){
                            Numero_de_cedula_Login.setError("El numero de cédula debe ser de 3 letras/numeros minimos de longitud");
                        }else if(Contrasena_Login.length() < 6){
                            Contrasena_Login.setError("La contraseña debe ser de 6 letras/numeros minimos de longitud");
                        }
                    }
                }

                break;
            case R.id.Btn_Registrarse_LoginActivity://Manda a la pantalla de registrarse
                Intent i = new Intent(this,RegistroUsuarioActivity.class);
                i.putExtra("ID", ID);
                i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(i);
                finish();
                break;
            default:
                break;
        }

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this,RegistroUsuarioActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ID", ID);
        i.putExtras(bundle);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(i);
        finish();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { managePrefs(); }

    @Override
    public void afterTextChanged(Editable s) {

    }
    //---------------------------------------revisa las preferencias del usuario
    private void managePrefs(){
        if(checkBox.isChecked()){
            editor.putString(KEY_USER, Numero_de_cedula_Login.getText().toString().trim());
            editor.putString(KEY_PASS,Contrasena_Login.getText().toString().trim());
            editor.putBoolean(KEY_REMEBER,  true);
            editor.apply();
        }else{
            editor.putBoolean(KEY_REMEBER,  false);
            editor.remove(KEY_PASS);
            editor.remove(KEY_USER);
            editor.apply();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { managePrefs(); }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    //---------------------------------------------------------Al presionar boton de facebook
    public void onClickFacebookButton(View view) {
        if (view == fb) {
            callbackManager = CallbackManager.Factory.create();

            LoginManager.getInstance().logInWithReadPermissions(LoginUsuarioActivity.this, Arrays.asList("public_profile"));

            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Log.i("Login:", "@@@onSuccess");
                    GraphRequest request = GraphRequest.newMeRequest(
                            loginResult.getAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject object, GraphResponse response) {
                                    Log.i("Login", "@@@response: " + response.toString());

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
                                    UsuariosService usuariosService = new UsuariosService("GET",name,email,"FacebookCetreExpressUser",ID,LoginUsuarioActivity.this);
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
                    Log.i("LOGIN", "@@@onCancel");
                }

                @Override
                public void onError(FacebookException error) {
                        Log.i("LOGIN", "@@@onError: "+ error.getMessage());
                }
            });
        }
    }
    //Al presionar boton de google
    public void onClickGoogleButton(View view) {
        signInGoogle(view);
    }
    public void signInGoogle(View v) {

        if (googleApiClient == null || !googleApiClient.isConnected()) {
            try {
                GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();
                googleApiClient = new GoogleApiClient.Builder(LoginUsuarioActivity.this)
                        .enableAutoManage(LoginUsuarioActivity.this,LoginUsuarioActivity.this)
                        .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, REQCODE);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(LoginUsuarioActivity.this,"ERROR",Toast.LENGTH_LONG).show();
            }
        }
    }
    //Si se autentifica correctamente el usuario, manda los datos al servicio de USUARIO SERVICE para verificar que el usuario exista
    private void handleResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            String name = Objects.requireNonNull(account).getDisplayName();
            String email = account.getEmail();
            // String imgUrl = Objects.requireNonNull(account.getPhotoUrl()).toString();
            UsuariosService usuariosService = new UsuariosService("GET","0",email,"GMAIL",ID,LoginUsuarioActivity.this);
            usuariosService.execute();
            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    googleApiClient.stopAutoManage(LoginUsuarioActivity.this);
                    googleApiClient.disconnect();
                }
            });
        }else{
            Toast.makeText(LoginUsuarioActivity.this,"ERROR",Toast.LENGTH_LONG).show();
        }
    }
}
