package com.example.irfan.squarecamera;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;


public class TableActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    PictureAdapter pictureAdapter;
    Database database = new Database(TableActivity.this);
    ArrayList<Picture> pictureArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        pictureArrayList.addAll(database.getAll());

        recyclerView = findViewById(R.id.recyclerView);
        pictureAdapter = new PictureAdapter(pictureArrayList, TableActivity.this);
        recyclerView.setAdapter(pictureAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(TableActivity.this));
    }
}
