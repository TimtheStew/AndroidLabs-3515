package edu.temple.lab4colorpallete;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class PalleteActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pallete);

        final String[] colors = {"red", "blue", "green","black", "white", "gray", "cyan", "magenta", "yellow", "olive"};

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ColorAdapter(this, colors));

        gridview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ColorActivity.class);
                intent.putExtra("color",colors[position]);
                startActivity(intent);

            }
        });

    }
}
