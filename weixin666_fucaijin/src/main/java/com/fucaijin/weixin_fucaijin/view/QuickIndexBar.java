package com.fucaijin.weixin_fucaijin.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.fucaijin.weixin_fucaijin.global.WeixinApplication;
import com.fucaijin.weixin_fucaijin.utils.ConvertUtils;

/**
 * 可检索的列表View
 * Created by fucaijin on 2018/5/31.
 */

public class QuickIndexBar extends View {
    private String[] indexArr = {"↑", "☆", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};
    private Paint mPaint;
    private int measuredWidth;
    private int measuredHeight;
    private boolean isShowBackGround = false;
    private int cellHeight;
    private int lastIndex = -1;

    public QuickIndexBar(Context context) {
        super(context);
        init();
    }

    public QuickIndexBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(!isInEditMode()){
            init();
        }
    }

    public QuickIndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public QuickIndexBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        if(!isInEditMode()){
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//设置抗锯齿
            mPaint.setColor(0xFF616161);
            mPaint.setTextSize(ConvertUtils.sp2px(WeixinApplication.getmContext(), 13));
            mPaint.setTextAlign(Paint.Align.CENTER);//设置文本的起点是文本的正中心
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        measuredWidth = getMeasuredWidth();
        measuredHeight = getMeasuredHeight();
        //根据控件的高度除以文字的数量，得出每个文字的格子的高度
        cellHeight = measuredHeight / indexArr.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < indexArr.length; i++) {
            canvas.drawText(indexArr[i], measuredWidth / 2, cellHeight * (i + 1), mPaint);//绘制文字，并设定它的位置和文字大小
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if (!isShowBackGround) {
                    setBackgroundColor(0x7FBFBFBF);
                    isShowBackGround = true;
                }

                float touchY = event.getY();
                int nowIndex = (int) (touchY / cellHeight);

//                如果触摸点在控件的范围内，且触摸位置的区域有所改变，例如从A变到了B，才执行，一直在A内就不执行
                if (nowIndex >= 0 && nowIndex < indexArr.length) {
                    if (lastIndex != nowIndex) {
                        if(touchLetterListener != null){
//                            把当前点击的字母传出去
                            touchLetterListener.onTouchLetter(indexArr[nowIndex]);
                        }
                        lastIndex = nowIndex;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                setBackgroundColor(0x00BFBFBF);
                isShowBackGround = false;
                lastIndex = -1;
                break;
        }
        return true;
    }

    private onTouchLetterListener touchLetterListener;

    public void setOnTouchLetterListener(onTouchLetterListener touchLetterListener){
        this.touchLetterListener = touchLetterListener;
    }

    public interface onTouchLetterListener{
        void onTouchLetter(String letter);
    }
}
