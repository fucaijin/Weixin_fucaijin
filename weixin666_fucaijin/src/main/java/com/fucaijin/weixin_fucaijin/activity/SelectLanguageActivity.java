package com.fucaijin.weixin_fucaijin.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.fucaijin.weixin_fucaijin.R;
import com.fucaijin.weixin_fucaijin.adapter.LanguageListAdapter;
import com.fucaijin.weixin_fucaijin.global.WeixinApplication;

public class SelectLanguageActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
//    TODO 真正更改语言的逻辑未完成
//    TODO 进来界面，根据历史记录自动选择到相应条目未实现
    private ListView lv_selectLanguage;
    private String[] languageList = {"跟随系统", "简体中文", "繁體中文（台灣）", "繁體中文（香港）", "English",
            "Bahasa Indonesia", "Bahasa Melayu", "Español", "중국어", "Italiano", "日本語", "Português", "Pусский",
            "Tiếng Việt", "Türkçe", "Deutsch", "Français"};
    private LanguageListAdapter languageListAdapter;
    private int nowSelectLanguageIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_language);
        initUI();
        languageListAdapter = new LanguageListAdapter(WeixinApplication.getmContext(), languageList);
        lv_selectLanguage.setAdapter(languageListAdapter);
//        TODO 根据配置文件，看需要选择哪个选项
        WeixinApplication.getConfig("languageList");
    }

    private void initUI() {
        RelativeLayout rl_bt_back = (RelativeLayout) findViewById(R.id.rl_bt_back);
        Button bt_save_select_language = (Button) findViewById(R.id.bt_save_select_language);
        lv_selectLanguage = (ListView) findViewById(R.id.lv_selectLanguage);

        rl_bt_back.setOnClickListener(this);
        bt_save_select_language.setOnClickListener(this);
        lv_selectLanguage.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //        TODO 返回按钮的selector未完成
            case R.id.rl_bt_back:
                finish();
                break;
            case R.id.bt_save_select_language:
                WeixinApplication.setConfig("language",nowSelectLanguageIndex + "");
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //设置当前选中的条目，并刷新数据
        languageListAdapter.setSelectItem(i);
        languageListAdapter.notifyDataSetInvalidated();
//        记录选择的语言的索引
        nowSelectLanguageIndex = i;
    }
}



