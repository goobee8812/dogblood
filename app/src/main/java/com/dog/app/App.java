package com.dog.app;

import android.app.Activity;
import android.app.Application;

import com.dog.BuildConfig;
import com.dog.dagger.componet.AppComponent;
import com.dog.dagger.componet.DaggerAppComponent;
import com.dog.dagger.module.AppModule;
import com.dog.log.LogUtil;
import com.dog.util.Utils;

import java.util.HashSet;
import java.util.Set;

public class App extends Application {

    private static App mApp;
    private static AppComponent mAppComponent;
    private Set<Activity> mAllActivities;

    @Override
    public void onCreate() {
        super.onCreate();
        boolean isApplicationRepeat = Utils.isApplicationRepeat(this);
        if (isApplicationRepeat) {
            return;
        }
        mApp = this;

        //初始化Logger的TAG
        LogUtil.init(BuildConfig.DEBUG);

    }

    public static synchronized App getInstance() {
        return mApp;
    }

    public static AppComponent getAppComponent() {
        if (mAppComponent == null) {
            mAppComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule())
                    .build();
        }
        return mAppComponent;
    }

    public void addActivity(Activity act) {
        if (mAllActivities == null) {
            mAllActivities = new HashSet<>();
        }
        mAllActivities.add(act);
    }

    public void removeActivity(Activity act) {
        if (mAllActivities != null) {
            mAllActivities.remove(act);
        }
    }

    public void exitApp() {
        if (mAllActivities != null) {
            synchronized (mAllActivities) {
                for (Activity act : mAllActivities) {
                    act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

}
