package com.example.klassi_fe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {


    TextView user,pass;
    Button login, register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        user = (TextView) findViewById(R.id.log_user_txt);
        pass = (TextView) findViewById(R.id.log_pass_txt);

        login = (Button) findViewById(R.id.log_login_but);
        register = (Button) findViewById(R.id.log_register_but);


        login.setOnClickListener(new View.OnClickListener(){
            public void onClick( View v){
                checkLogin();
            }
        } );

        register.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hay que agregar el intent al register
                //Intent intent = new Intent(LoginActivity.this, Register.class)
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
