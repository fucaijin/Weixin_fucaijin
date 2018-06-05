package com.fucaijin.weixin_fucaijin.activity;

import android.content.Intent;
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
import android.widget.Toast;

import com.fucaijin.weixin_fucaijin.R;
import com.fucaijin.weixin_fucaijin.global.WeixinApplication;
import com.fucaijin.weixin_fucaijin.utils.JudgementUtils;
import com.fucaijin.weixin_fucaijin.utils.Md5;

import static com.fucaijin.weixin_fucaijin.global.WeixinApplication.mContext;


/**
 * 登录页面：1.手机号登录 2.账号密码登录 3.已注销重新登录
 * 可侧滑关闭（在BaseActivity实现），输入框得获取焦点，且不为空时才显示清除输入框按钮，输入框得填完了，按钮才可点击
 * 登录了之后，要带着ip/IMEI码、请求类型码去请求服务器（ip用于判断用户的位置，给其分配给相应地区的服务器，分流减小服务器的压力），
 */
public class SignInActivity extends BaseActivity implements View.OnClickListener {
//    TODO 手机号、账号、密码这三个输入框的数字字体的更改
//    TODO 底部找回密码、紧急冻结、微信安全中心的点击逻辑的实现
//    TODO 尚未完成注销之后的使用之前账户登录的界面（语音登录界面）

    private LinearLayout sign_in_ll_use_phone_ui;
    private LinearLayout sign_in_ll_use_other_way_ui;
    private EditText sign_in_et_phone;
    private EditText sign_in_et_account;
    private EditText sign_in_et_password;
    private ImageView sign_in_iv_clean_phone_number;
    private ImageView sign_in_iv_clean_account;
    private ImageView sign_in_iv_clean_password;
    private ImageView sign_in_iv_account_editor_divider;
    private ImageView sign_in_iv_password_editor_divider;
    private Button sign_in_bt_next_step;
    private Button sign_in_bt_sign_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColor(0xFFFFFFFE);//全白的状态栏设置无效，采用接近纯白色代替
        setContentView(R.layout.activity_sign_in);
        initUI();
    }

    private void initUI() {

//        顶部的关闭按钮
        ImageView sign_in_iv_finish = (ImageView) findViewById(R.id.sign_in_iv_finish);
        sign_in_iv_finish.setOnClickListener(this);

        sign_in_ll_use_phone_ui = (LinearLayout) findViewById(R.id.sign_in_ll_use_phone_ui);
        sign_in_ll_use_other_way_ui = (LinearLayout) findViewById(R.id.sign_in_ll_use_other_way_ui);

//        手机号登录页面-------------------------------------
        RelativeLayout sign_in_rl_select_country = (RelativeLayout) findViewById(R.id.sign_in_rl_select_country);
        sign_in_et_phone = (EditText) findViewById(R.id.sign_in_et_phone);
        TextView sign_in_tv_others_way_sign_in = (TextView) findViewById(R.id.sign_in_tv_others_way_sign_in);
        sign_in_bt_next_step = (Button) findViewById(R.id.sign_in_bt_next_step);
        sign_in_iv_clean_phone_number = (ImageView) findViewById(R.id.sign_in_iv_clean_phone_number);

        sign_in_rl_select_country.setOnClickListener(this);
        sign_in_tv_others_way_sign_in.setOnClickListener(this);
        sign_in_bt_next_step.setOnClickListener(this);
        sign_in_iv_clean_phone_number.setOnClickListener(this);

//        手机号输入框的监听器：如果输入框为空，则按钮不可点击，且不显示清除按钮；否则反之
        sign_in_et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)) {
                    sign_in_iv_clean_phone_number.setVisibility(View.INVISIBLE);
                    sign_in_bt_next_step.setEnabled(false);
                } else {
                    sign_in_iv_clean_phone_number.setVisibility(View.VISIBLE);
                    sign_in_bt_next_step.setEnabled(true);
                }
            }
        });
//        ----------------------------------------------------

//        其他方式登录页面------------------------------------
        sign_in_et_account = (EditText) findViewById(R.id.sign_in_et_account);
        sign_in_et_password = (EditText) findViewById(R.id.sign_in_et_password);
        TextView sign_in_tv_phone = (TextView) findViewById(R.id.sign_in_tv_phone);
        sign_in_bt_sign_in = (Button) findViewById(R.id.sign_in_bt_sign_in);
        sign_in_iv_clean_account = (ImageView) findViewById(R.id.sign_in_iv_clean_account);
        sign_in_iv_clean_password = (ImageView) findViewById(R.id.sign_in_iv_clean_password);
        sign_in_iv_account_editor_divider = (ImageView) findViewById(R.id.sign_in_iv_account_editor_divider);
        sign_in_iv_password_editor_divider = (ImageView) findViewById(R.id.sign_in_iv_password_editor_divider);

        sign_in_tv_phone.setOnClickListener(this);
        sign_in_bt_sign_in.setOnClickListener(this);
        sign_in_iv_clean_account.setOnClickListener(this);
        sign_in_iv_clean_password.setOnClickListener(this);

//        账号、密码输入框的内容改变监听器：如果输入框有一个为空，则按钮不可点击，为空的输入框不显示清除按钮；否则反之
        sign_in_et_account.addTextChangedListener(new TextWatcher() {
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
                    sign_in_iv_clean_account.setVisibility(View.INVISIBLE);
                    sign_in_bt_sign_in.setEnabled(false);
                } else {
//                    如果账号的输入框不为空：设置清除账号按钮可见，且如果密码不为空就登录按钮可用
                    sign_in_iv_clean_account.setVisibility(View.VISIBLE);
                    if (!JudgementUtils.editTextEmpty(sign_in_et_password)) {
                        sign_in_bt_sign_in.setEnabled(true);
                    } else {
                        sign_in_bt_sign_in.setEnabled(false);
                    }
                }
            }
        });

        sign_in_et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)) {
                    sign_in_iv_clean_password.setVisibility(View.INVISIBLE);
                    sign_in_bt_sign_in.setEnabled(false);
                } else {
                    sign_in_iv_clean_password.setVisibility(View.VISIBLE);
                    if (!JudgementUtils.editTextEmpty(sign_in_et_account)) {
                        sign_in_bt_sign_in.setEnabled(true);
                    } else {
                        sign_in_bt_sign_in.setEnabled(false);
                    }
                }
            }
        });

//        账号、密码输入框的焦点监听器，如果获取焦点其下划线设置为绿色，失去焦点则变回原来的灰色，并隐藏清除按钮
        sign_in_et_account.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean getFocus) {
                if (getFocus) {
//                    注意:用代码设置颜色一定要用8位的颜色，否则会显示透明或不显示
                    sign_in_iv_account_editor_divider.setBackgroundColor(0xFF45C01A);
                    if (!JudgementUtils.editTextEmpty(sign_in_et_account)) {
                        sign_in_iv_clean_account.setVisibility(View.VISIBLE);
                    }
                } else {
//                    注意:用代码设置颜色一定要用8位的颜色，否则会显示透明或不显示
                    sign_in_iv_account_editor_divider.setBackgroundColor(0xFFD9D9D9);
                    sign_in_iv_clean_account.setVisibility(View.INVISIBLE);
                }
            }
        });

        sign_in_et_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean getFocus) {
                if (getFocus) {
//                    注意:用代码设置颜色一定要用8位的颜色，否则会显示透明或不显示
                    sign_in_iv_password_editor_divider.setBackgroundColor(0xFF45C01A);
                    if (!JudgementUtils.editTextEmpty(sign_in_et_password)) {
                        sign_in_iv_clean_password.setVisibility(View.VISIBLE);
                    }
                } else {
//                    注意:用代码设置颜色一定要用8位的颜色，否则会显示透明或不显示
                    sign_in_iv_password_editor_divider.setBackgroundColor(0xFFD9D9D9);
                    sign_in_iv_clean_password.setVisibility(View.INVISIBLE);
                }
            }
        });
//        ----------------------------------------------------

        TextView sign_in_tv_get_back_password = (TextView) findViewById(R.id.sign_in_tv_get_back_password);
        TextView sign_in_tv_emergency_freezing = (TextView) findViewById(R.id.sign_in_tv_emergency_freezing);
        TextView sign_in_tv_security_center = (TextView) findViewById(R.id.sign_in_tv_security_center);

        sign_in_tv_get_back_password.setOnClickListener(this);
        sign_in_tv_emergency_freezing.setOnClickListener(this);
        sign_in_tv_security_center.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_iv_finish:
                finish();
                break;
            case R.id.sign_in_rl_select_country:
//                TODO 打开选择国家的Avtivity逻辑代码未完成
                break;

//            切换到用微信号/QQ号/邮箱登录
            case R.id.sign_in_tv_others_way_sign_in:
                sign_in_ll_use_phone_ui.setVisibility(View.GONE);
                sign_in_ll_use_other_way_ui.setVisibility(View.VISIBLE);
                break;
//            TODO 下一页按钮的点击事件未完成，判断手机的号码位数 开启手机登录页面密码登录和验证码登录可随意切换
            case R.id.sign_in_bt_next_step:
                break;

//            切换到用手机登录
            case R.id.sign_in_tv_phone:
                sign_in_ll_use_other_way_ui.setVisibility(View.GONE);
                sign_in_ll_use_phone_ui.setVisibility(View.VISIBLE);
                break;

//            TODO 登录按钮的点击事件未完成,要先MD5加密，然后请求服务器，判断账户和密码是否正确，是的话返回相关信息
            case R.id.sign_in_bt_sign_in:
                String account = sign_in_et_account.getText().toString().trim();
                String password = sign_in_et_password.getText().toString().trim();
                String md5Password = Md5.md5(password);

                if(account.equals(WeixinApplication.getConfig("account")) && md5Password.equals(WeixinApplication.getConfig("password"))){
                    startActivity(new Intent(this,HomeActivity.class));
                }else {
                    Toast.makeText(mContext,"账号或密码有误，请重新输入",Toast.LENGTH_SHORT).show();
                }

                break;

//            以下三个都是在登录界面点击清除按钮以后，清除输入框，并隐藏清除按钮
            case R.id.sign_in_iv_clean_phone_number:
                sign_in_et_phone.getText().clear();
                sign_in_iv_clean_phone_number.setVisibility(View.GONE);
                sign_in_bt_next_step.setEnabled(false);
                break;
            case R.id.sign_in_iv_clean_account:
                sign_in_et_account.getText().clear();
                sign_in_iv_clean_account.setVisibility(View.GONE);
                sign_in_bt_sign_in.setEnabled(false);
                break;
            case R.id.sign_in_iv_clean_password:
                sign_in_et_password.getText().clear();
                sign_in_iv_clean_password.setVisibility(View.GONE);
                sign_in_bt_sign_in.setEnabled(false);
                break;

//            底部的三个带有链接的文字:找回密码、紧急冻结、微信安全中心。点击就跳到新的页面，
            case R.id.sign_in_tv_get_back_password:
                break;
            case R.id.sign_in_tv_emergency_freezing:
                break;
            case R.id.sign_in_tv_security_center:
                break;
        }
    }

}
