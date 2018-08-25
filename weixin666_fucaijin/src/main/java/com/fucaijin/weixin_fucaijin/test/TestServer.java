package com.fucaijin.weixin_fucaijin.test;

import com.fucaijin.weixin_fucaijin.utils.Http;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.fucaijin.weixin_fucaijin.utils.Http.HTTP_REQUEST_TYPE_CODE_TEST;

public class TestServer {

    /**
     * 测试服务器是否能正常接收请求
     *
     * @return 返回服务器返回的消息，如果请求不成功，则返回响应码，例如404什么的
     */
    public static String testServer() {
        String testResultStr = "";
        HashMap hashMap = new HashMap();
        hashMap.put("info", "test");
        HashMap responseHashMap = Http.postServer(HTTP_REQUEST_TYPE_CODE_TEST, hashMap);
        if (responseHashMap != null && responseHashMap.get("responseCode") != null) {
            int responseCode = (int) responseHashMap.get("responseCode");
            if (responseCode == 200) {
                JSONObject jsonObject = (JSONObject) responseHashMap.get("jsonObject");
                try {
                    testResultStr = (String) jsonObject.get("content");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return testResultStr;
            } else {
                testResultStr = "Net error code = " + responseCode;
            }

            return testResultStr;
        }
        return "连接服务器失败";
    }
}
