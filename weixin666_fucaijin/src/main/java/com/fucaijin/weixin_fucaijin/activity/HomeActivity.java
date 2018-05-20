package com.fucaijin.weixin_fucaijin.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fucaijin.weixin_fucaijin.R;
import com.fucaijin.weixin_fucaijin.adapter.HomeFragmentAdapter;
import com.fucaijin.weixin_fucaijin.adapter.HomeNewTaskPopupWindowAdapter;
import com.fucaijin.weixin_fucaijin.data.HomeNewTaskPopulWindowData;
import com.fucaijin.weixin_fucaijin.fragment.HomeFragmentAddressList;
import com.fucaijin.weixin_fucaijin.fragment.HomeFoundPageFragment;
import com.fucaijin.weixin_fucaijin.fragment.HomeFragmentMe;
import com.fucaijin.weixin_fucaijin.fragment.HomeWechatFragment;
import com.fucaijin.weixin_fucaijin.global.WeixinApplication;
import com.fucaijin.weixin_fucaijin.utils.ConvertUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener, View.OnLongClickListener {
//    TODO 设置底部按钮滑动时候的渐变细节未完成：应该添加一个中间图层，在滑动到一半的时候边框完全显示，滑动比例超过一般，Pressed状态的图案才开始显示
//    TODO 顶部标题栏的“微信”在有未读消息时候会显示消息数量，例如“微信（1）”
//    TODO 下方按钮的消息提醒设置未开发
//    TODO 标题栏的搜索功能未开发
//    TODO 新建任务的点击事件未完成
//    TODO 通讯录界面未开发
//    TODO 发现界面未开发
//    TODO 我界面未开发

    private ViewPager mViewPager;
    private List<Fragment> fragmentList;
    private Context mContext;

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
    private TextView homeTopTabTitleTv;
    private ImageView homeTopTabSearchIv;
    private ImageView homeTopTabNewTaskIv;
    private RelativeLayout homeTopTabRootRl;
    private LinearLayout homeActivityRootLl;
    private PopupWindow popupWindow;
    private int[] foundIconArray = {R.drawable.found_frag_circle_of_friends, R.drawable.found_frag_scan, R.drawable.found_frag_shake,
            R.drawable.found_frag_look, R.drawable.found_frag_search, R.drawable.found_frag_people_nearby, R.drawable.found_frag_drift_bottle,
            R.drawable.found_frag_shoping, R.drawable.found_frag_games, R.drawable.found_frag_mini_program};
    private String[] foundTextArray = {"朋友圈", "扫一扫", "摇一摇", "看一看",
            "搜一搜", "附近的人", "漂流瓶",
            "购物", "游戏", "小程序"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mContext =  WeixinApplication.getmContext();
        initUI();
    }

    /**
     * 获取所有需要用到的控件
     */
    private void initUI() {
        homeActivityRootLl = (LinearLayout) findViewById(R.id.activity_home);
//        获取标题栏,以及标题栏的文字，搜索按钮，新建任务按钮
        homeTopTabRootRl = (RelativeLayout) findViewById(R.id.home_top_tab_root_rl);
        homeTopTabTitleTv = (TextView) findViewById(R.id.home_top_tab_title_tv);
        homeTopTabSearchIv = (ImageView) findViewById(R.id.home_top_tab_search_iv);
        homeTopTabNewTaskIv = (ImageView) findViewById(R.id.home_top_tab_new_task_iv);

        homeTopTabSearchIv.setOnClickListener(this);
        homeTopTabSearchIv.setOnLongClickListener(this);
        homeTopTabNewTaskIv.setOnClickListener(this);

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

        Bundle foundFragmentBundle = new Bundle();
        foundFragmentBundle.putIntArray("foundIconArray",foundIconArray);
        foundFragmentBundle.putStringArray("foundTextArray",foundTextArray);

        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeWechatFragment());
        fragmentList.add(new HomeFragmentAddressList());
        fragmentList.add(HomeFoundPageFragment.getInstance(foundIconArray,foundTextArray));
        fragmentList.add(new HomeFragmentMe());
        mPagerAdapter = new HomeFragmentAdapter(getSupportFragmentManager(), fragmentList);

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setCurrentItem(0);//设置ViewPager的初始页面
        setTabSelection(0);//设置ViewPager的初始页面
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        switch (position) {
            case 0:
                homeBottomTabIvWechatNormal.setImageAlpha((int) (255f * positionOffset));
                homeBottomTabIvWechatPressed.setImageAlpha((int) (255f * (1 - positionOffset)));
                homeBottomTabTvWechatNormal.setAlpha(positionOffset);
                homeBottomTabTvWechatPressed.setAlpha(1 - positionOffset);

                homeBottomTabIvAddressListNormal.setImageAlpha((int) (255f * (1 - positionOffset)));
                homeBottomTabIvAddressListPressed.setImageAlpha((int) (255f * positionOffset));
                homeBottomTabTvAddressListNormal.setAlpha(1 - positionOffset);
                homeBottomTabTvAddressListPressed.setAlpha(positionOffset);
                break;

            case 1:
                homeBottomTabIvAddressListNormal.setImageAlpha((int) (255f * positionOffset));
                homeBottomTabIvAddressListPressed.setImageAlpha((int) (255f * (1 - positionOffset)));
                homeBottomTabTvAddressListNormal.setAlpha(positionOffset);
                homeBottomTabTvAddressListPressed.setAlpha(1 - positionOffset);

                homeBottomTabIvFoundNormal.setImageAlpha((int) (255f * (1 - positionOffset)));
                homeBottomTabIvFoundPressed.setImageAlpha((int) (255f * positionOffset));
                homeBottomTabTvFoundNormal.setAlpha(1 - positionOffset);
                homeBottomTabTvFoundPressed.setAlpha(positionOffset);
                break;

            case 2:
                homeBottomTabIvFoundNormal.setImageAlpha((int) (255f * positionOffset));
                homeBottomTabIvFoundPressed.setImageAlpha((int) (255f * (1 - positionOffset)));
                homeBottomTabTvFoundNormal.setAlpha(positionOffset);
                homeBottomTabTvFoundPressed.setAlpha(1 - positionOffset);

                homeBottomTabIvMeNormal.setImageAlpha((int) (255f * (1 - positionOffset)));
                homeBottomTabIvMePressed.setImageAlpha((int) (255f * positionOffset));
                homeBottomTabTvMeNormal.setAlpha(1 - positionOffset);
                homeBottomTabTvMePressed.setAlpha(positionOffset);
                break;
        }
    }

    @Override
    public void onPageSelected(int position) {
        setTabSelection(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    /**
     * 当前Activit的点击事件
     *
     * @param view 当前的点击的View
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_bottom_tab_ll_wechat:
//                设置指定ViewPager的页面是那一页，第二个参数是设置是否需要滑动，还是直接跳到那个页面
                mViewPager.setCurrentItem(0, false);
                break;
            case R.id.home_bottom_tab_ll_address_list:
                mViewPager.setCurrentItem(1, false);
                break;
            case R.id.home_bottom_tab_ll_found:
                mViewPager.setCurrentItem(2, false);
                break;
            case R.id.home_bottom_tab_ll_me:
                mViewPager.setCurrentItem(3, false);
                break;
            case R.id.home_top_tab_new_task_iv:
                showNewTaskPopupWindow();
                break;
            case R.id.home_top_tab_search_iv:
//                TODO 实现搜索功能
                break;

            case R.id.home_top_tab_new_task_popup_window_root:
//                如果点击的是popupwindow的外部，就关闭popupWindow
                popupWindow.dismiss();
                popupWindow = null;
                break;
        }
    }

    /**
     * 显示主页面右上角“+”号 新建任务的popupWindow
     */
    private void showNewTaskPopupWindow() {
//                TODO 并实现popupwindow相应的点击事件
//                弹出popupwindow
//                1.导入popupwindow的布局，并从布局找到ListView
        View popupWindowLayout = LayoutInflater.from(this).inflate(R.layout.home_top_tab_new_task_popupwindow_layout, null);
        ListView popupWindowListView = popupWindowLayout.findViewById(R.id.home_top_tab_new_task_popup_window_lv);

//                2.为listView准备数据(图标和文字)，并将每条数据存入一个列表中，
//                  列表中的对象存着每条数据的图标、文字地址，然后传入ListView的适配器中使用，
//                  最后将Adapter绑定ListView
        String[] homeNewTaskText = {"发起群聊", "添加朋友", "扫一扫", "收付款", "帮助与反馈"};
        int[] homeNewTaskImageId = {R.drawable.action_bar_new_group_chat, R.drawable.action_bar_add_new_friend, R.drawable.action_bar_scan, R.drawable.action_bar_pay, R.drawable.action_bar_help_and_feedback};
        ArrayList<HomeNewTaskPopulWindowData> populWindowDatas = new ArrayList<>();
        for (int i = 0; i < homeNewTaskText.length; i++) {
            HomeNewTaskPopulWindowData homeNewTaskPopulWindowData = new HomeNewTaskPopulWindowData();
            homeNewTaskPopulWindowData.text = homeNewTaskText[i];
            homeNewTaskPopulWindowData.drawableId = homeNewTaskImageId[i];
            populWindowDatas.add(homeNewTaskPopulWindowData);
        }
        popupWindowListView.setAdapter(new HomeNewTaskPopupWindowAdapter(populWindowDatas));

//                找到popupWindow的(填充屏幕)根布局的id，并给根布局设置点击事件，
//                如果不是点击popupWindow中的ListView的话，就让popupWindow消失
//                (让popupWindow充满屏幕原因1.实现点击外部消失 2.点击外部时候禁止外部的其他不相关控件响应)
        View popupWindowRoot = popupWindowLayout.findViewById(R.id.home_top_tab_new_task_popup_window_root);
        popupWindowRoot.setOnClickListener(this);

//                创建popupWindow，并指定其显示位置
        popupWindow = new PopupWindow(popupWindowLayout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.showAtLocation(homeActivityRootLl, Gravity.END, 0, 0);
    }

    /**
     * 根据当前选中的页面，来改变底部的导航栏按钮颜色
     *
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
     *
     * @param normalView  未选中的图标
     * @param pressedView 选中的图标
     * @param normalText  未选中的文字
     * @param pressedText 选中的文字
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

    /**
     * 重写返回键，如果当前显示着新建任务的popupWindow的话就关闭它，否则就默认操作
     */
    @Override
    public void onBackPressed() {
        if(popupWindow != null){
            popupWindow.dismiss();
            popupWindow = null;
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onLongClick(View view) {
        switch (view.getId()){
            case R.id.home_top_tab_search_iv:
//                长按搜索按钮时的事件

//                弹出Toast，设置Toast的位置，高度是获取标题栏的高度
                Toast toast = Toast.makeText(this, "搜索", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.END, ConvertUtils.dp2px(this,92), getResources().getDimensionPixelOffset(R.dimen.top_bar_height));//此处的92是:新建任务按钮长度 + 搜索按钮长度的一半 = 92dp
                toast.show();

//                设置震动
                Vibrator vibrator = (Vibrator)this.getSystemService(this.VIBRATOR_SERVICE);
                vibrator.vibrate(50);
                break;
        }
        return false;
    }
}
