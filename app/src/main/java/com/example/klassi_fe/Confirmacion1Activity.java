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

public class Confirmacion1Activity extends AppCompatActivity {

    TextView nombre,mail,comentarios;

    Toolbar toolbar;

    Intent intent;

    Button confirmar;

    ImageView perfil;

    MenuInteracions minteraction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacion1);

        minteraction = new MenuInteracions();

        nombre = findViewById(R.id.pnt_cnf2_nomal);

        mail = findViewById(R.id.pnt_cnf2_mailal);
        comentarios = findViewById(R.id.pnt_cnf2_lugar);

        confirmar = (Button) findViewById(R.id.perprof_btn_materias);

        perfil = (ImageView) findViewById(R.id.pnt_cnf2_imgprof);

        //seteo la toolbar
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.maintoolbar);
        setSupportActionBar(toolbar);

        CargoPerfil();


        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Confirmar();
            }
        });
    }


    private void CargoPerfil() {
        //en este metodo voy a hacer la llamada a la API para cargar el perfil cargado en el backend
        //una vez cargado, voy a reflejarlo en los Textview
        //Tambien voy a buscar si esta la imagen del usuario grabada en el dispositivo. en caso
        //que este se va a poner como imagen de perfil, en caso que no, se mostraravacio.


        nombre.setText("Nombre: "+ "juan perez");
        mail.setText("Mail: " + "asd@asd.com");


        //busco Imagen en File system
        File file;
        file = getFilesDir();

        String imagepath = getFilesDir() + "/imagen"+123+".jpg";

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap imagenperfil = BitmapFactory.decodeFile(imagepath,options);

        Log.d("imagen path de BusquedaActivity", "cargoperfil: "+ imagepath);

        if(imagenperfil != null ){
            perfil.setImageBitmap(imagenperfil);
        }



    }

    public void Confirmar(){
        intent = new Intent(Confirmacion1Activity.this , Confirmacion2Activity.class);
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
