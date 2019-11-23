package com.example.klassi_fe;

import android.app.ProgressDialog;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.klassi_fe.clase.BusquedaActivity;
import com.example.klassi_fe.objetos.MenuInteracions;
import com.example.klassi_fe.objetos.ObjetoClase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    MenuInteracions minteraction;
    private String userId, userRol, userNotificacion;

    private Toolbar toolbar;
    private LinearLayout clasesNotificadas;
    private ArrayList<ObjetoClase> mysClases;
    Button botonbusqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        minteraction = new MenuInteracions();

        clasesNotificadas = (LinearLayout) findViewById(R.id.home_clases_notificadas);
        mysClases = new ArrayList<ObjetoClase>();

        SharedPreferences sp = getSharedPreferences(minteraction.SHARED_PREF_NAME, MODE_PRIVATE);
        userRol = sp.getString(minteraction.KEY_NAME_ROL, null);
        userId = sp.getString(minteraction.KEY_NAME, null);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.maintoolbar);
        setSupportActionBar(toolbar);

        botonbusqueda = (Button) findViewById(R.id.home_btn_Klassi);

        fillClasesOnTable();

        botonbusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, BusquedaActivity.class);
                startActivity(intent);
            }
        });



    }

    private void CargarLinerLayout(){
        Log.d("CargarLinerLayout", " = " + mysClases.size());
        LayoutInflater inflater = LayoutInflater.from(this);
        if(mysClases.size() < 3){
            for (int i = 0; i < mysClases.size(); i++) {
                ObjetoClase clase = mysClases.get(i);
                View view  = inflater.inflate(R.layout.clases_notificadas_linear_horizontal, clasesNotificadas, false);
                TextView nombreProfesor = view.findViewById(R.id.nombre_profesor);
                TextView zona = view.findViewById(R.id.zona);
                TextView fechaHora = view.findViewById(R.id.fecha);

                nombreProfesor.setText(clase.getProfesor());
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

                nombreProfesor.setText(clase.getProfesor());
                zona.setText(clase.getZona());
                fechaHora.setText(clase.getHorario());
                clasesNotificadas.addView(view);
            }
        }

    }
    private void fillClasesOnTable() {

        final ProgressDialog loading = ProgressDialog.show(this, "Por favor espere...", "Actualizando datos...", false, false);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://192.168.100.116:3001/api/getClasesNotificadas/5dc5947ef011a50394553f9b", null, new Response.Listener<JSONObject>() {
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

        //voy a buscar en el backend si existen clases diponibles, en caso que existan voy a llenar
        //el Table layout con las clases del alumno.




        /*tablerow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        TextView txt = new TextView(this);
        txt.setText("dasdfadf, asdfasdfasdf");
        txt.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tablerow.addView(txt);

        table.addView(tablerow,new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));*/
    }

    private void showListView(JSONObject obj) throws JSONException {
        Log.d("showListView", " = " +obj);
        ObjetoClase myClase;
        JSONArray lista = obj.optJSONArray("result");
        for(int i = 0; i < lista.length(); i++){
            myClase = new ObjetoClase();
            JSONObject json_data = lista.getJSONObject(i);
            myClase.setProfesor(json_data.getString("profesor"));
            myClase.setHorario(json_data.getString("fecha") + " - " + json_data.getString("hora"));
            myClase.setZona(json_data.getString("zona"));
            mysClases.add(myClase);
        }

        CargarLinerLayout();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }



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

                break;
            case R.id.menu_share:
                minteraction.hacerShare("Shareado desde perfil alumnno",this);
                break;
            case R.id.menu_aboutUs:
                minteraction.mostrarAboutUs("",this);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
