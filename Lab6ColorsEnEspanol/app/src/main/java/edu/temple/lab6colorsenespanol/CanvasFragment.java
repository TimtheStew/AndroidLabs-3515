package edu.temple.lab6colorsenespanol;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


/**
 * The "detail fragment" portion of our color picker  and display app
 */
public class CanvasFragment extends Fragment {
    //names to call our arguments by
    private static final String ANDROID_COLORS = "android_colors";
    private static final String POSITION = "position";

    private String[] androidColors;
    private int position;

    public CanvasFragment() {
        // Required empty public constructor
    }

    /**
     * A factory method, for creating new instances of our Canvas Fragment
     *
     * @param androidColors List of color names that android will recognize
     * @return A new instance of fragment PaletteFragment.
     */
    public static CanvasFragment newInstance(String[] androidColors, int position) {
        CanvasFragment fragment = new CanvasFragment();
        Bundle args = new Bundle();
        args.putStringArray(ANDROID_COLORS, androidColors);
        args.putInt(POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            androidColors = getArguments().getStringArray(ANDROID_COLORS);
            position = getArguments().getInt(POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_canvas, container, false);
        //update our background color with the current selected color
        FrameLayout frameLayout = view.findViewById(R.id.canvas_background);
        frameLayout.setBackgroundColor(Color.parseColor(androidColors[this.position]));
        return view;
    }

    /**
     * A public method, callable by the containing activity, that will change the background color
     * of this fragment to that selected by the user when called. it does this by updating an
     * internal variable position then calling updateBackgroundColor()
     * @param position the position of the color selected in the androidColors array
     */
    public void onColorSelected(int position){
        this.position = position;
        updateBackgroudColor();
    }

    //the method that changes the background color to match our current position
    private void updateBackgroudColor(){
        FrameLayout frameLayout = getView().findViewById(R.id.canvas_background);
        frameLayout.setBackgroundColor(Color.parseColor(androidColors[this.position]));
    }




}
