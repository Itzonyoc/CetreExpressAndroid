package com.mydomain.cetreexpresv2;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import static com.mydomain.cetreexpresv2.Constantes.RECURSO_SERVICIO;
import static com.mydomain.cetreexpresv2.Constantes.URL_SERVICIOS;

public class ServicioService extends AsyncTask<String,String,String> {

    private ProgressDialog p;
    private boolean error;

    private int _Subcategoria_Id;
    Context _ctx;
    private String _Detalles;
    private String _Direccion_Entrega;
    private Double _Latitud_Recibo;
    private Double _Longitud_Recibo;
    private Double _Latitud_Entrega;
    private Double _Longitud_Entrega;
    private String _Hora_Entrega;
    private String _Servicio;
     @SuppressLint("StaticFieldLeak")
     View v;
     @SuppressLint("StaticFieldLeak")
     private TextView _PdS,_KmA,_Total;


    private String Detalles,Direccion_Entrega,Latitud_Recibo,Longitud_Recibo,Latitud_Entrega,Longitud_Entrega,Hora_Entrega,Fecha_Alta,Fecha_Modificacion,mensaje;
    private int ID,SubCategoria_Id,Estatus_Id;
    private Double Subtotal,Km_Adicionales,Monto_Km_Adicionales,Total;
    ArrayList<String> Array_Nombre = new ArrayList<>();
    ArrayList<Integer> Array_Id = new ArrayList<>();
    ArrayList<String> Array_Avatar = new ArrayList<>();

    public ServicioService(String servicio, int categoriaID, String direccion, Double latitudRecibo,
                           Double longitudRecibo, Double latitudEntrega, Double longitudEntrega,
                           String hora, String detalles, Context ctx, View view, TextView PDS,
                           TextView KMA, TextView total, ArrayList<Integer> AId,
                           ArrayList<String> ANombre, ArrayList<String> AAvatar) {
        this._Subcategoria_Id=categoriaID;
        this._Direccion_Entrega=direccion;
        this._Latitud_Recibo=latitudRecibo;
        this._Longitud_Recibo=longitudRecibo;
        this._Latitud_Entrega=latitudEntrega;
        this._Longitud_Entrega=longitudEntrega;
        this._Hora_Entrega=hora;
        this._Detalles=detalles;
        this._ctx=ctx;
        this.p=new ProgressDialog(_ctx);
        this._Servicio=servicio;
        this.v=view;
        this._PdS=PDS;
        this._KmA=KMA;
        this._Total=total;
        this.Array_Id=AId;
        this.Array_Nombre=ANombre;
        this.Array_Avatar=AAvatar;
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
    public ArrayList<String> Data(){
        TextView tvDE = (TextView) ((Home_Usuario_Confirmar)_ctx).findViewById(R.id.TV_DireccionEntrega);
        TextView tvPS = (TextView) ((Home_Usuario_Confirmar)_ctx).findViewById(R.id.PrecioServicio);
        TextView tvPKA = (TextView) ((Home_Usuario_Confirmar)_ctx).findViewById(R.id.PrecioKMAdicionales);
        TextView tvTL = (TextView) ((Home_Usuario_Confirmar)_ctx).findViewById(R.id.PrecioTotal);

        tvDE.setText(Direccion_Entrega);
        String ST = String.valueOf(Subtotal);
        tvPS.setText(ST);
        String MKA = String.valueOf(Monto_Km_Adicionales);
        tvPKA.setText(MKA);
        String TTL = String.valueOf(Total);
        tvTL.setText(TTL);

        ArrayList<String> list = new ArrayList<>();
        String _id = String.valueOf(ID);
        String _Stotal = String.valueOf(Subtotal);
        String _KMAd = String.valueOf(Km_Adicionales);
        String _MontoKMAd = String.valueOf(Monto_Km_Adicionales);
        String _PTotal = String.valueOf(Total);
        list.add(0,_id);
        list.add(1,_Stotal);
        list.add(2,_KMAd);
        list.add(3,_MontoKMAd);
        list.add(4,_PTotal);
        list.add(5,"S");
        return list;
    }
    @Override
    protected String doInBackground(String... strings) {
        boolean _error;
        if(_Servicio.equals("POST")){
            _error = POST(_Subcategoria_Id, _Detalles,_Direccion_Entrega,_Latitud_Recibo,
                    _Longitud_Recibo,_Latitud_Entrega,_Longitud_Entrega,_Hora_Entrega);
            error = !_error;
        }else if(_Servicio.equals("PATCH")){
            _error = PATCH(_Subcategoria_Id, _Detalles,_Direccion_Entrega,_Latitud_Recibo,
                    _Longitud_Recibo,_Latitud_Entrega,_Longitud_Entrega,_Hora_Entrega);
            error = !_error;
        }
        if(error & _Servicio.equals("POST") ){
            return "1";
        }else if (error & _Servicio.equals("PATCH")){
            return "-1";
        }else
        {
            return "0";
        }
    }

    private boolean POST(int _Subcategoria_Id, String _Detalles,String _Direccion_Entrega,
                         Double _Latitud_Recibo,Double _Longitud_Recibo,Double _Latitud_Entrega,
                         Double _Longitud_Entrega,String _Hora_Entrega){

        String _url = String.format("%s%s", URL_SERVICIOS, RECURSO_SERVICIO);
        boolean _error=false;
        URL url = null;
        try {
            url = new URL(_url);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("User-Agent", System.getProperty("https.agent"));
            urlConnection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            String urlParameters = "Subcategoria_Id="+_Subcategoria_Id+"&Detalles="+_Detalles+
                    "&Direccion_Entrega="+_Direccion_Entrega+"&Latitud_Recibo="+_Latitud_Recibo+
                    "&Longitud_Recibo="+_Longitud_Recibo+"&Latitud_Entrega="+_Latitud_Entrega+
                    "&Longitud_Entrega="+_Longitud_Entrega+"&Hora_Entrega="+_Hora_Entrega;

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
                    }else if(name.equals("data")){
                        try {
                            jsonReader.beginObject();
                            while (jsonReader.hasNext()) {
                                String name1 = jsonReader.nextName();
                                try{
                                    if(name1.equals("Id")){
                                        ID = jsonReader.nextInt();
                                    }else if(name1.equals("Detalles")){
                                        Detalles = jsonReader.nextString();
                                    }else if(name1.equals("Subcategoria_Id")){
                                        SubCategoria_Id = jsonReader.nextInt();
                                    }else if(name1.equals("Direccion_Entrega")){
                                        Direccion_Entrega = jsonReader.nextString();
                                    }else if(name1.equals("Latitud_Recibo")){
                                        Latitud_Recibo=jsonReader.nextString();
                                    }else if(name1.equals("Longitud_Recibo")){
                                        Longitud_Recibo = jsonReader.nextString();
                                    }else if(name1.equals("Latitud_Entrega")){
                                        Latitud_Entrega = jsonReader.nextString();
                                    }else if(name1.equals("Longitud_Entrega")){
                                        Longitud_Entrega = jsonReader.nextString();
                                    }else if(name1.equals("Hora_Entrega")){
                                        Hora_Entrega = jsonReader.nextString();
                                    }else if(name1.equals("Subtotal")){
                                        Subtotal = jsonReader.nextDouble();
                                    }else if(name1.equals("Km_Adicionales")){
                                        Km_Adicionales = jsonReader.nextDouble();
                                    }else if(name1.equals("Monto_Km_Adicionales")){
                                        Monto_Km_Adicionales = jsonReader.nextDouble();
                                    }else if(name1.equals("Total")){
                                        Total = jsonReader.nextDouble();
                                    }else if(name1.equals("Estatus_Id")){
                                        Estatus_Id = jsonReader.nextInt();
                                    }else if(name1.equals("Fecha_Alta")){
                                        Fecha_Alta = jsonReader.nextString();
                                    }else if(name1.equals("Fecha_Modificacion")){
                                        Fecha_Modificacion = jsonReader.nextString();
                                    } else {
                                        jsonReader.skipValue();
                                    }
                                }catch (Exception ex) {
                                    Log.d("PETICION", ex.getMessage());
                                    jsonReader.skipValue();
                                }
                            }
                            jsonReader.endObject();
                        } catch (Exception ex) {
                            Log.d("PETICION", ex.getMessage());
                            jsonReader.skipValue();
                        }
                    }else if(name.equals("mensaje")){
                        mensaje = jsonReader.nextString();
                    }
                }
                jsonReader.endObject();
                Log.d("PETICION", "EXITO");
            }else{
                Log.d("PETICION", "ERROR " + response + " " + _url);
            }
            urlConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return _error;
    }
    private boolean PATCH(int _Subcategoria_Id, String _Detalles,String _Direccion_Entrega,
                         Double _Latitud_Recibo,Double _Longitud_Recibo,Double _Latitud_Entrega,
                         Double _Longitud_Entrega,String _Hora_Entrega){

        String _url = String.format("%s%s", URL_SERVICIOS, RECURSO_SERVICIO);
        boolean _error=false;
        URL url = null;
        try {
            url = new URL(_url);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("User-Agent", System.getProperty("https.agent"));
            urlConnection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            String urlParameters = "Subcategoria_Id="+_Subcategoria_Id+"&Detalles="+_Detalles+
                    "&Direccion_Entrega="+_Direccion_Entrega+"&Latitud_Recibo="+_Latitud_Recibo+
                    "&Longitud_Recibo="+_Longitud_Recibo+"&Latitud_Entrega="+_Latitud_Entrega+
                    "&Longitud_Entrega="+_Longitud_Entrega+"&Hora_Entrega="+_Hora_Entrega;

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
                    }else if(name.equals("data")){
                            try {
                                jsonReader.beginObject();
                                while (jsonReader.hasNext()) {
                                    String name1 = jsonReader.nextName();
                                    try{
                                        if(name1.equals("Id")){
                                            ID = jsonReader.nextInt();
                                        }else if(name1.equals("Detalles")){
                                            Detalles = jsonReader.nextString();
                                        }else if(name1.equals("Subcategoria_Id")){
                                            SubCategoria_Id = jsonReader.nextInt();
                                        }else if(name1.equals("Direccion_Entrega")){
                                            Direccion_Entrega = jsonReader.nextString();
                                        }else if(name1.equals("Latitud_Recibo")){
                                            Latitud_Recibo=jsonReader.nextString();
                                        }else if(name1.equals("Longitud_Recibo")){
                                            Longitud_Recibo = jsonReader.nextString();
                                        }else if(name1.equals("Latitud_Entrega")){
                                            Latitud_Entrega = jsonReader.nextString();
                                        }else if(name1.equals("Longitud_Entrega")){
                                            Longitud_Entrega = jsonReader.nextString();
                                        }else if(name1.equals("Hora_Entrega")){
                                            Hora_Entrega = jsonReader.nextString();
                                        }else if(name1.equals("Subtotal")){
                                            Subtotal = jsonReader.nextDouble();
                                        }else if(name1.equals("Km_Adicionales")){
                                            Km_Adicionales = jsonReader.nextDouble();
                                        }else if(name1.equals("Monto_Km_Adicionales")){
                                            Monto_Km_Adicionales = jsonReader.nextDouble();
                                        }else if(name1.equals("Total")){
                                            Total = jsonReader.nextDouble();
                                        }else if(name1.equals("Estatus_Id")){
                                            Estatus_Id = jsonReader.nextInt();
                                        }else if(name1.equals("Fecha_Alta")){
                                            Fecha_Alta = jsonReader.nextString();
                                        }else if(name1.equals("Fecha_Modificacion")){
                                            Fecha_Modificacion = jsonReader.nextString();
                                        } else {
                                            jsonReader.skipValue();
                                        }
                                    }catch (Exception ex) {
                                        Log.d("PETICION", ex.getMessage());
                                        jsonReader.skipValue();
                                    }
                                }
                                jsonReader.endObject();
                            } catch (Exception ex) {
                                Log.d("PETICION", ex.getMessage());
                                jsonReader.skipValue();
                            }
                    }else if(name.equals("mensaje")){
                        mensaje = jsonReader.nextString();
                    }
                }
                jsonReader.endObject();
                Log.d("PETICION", "EXITO");
            }else{
                Log.d("PETICION", "ERROR " + response + " " + _url);
            }
            urlConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return _error;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        p.dismiss();
        switch (s){
            case "1":
                Intent i = new Intent(_ctx,usuario_Confirmado.class);
                Bundle bundle = new Bundle();
                bundle.putString("TOTAL", String.valueOf(Total));
                bundle.putString("LaRECIBO",Latitud_Recibo);
                bundle.putString("LongRECIBO",Longitud_Recibo);
                bundle.putString("LaENTREGA",Latitud_Entrega);
                bundle.putString("LongENTREGA",Longitud_Entrega);
                bundle.putString("DEntrega",Direccion_Entrega);
                bundle.putIntegerArrayList("AL_ID", Array_Id);
                bundle.putStringArrayList("AL_DSC", Array_Nombre);
                bundle.putStringArrayList("AL_Av",Array_Avatar);
                i.putExtras(bundle);
                _ctx.startActivity(i);
                break;
            case "-1":
                Toast.makeText(_ctx,"Actualizado",Toast.LENGTH_LONG).show();
                Data();
                break;
            case "0":
                Toast.makeText(_ctx,mensaje,Toast.LENGTH_LONG).show();
                break;
                default:
                    break;
        }
    }
}
