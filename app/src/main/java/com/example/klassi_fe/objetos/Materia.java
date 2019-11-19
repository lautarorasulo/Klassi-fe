package com.example.klassi_fe.objetos;

public class Materia {

    Materia(String id, String nombre, String escolaridad){
        id = this.id;
        nombre = this.nombre;
        escolaridad = this.escolaridad;
    }
    public Materia (){};

    public String id;
    public String nombre;
    public String escolaridad;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEscolaridad() {
        return escolaridad;
    }

    public void setEscolaridad(String escolaridad) {
        this.escolaridad = escolaridad;
    }

}
