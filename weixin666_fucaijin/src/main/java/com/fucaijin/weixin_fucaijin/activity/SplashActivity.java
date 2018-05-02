package com.fucaijin.weixin_fucaijin.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fucaijin.weixin_fucaijin.R;
import com.fucaijin.weixin_fucaijin.global.WeixinApplication;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener{
    //    Sign up   注册
    //    Sign in   登录
    //    Sign out  退出

    private FrameLayout fl_splash_bt;
    private Button bt_sign_in;
    private Button bt_sign_up;
    private TextView tv_language;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setStatusBarColor(); //设置沉浸式状态栏
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
        tv_language = (TextView) findViewById(R.id.tv_language);
        bt_sign_in = (Button) findViewById(R.id.bt_sign_in);
        bt_sign_up = (Button) findViewById(R.id.bt_sign_up);

        tv_language.setOnClickListener(this);
        bt_sign_in.setOnClickListener(this);
        bt_sign_up.setOnClickListener(this);
    }

    /**
     * 沉浸式状态栏;设置状态栏颜色为黑色:0xff000000 前两位0x是固定，ff是透明度，后6位是RGB
     */
    private void setStatusBarColor() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return;
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(0xff000000);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_language:
//                TODO 选择语言界面
//                startActivity(new Intent(this,SelectLanguageActivity.class));
                break;
            case R.id.bt_sign_in:
//                TODO 登录界面
                break;
            case R.id.bt_sign_up:
//                TODO 注册界面
                break;
        }
    }
}
