package com.example.klassi_fe.clase;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.klassi_fe.ClasesActivity;
import com.example.klassi_fe.HomeActivity;
import com.example.klassi_fe.R;
import com.example.klassi_fe.objetos.MenuInteracions;

import java.io.File;

public class ConfirmarClaseActivity extends AppCompatActivity {

    private TextView nombreal,nombreprof,mailal,mailprof,lugar,hora;
    private Toolbar toolbar;
    private Button confirmar, cancelar;
    private ImageView perfilal, perfilprof;
    private MenuInteracions minteraction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_clase);

        minteraction = new MenuInteracions();

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

        CargoPerfiles();
        cargoDatosClase();
        Confirmar();
        Cancelar();
    }

    private void CargoPerfiles() {
        //en este metodo voy a hacer la llamada a la API para cargar los perfiles cargado en el backend
        //una vez cargado, voy a reflejarlo en los Textview
        //Tambien voy a buscar si esta la imagen del usuario grabada en el dispositivo. en caso
        //que este se va a poner como imagen de perfil, en caso que no, se mostraravacio.


        nombreal.setText("Alumno");
        nombreprof.setText("Profesor");
        mailal.setText("Perez");
        mailprof.setText("Rodrigez");


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
        hora.setText("19:45");
        lugar.setText("Brandsen 805 - La Bombonera");
    }

    private void Confirmar(){
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //aca falta enviar la clase al backend
                Intent intent = new Intent(ConfirmarClaseActivity.this , HomeActivity.class);
                startActivity(intent);
                finish();
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
}
