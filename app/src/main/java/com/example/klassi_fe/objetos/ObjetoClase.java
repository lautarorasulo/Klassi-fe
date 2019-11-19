package com.example.klassi_fe.objetos;

public class ObjetoClase {
    public String _id;
    public String alumno;
    public String profesor;
    public String materia;
    public String zona;
    public String horario;

    public String get_id() {
        return _id;
    }

    public String getAlumno() {
        return alumno;
    }

    public String getProfesor() {
        return profesor;
    }

    public String getMateria() {
        return materia;
    }

    public String getZona() {
        return zona;
    }

    public String getHorario() {
        return horario;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setAlumno(String alumno) {
        this.alumno = alumno;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }
}
