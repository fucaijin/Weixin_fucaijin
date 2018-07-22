package com.fucaijin.weixin_fucaijin.utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import static com.fucaijin.weixin_fucaijin.activity.HomeActivity.HTTP_REQUEST_TYPE_CODE_GET_FRIENDS_INFO;
import static com.fucaijin.weixin_fucaijin.activity.PhoneLoginActivity.HTTP_REQUEST_TYPE_CODE_PHONE_LOGIN;
import static com.fucaijin.weixin_fucaijin.activity.RegisterActivity.HTTP_REQUEST_TYPE_CODE_REGISTER;

/**
 * 请求网络的类
 * Created by fucaijin on 2018/6/13.
 */

public class Http {
    private static HttpURLConnection connection;
    private static URL url;
    public static HashMap responseHashMap;

    /**
     * 用来向发送服务器post请求的
     *
     * @param type 请求类型，例如是注册，还是登录，或者是聊天什么的
     * @param info 请求的信息
     */
    public static HashMap postServer(int type, HashMap info) {
        switch (type) {
            case HTTP_REQUEST_TYPE_CODE_REGISTER:
                //注册请求
                return requestRegister(type, info);
            case HTTP_REQUEST_TYPE_CODE_PHONE_LOGIN:
                //登录请求
                return requestLogin(type, info);
            case HTTP_REQUEST_TYPE_CODE_GET_FRIENDS_INFO:
                return requestFriendsInfo(type, info);
        }
        return null;
    }

    /**
     * @param type 请求类型码
     * @param info 要提交的信息
     * @return 从服务器得到的所有好友的昵称、微信号、前面、头像等信息
     */
    private static HashMap requestFriendsInfo(int type, HashMap info) {
        String phone = (String) info.get("phone");
        String urlStr = (String) info.get("url");

//        拼接要发送的信息
        String sendInfoStr;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", type);
            jsonObject.put("phone", phone);
            sendInfoStr = jsonObject.toString();

//            向服务器发送请求
            requestServer(urlStr, sendInfoStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (responseHashMap != null) {
            return responseHashMap;
        }
        return null;
    }

    private static HashMap requestLogin(int type, HashMap info) {
        String phone = (String) info.get("phone");
        String password = (String) info.get("password");
        String urlStr = (String) info.get("url");

        //        拼接要发送的信息
        String sendInfoStr;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", type);
            jsonObject.put("phone", phone);
            jsonObject.put("password", password);
            sendInfoStr = jsonObject.toString();

//            向服务器发送请求
            requestServer(urlStr, sendInfoStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (responseHashMap != null) {
            return responseHashMap;
        }
        return null;
    }

    /**
     * 请求注册
     *
     * @param type
     * @param info 注册需要的信息集合HashMap
     */
    private static HashMap requestRegister(int type, HashMap info) {
        String urlStr = (String) info.get("url");
        String nickName = (String) info.get("nickName");
        String phone = (String) info.get("phone");
        String password = (String) info.get("password");
        String headPicture = (String) info.get("headPicture");

//        拼接要发送的信息
        String sendInfoStr;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", type);
            jsonObject.put("nickName", nickName);
            jsonObject.put("phone", phone);
            jsonObject.put("password", password);
            jsonObject.put("headPicture", headPicture);
            sendInfoStr = jsonObject.toString();

//            向服务器发送请求
            requestServer(urlStr, sendInfoStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (responseHashMap != null) {
            return responseHashMap;
        }
        return null;
    }

    /**
     * 向服务器发送请求
     *
     * @param urlStr      请求的地址
     * @param sendInfoStr 请求的信息
     * @return 返回一个HashMap，HashMap的“responseCode”有请求的结果，
     */
    private static void requestServer(final String urlStr, final String sendInfoStr) {
        Thread requestThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //根据地址创建URL对象(网络访问的url)
                    url = new URL(urlStr);

                    //打开网络链接
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoOutput(true);//因为post是通过流往服务器提交数据的，所以我们需要设置允许输出,然后就可以使用conn.getOutputStream().write()
                    connection.setDoInput(true);//接收数据的时候是需要获取流再转成我们要的数据，所以设置为允许输入，然后就可以使用conn.getInputStream().read();
                    //设置请求头
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    connection.setRequestProperty("Content-Length", String.valueOf(sendInfoStr.getBytes().length));

                    OutputStream os = connection.getOutputStream();//拿到输出流
                    os.write(sendInfoStr.getBytes());//使用输出流向服务器提交数据
                    os.flush();

                    responseHashMap = new HashMap<>();//准备一个HashMap用来装请求的结果，包括请求码，相应消息等
                    if (connection.getResponseCode() == 200) {
//                        如果请求成功，读取结果流
                        InputStream is = connection.getInputStream();
                        InputStreamReader isr = new InputStreamReader(is);
                        BufferedReader reader = new BufferedReader(isr);

//                        拼接结果
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            sb.append(line);
                        }
                        String responseContent = sb.toString();
                        JSONObject jsonObject = new JSONObject(responseContent);
                        //将接收到的消息装到hashMap中
                        responseHashMap.put("jsonObject", jsonObject);

                        //关闭流数据 节约内存消耗
                        is.close();
                        isr.close();
                        reader.close();
                        connection.disconnect();
                    }
                    //将ResponseCode装到hashMap中
                    responseHashMap.put("responseCode", connection.getResponseCode());

                } catch (MalformedURLException e) {
                    Log.e("HttpRequestServer Error", "url form wrong.Error type: MalformedURLException");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("HttpRequestServer Error", "IOException 1:" + e.getMessage());
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("JSONException Error", "IOException 1:" + e.getMessage());
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        });
        requestThread.start();
        try {
            requestThread.join();//执行了此方法thread.join()后，只有在thread执行完成后，在此行之后的代码才能继续往下执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
