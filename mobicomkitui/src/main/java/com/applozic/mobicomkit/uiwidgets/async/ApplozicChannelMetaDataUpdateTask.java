package com.applozic.mobicomkit.uiwidgets.async;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.applozic.mobicomkit.api.MobiComKitConstants;
import com.applozic.mobicomkit.channel.service.ChannelService;
import com.applozic.mobicomkit.feed.GroupInfoUpdate;
import com.applozic.mobicomkit.uiwidgets.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mihir on 27/1/18.
 */
public class ApplozicChannelMetaDataUpdateTask extends AsyncTask<Void, Void, Boolean> {

    Context context;
    Integer channelKey;
    private Map<String, String> metadata = new HashMap<>();
    ChannelMetaDataUpdateListener channelMetaDataUpdateListener;
    ChannelService channelService;
    Exception exception;
    String updateNameResponse;
    String clientGroupId;
    String imageUrl;
    GroupInfoUpdate groupInfoUpdate;

    public ApplozicChannelMetaDataUpdateTask(Context context, Integer channelKey, Map<String, String> metadata, ChannelMetaDataUpdateListener channelMetaDataUpdateListener) {
        this.channelKey = channelKey;
        this.metadata = metadata;
        this.channelMetaDataUpdateListener = channelMetaDataUpdateListener;
        this.context = context;
        this.channelService = ChannelService.getInstance(context);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            if (metadata != null && metadata.size() > 0) {
                if (channelKey != null && channelKey != 0) {
                    groupInfoUpdate = new GroupInfoUpdate(metadata, channelKey);
                } else if (!TextUtils.isEmpty(clientGroupId)) {
                    groupInfoUpdate = new GroupInfoUpdate(metadata, clientGroupId);
                }
                if (groupInfoUpdate != null && !TextUtils.isEmpty(imageUrl)) {
                    groupInfoUpdate.setImageUrl(imageUrl);
                }
                updateNameResponse = channelService.updateChannel(groupInfoUpdate);
                if (!TextUtils.isEmpty(updateNameResponse)) {
                    return MobiComKitConstants.SUCCESS.equals(updateNameResponse);
                }
            } else {
                throw new Exception(context.getString(R.string.applozic_userId_error_info_in_logs));
            }
        } catch (Exception e) {
            e.printStackTrace();
            exception = e;
            return false;
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean resultBoolean) {
        super.onPostExecute(resultBoolean);

        if (resultBoolean && channelMetaDataUpdateListener != null) {
            channelMetaDataUpdateListener.onUpdateSuccess(updateNameResponse, context);
        } else if (!resultBoolean && exception != null && channelMetaDataUpdateListener != null) {
            channelMetaDataUpdateListener.onFailure(updateNameResponse, exception, context);
        }
    }

    public interface ChannelMetaDataUpdateListener {
        void onUpdateSuccess(String response, Context context);

        void onFailure(String response, Exception e, Context context);
    }
}
