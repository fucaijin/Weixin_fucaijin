package com.fucaijin.weixin_fucaijin.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fucaijin.weixin_fucaijin.R;
import com.fucaijin.weixin_fucaijin.global.WeixinApplication;

import static com.fucaijin.weixin_fucaijin.global.WeixinApplication.headSculptureList;
import static com.fucaijin.weixin_fucaijin.global.WeixinApplication.nickNameList;

/**
 * Created by fucaijin on 2018/6/1.
 */

public class AddressListAdapter extends BaseAdapter {

    @Override
    public int getCount() {
        return nickNameList.length;
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
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if(convertView == null){
            convertView = View.inflate(WeixinApplication.getmContext(), R.layout.home_fragment_address_list_item, null);
        }
//        TODO 汉字转拼音，并根据拼音来排序和是否显示首字母，并且实现检索自动跳到相应item
        ViewHolder holder = ViewHolder.getViewHolder(convertView);
        holder.firstWord.setText("A");
        holder.headSculpture.setImageResource(headSculptureList[i]);
        holder.nickName.setText(nickNameList[i]);

        return convertView;
    }

    static class ViewHolder{
        TextView firstWord;
        TextView nickName;
        ImageView headSculpture;

        public ViewHolder(View convertView){
            firstWord = convertView.findViewById(R.id.address_list_name_first_word);
            headSculpture = convertView.findViewById(R.id.address_list_head_sculpture);
            nickName = convertView.findViewById(R.id.address_list_nick_name_tv);
        }

        public static ViewHolder getViewHolder(View convertView){
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            if(viewHolder == null){
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            return viewHolder;
        }
    }
}
