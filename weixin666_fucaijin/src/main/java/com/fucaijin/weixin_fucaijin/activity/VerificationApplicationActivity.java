package com.fucaijin.weixin_fucaijin.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.fucaijin.weixin_fucaijin.R;

/**
 * 申请添加好友时的验证申请界面
 */
public class VerificationApplicationActivity extends BaseActivity implements View.OnClickListener {

    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_application);
        setSwipeBackEnable(true);//设置可侧滑返回
        initUi();
        initData();
    }

    private void initUi() {
        RelativeLayout btnRlBack= (RelativeLayout) findViewById(R.id.btn_rl_verification_application_back);
        btnRlBack.setOnClickListener(this);
    }

    private void initData() {
        phone = getIntent().getStringExtra("phone");
    }


    @Override
    public void onClick(View view) {

    }
}
