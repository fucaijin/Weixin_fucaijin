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

import static com.fucaijin.weixin_fucaijin.global.WeixinApplication.mContext;

public class SelectLanguageActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
//    TODO 真正更改语言的逻辑未完成(更改后微信的所有文字都变成相应的语言)
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

//        根据配置文件中记录的选择的语言位置，在开启选择语言节目的时候自动选择选项
        int index = 0;
        String languageIndex = WeixinApplication.getConfig("language");
        if (!languageIndex.equals("")){
            index = Integer.parseInt(languageIndex);
        }

//        ListView数据的填充,传入要显示的列表，以及之前保存在xml文件中的语言选择的设置
        languageListAdapter = new LanguageListAdapter(mContext, languageList,index);
        lv_selectLanguage.setAdapter(languageListAdapter);
    }

    private void initUI() {
        RelativeLayout rl_bt_back = (RelativeLayout) findViewById(R.id.register_rl_bt_back);
        Button bt_save_select_language = (Button) findViewById(R.id.bt_save_select_language);
        lv_selectLanguage = (ListView) findViewById(R.id.lv_selectLanguage);

        rl_bt_back.setOnClickListener(this);
        bt_save_select_language.setOnClickListener(this);
        lv_selectLanguage.setOnItemClickListener(this);
    }

    /**
     * 顶部关闭按钮、保存按钮事件的监听
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_rl_bt_back:
//                TODO 返回按钮的selector遮罩效果(前景变灰)未完成
                finish();
                break;
            case R.id.bt_save_select_language:
                WeixinApplication.setConfigString("language",nowSelectLanguageIndex + "");
                finish();
                break;
        }
    }

    /**
     * ListView条目点击事件的监听
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//        设置当前选中的条目，并刷新数据
        languageListAdapter.setSelectItem(i);
        languageListAdapter.notifyDataSetInvalidated();

//        记录选择的语言的索引，用于保存到配置文件中
        nowSelectLanguageIndex = i;
    }
}



