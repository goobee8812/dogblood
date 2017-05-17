package com.dog.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import com.dog.log.LogUtil;

import java.util.Iterator;
import java.util.List;

/**
 * Created by cuiyue on 2017/1/20.
 */

public class Utils {


    /**
     * 判断程序是否重复启动
     */
    public static boolean isApplicationRepeat(Context applicationContext) {
        int pid = android.os.Process.myPid();
        String processName = null;
        ActivityManager am = (ActivityManager) applicationContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> l = am.getRunningAppProcesses();
        Iterator<ActivityManager.RunningAppProcessInfo> i = l.iterator();
        // PackageManager pm = applicationContext.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = i.next();
            try {
                if (info.pid == pid) {
                    processName = info.processName;
                }
            } catch (Exception e) {
            }
        }
        return processName == null || !processName.equalsIgnoreCase(applicationContext.getPackageName());
    }


    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 得到渠道ID
     */
    public static String getChannelID(Context context) {
        String channelName = getChannelName(context);
        String channelID = channelName2channelID(channelName);

        Log.d("abc","channelName-->"+channelName);

        return channelID;
    }

    /**
     * 得到渠道名字
     */
    public static String getChannelName(Context context) {
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appInfo.metaData.getString("UMENG_CHANNEL");
    }

    /**
     * 渠道名字转成ID
     */
    public static String channelName2channelID(String channelName) {

        String channelID = "1";
        switch (channelName) {
            case "ceshi":
                channelID = "1";
                break;
            case "server":
                channelID = "2000";
                break;
            case "wandoujia":
                channelID = "2001";
                break;
            case "qqTencent":
                channelID = "2002";
                break;
            case "zs91zhushou":
                channelID = "2003";
                break;
            case "huawei":
                channelID = "2004";
                break;
            case "xiaomi":
                channelID = "2005";
                break;
            case "baidu":
                channelID = "2006";
                break;
            case "anzhi":
                channelID = "2007";
                break;
            case "oppo":
                channelID = "2008";
                break;
            case "appchina":
                channelID = "209";
                break;
            case "lenovo":
                channelID = "2010";
                break;
            case "meizu":
                channelID = "2011";
                break;
            case "baidusem":
                channelID = "2012";
                break;
            case "UCWeb":
                channelID = "2013";
                break;
            case "dev360cn":
                channelID = "2014";
                break;
            case "vivo":
                channelID = "2015";
                break;
        }
        return channelID;
    }

}
