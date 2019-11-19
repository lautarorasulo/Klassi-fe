package com.example.klassi_fe.profesor;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.klassi_fe.R;
import com.example.klassi_fe.materiasActivity;
import com.example.klassi_fe.objetos.MenuInteracions;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PerfilProfesorActivity extends AppCompatActivity {

    TextView nombre,mail,comentarios;

    Toolbar toolbar;

    Button setimage, setHorarios;

    ImageView perfil;

    MenuInteracions minteraction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_alumno);

        minteraction = new MenuInteracions();
        comentarios = findViewById(R.id.pnt_cnf2_lugar);
        nombre = findViewById(R.id.pnt_cnf2_nomal);
        mail = findViewById(R.id.pnt_cnf2_mailal);
        setHorarios = findViewById(R.id.perprof_btn_horario);

        setimage = (Button) findViewById(R.id.perprof_btn_setimagen2);

        perfil = (ImageView) findViewById(R.id.pnt_cnf2_imgprof);

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

        setHorarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setearHorarios();
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


        nombre.setText("Juan Roman Riquelme");
        mail.setText("jromanriquelme@gmail.com");
        comentarios.setText("Profesor puntual muy aplicado");


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
        File savefile = new File(file, "imagen" + 123 + ".jpg");
        if (savefile.exists()) {
            savefile.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(savefile);
            imagenasalvar.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.close();
            Log.d("Image de save", "SaveImage: Grabo la imagen???" + savefile.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_User:
                minteraction.irPerfi(this.getLocalClassName(), this);
                break;
            case R.id.menu_notifications:

                break;
            case R.id.menu_share:
                minteraction.hacerShare("Shareado desde perfil profesor", this);
                break;
            case R.id.menu_aboutUs:
                minteraction.mostrarAboutUs("", this);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public void setearHorarios() {

        // Construyo un AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(PerfilProfesorActivity.this);

        // String array de HorariosActivity
        String[] horarios = new String[]{
                "13:00",
                "14:00",
                "15:00",
                "16:00",
                "17:00"
        };

        // Boolean array para guardar estado de HorariosActivity seleccionados
        final boolean[] checkedHorarios = new boolean[]{
                false, // 1
                true, // 2
                false, // 3
                true, // 4
                false //5

        };

        // Trae lista de HorariosActivity
        final List<String> horariosList = Arrays.asList(horarios);
        //Array para enviar al negocio a guardar HorariosActivity
        final List<String> horariosSelected = Arrays.asList(horarios);


        builder.setMultiChoiceItems(horarios, checkedHorarios, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                // Update de los estados de cada horario
                checkedHorarios[which] = isChecked;

                // Get the current focused item
                String currentItem = horariosList.get(which);

                // Notify the current action
                Toast.makeText(getApplicationContext(),
                        currentItem + " " + isChecked, Toast.LENGTH_SHORT).show();
            }
        });

        // Setear si es cancelable o no
        builder.setCancelable(true);



        // Titulo del dialog
        builder.setTitle("Setear HorariosActivity");

        // Boton guardar
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < checkedHorarios.length; i++) {
                    boolean checked = checkedHorarios[i];
                    if (checked) {
                        horariosSelected.add(horariosList.get(i));
                        agregarHorarios();
                    }
                }
            }
        });

        // Boton cancelar
        builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when click the neutral button
            }
        });

        AlertDialog dialog = builder.create();
        // Display the alert dialog on interface
        dialog.show();
    }

    public void verMaterias(){
        Intent intent = new Intent(PerfilProfesorActivity.this, materiasActivity.class);
        startActivity(intent);
    }

    public void agregarHorarios(){
        final ProgressDialog loading = ProgressDialog.show(this, "Por favor espere...", "Actualizando datos...", false, false);

        //JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getString(R.string.postClase), null, new Response.Listener<JSONObject>() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.postClase), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                Log.d("asdasd", "onResponse: "+response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "Error request "+ error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<>();
            params.put("aca va el array de horarios seteados", "");
            Log.d("SeteandoHorarios", "SeteandoHorarios " + params);
            return params;
        }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
