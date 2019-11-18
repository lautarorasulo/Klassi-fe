package com.example.klassi_fe.profesor;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.klassi_fe.R;
import com.example.klassi_fe.adapters.AdapterClasesPendientes;
import com.example.klassi_fe.objetos.ObjetoClase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClasesPendienteDosBotones extends AppCompatActivity {

    private String DATA_URL2;
    private ListView listView;
    private String id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clases_pendiente_dos_botones);

        id = "";

        DATA_URL2 = getString(R.string.clases_a_notificar) + id;
        listView = (ListView) findViewById(R.id.lista_pendientes_dos_botones);

        invocarServicio();


    }

    private void showListView(JSONObject obj){
        try{

            ArrayList<ObjetoClase> mysClases = new ArrayList<ObjetoClase>();

            JSONArray lista = obj.optJSONArray("result");

            Log.e("RESPUESTA", "lista" + lista);


            for(int i = 0; i < lista.length(); i++){
                JSONObject json_data = lista.getJSONObject(i);
                ObjetoClase myClase = new ObjetoClase();
                myClase.setAlumno("Alumno: "+json_data.getString("alumno"));
                myClase.setHorario("Horario: "+json_data.getString("fecha") +" - "+(json_data.getString("hora")));
                myClase.setMateria("Materia: "+json_data.getString("materia"));
                myClase.setZona("Zona: "+json_data.getString("zona"));


                mysClases.add(myClase);

            }
            AdapterClasesPendientes adapterListaProfesores = new AdapterClasesPendientes(this, mysClases);
            listView.setAdapter(adapterListaProfesores);



        }catch (Exception ex){
            Toast.makeText(this, "Error carga lista: "+ex.getMessage(), Toast.LENGTH_LONG).show();
        }finally {

        }
    }

    private void invocarServicio() {

        final ProgressDialog loading = ProgressDialog.show(this, "Por favor espere...", "Actualizando datos...", false, false);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(DATA_URL2, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.dismiss();
                showListView(response);

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


}
