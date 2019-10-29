package com.example.klassi_fe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class Register extends AppCompatActivity {


    EditText txt_name, txt_secndname, txt_email, txt_pwd;
    Button btn_reg;
    Intent intent;
    RadioButton rb1, rb2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);



        txt_name = findViewById(R.id.txt_name);
        txt_secndname = findViewById(R.id.txt_scndname);
        txt_email = findViewById(R.id.txt_pwd);
        txt_pwd = findViewById(R.id.txt_pwd);
        rb1 = findViewById(R.id.radioButton);
        rb2 = findViewById(R.id.radioButton2);

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
        if(txt_pwd.getText().toString().isEmpty()){
            txt_pwd.setError(getString(R.string.str_error));
        }
    }
}


//CREAR METODO POST