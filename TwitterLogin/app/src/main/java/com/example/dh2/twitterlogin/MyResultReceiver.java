package com.example.dh2.twitterlogin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.twitter.sdk.android.tweetcomposer.TweetUploadService;

/**
 * Created by dh2 on 26/06/17.
 */

public class MyResultReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (TweetUploadService.UPLOAD_SUCCESS.equals(intent.getAction())) {
            // success
            Toast.makeText(context, "Tweet Exitoso", Toast.LENGTH_SHORT).show();
        } else {
            // failure
            Toast.makeText(context, "Tweet Fallido", Toast.LENGTH_SHORT).show();
        }
    }
}
