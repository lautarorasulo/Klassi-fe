package com.example.klassi_fe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class BusquedaActivity extends AppCompatActivity {

    MenuInteracions minteraction;

    Toolbar toolbar;

    Spinner spinnermateria,spinnerescolaridad,spinnerzona;
    Button busqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);

        minteraction = new MenuInteracions();

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.maintoolbar);
        setSupportActionBar(toolbar);

        busqueda = (Button) findViewById(R.id.busq_btn_search);

        spinnermateria = (Spinner) findViewById(R.id.busq_spinner_materia);
        spinnerescolaridad = (Spinner) findViewById(R.id.busq_spinner_escolaridad);
        spinnerzona = (Spinner) findViewById(R.id.busq_spinner_zona);

        rellenarSpinners();


        busqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarProfesor();
            }
        });
    }

    private void buscarProfesor() {
        //se va  a ejecutar la busqueda con los items para poder encontrar los profesores, el llamado
        //al backend se va a realizar en este momento para poder recibir la respuesta con los datos
        //y luego directamente listar. igualmente hay que ver cuan factible es esto.

        int pos;
        String valormateria,valorescolaridad, valorzona;

        pos = spinnermateria.getSelectedItemPosition();

        valormateria = spinnermateria.getItemAtPosition(pos).toString();
        valorescolaridad = spinnerescolaridad.getItemAtPosition(pos).toString();
        valorzona = spinnerzona.getItemAtPosition(pos).toString();


        Intent intent = new Intent(BusquedaActivity.this, ProfesoresActivity.class);
        startActivity(intent);
    }

    private void rellenarSpinners() {
        //aca se tiene que llamar al backend para poder buscar las opciones disponibles que se encuentran
        // para rellenar el spinner


        // el siguiente codigo es Filler y solo ejemplo de como se deberia rellenar el spinner
        // Spinner Drop down elements
        List<String> materias = new ArrayList<String>();
        materias.add("Matematica");
        materias.add("Algebra");
        materias.add("Fisica");

        ArrayAdapter<String> adaptermaterias =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, materias);

        adaptermaterias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnermateria.setAdapter(adaptermaterias);


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

