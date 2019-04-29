package com.mydomain.cetreexpresv2;
import java.io.Serializable;

public class MenuElementos implements Serializable{

    private int ID, Estatus_Id;
    private String urlAvatar,Dsc;


    public MenuElementos(int id, String dsc, String urlAvatar, int estatus_id) {
        this.ID=id;
        this.Dsc=dsc;
        this.urlAvatar=urlAvatar;
        this.Estatus_Id=estatus_id;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getEstatus_Id() {
        return Estatus_Id;
    }

    public void setEstatus_Id(int estatus_Id) {
        Estatus_Id = estatus_Id;
    }

    public String getUrlAvatar() {
        return urlAvatar;
    }

    public void setUrlAvatar(String urlAvatar) {
        this.urlAvatar = urlAvatar;
    }

    public String getDsc() {
        return Dsc;
    }

    public void setDsc(String dsc) {
        Dsc = dsc;
    }

    @Override
    public String toString() {
        return "MenuElementos{" +
                "ID=" + ID +
                ", Estatus_Id=" + Estatus_Id +
                ", urlAvatar='" + urlAvatar + '\'' +
                ", Dsc='" + Dsc + '\'' +
                '}';
    }
}
