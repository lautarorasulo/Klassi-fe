package com.example.klassi_fe;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class horarios extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horarios);

        init();
        mostrarListViewMultipleChoiceChecked();
    }

    private void init(){this.listView = findViewById(R.id.ListView);}


    private void mostrarListViewMultipleChoiceChecked(){
        List<String> lista =
                new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.array_nr)));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice, lista);

        this.listView.setAdapter(arrayAdapter);

        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object object = parent.getAdapter().getItem(position);
                Toast.makeText(getBaseContext(), object.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }


}
