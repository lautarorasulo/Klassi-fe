package com.example.klassi_fe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterClasesPendientes extends BaseAdapter {

    Context context;
    ArrayList<ObjetoClase> clases;


    public AdapterClasesPendientes(Context c, ArrayList<ObjetoClase> p){
        this.context = c;
        this.clases = p;
    }

    @Override
    public int getCount() {
        return clases.size();
    }

    @Override
    public Object getItem(int position) {
        return clases.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.activity_clases_pendientes, null);
        TextView alumno = convertView.findViewById(R.id.clases_prf_alumno);
        TextView fecha = convertView.findViewById(R.id.clases_prf_fecha);
        TextView zona = convertView.findViewById(R.id.clases_prf_zona);
        TextView materia = convertView.findViewById(R.id.clases_prf_materia);
        Button btnAceptar = convertView.findViewById(R.id.clases_prf_btn_aceptar);
        Button btnRechazar = convertView.findViewById(R.id.clases_prf_btn_rechazar);

        final ObjetoClase clase = (ObjetoClase) getItem(position);

        alumno.setText(clase.alumno);
        fecha.setText(clase.horario);
        zona.setText(clase.zona);
        materia.setText(clase.materia);

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnRechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }
}
