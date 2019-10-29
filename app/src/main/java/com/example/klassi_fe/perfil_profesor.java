package com.example.klassi_fe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class perfil_profesor extends AppCompatActivity {

    EditText txt_p_name, txt_p_apellido, txt_p_localidad;
    Button btn_p_msg, btn_p_mts, btn_p_horarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil_profesor);

        txt_p_name = findViewById(R.id.txt_name);
        txt_p_apellido = findViewById(R.id.txt_scndname);
        btn_p_msg = findViewById(R.id.btn_p_msg);
        btn_p_mts = findViewById(R.id.btn_p_msg);
        btn_p_horarios = findViewById(R.id.btn_horarios);

        invocarListeners();
    }

    public void invocarListeners() {
        btn_p_horarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(perfil_profesor.this, horarios.class);
                startActivity(intent);
            }
        });

    }