package com.myproject.repaircar;

import android.app.Application;

/**
 * Created by Semicolon07 on 1/3/2017 AD.
 */

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Contextor.getInstance().init(getApplicationContext());
    }
}
