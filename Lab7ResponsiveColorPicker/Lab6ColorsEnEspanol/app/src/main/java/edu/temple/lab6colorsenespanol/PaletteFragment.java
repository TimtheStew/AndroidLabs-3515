package edu.temple.lab6colorsenespanol;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;


/**
 * The "master fragment" of our color picker and display app
 */
public class PaletteFragment extends Fragment {
    //names to refer to our arguments by
    private static final String ANDROID_COLORS = "android_colors";
    private static final String COLOR_NAMES = "color_names";

    //our copies of the color arrays
    private String[] androidColors;
    private String[] colorNames;

    //our interface
    OnColorSelectedListener mCallback;


    public PaletteFragment() {
        // Required empty public constructor
    }

    /**
     * A factory method, for creating new instances of our Palette Fragment
     *
     * @param androidColors List of color names that android will recognize
     * @param colorNames List of names of colors to display, changes with language
     * @return A new instance of fragment PaletteFragment.
     */
    public static PaletteFragment newInstance(String[] androidColors, String[] colorNames) {
        //make an instance and bundle
        PaletteFragment fragment = new PaletteFragment();
        Bundle args = new Bundle();

        //put our arrays in the bundle
        args.putStringArray(ANDROID_COLORS, androidColors);
        args.putStringArray(COLOR_NAMES, colorNames);

        //set the bundle as arguments to the fragment
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //check to make sure we have arguments
        if (getArguments() != null) {
            //set our locals with the arguments
            androidColors = getArguments().getStringArray(ANDROID_COLORS);
            colorNames = getArguments().getStringArray(COLOR_NAMES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_palette, container, false);
        GridView gridview = (GridView) view.findViewById(R.id.color_grid_view);
        //create our adapter by passing it our arrays
        gridview.setAdapter(new ColorAdapter(getActivity(), androidColors, colorNames));

        //creating a callback for when each of the gridview elements are clicked
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                /* Here we'll send a message back to our parent Activity using the callback defined
                in our interface. All we'll send is the position of the color they clicked.*/
                mCallback.onColorSelected(position);
            }
        });
        return view;
    }

    /**
     * the interface our containing activity must impliment, just one method for when someone picks
     * a color.
     */
    public interface OnColorSelectedListener {
        public void onColorSelected(int position);
    }

    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnColorSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnColorSelectedListener");
        }
    }

}
