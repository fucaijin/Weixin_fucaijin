package com.fucaijin.weixin_fucaijin.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fucaijin.weixin_fucaijin.R;
import com.fucaijin.weixin_fucaijin.activity.PersonalActivity;
import com.fucaijin.weixin_fucaijin.adapter.AddressListAdapter;
import com.fucaijin.weixin_fucaijin.utils.ConvertUtils;
import com.fucaijin.weixin_fucaijin.view.QuickIndexBar;

import java.util.Collections;

import static com.fucaijin.weixin_fucaijin.global.WeixinApplication.mAddressListItem;
import static com.fucaijin.weixin_fucaijin.global.WeixinApplication.mAddressListOfficialItemSize;
import static com.fucaijin.weixin_fucaijin.global.WeixinApplication.mContext;

/**
 * Created by fucaijin on 2018/5/9.
 */

public class HomeAddressListFragment extends Fragment implements AdapterView.OnItemClickListener {
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
//                    获取传过来的View对象，并提取昵称和头像，并传到下一个打开的页面(个人信息页面PersonalActivity)
                    View view = (View) msg.obj;

//                    还原对象
                    TextView nickName = view.findViewById(R.id.address_list_nick_name_tv);
                    ImageView headSculpture = view.findViewById(R.id.address_list_head_sculpture);

//                    提取昵称中的文字
                    String name = nickName.getText().toString();

//                    以下注释是因为已经将drawable转换成Byte[]封装到了工具类，因此注释，如果封装无Bug可以删除

//                    获取头像图片，并转为Bitmap对象
//                    Bitmap headSculptureBitMap = ((BitmapDrawable)headSculpture.getDrawable()).getBitmap();
//                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
////                    压缩Bitmap到输出流里，三个参数分别是：输出格式，压缩质量（0-100），图像处理的输出流
//                    headSculptureBitMap.compress(Bitmap.CompressFormat.PNG, 100 ,byteArrayOutputStream);
////                    将输入流转换为Byte数组，便于传输
//                    byte[] imageBytes = byteArrayOutputStream.toByteArray();

                    byte[] imageBytes = ConvertUtils.drawable2byteArray(headSculpture.getDrawable(), mContext);

//                    将昵称和图像传给要打开的PersonalActivity页面
                    Intent intent = new Intent(mContext, PersonalActivity.class);
                    intent.putExtra("nickName",name);
                    intent.putExtra("imageByte", imageBytes);
                    startActivity(intent);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private QuickIndexBar quickIndexBar;
    private TextView currentBigLetterTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.home_fragment_address_list, container, false);

        Collections.sort(mAddressListItem);//根据拼音来排序
        quickIndexBar = inflate.findViewById(R.id.home_fragment_address_list_quick_index_bar);
        currentBigLetterTv = inflate.findViewById(R.id.address_list_quick_index_big_letter);

        final ListView addressListLv = inflate.findViewById(R.id.address_list_lv);
        addressListLv.setAdapter(new AddressListAdapter(mHandler));
//        addressListLv.setOnItemClickListener(this); //条目的点击事件在AddressListAdapter设置，代替setOnItemClickListener

//        给右侧边栏设置触摸监听器，监听触摸到哪个字母，和监听手指是否离开右侧边栏
        quickIndexBar.setOnTouchLetterListener(new QuickIndexBar.onTouchLetterListener() {
            @Override
            public void onTouchLetter(String letter) {
                for (int i = 0; i < mAddressListItem.size(); i++) {
                    if(letter.equals(mAddressListItem.get(i).getNickNameFirstLetter())){
                        addressListLv.setSelection(i + mAddressListOfficialItemSize);
                        break;
                    }else if(letter.equals("↑")){
                        addressListLv.setSelection(0);
                        break;
                    }
                }
                showCurrentBigLetter(letter);
            }

            @Override
            public void onCancelTouch() {
                currentBigLetterTv.setVisibility(View.GONE);
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

//        以下注释代码是因为渐变透明显示效果有BUG，暂时不使用
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

    /**
     * 隐藏中间显示的当前的大字母
     * @param letter 当前右侧检索栏所滑到的字母
     */
    private void showCurrentBigLetter(String letter) {
        currentBigLetterTv.setVisibility(View.VISIBLE);
        currentBigLetterTv.setText(letter);
    }

    /**
     * 隐藏中间显示的当前的大字母
     */
    public void hideCurrentBigLetter() {
        currentBigLetterTv.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(mContext,"ListView这是第 " + i,Toast.LENGTH_SHORT).show();
    }
}
