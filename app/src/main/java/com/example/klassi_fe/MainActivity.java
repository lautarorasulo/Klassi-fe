package com.example.klassi_fe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ToolbarWidgetWrapper;

public class MainActivity extends AppCompatActivity {


    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolbar = (Toolbar) findViewById(R.id.idtoolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().hide();


        setContentView(R.layout.activity_main);



    }
}
