package com.fucaijin.weixin_fucaijin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fucaijin.weixin_fucaijin.R;
import com.fucaijin.weixin_fucaijin.utils.Http;
import com.fucaijin.weixin_fucaijin.utils.JudgementUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.fucaijin.weixin_fucaijin.global.WeixinApplication.HTTP_HOST_URL;
import static com.fucaijin.weixin_fucaijin.global.WeixinApplication.mContext;

public class SearchFriendActivity extends BaseActivity implements View.OnClickListener {

    public static final int HTTP_REQUEST_TYPE_CODE_GET_SEARCH_FRIEND_INFO = 17;
    public static final String HTTP_GET_URL_SEARCH_USER_INFO = HTTP_HOST_URL + "search_user_info/";
    public static final int HTTP_RESPONSE_TYPE_CODE_SEARCH_USER_FOUND = 26;
    public static final int HTTP_RESPONSE_TYPE_CODE_SEARCH_USER_NOT_FOUND = 28;
    public static final int NETWORK_ERROR = 1011;//网络错误
    private boolean etEmpty;
    private EditText inputEt;
    private ImageView clearEtIv;
    private int INFO_TYPE_WEXIN_ID = 0;
    public static int INFO_TYPE_PHONE = 1;
    private int INFO_TYPE_QQ_ID = 2;
    private LinearLayout itemLl;
    private LinearLayout notFindUserViewRootLl;
    private TextView otherWaySearchTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friend);
        initUi();
        setSwipeBackEnable(true);//开启可以侧滑返回
    }

    private void initUi() {
//        获取Activity根控件、返回键、给返回键设置点击事件，获取输入框控件，输入框清除按钮，获取搜索条，获取搜索条内的可变文本控件
        LinearLayout rootLl = (LinearLayout) findViewById(R.id.search_friend_root_ll);
        rootLl.setOnClickListener(this);
        RelativeLayout backBtnRl = (RelativeLayout) findViewById(R.id.search_friend_activity_top_bar_back_btn_rl);
        backBtnRl.setOnClickListener(this);
        inputEt = (EditText) findViewById(R.id.search_friend_input_et);
        clearEtIv = (ImageView) findViewById(R.id.search_friend_clear_input_iv);
        clearEtIv.setOnClickListener(this);
        itemLl = (LinearLayout) findViewById(R.id.search_friend_item_ll);
        itemLl.setOnClickListener(this);
        final TextView searchInfoTv = (TextView) findViewById(R.id.search_friend_info);

        notFindUserViewRootLl = (LinearLayout) findViewById(R.id.not_find_user_view_root_ll);
        otherWaySearchTv = (TextView) findViewById(R.id.other_way_search_green_tv);

        inputEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().isEmpty()) {
                    clearEtIv.setVisibility(View.INVISIBLE);
                    itemLl.setVisibility(View.GONE);
                    etEmpty = true;
                } else {
                    clearEtIv.setVisibility(View.VISIBLE);
                    etEmpty = false;
                    itemLl.setVisibility(View.VISIBLE);
                    searchInfoTv.setText(editable);
                }
                notFindUserViewRootLl.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_friend_activity_top_bar_back_btn_rl:
                finish();
                break;
            case R.id.search_friend_root_ll:
                if (etEmpty) {
                    finish();
                }
                break;
            case R.id.search_friend_clear_input_iv:
                if (!etEmpty) {
                    inputEt.getText().clear();
                }
                itemLl.setVisibility(View.GONE);
                notFindUserViewRootLl.setVisibility(View.GONE);
                break;
            case R.id.search_friend_item_ll:
                getSearchFriendInfo(inputEt.getText().toString().trim());
                break;
        }
    }

    /**
     * @param searchInfo 根据输入框输入的微信号/手机号
     * @return 返回执行码：找到用户/没找到用户/网络错误
     */
    private void getSearchFriendInfo(String searchInfo) {
//        判断输入的是微信号/手机号/QQ号
        int infoType;
        if (JudgementUtils.containLetter(searchInfo)) {
//            如果包含字母就是微信号
            infoType = INFO_TYPE_WEXIN_ID;
        } else if (searchInfo.length() == 11) {
            //如果长度为11位数就是手机号
            infoType = INFO_TYPE_PHONE;
        } else {
            infoType = INFO_TYPE_QQ_ID;
        }

        HashMap info = new HashMap();
        info.put("info_type", infoType);
        info.put("search_info", searchInfo);
        info.put("url", HTTP_GET_URL_SEARCH_USER_INFO);
        HashMap responseHashMap = Http.getServer(HTTP_REQUEST_TYPE_CODE_GET_SEARCH_FRIEND_INFO, info);

        if (responseHashMap != null) {
//            获取响应码，以及json对象
            int responseCode = (int) responseHashMap.get("responseCode");
            JSONObject jsonObject = (JSONObject) responseHashMap.get("jsonObject");
            Log.v("json", jsonObject.toString());

//            如果相应成功，就获取json里面的数据
            if (responseCode == 200) {
                try {
                    int resultCode = (int) jsonObject.get("code");
                    switch (resultCode) {
                        case HTTP_RESPONSE_TYPE_CODE_SEARCH_USER_FOUND:
//                            查询成功
                            JSONObject jContent = (JSONObject) jsonObject.get("content");

                            String nickName = (String) jContent.get("nick_name");
                            String phone = (String) jContent.get("phone");
                            String area = (String) jContent.get("area");
                            String personalitySignature = (String) jContent.get("personality_signature");
                            String sex = (String) jContent.get("sex");

                            showSearchUserInfo(nickName, phone, area, personalitySignature, sex);
                            break;
                        case HTTP_RESPONSE_TYPE_CODE_SEARCH_USER_NOT_FOUND:
//                            没有查到用户
                            showSearchNoUser();
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(mContext, "注册失败，网络请求失败，responseCode = " + responseCode, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 如果没找到用户信息就执行此方法
     */
    private void showSearchNoUser() {
        itemLl.setVisibility(View.GONE);
        notFindUserViewRootLl.setVisibility(View.VISIBLE);
        otherWaySearchTv.setText(inputEt.getText().toString().trim());
    }

    /**
     * 如果搜索到用户，就执行此方法，跳到用户信息页面
     *
     * @param nickName             从服务器获取到的昵称
     * @param phone        从服务器获取到的头像base64 String格式，需要按需转换成bitmap或drawable
     * @param area                 从服务器获取到的区域
     * @param personalitySignature 从服务器获取到的个性签名
     * @param sex
     */
    private void showSearchUserInfo(String nickName, String phone, String area, String personalitySignature, String sex) {
        Intent intent = new Intent(this, SearchUserDetailInfoActivity.class);
        intent.putExtra("nickName", nickName);
        intent.putExtra("phone", phone);
        intent.putExtra("area", area);
        intent.putExtra("personalitySignature", personalitySignature);
        intent.putExtra("sex", sex);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        notFindUserViewRootLl.setVisibility(View.GONE);
    }
}


