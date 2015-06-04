package com.zhi.app.test;

import com.zhi.app.utils.HttpUtils;

import android.test.AndroidTestCase;
import android.util.Log;

/**
 * Created by lenovo on 2015/5/23 0023.
 */
public class TestHttpUtils extends AndroidTestCase {

    public void testSendInfo() {
        String info1 = HttpUtils.doGet("你好");
        Log.i("TEST", info1);
    }
}
