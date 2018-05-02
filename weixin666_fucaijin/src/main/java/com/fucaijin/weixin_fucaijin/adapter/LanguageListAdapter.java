package com.fucaijin.weixin_fucaijin.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fucaijin.weixin_fucaijin.R;

/**
 * Created by fucaijin on 2018/5/2.
 */

public class LanguageListAdapter extends BaseAdapter {
    private Context context;
    private String[] languages;
    public LanguageListAdapter(Context context, String[] language) {
        this.context = context;
        this.languages = language;
    }

    @Override
    public int getCount() {
        return languages.length;
    }

    @Override
    public Object getItem(int i) {
        return languages[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = View.inflate(context,R.layout.language_item,null);
        TextView tv_ = view.findViewById(R.id.tv_);
//        ImageView iv_select_language_radio_button = view.findViewById(R.id.iv_select_language_radio_button);
        tv_.setText(languages[i]);
        return view;
    }
}
