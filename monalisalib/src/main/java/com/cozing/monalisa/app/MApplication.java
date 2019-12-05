package com.cozing.monalisa.app;

import android.app.Activity;
import android.app.Application;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * desc: 扩展Application
 *
 * Created by wanghuquan on 2018/2/11.
 */

public class MApplication extends Application{

    private static MApplication mApplication;

    private Set<Activity> mActivitys = Collections.newSetFromMap(new WeakHashMap<Activity, Boolean>());
    /**
     * 禁止子类重写该方法，改成重写afterApplicationCreate()
     */
    @Override
    public final void onCreate() {
        super.onCreate();

        mApplication = this;

        afterApplicationCreate();
    }

    /**
     * 获取运行中的Application实例
     */
    public static MApplication getInstance(){
        return mApplication;
    }

    /**
     * 在Activity创建时候将Activity实例存起来
     *
     * @param activity 创建的Activity实例
     */
    protected void addActivity(Activity activity){
        synchronized (this){
            mActivitys.add(activity);
        }
    }

    /**
     * 将Activity被摧毁时将Activity实例销毁，一般建议写在Activity的onDestroy();
     *
     * @param activity 将要被销毁的Activity实例
     */
    protected void removeActivity(Activity activity){
        synchronized (this){
            mActivitys.remove(activity);
        }
    }

    /**
     * 子类在该方法中处理初始化，代替Application中的onCreate();
     */
    protected void afterApplicationCreate(){}

    /**
     * 杀死APP
     */
    public void killApp(){

        for(Activity activity : mActivitys){
            activity.finish();
        }
        mActivitys.clear();

        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
