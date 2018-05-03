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
        //TODO ListView的优化
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(context,R.layout.item_language,null);
            viewHolder.tv_language = convertView.findViewById(R.id.tv_language);
            viewHolder.iv_radio_button = convertView.findViewById(R.id.iv_select_language_radio_button);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_language.setText(languages[i]);

        if(selectItem != i){
            viewHolder.iv_radio_button.setImageResource(R.drawable.radio_button_normal);
        }else {
            viewHolder.iv_radio_button.setImageResource(R.drawable.radio_button_pressed);
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
