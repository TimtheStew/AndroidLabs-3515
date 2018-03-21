package edu.temple.lab6colorsenespanol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
        implements PaletteFragment.OnColorSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*we get the list of color names to pass android when we want to display the colors
        themselves, which doesn't change based on language */
        final String[] androidColors = getResources().getStringArray(R.array.android_colors_array);
        //we get the list of color names, which could change depending on language
        final String[] colorNames = getResources().getStringArray(R.array.colors_array);

        //create our picker fragment
        PaletteFragment palleteFragment = PaletteFragment.newInstance(androidColors, colorNames);

        //stick the picker in the picker slot (the container we have in any layout)
        getSupportFragmentManager().beginTransaction()
                .add(R.id.palette_container, palleteFragment, "palette_boy")
                .commit();

        //check to see if we're in the two paned layout
        if (findViewById(R.id.color_container) != null){
            //make a default fragment, displaying rojo for now, ho.
            CanvasFragment canvasFragment = CanvasFragment.newInstance(androidColors, 0);

            //stick it in the color slot
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.color_container, canvasFragment, "canvas_boy")
                    .commit();
        }
    }

    @Override
    public void onColorSelected(int position) {
        final String[] androidColors = getResources().getStringArray(R.array.android_colors_array);

        //check to see if we can just update the color fragment
        if (findViewById(R.id.color_container) != null){
            // if we are two-paned, just call that method boy
            CanvasFragment canvasFragment = (CanvasFragment) getSupportFragmentManager()
                    .findFragmentByTag("canvas_boy");

            canvasFragment.onColorSelected(position);
        } else { // or if we're single paned, and we gotta swap out the fragments
            CanvasFragment canvasFragment = CanvasFragment.newInstance(androidColors, position);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.palette_container, canvasFragment)
                    .addToBackStack(null)
                    .commit();
        }


    }
}
