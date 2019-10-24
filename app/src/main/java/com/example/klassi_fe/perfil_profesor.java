package com.example.klassi_fe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class perfil_profesor extends AppCompatActivity {

    EditText txt_p_name, txt_p_apellido, txt_p_localidad;
    Button btn_p_msg, btn_p_mts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil_profesor);
    }

    public void invocarListeners(){
        btn_p_msg.setOnClickListener();
    }
}
