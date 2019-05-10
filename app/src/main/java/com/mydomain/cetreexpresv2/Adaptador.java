package com.mydomain.cetreexpresv2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.gson.Gson;
import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
//*****************************************************************************ADAPTADOR QUE SIRVE PARA LISTAR LOS PRODUCTOS DEL SERVICIO
public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder> {

    //-------------------------------------------------------------------------Variables de la clase
    private ArrayList<Elemento> elementos;
    private Context context;
    ArrayList<String> Array_Nombre = new ArrayList<>();
    ArrayList<Integer> Array_Id = new ArrayList<>();
    ArrayList<String> Array_Avatar = new ArrayList<>();
    String LatR,LatE,LongR,LongE;


    //---------------------------------------------------------------------CONSTRUCTOR DEL ADAPTADOR
    public Adaptador(ArrayList<Elemento> elementos, Context context, ArrayList<Integer> array_Id, ArrayList<String> array_Nombre, ArrayList<String> array_Avatar,String LatR,String LatE,String LongR,String LongE){

        this.context=context;
        this.elementos=elementos;
        this.Array_Id=array_Id;
        this.Array_Nombre=array_Nombre;
        this.Array_Avatar=array_Avatar;
        this.LatE=LatE;
        this.LatR=LatR;
        this.LongE=LongE;
        this.LongR=LongR;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Funcion que declara el holder donde se mostrarán los datos
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.elementos_cetre_go,viewGroup,false);
        saveListaData();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //Funcion que declara cada una de las partes que contiene un elemento como el nombre, precio y descripciones
            final Elemento lista = elementos.get(i);
        viewHolder.tv_Nombre.setText(lista.get_nombre());
        viewHolder.tv_Precio.setText(String.format("$%s", lista.get_precio()));
        viewHolder.tv_DesIz.setText(lista.get_descripcionIzquierda());
        viewHolder.tv_DesDer.setText(lista.get_DescripcionDerecha());
        //cuando se presiona algún elemento abre una nueva pantalla con los datos obtenidos de el elemento elegido
        viewHolder.ImgBtn.setOnClickListener(new View.OnClickListener() {//---------viewItemClick
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,Home_Usuario_Confirmar.class);
                Bundle bundle2 = new Bundle();
                bundle2.putInt("CategoriaID", lista.getCategoria_Id());
                bundle2.putInt("ID", lista.get_ID());
                bundle2.putString("Nombre", lista.get_nombre());
                bundle2.putString("Precio", lista.get_precio());
                bundle2.putString("porKM", lista.get_Km());
                bundle2.putString("porKG", lista.get_Kg());
                bundle2.putString("Hasta", lista.get_hasta());
                bundle2.putIntegerArrayList("AL_ID",Array_Id);
                bundle2.putStringArrayList("AL_DSC", Array_Nombre);
                bundle2.putStringArrayList("AL_Av", Array_Avatar);
                bundle2.putString("LaRECIBO",LatR);
                bundle2.putString("LongRECIBO",LongR);
                bundle2.putString("LaENTREGA",LatE);
                bundle2.putString("LongENTREGA",LongE);
                i.putExtras(bundle2);
                i.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }});
        viewHolder.btn.setOnClickListener(new View.OnClickListener() {//---------viewItemClick
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,Home_Usuario_Confirmar.class);
                Bundle bundle2 = new Bundle();
                bundle2.putInt("CategoriaID", lista.getCategoria_Id());
                bundle2.putInt("ID", lista.get_ID());
                bundle2.putString("Nombre", lista.get_nombre());
                bundle2.putString("Precio", lista.get_precio());
                bundle2.putString("porKM", lista.get_Km());
                bundle2.putString("porKG", lista.get_Kg());
                bundle2.putString("Hasta", lista.get_hasta());
                bundle2.putIntegerArrayList("AL_ID",Array_Id);
                bundle2.putStringArrayList("AL_DSC", Array_Nombre);
                bundle2.putStringArrayList("AL_Av", Array_Avatar);
                bundle2.putString("LaRECIBO",LatR);
                bundle2.putString("LongRECIBO",LongR);
                bundle2.putString("LaENTREGA",LatE);
                bundle2.putString("LongENTREGA",LongE);
                i.putExtras(bundle2);
                i.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }});
        saveListaData();
    }

    //--------------------------------------------------------------Guarda los elementos de la lista
    private void saveListaData(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Shared_Preferences",Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(elementos);
        editor.putString("lista",json);
        editor.apply();
    }
    //--------------------------------------------------Obtiene la cantidad de elementos en la lista
    @Override
    public int getItemCount() {
        return elementos.size();
    }
    //-----------------------------------------------------Clase que delcara los elemntos del objeto
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_Nombre,tv_Precio,tv_DesIz,tv_DesDer;
        Button btn;
        ImageButton ImgBtn;

        //--------------------------------------------------Se declaran los elementos de cada objeto
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            tv_Nombre=itemView.findViewById(R.id.Nombre);
            tv_Precio=itemView.findViewById(R.id.Precio);
            tv_DesIz=itemView.findViewById(R.id.Descripción_Izquierda);
            tv_DesDer=itemView.findViewById(R.id.Descripción_Derecha);
            btn=itemView.findViewById(R.id.addButton);
            ImgBtn=itemView.findViewById(R.id.Boton_de_fondo);
        }
    }
}
