package com.fucaijin.weixin_fucaijin.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fucaijin.weixin_fucaijin.R;
import com.fucaijin.weixin_fucaijin.adapter.AddressListAdapter;
import com.fucaijin.weixin_fucaijin.view.QuickIndexBar;

/**
 * Created by fucaijin on 2018/5/9.
 */

public class HomeFragmentAddressList extends Fragment {

    private QuickIndexBar quickIndexBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.home_fragment_address_list, container, false);

        quickIndexBar = inflate.findViewById(R.id.home_fragment_address_list_quick_index_bar);
        ListView addressListLv = inflate.findViewById(R.id.address_list_lv);
        addressListLv.setAdapter(new AddressListAdapter());

        quickIndexBar.setOnTouchLetterListener(new QuickIndexBar.onTouchLetterListener() {
            @Override
            public void onTouchLetter(String letter) {
//                TODO 快速检索逻辑未完成
            }
        });
        return inflate;
    }

    /**
     * 隐藏通讯录页右侧的检索栏
     */
    public void hideIndexBar() {
//        只要ViewPager有滑动，就调用此方法，隐藏侧边检索栏，并设置检索栏背景为全透明
        quickIndexBar.setVisibility(View.INVISIBLE);
        quickIndexBar.setBackgroundColor(0x00000000);

//        以下注释代码是因为渐变显示效果有BUG，暂时不使用
//        quickIndexBar.setAlpha(0);
//        quickIndexBar.setBackgroundColor(0x00000000);
    }

    /**
     * 显示通讯录页右侧检索栏
     */
    public void showIndexBar() {
//        TODO 通讯录QuickIndex的渐变显示效果未完成
        quickIndexBar.setVisibility(View.VISIBLE);
//        以下注释是用于显示时候渐变显示，但有闪烁的BUG，就是某种情况下他会先显示，再变为0再实现渐变效果
//        ObjectAnimator anim = ObjectAnimator.ofFloat(quickIndexBar, "alpha", 0f, 1f);
//        anim.setDuration(500);// 动画持续时间
//        anim.start();
    }
}
