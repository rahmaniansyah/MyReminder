package com.example.rahmaniansyahdp.myreminder;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

/**
 * Created by Rahmaniansyah DP on 01/11/2016.
 */

public class AlarmReceiver extends BroadcastReceiver {
    MediaPlayer player ;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm aktif!", Toast.LENGTH_LONG).show();
        player = MediaPlayer.create(context, R.raw.alarm);
        player.start();
    }
}
