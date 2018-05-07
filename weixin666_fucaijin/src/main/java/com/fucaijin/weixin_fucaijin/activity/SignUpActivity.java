package com.fucaijin.weixin_fucaijin.activity;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.fucaijin.weixin_fucaijin.R;

/**
 * 获取填写的信息，以及用户的ip/设备号(防止恶意攻击)、请求类型码提交到服务器，由服务器判断账号是否存在
 * 请求类型码：用于判断是哪种类型请求（例如：注册、登录、发消息、刷朋友圈、）,然后服务器判断请求类型，执行相应的逻辑
 */
public class SignUpActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initUI();
    }

    private void initUI() {
//        设置底部的部分文字添加链接，可以直接转跳到浏览器打开页面
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
}
