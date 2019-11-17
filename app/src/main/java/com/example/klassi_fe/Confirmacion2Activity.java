package com.example.klassi_fe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class Confirmacion2Activity extends AppCompatActivity {

    TextView nombreal, nombreprof, mailal , mailprof, lugar, hora;

    Toolbar toolbar;

    Intent intent;

    Button confirmar, cancelar;

    ImageView perfilal, perfilprof;

    MenuInteracions minteraction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacion1);

        minteraction = new MenuInteracions();

        nombreal = findViewById(R.id.pnt_cnf2_nomal);
        nombreprof = findViewById(R.id.pnt_cnf2_nomprof);

        mailal = findViewById(R.id.pnt_cnf2_mailal);
        mailprof = findViewById(R.id.pnt_cnf2_mailprof);
        lugar = findViewById(R.id.pnt_cnf2_lugar);
        hora = findViewById(R.id.pnt_cnf2_hora);


        confirmar = (Button) findViewById(R.id.perprof_btn_materias);
        cancelar = (Button) findViewById(R.id.pnt_cnf2_cancelar);

        perfilprof = (ImageView) findViewById(R.id.pnt_cnf2_imgprof);
        perfilal = (ImageView) findViewById(R.id.pnt_cnf2_imgal);

        //seteo la toolbar
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.maintoolbar);
        setSupportActionBar(toolbar);

        CargoPerfiles();
        cargoDatosClase();

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Confirmar();
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cancelar();
            }
        });


    }


    private void CargoPerfiles() {
        //en este metodo voy a hacer la llamada a la API para cargar los perfiles cargado en el backend
        //una vez cargado, voy a reflejarlo en los Textview
        //Tambien voy a buscar si esta la imagen del usuario grabada en el dispositivo. en caso
        //que este se va a poner como imagen de perfil, en caso que no, se mostraravacio.


        nombreal.setText("Nombre: "+ "juan perez");
        nombreprof.setText("Nombre: "+ "juan rodriguez");
        mailal.setText("Mail: " + "asd@asd.com");
        mailprof.setText("Mail: " + "asd@asd.com");


        //busco Imagen en File system
        File file;
        file = getFilesDir();

        String imagepath = getFilesDir() + "/imagen"+123+".jpg";

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap imagenperfil = BitmapFactory.decodeFile(imagepath,options);

        Log.d("imagen path de BusquedaActivity", "cargoperfil: "+ imagepath);

        if(imagenperfil != null ){
            perfilal.setImageBitmap(imagenperfil);
        }

        //falta traer imagen del profesor en el negocio



    }

    private void cargoDatosClase(){
        //aca se carga hora y lugar seteados por el usuario al momento de elegir una clase
        hora.setText("19:45");
        lugar.setText("Brandsen 805 - La Bombonera");
    }

    public void Confirmar(){
        //aca falta enviar la clase al backend
        intent = new Intent(Confirmacion2Activity.this ,ClasesActivity.class);
        startActivity(intent);
    }

    public void Cancelar(){
        intent = new Intent(Confirmacion2Activity.this , Confirmacion1Activity.class);
        startActivity(intent);
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
