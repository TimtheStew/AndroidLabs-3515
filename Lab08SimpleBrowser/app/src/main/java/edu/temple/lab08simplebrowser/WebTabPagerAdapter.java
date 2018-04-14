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
    //the arraylist of Fragments
    //private ArrayList<Fragment> tabArrayList;

    private ArrayList<String> urlArrayList;

    private int numTabs;

    public WebTabPagerAdapter(FragmentManager fm) {
        super(fm);
        //tabArrayList = new ArrayList<>();
        urlArrayList = new ArrayList<>();
        numTabs = 0;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
    /**
    @Override
    public Object instantiateItem(ViewGroup container, int position){
        WebTabFragment webTabFragment = WebTabFragment.newInstance("https://wwww.google.com");
        tabArrayList.add(position, webTabFragment);
        notifyDataSetChanged();
        return tabArrayList.get(position);
    }
    **/
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
        //return urlArrayList.size();
        //return tabArrayList.size();
    }

    public void add(String url){
        Log.d("pagAdapt", "add()");
        urlArrayList.add(url);
        //notifyDataSetChanged();
    }

    public void set(int position, String url){
        //tabArrayList.add(position, WebTabFragment.newInstance("https://www.google.com"));
        Log.d("pagAdapt", "set url to: " + url);
        urlArrayList.set(position, url);
        //notifyDataSetChanged();
    }

    //the method we implement per the interface provided by WebTabFragment
    //public void onUrlChanged(String url, int position) {
      //  urlArrayList.set(position, url);
        //notifyDataSetChanged();
    //}
    /**
    public void loadPageFor(int position, String url){
        WebTabFragment webTabFragment = (WebTabFragment) tabArrayList.get(position);
        webTabFragment.navigateTo(url);
    }
     **/

}
