package com.example.klassi_fe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class confirmacion1 extends AppCompatActivity {

    TextView txt_nombre, txt_apellido, txt_localidad;
    Button btn_confirmacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacion1);

        txt_nombre = findViewById(R.id.p_cnf1_nombre);
        txt_apellido = findViewById(R.id.p_cnf1_apellido);
        txt_localidad = findViewById(R.id.p_cnf1_localidad);
        btn_confirmacion = findViewById(R.id.btn_cnf1);

        invocarListeners();
    }

    public void invocarListeners(){
        btn_confirmacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(confirmacion1.this, confirmacion2.class);
                startActivity(intent);
            }
        });
    }
}
