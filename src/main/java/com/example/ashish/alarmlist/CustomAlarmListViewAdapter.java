package com.example.ashish.alarmlist;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.ashish.alarmlist.ListItemAlarm;
import com.example.ashish.alarmlist.ViewHolder;

import java.util.List;

/**
 * Created by Ashish on 18-10-2015.
 */
public class CustomAlarmListViewAdapter extends ArrayAdapter<ListItemAlarm> {

    Context context;

    public CustomAlarmListViewAdapter(Context context, int resourceId, List<ListItemAlarm> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        ListItemAlarm listItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_view_with_toggle, null);
            holder = new ViewHolder();
            holder.AlarmText = (TextView) convertView.findViewById(R.id.textViewListItem);
           // holder.Delete = (Button)convertView.findViewById(R.id.DeleteAlarm);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.AlarmText.setText(listItem.getAlarmName());
        holder.AlarmName = listItem.getAlarmName();
        holder.RequestId = listItem.getRequestId();

        if(listItem.getState() == 1) {
            holder.AlarmText.setTextColor(Color.GREEN);
        }
        else{
            holder.AlarmText.setTextColor(Color.RED);
        }
        //holder.Delete.setTag(holder);
/*
        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewHolder deleted = (ViewHolder)v.getTag();
                Log.e("Dipak", "Delete"+deleted.RequestId+"fdsj"+deleted.AlarmName );

               // adapter.remove(itemToRemove);
            }
        });
*/

        return convertView;
    }

}
