package com.fucaijin.weixin_fucaijin.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.fucaijin.weixin_fucaijin.R;
import com.fucaijin.weixin_fucaijin.adapter.LanguageListAdapter;
import com.fucaijin.weixin_fucaijin.global.WeixinApplication;

public class SelectLanguageActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView lv_selectLanguage;
    private String[] language = {"跟随系统", "简体中文", "繁體中文（台灣）", "繁體中文（香港）", "English",
            "Bahasa Indonesia", "Bahasa Melayu", "Español", "중국어", "Italiano", "日本語", "Português", "русский",
            "Tiếng Việt", "Türkçe", "Deutsch", "Français"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_language);
        initUI();
        lv_selectLanguage.setAdapter(new LanguageListAdapter(WeixinApplication.getmContext(),language));
    }

    private void initUI() {
        RelativeLayout rl_bt_back = (RelativeLayout) findViewById(R.id.rl_bt_back);
        Button bt_save_select_language = (Button) findViewById(R.id.bt_save_select_language);
        lv_selectLanguage = (ListView) findViewById(R.id.lv_selectLanguage);

        rl_bt_back.setOnClickListener(this);
        bt_save_select_language.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //        TODO 返回按钮的selector未完成
            case R.id.rl_bt_back:
                finish();
                break;
            case R.id.bt_save_select_language:
//                TODO 获取ListView当前选择哪项，然后保存到xml文件中
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ImageView radio_button = view.findViewById(R.id.iv_select_language_radio_button);
        radio_button.setImageResource(R.drawable.radio_button_pressed);
    }
}



