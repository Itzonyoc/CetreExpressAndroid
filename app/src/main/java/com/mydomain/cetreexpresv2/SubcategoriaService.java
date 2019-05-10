package com.mydomain.cetreexpresv2;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.JsonReader;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import javax.net.ssl.HttpsURLConnection;
import static com.mydomain.cetreexpresv2.Constantes.RECURSO_SUBCATEGORIA;
import static com.mydomain.cetreexpresv2.Constantes.URL_SERVICIOS;

//----------------------------------Servicio que lista los datos del elemento seleccionado
public class SubcategoriaService extends AsyncTask<String,String,String> {

    //-----------------------------------------Variables del servicio
    private ProgressDialog p;
    private String Acceso="Sin_Acceso";
    private int ID,tipo, Categoria_Id;
    private String Dsc,Nombre,mensaje="SinMensaje",Sub_Dsc,Precio,Por_Kilometro,Por_Kilogramo,Hasta;
    Context _ctx;
    ArrayList<Elemento> lista=new ArrayList<Elemento>();
    RecyclerView _rv;
    Application _app;
    RecyclerView.Adapter _adapter;
    ArrayList<String> Array_Nombre = new ArrayList<>();
    ArrayList<Integer> Array_Id = new ArrayList<>();
    ArrayList<String> Array_Avatar = new ArrayList<>();
    String LatR,LatE,LongR,LongE;


    //----------------------------------------------------------------------Constructor del servicio
    public SubcategoriaService(Cetre_Go_Usuario cetre_go_usuario, int id, RecyclerView rv, Application application,
                               RecyclerView.Adapter adapter, ArrayList<Integer> integers, ArrayList<String> nombre, ArrayList<String> avatar, String latR, String latE, String longR, String longE) {
        this.ID=id;
        this._ctx=cetre_go_usuario;
        this._rv=rv;
        this._app=application;
        this._adapter=adapter;
        this.p=new ProgressDialog(_ctx);
        this.Array_Id=integers;
        this.Array_Nombre=nombre;
        this.Array_Avatar=avatar;
        this.LatE=latE;
        this.LatR=latR;
        this.LongE=longE;
        this.LongR=longR;
    }

    //------------------------------------------Se crea un dialogo de carga mientras el servicio trabaja
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        p.setMessage("Cargando...");
        p.setIndeterminate(false);
        p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        p.setCancelable(false);
        p.show();
    }
    //---------------------------------------------------------------------Verifica que clase de servicio se necesita.
    //El GET verifica los datos para pedir el servico
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
    //-----------------------------------------------------------------------------------Metodo GET
    private boolean GET() {
        boolean _error = false;
        String _url = String.format("%s%s", URL_SERVICIOS, RECURSO_SUBCATEGORIA+"?Categoria_Id="+ID);
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
                                        }else if(name1.equals("Nombre")){
                                            Nombre = jsonReader.nextString();
                                        }else if(name1.equals("Categoria_Id")){
                                            Categoria_Id = jsonReader.nextInt();
                                        }else if(name1.equals("Dsc")){
                                            Dsc = jsonReader.nextString();
                                        }else if(name1.equals("Sub_Dsc")){
                                            Sub_Dsc=jsonReader.nextString();
                                        }else if(name1.equals("Precio")){
                                            Precio = jsonReader.nextString();
                                        }else if(name1.equals("Por_Kilometro")){
                                            Por_Kilometro = jsonReader.nextString();
                                        }else if(name1.equals("Por_Kilogramo")){
                                            Por_Kilogramo = jsonReader.nextString();
                                        }else if(name1.equals("Hasta")){
                                            Hasta = jsonReader.nextString();
                                        }else if(name1.equals("Estatus_Id")){
                                            tipo = jsonReader.nextInt();
                                        } else {
                                            jsonReader.skipValue();
                                        }
                                    }catch (Exception ex) {
                                        Log.d("PETICION", ex.getMessage());
                                        jsonReader.skipValue();
                                    }
                                }
                                jsonReader.endObject();

                                Elemento el = new Elemento(Nombre, Dsc, Sub_Dsc, Precio, ID, Por_Kilometro,Por_Kilogramo,Hasta,tipo,mensaje,Categoria_Id);
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
    private static final String PREFS_TAG = "SharedPrefs";

    //Despues de ejecutarse el servicio cierra el dialogo de carga y carga la lista
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        p.dismiss();
        if(Acceso.compareTo("Con_Acceso")==0){
            _adapter= new Adaptador(lista, _app,Array_Id,Array_Nombre,Array_Avatar,LatR,LatE,LongR,LongE);
            _rv.setAdapter(_adapter);
        }
    }
}
