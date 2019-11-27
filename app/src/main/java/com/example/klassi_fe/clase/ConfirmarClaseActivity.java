package com.example.klassi_fe.clase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.klassi_fe.HomeActivity;
import com.example.klassi_fe.R;
import com.example.klassi_fe.autenticacion.LoginActivity;
import com.example.klassi_fe.objetos.MenuInteracions;
import com.example.klassi_fe.objetos.Profesor;
import com.example.klassi_fe.objetos.Zona;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConfirmarClaseActivity extends AppCompatActivity {

    private MenuInteracions minteraction;
    private String userId, userRol, userNotificacion;

    private TextView nombreal,nombreprof,mailal,mailprof,lugar,hora;
    private Toolbar toolbar;
    private Button confirmar, cancelar;
    private ImageView perfilal, perfilprof;
    private Profesor myProfesor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_clase);
        myProfesor = new Profesor();
        Bundle bundle = getIntent().getExtras();
        myProfesor = bundle.getParcelable("profesor");


        minteraction = new MenuInteracions();
        SharedPreferences sp = getSharedPreferences(minteraction.SHARED_PREF_NAME, MODE_PRIVATE);
        userRol = sp.getString(minteraction.KEY_NAME_ROL, null);
        userId = sp.getString(minteraction.KEY_NAME, null);

        confirmar = (Button) findViewById(R.id.pnt_cnf2_confirmar);
        cancelar = (Button) findViewById(R.id.pnt_cnf2_cancelar);
        nombreal = (TextView) findViewById(R.id.pnt_cnf2_nomal);
        nombreprof = (TextView) findViewById(R.id.pnt_cnf2_nomprof);
        mailal = (TextView) findViewById(R.id.pnt_cnf2_apellido_al);
        mailprof = (TextView) findViewById(R.id.pnt_cnf2_apellido_prof);
        lugar = (TextView) findViewById(R.id.pnt_cnf2_lugar);
        hora = (TextView) findViewById(R.id.pnt_cnf2_hora);
        perfilprof = (ImageView) findViewById(R.id.pnt_cnf2_imgprof);
        perfilal = (ImageView) findViewById(R.id.pnt_cnf2_imgal);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.maintoolbar);
        setSupportActionBar(toolbar);

        buscarDatosAlumno();
        cargoDatosClase();
        Confirmar();
        Cancelar();
    }

    private void buscarDatosAlumno() {
        final ProgressDialog loading = ProgressDialog.show(this, "Por favor espere...", "Actualizando datos...", false, false);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(getString(R.string.getProfesor) + userId, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.dismiss();
                try {
                    mapearDatos(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "Error request "+ error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    public void mapearDatos(JSONObject user) throws JSONException {
        nombreal.setText(user.optJSONObject("result").getString("nombre"));
        mailal.setText(user.optJSONObject("result").getString("email"));
        nombreprof.setText(myProfesor.getNombre());
        mailprof.setText(myProfesor.getMail());
    }

    private void CargoPerfiles() {
        //en este metodo voy a hacer la llamada a la API para cargar los perfiles cargado en el backend
        //una vez cargado, voy a reflejarlo en los Textview
        //Tambien voy a buscar si esta la imagen del usuario grabada en el dispositivo. en caso
        //que este se va a poner como imagen de perfil, en caso que no, se mostraravacio.

        //busco Imagen en File system
        File file;
        file = getFilesDir();

        //String imagepath = getFilesDir() + "/imagen"+123+".jpg";

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        // Bitmap imagenperfil = BitmapFactory.decodeFile(imagepath,options);

        // if(imagenperfil != null ){
        //     perfilal.setImageBitmap(imagenperfil);
        // }

        //falta traer imagen del profesor en el negocio
    }

    private void cargoDatosClase(){
        //aca se carga hora y lugar seteados por el usuario al momento de elegir una clase
        hora.setText(ProfesoresActivity.hora + " " + ProfesoresActivity.fecha);
        lugar.setText(ProfesoresActivity.zona);
    }

    private void postClase(){
        final ProgressDialog loading = ProgressDialog.show(this, "Por favor espere...", "Actualizando datos...", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.generarClase), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                Intent intent = new Intent(ConfirmarClaseActivity.this , HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "Error request "+ error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            protected Map<String, String> getParams() {
                Map<java.lang.String, java.lang.String> params = new HashMap<>();
                params.put("idAlumno", userId);
                params.put("idProfesor", myProfesor.getId());
                params.put("idMateria", ProfesoresActivity.idMateria);
                params.put("idZona", ProfesoresActivity.idZona);
                params.put("fecha", ProfesoresActivity.fecha);
                params.put("hora", ProfesoresActivity.hora);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void Confirmar(){
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postClase();
            }
        });
    }

    private void Cancelar(){
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmarClaseActivity.this , BusquedaActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_User:
                minteraction.irPerfi(this.getLocalClassName(),this, userRol);
                break;
            case R.id.menu_notifications:
                minteraction.irClasesPendientes(this, userRol);
                break;
            case R.id.menu_share:
                minteraction.hacerShare("Shareado desde perfil alumnno",this);
                break;
            case R.id.menu_aboutUs:
                minteraction.mostrarAboutUs("",this);
                break;
            case R.id.menu_home:
                minteraction.goToHome(this,userRol);
                break;
            case R.id.menu_logout:
                Intent intent = new Intent(ConfirmarClaseActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
