package com.fucaijin.weixin_fucaijin.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fucaijin.weixin_fucaijin.R;

public class SignInActivity extends BaseActivity implements View.OnClickListener {
//    TODO 手机号、账号、密码这三个输入框的数字字体的更改
//    TODO 按钮监听输入框的灰色效果
//    TODO 底部找回密码、紧急冻结、微信安全中心的点击逻辑的实现

    private LinearLayout ll_use_phone_sign_in_ui;
    private LinearLayout ll_use_other_way_sign_in_ui;
    private EditText et_phone;
    private EditText et_account;
    private EditText et_password;
    private ImageView iv_clean_phone_number;
    private ImageView iv_clean_account;
    private ImageView iv_clean_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColor(0xFFFFFFFE);//全白的状态栏设置无效，采用接近纯白色代替
        setContentView(R.layout.activity_sign_in);
        initUI();
    }

    private void initUI() {

        ImageView iv_close_sign_in_activity = (ImageView) findViewById(R.id.iv_close_bt_sign_in_activity);
        iv_close_sign_in_activity.setOnClickListener(this);

        ll_use_phone_sign_in_ui = (LinearLayout) findViewById(R.id.ll_use_phone_sign_in_ui);
        ll_use_other_way_sign_in_ui = (LinearLayout) findViewById(R.id.ll_use_other_way_sign_in_ui);


//        手机号登录页面-------------------------------------
        RelativeLayout rl_select_country = (RelativeLayout) findViewById(R.id.rl_select_country);
        et_phone = (EditText) findViewById(R.id.et_phone);
        TextView tv_others_way_sign_in = (TextView) findViewById(R.id.tv_others_way_sign_in);
        Button bt_sign_in_next = (Button) findViewById(R.id.bt_sign_in_next);
        iv_clean_phone_number = (ImageView) findViewById(R.id.iv_clean_phone_number);

        rl_select_country.setOnClickListener(this);
        tv_others_way_sign_in.setOnClickListener(this);
        bt_sign_in_next.setOnClickListener(this);
        iv_clean_phone_number.setOnClickListener(this);

        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.equals("")) {
                    iv_clean_phone_number.setVisibility(View.INVISIBLE);
                } else {
                    iv_clean_phone_number.setVisibility(View.VISIBLE);
                }
            }
        });
//        ----------------------------------------------------

//        其他方式登录页面------------------------------------
        et_account = (EditText) findViewById(R.id.et_account);
        et_password = (EditText) findViewById(R.id.et_password);
        TextView tv_phone_sign_in = (TextView) findViewById(R.id.tv_phone_sign_in);
        Button bt_sign_in_sign_in_activity = (Button) findViewById(R.id.bt_sign_in_sign_in_activity);
        iv_clean_account = (ImageView) findViewById(R.id.iv_clean_account);
        iv_clean_password = (ImageView) findViewById(R.id.iv_clean_password);

        tv_phone_sign_in.setOnClickListener(this);
        bt_sign_in_sign_in_activity.setOnClickListener(this);
        iv_clean_account.setOnClickListener(this);
        iv_clean_password.setOnClickListener(this);

        et_account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.equals("")) {
                    iv_clean_account.setVisibility(View.INVISIBLE);
                } else {
                    iv_clean_account.setVisibility(View.VISIBLE);
                }
            }
        });
        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.equals("")) {
                    iv_clean_password.setVisibility(View.INVISIBLE);
                } else {
                    iv_clean_password.setVisibility(View.VISIBLE);
                }
            }
        });
//        ----------------------------------------------------
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_close_bt_sign_in_activity:
                finish();
                break;
            case R.id.rl_select_country:
//                TODO 完成选择国家的Avtivity逻辑代码
                break;

//            切换到用微信号/QQ号/邮箱登录
            case R.id.tv_others_way_sign_in:
                ll_use_phone_sign_in_ui.setVisibility(View.GONE);
                ll_use_other_way_sign_in_ui.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_sign_in_next:
                break;

//            切换到用手机登录
            case R.id.tv_phone_sign_in:
                ll_use_other_way_sign_in_ui.setVisibility(View.GONE);
                ll_use_phone_sign_in_ui.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_sign_in_sign_in_activity:
                break;

//            以下三个都是在登录界面点击清除按钮以后，清除输入框，并隐藏清除按钮
            case R.id.iv_clean_phone_number:
                et_phone.setText("");
                iv_clean_phone_number.setVisibility(View.INVISIBLE);
                break;
            case R.id.iv_clean_account:
                et_account.setText("");
                iv_clean_account.setVisibility(View.INVISIBLE);
                break;
            case R.id.iv_clean_password:
                et_password.setText("");
                iv_clean_password.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
