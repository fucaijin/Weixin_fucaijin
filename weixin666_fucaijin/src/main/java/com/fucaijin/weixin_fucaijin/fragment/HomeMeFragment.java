package com.fucaijin.weixin_fucaijin.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fucaijin.weixin_fucaijin.R;

import java.io.File;

/**
 * Created by fucaijin on 2018/5/9.
 */

public class HomeMeFragment extends Fragment {
    String phone;
    String nickName;
    private TextView myNickName;

    public static Fragment getInstance(String phone, String nickName) {
        HomeMeFragment homeMeFragment = new HomeMeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("phone", phone);
        bundle.putString("nickName", nickName);
        homeMeFragment.setArguments(bundle);
        return homeMeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        phone = arguments.getString("phone");
        nickName = arguments.getString("nickName");
    }

//    TODO 需要给各个条目添加ID，并找到，然后实现各个点击事件
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.home_fragment_me, container, false);
        ImageView myHeadSculpture = inflate.findViewById(R.id.home_me_fragment_my_head_sculpture);
        myNickName = inflate.findViewById(R.id.home_me_fragment_my_nick_name);
        TextView myWecahtId = inflate.findViewById(R.id.home_me_fragment_my_wechat_id);

        String myHeadSculpturePath = Environment.getExternalStorageDirectory().getPath() + "/weixin_fucaijin/image/head_sculpture/" + phone + ".png";
        myHeadSculpture.setImageURI(Uri.fromFile(new File(myHeadSculpturePath)));
//        myWechatId.setText();  此处的微信号暂未实现，因此不做任何操作
        return inflate;
    }

    public void setMyNickName(final String nickName) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (myNickName != null) {
                        myNickName.setText(nickName);
                    }
                }
            });
    }
}
