package com.fucaijin.weixin_fucaijin.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.fucaijin.weixin_fucaijin.R;
import com.fucaijin.weixin_fucaijin.utils.ConvertUtils;

public class SearchUserDetailInfoActivity extends AppCompatActivity {

    private String nickName;
    private String headSculpture;
    private String area;
    private String personalitySignature;
    private TextView personalitySignatureTv;
    private TextView nickNameTv;
    private ImageView headSculptureIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user_detail_info);
        initUi();
        initData();
        showUi();
    }

    private void showUi() {
//        设置头像
        Bitmap headSculptureBitmap = ConvertUtils.base64Str2Bitmap(headSculpture);
        if(headSculptureBitmap != null){
            headSculptureIv.setImageBitmap(headSculptureBitmap);
        }

//        设置昵称
        nickNameTv.setText(nickName);

//        TODO 待修改：此处要修改为真实的获取服务端的数据
        personalitySignatureTv.setText("每一个没有进步的日子，都是对生命的辜负");
    }

    private void initData() {
        Intent intent = getIntent();
        nickName = intent.getStringExtra("nickName");
        headSculpture = intent.getStringExtra("headSculpture");
        area = intent.getStringExtra("area");
        personalitySignature = intent.getStringExtra("personalitySignature");
    }

    private void initUi() {
        headSculptureIv = findViewById(R.id.search_user_detail_info_activity_head_sculpture_iv);
        nickNameTv = findViewById(R.id.search_user_detail_info_activity_nick_name);
        personalitySignatureTv = findViewById(R.id.search_user_detail_info_activity_personality_signature);
    }
}
