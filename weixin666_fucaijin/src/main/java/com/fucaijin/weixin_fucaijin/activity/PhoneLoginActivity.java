package com.fucaijin.weixin_fucaijin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fucaijin.weixin_fucaijin.R;
import com.fucaijin.weixin_fucaijin.global.WeixinApplication;
import com.fucaijin.weixin_fucaijin.utils.ConvertUtils;
import com.fucaijin.weixin_fucaijin.utils.Http;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.fucaijin.weixin_fucaijin.global.WeixinApplication.HTTP_HOST_URL;
import static com.fucaijin.weixin_fucaijin.global.WeixinApplication.mContext;
import static com.fucaijin.weixin_fucaijin.utils.Http.responseHashMap;

/**
 * 使用手机登录页面(就是输入了手机号，然后点击下一步打开的那个页面)
 * Created by fucaijin on 2018/6/22.
 */

public class PhoneLoginActivity extends BaseActivity implements View.OnClickListener, TextWatcher {

    private static final String HTTP_POST_URL_LOGIN = HTTP_HOST_URL + "login/";//完整的应该是这样："http://192.168.1.105:8000/login/";
    private static final int HTTP_RESPONSE_TYPE_CODE_LOGIN_PHONE_NOT_EXIST = 16; //请求登录，但手机号尚未注册
    private static final int HTTP_RESPONSE_TYPE_CODE_LOGIN_PASSWORD_ERROR = 18;
    private static final int HTTP_RESPONSE_TYPE_CODE_LOGIN_SUCCESS = 20;
    private TextView phoneTv;
    private Button phoneLoginBtn;
    private EditText passwordEt;
    private ImageView cleanPasswordIv;
    public static final int HTTP_REQUEST_TYPE_CODE_PHONE_LOGIN = 13;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);
        setStatusBarColor(0xFFC1C1C1);//设置灰色的标题栏颜色
        initUi();
        initData();
    }

    private void initData() {
        phoneTv.setText("+86" + getIntent().getStringExtra("phone"));
    }

    private void initUi() {
        ImageView finishIv =  (ImageView) findViewById(R.id.iv_phone_login_finish);
        finishIv.setOnClickListener(this);

        phoneTv = (TextView) findViewById(R.id.login_phone_login_phone);
        phoneLoginBtn = (Button) findViewById(R.id.login_btn_phone_login);
        phoneLoginBtn.setOnClickListener(this);

        passwordEt = (EditText) findViewById(R.id.et_phone_login_password);
        passwordEt.addTextChangedListener(this);

        cleanPasswordIv = (ImageView) findViewById(R.id.iv_login_clean_phone_password);
        cleanPasswordIv.setOnClickListener(this);

        TextView loginTvTetBackPassword = (TextView) findViewById(R.id.login_tv_get_back_password);
        TextView loginTvEmergencyFreezing = (TextView) findViewById(R.id.login_tv_emergency_freezing);
        TextView loginTvSecurityCenter = (TextView) findViewById(R.id.login_tv_security_center);

        loginTvTetBackPassword.setOnClickListener(this);
        loginTvEmergencyFreezing.setOnClickListener(this);
        loginTvSecurityCenter.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_phone_login_finish:
//                关闭页面按钮
                finish();
                break;
            case R.id.login_btn_phone_login:
                requestLogin();
//                登录按钮
                break;

            case R.id.login_tv_get_back_password:
//                找回密码
                break;
            case R.id.login_tv_emergency_freezing:
//                紧急冻结
                break;
            case R.id.login_tv_security_center:
//                微信安全中心
                break;
            case R.id.iv_login_clean_phone_password:
//                清空密码输入框按钮
                passwordEt.getText().clear();
                break;
        }
    }

    private void requestLogin() {
        String phone = phoneTv.getText().toString().substring(3);//截取手机号码，例如+8613868959855，就会从index为3开始截取
        String passwordMd5 = ConvertUtils.string2md5(passwordEt.getText().toString());

//        将要传输到服务器的信息封装到HashMap中
        HashMap<Object, Object> loginInfoMap = new HashMap<>();
        loginInfoMap.put("phone",phone);
        loginInfoMap.put("password",passwordMd5);
        loginInfoMap.put("url",HTTP_POST_URL_LOGIN);

//        将信息提交到服务器
        HashMap hashMap = Http.postServer(HTTP_REQUEST_TYPE_CODE_PHONE_LOGIN, loginInfoMap);
        responseHashMap = null;//得到返回的数据后，清空Http类的请求数据，以便判断下次是否请求到数据

//        根据网络请求，判断是否成功，如果网络请求成功，则判断是否用户名不存在，或者秘密错误或者登录成功
        int responseCode = (int) hashMap.get("responseCode");
        if(responseCode == 200){
            JSONObject jsonObject = (JSONObject) hashMap.get("jsonObject");
            try {
                int code = (int) jsonObject.get("code");
                String content = (String) jsonObject.get("content");
                switch (code){
                    case HTTP_RESPONSE_TYPE_CODE_LOGIN_PHONE_NOT_EXIST:
//                        该手机号未注册
                    case HTTP_RESPONSE_TYPE_CODE_LOGIN_PASSWORD_ERROR:
//                        密码错误
                        Toast.makeText(mContext, content, Toast.LENGTH_SHORT).show();
                        break;
                    case HTTP_RESPONSE_TYPE_CODE_LOGIN_SUCCESS:
//                        登录成功，保存信息到本地，不是首次登录，还有账户密码，以便下次可以直接登录
//                        关闭之前的所有Activity,然后打开主页面
                        WeixinApplication.setConfigString("user_phone",phone);
                        WeixinApplication.setConfigString("password",passwordMd5);

                        Intent openHomeIntent = new Intent(this, HomeActivity.class);
                        openHomeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(openHomeIntent);
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
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
        if (editable.toString().isEmpty()) {
            cleanPasswordIv.setVisibility(View.INVISIBLE);
        }else {
            cleanPasswordIv.setVisibility(View.VISIBLE);
        }
    }
}
