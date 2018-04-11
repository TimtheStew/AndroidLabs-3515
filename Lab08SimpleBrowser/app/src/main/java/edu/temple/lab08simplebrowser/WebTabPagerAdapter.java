package edu.temple.lab08simplebrowser;

/**
 * Created by Timbo on 4/11/18.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class WebTabPagerAdapter extends FragmentStatePagerAdapter {
    //the arraylist of Fragments
    private ArrayList<Fragment> tabArrayList;

    public WebTabPagerAdapter(FragmentManager fm) {
        super(fm);
        tabArrayList = new ArrayList<>();
    }

    public Fragment newTab(String url){
        WebTabFragment webTabFragment = WebTabFragment.newInstance(url);
        tabArrayList.add(webTabFragment);
        return getItem(getCount()-1);
    }

    @Override
    public Fragment getItem(int position) {
        return tabArrayList.get(position);
    }

    @Override
    public int getCount() {
        return tabArrayList.size();
    }
}
