package com.fucaijin.weixin_fucaijin.activity;

import android.os.Bundle;

import com.fucaijin.weixin_fucaijin.R;

public class SignInActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColor(0xFFFFFFFE);//全白的状态栏设置无效，采用接近纯白色代替
        setContentView(R.layout.activity_sign_in);
        initUI();
    }

    private void initUI() {
    }
}
