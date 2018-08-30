package com.fucaijin.weixin_fucaijin.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.text.SimpleDateFormat;
import java.util.HashMap;

import static com.fucaijin.weixin_fucaijin.activity.HomeActivity.HTTP_REQUEST_TYPE_CODE_GET_FRIENDS_INFO;
import static com.fucaijin.weixin_fucaijin.activity.PhoneLoginActivity.HTTP_REQUEST_TYPE_CODE_PHONE_LOGIN;
import static com.fucaijin.weixin_fucaijin.activity.RegisterActivity.HTTP_REQUEST_TYPE_CODE_REGISTER;
import static com.fucaijin.weixin_fucaijin.activity.SearchFriendActivity.HTTP_REQUEST_TYPE_CODE_GET_SEARCH_FRIEND_INFO;
import static com.fucaijin.weixin_fucaijin.global.WeixinApplication.HTTP_HOST_URL;

/**
 * 请求网络的类
 * Created by fucaijin on 2018/6/13.
 */

public class Http {
    private static HttpURLConnection postConnection;
    private static HttpURLConnection getConnection;
    public static HashMap postResponseHashMap;
    public static HashMap getResponseHashMap;
    private static URL url;
    public final static int HTTP_REQUEST_TYPE_CODE_TEST = 999;
    public static String HTTP_POST_URL_TEST = HTTP_HOST_URL + "test/";
    public static String HTTP_GET_URL_GET_USER_HEAD_SCULPTURE = HTTP_HOST_URL + "get_head_sculpture/";


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
                return requestFriendsInfo(type, info); //获取好友的头像，昵称等信息
            case HTTP_REQUEST_TYPE_CODE_TEST:
                long currentTimeMillis = System.currentTimeMillis();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String timeStr = format.format(currentTimeMillis);
                requestPostServer(HTTP_POST_URL_TEST,"testServer = " + timeStr);
                return postResponseHashMap;
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
            requestPostServer(urlStr, sendInfoStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (postResponseHashMap != null) {
            return postResponseHashMap;
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
            requestPostServer(urlStr, sendInfoStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (postResponseHashMap != null) {
            return postResponseHashMap;
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
            requestPostServer(urlStr, sendInfoStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (postResponseHashMap != null) {
            return postResponseHashMap;
        }
        return null;
    }

    /**
     * 向服务器发送POST请求
     * @param urlStr      请求的地址
     * @param sendInfoStr 请求的信息
     * @return 返回一个HashMap，HashMap的“responseCode”有请求的结果，
     */
    private static void requestPostServer(final String urlStr, final String sendInfoStr) {
        Thread requestThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //根据地址创建URL对象(网络访问的url)
                    url = new URL(urlStr);

                    //打开网络链接
                    postConnection = (HttpURLConnection) url.openConnection();
                    postConnection.setRequestMethod("POST");
                    postConnection.setConnectTimeout(8000);
                    postConnection.setReadTimeout(8000);
                    postConnection.setDoOutput(true);//因为post是通过流往服务器提交数据的，所以我们需要设置允许输出,然后就可以使用conn.getOutputStream().write()
                    postConnection.setDoInput(true);//接收数据的时候是需要获取流再转成我们要的数据，所以设置为允许输入，然后就可以使用conn.getInputStream().read();
                    //设置请求头
//                    postConnection.setRequestProperty("Charset","UTF-8");
                    postConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    postConnection.setRequestProperty("Content-Length", String.valueOf(sendInfoStr.getBytes().length));

                    OutputStream os = postConnection.getOutputStream();//拿到输出流
                    os.write(sendInfoStr.getBytes());//使用输出流向服务器提交数据
                    os.flush();

                    postResponseHashMap = new HashMap<>();//准备一个HashMap用来装请求的结果，包括请求码，相应消息等
                    if (postConnection.getResponseCode() == 200) {
//                        如果请求成功，读取结果流
                        InputStream is = postConnection.getInputStream();
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
                        postResponseHashMap.put("jsonObject", jsonObject);

                        //关闭流数据 节约内存消耗
                        is.close();
                        isr.close();
                        reader.close();
                        postConnection.disconnect();
                    }
                    //将ResponseCode装到hashMap中
                    postResponseHashMap.put("responseCode", postConnection.getResponseCode());

                } catch (MalformedURLException e) {
                    Log.e("HttpRequestServer Error", "url form wrong.Error type: MalformedURLException");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("HttpRequestServer Error", "IOException 1:" + e.getMessage());
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("JSONException Error", "IOException 1:" + e.getMessage());
                } finally {
                    if (postConnection != null) {
                        postConnection.disconnect();
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

    /**
     * @param type GET请求的请求类型
     * @param info GET请求的数据（其中包含info_type-微信号/手机/QQ和search_info-要搜索的手机或微信号等）
     * @return
     */
    public static HashMap getServer(int type, HashMap info) {
        switch (type) {
            case HTTP_REQUEST_TYPE_CODE_GET_SEARCH_FRIEND_INFO:
                //添加好友时搜索用户的请求
                return requestSearchUserInfo(type,info);
        }
        return null;
    }

    /**
     * @param type GET的类型
     * @param info GET的信息
     * @return 返回GET的结果
     */
    private static HashMap requestSearchUserInfo(int type, HashMap info) {
        int infoType = (int) info.get("info_type");
        String searchInfo = (String) info.get("search_info");
        String url = (String) info.get("url");
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", type);
            jsonObject.put("info_type", infoType);
            jsonObject.put("search_info", searchInfo);
            String sendInfo = jsonObject.toString();
//            向服务器发送请求
            requestGetServer(url, sendInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (getResponseHashMap != null) {
            return getResponseHashMap;
        }

        return null;
    }

    /**
     * 向服务器发送GET请求
     * @param urlStr      请求的地址
     * @param sendInfoStr 请求的信息
     * @return 返回一个HashMap，HashMap的“responseCode”有请求的结果，
     */
    private static void requestGetServer(final String urlStr, final String sendInfoStr) {
        Thread requestThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //根据地址创建URL对象(网络访问的url)
                    url = new URL(urlStr);

                    //打开网络链接
                    getConnection = (HttpURLConnection) url.openConnection();
                    getConnection.setRequestMethod("GET");
                    getConnection.setConnectTimeout(8000);
                    getConnection.setReadTimeout(8000);
                    getConnection.setDoOutput(true);//因为post是通过流往服务器提交数据的，所以我们需要设置允许输出,然后就可以使用conn.getOutputStream().write()
                    getConnection.setDoInput(true);//接收数据的时候是需要获取流再转成我们要的数据，所以设置为允许输入，然后就可以使用conn.getInputStream().read();

                    OutputStream os = getConnection.getOutputStream();//拿到输出流
                    os.write(sendInfoStr.getBytes());//使用输出流向服务器提交数据
                    os.flush();

                    getResponseHashMap = new HashMap<>();//准备一个HashMap用来装请求的结果，包括请求码，相应消息等
                    if (getConnection.getResponseCode() == 200) {
//                        如果请求成功，读取结果流
                        InputStream is = getConnection.getInputStream();
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
                        getResponseHashMap.put("jsonObject", jsonObject);

                        //关闭流数据 节约内存消耗
                        is.close();
                        isr.close();
                        reader.close();
                        getConnection.disconnect();
                    }
                    //将ResponseCode装到hashMap中
                    getResponseHashMap.put("responseCode", getConnection.getResponseCode());

                } catch (MalformedURLException e) {
                    Log.e("HttpRequestServer Error", "url form wrong.Error type: MalformedURLException");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("HttpRequestServer Error", "IOException 1:" + e.getMessage());
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("JSONException Error", "IOException 1:" + e.getMessage());
                } finally {
                    if (getConnection != null) {
                        getConnection.disconnect();
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

    public static Bitmap getHeadSculpture(final String phone){
        final Bitmap[] bm = new Bitmap[1];
        Thread requestThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //根据地址创建URL对象(网络访问的url)
                    url = new URL(HTTP_GET_URL_GET_USER_HEAD_SCULPTURE);

                    //打开网络链接
                    getConnection = (HttpURLConnection) url.openConnection();
                    getConnection.setRequestMethod("GET");
                    getConnection.setConnectTimeout(8000);
                    getConnection.setReadTimeout(8000);
                    getConnection.setDoOutput(true);//因为post是通过流往服务器提交数据的，所以我们需要设置允许输出,然后就可以使用conn.getOutputStream().write()
                    getConnection.setDoInput(true);//接收数据的时候是需要获取流再转成我们要的数据，所以设置为允许输入，然后就可以使用conn.getInputStream().read();

                    OutputStream os = getConnection.getOutputStream();//拿到输出流
                    os.write(phone.getBytes());//使用输出流向服务器提交数据
                    os.flush();
                    if (getConnection.getResponseCode() == 200) {
//                        如果请求成功，读取结果流
                        InputStream is = getConnection.getInputStream();
                        //把流里的数据读取出来，并构造成图片
                        bm[0] = BitmapFactory.decodeStream(is);
                        is.close();
                        getConnection.disconnect();
                    }
                } catch (MalformedURLException e) {
                    Log.e("HttpRequestServer Error", "url form wrong.Error type: MalformedURLException");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("HttpRequestServer Error", "IOException 1:" + e.getMessage());
                } finally {
                    if (getConnection != null) {
                        getConnection.disconnect();
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
        return bm[0];
    }
}
