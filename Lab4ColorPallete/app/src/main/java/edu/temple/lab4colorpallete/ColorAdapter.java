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
    private final String[] colors;

    public ColorAdapter(Context context, String[] colors){
        this.mContext = context;
        this.colors = colors;
    }

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        TextView colorTextView = new TextView(mContext);
        colorTextView.setText(colors[position]);
        colorTextView.setBackgroundColor(Color.parseColor(colors[position]));
        return colorTextView;
    }
}
