package com.example.ashish.alarmlist;

import android.app.PendingIntent;

/**
 * Created by Ashish on 18-10-2015.
 */


public class ListItemAlarm {
    private int RequestId;
    private String AlarmName;
    private PendingIntent pendingIntent;
    private int state;

    public ListItemAlarm(int RequestId, String AlarmName, PendingIntent pendingIntent, int state) {
        this.RequestId = RequestId;
        this.AlarmName = AlarmName;
        this.pendingIntent = pendingIntent;
        this.state = state;

    }
    public int getRequestId() {
        return RequestId;
    }
    public void setRequestId(int RequestId) {
        this.RequestId = RequestId;
    }
    public String getAlarmName() {
        return AlarmName;
    }
    public void setAlarmName(String AlarmName) {
        this.AlarmName = AlarmName;
    }
    public PendingIntent getPendingIntent(){
        return pendingIntent;
    }
    public void setState(int state){
        this.state = state;
    }
    public int getState(){return state;}


}

