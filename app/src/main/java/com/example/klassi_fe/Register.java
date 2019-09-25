package com.example.klassi_fe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Register extends AppCompatActivity {


    EditText txt_name, txt_secndname, txt_email, txt_dni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        Button btn_reg;

        txt_name = findViewById(R.id.txt_name);
        txt_secndname = findViewById(R.id.txt_scndname);
        txt_email = findViewById(R.id.txt_dni);
        txt_dni = findViewById(R.id.txt_dni);

        btn_reg = findViewById(R.id.btn_reg);

    }


    public void validar(){
        if(txt_name.getText().toString().isEmpty()){
            txt_name.setError(getString(R.string.str_error));
        }
        if(txt_secndname.getText().toString().isEmpty()){
            txt_secndname.setError(getString(R.string.str_error));
        }
        if(txt_email.getText().toString().isEmpty()){
            txt_email.setError(getString(R.string.str_error));
        }
        if(txt_dni.getText().toString().isEmpty()){
            txt_dni.setError(getString(R.string.str_error));
        }
    }
}
