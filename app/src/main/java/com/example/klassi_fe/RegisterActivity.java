package com.example.klassi_fe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.io.File;

public class RegisterActivity extends AppCompatActivity {


    EditText txt_name,txt_email, txt_pwd;
    Button btn_reg;
    Intent intent;
    RadioButton rb1, rb2;
    ImageView logo;
    String tipoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);



        txt_name = findViewById(R.id.reg_name_txt);
        txt_email = findViewById(R.id.reg_mail_txt);
        txt_pwd = findViewById(R.id.reg_pass_txt);
        rb1 = findViewById(R.id.reg_al_btn);
        rb2 = findViewById(R.id.reg_prof_btn);


        btn_reg = (Button) findViewById(R.id.reg_reg_btn);

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                checkRegister();
                //maestroProfesor();
                // intent = new Intent(RegisterActivity.this , PerfilProfesorActivity.class);
                // startActivity(intent);

            }
        });
    }





    public void maestroProfesor(){
        if (rb1.isChecked()){
            tipoUsuario = "Alumno";
        }
        else if(rb2.isChecked()){
            tipoUsuario = "Profesor";
        }
    }


    private void checkRegister() {
        //checkea el registro, verificando los datos y enviando los datos al backend
        Boolean readytosend;
        readytosend = true;
        if(txt_name.getText().toString().isEmpty()){
            txt_name.setError(getString(R.string.str_error));
            readytosend = false;
        }
        if(txt_email.getText().toString().isEmpty()){
            txt_email.setError(getString(R.string.str_error));
            readytosend = false;
        }
        if(txt_pwd.getText().toString().isEmpty()){
            txt_pwd.setError(getString(R.string.str_error));
            readytosend = false;
        }

        if (readytosend){
            //aca se debe ejecutar el llamado al backend
            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
            startActivity(intent);
        }

    }
}


//CREAR METODO POST