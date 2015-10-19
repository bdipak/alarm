package com.example.ashish.alarmlist;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


public class AlarmReciever extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        int alarmid = intent.getIntExtra("AlarmId", 0);
        MainActivity.getInstance().updateAlarmTriggered(alarmid);
        long ringtime = 5000;
        Log.d("Dipak", "Alarm Received Id:"+alarmid);
        Toast.makeText(context, context.getString(R.string.Alarm_Msg), Toast.LENGTH_LONG).show();
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        final Ringtone r = RingtoneManager.getRingtone(context, notification);
        r.play();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                r.stop();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, ringtime);
    }
}