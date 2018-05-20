package com.fucaijin.weixin_fucaijin.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fucaijin.weixin_fucaijin.R;
import com.fucaijin.weixin_fucaijin.adapter.HomeFragmentFoundListViewAdapter;

/**
 * Created by fucaijin on 2018/5/9.
 */

public class HomeFoundPageFragment extends Fragment implements AdapterView.OnItemClickListener {
//    TODO ListView没有实现点击的Selector效果，原因：Item设置了backGround，ListVie的Selector不起作用
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
        ListView homeFragmentFoundLv = (ListView) foundFragmentLayout.findViewById(R.id.home_fragment_found_lv);
        homeFragmentFoundLv.setOnItemClickListener(this);
        homeFragmentFoundLv.setAdapter(new HomeFragmentFoundListViewAdapter(foundIconArray,foundTextArray));
        return foundFragmentLayout;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//        TODO 测试用的，实现效果后删除
        TextView itemText = view.findViewById(R.id.home_found_frag_lv_item_tv);
        Toast.makeText(getContext(), itemText.getText(), Toast.LENGTH_SHORT).show();
    }
}
