package com.applozic.mobicomkit.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.applozic.mobicomkit.uiwidgets.uilistener.ALSessionEndCallback;
import com.applozic.mobicommons.people.channel.Channel;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Created by sunil on 21/3/16.
 */
public class ApplozicSampleApplication extends MultiDexApplication implements ALSessionEndCallback {

    @Override
    public void onCreate() {
        super.onCreate();
       Fabric.with(this, new Crashlytics());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void sessionEnded(Activity activity, Channel channel) {
        //YOUR ACTION
    }
}
