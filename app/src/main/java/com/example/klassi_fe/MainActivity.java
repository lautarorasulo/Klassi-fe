package com.example.klassi_fe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    EditText txt_name, txt_scndname, txt_email, txt_dni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_reg;

        txt_name = findViewById(R.id.txt_name);
        txt_scndname = findViewById(R.id.txt_scndname);
        txt_email = findViewById(R.id.txt_dni);
        txt_dni = findViewById(R.id.txt_dni);

        btn_reg = findViewById(R.id.btn_reg);


        btn_reg.setOnClickListener(new View.OnClickListener()){
            @Override
                public void onClick(View v){
                validar();
            }
        }
    }

    public void validar(){
        if(txt_name.getText().toString().isEmpty()){
            txt_name.setError(getString(R.string.str_error));
        }
        if(txt_scndname.getText().toString().isEmpty()){
            txt_scndname.setError(getString(R.string.str_error));
        }
        if(txt_email.getText().toString().isEmpty()){
            txt_email.setError(getString(R.string.str_error));
        }
        if(txt_dni.getText().toString().isEmpty()){
            txt_dni.setError(getString(R.string.str_error));
        }
    }
}
