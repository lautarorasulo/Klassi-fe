package com.example.klassi_fe;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ToolbarWidgetWrapper;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(MainActivity.this , Register.class);
        startActivity(intent);

        Handler myhandler = new Handler();

        myhandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        },1000);


    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
