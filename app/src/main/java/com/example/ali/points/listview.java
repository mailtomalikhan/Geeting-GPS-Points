package com.example.ali.points;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class listview extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    public  void  onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        databaseHelper = new DatabaseHelper(this);
        final String getdata[]=databaseHelper.readdtat();

        ListView listView=(ListView) findViewById(R.id.list);
ArrayAdapter<String>  adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getdata);
listView.setAdapter(adapter);

listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?>listview , View view, int position, long id) {
        Intent intent =new Intent(getApplicationContext(),MapsActivity2.class);
        intent.putExtra("name",getdata[position]);
        startActivity(intent);
    }
});

    }


}
