package com.fucaijin.weixin_fucaijin.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.fucaijin.weixin_fucaijin.R;
import com.fucaijin.weixin_fucaijin.adapter.AddressListAdapter;
import com.fucaijin.weixin_fucaijin.view.QuickIndexBar;

import java.util.Collections;

import static com.fucaijin.weixin_fucaijin.global.WeixinApplication.mAddressListItem;

/**
 * Created by fucaijin on 2018/5/9.
 */

public class HomeFragmentAddressList extends Fragment {

    private QuickIndexBar quickIndexBar;
    private TextView currentBigLetterTv;
    private Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.home_fragment_address_list, container, false);

        Collections.sort(mAddressListItem);//根据拼音来排序
        quickIndexBar = inflate.findViewById(R.id.home_fragment_address_list_quick_index_bar);
        currentBigLetterTv = inflate.findViewById(R.id.address_list_quick_index_big_letter);

        final ListView addressListLv = inflate.findViewById(R.id.address_list_lv);
        addressListLv.setAdapter(new AddressListAdapter());

        quickIndexBar.setOnTouchLetterListener(new QuickIndexBar.onTouchLetterListener() {
            @Override
            public void onTouchLetter(String letter) {
//                TODO 快速检索逻辑未完成
                for (int i = 0; i < mAddressListItem.size(); i++) {
                    if(letter.equals(mAddressListItem.get(i).getNickNameFirstLetter())){
                        addressListLv.setSelection(i);
                        break;
                    }
                }
                showCurrentLetter(letter);
            }

            @Override
            public void onCancelTouch() {
                currentBigLetterTv.setVisibility(View.GONE);
            }
        });
        return inflate;
    }

    private void showCurrentLetter(String letter) {
        currentBigLetterTv.setVisibility(View.VISIBLE);
        currentBigLetterTv.setText(letter);
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

    public void hideCurrentBigLetter() {
        currentBigLetterTv.setVisibility(View.GONE);
    }
}
