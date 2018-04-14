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
import android.

/** Main Activity. Holds the EditText for URL's, and a "GO" button for navigating to URL's
 * Also contains a viewpager, that displays a collection of WebTabFragments using a
 * FragmentStatePagerAdapter.
 * The URL EditText always displays the last URL of the current WebTabFragment.  **/
public class MainActivity extends AppCompatActivity {

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
    //the toolbar
    Toolbar mToolbar;
    //the current fragment
    WebTabFragment currWebTabFragment;
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
                //currWebTabFragment = (WebTabFragment) mWebTabPagerAdapter.getItem(currentTab);
                //currWebTabFragment.navigateTo(url);
                //mWebTabPagerAdapter.loadPageFor(currentTab, url);

                //set the url value for this tab to be the url in the textedit
                mWebTabPagerAdapter.set(currentTab, url);
                //update the fragment
                updateFragment();
                mWebTabPagerAdapter.notifyDataSetChanged();
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

        //set up the toolbar
        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);

        //make the first tab for them
        currentTab = 0;
        mWebTabPagerAdapter.add(HOME_PAGE);
        updateFragment();
        mWebTabPagerAdapter.notifyDataSetChanged();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            Log.d("menuu", "left");
            case R.id.action_left:
                currentTab --;
                if (currentTab < 0){
                    currentTab = 0;
                }
                updateFragment();
                return true;
            case R.id.action_right:
                currentTab ++;
                if (currentTab > (mWebTabPagerAdapter.getCount() - 1)){
                    currentTab = mWebTabPagerAdapter.getCount()-1;
                }
                updateFragment();
                return true;
            case R.id.action_new:
                currentTab = mWebTabPagerAdapter.getCount();
                mWebTabPagerAdapter.add(HOME_PAGE);
                updateFragment();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //the method we implement per the interface provided by WebTabFragment
    public void onUrlChanged(String url, int position) {
        urlEditText.setText(url);
    }

    public void updateFragment(){
        mViewPager.setCurrentItem(currentTab);
    }
}
