package com.mydomain.cetreexpresv2;

import android.annotation.SuppressLint;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import static com.mydomain.cetreexpresv2.Constantes.RECURSO_LOGIN;
import static com.mydomain.cetreexpresv2.Constantes.RECURSO_PERSONA;
import static com.mydomain.cetreexpresv2.Constantes.URL_SERVICIOS;


public class UsuariosService extends AsyncTask<String,String,String> {
    static final String SHARED_PREFS = "SharedPrefs";
    static final String OUTDATA_ACCESS = "OUTDATA_ACCESS";
    static final String OUTDATA_CEDULA_R = "OUTDATA_CEDULA_R";
    static final String OUTDATA_CONTRASENA_R = "OUTDATA_CONTRASENA_R";
    private static final String OUTDATA_CEDULA_A = "OUTDATA_CEDULA_A";
    private static final String OUTDATA_CONTRASENA_A = "OUTDATA_CONTRASENA_A";
    boolean Registro=false;
    boolean Acceso=false;
    private ProgressDialog p;

    @SuppressLint("StaticFieldLeak")
    private Context _ctx;
    private String _metodo;
    private String _nombre;
    private String _n_cedula;
    private String _contrasena;
    private Integer _id;
    String nombre;
    private String cedula;


        public UsuariosService(String s, String nom, String n_cedula, String contrasena, Integer id, Context ctx) {
            this._metodo = s;
            this._nombre = nom;
            this._n_cedula=n_cedula;
            this._contrasena=contrasena;
            this._id=id;
            this._ctx=ctx;
            this.p=new ProgressDialog(ctx);
        }


    @Override
    protected void onPreExecute() {
            super.onPreExecute();
        p.setMessage("Cargando...");
        p.setIndeterminate(false);
        p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        p.setCancelable(false);
        p.show();
    }

    @Override
    protected String doInBackground(String... strings) {

        if (_metodo.compareTo("POST") == 0) {
            Boolean _error = POST(_nombre,_n_cedula,_contrasena,_id);
            if (!_error) {
                System.out.println("Registro Completado.");
                Registro = true;
            } else {
                System.out.println("Número de cédula/Contraseña incorrectos.");
                Registro = false;
            }
        } else if (_metodo.compareTo("GET") == 0) {
            Boolean _error = GET(_n_cedula,_contrasena);
            if (!_error) {
                System.out.println("Acceso Concedido");
                Acceso = true;
            } else {
                System.out.println("Número de cédula/Contraseña incorrectos.");
                Acceso = false;
            }
        }

        if(Registro){
            return "1";
        }else if(Acceso){
            return "2";
        }else{
                return "-1";
        }
    }

    private boolean POST(String _Nombre, String _N_cedula, String _Contrasena, Integer _Id) {

        String _url = String.format("%s%s", URL_SERVICIOS, RECURSO_PERSONA);
        boolean _error=false;
        URL url = null;
        try {
            url = new URL(_url);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("User-Agent", System.getProperty("https.agent"));
            urlConnection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            String urlParameters = "Nombre="+_Nombre+"&Numero_Cedula="+_N_cedula+"&Contrasena="+_Contrasena+"&Usuario_Tipo_Id="+_Id;
            urlConnection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();
            int response =  urlConnection.getResponseCode();

            if (response == 200) {
                InputStream responseBody = urlConnection.getInputStream();
                InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                JsonReader jsonReader = new JsonReader(responseBodyReader);
                jsonReader.beginObject();

                while (jsonReader.hasNext()) {
                    String name = jsonReader.nextName();
                    if (name.equals("error")) {
                        _error = jsonReader.nextBoolean();
                    }else if(name.equals("id")){
                        int ID = jsonReader.nextInt();
                    }else if(name.equals("mensaje")){
                        String mensaje = jsonReader.nextString();
                    }
                }
                jsonReader.endObject();
                Log.d("PETICION", "USUARIO REGISTRADO");
            }else{
                Log.d("PETICION", "ERROR " + response + " " + _url);
            }
            urlConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            _error=true;
        }
        return _error;
    }

    private boolean GET(String _N_cedula, String _Contrasena) {
        boolean _error = false;
        String _url = String.format("%s%s", URL_SERVICIOS, RECURSO_LOGIN + "?Numero_Cedula=" + _N_cedula +"&Contrasena="+_Contrasena);
        URL url = null;

        try {
            url = new URL(_url);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            int response = urlConnection.getResponseCode();
            if (response == 200) {
                InputStream responseBody = urlConnection.getInputStream();
                InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                JsonReader jsonReader = new JsonReader(responseBodyReader);
                jsonReader.beginObject();

                while (jsonReader.hasNext()) {

                    String name = jsonReader.nextName();

                    if (name.equals("error")) {
                        _error = jsonReader.nextBoolean();
                    }else if(name.equals("id")){
                        int ID = jsonReader.nextInt();
                    }else if(name.equals("mensaje")){
                        String mensaje = jsonReader.nextString();
                    }else if(name.equals("token")){
                        String token = jsonReader.nextString();
                    }else if(name.equals("tipo")){
                        int tipo = jsonReader.nextInt();
                    }else if(name.equals("cedula")){
                        cedula = jsonReader.nextString();
                    }else if(name.equals("nombre")){
                        nombre = jsonReader.nextString();
                    }
                }
                jsonReader.endObject();
                }else{
                    Log.d("PETICION", "ERROR " + response + " " + _url);
                }
        } catch (IOException | IllegalStateException e) {
            e.printStackTrace();
        }
        return _error;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        p.dismiss();
        if(s.equals("1")){
            Toast.makeText(_ctx,"Registro Completado Exitosamente",Toast.LENGTH_LONG).show();
        }else if(s.equals("2")){
            Toast.makeText(_ctx,"Acceso concedido.",Toast.LENGTH_LONG).show();
            if(_id == 1){
                CategoriaService categoriaService = new CategoriaService(_ctx,_n_cedula,_id,nombre,cedula);
                categoriaService.execute();
            }else if(_id == 2){

            }else{
                Toast.makeText(_ctx,"ERROR",Toast.LENGTH_LONG).show();
            }
        }else {
            if(_metodo.equals("POST")){
                Toast.makeText(_ctx,"ERROR: Registro no completado",Toast.LENGTH_LONG).show();
            }else if(_metodo.equals("GET")){
                Toast.makeText(_ctx,"ERROR: El usuario no existe",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(_ctx,"ERROR",Toast.LENGTH_LONG).show();
            }
        }
    }
}
