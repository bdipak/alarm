package com.example.ashish.alarmlist;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TimePicker;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends Activity {
    private static MainActivity ins;
    private TabHost myTabHost;
    List<ListItemAlarm> listItemsAlarm;// = new ArrayList<ListItemAlarm>();
    ArrayAdapter<ListItemAlarm> adapter;
    ListView listViewHandle;
    AlarmManager alarmManager;
    Intent myIntent;
    int alarmid = 0;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ins = this;
        myTabHost =(TabHost) findViewById(R.id.tabHost);
        myTabHost.setup();
        listItemsAlarm = new ArrayList<ListItemAlarm>();
        myTabHost.addTab(myTabHost.newTabSpec("tab1").setIndicator("Create Alarm").setContent(R.id.tab1));
        myTabHost.addTab(myTabHost.newTabSpec("tab1").setIndicator("Show Alarm").setContent(R.id.tab2));
        listViewHandle = (ListView)findViewById(R.id.listView);
        adapter = new CustomAlarmListViewAdapter(this, R.layout.list_view_with_toggle, listItemsAlarm);
        listViewHandle.setAdapter(adapter);
        alarmManager = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
        myIntent = new Intent(this, AlarmReciever.class);
        mContext = this;

        listViewHandle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                //AlarmManager alarmManager = (AlarmManager) getBaseContext().getSystemService(getBaseContext().ALARM_SERVICE);
                ListItemAlarm itemToRemove = adapter.getItem(position);
                Toast.makeText(getBaseContext(), itemToRemove.getAlarmName()+" Deleted", Toast.LENGTH_LONG).show();
                Log.d("Dipak", "Event Received"+itemToRemove.getAlarmName()+itemToRemove.getRequestId());
                //Intent myIntent = new Intent(getBaseContext(), AlarmReciever.class);
                //PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, itemToRemove.getRequestId(), myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.cancel(itemToRemove.getPendingIntent());
                adapter.remove(itemToRemove);

            }
        });
    }

    public static MainActivity getInstance(){
        return ins;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void SetAlarm(View view) {
        TimePicker timePicker1= (TimePicker) findViewById(R.id.timePicker);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, timePicker1.getCurrentHour());
        calendar.set(Calendar.MINUTE, timePicker1.getCurrentMinute());
        calendar.set(Calendar.SECOND, 0);
        //Intent myIntent = new Intent(getBaseContext(), AlarmReciever.class);
        myIntent.putExtra("AlarmId", ++alarmid);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, alarmid, myIntent, PendingIntent.FLAG_UPDATE_CURRENT );
        //AlarmManager alarmManager = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Toast.makeText(this, this.getString(R.string.Alarm_Set), Toast.LENGTH_LONG).show();
        ListItemAlarm addentry = new ListItemAlarm(alarmid, "Time-"+timePicker1.getCurrentHour()+":"+timePicker1.getCurrentMinute()+":00", pendingIntent, 1);
        adapter.add(addentry);


    }


    public void updateAlarmTriggered(int alarmid)
    {
        for (ListItemAlarm item : listItemsAlarm)
        {
            if(item.getRequestId() == alarmid)
            {
                if (item.getState()  == 1) {
                    item.setState(0);
                    Log.e("Dipak", "Alarm id:"+alarmid+" state changed to 0");
                    adapter.notifyDataSetChanged();
                    break;
                }
            }
        }
    }

    /*
    public void DeleteAlarm(View view) {
        ViewHolder deleteitem = (ViewHolder) view.getTag();
        Log.e("Dipak", "DeleteAlarm"+deleteitem.RequestId+"fdsj"+deleteitem.AlarmName );
        Toast.makeText(this, deleteitem.AlarmName+"Deleted", Toast.LENGTH_LONG).show();

        AlarmManager alarmManager = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
        ListItemAlarm itemToRemove = new ListItemAlarm(deleteitem.RequestId, deleteitem.AlarmName);
        Log.e("Dipak", "DeleteItem"+itemToRemove.getAlarmName()+"  "+itemToRemove.getRequestId() );

        Intent myIntent = new Intent(this, AlarmReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, itemToRemove.getRequestId(), myIntent, 0);
        alarmManager.cancel(pendingIntent);
        listItemsAlarm.remove(itemToRemove);
        //adapter.remove(itemToRemove);
        adapter = new CustomAlarmListViewAdapter(this, R.layout.list_view_with_toggle, listItemsAlarm);
        listViewHandle.setAdapter(adapter);
        int pos  = adapter.getPosition(itemToRemove);
        Toast.makeText(this, pos+"Deleted", Toast.LENGTH_LONG).show();
    }
*/
}
