package com.example.klassi_fe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class PerfilAlumno extends AppCompatActivity {

    TextView nombre,edad,zona,ultimas_materias,quieroaprender,buscoprofe;

    Toolbar toolbar;

    Button setimage;

    ImageView perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_alumno);

        nombre = findViewById(R.id.perfal_txt_nombre);
        edad = findViewById(R.id.perfal_txt_edad);
        zona= findViewById(R.id.perfal_txt_zona);
        ultimas_materias = findViewById(R.id.perfal_txt_ultmat);
        quieroaprender= findViewById(R.id.perfal_txt_quieroaprender);
        buscoprofe = findViewById(R.id.perfal_txt_buscoprofe);

        setimage = (Button) findViewById(R.id.perfal_btn_setimagen);

        perfil = (ImageView) findViewById(R.id.perfal_img_perfil);

        //seteo la toolbar
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.maintoolbar);
        setSupportActionBar(toolbar);

        cargoperfil();


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

    private void cargoperfil() {
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

        Log.d("imagen path de busqueda", "cargoperfil: "+ imagepath);

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
}
