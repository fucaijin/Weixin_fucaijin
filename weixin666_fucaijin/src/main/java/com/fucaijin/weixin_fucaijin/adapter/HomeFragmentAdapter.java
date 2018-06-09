package com.fucaijin.weixin_fucaijin.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by fucaijin on 2018/5/13.
 */

public class HomeFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;

    public HomeFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int arg0) {
        return mFragments.get(arg0);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
