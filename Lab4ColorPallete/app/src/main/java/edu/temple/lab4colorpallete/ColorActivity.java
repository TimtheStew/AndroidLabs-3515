package edu.temple.lab4colorpallete;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;

public class ColorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);

        //get the intent that started us
        Intent intent = getIntent();

        //get the string with name color we sent along with the intent
        String color = intent.getStringExtra("color");

        //get a reference to the layout using the ID we set in the XML
        ConstraintLayout layout = findViewById(R.id.colorBackground);

        //set the background color to the color that was passed along with the intent
        layout.setBackgroundColor(Color.parseColor(color));
    }
}
