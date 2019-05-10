package com.mydomain.cetreexpresv2;


//************************************************************CLASE SIN USO******************************************************************


//Se descartó esta pantalla por parte del comprador.
//Esta clase añadia los datos de cada elemento de la lista


//************************************************************CLASE SIN USO******************************************************************



public class Elemento {
    private String _nombre,_descripcionIzquierda,_DescripcionDerecha,_precio,_Km,_Kg,_hasta,_Msj;
    private Integer _ID,_status_id,categoria_Id;

    public Elemento(String _nombre, String _descripcionIzquierda, String _DescripcionDerecha, String _precio,Integer ID,String Km, String Kg,String hasta,Integer status_id,String msj,int categoria_Id) {
        this._nombre = _nombre;
        this._descripcionIzquierda = _descripcionIzquierda;
        this._DescripcionDerecha = _DescripcionDerecha;
        this._precio = _precio;
        this._Kg=Kg;
        this._Km=Km;
        this._ID=ID;
        this._hasta=hasta;
        this._status_id=status_id;
        this._Msj=msj;
        this.categoria_Id=categoria_Id;
    }

    public Integer getCategoria_Id() {
        return categoria_Id;
    }

    public void setCategoria_Id(Integer categoria_Id) {
        this.categoria_Id = categoria_Id;
    }

    public String get_nombre() {
        return _nombre;
    }

    public void set_nombre(String _nombre) {
        this._nombre = _nombre;
    }

    public String get_descripcionIzquierda() {
        return _descripcionIzquierda;
    }

    public void set_descripcionIzquierda(String _descripcionIzquierda) {
        this._descripcionIzquierda = _descripcionIzquierda;
    }

    public String get_DescripcionDerecha() {
        return _DescripcionDerecha;
    }

    public void set_DescripcionDerecha(String _DescripcionDerecha) {
        this._DescripcionDerecha = _DescripcionDerecha;
    }

    public String get_precio() {
        return _precio;
    }

    public void set_precio(String _precio) {
        this._precio = _precio;
    }

    public String get_Km() { return _Km; }

    public void set_Km(String _Km) { this._Km = _Km; }

    public String get_Kg() { return _Kg; }

    public void set_Kg(String _Kg) { this._Kg = _Kg; }

    public String get_hasta() { return _hasta; }

    public void set_hasta(String _hasta) { this._hasta = _hasta; }

    public String get_Msj() { return _Msj; }

    public void set_Msj(String _Msj) { this._Msj = _Msj; }

    public Integer get_ID() { return _ID; }

    public void set_ID(Integer _ID) { this._ID = _ID; }

    public Integer get_status_id() { return _status_id; }

    public void set_status_id(Integer _status_id) { this._status_id = _status_id; }

    @Override
    public String toString() {
        return "Elemento{" +
                "_nombre='" + _nombre + '\'' +
                ", _descripcionIzquierda='" + _descripcionIzquierda + '\'' +
                ", _DescripcionDerecha='" + _DescripcionDerecha + '\'' +
                ", _precio='" + _precio + '\'' +
                ", _Km='" + _Km + '\'' +
                ", _Kg='" + _Kg + '\'' +
                ", _hasta='" + _hasta + '\'' +
                ", _Msj='" + _Msj + '\'' +
                ", _ID=" + _ID +
                ", _status_id=" + _status_id +
                ", categoria_Id=" + categoria_Id +
                '}';
    }
}
