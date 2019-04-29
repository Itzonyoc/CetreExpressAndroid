package com.mydomain.cetreexpresv2;


public class Usuario {

    private String _Nombre;
    private String _Numero_de_cedula;
    private String _Contrasena;
    private int _Tipo_de_usuario;

    //  CONSTRUCTOR

    public Usuario(String _Nombre, String _Numero_de_cedula, String _Contrasena, int _Tipo_de_usuario) {
        this._Nombre = _Nombre;
        this._Numero_de_cedula = _Numero_de_cedula;
        this._Contrasena = _Contrasena;
        this._Tipo_de_usuario = _Tipo_de_usuario;
    }

    //GETTERS

    public String get_Nombre() {
        return _Nombre;
    }

    public String get_Numero_de_cedula() {
        return _Numero_de_cedula;
    }

    public String get_Contrasena() {
        return _Contrasena;
    }

    public int get_Tipo_de_usuario() {
        return _Tipo_de_usuario;
    }

    //SETTERS

    public void set_Nombre(String _Nombre) {
        this._Nombre = _Nombre;
    }

    public void set_Numero_de_cedula(String _Numero_de_cedula) {
        this._Numero_de_cedula = _Numero_de_cedula;
    }

    public void set_Contrasena(String _Contrasena) {
        this._Contrasena = _Contrasena;
    }

    public void set_Tipo_de_usuario(int _Tipo_de_usuario) {
        this._Tipo_de_usuario = _Tipo_de_usuario;
    }


}
