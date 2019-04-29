package com.mydomain.cetreexpresv2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import static com.mydomain.cetreexpresv2.Constantes.RECURSO_CATEGORIA;
import static com.mydomain.cetreexpresv2.Constantes.URL_SERVICIOS;

public class CategoriaService extends AsyncTask<String,String,String> {
    private String Acceso="Sin_Acceso";
    private int ID,Estatus_Id;
    private String Dsc,urlAvatar,mensaje;
    private Intent i;
    private Context _ctx;
    private String _n_cedula;
    private int _id;
    private String nombre;
    private String cedula;

    ArrayList<MenuElementos> lista=new ArrayList<MenuElementos>();
    ArrayList<String> Nombre = new ArrayList<>();
    ArrayList<Integer> Id = new ArrayList<>();
    ArrayList<String> Avatar = new ArrayList<>();

    public CategoriaService(Context context, String n_cedula, Integer id, String nombre, String cedula) {
        this._ctx=context;
        this._n_cedula=n_cedula;
        this._id=id;
        this.nombre=nombre;
        this.cedula=cedula;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {

            Boolean _error = GET();
            if (!_error) {
                System.out.println("SIN ERROR");
                Acceso = "Con_Acceso";
            } else {
                System.out.println("************ERROR*****************");
                Acceso = "Sin_Acceso";
            }
            return Acceso;
        }

    private boolean GET() {
        boolean _error = false;
        String _url = String.format("%s%s", URL_SERVICIOS, RECURSO_CATEGORIA);
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
                    } else if (name.equals("data")) {
                        jsonReader.beginArray();
                        while (jsonReader.hasNext()) {
                            try {
                                jsonReader.beginObject();
                                while (jsonReader.hasNext()) {
                                    String name1 = jsonReader.nextName();
                                    try{
                                        if(name1.equals("Id")){
                                            ID = jsonReader.nextInt();
                                        }else if(name.equals("Estatus_Id")){
                                            Estatus_Id = jsonReader.nextInt();
                                        }else if(name1.equals("Dsc")){
                                            Dsc = jsonReader.nextString();
                                        }else if(name1.equals("Avatar")){
                                            urlAvatar=jsonReader.nextString();
                                        } else {
                                            jsonReader.skipValue();
                                        }
                                    }catch (Exception ex) {
                                        Log.d("PETICION", ex.getMessage());
                                        jsonReader.skipValue();
                                    }
                                }
                                jsonReader.endObject();

                                MenuElementos el = new MenuElementos(ID, Dsc, urlAvatar,  Estatus_Id);
                                lista.add(el);
                            } catch (Exception ex) {
                                Log.d("PETICION", ex.getMessage());
                                jsonReader.skipValue();
                            }

                        }
                        jsonReader.endArray();
                    }else if(name.equals("mensaje")){
                        mensaje = jsonReader.nextString();
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
        for(int x=0;x<lista.size();x++){
            MenuElementos MELista = lista.get(x);
            Nombre.add(MELista.getDsc());
            Avatar.add(MELista.getUrlAvatar());
            Id.add(MELista.getID());
        }
        i = new Intent(_ctx,HomeUsuario2.class);
        Bundle dataBundle = new Bundle();
        dataBundle.putIntegerArrayList("AL_ID", Id);
        dataBundle.putStringArrayList("AL_DSC", Nombre);
        dataBundle.putStringArrayList("AL_Av", Avatar);
        dataBundle.putString("NOMBRE", nombre);
        dataBundle.putString("CEDULA", cedula);
        i.putExtras(dataBundle);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        _ctx.startActivity(i);
    }
}
