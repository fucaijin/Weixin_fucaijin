package com.fucaijin.weixin_fucaijin.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fucaijin.weixin_fucaijin.R;
import com.fucaijin.weixin_fucaijin.global.WeixinApplication;
import com.fucaijin.weixin_fucaijin.utils.ConvertUtils;

/**
 * 发现页面的ListView的适配器
 * Created by fucaijin on 2018/5/19.
 */

public class HomeFragmentFoundListViewAdapter extends BaseAdapter {
    private int[] foundIconArray;
    private String[] foundTextArray;

    public HomeFragmentFoundListViewAdapter(int[] foundIconArray, String[] foundTextArray) {
        this.foundIconArray = foundIconArray;
        this.foundTextArray = foundTextArray;
    }

    @Override
    public int getCount() {
        return foundTextArray.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView = View.inflate(WeixinApplication.getmContext(), R.layout.home_found_frag_lv_item, null);

        LinearLayout itemRootLl = itemView.findViewById(R.id.home_found_frag_lv_item_circle_of_friends);

        ImageView itemIv = itemView.findViewById(R.id.home_found_frag_lv_item_iv);
        TextView itemTv = itemView.findViewById(R.id.home_found_frag_lv_item_tv);
        ImageView itemDivider = itemView.findViewById(R.id.home_found_frag_lv_item_divider);

        itemIv.setImageResource(foundIconArray[i]);
        itemTv.setText(foundTextArray[i]);

//        如果是第一个条目，或者是第偶数个条目，都要与上面有间隔
        if(i == 0 || i%2 == 1){
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(itemRootLl.getLayoutParams());
            layoutParams.setMargins(0, ConvertUtils.dp2px(WeixinApplication.getmContext(),20), 0, 0);
            itemRootLl.setLayoutParams(layoutParams);

            if (i == 0 ) itemDivider.setVisibility(View.GONE);
        }else {
//            设置分割线是否可见
            itemDivider.setVisibility(View.GONE);
        }

        return itemView;
    }
}
