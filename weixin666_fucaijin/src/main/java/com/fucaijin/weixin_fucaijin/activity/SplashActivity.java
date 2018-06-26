package com.fucaijin.weixin_fucaijin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fucaijin.weixin_fucaijin.R;
import com.fucaijin.weixin_fucaijin.global.WeixinApplication;

import static com.fucaijin.weixin_fucaijin.global.WeixinApplication.getHandler;
import static com.fucaijin.weixin_fucaijin.global.WeixinApplication.mContext;

public class SplashActivity extends BaseActivity implements View.OnClickListener {
    private FrameLayout fl_splash_bt;
    private TextView tv_language;
    private String[] languageList = {"跟随系统", "简体中文", "繁體中文（台灣）", "繁體中文（香港）", "English",
            "Bahasa Indonesia", "Bahasa Melayu", "Español", "중국어", "Italiano", "日本語", "Português", "Pусский",
            "Tiếng Việt", "Türkçe", "Deutsch", "Français"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColor(0xFF000000); //设置沉浸式状态栏
        setContentView(R.layout.activity_splash);

        if (WeixinApplication.isIsLogined()) {
//            已经登录，正常运行（闪屏页面3秒过后进入主页面，并关闭闪屏页）
            getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(mContext, HomeActivity.class));
                    finish();
                }
            }, 2000);
        } else if (!WeixinApplication.isIsFirstRun()) {
//            TODO 如果不是登录状态，也不是第一次运行。那说明当前是注销状态
            Toast.makeText(mContext, "当前是注销状态", Toast.LENGTH_SHORT).show();
        } else{
//            首次登录，进入登录/注册页面（如果未登录，且是首次运行就走到这里）
            initUI();
            fl_splash_bt.setVisibility(View.VISIBLE);
        }

    }

    private void initUI() {
        fl_splash_bt = (FrameLayout) findViewById(R.id.fl_splash_bt);
        tv_language = (TextView) findViewById(R.id.tv_titile);
        Button bt_login = (Button) findViewById(R.id.bt_login_splash_activity);
        Button bt_register = (Button) findViewById(R.id.bt_register);

        tv_language.setOnClickListener(this);
        bt_login.setOnClickListener(this);
        bt_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_titile:
                startActivity(new Intent(this, SelectLanguageActivity.class));
                break;
            case R.id.bt_login_splash_activity:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.bt_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (WeixinApplication.isIsFirstRun()) {
//            如果是显示注册页面，刷新当前的选择语言
            String language = WeixinApplication.getConfig("language");
            if (!language.equals("") && !language.equals("-1")) {
                int index = Integer.parseInt(language);
                String str = languageList[index];
                tv_language.setText(str);

                //如果当前选择的是第一项(跟随系统)，则设置为语言
                if (language.equals("0")) {
                    tv_language.setText("语言");
                }
            }
        }

    }
}
