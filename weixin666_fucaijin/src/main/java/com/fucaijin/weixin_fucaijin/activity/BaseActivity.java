package com.fucaijin.weixin_fucaijin.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by fucaijin on 2018/5/2.
 */
public class BaseActivity extends SwipeBackActivity {
//    TODO 侧滑关闭，以及打开/关闭的过渡动画做改变，效果未完成

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColor(-1);//传入-1则表示使用默认值
        setSwipeBackEnable(false);//默认设置了不可侧滑返回
    }

    /**
     * 设置状态栏颜色的方法，本方法为default修饰，只能本包类调用，传入的颜色值 0xFF393A3F - 第三第四位代表透明度;
     * 默认颜色是灰色0xFF393A3F，如果需要更改，就传入新值
     * @param color 传入需要将状态栏改成的16进制颜色值，例如：0xFF393A3F
     */
     void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return;
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if(color == -1){
            window.setStatusBarColor(0xFF393A3F);//默认设置状态栏为灰色
        }else {
            window.setStatusBarColor(color);
        }
    }
}
