package com.fucaijin.weixin_fucaijin.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fucaijin.weixin_fucaijin.R;

/**
 * fucaijin by zcy912 on 2018/6/7.
 */

public class ChatMoreFunctionFragmentTwo extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View inflate = inflater.inflate(R.layout.activity_chat_more_function_second, null);
        return inflate;

    }
}
