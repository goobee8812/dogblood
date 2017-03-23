package com.dog.util;

import android.app.ActivityManager;
import android.content.Context;

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

}
