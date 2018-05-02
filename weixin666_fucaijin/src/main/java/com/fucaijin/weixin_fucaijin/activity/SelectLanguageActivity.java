package com.fucaijin.weixin_fucaijin.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.fucaijin.weixin_fucaijin.R;

public class SelectLanguageActivity extends BaseActivity implements View.OnClickListener {

    private ListView lv_selectLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_language);
        initUI();
        lv_selectLanguage.setAdapter( new LanguageListAdapter());
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
            case R.id.rl_bt_back:
                finish();
                break;
            case R.id.bt_save_select_language:
//                TODO 获取ListView当前选择哪项，然后保存到xml文件中
                break;
        }
    }

    private class LanguageListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return null;
        }
    }

}



