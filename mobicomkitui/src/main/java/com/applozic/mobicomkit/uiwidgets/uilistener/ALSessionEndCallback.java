package com.applozic.mobicomkit.uiwidgets.uilistener;

import android.app.Activity;
import android.content.Context;

import com.applozic.mobicommons.people.channel.Channel;

/**
 * Created by mihir on 31/01/18.
 */

public interface ALSessionEndCallback {
    void sessionEnded(Activity activity, Channel channel);
}