package com.example.klassi_fe.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.klassi_fe.R;
import com.example.klassi_fe.autenticacion.LoginActivity;
import com.example.klassi_fe.autenticacion.RegisterActivity;
import com.example.klassi_fe.clase.Confirmacion1Activity;
import com.example.klassi_fe.objetos.Profesor;

import java.util.ArrayList;

public class AdapterListaProfesores extends BaseAdapter {
    Context context;
    ArrayList<Profesor> profesores;


    public AdapterListaProfesores(Context c, ArrayList<Profesor> p){
        this.context = c;
        this.profesores = p;
    }

    @Override
    public int getCount() {
        return profesores.size();
    }

    @Override
    public Object getItem(int position) {
        return profesores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.card_profesor, null);
        TextView nombre = convertView.findViewById(R.id.nombre_profesor);
        Button ver = convertView.findViewById(R.id.btn_perfil_profesor);

        final Profesor profesor = (Profesor) getItem(position);
        nombre.setText(profesor.getNombre());


        ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(context, Confirmacion1Activity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("profesor", profesor);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
