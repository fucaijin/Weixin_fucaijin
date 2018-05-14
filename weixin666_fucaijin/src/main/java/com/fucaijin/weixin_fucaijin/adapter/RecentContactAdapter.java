package com.fucaijin.weixin_fucaijin.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fucaijin.weixin_fucaijin.global.WeixinApplication;

import java.util.List;

/**
 * Created by fucaijin on 2018/5/14.
 */
public class RecentContactAdapter extends BaseAdapter {
    Context mContext;
    List list;
    public RecentContactAdapter(List list) {
        this.list = list;
        mContext = WeixinApplication.getmContext();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView tv = new TextView(mContext);
        tv.setText("i = " + i);
        tv.setTextColor(0xFF000000);
        tv.setTextSize(30);
        tv.setPadding(100,0,0,0);
        view = tv;
        return view;
    }


}
