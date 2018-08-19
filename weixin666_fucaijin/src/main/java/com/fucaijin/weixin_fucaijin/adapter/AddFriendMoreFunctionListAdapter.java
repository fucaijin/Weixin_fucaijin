package com.fucaijin.weixin_fucaijin.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fucaijin.weixin_fucaijin.R;

public class AddFriendMoreFunctionListAdapter extends BaseAdapter {

    private final int[] moreFuncImage;
    private final String[] moreFuncText;
    private final String[] moreFuncDescribeText;
    private Context context;

    public AddFriendMoreFunctionListAdapter(Context context, int[] moreFuncImage, String[] moreFuncText, String[] moreFuncDescribeText) {
        this.context=context;
        this.moreFuncImage =moreFuncImage;
        this.moreFuncText =moreFuncText;
        this.moreFuncDescribeText =moreFuncDescribeText;
    }

    @Override
    public int getCount() {
        return moreFuncText.length;
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
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if(convertView == null){
            convertView = View.inflate(context, R.layout.add_friend_more_function_item_layout, null);
        }
        ViewHolder viewHolder = ViewHolder.getViewHolder(convertView);
        viewHolder.imageView.setImageDrawable(context.getDrawable(moreFuncImage[i]));
        viewHolder.textView.setText(moreFuncText[i]);
        viewHolder.describeTextView.setText(moreFuncDescribeText[i]);
        return convertView;
    }

    static class ViewHolder{
        ImageView imageView;
        TextView textView;
        TextView describeTextView;

        public ViewHolder(View view) {
            imageView = view.findViewById(R.id.add_friend_more_item_iv);
            textView = view.findViewById(R.id.add_friend_more_item_tv);
            describeTextView = view.findViewById(R.id.add_friend_more_item_des_tv);
        }

        public static ViewHolder getViewHolder(View view){
            ViewHolder viewHolder = (ViewHolder) view.getTag();
            if(viewHolder == null){
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            }
            return viewHolder;
        }
    }
}
