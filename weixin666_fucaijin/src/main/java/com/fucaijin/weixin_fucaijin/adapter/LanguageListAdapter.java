package com.fucaijin.weixin_fucaijin.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fucaijin.weixin_fucaijin.R;

/**
 * Created by fucaijin on 2018/5/2.
 */

public class LanguageListAdapter extends BaseAdapter {
    private Context context;
    private String[] languages;
    private int selectItem = -1;
    private int defaultIndex;
    private boolean showDefaultItem = false;

    public LanguageListAdapter(Context context, String[] language, int index) {
        this.context = context;
        this.languages = language;

        defaultIndex = index;
        showDefaultItem = true;
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
        //TODO ListView的优化
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(context,R.layout.select_language_list_view_item,null);
            viewHolder.tv_language = convertView.findViewById(R.id.tv_language);
            viewHolder.iv_radio_button = convertView.findViewById(R.id.iv_select_language_radio_button);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_language.setText(languages[i]);

//        判断当前条目是否selectItem来设定其图案
        if(selectItem != i){
            viewHolder.iv_radio_button.setImageResource(R.drawable.radio_button_normal);
        }else {
            viewHolder.iv_radio_button.setImageResource(R.drawable.radio_button_pressed);
        }

//        如果是刚打开页面，而且当前View是之前选择语言的Index，就设置为选中
        if(showDefaultItem && i == defaultIndex){
            viewHolder.iv_radio_button.setImageResource(R.drawable.radio_button_pressed);
            showDefaultItem = false;
        }

        return convertView;
    }

    private static class ViewHolder{
        TextView tv_language;
        ImageView iv_radio_button;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }
}
