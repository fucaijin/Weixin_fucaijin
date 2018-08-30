package com.fucaijin.weixin_fucaijin.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fucaijin.weixin_fucaijin.R;
import com.fucaijin.weixin_fucaijin.adapter.AddFriendMoreFunctionListAdapter;
import com.fucaijin.weixin_fucaijin.global.WeixinApplication;
import com.fucaijin.weixin_fucaijin.utils.ConvertUtils;

public class AddFriendActivity extends BaseActivity implements View.OnClickListener {

    private TextView myIdTv;
    private Context mContext = WeixinApplication.mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        setSwipeBackEnable(true);//开启可以侧滑返回
        initUi();
    }

    private void initUi() {
        RelativeLayout backBtnRl= (RelativeLayout) findViewById(R.id.add_friend_activity_top_bar_back_btn_rl);
        backBtnRl.setOnClickListener(this);
        RelativeLayout searchFriendRl = (RelativeLayout) findViewById(R.id.add_friend_search_friend_rl);
        searchFriendRl.setOnClickListener(this);

        myIdTv = (TextView) findViewById(R.id.add_friend_myself_id);
        Drawable microQrCodeDrawable = ContextCompat.getDrawable(mContext, R.drawable.me_frag_item_qr_code_micro);
        microQrCodeDrawable.setBounds(0,0, ConvertUtils.dp2px(mContext, 18), ConvertUtils.dp2px(mContext, 18));
        myIdTv.setCompoundDrawables(null, null, microQrCodeDrawable, null);

        ListView moreFuncLv = (ListView) findViewById(R.id.add_friend_more_function_lv);
        int[] moreFuncImage = {R.drawable.add_friend_radar_add_friend,
                R.drawable.address_list_group_chat,
                R.drawable.add_friend_activity_scan,
                R.drawable.address_list_new_friends,
                R.drawable.address_list_public_account};
        String[] moreFuncText = {"雷达加朋友", "面对面建群", "扫一扫", "手机联系人", "公众号"};
        String[] moreFuncDescribeText = {"添加身边的朋友", "与身边的朋友进入同一个群聊", "扫描二维码名片", "添加或邀请通讯录中的朋友", "获取更多资讯和服务"};
        moreFuncLv.setAdapter(new AddFriendMoreFunctionListAdapter(mContext,moreFuncImage,moreFuncText,moreFuncDescribeText));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_friend_search_friend_rl:
                startActivity(new Intent(this, SearchFriendActivity.class));
                break;
            case R.id.add_friend_activity_top_bar_back_btn_rl:
                finish();
                break;
        }
    }
}
