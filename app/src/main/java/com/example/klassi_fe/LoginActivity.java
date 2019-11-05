package com.example.klassi_fe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {


    TextView user,pass;
    Button login, register, register2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        user = (TextView) findViewById(R.id.reg_name_txt);
        pass = (TextView) findViewById(R.id.reg_mail_txt);

        login = (Button) findViewById(R.id.reg_reg_btn);
        register = (Button) findViewById(R.id.log_register_but);
        register2 = (Button) findViewById(R.id.log_register2_but3);


        login.setOnClickListener(new View.OnClickListener(){
            public void onClick( View v){
                checkLogin();
            }
        } );

        register.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hay que agregar el intent al register
                //Intent intent = new Intent(LoginActivity.this, RegisterActivity.class)
            }
        }));

        register2.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        }));



    }

    private void checkLogin() {
        //checkea el Login, verificando los datos y enviando los datos al backend
        Boolean readytosend;
        readytosend = true;
        if(user.toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Usuario Vacio",Toast.LENGTH_SHORT).show();
            readytosend = false;
        }
        if(pass.toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Password Vacio",Toast.LENGTH_SHORT).show();
            readytosend = false;
        }

        if (readytosend){
            //aca se debe ejecutar el llamado al backend
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}
