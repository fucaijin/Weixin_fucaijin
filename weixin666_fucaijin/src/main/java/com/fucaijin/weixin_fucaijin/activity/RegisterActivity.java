package com.fucaijin.weixin_fucaijin.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Base64;
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
import com.fucaijin.weixin_fucaijin.utils.ConvertUtils;
import com.fucaijin.weixin_fucaijin.utils.HandleResponseCode;
import com.fucaijin.weixin_fucaijin.utils.Http;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.options.RegisterOptionalUserInfo;
import cn.jpush.im.api.BasicCallback;

import static com.fucaijin.weixin_fucaijin.global.WeixinApplication.HTTP_HOST_URL;
import static com.fucaijin.weixin_fucaijin.global.WeixinApplication.mContext;
import static com.fucaijin.weixin_fucaijin.utils.Http.responseHashMap;

/**
 * 注册页面
 * 获取填写的信息，以及用户的ip/设备号(防止恶意攻击)、请求类型码提交到服务器，由服务器判断账号是否存在
 * 请求类型码：用于判断是哪种类型请求（例如：注册、登录、发消息、刷朋友圈、）,然后服务器判断请求类型，执行相应的逻辑
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener, View.OnFocusChangeListener, TextWatcher {
    private static final int HTTP_RESPONSE_TYPE_CODE_REGISTER_SUCCESS = 12;
    private static final int HTTP_RESPONSE_TYPE_CODE_REGISTER_PHONE_REPEAT = 14;
    private ImageView registerNicknameDivider;
    private ImageView registerPhoneDivider;
    private ImageView registerPasswordDivider;
    private ImageView registerIvPasswordVisibilityMode;
    private EditText registerEtPassword;
    private EditText registerEtNickName;
    private EditText registerEtPhone;
    private Button registerBtRegister;
    private ImageButton registerIbSelectHeadSculpture;//TODO 注册域名需要改成实际的
    public static final String HTTP_POST_URL_REGISTER = HTTP_HOST_URL + "register/";
    public static final int HTTP_REQUEST_TYPE_CODE_REGISTER = 11;//注册请求码

    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择头像
    private static final int PHOTO_REQUEST_CUT = 3;// 剪切完成的结果

    private String headPictureStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initUI();
    }

    private void initUI() {
        RelativeLayout registerRlBtBack = (RelativeLayout) findViewById(R.id.register_rl_bt_back);
        registerEtNickName = (EditText) findViewById(R.id.register_et_nick_name);
        registerIbSelectHeadSculpture = (ImageButton) findViewById(R.id.register_ib_select_head_sculpture);
        LinearLayout registerLlArea = (LinearLayout) findViewById(R.id.register_ll_area);
        registerEtPhone = (EditText) findViewById(R.id.register_et_phone);
        registerEtPassword = (EditText) findViewById(R.id.register_et_password);
        registerIvPasswordVisibilityMode = (ImageView) findViewById(R.id.register_iv_password_visibility_mode);
        registerBtRegister = (Button) findViewById(R.id.register_bt_register);
        registerNicknameDivider = (ImageView) findViewById(R.id.register_nickname_divider);
        registerPhoneDivider = (ImageView) findViewById(R.id.register_phone_divider);
        registerPasswordDivider = (ImageView) findViewById(R.id.register_password_divider);

        registerRlBtBack.setOnClickListener(this);
        registerIbSelectHeadSculpture.setOnClickListener(this);
        registerLlArea.setOnClickListener(this);
        registerIvPasswordVisibilityMode.setOnClickListener(this);
        registerBtRegister.setOnClickListener(this);

//        设置焦点监听器，如果焦点在哪个输入框，哪个输入框底下的下划线就设置为绿色
        registerEtNickName.setOnFocusChangeListener(this);
        registerEtPhone.setOnFocusChangeListener(this);
        registerEtPassword.setOnFocusChangeListener(this);

//        设置内容监听器，用于监听昵称、手机号、密码是否信息齐全，如果齐全，注册按钮设置为可点击
        registerEtNickName.addTextChangedListener(this);
        registerEtPhone.addTextChangedListener(this);
        registerEtPassword.addTextChangedListener(this);

//        设置底部的部分文字添加链接，实现直接转跳到浏览器打开页面
        agreementTextBindUrl();
    }

    /**
     * 设置注册页面底部的协议部分文字添加链接，可以直接转跳到浏览器打开页面
     */
    private void agreementTextBindUrl() {
        TextView tv_agreement = (TextView) findViewById(R.id.register_tv_agreement);
//        拼接html = （指定颜色）普通文字 + (指定颜色)链接文字
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

//        fromHtml过时，因此做版本兼容判断，如果大于版本N版，则使用上面的两个参数的方法
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            tv_agreement.append(Html.fromHtml(stringBuilder.toString(), Html.FROM_HTML_MODE_LEGACY));
        } else {
            tv_agreement.append(Html.fromHtml(stringBuilder.toString()));
        }
//        也可以写成一行,如下面：
//        register_tv_agreement.append(Html.fromHtml("<font color='#999999'><a>点击上面的“注册”按钮，&nbsp;即表示你同意&nbsp;&nbsp;</a><font color='#576b95'><a href=\"https://weixin.qq.com/agreement?lang=zh_CN\">《腾讯微信软件许可及服务协议》</a><font color='#999999'><a>和</a><font color='#576b95'><a href=\"https://weixin.qq.com/agreement?lang=zh_CN&s=privacy&cc=CN\">《微信隐私保护指引》</a>"));
        tv_agreement.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_rl_bt_back:
                finish();
                break;
            case R.id.register_ib_select_head_sculpture:
                selectHeadSculpture();
                break;
            case R.id.register_ll_area:
//                TODO 打开选择国家页面
                break;
            case R.id.register_iv_password_visibility_mode:
//                获取密码输入框是否是可见模式，如果不可见就这设置为可见，并更改可见密码的按钮图案，若可见则反之
                if (registerEtPassword.getInputType() == 128) {//如果现在是显示密码模式
                    registerIvPasswordVisibilityMode.setImageResource(R.drawable.register_iv_bg_hide_password);
                    registerEtPassword.setInputType(129);//设置为隐藏密码
                } else {
                    registerIvPasswordVisibilityMode.setImageResource(R.drawable.register_iv_bg_show_password);
                    registerEtPassword.setInputType(128);//设置为显示密码
                }
                registerEtPassword.setSelection(registerEtPassword.getText().length());//设置光标的位置到末尾
                break;
            case R.id.register_bt_register:
//                以下是模拟注册，把昵称、手机、密码（加密）存到本地文件中，然后开启登录界面，然后销毁当前界面
                String nickNameStr = registerEtNickName.getText().toString().trim();
                final String phoneStr = registerEtPhone.getText().toString().trim();
                String passwordStr = registerEtPassword.getText().toString().trim();
                final String passwordMd5 = ConvertUtils.string2md5(passwordStr);

//                调用极光im SDK进行注册
//                JMessageClient.register(String username, String password, BasicCallback callback);
                RegisterOptionalUserInfo registerOptionalUserInfo = new RegisterOptionalUserInfo();
                registerOptionalUserInfo.setNickname(nickNameStr);
                JMessageClient.register(phoneStr, passwordMd5, registerOptionalUserInfo, new BasicCallback() {
                    @Override
                    public void gotResult(int statusCode, String s) {
//                        TODO 尚未保存头像图片
//                        注册成功
                        if (statusCode == 0) {
                            WeixinApplication.setConfigString("account", phoneStr);
                            WeixinApplication.setConfigString("password", passwordMd5);
                            Toast.makeText(mContext, "注册成功", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            HandleResponseCode.onHandle(mContext, statusCode);
                        }
                    }
                });

//                如果有头像，就注册。姓名、手机、密码等之前已经检查，以上项目都填了，注册按钮才可使用，不然就是灰色的
                if (headPictureStr != null) {
                    boolean registerSuccess = requestRegister(nickNameStr, phoneStr, passwordMd5, headPictureStr);
                    if (registerSuccess) {
//                    如果注册成功，就把用户名和密码存到本地，然后打开登录页面
                        WeixinApplication.setConfigString("nick_name", nickNameStr);
                        WeixinApplication.setConfigString("account", phoneStr);
                        WeixinApplication.setConfigString("password", passwordMd5);
                        Toast.makeText(mContext, "注册成功", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, LoginActivity.class));
                        finish();
                    }
                } else {
                    Toast.makeText(mContext, "请选择头像", Toast.LENGTH_SHORT).show();
                }



                break;
        }
    }

    /**
     * 向服务器请求注册的方法
     *
     * @param nickName       昵称
     * @param phone          手机号
     * @param passwordMd5    已经经过md5加密后的密码
     * @param headPictureStr
     * @return 注册结果。如果失败，那么失败的原因是什么。
     */
    private boolean requestRegister(String nickName, String phone, String passwordMd5, String headPictureStr) {
//        将要发送的信息和url封装到HashMap中
        HashMap<Object, Object> registerInfoMap = new HashMap<>();
        registerInfoMap.put("url", HTTP_POST_URL_REGISTER);
        registerInfoMap.put("nickName", nickName);
        registerInfoMap.put("phone", phone);
        registerInfoMap.put("password", passwordMd5);
        registerInfoMap.put("headPicture", headPictureStr);

        HashMap resultHashMap = Http.postServer(HTTP_REQUEST_TYPE_CODE_REGISTER, registerInfoMap);
        responseHashMap = null;//得到返回的数据后，清空Http类的请求数据，以便判断下次是否请求到数据

//        请求结束后结果的处理
        if (resultHashMap != null) {
            int responseCode = (int) resultHashMap.get("responseCode");
            JSONObject jsonObject = (JSONObject) resultHashMap.get("jsonObject");

            if (responseCode == 200) {
                String content = "";
                int code = -12;
                try {
                    content = (String) jsonObject.get("content");
                    code = (int) jsonObject.get("code");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                switch (code) {
                    case HTTP_RESPONSE_TYPE_CODE_REGISTER_SUCCESS:
                        //注册成功
                        Toast.makeText(mContext, content, Toast.LENGTH_SHORT).show();
                        return true;
                    case HTTP_RESPONSE_TYPE_CODE_REGISTER_PHONE_REPEAT:
//                    TODO 打开号码已注册界面，并根据返回来的头像，昵称显示出来
                        //该手机号码已注册
                        Toast.makeText(mContext, content, Toast.LENGTH_SHORT).show();
                        return false;
                }
            } else {
                Toast.makeText(mContext, "注册失败，网络请求失败，responseCode = " + responseCode, Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }

    /**
     * 跳到相册页面，选择照片，然后返回截图、压缩，并显示在Activity中
     */
    private void selectHeadSculpture() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //权限还没有授予，需要在这里写申请权限的代码
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        } else {
            //权限已经被授予，在这里直接写要执行的相应方法即可
            // 激活系统图库，选择一张图片
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
            startActivityForResult(Intent.createChooser(intent, "选择表情图片"), PHOTO_REQUEST_GALLERY);
        }
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
            case R.id.register_et_nick_name:
                if (b) {
                    registerNicknameDivider.setBackgroundColor(0xFF45C01A);
                } else {
                    registerNicknameDivider.setBackgroundColor(0xFFD9D9D9);
                }
                break;
            case R.id.register_et_phone:
                if (b) {
                    registerPhoneDivider.setBackgroundColor(0xFF45C01A);
                } else {
                    registerPhoneDivider.setBackgroundColor(0xFFD9D9D9);
                }
                break;
            case R.id.register_et_password:
                if (b) {
                    registerPasswordDivider.setBackgroundColor(0xFF45C01A);
                } else {
                    registerPasswordDivider.setBackgroundColor(0xFFD9D9D9);
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
        if (!TextUtils.isEmpty(registerEtNickName.getText().toString().trim())
                && !TextUtils.isEmpty(registerEtPhone.getText().toString().trim())
                && !TextUtils.isEmpty(registerEtPassword.getText().toString().trim())) {
            registerBtRegister.setEnabled(true);
        } else {
            registerBtRegister.setEnabled(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                crop(uri);
            }

        } else if (requestCode == PHOTO_REQUEST_CUT) {
            // 从剪切图片返回的数据
            if (data != null) {
                Bitmap customHeadSculptureBitmap = data.getParcelableExtra("data");
                registerIbSelectHeadSculpture.setImageBitmap(customHeadSculptureBitmap);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();//将Bitmap转成Byte[]
                customHeadSculptureBitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);//压缩
                //加密转换成String
                headPictureStr = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
            }
        }
    }

    /**
     * 裁剪压缩图片
     *
     * @param uri 要裁剪的图片地址
     */
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// 裁剪框的比例，1：1，如果没有设置，则自由裁剪模式
        intent.putExtra("aspectY", 1);

        int sculptureSize = (int) getResources().getDimension(R.dimen.register_select_head_sculpture_size);//注意，此处如果没转成int，会卡住
        intent.putExtra("outputX", sculptureSize);// 裁剪后输出图片的尺寸大小，用int类型，否则会卡住
        intent.putExtra("outputY", sculptureSize);
        intent.putExtra("outputFormat", "PNG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_REQUEST_CUT);// 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
    }
}
