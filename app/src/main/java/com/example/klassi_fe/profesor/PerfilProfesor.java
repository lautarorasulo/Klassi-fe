package com.example.klassi_fe.profesor;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.klassi_fe.R;
import com.example.klassi_fe.materiasActivity;
import com.example.klassi_fe.objetos.MenuInteracions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PerfilProfesor extends AppCompatActivity {

    private String userId, userRol, userNotificacion;
    private TextView nombre,apellido,mail,comentarios;
    private Toolbar toolbar;
    private Button setimage, setHorarios;
    private ImageView perfil;
    private MenuInteracions minteraction;
    private JSONArray horas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_profesor);

        minteraction = new MenuInteracions();

        comentarios = (TextView) findViewById(R.id.pnt_cnf2_lugar);
        nombre = (TextView) findViewById(R.id.pnt_cnf2_nomal);
        apellido = (TextView) findViewById(R.id.perfil_profesor_apellido);
        mail = (TextView) findViewById(R.id.pnt_cnf2_mailal);
        setHorarios = (Button) findViewById(R.id.perprof_btn_horario);

        // datos de mi usuario logeado
        SharedPreferences sp = getSharedPreferences(minteraction.SHARED_PREF_NAME, MODE_PRIVATE);
        userId = sp.getString(minteraction.KEY_NAME,null);
        userRol = sp.getString(minteraction.KEY_NAME_ROL, null);
        userNotificacion = sp.getString(minteraction.KEY_NAME_NOTIFICACION, null);

        //seteo la toolbar
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.maintoolbar);
        setSupportActionBar(toolbar);

        setHorarios();
        CargoPerfil();
    }

    private void setHorarios(){
        setHorarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    setearHorarios();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void CargoPerfil() {

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

        Log.d("imagen path de BusquedaActivity", "cargoperfil: "+ imagepath);

        if(imagenperfil != null ){
            perfil.setImageBitmap(imagenperfil);
        }

    }

    public void mapearDatos(JSONObject profe) throws JSONException {
        nombre.setText(profe.optJSONObject("result").getString("nombre"));
        apellido.setText(profe.optJSONObject("result").getString("apellido"));
        mail.setText(profe.optJSONObject("result").getString("email"));
        comentarios.setText(profe.optJSONObject("result").getString("descripcion"));
        horas = profe.optJSONObject("result").getJSONArray("horas");
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
                //  minteraction.irPerfi(this.getLocalClassName(), this, userRol);
                break;
            case R.id.menu_notifications:
                minteraction.irClasesPendientes(this, userRol);
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

    public void setearHorarios() throws JSONException {
        final ArrayList<String> addHora = new ArrayList<String>();
        final ArrayList<String> deleteHora = new ArrayList<String>();

        // Construyo un AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(PerfilProfesor.this, R.style.DialogTheme);

        // String array de HorariosActivity
        final String[] horarios = new String[]{
                "08:00",
                "09:00",
                "10:00",
                "11:00",
                "12:00",
                "13:00",
                "14:00",
                "15:00",
                "16:00",
                "17:00",
                "18:00",
                "19:00"
        };

        // Boolean array para guardar estado de HorariosActivity seleccionados
        final boolean[] checkedHorarios = new boolean[]{
                false, // 1
                false, // 2
                false, // 3
                false, // 4
                false, //5
                false, // 6
                false, // 7
                false, // 8
                false, // 9
                false, // 10
                false, // 11
                false // 12
        };

        for(int i = 0; i < horarios.length; i++){
            int j = 0;
            boolean flag = true;
         //   Log.d("VER DATOS", "Horarios: " + horarios[i]);

            while( j < horas.length() && flag ){
                if(horarios[i].equals(horas.get(j).toString())){
                    Log.d("ENTRO AL IF", "HORARIO: " + horarios[i]);
                    checkedHorarios[i] = true;
                    flag = false;
                } else {
                    j++;
                }
            }
        }

        // Trae lista de HorariosActivity
        final List<String> horariosList = Arrays.asList(horarios);
        //Array para enviar al negocio a guardar HorariosActivity
        //final List<String> horariosSelected = Arrays.asList(horarios);


        builder.setMultiChoiceItems(horarios, checkedHorarios, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {



                // Update de los estados de cada horario
                checkedHorarios[which] = isChecked;

                // Get the current focused item
                String currentItem = horariosList.get(which);
                //Log.d("SELECCION", " : " + currentItem +" " + checkedHorarios[which]);
                if(checkedHorarios[which]){
                    addHora.add(currentItem);
                } else {
                    deleteHora.add(currentItem);
                }
                // Notify the current action
                //Toast.makeText(getApplicationContext(),
                //      currentItem + " " + isChecked, Toast.LENGTH_SHORT).show();
            }
        });

        // Setear si es cancelable o no
        builder.setCancelable(true);

        // Titulo del dialog
        builder.setTitle("Agregar Horario");

        // Boton guardar
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               //Log.d("ADD HORA: ", " = " + addHora);
               for(int i = 0; i < addHora.size(); i++){
                   agregarHorarios(addHora.get(i), true);
               }
               for(int j = 0; j < deleteHora.size(); j++){
                   agregarHorarios(deleteHora.get(j), false);
                   /** HACER METODO PARA ELIMINAR HORAS*/
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


    public void agregarHorarios(final String mysHorarios, boolean addHora){
        String url = "";

        if(addHora){
            url = getString(R.string.addHora);
        } else {
            url = getString(R.string.deleteHora);
        }
        final ProgressDialog loading = ProgressDialog.show(this, "Por favor espere...", "Actualizando datos...", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
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
            params.put("idProfesor", userId);
            params.put("horas", mysHorarios);
            return params;
        }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
