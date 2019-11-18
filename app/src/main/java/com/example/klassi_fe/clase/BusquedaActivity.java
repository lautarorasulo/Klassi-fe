package com.example.klassi_fe.clase;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.klassi_fe.R;
import com.example.klassi_fe.objetos.Materia;
import com.example.klassi_fe.objetos.MenuInteracions;
import com.example.klassi_fe.objetos.ObjetoClase;
import com.example.klassi_fe.objetos.Profesor;
import com.example.klassi_fe.objetos.Zona;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BusquedaActivity extends AppCompatActivity {

    MenuInteracions minteraction;

    Toolbar toolbar;

    Materia materia;
    List<Materia> materias;
    List<Zona> myZonas;
    //List<Profesor> myProfesores;
    ArrayList<Profesor> mysProfesores;



    Spinner spinnermateria,spinnerescolaridad,spinnerzona, spinnerhora;
    Button busqueda;

    TextView prueba, datepicker;

    private int mYear;
    private int mMonth;
    private int mDay;
    private TextView txtFecha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);

        minteraction = new MenuInteracions();

        materias = new ArrayList<Materia>();
        myZonas = new ArrayList<Zona>();

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.maintoolbar);
        setSupportActionBar(toolbar);

        busqueda = (Button) findViewById(R.id.busq_btn_search);
        datepicker = (TextView) findViewById(R.id.busq_btn_datepicker);

        spinnermateria = (Spinner) findViewById(R.id.busq_spinner_materia);
        spinnerescolaridad = (Spinner) findViewById(R.id.busq_spinner_escolaridad);
        spinnerzona = (Spinner) findViewById(R.id.busq_spinner_zona);
        spinnerhora = (Spinner) findViewById(R.id.busq_spinner_hora);

        prueba = (TextView) findViewById(R.id.busq_nomod_welcome);
        txtFecha = (TextView) findViewById(R.id.busq_txtd_date);

        rellenarSpinners();


        prueba.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                return false;
            }
        });
        busqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarProfesor();
            }
        });

        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalendar();
            }
        });
    }

    private void openCalendar() {
        Calendar mcurrentDate = Calendar.getInstance();
        mYear = mcurrentDate.get(Calendar.YEAR);
        mMonth = mcurrentDate.get(Calendar.MONTH);
        mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                Calendar myCalendar = Calendar.getInstance();
                myCalendar.set(Calendar.YEAR, selectedyear);
                myCalendar.set(Calendar.MONTH, selectedmonth);
                myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                String myFormat = "dd/MM/yy"; //Change as you need
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
                txtFecha.setText(sdf.format(myCalendar.getTime()));

                mDay = selectedday;
                mMonth = selectedmonth;
                mYear = selectedyear;
            }
        }, mYear, mMonth, mDay);
        //mDatePicker.setTitle("Select date");
        mDatePicker.show();

    }

    private void buscarProfesor() {
        //se va  a ejecutar la busqueda con los items para poder encontrar los profesores, el llamado
        //al backend se va a realizar en este momento para poder recibir la respuesta con los datos
        //y luego directamente listar. igualmente hay que ver cuan factible es esto.

        int pos;
        final String valormateria,valorescolaridad, valorzona, valorhora,valorfecha;
        String valmat, valmatloop="";

        pos = spinnerescolaridad.getSelectedItemPosition();
        valorescolaridad = spinnerescolaridad.getItemAtPosition(pos).toString();


        pos = spinnermateria.getSelectedItemPosition();
        valmat = spinnermateria.getItemAtPosition(pos).toString();

        for(int j=0; j < materias.size(); j++){

            if(materias.get(j).getNombre().equals(valmat) &&
                    materias.get(j).getEscolaridad().equals(valorescolaridad)){
                valmatloop = materias.get(j).getId();
            }
        }

        valormateria = valmatloop;

        pos = spinnerzona.getSelectedItemPosition();
        valorzona = spinnerzona.getItemAtPosition(pos).toString();

        pos = spinnerhora.getSelectedItemPosition();
        valorhora = spinnerhora.getItemAtPosition(pos).toString();

        valorfecha = txtFecha.getText().toString();


        final ProgressDialog loading = ProgressDialog.show(this, "Por favor espere...", "Actualizando datos...", false, false);

        //JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getString(R.string.postClase), null, new Response.Listener<JSONObject>() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.postClase), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                Log.d("asdasd", "onResponse: "+response);
                try {
                    prepararProfesores(response);
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

                params.put("idZona",buscarIdZona((ArrayList<Zona>) myZonas, valorzona));
                params.put("idMateria", valormateria);
                params.put("fecha", valorfecha);
                params.put("hora", valorhora);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void prepararProfesores(String respuesta) throws JSONException {

        mysProfesores = new ArrayList<Profesor>();

        JSONObject jsonObject = new JSONObject(respuesta);

        JSONArray lista = jsonObject.optJSONArray("result");

        for(int i = 0; i < lista.length(); i++){
            Profesor myProfesor = new Profesor();
            JSONObject json_data = lista.getJSONObject(i);
            myProfesor.setNombre(json_data.getString("nombre"));
            myProfesor.setApellido(json_data.getString("apellido"));
            myProfesor.setMail(json_data.getString("email"));
            myProfesor.setDescripcion(json_data.getString("descripcion"));
            myProfesor.setPremium(json_data.getBoolean("premiun"));

            mysProfesores.add(myProfesor);
        }


        cambiarActivity();


    }

    private void cambiarActivity(){
        Intent intent = new Intent(BusquedaActivity.this, ProfesoresActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("ListaProfesores", mysProfesores);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    public String buscarIdZona(ArrayList<Zona> myArray, String zonaSeleccionada ){
        boolean encontro=true;
        int i=0;

        while(i < myArray.size()){

            if(myArray.get(i).getNombre().equals(zonaSeleccionada)){
                return myArray.get(i).get_id();
            }else{
                i++;
            }
        }
        return null;
    }


    private void rellenarSpinners() {
        //aca se tiene que llamar al backend para poder buscar las opciones disponibles que se encuentran
        // para rellenar el spinner

        invocarServicio(getString(R.string.getMaterias));
        invocarServicio(getString(R.string.getZonas));


        // el siguiente codigo es Filler y solo ejemplo de como se deberia rellenar el spinner
        // Spinner Drop down elements
        List<String> escolaridad = new ArrayList<String>();
        escolaridad.add("Primario");
        escolaridad.add("Secundario");
        escolaridad.add("Terciario");

        ArrayAdapter<String> adapterescolaridad =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, escolaridad);

        adapterescolaridad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerescolaridad.setAdapter(adapterescolaridad);

        List<String> horarios = new ArrayList<String>();

        horarios.add("08");
        horarios.add("09");
        horarios.add("10");
        horarios.add("11");
        horarios.add("12");
        horarios.add("13");
        horarios.add("14");
        horarios.add("15");
        horarios.add("16");
        horarios.add("17");
        horarios.add("18");
        horarios.add("19");


        ArrayAdapter<String> adapterhorarios =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, horarios);

        adapterhorarios.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerhora.setAdapter(adapterhorarios);

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

            List<String> zonas = new ArrayList<String>();
            List<String> idzonas = new ArrayList<String>();
            List<String> materiastring = new ArrayList<String>();
            HashSet<String> hashmateria = new HashSet<String>();



            JSONArray lista = obj.optJSONArray("result");

            JSONObject json_data2 = lista.getJSONObject(0);
            //evaluo si es Zona o Materias

            if(json_data2.length() <= 3){
                for(int i = 0; i < lista.length(); i++){
                    JSONObject json_data = lista.getJSONObject(i);
                    Zona myZona = new Zona();
                    myZona.setNombre(json_data.getString("nombre"));
                    myZona.set_id(json_data.getString("_id"));
                    myZonas.add(myZona);
                    zonas.add(json_data.getString("nombre"));
                    idzonas.add(json_data.getString("_id"));
                }
                ArrayAdapter<String> adapterzonas =
                        new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, zonas);

                adapterzonas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinnerzona.setAdapter(adapterzonas);

            }else{
                for(int i = 0; i < lista.length(); i++){
                    JSONObject json_data = lista.getJSONObject(i);

                    materia = new Materia();
                    materia.setNombre((json_data.getString("nombre")));
                    materia.setId(json_data.getString("_id"));
                    json_data2 = json_data.getJSONObject("escolaridad");

                    materia.setEscolaridad(json_data2.getString("nivel"));

                    materiastring.add(json_data.getString("nombre"));
                    materias.add(materia);


                }

                hashmateria.addAll(materiastring);
                materiastring.clear();
                materiastring.addAll(hashmateria);

                ArrayAdapter<String> adaptermaterias =
                        new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, materiastring);

                adaptermaterias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinnermateria.setAdapter(adaptermaterias);
            }

        }catch (Exception ex){
            Toast.makeText(this, "Error carga lista: "+ex.getMessage(), Toast.LENGTH_LONG).show();
        }finally {

        }
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
                minteraction.irPerfi(this.getLocalClassName(),this);
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

