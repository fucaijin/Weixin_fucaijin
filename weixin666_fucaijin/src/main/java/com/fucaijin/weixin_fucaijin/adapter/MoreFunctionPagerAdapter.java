package com.fucaijin.weixin_fucaijin.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fucaijin.weixin_fucaijin.fragment.ChatMoreFunctionFragment;
import com.fucaijin.weixin_fucaijin.fragment.ChatMoreFunctionFragmentTwo;

import java.util.ArrayList;

/**
 * Created by fucaijin on 2018/6/7.
 */

public class MoreFunctionPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> moreFunctionPagerList = new ArrayList();

    public MoreFunctionPagerAdapter(FragmentManager fm) {
        super(fm);
        moreFunctionPagerList.add(new ChatMoreFunctionFragment());
        moreFunctionPagerList.add(new ChatMoreFunctionFragmentTwo());
    }

    @Override
    public Fragment getItem(int position) {
        return moreFunctionPagerList.get(position);
    }

    @Override
    public int getCount() {
        return moreFunctionPagerList.size();
    }
}
