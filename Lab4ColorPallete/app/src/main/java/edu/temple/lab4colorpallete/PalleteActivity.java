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

        //the list of color values
        final String[] colors = {"red", "blue", "green","black", "white", "gray", "cyan", "magenta", "yellow", "olive"};

        //creating our gridview
        GridView gridview = (GridView) findViewById(R.id.gridview);

        //setting the gridview adapter to be an instance of our custom ColorAdapter
        gridview.setAdapter(new ColorAdapter(this, colors));


        //Creating a callback for when each of the gridview Items are clicked
        gridview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                //create an Intent Object, passing it our context and a class to identify the
                //activity it should go to
                Intent intent = new Intent(getApplicationContext(), ColorActivity.class);

                //attaching a color String to our Intent using the position argument to the callback
                intent.putExtra("color",colors[position]);

                //starting a new activity with the intent we just made
                startActivity(intent);

            }
        });

    }
}
