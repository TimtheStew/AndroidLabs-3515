package edu.temple.lab08simplebrowser;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

/** Main Activity. Holds the EditText for URL's, and a "GO" button for navigating to URL's
 * Also contains a viewpager, that displays a collection of WebTabFragments using a
 * FragmentStatePagerAdapter.
 * The URL EditText always displays the last URL of the current WebTabFragment.  **/
public class MainActivity extends AppCompatActivity implements WebTabFragment.OnUrlChangedListener {

    //the URL edit text
    EditText urlEditText;
    //the button for navigating to the current URL
    Button goButton;
    //our ViewPager
    ViewPager mViewPager;
    //our WebTabPagerAdapter (our implementation of FragmentStatePagerAdapter)
    WebTabPagerAdapter mWebTabPagerAdapter;
    //our current tab index
    int currentTab;
    int numTabs;
    //the toolbar
    Toolbar mToolbar;
    //the current fragment
    WebTabFragment currWebTabFragment;
    //our copy of the current URLs for each tab
    ArrayList<String> urlArrayList;
    //home page url
    final String HOME_PAGE = "https://www.google.com";

    //overriding a method to create our options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting the callback for our button
        goButton = (Button) findViewById(R.id.goButton);
        goButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //navigate the current tab to the URL currently in the EditText
                String url = urlEditText.getText().toString();
                Log.d("goButt", "tried to go to: " + url);
                //set the url value for this tab to be the url in the textedit
                mWebTabPagerAdapter.set(currentTab, url);
                WebTabFragment webTabFragment = (WebTabFragment) mWebTabPagerAdapter.getItem(mViewPager.getCurrentItem());
                webTabFragment.setCurrentUrl(url);

                //update the fragment
                updateFragment();
            }
        });
        // get a reference to the EditText
        urlEditText = (EditText) findViewById(R.id.urlEditText);
        // get a reference to the ViewPager
        mViewPager = (ViewPager) findViewById(R.id.pager);
        //create our WebTabPager Adapter
        mWebTabPagerAdapter = new WebTabPagerAdapter(getSupportFragmentManager());
        //set our Pager's adapter
        mViewPager.setAdapter(mWebTabPagerAdapter);
        mViewPager.setOffscreenPageLimit(0);
        //add this to better track URL's
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                urlEditText.setText(mWebTabPagerAdapter.urlArrayList.get(mViewPager.getCurrentItem()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //set up the toolbar
        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);


        //make the first tab for them
        currentTab = 0;
        numTabs = 1;
        mWebTabPagerAdapter.add(HOME_PAGE);
        mWebTabPagerAdapter.notifyDataSetChanged();
        updateFragment();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_left:
                currentTab --;
                Log.d("menuu", "left");
                if (currentTab < 0){
                    currentTab = 0;
                }
                updateFragment();
                return true;
            case R.id.action_right:
                currentTab ++;
                Log.d("menuu", "right");
                if (currentTab > (mWebTabPagerAdapter.getCount() - 1)){
                    currentTab = mWebTabPagerAdapter.getCount()-1;
                }
                updateFragment();
                return true;
            case R.id.action_new:
                Log.d("menuu", "new");
                currentTab = numTabs;
                numTabs++;
                mWebTabPagerAdapter.add(HOME_PAGE);
                mWebTabPagerAdapter.notifyDataSetChanged();
                updateFragment();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //the method we implement per the interface provided by WebTabFragment
    public void onUrlChanged(String url, int position) {
        urlEditText.setText(url);
        mWebTabPagerAdapter.set(currentTab, url);
        mWebTabPagerAdapter.notifyDataSetChanged();
        updateFragment();

    }

    public void updateFragment(){
        Log.d("MainAct", "updtFrag()");
        mViewPager.setCurrentItem(currentTab);
    }
}
