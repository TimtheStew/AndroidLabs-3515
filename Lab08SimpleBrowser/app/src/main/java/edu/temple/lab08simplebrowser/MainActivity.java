package edu.temple.lab08simplebrowser;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                currWebTabFragment = (WebTabFragment) mWebTabPagerAdapter.getItem(currentTab);
                currWebTabFragment.navigateTo(url);
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

        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
    }

    //the method we implement per the interface provided by WebTabFragment
    public void onUrlChanged(String url) {
        urlEditText.setText(url);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_backward:
                currentTab --;
                if (currentTab < 0){
                    currentTab = 0;
                }
                updateFragment();
                return true;
            case R.id.action_forward:
                currentTab ++;
                if (currentTab > (mWebTabPagerAdapter.getCount() - 1)){
                    currentTab = mWebTabPagerAdapter.getCount()-1;
                }
                updateFragment();
                return true;
            case R.id.action_new:
                mWebTabPagerAdapter.newTab("https://www.google.com");
                currentTab = mWebTabPagerAdapter.getCount()-1;
                updateFragment();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateFragment(){
        mViewPager.setCurrentItem(currentTab);
    }
}
