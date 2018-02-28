package edu.temple.lab4colorpallete;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Timbo on 2/27/18.
 */

public class ColorAdapter extends BaseAdapter {
    private final Context mContext;

    //the String array that will store our colors
    private final String[] colors;

    //constructor
    public ColorAdapter(Context context, String[] colors){
        this.mContext = context;
        this.colors = colors;
    }

    //required overrides
    @Override
    public int getCount(){
        return colors.length;
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

        //Setting the text to be the name of a color using the position arg
        colorTextView.setText(colors[position]);

        //setting the background color of the textview to be the color specified by the name
        colorTextView.setBackgroundColor(Color.parseColor(colors[position]));

        //returning the view we made
        return colorTextView;
    }
}
