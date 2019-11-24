package com.example.klassi_fe.objetos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.stream.Stream;

public class Profesor extends ArrayList<Parcelable> implements Parcelable {
    String _id;
    String nombre;
    String apellido;
    String mail;
    String descripcion;
    Boolean premium;

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getPremium() {
        return premium;
    }

    public void setPremium(Boolean premium) {
        this.premium = premium;
    }

    public Profesor(){}


    public Profesor(Parcel in) {
        this._id = in.readString();
        this.nombre = in.readString();
        this.apellido = in.readString();
        this.mail = in.readString();
        this.descripcion = in.readString();

    }


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this._id);
        dest.writeString(this.nombre);
        dest.writeString(this.apellido);
        dest.writeString(this.mail);
        dest.writeString(this.descripcion);
    }

    public static final Parcelable.Creator<Profesor> CREATOR=
            new Parcelable.Creator<Profesor>() {
                public Profesor createFromParcel(Parcel in) {
                    return new Profesor(in);
                }

                public Profesor[] newArray(int size) {
                    return new Profesor[size];
                }
            };
}
