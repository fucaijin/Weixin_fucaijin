package com.fucaijin.weixin_fucaijin.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fucaijin.weixin_fucaijin.R;
import com.fucaijin.weixin_fucaijin.global.WeixinApplication;
import com.fucaijin.weixin_fucaijin.utils.Md5;

/**
 * 注册页面
 * <p>
 * 获取填写的信息，以及用户的ip/设备号(防止恶意攻击)、请求类型码提交到服务器，由服务器判断账号是否存在
 * 请求类型码：用于判断是哪种类型请求（例如：注册、登录、发消息、刷朋友圈、）,然后服务器判断请求类型，执行相应的逻辑
 */
public class SignUpActivity extends BaseActivity implements View.OnClickListener, View.OnFocusChangeListener, TextWatcher {
    private ImageView sign_up_nickname_divider;
    private ImageView sign_up_phone_divider;
    private ImageView sign_up_password_divider;
    private ImageView sign_up_iv_password_visibility_mode;
    private EditText sign_up_et_password;
    private EditText sign_up_et_nick_name;
    private EditText sign_up_et_phone;
    private Button sign_up_bt_sign_up;
    private ImageButton sign_up_ib_select_head_sculpture;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initUI();
        mContext = WeixinApplication.getmContext();
    }

    private void initUI() {
        RelativeLayout sign_up_rl_bt_back = (RelativeLayout) findViewById(R.id.sign_up_rl_bt_back);
        sign_up_et_nick_name = (EditText) findViewById(R.id.sign_up_et_nick_name);
        sign_up_ib_select_head_sculpture = (ImageButton) findViewById(R.id.sign_up_ib_select_head_sculpture);
        LinearLayout sign_up_ll_select_country = (LinearLayout) findViewById(R.id.sign_up_ll_select_country);
        sign_up_et_phone = (EditText) findViewById(R.id.sign_up_et_phone);
        sign_up_et_password = (EditText) findViewById(R.id.sign_up_et_password);
        sign_up_iv_password_visibility_mode = (ImageView) findViewById(R.id.sign_up_iv_password_visibility_mode);
        sign_up_bt_sign_up = (Button) findViewById(R.id.sign_up_bt_sign_up);
        sign_up_nickname_divider = (ImageView) findViewById(R.id.sign_up_nickname_divider);
        sign_up_phone_divider = (ImageView) findViewById(R.id.sign_up_phone_divider);
        sign_up_password_divider = (ImageView) findViewById(R.id.sign_up_password_divider);

        sign_up_rl_bt_back.setOnClickListener(this);
        sign_up_ib_select_head_sculpture.setOnClickListener(this);
        sign_up_ll_select_country.setOnClickListener(this);
        sign_up_iv_password_visibility_mode.setOnClickListener(this);
        sign_up_bt_sign_up.setOnClickListener(this);

//        设置焦点监听器，如果焦点在哪个输入框，哪个输入框底下的下划线就设置为绿色
        sign_up_et_nick_name.setOnFocusChangeListener(this);
        sign_up_et_phone.setOnFocusChangeListener(this);
        sign_up_et_password.setOnFocusChangeListener(this);

//        设置内容监听器，用于监听昵称、手机号、密码是否信息齐全，如果齐全，注册按钮设置为可点击
        sign_up_et_nick_name.addTextChangedListener(this);
        sign_up_et_phone.addTextChangedListener(this);
        sign_up_et_password.addTextChangedListener(this);

//        设置底部的部分文字添加链接，实现直接转跳到浏览器打开页面
        agreementTextBindUrl();
    }

    /**
     * 设置注册页面底部的协议部分文字添加链接，可以直接转跳到浏览器打开页面
     */
    private void agreementTextBindUrl() {
        TextView tv_agreement = (TextView) findViewById(R.id.sign_up_tv_agreement);
//        拼接html = （指定颜色）普通文字 + (指定颜色)链接文字

//        用+号拼接字符串效率过低，因此注销
//        sign_up_tv_agreement.append(Html.fromHtml("<font color='#999999'>" +
//                "<a>点击上面的“注册”按钮，&nbsp;即表示你同意&nbsp;&nbsp;" +
//                "</a><font color='#576b95'>" +
//                "<a href=\"https://weixin.qq.com/agreement?lang=zh_CN\">" +
//                "《腾讯微信软件许可及服务协议》</a><font color='#999999'>" +
//                "<a>和</a>" +
//                "<font color='#576b95'>" +
//                "<a href=\"https://weixin.qq.com/agreement?lang=zh_CN&s=privacy&cc=CN\">" +
//                "《微信隐私保护指引》</a>"));

//        拼接html，生成带链接的TextView
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<font color='#999999'><a>")//设置没有链接的普通文字的颜色
                .append("点击上面的“注册”按钮，&nbsp;即表示你同意&nbsp;&nbsp;")//普通文字，&nbsp;代表一个空格，注意后面有个分号
                .append("</a><font color='#576b95'><a href=\"")//设置有链接的文字的颜色
                .append("https://weixin.qq.com/agreement?lang=zh_CN")//《服务协议》url
                .append("\">")
                .append("《腾讯微信软件许可及服务协议》")//链接文字1
                .append("</a><font color='#999999'><a>")
                .append("和")
                .append("</a><font color='#576b95'><a href=\"")
                .append("https://weixin.qq.com/agreement?lang=zh_CN&s=privacy&cc=CN")//《隐私保护》url
                .append("\">")
                .append("《微信隐私保护指引》")//链接文字2
                .append("</a>");


        tv_agreement.append(Html.fromHtml(stringBuilder.toString()));
//        也可以写成一行,如下面：
//        sign_up_tv_agreement.append(Html.fromHtml("<font color='#999999'><a>点击上面的“注册”按钮，&nbsp;即表示你同意&nbsp;&nbsp;</a><font color='#576b95'><a href=\"https://weixin.qq.com/agreement?lang=zh_CN\">《腾讯微信软件许可及服务协议》</a><font color='#999999'><a>和</a><font color='#576b95'><a href=\"https://weixin.qq.com/agreement?lang=zh_CN&s=privacy&cc=CN\">《微信隐私保护指引》</a>"));
        tv_agreement.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_up_rl_bt_back:
                finish();
                break;
            case R.id.sign_up_ib_select_head_sculpture:
//                TODO 打开相册选择，然后返回并显示
                selectHeadSculpture();
                break;
            case R.id.sign_up_ll_select_country:
//                TODO 打开选择国家页面
                break;
            case R.id.sign_up_iv_password_visibility_mode:
//                获取密码输入框是否是可见模式，如果不可见就这设置为可见，并更改可见密码的按钮图案，若可见则反之
                if (sign_up_et_password.getInputType() == 128) {//如果现在是显示密码模式
                    sign_up_iv_password_visibility_mode.setImageResource(R.drawable.sign_up_iv_bg_hide_password);
                    sign_up_et_password.setInputType(129);//设置为隐藏密码
                } else {
                    sign_up_iv_password_visibility_mode.setImageResource(R.drawable.sign_up_iv_bg_show_password);
                    sign_up_et_password.setInputType(128);//设置为显示密码
                }
                sign_up_et_password.setSelection(sign_up_et_password.getText().length());//设置光标的位置到末尾
                break;
            case R.id.sign_up_bt_sign_up:
//                TODO 执行提交信息到服务器，由服务器判断是否已经注册，并给出相应结果，如果返回的是登录，就执行登录逻辑
//                以下是模拟注册，把昵称、手机、密码（加密）存到本地文件中，然后开启登录界面，然后销毁当前界面
                String str_nick_name = sign_up_et_nick_name.getText().toString().trim();
                String str_phone = sign_up_et_phone.getText().toString().trim();
                String str_password = sign_up_et_password.getText().toString().trim();
                WeixinApplication.setConfig("nick_name",str_nick_name);
                WeixinApplication.setConfig("account",str_phone);
                WeixinApplication.setConfig("password", Md5.md5(str_password));
                Toast.makeText(mContext,"注册成功",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,SignInActivity.class));
                finish();
                break;
        }
    }

    /**
     * 跳到相册页面，选择照片，然后截图、压缩，并显示在Activity中
     */
    private void selectHeadSculpture() {
//        TODO 选择头像注册未完成
    }

    /**
     * 监听EditText的焦点获取，来设置EditText的下划线颜色
     *
     * @param view 当前获得/失去焦点的View
     * @param b    true是获得焦点，false是失去焦点
     */
    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()) {
            case R.id.sign_up_et_nick_name:
                if (b) {
                    sign_up_nickname_divider.setBackgroundColor(0xFF45C01A);
                } else {
                    sign_up_nickname_divider.setBackgroundColor(0xFFD9D9D9);
                }
                break;
            case R.id.sign_up_et_phone:
                if (b) {
                    sign_up_phone_divider.setBackgroundColor(0xFF45C01A);
                } else {
                    sign_up_phone_divider.setBackgroundColor(0xFFD9D9D9);
                }
                break;
            case R.id.sign_up_et_password:
                if (b) {
                    sign_up_password_divider.setBackgroundColor(0xFF45C01A);
                } else {
                    sign_up_password_divider.setBackgroundColor(0xFFD9D9D9);
                }
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (!TextUtils.isEmpty(sign_up_et_nick_name.getText().toString().trim())
                && !TextUtils.isEmpty(sign_up_et_phone.getText().toString().trim())
                && !TextUtils.isEmpty(sign_up_et_password.getText().toString().trim())) {
            sign_up_bt_sign_up.setEnabled(true);
        } else {
            sign_up_bt_sign_up.setEnabled(false);
        }
    }
}
