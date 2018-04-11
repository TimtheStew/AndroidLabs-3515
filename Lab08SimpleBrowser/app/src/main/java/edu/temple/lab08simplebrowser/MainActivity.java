package edu.temple.lab08simplebrowser;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

/** Main Activity. Holds the EditText for URL's, and a "GO" button for navigating to URL's
 * Also contains a viewpager, that displays a collection of WebTabFragments using a
 * FragmentStatePagerAdapter.
 * The URL EditText always displays the last URL of the current WebTabFragment.  **/
public class MainActivity extends Activity {

    //the URL edit text
    EditText urlEditText;
    //the button for navigating to the current URL
    Button goButton;
    //the arraylist of fragments
    ArrayList<Fragment> tabArrayList;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting the callback for our button
        goButton = (Button) findViewById(R.id.goButton);
        goButton.setOnClickListener(new View.OnClickListener(){
            public void OnClick(View v){

            }
        });
    }

    //the method we implement per the interface provided by WebTabFragment
    public void onUrlChanged(String url) {
        urlEditText.setText(url);
    }
}
