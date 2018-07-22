package com.fucaijin.weixin_fucaijin.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
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
import com.fucaijin.weixin_fucaijin.fragment.HomeAddressListFragment;
import com.fucaijin.weixin_fucaijin.fragment.HomeFoundPageFragment;
import com.fucaijin.weixin_fucaijin.fragment.HomeMeFragment;
import com.fucaijin.weixin_fucaijin.fragment.HomeWechatFragment;
import com.fucaijin.weixin_fucaijin.global.WeixinApplication;
import com.fucaijin.weixin_fucaijin.utils.ConvertUtils;
import com.fucaijin.weixin_fucaijin.utils.Http;
import com.fucaijin.weixin_fucaijin.utils.JudgementUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.fucaijin.weixin_fucaijin.global.WeixinApplication.HTTP_HOST_URL;
import static com.fucaijin.weixin_fucaijin.global.WeixinApplication.mContext;
import static com.fucaijin.weixin_fucaijin.utils.Http.responseHashMap;

public class HomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener, View.OnLongClickListener {
    private static final String HTTP_POST_URL_GET_FRIENDS_INFO = HTTP_HOST_URL + "get_friends_info/";
    public static final int HTTP_REQUEST_TYPE_CODE_GET_FRIENDS_INFO = 15;
//    TODO 设置底部按钮滑动时候的渐变细节未完成：应该添加一个中间图层，在滑动到一半的时候边框完全显示，滑动比例超过一般，Pressed状态的图案才开始显示
//    TODO 顶部标题栏的“微信”在有未读消息时候会显示消息数量，例如“微信（1）”
//    TODO 下方按钮的消息提醒设置未开发
//    TODO 新建任务的点击事件未完成

    private ViewPager mViewPager;
    private List<Fragment> fragmentList;

    private ImageView homeBottomTabIvWechatNormal;
    private ImageView homeBottomTabIvWechatMiddle;
    private ImageView homeBottomTabIvWechatPressed;
    private ImageView homeBottomTabIvAddressListNormal;
    private ImageView homeBottomTabIvAddressListMiddle;
    private ImageView homeBottomTabIvAddressListPressed;
    private ImageView homeBottomTabIvFoundNormal;
    private ImageView homeBottomTabIvFoundMiddle;
    private ImageView homeBottomTabIvFoundPressed;
    private ImageView homeBottomTabIvMeNormal;
    private ImageView homeBottomTabIvMeMiddle;
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
    private HomeAddressListFragment homeAddressListFragment;
    private boolean isShowIndexBar;
    private FrameLayout homeSearchPage;
    private boolean isHomeSearchPageOpened;
    private LinearLayout searchAppointContentRoot;
    private EditText searchPageInputEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initUI();
        updateData();
    }

    private void updateData() {
        boolean networkAvailable = JudgementUtils.isNetworkAvailable(this);
        if (!networkAvailable) {
            Toast.makeText(mContext, "请连接网络...", Toast.LENGTH_SHORT).show();
        } else if (WeixinApplication.isIsFirstRun()) {
//            是首次登录就请求好友数据(头像，昵称)，以及之前好友发送自己未收到的消息

            firstTimeRequest();
//            把首次登录设置为False，下次再进来的时候就不需要请求好友头像和昵称这些了。
            WeixinApplication.setConfigBoolean("is_first_run", false);
            WeixinApplication.setConfigBoolean("is_logined", true);
        } else {
//            TODO 如果不是首次登录，就请求未接收到的数据即可

        }

    }

    private void firstTimeRequest() {
        requestFriendsInfo();//TODO 未完成 请求好友信息(头像，昵称，签名等)
        updateUnReceiveMsg();//TODO 未完成 请求之前未收到的消息
    }

    /**
     * 请求之前未收到的消息
     */
    private void updateUnReceiveMsg() {
        //TODO 未完成 请求之前未收到的消息

    }

    /**
     * 请求好友信息(头像，昵称，签名等)
     */
    private void requestFriendsInfo() {
        //TODO 未完成 请求好友信息(头像，昵称，签名等)
        HashMap<Object, Object> getFriendsInfoMap = new HashMap<>();
        getFriendsInfoMap.put("url", HTTP_POST_URL_GET_FRIENDS_INFO);
        getFriendsInfoMap.put("phone", WeixinApplication.getConfig("user_phone"));

        HashMap hashMap = Http.postServer(HTTP_REQUEST_TYPE_CODE_GET_FRIENDS_INFO, getFriendsInfoMap);
        responseHashMap = null;//得到返回的数据后，清空Http类的请求数据，以便判断下次是否请求到数据


    }

    /**
     * 获取所有需要用到的控件
     */
    private void initUI() {
//        获取主页面的根布局
        homeActivityRootLl = (LinearLayout) findViewById(R.id.activity_home);

//        获取标题栏,以及标题栏的文字，搜索按钮，新建任务按钮
        initHomeTopTabUi();

//        初始化搜索页面
        initSearchPageUi();

//        初始化下方导航栏，并设置监听(点击)事件
        initBottomTabButtonUi();

//        找到ViewPager，并准备数据(适配器),然后给ViewPager设置适配器即可
        initCenterViewPagerUi();
    }

    /**
     * 初始化中间的ViewPager
     * （包含了往ViewPager填充四个Fragment，并且给ViewPager设置滑动监听、设置指定的默认页面）
     */
    private void initCenterViewPagerUi() {
        mViewPager = (ViewPager) findViewById(R.id.home_view_pager);

        Bundle foundFragmentBundle = new Bundle();
        foundFragmentBundle.putIntArray("foundIconArray", foundIconArray);
        foundFragmentBundle.putStringArray("foundTextArray", foundTextArray);

        homeAddressListFragment = new HomeAddressListFragment();
        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeWechatFragment());
        fragmentList.add(homeAddressListFragment);
        fragmentList.add(HomeFoundPageFragment.getInstance(foundIconArray, foundTextArray));
        fragmentList.add(new HomeMeFragment());
        mPagerAdapter = new HomeFragmentAdapter(getSupportFragmentManager(), fragmentList);

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setCurrentItem(0);//设置ViewPager的初始页面
        setTabSelection(0);//设置ViewPager的初始页面
    }

    /**
     * 初始化底部的四个按钮
     * （获取四个控件的根布局，并设置点击事件，
     * 然后获取每个按钮的三张不同状态的图片，以及两种不同状态的文字）
     */
    private void initBottomTabButtonUi() {
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
        homeBottomTabIvWechatMiddle = (ImageView) findViewById(R.id.home_bottom_tab_iv_wechat_middle);
        homeBottomTabIvWechatPressed = (ImageView) findViewById(R.id.home_bottom_tab_iv_wechat_pressed);

        homeBottomTabIvAddressListNormal = (ImageView) findViewById(R.id.home_bottom_tab_iv_address_list_normal);
        homeBottomTabIvAddressListMiddle = (ImageView) findViewById(R.id.home_bottom_tab_iv_address_list_middle);
        homeBottomTabIvAddressListPressed = (ImageView) findViewById(R.id.home_bottom_tab_iv_address_list_pressed);

        homeBottomTabIvFoundNormal = (ImageView) findViewById(R.id.home_bottom_tab_iv_found_normal);
        homeBottomTabIvFoundMiddle = (ImageView) findViewById(R.id.home_bottom_tab_iv_found_middle);
        homeBottomTabIvFoundPressed = (ImageView) findViewById(R.id.home_bottom_tab_iv_found_pressed);

        homeBottomTabIvMeNormal = (ImageView) findViewById(R.id.home_bottom_tab_iv_me_normal);
        homeBottomTabIvMeMiddle = (ImageView) findViewById(R.id.home_bottom_tab_iv_me_middle);
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
    }

    /**
     * 初始化搜索页面
     * （搜索页面的根布局、下方两行文字即文字标题的根布局，
     * 搜索框的根布局，返回按钮的根布局，语音搜索及清空输入框的控件；
     * 并为返回按钮、清空输入框按钮设置点击事件，给输入框添加内容变化监听事件）
     */
    private void initSearchPageUi() {
        homeSearchPage = (FrameLayout) findViewById(R.id.home_search_page_ll);
        searchAppointContentRoot = (LinearLayout) findViewById(R.id.search_appoint_content_root);
        searchPageInputEt = (EditText) findViewById(R.id.search_page_input_et);
        RelativeLayout searchPageBack = (RelativeLayout) findViewById(R.id.search_page_back_btn_rl);
        searchPageBack.setOnClickListener(this);
        final ImageView searchPageVoiceSearch = (ImageView) findViewById(R.id.search_page_voice_btn_iv);
        final ImageView searchPageClearEditText = (ImageView) findViewById(R.id.search_page_clear_edit_text_btn_iv);
        searchPageClearEditText.setOnClickListener(this);

//        给输入框设置文本变化监听器
        searchPageInputEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)) {
                    searchPageClearEditText.setVisibility(View.GONE);
                    searchPageVoiceSearch.setVisibility(View.VISIBLE);
                } else {
                    searchPageVoiceSearch.setVisibility(View.GONE);
                    searchPageClearEditText.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * 初始化顶部标题栏（顶部标题栏的根布局、标题、搜索按钮、新建任务按钮）
     */
    private void initHomeTopTabUi() {
        homeTopTabRootRl = (RelativeLayout) findViewById(R.id.home_top_tab_root_rl);
        homeTopTabTitleTv = (TextView) findViewById(R.id.home_top_tab_title_tv);
        homeTopTabSearchIv = (ImageView) findViewById(R.id.home_top_tab_search_iv);
        homeTopTabNewTaskIv = (ImageView) findViewById(R.id.home_top_tab_new_task_iv);

//        给搜索按钮设置点击和长按事件，给新任务按钮设置点击事件
        homeTopTabSearchIv.setOnClickListener(this);
        homeTopTabSearchIv.setOnLongClickListener(this);
        homeTopTabNewTaskIv.setOnClickListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        switch (position) {
            case 0:
                homeBottomTabIvWechatNormal.setImageAlpha((int) (255f * positionOffset));
                homeBottomTabIvWechatPressed.setImageAlpha((int) (255f * (1 - positionOffset)));
                homeBottomTabIvAddressListNormal.setImageAlpha((int) (255f * (1 - positionOffset)));
                homeBottomTabIvAddressListPressed.setImageAlpha((int) (255f * positionOffset));
//
                homeBottomTabTvWechatNormal.setAlpha(positionOffset);
                homeBottomTabTvWechatPressed.setAlpha(1 - positionOffset);
                homeBottomTabTvAddressListNormal.setAlpha(1 - positionOffset);
                homeBottomTabTvAddressListPressed.setAlpha(positionOffset);

//                下面注释代码是实现二级渐变过渡的效果，但有闪烁的Bug,暂时注释
//                (即实现当在微信页面，手指向左滑的时候，一共有2步（以滑到屏幕的一半为分界线）:
//                  1.微信选中状态从完全显示到(滑到屏幕一半宽度的时候)完全消失，同时中间状态(和未选中图案一致但颜色是绿色)从完全消失到完全显示
//                  2.中间状态(和未选中图案一致但颜色是绿色)从完全显示到完全消失，同时未选中状态从完全消失到完全显示)
//                if (positionOffset < 0.5) {
//                      //手指往左滑，尚未滑到一半，
//                    homeBottomTabIvWechatPressed.setImageAlpha((int) (255f * (1 - positionOffset * 2)));
//                    homeBottomTabIvWechatMiddle.setImageAlpha((int) (255f * positionOffset * 2));
//                } else {
//                    homeBottomTabIvWechatMiddle.setImageAlpha((int) (255f * (1 - (positionOffset - 0.5) * 2)));
//                    homeBottomTabIvWechatNormal.setImageAlpha((int) (255f * (positionOffset - 0.5) * 2));
//                }

//                在通讯录向微信页滑动过程中也会进入此代码块
                if (isShowIndexBar) {
//                    如果页面处于滑动状态，则隐藏侧边快速检索栏。记录显示或隐藏状态是避免滑动过程中过多调用隐藏方法影响性能
                    homeAddressListFragment.hideIndexBar();
                    homeAddressListFragment.hideCurrentBigLetter();
                    isShowIndexBar = false;
                }
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


//                如果当前页面是通讯录页(position为1)，并且页面滑动停止的时候，就显示右侧快速检索栏
                if (positionOffset == 0) {
                    homeAddressListFragment.showIndexBar();
                    isShowIndexBar = true;
                } else if (isShowIndexBar) {
//                    如果页面处于滑动状态，则隐藏右侧快速检索栏。记录显示或隐藏状态是避免滑动过程中过多调用隐藏方法影响性能
                    homeAddressListFragment.hideIndexBar();
                    homeAddressListFragment.hideCurrentBigLetter();
                    isShowIndexBar = false;
                }

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
//        更改底部Tab的颜色
        setTabSelection(position);

//        如果是滑到通讯录页面，则显示通讯录页的右侧快速检索栏
        if (position == 1) {
            homeAddressListFragment.showIndexBar();
            isShowIndexBar = true;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
//        0：什么都没做 1：开始滑动 2：滑动结束
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
//                设置指定ViewPager的页面是那一页，第二个参数是设置是否需要滑动（false是不滑动，直接跳到那个页面）
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
//                隐藏主页面，显示搜索页面
                showSearchPage();
                break;

            case R.id.home_top_tab_new_task_popup_window_root:
//                如果点击的是popupWindow的外部，就关闭popupWindow
                popupWindow.dismiss();
                popupWindow = null;
                break;

            case R.id.search_page_back_btn_rl:
//                隐藏主搜索页面，显示主页面
                hideSearchPage();
                break;

            case R.id.search_page_clear_edit_text_btn_iv:
//                清空输入框
                searchPageInputEt.getText().clear();
                break;
        }
    }

    /**
     * 隐藏主页面，显示搜索页面
     */
    private void showSearchPage() {
        homeActivityRootLl.setVisibility(View.GONE);
        homeSearchPage.setVisibility(View.VISIBLE);

//        设置输入框自动获取焦点并弹出键盘 TODO 此处有BUG，首次进去的时候没有自动弹出键盘，第二次以后才能弹出
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(searchPageInputEt, 0);

//        页面弹出的动画效果
        AnimatorSet animatorSet = new AnimatorSet();//组合动画
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(homeSearchPage, "scaleX", 0.9f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(homeSearchPage, "scaleY", 0.9f, 1f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(homeSearchPage, "alpha", 0.5f, 1f);

        animatorSet.setDuration(250);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.play(scaleX).with(scaleY).with(alpha);//动画同时开始
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                searchAppointContentRoot.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animatorSet.start();

        isHomeSearchPageOpened = true;
    }

    /**
     * 隐藏搜索页面，显示主页面
     */
    private void hideSearchPage() {
//        收起键盘
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

//        隐藏搜索页面，并显示主页面
        searchAppointContentRoot.setVisibility(View.GONE);
        homeSearchPage.setVisibility(View.GONE);
        homeActivityRootLl.setVisibility(View.VISIBLE);

    }

    /**
     * 弹出主页面右上角“+”号 新建任务的popupWindow
     */
    private void showNewTaskPopupWindow() {
//        TODO 并实现popupwindow相应的点击事件

//            弹出popupwindow
//            1.导入popupwindow的布局，并从布局找到ListView
        View popupWindowLayout = LayoutInflater.from(this).inflate(R.layout.home_top_tab_new_task_popupwindow_layout, null);
        ListView popupWindowListView = popupWindowLayout.findViewById(R.id.home_top_tab_new_task_popup_window_lv);

//        2.为listView准备数据(图标和文字)，并将每条数据存入一个列表中，
//          列表中的对象存着每条数据的图标、文字地址，然后传入ListView的适配器中使用，
//          最后将Adapter绑定ListView
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

//        找到popupWindow的(填充屏幕)根布局的id，并给根布局设置点击事件，
//        如果不是点击popupWindow中的ListView的话，就让popupWindow消失
//        (让popupWindow充满屏幕原因1.实现点击外部消失 2.点击外部时候禁止外部的其他不相关控件响应)
        View popupWindowRoot = popupWindowLayout.findViewById(R.id.home_top_tab_new_task_popup_window_root);
        popupWindowRoot.setOnClickListener(this);

//                创建popupWindow，并指定其显示位置
        popupWindow = new PopupWindow(popupWindowLayout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//        设置popupWindow动画(放大弹出和缩小消失,并伴随透明度的变化)
        popupWindow.setAnimationStyle(R.style.custom_popup_window_anim_style);
        popupWindow.showAtLocation(homeActivityRootLl, Gravity.END, 0, 0);
        popupWindow.update();

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
                setBottomTabSelect(homeBottomTabIvWechatNormal, homeBottomTabIvWechatMiddle,
                        homeBottomTabIvWechatPressed, homeBottomTabTvWechatNormal, homeBottomTabTvWechatPressed);
                break;
            case 1:
                setBottomTabSelect(homeBottomTabIvAddressListNormal, homeBottomTabIvAddressListMiddle,
                        homeBottomTabIvAddressListPressed, homeBottomTabTvAddressListNormal, homeBottomTabTvAddressListPressed);
                break;
            case 2:
                setBottomTabSelect(homeBottomTabIvFoundNormal, homeBottomTabIvFoundMiddle,
                        homeBottomTabIvFoundPressed, homeBottomTabTvFoundNormal, homeBottomTabTvFoundPressed);
                break;
            case 3:
                setBottomTabSelect(homeBottomTabIvMeNormal, homeBottomTabIvMeMiddle,
                        homeBottomTabIvMePressed, homeBottomTabTvMeNormal, homeBottomTabTvMePressed);
                break;
        }
    }

    /**
     * 设置底部的导航按钮为选中状态
     *
     * @param normalView  未选中状态的图标
     * @param pressedView 滑到一半时候的图标
     * @param pressedView 选中的图标
     * @param normalText  未选中的文字
     * @param pressedText 选中的文字
     */
    private void setBottomTabSelect(ImageView normalView, ImageView middleView, ImageView pressedView, TextView normalText, TextView pressedText) {
//        如果选中页面，页面底部标签的颜色应该是显示绿色，灰色则完全透明
        normalView.setImageAlpha(0);
        pressedView.setImageAlpha(255);
        middleView.setImageAlpha(255);

        normalText.setAlpha(0f);
        pressedText.setAlpha(1f);
    }

    /**
     * 清除所有的底部导航栏选中状态，让所有选项显示未选中状态（颜色）
     */
    private void clearSelection() {
        homeBottomTabIvWechatNormal.setImageAlpha(255);
        homeBottomTabTvWechatNormal.setAlpha(1f);
        homeBottomTabIvWechatPressed.setImageAlpha(0);
        homeBottomTabIvWechatMiddle.setImageAlpha(0);
        homeBottomTabTvWechatPressed.setAlpha(0f);

        homeBottomTabIvAddressListNormal.setImageAlpha(255);
        homeBottomTabTvAddressListNormal.setAlpha(1f);
        homeBottomTabIvAddressListPressed.setImageAlpha(0);
        homeBottomTabIvAddressListMiddle.setImageAlpha(0);
        homeBottomTabTvAddressListPressed.setAlpha(0f);

        homeBottomTabIvFoundNormal.setImageAlpha(255);
        homeBottomTabTvFoundNormal.setAlpha(1f);
        homeBottomTabIvFoundPressed.setImageAlpha(0);
        homeBottomTabIvFoundMiddle.setImageAlpha(0);
        homeBottomTabTvFoundPressed.setAlpha(0f);

        homeBottomTabIvMeNormal.setImageAlpha(255);
        homeBottomTabTvMeNormal.setAlpha(1f);
        homeBottomTabIvMePressed.setImageAlpha(0);
        homeBottomTabIvMeMiddle.setImageAlpha(0);
        homeBottomTabTvMePressed.setAlpha(0f);
    }

    /**
     * 重写返回键，如果当前显示着新建任务的popupWindow的话就关闭它，否则就默认操作
     */
    @Override
    public void onBackPressed() {
        if (popupWindow != null) {
            popupWindow.dismiss();
            popupWindow = null;
        } else {
//            如果当前搜索页面是打开状态，隐藏主搜索页面，显示主页面
            if (isHomeSearchPageOpened) {
                hideSearchPage();
                return;
            }
            super.onBackPressed();
        }
    }

    @Override
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.home_top_tab_search_iv:
//                弹出Toast，设置Toast的位置，高度是获取标题栏的高度
                Toast toast = Toast.makeText(this, "搜索", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.END, ConvertUtils.dp2px(this, 92), getResources().getDimensionPixelOffset(R.dimen.top_bar_height));//此处的92是:新建任务按钮长度 + 搜索按钮长度的一半 = 92dp
                toast.show();

//                设置震动
                Vibrator vibrator = (Vibrator) this.getSystemService(this.VIBRATOR_SERVICE);
                vibrator.vibrate(50);
                break;
        }
        return false;
    }
}
