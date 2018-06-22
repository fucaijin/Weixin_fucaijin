package com.fucaijin.weixin_fucaijin.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 转换工具类，例如px和sp、dp之间的转换等
 * Created by fucaijin on 2018/5/16.
 */

public class ConvertUtils {

    /**
     * dp转换成px
     */
    public static int dp2px(Context context, float dpValue){
        float scale=context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }

    /**
     * px转换成dp
     */
    public static int px2dp(Context context,float pxValue){
        float scale=context.getResources().getDisplayMetrics().density;
        return (int)(pxValue/scale+0.5f);
    }

    /**
     * sp转换成px
     */
    public static int sp2px(Context context, float spValue){
        float fontScale=context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue*fontScale+0.5f);
    }

    /**
     * px转换成sp
     */
    public static int px2sp(Context context,float pxValue){
        float fontScale=context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue/fontScale+0.5f);
    }

    /**
     * @param drawable 要转换成byte[]的drawable对象
     * @param context 上下文环境
     * @return 转换完成后的byte[]
     */
    public static byte[] drawable2byteArray(Drawable drawable, Context context){
//        先将drawable转换成Bitmap
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        压缩Bitmap到输出流里，三个参数分别是：输出格式，压缩质量（0-100），图像处理的输出流
        bitmap.compress(Bitmap.CompressFormat.PNG, 100 ,byteArrayOutputStream);
//        将输入流转换为Byte数组，便于传输
        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        return imageBytes;
    }

    /**
     * 将字符串转换成md5
     * @param string 要转化成md5的值
     * @return md5后的32位字符的值 例如：8a37d6ac928e7336d43ed13ab823ccf6
     */
    public static String string2md5(String string){
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
