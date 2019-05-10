package com.mydomain.cetreexpresv2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
//**********************************************Primera clase que se ejecuta al iniciar la aplicación
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Check permissions
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
        }
    }
    //------------------------------------------Al presionar alguno de esos 2 botones
    public void alPresionarBoton(View view){
        switch(view.getId()){
            case R.id.Btn_Usuario:
                int User_ID = 1; //ID de usuario
                Intent i = new Intent(this,LoginUsuarioActivity.class);
                i.putExtra("ID", User_ID);
                startActivity(i);
                break;
            case R.id.Btn_CetrePartner:
                int CP_ID = 2;//ID de partner
                Intent in = new Intent(this,LoginUsuarioActivity.class);
                in.putExtra("ID", CP_ID);
                startActivity(in);
                break;

            default:
                break;
        }

    }
}
