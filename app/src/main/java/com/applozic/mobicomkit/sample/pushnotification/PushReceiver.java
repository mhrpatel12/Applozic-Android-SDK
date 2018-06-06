package com.applozic.mobicomkit.sample.pushnotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.applozic.mobicomkit.api.notification.MobiComPushReceiver;
import com.applozic.mobicommons.json.GsonUtils;

import java.util.HashMap;
import java.util.Map;

public class PushReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String notificationTitle = "MyApp";
        String notificationText = "Test notification";

        // Attempt to extract the "message" property from the payload: {"message":"Hello World!"}
        if (intent.getStringExtra("message") != null) {
            notificationText = (String) intent.getExtras().get("message");
        }
        Map<String, String> map = new HashMap<String, String>();
        map = (Map<String, String>) GsonUtils.getObjectFromJson(notificationText, map.getClass());
        MobiComPushReceiver.processMessageAsync(context, map); //pass it to Applozic or handle notification by your own.
    }
}