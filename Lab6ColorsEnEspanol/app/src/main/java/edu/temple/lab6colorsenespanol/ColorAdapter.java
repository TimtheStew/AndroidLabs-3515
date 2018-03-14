package edu.temple.lab6colorsenespanol;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Administrator on 3/13/2018.
 */

public class ColorAdapter extends BaseAdapter {
    private final Context mContext;

    //the String arrays that store our parsable android colors and their names
    private String[] androidColors;
    private String[] colorNames;

    //constructor
    public ColorAdapter(Context context, String[] androidColors, String[] colorNames){
        this.mContext = context;
        this.androidColors = androidColors;
        this.colorNames = colorNames;
    }

    //required overrides
    @Override
    public int getCount(){
        return colorNames.length;
    }

    @Override
    public long getItemId(int position){
        return 0;
    }

    @Override
    public Object getItem(int position){
        return null;
    }

    //this is called on each Item to determine what view to show in the GridView
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //creating a TextView object
        TextView colorTextView = new TextView(mContext);

        /* Setting the text to be the name of a color using the position arg and our color names
        array, which can vary with language */
        colorTextView.setText(colorNames[position]);

        /*
        setting the background color of the textview to be the color specified by the name,
        but that android will recognize
        */
        colorTextView.setBackgroundColor(Color.parseColor(androidColors[position]));

        //returning the view we made
        return colorTextView;
    }
}


