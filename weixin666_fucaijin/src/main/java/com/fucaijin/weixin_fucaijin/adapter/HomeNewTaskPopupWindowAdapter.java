package com.fucaijin.weixin_fucaijin.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fucaijin.weixin_fucaijin.R;
import com.fucaijin.weixin_fucaijin.data.HomeNewTaskPopulWindowData;
import com.fucaijin.weixin_fucaijin.global.WeixinApplication;

import java.util.ArrayList;

/**
 * Created by fucaijin on 2018/5/15.
 */

public class HomeNewTaskPopupWindowAdapter extends BaseAdapter {

    ArrayList<HomeNewTaskPopulWindowData> datasList;
    public HomeNewTaskPopupWindowAdapter(ArrayList<HomeNewTaskPopulWindowData> populWindowDatas) {
        datasList = populWindowDatas;
    }

    @Override
    public int getCount() {
        return datasList.size();
    }

    @Override
    public Object getItem(int i) {
        return datasList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View inflate = View.inflate(WeixinApplication.getmContext(), R.layout.home_search_popul_window_item_layout, null);
        ImageView newTaskImage = inflate.findViewById(R.id.home_top_tab_search_popup_window_iv);
        TextView newTaskText = inflate.findViewById(R.id.home_top_tab_search_popup_window_tv);
        newTaskImage.setImageResource(datasList.get(i).drawableId);
        newTaskText.setText(datasList.get(i).text);
        return inflate;
    }
}
