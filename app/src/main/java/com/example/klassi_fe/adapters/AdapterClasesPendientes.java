package com.example.klassi_fe.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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
import com.example.klassi_fe.objetos.ObjetoClase;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdapterClasesPendientes extends BaseAdapter {

    Context context;
    ArrayList<ObjetoClase> clases;


    public AdapterClasesPendientes(Context c, ArrayList<ObjetoClase> p){
        this.context = c;
        this.clases = p;
    }

    @Override
    public int getCount() {
        return clases.size();
    }

    @Override
    public Object getItem(int position) {
        return clases.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.activity_clases_pendientes, null);
        TextView alumno = convertView.findViewById(R.id.clases_prf_alumno);
        TextView fecha = convertView.findViewById(R.id.clases_prf_fecha);
        TextView zona = convertView.findViewById(R.id.clases_prf_zona);
        TextView materia = convertView.findViewById(R.id.clases_prf_materia);
        Button btnAceptar = convertView.findViewById(R.id.clases_prf_btn_aceptar);
        Button btnRechazar = convertView.findViewById(R.id.clases_prf_btn_rechazar);

        final ObjetoClase clase = (ObjetoClase) getItem(position);

        alumno.setText(clase.alumno);
        fecha.setText(clase.horario);
        zona.setText(clase.zona);
        materia.setText(clase.materia);

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog loading = ProgressDialog.show(context, "Por favor espere...", "Actualizando datos...", false, false);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://klassi-3.herokuapp.com/api/aceptarclase/" + clase.get_id(), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.dismiss();
                        Toast.makeText(context, "Clase aceptada con exito", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(context, "Error request "+ error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(jsonObjectRequest);
            }
        });

        btnRechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog loading = ProgressDialog.show(context, "Por favor espere...", "Actualizando datos...", false, false);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://klassi-3.herokuapp.com/api/removeClase", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        Toast.makeText(context, "Clase rechazada con exito", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(context, "Error request "+ error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("idClase", clase.get_id());
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);
            }
        });

        return convertView;
    }
}
