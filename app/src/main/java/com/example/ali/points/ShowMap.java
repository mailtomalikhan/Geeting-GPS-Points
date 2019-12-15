package com.example.ali.points;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShowMap extends AppCompatActivity {
    DatabaseHelper databaseHelper;


    public  void  onCreate(Bundle savedInstanceS) {
        super.onCreate(savedInstanceS);
        setContentView(R.layout.map);
        Button Send = (Button) findViewById(R.id.send);
        TextView list = (TextView) findViewById(R.id.display);
        TextView  scroll = (TextView) findViewById(R.id.t);
        list.setText("ali");
        list.setText(getIntent().getStringExtra("name"));
        databaseHelper = new DatabaseHelper(this);
        final String getdata[][]=databaseHelper.readpoints(getIntent().getStringExtra("name"));
        for(int i=0;i<4;i++) {

            scroll.append("\n " + getdata[i][0] + "\n " + getdata[i][1] + "\n" + getdata[i][2] + "\n" + getdata[i][3] + "\n _____________");

        }


       Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ShowMap.this, "Data Send Sucsessfully", Toast.LENGTH_SHORT).show();
                // Write a message to the database
            }
        });
    }
    }


