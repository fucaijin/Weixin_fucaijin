package com.fucaijin.weixin_fucaijin.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.fucaijin.weixin_fucaijin.R;

/**
 * Created by fucaijin on 2018/5/9.
 */

public class HomeFoundPageFragment extends Fragment implements AdapterView.OnItemClickListener {
//    TODO 需要根据设置来设定条目是否隐藏，并且如果之前的条目隐藏，当前条目是否要增加MarginTop。读取配置文件，获取显示的array,判断当前哪些条目显示
    int[] foundIconArray;
    String[] foundTextArray;

    public static Fragment getInstance(int[] foundIconArray, String[] foundTextArray) {
        HomeFoundPageFragment homeFragmentFound = new HomeFoundPageFragment();
        Bundle bundle = new Bundle();
        bundle.putIntArray("foundIconArray", foundIconArray);
        bundle.putStringArray("foundTextArray", foundTextArray);
        homeFragmentFound.setArguments(bundle);
        return homeFragmentFound;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        foundTextArray = arguments.getStringArray("foundTextArray");
        foundIconArray = arguments.getIntArray("foundIconArray");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            foundTextArray = savedInstanceState.getStringArray("foundTextArray");
            foundIconArray = savedInstanceState.getIntArray("foundIconArray");
        }

        View foundFragmentLayout = inflater.inflate(R.layout.home_fragment_found, container, false);
        return foundFragmentLayout;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//        TODO 测试用的，实现效果后删除
        TextView itemText = view.findViewById(R.id.home_found_frag_lv_item_tv);
        Toast.makeText(getContext(), itemText.getText(), Toast.LENGTH_SHORT).show();
    }
}
