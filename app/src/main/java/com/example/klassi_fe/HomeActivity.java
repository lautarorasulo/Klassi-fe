package com.example.klassi_fe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.klassi_fe.clase.BusquedaActivity;
import com.example.klassi_fe.objetos.MenuInteracions;

public class HomeActivity extends AppCompatActivity {

    MenuInteracions minteraction;

    Toolbar toolbar;

    TableLayout table;
    TableRow tablerow;

    Button botonbusqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        table = (TableLayout) findViewById(R.id.home_tbl_clases);
        tablerow = new TableRow(this);


        minteraction = new MenuInteracions();

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

    private void fillClasesOnTable() {

        //voy a buscar en el backend si existen clases diponibles, en caso que existan voy a llenar
        //el Table layout con las clases del alumno.




        tablerow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        TextView txt = new TextView(this);
        txt.setText("dasdfadf, asdfasdfasdf");
        txt.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tablerow.addView(txt);

        table.addView(tablerow,new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));



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
