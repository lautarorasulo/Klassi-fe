package com.example.klassi_fe;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

public class MenuInteracions {



    public void irPerfi(String s, Context c){


        if(!s.equals("PerfilAlumno")){
            Intent intent = new Intent(c,PerfilAlumno.class);
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
