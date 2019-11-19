package com.example.klassi_fe.clase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.klassi_fe.R;
import com.example.klassi_fe.adapters.AdapterListaProfesores;
import com.example.klassi_fe.objetos.MenuInteracions;
import com.example.klassi_fe.objetos.Profesor;

import java.util.ArrayList;
import java.util.List;

public class ProfesoresActivity extends AppCompatActivity {

    MenuInteracions minteraction;

    private Toolbar toolbar;
    private Button btnAtras;
    private ListView listaProfesores;
    private ArrayList<Profesor> mysProfesores;
    private AdapterListaProfesores adapterListaProfesores;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesores);
        btnAtras = (Button) findViewById(R.id.lista_profesores_btn_atras);
        mysProfesores = new ArrayList<Profesor>();

        Bundle bundle = getIntent().getExtras();
        mysProfesores = bundle.getParcelableArrayList("ListaProfesores");

        adapterListaProfesores = new AdapterListaProfesores(this, mysProfesores);
        listaProfesores = (ListView) findViewById(R.id.lista_profesores);
        listaProfesores.setAdapter(adapterListaProfesores);

        minteraction = new MenuInteracions();
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.maintoolbar);
        setSupportActionBar(toolbar);

        btnAtrasAction();

    }

    public void btnAtrasAction(){
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
