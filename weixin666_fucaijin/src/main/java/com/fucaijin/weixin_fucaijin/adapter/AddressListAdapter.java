package com.fucaijin.weixin_fucaijin.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fucaijin.weixin_fucaijin.R;

import static com.fucaijin.weixin_fucaijin.global.WeixinApplication.mAddressListItem;
import static com.fucaijin.weixin_fucaijin.global.WeixinApplication.mContext;

/**
 * 通讯录的列表的适配器，数据是从WeixinApplication类中获取的
 * Created by fucaijin on 2018/6/1.
 */

public class AddressListAdapter extends BaseAdapter implements View.OnLongClickListener, View.OnClickListener {

    @Override
    public int getCount() {
        return mAddressListItem.size();
    }

    @Override
    public Object getItem(int i) {
        return mAddressListItem.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if(convertView == null){
            convertView = View.inflate(mContext, R.layout.home_fragment_address_list_item, null);
        }

//        从数据中获取昵称、昵称的首字母
        String nickName = mAddressListItem.get(i).getNickName();
        String nickNameFirstLetter = mAddressListItem.get(i).getNickNameFirstLetter();

        ViewHolder holder = ViewHolder.getViewHolder(convertView);
        holder.headSculpture.setImageResource(mAddressListItem.get(i).getHeadSculpture());
        holder.nickName.setText(nickName);

//        原本应该给ListView设置点击和长按事件的，结果ListView没响应，只能在此处设置，
//        TODO 但长按通讯录，弹出PopupWindow功能未实现
//        holder.itemRoot.setOnLongClickListener(this);
//        holder.itemRoot.setOnClickListener(this);

//        如果当前不是第一个条目，则判断当前条目的首字母是否和上一个的相同，如果相同就隐藏，不相同就显示
        if(i > 0 ){
            String lastNickNameFirstLetter = mAddressListItem.get(i - 1).getNickNameFirstLetter();//获取上一个条目的首字母
            if(nickNameFirstLetter.equals(lastNickNameFirstLetter)){
                holder.firstWord.setVisibility(View.GONE);
            }else {
                holder.firstWord.setVisibility(View.VISIBLE);
                holder.firstWord.setText(nickNameFirstLetter);
            }
        }else {
            holder.firstWord.setVisibility(View.VISIBLE);
            holder.firstWord.setText(nickNameFirstLetter);
        }
        return convertView;
    }

    @Override
    public boolean onLongClick(View view) {
        Toast.makeText(mContext,"长按了 - 内部",Toast.LENGTH_SHORT).show();
        TextView tv = new TextView(mContext);
        tv.setText("设置备注及标签");
        tv.setTextColor(0xFF000000);
//        以下代码无效，弹不出来PopupWindow，暂时注销
//        PopupWindow popupWindow = new PopupWindow(tv, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
//        popupWindow.update();
        return true;
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(mContext,"点击了 - 内部",Toast.LENGTH_SHORT).show();
    }

    private static class ViewHolder{
        TextView firstWord;
        TextView nickName;
        ImageView headSculpture;
        RelativeLayout itemRoot;

        ViewHolder(View convertView){
            firstWord = convertView.findViewById(R.id.address_list_name_first_word);
            headSculpture = convertView.findViewById(R.id.address_list_head_sculpture);
            nickName = convertView.findViewById(R.id.address_list_nick_name_tv);
            itemRoot = convertView.findViewById(R.id.address_list_item_root);

        }

        static ViewHolder getViewHolder(View convertView){
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            if(viewHolder == null){
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            return viewHolder;
        }
    }
}
