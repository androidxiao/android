package com.duomizhibo.phonelive.utils;

import android.util.Log;


/**
 * 日志打印管理类
 * Created by Adam_Lee on 2015/9/6.
 */
public class LogUtil {

    private static final String TAG = "ez";

    private LogUtil(){}

    public static void debug(String msg) {
        if(TLog.DEBUG)
            Log.d(TAG, msg);
    }

    public static void info(String msg) {
        if(TLog.DEBUG)
            Log.i(TAG, msg);
    }

    public static void showLimitLog(String str) {
        if (TLog.DEBUG) {
            if(str.length() > 4000) {
                for(int i=0;i<str.length();i+=4000){
                    if(i+4000<str.length())
                        LogUtil.debug("超出长度限制---->"+str.substring(i, i+4000));
                    else
                        LogUtil.debug("超出长度限制---->"+str.substring(i, str.length()));
                }
            } else
                LogUtil.debug("超出长度限制----->"+str);
        }

    }

}
