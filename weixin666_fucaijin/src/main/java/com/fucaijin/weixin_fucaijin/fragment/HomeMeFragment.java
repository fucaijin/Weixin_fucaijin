package com.fucaijin.weixin_fucaijin.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fucaijin.weixin_fucaijin.R;

/**
 * Created by fucaijin on 2018/5/9.
 */

public class HomeMeFragment extends Fragment {
//    TODO 需要给各个条目添加ID，并找到，然后实现各个点击事件
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment_me, container, false);
    }
}