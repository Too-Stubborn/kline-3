package com.huobi.klinelib.utils;

import android.util.Log;

import com.huobi.klinelib.BuildConfig;

/*************************************************************************
 * Description   :
 *
 * @PackageName  : com.huobi.klinelib.utils
 * @FileName     : LogUtil.java
 * @Author       : chao
 * @Date         : 2019/1/10
 * @Email        : icechliu@gmail.com
 * @version      : V1
 *************************************************************************/
public class LogUtil {
    private final static String TAG = "CHAO=>";

    public static void e(Object o) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, String.valueOf(o));
        }
    }

}
