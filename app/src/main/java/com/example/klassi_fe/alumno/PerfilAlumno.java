package com.example.klassi_fe.alumno;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.klassi_fe.HomeActivity;
import com.example.klassi_fe.R;
import com.example.klassi_fe.autenticacion.LoginActivity;
import com.example.klassi_fe.objetos.MenuInteracions;
import com.example.klassi_fe.profesor.PerfilProfesor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;

public class PerfilAlumno extends AppCompatActivity {

    private String userId, userRol, userNotificacion;
    private TextView nombre,apellido,mail,ultimas_materias,quieroaprender,buscoprofe;
    private Toolbar toolbar;
    private Button setimage, btnAtras;
    private ImageView perfil;
    private MenuInteracions minteraction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_alumno);

        minteraction = new MenuInteracions();

        nombre = (TextView) findViewById(R.id.pnt_cnf2_nomal);
        apellido = (TextView) findViewById(R.id.perprof_txt_apellido);
        mail = (TextView) findViewById(R.id.pnt_cnf2_mailal);
        ultimas_materias = findViewById(R.id.pnt_cnf2_lugar);
        quieroaprender = findViewById(R.id.perprof_txt_setearHorario);
        buscoprofe = (TextView) findViewById(R.id.perfal_txt_buscoprofe);
        setimage = (Button) findViewById(R.id.perprof_btn_horario);
        btnAtras = (Button) findViewById(R.id.perfil_alumno_atras);
        perfil = (ImageView) findViewById(R.id.pnt_cnf2_imgprof);

        SharedPreferences sp = getSharedPreferences(minteraction.SHARED_PREF_NAME, MODE_PRIVATE);
        userId = sp.getString(minteraction.KEY_NAME,null);
        userRol = sp.getString(minteraction.KEY_NAME_ROL, null);
        userNotificacion = sp.getString(minteraction.KEY_NAME_NOTIFICACION, null);

        //seteo la toolbar
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.maintoolbar);
        setSupportActionBar(toolbar);

        CargoPerfil();
        btnAtrasAction();

        setimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrearImagen();
            }
        });
    }

    public void btnAtrasAction(){
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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

        final ProgressDialog loading = ProgressDialog.show(this, "Por favor espere...", "Actualizando datos...", false, false);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(getString(R.string.getProfesor) + userId, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.dismiss();
                try {
                    mapearDatos(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "Error request "+ error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);


        //busco Imagen en File system
        File file;
        file = getFilesDir();

        String imagepath = getFilesDir() + "/imagen"+123+".jpg";

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap imagenperfil = BitmapFactory.decodeFile(imagepath,options);

        if(imagenperfil != null ){
            perfil.setImageBitmap(imagenperfil);
        }

    }

    public void mapearDatos(JSONObject profe) throws JSONException {
        nombre.setText("Nombre: \n" +profe.optJSONObject("result").getString("nombre"));
        apellido.setText("Apellido: \n" +profe.optJSONObject("result").getString("apellido"));
        mail.setText("Email: \n" +profe.optJSONObject("result").getString("email"));
        buscoprofe.setText("Comentario: \n" +profe.optJSONObject("result").getString("descripcion"));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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
            case R.id.menu_home:
                minteraction.goToHome(this,userRol);
                break;
            case R.id.menu_logout:
                Intent intent = new Intent(PerfilAlumno.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
