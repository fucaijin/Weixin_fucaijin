package com.fucaijin.weixin_fucaijin.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fucaijin.weixin_fucaijin.R;
import com.fucaijin.weixin_fucaijin.adapter.HomeFragmentAdapter;
import com.fucaijin.weixin_fucaijin.fragment.HomeFragmentAddressList;
import com.fucaijin.weixin_fucaijin.fragment.HomeFragmentFound;
import com.fucaijin.weixin_fucaijin.fragment.HomeFragmentMe;
import com.fucaijin.weixin_fucaijin.fragment.HomeWechatFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private ViewPager viewPager;
    private List<Fragment> fragmentList;
    private ImageView home_bottom_tab_iv_wechat;
    private ImageView home_bottom_tab_iv_address_list;
    private ImageView home_bottom_tab_iv_found;
    private ImageView home_bottom_tab_iv_me;
    private TextView home_bottom_tab_tv_wechat;
    private TextView home_bottom_tab_tv_address_list;
    private TextView home_bottom_tab_tv_found;
    private TextView home_bottom_tab_tv_me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initUI();
    }

    /**
     * 获取所有需要用到的控件
     */
    private void initUI() {
        LinearLayout home_bottom_tab_ll_wechat = (LinearLayout) findViewById(R.id.home_bottom_tab_ll_wechat);
        LinearLayout home_bottom_tab_ll_address_list = (LinearLayout) findViewById(R.id.home_bottom_tab_ll_address_list);
        LinearLayout home_bottom_tab_ll_found = (LinearLayout) findViewById(R.id.home_bottom_tab_ll_found);
        LinearLayout home_bottom_tab_ll_me = (LinearLayout) findViewById(R.id.home_bottom_tab_ll_me);

        home_bottom_tab_ll_wechat.setOnClickListener(this);
        home_bottom_tab_ll_address_list.setOnClickListener(this);
        home_bottom_tab_ll_found.setOnClickListener(this);
        home_bottom_tab_ll_me.setOnClickListener(this);

        home_bottom_tab_iv_wechat = (ImageView) findViewById(R.id.home_bottom_tab_iv_wechat);
        home_bottom_tab_iv_address_list = (ImageView) findViewById(R.id.home_bottom_tab_iv_address_list);
        home_bottom_tab_iv_found = (ImageView) findViewById(R.id.home_bottom_tab_iv_found);
        home_bottom_tab_iv_me = (ImageView) findViewById(R.id.home_bottom_tab_iv_me);

        home_bottom_tab_tv_wechat = (TextView) findViewById(R.id.home_bottom_tab_tv_wechat);
        home_bottom_tab_tv_address_list = (TextView) findViewById(R.id.home_bottom_tab_tv_address_list);
        home_bottom_tab_tv_found = (TextView) findViewById(R.id.home_bottom_tab_tv_found);
        home_bottom_tab_tv_me = (TextView) findViewById(R.id.home_bottom_tab_tv_me);

//        创建要放置到ViewPager中的Fragment的集合并把Fragment放进去，然后把Fra
        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeWechatFragment());
        fragmentList.add(new HomeFragmentAddressList());
        fragmentList.add(new HomeFragmentFound());
        fragmentList.add(new HomeFragmentMe());
        HomeFragmentAdapter adapter = new HomeFragmentAdapter(getSupportFragmentManager(), fragmentList);

        viewPager = (ViewPager) findViewById(R.id.home_view_pager);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(0);
        setTabSelection(0);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setTabSelection(position);
    }

    /**
     * 根据当前选中的页面，来改变底部的导航栏按钮颜色
     * @param position 当前选中的页面
     */
    private void setTabSelection(int position) {
        clearSelection();
        switch (position) {
            case 0:
                home_bottom_tab_iv_wechat.setImageResource(R.drawable.home_bottom_tab_wechat_pressed);
                home_bottom_tab_tv_wechat.setTextColor(Color.parseColor("#33cc33"));
                break;
            case 1:
                home_bottom_tab_iv_address_list.setImageResource(R.drawable.home_bottom_tab_address_list_pressed);
                home_bottom_tab_tv_address_list.setTextColor(Color.parseColor("#33cc33"));
                break;
            case 2:
                home_bottom_tab_iv_found.setImageResource(R.drawable.home_bottom_tab_found_pressed);
                home_bottom_tab_tv_found.setTextColor(Color.parseColor("#33cc33"));
                break;
            case 3:
                home_bottom_tab_iv_me.setImageResource(R.drawable.home_bottom_tab_me_pressed);
                home_bottom_tab_tv_me.setTextColor(Color.parseColor("#33cc33"));
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 当前Activit的点击事件
     * @param view 当前的点击的View
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.home_bottom_tab_ll_wechat:
                viewPager.setCurrentItem(0);
                break;
            case R.id.home_bottom_tab_ll_address_list:
                viewPager.setCurrentItem(1);
                break;
            case R.id.home_bottom_tab_ll_found:
                viewPager.setCurrentItem(2);
                break;
            case R.id.home_bottom_tab_ll_me:
                viewPager.setCurrentItem(3);
                break;
            case R.id.home_tab_top_new_task:
//                TODO 弹出popupwindow，并实现相应的点击事件
                break;
            case R.id.home_tab_top_search:
//                TODO 实现搜索功能
                break;
        }
    }

    /**
     * 清除所有的底部导航栏选中状态
     */
    private void clearSelection() {
        home_bottom_tab_iv_wechat.setImageResource(R.drawable.home_bottom_tab_wechat_normal);
        home_bottom_tab_tv_wechat.setTextColor(Color.parseColor("#999999"));

        home_bottom_tab_iv_address_list.setImageResource(R.drawable.home_bottom_tab_address_list_normal);
        home_bottom_tab_tv_address_list.setTextColor(Color.parseColor("#999999"));

        home_bottom_tab_iv_found.setImageResource(R.drawable.home_bottom_tab_found_normal);
        home_bottom_tab_tv_found.setTextColor(Color.parseColor("#999999"));

        home_bottom_tab_iv_me.setImageResource(R.drawable.home_bottom_tab_me_normal);
        home_bottom_tab_tv_me.setTextColor(Color.parseColor("#999999"));
    }

}
