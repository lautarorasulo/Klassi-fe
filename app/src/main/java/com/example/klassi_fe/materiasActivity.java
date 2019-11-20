package com.example.klassi_fe;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.klassi_fe.objetos.Materia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class materiasActivity extends AppCompatActivity {
    Materia materia, materiaAsig, materiaAgregar;
    List<Materia> materias;
    ListView materiasList;
    Button agregar;
    Spinner materiaSpinner, escolaridadSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materias);


        materiasList = (ListView) findViewById(R.id.materias_list);
        agregar = (Button) findViewById(R.id.button_agregar);
        materiaSpinner = (Spinner) findViewById(R.id.spinner_materia);
        escolaridadSpinner = (Spinner) findViewById(R.id.spinner_escolaridad);

        rellenarSpinners();
        materiasAsignadas("aca la url de materia del profesor");
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarMateria();
            }
        });
    }

    private void rellenarSpinners() {
        //aca se tiene que llamar al backend para poder buscar las opciones disponibles que se encuentran
        // para rellenar el spinner

        invocarServicio(getString(R.string.getMaterias));


        // el siguiente codigo es Filler y solo ejemplo de como se deberia rellenar el spinner
        // Spinner Drop down elements
        List<String> escolaridad = new ArrayList<String>();
        escolaridad.add("Primaria");
        escolaridad.add("Secundaria");
        escolaridad.add("Terciario");

        ArrayAdapter<String> adapterescolaridad =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, escolaridad);

        adapterescolaridad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        escolaridadSpinner.setAdapter(adapterescolaridad);
    }


    private void invocarServicio(String dataurl) {

        final ProgressDialog loading = ProgressDialog.show(this, "Por favor espere...", "Actualizando datos...", false, false);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(dataurl, null, new Response.Listener<JSONObject>() {
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

    private void showListView(JSONObject obj){
        try{

            List<String> materiastring = new ArrayList<String>();


            JSONArray lista = obj.optJSONArray("result");

            Log.e("RESPUESTA", "lista" + lista);

            JSONObject json_data2 = lista.getJSONObject(0);
            //evaluo si es Zona o Materias

            Log.d("prueba esco", "showListView: "+json_data2.length());


                for(int i = 0; i < lista.length(); i++){
                    JSONObject json_data = lista.getJSONObject(i);

                    materia = new Materia(json_data.getString("_id"),
                            json_data.getString("nombre"),"primaria");

                    materiastring.add(json_data.getString("nombre"));
                    materias.add(materia);


                }
                ArrayAdapter<String> adaptermaterias =
                        new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, materiastring);

                adaptermaterias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                materiaSpinner.setAdapter(adaptermaterias);


        }catch (Exception ex){
            Toast.makeText(this, "Error carga lista: "+ex.getMessage(), Toast.LENGTH_LONG).show();
        }finally {

        }
    }

    private void materiasAsignadas(String url) {
        final ProgressDialog loading = ProgressDialog.show(this, "Por favor espere...", "Actualizando datos...", false, false);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.dismiss();
                try {
                    cargarMaterias(response);
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

    private void cargarMaterias(JSONObject objeto) throws JSONException {
        List<String> materiasAsignadas = new ArrayList<String>();


        JSONArray lista = objeto.optJSONArray("result");

        Log.e("RESPUESTA", "lista" + lista);

        JSONObject json_data2 = lista.getJSONObject(0);


        for(int i = 0; i < lista.length(); i++){
            JSONObject json_data = lista.getJSONObject(i);

            materiaAsig = new Materia(json_data.getString("_id"),
                    json_data.getString("nombre"),"primaria");

            materiasAsignadas.add(json_data.getString("nombre"));
            materias.add(materiaAsig);


        }
        ArrayAdapter<String> adaptermaterias =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, materiasAsignadas);

        materiaSpinner.setAdapter(adaptermaterias);
    }

    private void agregarMateria(){
        int posM, posE;
        final String valormateria,valorescolaridad;

        posM = materiaSpinner.getSelectedItemPosition();
        posE = escolaridadSpinner.getSelectedItemPosition();
        valormateria = materiaSpinner.getItemAtPosition(posM).toString();
        valorescolaridad = escolaridadSpinner.getItemAtPosition(posE).toString();
        materiaAgregar.setNombre(valormateria);
        materiaAgregar.setEscolaridad(valorescolaridad);



        final ProgressDialog loading = ProgressDialog.show(this, "Por favor espere...", "Actualizando datos...", false, false);

        //JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getString(R.string.postClase), null, new Response.Listener<JSONObject>() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.postClase), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                Log.d("asdasd", "onResponse: "+response);
                materiasAsignadas("string de materias del profesor");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "Error request "+ error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){protected Map<String, String> getParams() {
            //buscarIdZona((ArrayList<Zona>) myZonas, valorzona)
            //buscarId((ArrayList<Materia>) materias, valormateria )
            Map<String, String> params = new HashMap<>();
            //params.put(materiaAgregar);
            //aca ver con Santi como enviar la materia agregada
            Log.d("AgregandoMateria", "AgregarMateria" + params);
            return params;
        }
        };



        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
