package com.fucaijin.weixin_fucaijin.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fucaijin.weixin_fucaijin.R;
import com.fucaijin.weixin_fucaijin.utils.ConvertUtils;
import com.fucaijin.weixin_fucaijin.utils.JudgementUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import static com.fucaijin.weixin_fucaijin.global.WeixinApplication.mContext;


/**
 * 登录页面：1.手机号登录 2.账号密码登录 3.已注销重新登录
 * 可侧滑关闭（在BaseActivity实现），输入框得获取焦点，且不为空时才显示清除输入框按钮，输入框得填完了，按钮才可点击
 * 登录了之后，要带着ip/IMEI码、请求类型码去请求服务器（ip用于判断用户的位置，给其分配给相应地区的服务器，分流减小服务器的压力），
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
//    TODO 手机号、账号、密码这三个输入框的数字字体的更改
//    TODO 底部找回密码、紧急冻结、微信安全中心的点击逻辑的实现
//    TODO 尚未完成注销之后的使用之前账户登录的界面（语音登录界面）

    private LinearLayout login_ll_use_phone_ui;
    private LinearLayout login_ll_use_other_way_ui;
    private EditText login_et_phone;
    private EditText login_et_account;
    private EditText login_et_password;
    private ImageView login_iv_clean_phone_number;
    private ImageView login_iv_clean_account;
    private ImageView login_iv_clean_password;
    private ImageView login_iv_account_editor_divider;
    private ImageView login_iv_password_editor_divider;
    private Button login_bt_next_step;
    private Button login_bt_login;
    private String urlLogin = "http://192.168.1.105:8000/login/";//TODO 此域名未完成

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setStatusBarColor(0xFFC1C1C1);//设置灰色的标题栏颜色
        initUI();
    }

    private void initUI() {

//        顶部的关闭按钮
        ImageView login_iv_finish = findViewById(R.id.login_iv_finish);
        login_iv_finish.setOnClickListener(this);

        login_ll_use_phone_ui = findViewById(R.id.login_ll_use_phone_ui);
        login_ll_use_other_way_ui = findViewById(R.id.login_ll_use_other_way_ui);

//        手机号登录页面-------------------------------------
        RelativeLayout login_rl_select_country = findViewById(R.id.login_rl_select_country);
        login_et_phone = findViewById(R.id.login_et_phone);
        TextView login_tv_others_way_login = findViewById(R.id.login_tv_others_way_login);
        login_bt_next_step = findViewById(R.id.login_bt_next_step);
        login_iv_clean_phone_number = findViewById(R.id.login_iv_clean_phone_number);

        login_rl_select_country.setOnClickListener(this);
        login_tv_others_way_login.setOnClickListener(this);
        login_bt_next_step.setOnClickListener(this);
        login_iv_clean_phone_number.setOnClickListener(this);

//        手机号输入框的监听器：如果输入框为空，则按钮不可点击，且不显示清除按钮；否则反之
        login_et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)) {
                    login_iv_clean_phone_number.setVisibility(View.INVISIBLE);
                    login_bt_next_step.setEnabled(false);
                } else {
                    login_iv_clean_phone_number.setVisibility(View.VISIBLE);
                    login_bt_next_step.setEnabled(true);
                }
            }
        });
//        ----------------------------------------------------

//        其他方式登录页面------------------------------------
        login_et_account = findViewById(R.id.login_et_account);
        login_et_password = findViewById(R.id.login_et_password);
        TextView login_tv_phone = findViewById(R.id.login_tv_phone);
        login_bt_login = findViewById(R.id.login_bt_login);
        login_iv_clean_account = findViewById(R.id.login_iv_clean_account);
        login_iv_clean_password = findViewById(R.id.login_iv_clean_password);
        login_iv_account_editor_divider = findViewById(R.id.login_iv_account_editor_divider);
        login_iv_password_editor_divider = findViewById(R.id.login_iv_password_editor_divider);

        login_tv_phone.setOnClickListener(this);
        login_bt_login.setOnClickListener(this);
        login_iv_clean_account.setOnClickListener(this);
        login_iv_clean_password.setOnClickListener(this);

//        账号、密码输入框的内容改变监听器：如果输入框有一个为空，则按钮不可点击，为空的输入框不显示清除按钮；否则反之
        login_et_account.addTextChangedListener(new TextWatcher() {
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
                    login_iv_clean_account.setVisibility(View.INVISIBLE);
                    login_bt_login.setEnabled(false);
                } else {
//                    如果账号的输入框不为空：设置清除账号按钮可见，且如果密码不为空就登录按钮可用
                    login_iv_clean_account.setVisibility(View.VISIBLE);
                    if (!JudgementUtils.editTextEmpty(login_et_password)) {
                        login_bt_login.setEnabled(true);
                    } else {
                        login_bt_login.setEnabled(false);
                    }
                }
            }
        });

        login_et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)) {
                    login_iv_clean_password.setVisibility(View.INVISIBLE);
                    login_bt_login.setEnabled(false);
                } else {
                    login_iv_clean_password.setVisibility(View.VISIBLE);
                    if (!JudgementUtils.editTextEmpty(login_et_account)) {
                        login_bt_login.setEnabled(true);
                    } else {
                        login_bt_login.setEnabled(false);
                    }
                }
            }
        });

//        账号、密码输入框的焦点监听器，如果获取焦点其下划线设置为绿色，失去焦点则变回原来的灰色，并隐藏清除按钮
        login_et_account.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean getFocus) {
                if (getFocus) {
//                    注意:用代码设置颜色一定要用8位的颜色，否则会显示透明或不显示
                    login_iv_account_editor_divider.setBackgroundColor(0xFF45C01A);
                    if (!JudgementUtils.editTextEmpty(login_et_account)) {
                        login_iv_clean_account.setVisibility(View.VISIBLE);
                    }
                } else {
//                    注意:用代码设置颜色一定要用8位的颜色，否则会显示透明或不显示
                    login_iv_account_editor_divider.setBackgroundColor(0xFFD9D9D9);
                    login_iv_clean_account.setVisibility(View.INVISIBLE);
                }
            }
        });

        login_et_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean getFocus) {
                if (getFocus) {
//                    注意:用代码设置颜色一定要用8位的颜色，否则会显示透明或不显示
                    login_iv_password_editor_divider.setBackgroundColor(0xFF45C01A);
                    if (!JudgementUtils.editTextEmpty(login_et_password)) {
                        login_iv_clean_password.setVisibility(View.VISIBLE);
                    }
                } else {
//                    注意:用代码设置颜色一定要用8位的颜色，否则会显示透明或不显示
                    login_iv_password_editor_divider.setBackgroundColor(0xFFD9D9D9);
                    login_iv_clean_password.setVisibility(View.INVISIBLE);
                }
            }
        });
//        ----------------------------------------------------

        TextView loginTvTetBackPassword = findViewById(R.id.login_tv_get_back_password);
        TextView loginTvEmergencyFreezing = findViewById(R.id.login_tv_emergency_freezing);
        TextView loginTvSecurityCenter = findViewById(R.id.login_tv_security_center);

        loginTvTetBackPassword.setOnClickListener(this);
        loginTvEmergencyFreezing.setOnClickListener(this);
        loginTvSecurityCenter.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_iv_finish:
                finish();
                break;
            case R.id.login_rl_select_country:
//                TODO 打开选择国家的Avtivity逻辑代码未完成
                break;

//            切换到用微信号/QQ号/邮箱登录
            case R.id.login_tv_others_way_login:
                login_ll_use_phone_ui.setVisibility(View.GONE);
                login_ll_use_other_way_ui.setVisibility(View.VISIBLE);
                login_et_phone.getText().clear();
                break;
//            TODO 下一页按钮的点击事件未完成，判断手机的号码位数 开启手机登录页面密码登录和验证码登录可随意切换
            case R.id.login_bt_next_step:
//                1.弹出加载对话框，然后判断手机位数
//                2.判断完成后关闭加载对话框，如果手机位数正确则进入下一页，如果不正确则弹出手机位数不正确弹窗
                Dialog dialog = new Dialog(this);
                View waitingLoadView = getLayoutInflater().inflate(R.layout.waiting_load_dialog_layout, null);
                dialog.setContentView(waitingLoadView);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                String phone = login_et_phone.getText().toString().trim();
                if(phone.length() == 11){
                    dialog.dismiss();
                    Intent intent = new Intent(this, PhoneLoginActivity.class);
                    intent.putExtra("phone",login_et_phone.getText().toString().trim());
                    startActivity(intent);
                }else {
                    dialog.dismiss();

                    final AlertDialog builder = new AlertDialog.Builder(this).create();
                    View inflate = View.inflate(this, R.layout.login_phone_error, null);
                    View dialogBtnPositive = inflate.findViewById(R.id.bt_login_error_phone_positive);
                    builder.setView(inflate);
                    builder.show();

//                    设置AlertDialog的弹窗大小
                    WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                    params.width = ConvertUtils.dp2px(this,312);//此处两个值是根据目测比对真实微信，在自己手机上的值，可能会有适配问题
                    params.height = ConvertUtils.dp2px(this,208);
                    builder.getWindow().setAttributes(params);

                    dialogBtnPositive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            builder.dismiss();
                        }
                    });
                }

                break;

//            切换到用手机登录
            case R.id.login_tv_phone:
                login_ll_use_other_way_ui.setVisibility(View.GONE);
                login_ll_use_phone_ui.setVisibility(View.VISIBLE);
                login_et_account.getText().clear();
                login_et_password.getText().clear();
                break;
            case R.id.login_bt_login:
                String account = login_et_account.getText().toString().trim();
                String password = login_et_password.getText().toString().trim();
                String md5Password = ConvertUtils.string2md5(password);

                //判断是否有网络，有网络才进行网络请求
                if (JudgementUtils.isNetworkAvailable(this)) {
                    post2Server(account, md5Password);
                } else {
                    Toast.makeText(this, "请连接网络", Toast.LENGTH_SHORT).show();
                }

                break;

//            以下三个都是在登录界面点击清除按钮以后，清除输入框，并隐藏清除按钮
            case R.id.login_iv_clean_phone_number:
                login_et_phone.getText().clear();
                login_iv_clean_phone_number.setVisibility(View.GONE);
                login_bt_next_step.setEnabled(false);
                break;
            case R.id.login_iv_clean_account:
                login_et_account.getText().clear();
                login_iv_clean_account.setVisibility(View.GONE);
                login_bt_login.setEnabled(false);
                break;
            case R.id.login_iv_clean_password:
                login_et_password.getText().clear();
                login_iv_clean_password.setVisibility(View.GONE);
                login_bt_login.setEnabled(false);
                break;

//            底部的三个带有链接的文字:找回密码、紧急冻结、微信安全中心。点击就跳到新的页面，
            case R.id.login_tv_get_back_password:
                break;
            case R.id.login_tv_emergency_freezing:
                break;
            case R.id.login_tv_security_center:
                break;
        }
    }

    /**
     * 用来向发送服务器请求登录
     *
     * @param account     账户名
     * @param md5Password 转换成md5后的密码
     */
    private void post2Server(final String account, final String md5Password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    //根据地址创建URL对象(网络访问的url)
                    URL url = new URL(urlLogin);
                    //打开网络链接
                    connection = (HttpURLConnection) url.openConnection();
                    //设置请求的方式
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    //因为post是通过流往服务器提交数据的，所以我们需要设置允许输出
                    //接收数据的时候
                    connection.setDoOutput(true);//发送POST请求必须设置允许输出,以后就可以使用conn.getOutputStream().write()
                    connection.setDoInput(true);//发送POST请求必须设置允许输入,设置以后就可以使用conn.getInputStream().read();

                    //拼接处要提交的字符串
                    String data = "username=" + URLEncoder.encode(account, "UTF-8") + "&password=" + URLEncoder.encode(md5Password, "UTF-8");

                    //使用post表单提交数据，http请求头会有这样两行数据
                    //Content-Type:application/x-www-form-urlencoded
                    //Content-Length:36(根据实际情况来，是提交的数据的长度)
                    //设置请求头
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    connection.setRequestProperty("Content-Length", String.valueOf(data.length()));

                    //拿到输出流
                    OutputStream os = connection.getOutputStream();
                    //使用输出流向服务器提交数据
                    os.write(data.getBytes());
                    os.flush();

                    if (connection.getResponseCode() == 200) {
                        InputStream is = connection.getInputStream();
                        InputStreamReader isr = new InputStreamReader(is);
                        BufferedReader reader = new BufferedReader(isr);

                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            sb.append(line);
                        }
                        String responseStr = sb.toString();

                        //关闭流数据 节约内存消耗
                        is.close();
                        isr.close();
                        reader.close();
                        connection.disconnect();

                        if (responseStr.equals("登录成功")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(mContext, "登录成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else if (responseStr.equals("登录失败")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(mContext, "登录失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else if (responseStr.equals("用户不存在")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(mContext, "用户不存在，请先注册", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, "网络请求失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (MalformedURLException e) {
                    Log.e("LoginActivity", "url form wrong");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("LoginActivity", "IOException 1:" + e.getMessage());
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

}
