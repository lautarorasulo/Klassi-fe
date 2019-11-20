package com.example.klassi_fe.autenticacion;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import com.example.klassi_fe.HomeActivity;
import com.example.klassi_fe.R;

public class RegisterActivity extends AppCompatActivity {


    EditText txt_name, txt_apellido, txt_email, txt_pwd;
    Button btn_reg;
    RadioButton rb1, rb2;
    ImageView logo;
    String tipoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);



        txt_name = findViewById(R.id.reg_name_txt);
        txt_apellido = findViewById(R.id.reg_ape_txt);
        txt_email = findViewById(R.id.reg_mail_txt);
        txt_pwd = findViewById(R.id.reg_pass_txt);
        rb1 = findViewById(R.id.reg_al_btn);
        rb2 = findViewById(R.id.reg_prof_btn);


        btn_reg = (Button) findViewById(R.id.reg_reg_btn);

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                checkRegister();
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
        if(txt_apellido.getText().toString().isEmpty()){
            txt_apellido.setError(getString(R.string.str_error));
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
        if(txt_name.getText().toString().isEmpty()){
            txt_name.setError(getString(R.string.str_error));
            readytosend = false;
        }
        if(rb1.isChecked() || rb2.isChecked()) {
            if (rb1.isChecked()) {
                tipoUsuario = "Alumno";
            } else if (rb2.isChecked()) {
                tipoUsuario = "Profesor";
            }
        }
        if (readytosend){
            //aca se debe ejecutar el llamado al backend
            registrarUser();
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        }

    }

    private void registrarUser(){
        final ProgressDialog loading = ProgressDialog.show(this, "Por favor espere...", "Actualizando datos...", false, false);

        //JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getString(R.string.postrregister), null, new Response.Listener<JSONObject>() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,  getString(R.string.postRegister), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                Log.d("Mensaje OK:", "onResponse: "+response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "Error request "+ error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<>();
            params.put("rol" , tipoUsuario);
            params.put("nombre" , txt_name.getText().toString());
            params.put("apellido" , txt_apellido.getText().toString());
            params.put("email" , txt_email.getText().toString());
            params.put("password" , txt_pwd.getText().toString());
            params.put("descripcion" , "Usuario de tipo: " + tipoUsuario);

            Log.d("AgregandoMateria", "AgregarMateria" + params);
            return params;
        }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}


