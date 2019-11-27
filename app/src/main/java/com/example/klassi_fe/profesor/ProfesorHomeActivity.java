package com.example.klassi_fe.profesor;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.example.klassi_fe.objetos.ObjetoClase;
import com.example.klassi_fe.objetos.Zona;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfesorHomeActivity extends AppCompatActivity {

    private MenuInteracions minteraction;
    private String userId, userRol, userNotificacion;
    private Toolbar toolbar;
    private LinearLayout clasesNotificadas, linearMaterias, linearZonas;
    private Button addZonas;
    private ArrayList<ObjetoClase> mysClases;
    JSONArray materias;
    JSONArray zonas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesor_home);

        minteraction = new MenuInteracions();
        SharedPreferences sp = getSharedPreferences(minteraction.SHARED_PREF_NAME, MODE_PRIVATE);
        userRol = sp.getString(minteraction.KEY_NAME_ROL, null);
        userId = sp.getString(minteraction.KEY_NAME, null);
        userNotificacion = sp.getString(minteraction.KEY_NAME_NOTIFICACION, null);
        addZonas = (Button) findViewById(R.id.add_zona);

        clasesNotificadas = (LinearLayout) findViewById(R.id.profesor_home_clases_notificadas);
        linearMaterias = (LinearLayout) findViewById(R.id.layout_materias);
        linearZonas = (LinearLayout) findViewById(R.id.layout_zonas);
        mysClases = new ArrayList<ObjetoClase>();
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.maintoolbar_home_profesor);
        setSupportActionBar(toolbar);

        fillClasesOnTable();
        buscarInformacionAdicional();
        agregarZona();
    }

    private void agregarZona(){
        addZonas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    agregarZonaDialog ();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void agregarZonaDialog() throws JSONException {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ProfesorHomeActivity.this, R.style.DialogTheme);

        final ArrayList<String> agregarZona = new ArrayList<String>();

        final String[] myZona = new String[]{
                "Caballito",
                "Palermo",
                "Belgrano",
                "Colegiales",
                "Saavedra"
        };

        final boolean[] checkedZona = new boolean[]{
                false,
                false,
                false,
                false,
                false
        };

        for(int i = 0; i < myZona.length; i++){
            int j = 0;
            boolean flag = true;
            //profesorMaterias.optJSONObject(i).getString("nombre");
            while( j < zonas.length() && flag ){
                if(myZona[i].equals(zonas.getJSONObject(j).getString("nombre"))){
                    checkedZona[i] = true;
                    flag = false;
                } else {
                    j++;
                }
            }
        }

        final List<String> zonaList = Arrays.asList(myZona);

        builder.setMultiChoiceItems(myZona, checkedZona, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                // Update de los estados de cada horario
                checkedZona[which] = isChecked;

                // Get the current focused item
                String currentItem = zonaList.get(which);
                if(checkedZona[which]){
                    agregarZona.add(currentItem);
                } else {

                }
            }
        });

        // Setear si es cancelable o no
        builder.setCancelable(true);

        // Titulo del dialog
        builder.setTitle("Seleccionar Escolaridad");

        // Boton guardar
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for(int i = 0; i < agregarZona.size(); i++){
                    agregarZonaPost (agregarZona.get(i));
                }
            /*    for(int j = 0; j < deleteEscolaridad.size(); j++){
                    postMateria(materia, deleteEscolaridad.get(j), false);
                }*/
            }
        });
        // Boton cancelar
        builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when click the neutral button
            }
        });
        AlertDialog dialog = builder.create();
        // Display the alert dialog on interface
        dialog.show();


    }

    private void agregarZonaPost (final String nombreZona){
        final ProgressDialog loading = ProgressDialog.show(this, "Por favor espere...", "Actualizando datos...", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.addZona), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                try {
                    zonas = new JSONObject(response).optJSONObject("result").getJSONArray("zonas");
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
        }){
            protected Map<String, String> getParams() {
                Map<java.lang.String, java.lang.String> params = new HashMap<>();
                params.put("zona", nombreZona);
                params.put("idProfesor", userId);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void cargarLinearZonas() throws JSONException {
        LayoutInflater inflater = LayoutInflater.from(this);
        for(int i = 0; i < zonas.length(); i++){
            String myZona = zonas.optJSONObject(i).getString("nombre");
            View view  = inflater.inflate(R.layout.simple_layout, linearZonas, false);
            TextView detallesProfesor = view.findViewById(R.id.detalles_profesor);

            detallesProfesor.setText(myZona);
            linearZonas.addView(view);
        }
    }

    private void cargarLinearMaterias() throws JSONException {
        LayoutInflater inflater = LayoutInflater.from(this);
        for(int i = 0; i < materias.length(); i++){
            String myMateria = materias.optJSONObject(i).getString("nombre") + " - " + materias.optJSONObject(i).getString("escolaridad");
            View view  = inflater.inflate(R.layout.simple_layout, linearMaterias, false);
            TextView detallesProfesor = view.findViewById(R.id.detalles_profesor);

            detallesProfesor.setText(myMateria);
            linearMaterias.addView(view);
        }
    }

    private void CargarLinerLayout(){
        int i = 0;

        LayoutInflater inflater = LayoutInflater.from(this);

        while ( i < mysClases.size() && i < 3 ){
            ObjetoClase clase = mysClases.get(i);
            View view  = inflater.inflate(R.layout.clases_notificadas_linear_horizontal, clasesNotificadas, false);
            TextView nombreProfesor = view.findViewById(R.id.nombre_profesor);
            TextView zona = view.findViewById(R.id.zona);
            TextView fechaHora = view.findViewById(R.id.fecha);

            nombreProfesor.setText(clase.getAlumno());
            zona.setText(clase.getZona());
            fechaHora.setText(clase.getHorario());
            clasesNotificadas.addView(view);
            i++;
        }
      /*
        if(mysClases.size() < 3){
            for (int i = 0; i < mysClases.size(); i++) {
                ObjetoClase clase = mysClases.get(i);
                View view  = inflater.inflate(R.layout.clases_notificadas_linear_horizontal, clasesNotificadas, false);
                TextView nombreProfesor = view.findViewById(R.id.nombre_profesor);
                TextView zona = view.findViewById(R.id.zona);
                TextView fechaHora = view.findViewById(R.id.fecha);

                nombreProfesor.setText(clase.getAlumno());
                zona.setText(clase.getZona());
                fechaHora.setText(clase.getHorario());
                clasesNotificadas.addView(view);
            }
        } else {
            for (int i = 1; i < 4; i++) {
                ObjetoClase clase = mysClases.get(mysClases.size() - i);
                View view  = inflater.inflate(R.layout.clases_notificadas_linear_horizontal, clasesNotificadas, false);
                TextView nombreProfesor = view.findViewById(R.id.nombre_profesor);
                TextView zona = view.findViewById(R.id.zona);
                TextView fechaHora = view.findViewById(R.id.fecha);

                nombreProfesor.setText(clase.getAlumno());
                zona.setText(clase.getZona());
                fechaHora.setText(clase.getHorario());
                clasesNotificadas.addView(view);
            }
        }*/
    }

    private void showListView(JSONObject obj) throws JSONException {
        ObjetoClase myClase;
        JSONArray lista = obj.optJSONArray("result");
        for(int i = 0; i < lista.length(); i++){
            myClase = new ObjetoClase();
            JSONObject json_data = lista.getJSONObject(i);
            myClase.setAlumno(json_data.getString("alumno"));
            myClase.setHorario(json_data.getString("fecha") + " - " + json_data.getString("hora"));
            myClase.setZona(json_data.getString("zona"));
            mysClases.add(myClase);
        }
        CargarLinerLayout();
    }

    private void parseInfoAdicional(JSONObject response) throws JSONException {
        materias = response.optJSONObject("result").getJSONArray("materias");
        zonas = response.optJSONObject("result").getJSONArray("zonas");
        cargarLinearMaterias();
        cargarLinearZonas();
    }

    private void buscarInformacionAdicional(){
        final ProgressDialog loading = ProgressDialog.show(this, "Por favor espere...", "Actualizando datos...", false, false);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(getString(R.string.informacionAdicional) + userId, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.dismiss();
                try {
                    parseInfoAdicional(response);
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

    private void fillClasesOnTable() {

        final ProgressDialog loading = ProgressDialog.show(this, "Por favor espere...", "Actualizando datos...", false, false);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(getString(R.string.clasesNotificadas) + userId, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.dismiss();
                try {
                    showListView(response);
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


    @Override
    protected void onPause() {
        super.onPause();
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
                // minteraction.goToHome(this,userRol);
                break;
            case R.id.menu_logout:
                Intent intent = new Intent(ProfesorHomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
