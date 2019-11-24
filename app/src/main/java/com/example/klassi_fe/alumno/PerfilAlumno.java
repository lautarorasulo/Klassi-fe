package com.example.klassi_fe.alumno;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
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

import com.example.klassi_fe.R;
import com.example.klassi_fe.objetos.MenuInteracions;

import java.io.File;
import java.io.FileOutputStream;

public class PerfilAlumno extends AppCompatActivity {

    static final String SHARED_PREF_NAME = "userID";

    static final String KEY_NAME = "key_userID";
    private static final String KEY_NAME_ROL = "key_userROL";
    private static final String KEY_NAME_NOTIFICACION = "key_userNOTIFICACION";

    private String userId, userRol, userNotificacion;

    TextView nombre,edad,zona,ultimas_materias,quieroaprender,buscoprofe;

    Toolbar toolbar;

    Button setimage;

    ImageView perfil;

    MenuInteracions minteraction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_alumno);

        minteraction = new MenuInteracions();

        nombre = findViewById(R.id.pnt_cnf2_nomal);
        edad = findViewById(R.id.perprof_txt_apellido);
        zona= findViewById(R.id.pnt_cnf2_mailal);
        ultimas_materias = findViewById(R.id.pnt_cnf2_lugar);
        quieroaprender= findViewById(R.id.perprof_txt_setearHorario);
        buscoprofe = findViewById(R.id.perfal_txt_buscoprofe);

        setimage = (Button) findViewById(R.id.perprof_btn_horario);

        perfil = (ImageView) findViewById(R.id.pnt_cnf2_imgprof);

        SharedPreferences sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        userId = sp.getString(KEY_NAME,null);
        userRol = sp.getString(KEY_NAME_ROL, null);
        userNotificacion = sp.getString(KEY_NAME_NOTIFICACION, null);


        Log.d("tuvieja ", userId);
        Log.d("tuvieja ", userRol);
        Log.d("tuvieja ", userNotificacion);


        //seteo la toolbar
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.maintoolbar);
        setSupportActionBar(toolbar);

        CargoPerfil();


        setimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrearImagen();
            }
        });
    }

    private void CrearImagen() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,0);
    }

    private void CargoPerfil() {
        //en este metodo voy a hacer la llamada a la API para cargar el perfil cargado en el backend
        //una vez cargado, voy a reflejarlo en los Textview
        //Tambien voy a buscar si esta la imagen del usuario grabada en el dispositivo. en caso
        //que este se va a poner como imagen de perfil, en caso que no, se mostraravacio.


        nombre.setText("Nombre: "+ "juan perez");
        edad.setText("Edad: "+ "10");
        zona.setText("zona: "+"caballito");
        quieroaprender.setText("Materias: " + "lengua, matematicas, etc");
        buscoprofe.setText("Busco profesor:" + "que enseñe de manera lenta y efectiva");

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        Log.d("asd", "onActivityResult: llego aca");
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        perfil.setImageBitmap(bitmap);
        SaveImage(bitmap);
    }

    private void SaveImage(Bitmap imagenasalvar) {

        // salva la imagen que el usuario acaba de sacar.
        File file;
        file = getFilesDir();
        File savefile = new File(file, "imagen"+123+".jpg");
        if(savefile.exists()){
            savefile.delete();
        }
        try{
            FileOutputStream out = new FileOutputStream(savefile);
            imagenasalvar.compress(Bitmap.CompressFormat.JPEG,90,out);
            out.close();
            Log.d("Image de save", "SaveImage: Grabo la imagen???"+savefile.toString());
        }catch (Exception e){
            e.printStackTrace();
        }


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
               // minteraction.irPerfi(this.getLocalClassName(),this);
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
        }

        return super.onOptionsItemSelected(item);
    }
}
