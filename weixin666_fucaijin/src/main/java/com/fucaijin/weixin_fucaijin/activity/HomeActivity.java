package com.fucaijin.weixin_fucaijin.activity;

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
//    TODO 设置底部按钮滑动时候的渐变细节未完成：应该添加一个中间图层，在滑动到一半的时候边框完全显示，滑动比例超过一般，Pressed状态的图案才开始显示
    private ViewPager mViewPager;
    private List<Fragment> fragmentList;

    private ImageView homeBottomTabIvWechatNormal;
    private ImageView homeBottomTabIvWechatPressed;
    private ImageView homeBottomTabIvAddressListNormal;
    private ImageView homeBottomTabIvAddressListPressed;
    private ImageView homeBottomTabIvFoundNormal;
    private ImageView homeBottomTabIvFoundPressed;
    private ImageView homeBottomTabIvMeNormal;
    private ImageView homeBottomTabIvMePressed;

    private TextView homeBottomTabTvWechatNormal;
    private TextView homeBottomTabTvWechatPressed;
    private TextView homeBottomTabTvAddressListNormal;
    private TextView homeBottomTabTvAddressListPressed;
    private TextView homeBottomTabTvFoundNormal;
    private TextView homeBottomTabTvFoundPressed;
    private TextView homeBottomTabTvMeNormal;
    private TextView homeBottomTabTvMePressed;
    private HomeFragmentAdapter mPagerAdapter;

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
//        获取下方按钮，并设置监听点击事件
        LinearLayout homeBottomTabLlWechat = (LinearLayout) findViewById(R.id.home_bottom_tab_ll_wechat);
        LinearLayout homeBottomTabLlAddressList = (LinearLayout) findViewById(R.id.home_bottom_tab_ll_address_list);
        LinearLayout homeBottomTabLlFound = (LinearLayout) findViewById(R.id.home_bottom_tab_ll_found);
        LinearLayout homeBottomTabLlMe = (LinearLayout) findViewById(R.id.home_bottom_tab_ll_me);

        homeBottomTabLlWechat.setOnClickListener(this);
        homeBottomTabLlAddressList.setOnClickListener(this);
        homeBottomTabLlFound.setOnClickListener(this);
        homeBottomTabLlMe.setOnClickListener(this);

//        获取下方导航栏的图片(每张图片包含点击和正常两张的叠加)
        homeBottomTabIvWechatNormal = (ImageView) findViewById(R.id.home_bottom_tab_iv_wechat_normal);
        homeBottomTabIvWechatPressed = (ImageView) findViewById(R.id.home_bottom_tab_iv_wechat_pressed);
        homeBottomTabIvAddressListNormal = (ImageView) findViewById(R.id.home_bottom_tab_iv_address_list_normal);
        homeBottomTabIvAddressListPressed = (ImageView) findViewById(R.id.home_bottom_tab_iv_address_list_pressed);
        homeBottomTabIvFoundNormal = (ImageView) findViewById(R.id.home_bottom_tab_iv_found_normal);
        homeBottomTabIvFoundPressed = (ImageView) findViewById(R.id.home_bottom_tab_iv_found_pressed);
        homeBottomTabIvMeNormal = (ImageView) findViewById(R.id.home_bottom_tab_iv_me_normal);
        homeBottomTabIvMePressed = (ImageView) findViewById(R.id.home_bottom_tab_iv_me_pressed);

//        获取下方导航栏的文字(每段文字包含点击和正常两张的叠加)
        homeBottomTabTvWechatNormal = (TextView) findViewById(R.id.home_bottom_tab_tv_wechat_normal);
        homeBottomTabTvWechatPressed = (TextView) findViewById(R.id.home_bottom_tab_tv_wechat_pressed);
        homeBottomTabTvAddressListNormal = (TextView) findViewById(R.id.home_bottom_tab_tv_address_list_normal);
        homeBottomTabTvAddressListPressed = (TextView) findViewById(R.id.home_bottom_tab_tv_address_list_pressed);
        homeBottomTabTvFoundNormal = (TextView) findViewById(R.id.home_bottom_tab_tv_found_normal);
        homeBottomTabTvFoundPressed = (TextView) findViewById(R.id.home_bottom_tab_tv_found_pressed);
        homeBottomTabTvMeNormal = (TextView) findViewById(R.id.home_bottom_tab_tv_me_normal);
        homeBottomTabTvMePressed = (TextView) findViewById(R.id.home_bottom_tab_tv_me_pressed);

//        找到ViewPager，并准备数据(适配器),然后给ViewPager设置适配器即可
        mViewPager = (ViewPager) findViewById(R.id.home_view_pager);

        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeWechatFragment());
        fragmentList.add(new HomeFragmentAddressList());
        fragmentList.add(new HomeFragmentFound());
        fragmentList.add(new HomeFragmentMe());
        mPagerAdapter = new HomeFragmentAdapter(getSupportFragmentManager(), fragmentList);

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setCurrentItem(0);//设置ViewPager的初始页面
        setTabSelection(0);//设置ViewPager的初始页面
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        switch (position){
            case 0:
                homeBottomTabIvWechatNormal.setImageAlpha((int) (255f*positionOffset));
                homeBottomTabIvWechatPressed.setImageAlpha((int) (255f*(1-positionOffset)));
                homeBottomTabTvWechatNormal.setAlpha(positionOffset);
                homeBottomTabTvWechatPressed.setAlpha(1-positionOffset);

                homeBottomTabIvAddressListNormal.setImageAlpha((int) (255f*(1-positionOffset)));
                homeBottomTabIvAddressListPressed.setImageAlpha((int) (255f*positionOffset));
                homeBottomTabTvAddressListNormal.setAlpha(1-positionOffset);
                homeBottomTabTvAddressListPressed.setAlpha(positionOffset);
                break;

            case 1:
                homeBottomTabIvAddressListNormal.setImageAlpha((int) (255f*positionOffset));
                homeBottomTabIvAddressListPressed.setImageAlpha((int) (255f*(1-positionOffset)));
                homeBottomTabTvAddressListNormal.setAlpha(positionOffset);
                homeBottomTabTvAddressListPressed.setAlpha(1-positionOffset);

                homeBottomTabIvFoundNormal.setImageAlpha((int) (255f*(1-positionOffset)));
                homeBottomTabIvFoundPressed.setImageAlpha((int) (255f*positionOffset));
                homeBottomTabTvFoundNormal.setAlpha(1-positionOffset);
                homeBottomTabTvFoundPressed.setAlpha(positionOffset);
                break;

            case 2:
                homeBottomTabIvFoundNormal.setImageAlpha((int) (255f*positionOffset));
                homeBottomTabIvFoundPressed.setImageAlpha((int) (255f*(1-positionOffset)));
                homeBottomTabTvFoundNormal.setAlpha(positionOffset);
                homeBottomTabTvFoundPressed.setAlpha(1-positionOffset);

                homeBottomTabIvMeNormal.setImageAlpha((int) (255f*(1-positionOffset)));
                homeBottomTabIvMePressed.setImageAlpha((int) (255f*positionOffset));
                homeBottomTabTvMeNormal.setAlpha(1-positionOffset);
                homeBottomTabTvMePressed.setAlpha(positionOffset);
                break;
        }
    }

    @Override
    public void onPageSelected(int position) {
        setTabSelection(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    /**
     * 当前Activit的点击事件
     * @param view 当前的点击的View
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.home_bottom_tab_ll_wechat:
//                设置指定ViewPager的页面是那一页，第二个参数是设置是否需要滑动，还是直接跳到那个页面
                mViewPager.setCurrentItem(0,false);
                break;
            case R.id.home_bottom_tab_ll_address_list:
                mViewPager.setCurrentItem(1,false);
                break;
            case R.id.home_bottom_tab_ll_found:
                mViewPager.setCurrentItem(2,false);
                break;
            case R.id.home_bottom_tab_ll_me:
                mViewPager.setCurrentItem(3,false);
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
     * 根据当前选中的页面，来改变底部的导航栏按钮颜色
     * @param position 当前选中的页面
     */
    private void setTabSelection(int position) {
//        先设置所有的按钮为未选中状态，然后判断当前的点击按钮是哪个，将其设置为选中状态
        clearSelection();
        switch (position) {
            case 0:
                setBottomTabSelect(homeBottomTabIvWechatNormal, homeBottomTabIvWechatPressed, homeBottomTabTvWechatNormal, homeBottomTabTvWechatPressed);
                break;
            case 1:
                setBottomTabSelect(homeBottomTabIvAddressListNormal, homeBottomTabIvAddressListPressed, homeBottomTabTvAddressListNormal, homeBottomTabTvAddressListPressed);
                break;
            case 2:
                setBottomTabSelect(homeBottomTabIvFoundNormal, homeBottomTabIvFoundPressed, homeBottomTabTvFoundNormal, homeBottomTabTvFoundPressed);
                break;
            case 3:
                setBottomTabSelect(homeBottomTabIvMeNormal, homeBottomTabIvMePressed, homeBottomTabTvMeNormal, homeBottomTabTvMePressed);
                break;
        }
    }

    /**
     * 设置底部的导航按钮为选中状态
     * @param normalView 未选中的图标
     * @param pressedView 选中的图标
     * @param normalText  未选中的文字
     * @param pressedText  选中的文字
     */
    private void setBottomTabSelect(ImageView normalView, ImageView pressedView, TextView normalText, TextView pressedText) {
        normalView.setImageAlpha(0);
        pressedView.setImageAlpha(255);
        normalText.setAlpha(0f);
        pressedText.setAlpha(1f);
    }

    /**
     * 清除所有的底部导航栏选中状态，让所有选项显示未选中状态（颜色）
     */
    private void clearSelection() {
        homeBottomTabIvWechatNormal.setImageAlpha(255);
        homeBottomTabIvWechatPressed.setImageAlpha(0);
        homeBottomTabTvWechatNormal.setAlpha(1f);
        homeBottomTabTvWechatPressed.setAlpha(0f);

        homeBottomTabIvAddressListNormal.setImageAlpha(255);
        homeBottomTabIvAddressListPressed.setImageAlpha(0);
        homeBottomTabTvAddressListNormal.setAlpha(1f);
        homeBottomTabTvAddressListPressed.setAlpha(0f);

        homeBottomTabIvFoundNormal.setImageAlpha(255);
        homeBottomTabIvFoundPressed.setImageAlpha(0);
        homeBottomTabTvFoundNormal.setAlpha(1f);
        homeBottomTabTvFoundPressed.setAlpha(0f);

        homeBottomTabIvMeNormal.setImageAlpha(255);
        homeBottomTabIvMePressed.setImageAlpha(0);
        homeBottomTabTvMeNormal.setAlpha(1f);
        homeBottomTabTvMePressed.setAlpha(0f);
    }

}
