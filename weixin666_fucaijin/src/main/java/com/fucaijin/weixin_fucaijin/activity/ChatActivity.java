package com.fucaijin.weixin_fucaijin.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fucaijin.weixin_fucaijin.R;
import com.fucaijin.weixin_fucaijin.adapter.MoreFunctionPagerAdapter;

import static com.fucaijin.weixin_fucaijin.global.WeixinApplication.mContext;

public class ChatActivity extends BaseActivity implements TextWatcher, View.OnFocusChangeListener, View.OnClickListener, ViewPager.OnPageChangeListener {

    private ImageView messageEtDivider;
    private EditText messageEt;
    private Button sendMessageBtn;
    private ImageView moreFunctionBtn;
    private boolean messageInputEtLastEmptyStatus = true;
    private RelativeLayout finishChat;
    private TextView nickNameTitle;
    private ListView messageContent;
    private ImageView keyBoardInputBtnIv;
    private ImageView voiceInputBtnIv;
    private Button voiceInputBtn;
    private RelativeLayout moreFunctionRootRl;
    private boolean isMoreFunctionVisible = false;
    private RelativeLayout messageEtRootRl;
    private ImageView navigationTwo;
    private ImageView navigationOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initUI();
        setSwipeBackEnable(true);//开启可以侧滑返回
    }

    private void initUI() {
        initTopBarUi();
        initCenterUi();
        initBottomBarUi();
        initMoreFunctionBarUi();
    }

    /**
     * 初始化底部可展开/收起的更多功能栏
     */
    private void initMoreFunctionBarUi() {
        moreFunctionRootRl = (RelativeLayout) findViewById(R.id.chat_activity_more_function_root_rl);

//        底下展开的更多功能的页面
        ViewPager moreFunctionPager = (ViewPager) findViewById(R.id.chat_activity_more_function_view_pager);
        moreFunctionPager.setAdapter(new MoreFunctionPagerAdapter(getSupportFragmentManager()));
        moreFunctionPager.addOnPageChangeListener(this);

//        最底部两个指示器点
        navigationOne = (ImageView) findViewById(R.id.chat_activity_more_function_navigation_one);
        navigationTwo = (ImageView) findViewById(R.id.chat_activity_more_function_navigation_two);
    }

    /**
     * 底部输入栏的初始化
     */
    private void initBottomBarUi() {
//        文字/语音 输入按钮
        keyBoardInputBtnIv = (ImageView) findViewById(R.id.chat_activity_use_key_board_input_iv);
        voiceInputBtnIv = (ImageView) findViewById(R.id.chat_activity_use_voice_input_iv);
        keyBoardInputBtnIv.setOnClickListener(this);
        voiceInputBtnIv.setOnClickListener(this);

//        文字输入框
        messageEtRootRl = (RelativeLayout) findViewById(R.id.chat_activity_use_key_board_input_et_rl);
        messageEt = (EditText) findViewById(R.id.chat_activity_message_input_et);
        messageEt.addTextChangedListener(this);
        messageEt.setOnFocusChangeListener(this);
        messageEtDivider = (ImageView) findViewById(R.id.chat_activity_message_input_et_divider);


//        语音输入按钮，及长按事件的监听
        voiceInputBtn = (Button) findViewById(R.id.chat_activity_use_voice_input_bt);
        voiceInputBtn.setLongClickable(true);
        voiceInputBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });

//        发送消息/更多功能 按钮
        sendMessageBtn = (Button) findViewById(R.id.chat_activity_send_message_btn);
        moreFunctionBtn = (ImageView) findViewById(R.id.chat_activity_more_function_iv_btn);
        moreFunctionBtn.setOnClickListener(this);


    }

    /**
     * 中间的聊天内容的ListView的初始化
     */
    private void initCenterUi() {
        messageContent = (ListView) findViewById(R.id.chat_activity_message_content_lv);
    }

    /**
     * 顶部的标题栏的控件的初始化
     */
    private void initTopBarUi() {
        finishChat = (RelativeLayout) findViewById(R.id.chat_activity_top_bar_back_btn_rl);
        finishChat.setOnClickListener(this);
        nickNameTitle = (TextView) findViewById(R.id.chat_activity_top_bar_nick_name);
        String nickName = getIntent().getStringExtra("nickName");
        nickNameTitle.setText(nickName);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        boolean isEmpty = TextUtils.isEmpty(editable);
        if (isEmpty != messageInputEtLastEmptyStatus) {
            if (isEmpty) {
                hideSendMessageBtn();
            } else {
                showSendMessageBtn();
            }
            messageInputEtLastEmptyStatus = isEmpty;
        }
    }

    /**
     * 如果输入框不为空，则隐藏更多功能按钮，同时用动画显示发送按钮
     */
    private void showSendMessageBtn() {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(sendMessageBtn, "scaleX", 0.5f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(sendMessageBtn, "scaleY", 0.5f, 1f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(sendMessageBtn, "alpha", 0.5f, 1f);
        animatorSet.setDuration(100);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                moreFunctionBtn.setVisibility(View.INVISIBLE);
                sendMessageBtn.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        animatorSet.play(scaleX).with(scaleY).with(alpha);
        animatorSet.start();
    }

    /**
     * 如果输入框为空，则用动画隐藏发送按钮，同时显示更多功能按钮
     */
    private void hideSendMessageBtn() {
        AnimatorSet animatorSett = new AnimatorSet();
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(sendMessageBtn, "scaleX", 1f, 0.5f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(sendMessageBtn, "scaleY", 1f, 0.5f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(sendMessageBtn, "alpha", 1f, 0.5f);
        animatorSett.setDuration(100);
        animatorSett.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                sendMessageBtn.setVisibility(View.INVISIBLE);
                moreFunctionBtn.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        animatorSett.play(scaleX).with(scaleY).with(alpha);
        animatorSett.start();
    }

    /**
     * 焦点变化的监听回调方法，此处根据焦点的变化来改变输入框下划线的颜色
     *
     * @param view 当前获取焦点的View, 此处只给一个控件(消息输入框)设置了获取焦点监听，因此此处的View就是消息输入框
     * @param b    是获得焦点还是失去焦点
     */
    @Override
    public void onFocusChange(View view, boolean b) {
        if (b) {
//            如果获得焦点则 1.输入框底部下划线变成绿色  2.如果底部更多功能栏已经展开，则闭合
            messageEtDivider.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorLineGreen));
            if(isMoreFunctionVisible){
                moreFunctionRootRl.setVisibility(View.GONE);
            }
        } else {
//            如果失去焦点 输入框底部下划线变灰色
            messageEtDivider.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorLightGray));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chat_activity_top_bar_back_btn_rl:
                // 标题栏 返回键
                finish();
                break;
            case R.id.chat_activity_use_key_board_input_iv:
                // 之前是语音聊天状态，现在按下了文字输入，
                // 此时需要操作：
                // 1. 隐藏圆形键盘按钮
                // 2. 隐藏方形语音输入按钮
                // 3. 显示圆形输入按钮
                // 4. 显示输入框
                // 5. 如果输入框内有文字，则隐藏更多功能按钮，显示发送按钮；如果输入框无文字，则隐藏发送按钮，显示更多功能按钮
                keyBoardInputBtnIv.setVisibility(View.INVISIBLE);   // 1.隐藏圆形键盘按钮
                voiceInputBtn.setVisibility(View.INVISIBLE);        // 2.隐藏方形语音输入按钮

                voiceInputBtnIv.setVisibility(View.VISIBLE);        // 3.显示圆形输入按钮
                messageEtRootRl.setVisibility(View.VISIBLE);        // 4.显示输入框

                if(!TextUtils.isEmpty(messageEt.getText())){
                    showSendMessageBtn();
                }
                break;
            case R.id.chat_activity_use_voice_input_iv:
                // 之前是文字输入状态，现在按下了语音输入，应该切换成语音输入，

                // 此时需要操作：
                // 1.隐藏圆形语音输入按钮
                // 2.隐藏输入框
                // 3.显示圆形文字输入按钮
                // 4.显示方形语音输入按钮
                // 5.如果当前输入框有文字，则隐藏发送按钮，显示更多功能圆形按钮
                // 6.如果底部更多功能板块开着，就得合上
                // 7.让输入框失去焦点/收起键盘

                voiceInputBtnIv.setVisibility(View.INVISIBLE);  // 1.隐藏圆形语音输入按钮
                messageEtRootRl.setVisibility(View.INVISIBLE);  // 2.隐藏输入框

                keyBoardInputBtnIv.setVisibility(View.VISIBLE); // 3.显示圆形文字输入按钮
                voiceInputBtn.setVisibility(View.VISIBLE);      // 4.显示方形语音输入按钮

                if(!TextUtils.isEmpty(messageEt.getText())){    // 5.如果当前输入框有文字，则隐藏发送按钮，显示更多功能圆形按钮
                    hideSendMessageBtn();
                }

                if (isMoreFunctionVisible) {                    // 6.如果底部更多功能板块开着，就得合上
                    moreFunctionRootRl.setVisibility(View.GONE);
                    isMoreFunctionVisible = !isMoreFunctionVisible;
                }

                //获取最顶层的View,并获取输入管理器，然后隐藏键盘
                View rootView = getWindow().peekDecorView();
                if (rootView != null) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                break;
            case R.id.chat_activity_more_function_iv_btn:
                // 展开/收起 更多功能按钮

                // 如果当前是文字输入状态，按下则展开底部更多功能板块
                // 如果当前是语音输入状态，则①隐藏圆形键盘输入按钮，②隐藏方形语音输入按钮，3.显示圆形语音输入按钮，4.显示输入框；5.并展开底部更多功能板块
                // 只要展开更多按钮，就要让输入框失去焦点，收起键盘
                if (!isMoreFunctionVisible) {
                    keyBoardInputBtnIv.setVisibility(View.INVISIBLE);   //1.隐藏圆形键盘输入按钮
                    voiceInputBtn.setVisibility(View.INVISIBLE);        //2.隐藏方形语音输入按钮

                    voiceInputBtnIv.setVisibility(View.VISIBLE);        //3.显示圆形语音输入按钮
                    messageEtRootRl.setVisibility(View.VISIBLE);        //4.显示输入框

                    moreFunctionRootRl.setVisibility(View.VISIBLE);     //5.展开底部更多功能板块
                } else {
                    //如果当前更多功能已经展开，则关闭即可
                    moreFunctionRootRl.setVisibility(View.GONE);
                }
                isMoreFunctionVisible = !isMoreFunctionVisible;
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (isMoreFunctionVisible) {
            moreFunctionRootRl.setVisibility(View.GONE);
            isMoreFunctionVisible = !isMoreFunctionVisible;
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                navigationOne.setImageResource(R.drawable.chat_activity_more_function_navigation_pressed);
                navigationTwo.setImageResource(R.drawable.chat_activity_more_function_navigation_normal);
                break;
            case 1:
                navigationTwo.setImageResource(R.drawable.chat_activity_more_function_navigation_pressed);
                navigationOne.setImageResource(R.drawable.chat_activity_more_function_navigation_normal);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
