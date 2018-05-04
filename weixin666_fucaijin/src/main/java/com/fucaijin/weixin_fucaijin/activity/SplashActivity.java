package com.fucaijin.weixin_fucaijin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fucaijin.weixin_fucaijin.R;
import com.fucaijin.weixin_fucaijin.global.WeixinApplication;

public class SplashActivity extends BaseActivity implements View.OnClickListener{
    //    Sign up   注册
    //    Sign in   登录
    //    Sign out  退出

    private FrameLayout fl_splash_bt;
    private Button bt_sign_in;
    private Button bt_sign_up;
    private TextView tv_language;
    private String[] languageList = {"跟随系统", "简体中文", "繁體中文（台灣）", "繁體中文（香港）", "English",
            "Bahasa Indonesia", "Bahasa Melayu", "Español", "중국어", "Italiano", "日本語", "Português", "Pусский",
            "Tiếng Việt", "Türkçe", "Deutsch", "Français"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColor(0xFF000000); //设置沉浸式状态栏
        setContentView(R.layout.activity_splash);
        if(WeixinApplication.isSignIn()){
//            TODO 已经登录，正常运行（闪屏页面3秒过后进入主页面）
        }else if(!WeixinApplication.isSignIn() && !WeixinApplication.isFirstRun()){
//            TODO 曾经登录过，但已经退出，需要登录 (不是登录状态，而且也不是第一次登录)

        }else if(WeixinApplication.isFirstRun()){
//            首次登录，进入登录/注册页面
            initUI();
            fl_splash_bt.setVisibility(View.VISIBLE);
        }
    }

    private void initUI() {
        fl_splash_bt = (FrameLayout) findViewById(R.id.fl_splash_bt);
        tv_language = (TextView) findViewById(R.id.tv_titile);
        bt_sign_in = (Button) findViewById(R.id.bt_sign_in_splash_activity);
        bt_sign_up = (Button) findViewById(R.id.bt_sign_up);

        tv_language.setOnClickListener(this);
        bt_sign_in.setOnClickListener(this);
        bt_sign_up.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_titile:
                startActivity(new Intent(this,SelectLanguageActivity.class));
                break;
            case R.id.bt_sign_in_splash_activity:
                startActivity(new Intent(this,SignInActivity.class));
                break;
            case R.id.bt_sign_up:
//                TODO 注册界面
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(WeixinApplication.isFirstRun()){
            //刷新当前的选择语言
            String language = WeixinApplication.getConfig("language");
            if(!language.equals("") && !language.equals("-1")){
                int index = Integer.parseInt(language);
                String str = languageList[index];
                tv_language.setText(str);

                //如果是
                if(language.equals("0")){
                    tv_language.setText("语言");
                }
            }
        }

    }
}
