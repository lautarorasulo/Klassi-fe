package com.example.klassi_fe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register extends AppCompatActivity {


    EditText txt_name, txt_secndname, txt_email, txt_dni;
    Button btn_reg;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);



        txt_name = findViewById(R.id.txt_name);
        txt_secndname = findViewById(R.id.txt_scndname);
        txt_email = findViewById(R.id.txt_dni);
        txt_dni = findViewById(R.id.txt_dni);

        btn_reg = (Button) findViewById(R.id.btn_reg);

        invocarListeners();

    }

    public void invocarListeners(){
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                validate();
                //POST REGISTRACION
                /*  intent = new Intent(Register.this ,Login.class);
                        startActivity(intent);*/
            }
        });
    }


    public void validate(){
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


//CREAR METODO POST