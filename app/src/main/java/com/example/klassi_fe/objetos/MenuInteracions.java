package com.example.klassi_fe.objetos;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

import com.example.klassi_fe.alumno.PerfilAlumno;
import com.example.klassi_fe.profesor.ClasesPendienteDosBotones;
import com.example.klassi_fe.profesor.PerfilProfesor;


public class MenuInteracions {

    public static final String SHARED_PREF_NAME = "userID";
    public static final String KEY_NAME = "key_userID";
    public static final String KEY_NAME_ROL = "key_userROL";
    public static final String KEY_NAME_NOTIFICACION = "key_userNOTIFICACION";

    public void irPerfi(String s, Context c, String userRol){

        if(userRol.equals("Profesor")){
            Intent intent = new Intent(c, PerfilProfesor.class);
            c.startActivity(intent);
        }else{
            if(!s.equals("PerfilAlumno")){
                Intent intent = new Intent(c, PerfilAlumno.class);
                c.startActivity(intent);
            }
        }
    }


    public  void irClasesPendientes(Context c, String userRol){
        if(userRol.equals("Profesor")){
            Intent intent = new Intent(c, ClasesPendienteDosBotones.class);
            c.startActivity(intent);
        }

    }

    public void hacerShare(String s, Context c){


        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, s);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        c.startActivity(shareIntent);
    }

    public void mostrarAboutUs(String s, Context c){


        final EditText taskEditText = new EditText(c);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("About Us")
                .setMessage("Klassi es una aplicacion hecha por los alumnos de ORT belgrano," +
                        "los integrantes son: /n Jorge ---  Santi --- Jonas --- Lautaro")
                .setView(taskEditText)
                //.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                //    @Override
                //    public void onClick(DialogInterface dialog, int which) {
                //        String task = String.valueOf(taskEditText.getText());
                //    }
                //})
                .setNegativeButton("Gracias!!", null)
                .create();
        dialog.show();
    }

}
