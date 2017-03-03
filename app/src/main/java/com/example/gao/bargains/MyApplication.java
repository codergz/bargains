package com.example.gao.bargains;

import android.app.Application;
import android.content.Context;

/**
 * Created by gao on 2017/2/28.
 */

public class MyApplication extends Application {

    private static Context context;

    public void onCreate(){
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}
