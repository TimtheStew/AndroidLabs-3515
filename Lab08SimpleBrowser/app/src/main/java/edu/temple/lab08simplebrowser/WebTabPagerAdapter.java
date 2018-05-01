package edu.temple.lab08simplebrowser;

/**
 * Created by Timbo on 4/11/18.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import java.util.ArrayList;

public class WebTabPagerAdapter extends FragmentStatePagerAdapter {

    public ArrayList<String> urlArrayList;
    private ArrayList<WebTabFragment> tabArrayList;
    public WebTabFragment webTabFragment;

    private int numTabs;
    private int currTabIndex;

    public WebTabPagerAdapter(FragmentManager fm) {
        super(fm);
        urlArrayList = new ArrayList<>();
        tabArrayList = new ArrayList<>();
        numTabs = 0;
        currTabIndex = 0;
    }
/*
    // we override and this and always return POSITION_NONE so that notifyDataSetChanged() always
    // actually calls getItem() again?
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
  */
    @Override
    public Fragment getItem(int position) {
        Log.d("pagAdapt", "getItem()");
        return WebTabFragment.newInstance(urlArrayList.get(position), position);
    }


    @Override
    public int getCount() {
        Log.d("pagAdapt", "getCount()");
        numTabs = urlArrayList.size();
        return  numTabs;
    }
    @Override
    public int getItemPosition(Object object) {
        WebTabFragment webTabFragment = (WebTabFragment ) object;
        if (webTabFragment != null) {
            webTabFragment.navigateTo(urlArrayList.get(webTabFragment.mPosition));
        }
        return super.getItemPosition(object);
    }

    public void add(String url){
        Log.d("pagAdapt", "add()");
        urlArrayList.add(url);
    }

    public void set(int position, String url){
        Log.d("pagAdapt", "set url to: " + url);
        urlArrayList.set(position, url);
    }
    public void goTo(String url){

    }
}
