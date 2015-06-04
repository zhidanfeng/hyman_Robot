package com.zhi.app.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import com.google.gson.Gson;
import com.zhi.app.entity.ChatMsg;
import com.zhi.app.entity.ReturnMsg;
import com.zhi.app.entity.ChatMsg.Type;

/**
 * Created by lenovo on 2015/5/23 0023.
 */
public class HttpUtils {
    private static final String URL = "http://www.tuling123.com/openapi/api";
    private static final String API_KEY = "8fb8bb94e811731285f53e2ffb230d07";
    
    /**
     * 向机器人发送想问的问题，并获得封装后的返回的答案
     * @param msg
     * @return
     */
    public static ChatMsg sendMsg(String msg) {
    	ChatMsg chatMsg = new ChatMsg();
    	
    	String returnJson = doGet(msg);
    	Gson gson = new Gson();
    	ReturnMsg returnMsg = gson.fromJson(returnJson, ReturnMsg.class);
    	
    	chatMsg.setMsg(returnMsg.getText());
    	chatMsg.setType(Type.RECEIVE);
    	chatMsg.setDate(new Date());
    	
    	return chatMsg;
    }
    
    /**
     * 向图灵机器人询问，并返回询问结果
     *
     * @param info 你所想问的问题
     * @return 机器人回答的结果
     */
    public static String doGet(String info) {

        String url = setParams(info);

        InputStream is = null;
        BufferedReader reader = null;
        StringBuffer sb = null;

        try {
        	
        	URL urlNet = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlNet.openConnection();
            conn.setReadTimeout(5 * 1000);
            conn.setConnectTimeout(5 * 1000);
            conn.setRequestMethod("GET");

            is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is));

            String line = "";
            sb = new StringBuffer();

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    /**
     * 设置链接地址
     * @param info
     * @return
     */
    private static String setParams(String info) {
        String url = null;

        try {
        	
            url = URL + "?key=" + API_KEY + "&info=" + URLEncoder.encode(info, "utf-8");
            
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return url;
    }
}
