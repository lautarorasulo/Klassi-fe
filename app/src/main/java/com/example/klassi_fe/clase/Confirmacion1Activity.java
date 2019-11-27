package com.example.klassi_fe.clase;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.klassi_fe.HomeActivity;
import com.example.klassi_fe.R;
import com.example.klassi_fe.autenticacion.LoginActivity;
import com.example.klassi_fe.objetos.MenuInteracions;
import com.example.klassi_fe.objetos.Profesor;

import java.io.File;

public class Confirmacion1Activity extends AppCompatActivity {

    private MenuInteracions minteraction;
    private String userId, userRol, userNotificacion;

    private TextView nombre,mail,comentarios,apellido;
    private Toolbar toolbar;
    private Button confirmar;
    private ImageView perfil;
    private Profesor myProfesor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacion1);

        myProfesor = new Profesor();
        Bundle bundle = getIntent().getExtras();
        myProfesor = bundle.getParcelable("profesor");

        minteraction = new MenuInteracions();
        SharedPreferences sp = getSharedPreferences(minteraction.SHARED_PREF_NAME, MODE_PRIVATE);
        userRol = sp.getString(minteraction.KEY_NAME_ROL, null);
        userId = sp.getString(minteraction.KEY_NAME, null);

        nombre = (TextView) findViewById(R.id.pnt_cnf2_nomal);
        apellido = (TextView) findViewById(R.id.pnt_cnf2_apellido);
        mail = (TextView) findViewById(R.id.pnt_cnf2_mailal);
        comentarios = (TextView) findViewById(R.id.pnt_cnf2_desc);

        confirmar = (Button) findViewById(R.id.pnt_cnf_confirmar);

        perfil = (ImageView) findViewById(R.id.pnt_cnf2_imgprof);

        //seteo la toolbar
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.maintoolbar);
        setSupportActionBar(toolbar);

        CargoPerfil();
        Confirmar();
    }


    private void CargoPerfil() {
        //en este metodo voy a hacer la llamada a la API para cargar el perfil cargado en el backend
        //una vez cargado, voy a reflejarlo en los Textview
        //Tambien voy a buscar si esta la imagen del usuario grabada en el dispositivo. en caso
        //que este se va a poner como imagen de perfil, en caso que no, se mostraravacio.

        Log.d("MY PROFESOR", " = " + myProfesor.getId());
        nombre.setText("Nombre: "+ myProfesor.getNombre());
        apellido.setText("Apellido: "+ myProfesor.getApellido());
        mail.setText("Mail: " + myProfesor.getMail());
        comentarios.setText(myProfesor.getDescripcion());


        //busco Imagen en File system
        File file;
        file = getFilesDir();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

    }

    private void Confirmar(){
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Confirmacion1Activity.this, ConfirmarClaseActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("profesor", myProfesor);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
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
                minteraction.irPerfi(this.getLocalClassName(),this, userRol);
                break;
            case R.id.menu_notifications:
                minteraction.irClasesPendientes(this, userRol);
                break;
            case R.id.menu_share:
                minteraction.hacerShare("Shareado desde perfil alumnno",this);
                break;
            case R.id.menu_aboutUs:
                minteraction.mostrarAboutUs("",this);
                break;
            case R.id.menu_home:
                minteraction.goToHome(this,userRol);
                break;
            case R.id.menu_logout:
                Intent intent = new Intent(Confirmacion1Activity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
