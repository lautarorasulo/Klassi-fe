package com.example.klassi_fe.autenticacion;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.klassi_fe.HomeActivity;
import com.example.klassi_fe.R;
import com.example.klassi_fe.profesor.ProfesorHomeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private String error404 = "El usuario o password no es correcto, intente nuevamente";
    private static final String SHARED_PREF_NAME = "userID";

    private static final String KEY_NAME = "key_userID";
    private static final String KEY_NAME_ROL = "key_userROL";
    private static final String KEY_NAME_NOTIFICACION = "key_userNOTIFICACION";

    private TextView user,pass;
    private Button login, register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        user = (TextView) findViewById(R.id.reg_name_txt);
        pass = (TextView) findViewById(R.id.reg_mail_txt);

        login = (Button) findViewById(R.id.reg_reg_btn);
        register = (Button) findViewById(R.id.log_register2_but3);

        goLogin();
        goRegister();
    }

    private void goRegister(){
        register.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        }));
    }

    private void goLogin(){
        login.setOnClickListener(new View.OnClickListener(){
            public void onClick( View v){
                checkLogin();
            }});
    }

    private void checkLogin() {
        Boolean readytosend = true;
        if(user.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Usuario Vacio",Toast.LENGTH_SHORT).show();
            readytosend = false;
        } else
        if(pass.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Password Vacio",Toast.LENGTH_SHORT).show();
            readytosend = false;
        }

        if (readytosend){
            //aca se debe ejecutar el llamado al backend
            /////////////////////
                final ProgressDialog loading = ProgressDialog.show(this, "Por favor espere...",
                        "Actualizando datos...", false, false);


                StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.login), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                loading.dismiss();

                                SharedPreferences sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                String[] userID = response.split("\"");

                                editor.putString(KEY_NAME, userID[5]);
                                editor.putString(KEY_NAME_ROL, userID[9]);
                                editor.putString(KEY_NAME_NOTIFICACION, userID[12]);
                                editor.apply();

                                if( "Profesor".equals(userID[9])){
                                    Intent intent = new Intent(LoginActivity.this , ProfesorHomeActivity.class);
                                    startActivity(intent);
                                    if(":false}}".equals(userID[12])){
                                        Toast.makeText(getApplicationContext(), "Tiene clases para confirmar", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Intent intent = new Intent(LoginActivity.this ,HomeActivity.class);
                                    startActivity(intent);
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        if(error.networkResponse.statusCode == 404)
                        {
                            Toast.makeText(getApplicationContext(), error404, Toast.LENGTH_LONG).show();

                        }else{

                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                })
                {
                    protected Map<String, String> getParams(){
                        Map<String, String> logdata = new HashMap<>();
                        logdata.put("email", user.getText().toString());
                        logdata.put("password", pass.getText().toString());
                        return logdata;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
            //////////////////////
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
