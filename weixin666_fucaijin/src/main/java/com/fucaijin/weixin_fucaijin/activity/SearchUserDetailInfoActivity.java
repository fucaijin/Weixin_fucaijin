package com.fucaijin.weixin_fucaijin.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fucaijin.weixin_fucaijin.R;
import com.fucaijin.weixin_fucaijin.utils.Http;

public class SearchUserDetailInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private String nickName;
    private String phone;
    private String area;
    private String sex;
    private String personalitySignature;
    private TextView personalitySignatureTv;
    private TextView nickNameTv;
    private ImageView headSculptureIv;
    private RelativeLayout backBtnRl;

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
//        Bitmap headSculptureBitmap = ConvertUtils.base64Str2Bitmap(phone);
        Bitmap headSculpture = Http.getHeadSculpture(phone);
        headSculptureIv.setImageBitmap(headSculpture);
        //        设置昵称
        nickNameTv.setText(nickName);

//        TODO 待修改：此处要修改为真实的获取服务端的数据
        personalitySignatureTv.setText("每一个没有进步的日子，都是对生命的辜负");
    }

    private void initData() {
        Intent intent = getIntent();
        nickName = intent.getStringExtra("nickName");
        phone = intent.getStringExtra("phone");
        area = intent.getStringExtra("area");
        personalitySignature = intent.getStringExtra("personalitySignature");
        sex = intent.getStringExtra("sex");
    }

    private void initUi() {
        backBtnRl = (RelativeLayout) findViewById(R.id.search_user_detail_info_activity_top_bar_back_btn_rl);
        backBtnRl.setOnClickListener(this);
        headSculptureIv = findViewById(R.id.search_user_detail_info_activity_head_sculpture_iv);
        nickNameTv = findViewById(R.id.search_user_detail_info_activity_nick_name);
        personalitySignatureTv = findViewById(R.id.search_user_detail_info_activity_personality_signature);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.search_user_detail_info_activity_top_bar_back_btn_rl:
                finish();
                break;
        }
    }
}
