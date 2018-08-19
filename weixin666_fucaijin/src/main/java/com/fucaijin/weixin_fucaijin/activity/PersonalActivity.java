package com.fucaijin.weixin_fucaijin.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fucaijin.weixin_fucaijin.R;
import com.fucaijin.weixin_fucaijin.utils.ConvertUtils;
import com.github.promeg.pinyinhelper.Pinyin;

import java.util.Random;

public class PersonalActivity extends AppCompatActivity implements View.OnClickListener {

    private String nickName;
    private boolean isMan;
    private Bitmap headSculptureBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        initData();
        initUi();
    }

    private void initUi() {
        RelativeLayout backBtnRl = findViewById(R.id.personal_activity_top_bar_back_btn_rl);
        backBtnRl.setOnClickListener(this);

        TextView personalNickName = findViewById(R.id.personal_activity_nick_name);
        personalNickName.setText(nickName);
//        随机设置性别(图像)图像
        int sexImageSize = ConvertUtils.dp2px(this, 14);
        Drawable drawable;
        if(new Random().nextBoolean()){
            drawable = getResources().getDrawable(R.drawable.personal_activity_sex_female, null);
        }else {
            drawable = getResources().getDrawable(R.drawable.personal_activity_sex_male, null);
        }
        drawable.setBounds(0, 0, sexImageSize,sexImageSize);
        personalNickName.setCompoundDrawables(null, null, drawable,null);

        ImageView personalHeadSulptureIv  = findViewById(R.id.personal_activity_head_sulpture);
        personalHeadSulptureIv.setImageBitmap(headSculptureBitmap);

        TextView wechatIdTv = findViewById(R.id.personal_activity_wechat_id);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("微信号:");

        // 将昵称转成拼音，作为微信号
        char[] chars = nickName.toCharArray();
        for(char eachChars : chars){
            String s = Pinyin.toPinyin(eachChars);
            stringBuilder.append(s.toLowerCase());//转小写再拼接
        }
        String virtulWechatId = stringBuilder.toString();

        wechatIdTv.setText(virtulWechatId);

    }

    private void initData() {
        nickName = getIntent().getStringExtra("nickName");
        byte[] imageBytes = getIntent().getByteArrayExtra("imageByte");
        headSculptureBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.personal_activity_top_bar_back_btn_rl:
                finish();
                break;
        }
    }
}
