package com.fucaijin.weixin_fucaijin.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.fucaijin.weixin_fucaijin.R;
import com.fucaijin.weixin_fucaijin.adapter.HomeFragmentAdapter;
import com.fucaijin.weixin_fucaijin.fragment.HomeFragmentAddressList;
import com.fucaijin.weixin_fucaijin.fragment.HomeFragmentFound;
import com.fucaijin.weixin_fucaijin.fragment.HomeFragmentMe;
import com.fucaijin.weixin_fucaijin.fragment.HomeWechatFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener{
    private ViewPager viewPager;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initUI();
    }

    private void initUI() {
//        创建要放置到ViewPager中的Fragment的集合并把Fragment放进去，然后把Fra
        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeWechatFragment());
        fragmentList.add(new HomeFragmentAddressList());
        fragmentList.add(new HomeFragmentFound());
        fragmentList.add(new HomeFragmentMe());
        HomeFragmentAdapter adapter = new HomeFragmentAdapter(getSupportFragmentManager(), fragmentList);

        viewPager = (ViewPager) findViewById(R.id.home_view_pager);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
