package com.fucaijin.weixin_fucaijin.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fucaijin.weixin_fucaijin.R;


/**
 * 登录页面：1.手机号登录 2.账号密码登录 3.已注销重新登录
 * 可侧滑关闭（在BaseActivity实现），输入框得获取焦点，且不为空时才显示清除输入框按钮，输入框得填完了，按钮才可点击
 * 登录了之后，要带着ip/IMEI码、请求类型码去请求服务器（ip用于判断用户的位置，给其分配给相应地区的服务器，分流减小服务器的压力），
 */
public class SignInActivity extends BaseActivity implements View.OnClickListener {
//    TODO 手机号、账号、密码这三个输入框的数字字体的更改
//    TODO 底部找回密码、紧急冻结、微信安全中心的点击逻辑的实现
//    TODO 尚未完成注销之后的使用之前账户登录的界面（语音登录界面）

    private LinearLayout ll_use_phone_sign_in_ui;
    private LinearLayout ll_use_other_way_sign_in_ui;
    private EditText et_phone;
    private EditText et_account;
    private EditText et_password;
    private ImageView iv_clean_phone_number;
    private ImageView iv_clean_account;
    private ImageView iv_clean_password;
    private ImageView divider_account_editor;
    private ImageView divider_password_editor;
    private Button bt_sign_in_next;
    private Button bt_sign_in_sign_in_activity;

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
        bt_sign_in_next = (Button) findViewById(R.id.bt_sign_in_next);
        iv_clean_phone_number = (ImageView) findViewById(R.id.iv_clean_phone_number);

        rl_select_country.setOnClickListener(this);
        tv_others_way_sign_in.setOnClickListener(this);
        bt_sign_in_next.setOnClickListener(this);
        iv_clean_phone_number.setOnClickListener(this);

//        手机号输入框的监听器：如果输入框为空，则按钮不可点击，且不显示清除按钮；否则反之
        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)) {
                    iv_clean_phone_number.setVisibility(View.INVISIBLE);
                    bt_sign_in_next.setEnabled(false);
                } else {
                    iv_clean_phone_number.setVisibility(View.VISIBLE);
                    bt_sign_in_next.setEnabled(true);
                }
            }
        });
//        ----------------------------------------------------

//        其他方式登录页面------------------------------------
        et_account = (EditText) findViewById(R.id.et_account);
        et_password = (EditText) findViewById(R.id.et_password);
        TextView tv_phone_sign_in = (TextView) findViewById(R.id.tv_phone_sign_in);
        bt_sign_in_sign_in_activity = (Button) findViewById(R.id.bt_sign_in_sign_in_activity);
        iv_clean_account = (ImageView) findViewById(R.id.iv_clean_account);
        iv_clean_password = (ImageView) findViewById(R.id.iv_clean_password);
        divider_account_editor = (ImageView) findViewById(R.id.divider_account_editor);
        divider_password_editor = (ImageView) findViewById(R.id.divider_password_editor);

        tv_phone_sign_in.setOnClickListener(this);
        bt_sign_in_sign_in_activity.setOnClickListener(this);
        iv_clean_account.setOnClickListener(this);
        iv_clean_password.setOnClickListener(this);

//        账号、密码输入框的内容改变监听器：如果输入框有一个为空，则按钮不可点击，为空的输入框不显示清除按钮；否则反之
        et_account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)) {
//                    如果账号的输入框为空：设置清除账号按钮不可见，设置登录按钮不可用
                    iv_clean_account.setVisibility(View.INVISIBLE);
                    bt_sign_in_sign_in_activity.setEnabled(false);
                } else {
//                    如果账号的输入框不为空：设置清除账号按钮可见，且如果密码不为空就登录按钮可用
                    iv_clean_account.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(et_password.getText().toString().trim())){
                        bt_sign_in_sign_in_activity.setEnabled(true);
                    }else {
                        bt_sign_in_sign_in_activity.setEnabled(false);
                    }
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
                if (TextUtils.isEmpty(editable)) {
                    iv_clean_password.setVisibility(View.INVISIBLE);
                    bt_sign_in_sign_in_activity.setEnabled(false);
                } else {
                    iv_clean_password.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(et_account.getText().toString().trim())){
                        bt_sign_in_sign_in_activity.setEnabled(true);
                    }else {
                        bt_sign_in_sign_in_activity.setEnabled(false);
                    }
                }
            }
        });

//        账号、密码输入框的焦点监听器，如果获取焦点其下划线设置为绿色，失去焦点则变回原来的灰色，并隐藏清除按钮
        et_account.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean getFocus) {
                if(getFocus){
//                    注意:用代码设置颜色一定要用8位的颜色，否则会显示透明或不显示
                    divider_account_editor.setBackgroundColor(0xFF45C01A);
                    if(!TextUtils.isEmpty(et_account.getText().toString().trim())){
                        iv_clean_account.setVisibility(View.VISIBLE);
                    }
                }else {
//                    注意:用代码设置颜色一定要用8位的颜色，否则会显示透明或不显示
                    divider_account_editor.setBackgroundColor(0xFFD9D9D9);
                    iv_clean_account.setVisibility(View.INVISIBLE);
                }
            }
        });

        et_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean getFocus) {
                if(getFocus){
//                    注意:用代码设置颜色一定要用8位的颜色，否则会显示透明或不显示
                    divider_password_editor.setBackgroundColor(0xFF45C01A);
                    if(!TextUtils.isEmpty(et_password.getText().toString().trim())){
                        iv_clean_password.setVisibility(View.VISIBLE);
                    }
                }else {
//                    注意:用代码设置颜色一定要用8位的颜色，否则会显示透明或不显示
                    divider_password_editor.setBackgroundColor(0xFFD9D9D9);
                    iv_clean_password.setVisibility(View.INVISIBLE);
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
//                TODO 打开选择国家的Avtivity逻辑代码未完成
                break;

//            切换到用微信号/QQ号/邮箱登录
            case R.id.tv_others_way_sign_in:
                ll_use_phone_sign_in_ui.setVisibility(View.GONE);
                ll_use_other_way_sign_in_ui.setVisibility(View.VISIBLE);
                break;
//            TODO 下一页按钮的点击事件未完成
            case R.id.bt_sign_in_next:
                break;

//            切换到用手机登录
            case R.id.tv_phone_sign_in:
                ll_use_other_way_sign_in_ui.setVisibility(View.GONE);
                ll_use_phone_sign_in_ui.setVisibility(View.VISIBLE);
                break;

//            TODO 登录按钮的点击事件未完成
            case R.id.bt_sign_in_sign_in_activity:
                break;

//            以下三个都是在登录界面点击清除按钮以后，清除输入框，并隐藏清除按钮
            case R.id.iv_clean_phone_number:
                et_phone.getText().clear();
                iv_clean_phone_number.setVisibility(View.GONE);
                bt_sign_in_next.setEnabled(false);
                break;
            case R.id.iv_clean_account:
                et_account.getText().clear();
                iv_clean_account.setVisibility(View.GONE);
                bt_sign_in_sign_in_activity.setEnabled(false);
                break;
            case R.id.iv_clean_password:
                et_password.getText().clear();
                iv_clean_password.setVisibility(View.GONE);
                bt_sign_in_sign_in_activity.setEnabled(false);
                break;
        }
    }

}
