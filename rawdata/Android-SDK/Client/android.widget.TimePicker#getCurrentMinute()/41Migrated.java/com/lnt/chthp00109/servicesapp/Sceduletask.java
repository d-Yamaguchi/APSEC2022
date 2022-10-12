package com.lnt.chthp00109.servicesapp;
import android.support.v7.app.AppCompatActivity;
public class Sceduletask extends android.support.v7.app.AppCompatActivity {
    com.lnt.chthp00109.servicesapp.SaveLogFiles logFiles = new com.lnt.chthp00109.servicesapp.SaveLogFiles();

    private static final java.lang.String TAG = com.lnt.chthp00109.servicesapp.Sceduletask.class.getSimpleName();

    android.widget.TimePicker timePicker;

    java.lang.String amorpm = "";

    java.lang.String times = "";

    int TimES;

    android.widget.TextView scdullertime;

    android.widget.Switch disableclock;

    android.app.PendingIntent pi;

    android.content.Intent pendingintent;

    android.app.AlarmManager am;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sceduletask);
        timePicker = findViewById(R.id.TimePicker);
        disableclock = findViewById(R.id.Disableclock);
        am = ((android.app.AlarmManager) (getApplicationContext().getSystemService(com.lnt.chthp00109.servicesapp.ALARM_SERVICE)));
        pendingintent = new android.content.Intent(this, com.lnt.chthp00109.servicesapp.MyAlarm.class);
        scdullertime = findViewById(R.id.scheduletime);
        pi = android.app.PendingIntent.getBroadcast(this, 0, pendingintent, android.app.PendingIntent.FLAG_ONE_SHOT);
        disableclock.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
            @java.lang.Override
            public void onCheckedChanged(android.widget.CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    android.util.Log.d("ScedulerTask", "Desableing the alarm");
                    disablealarm();
                }
            }
        });
    }

    public void SetAlarm(android.view.View view) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            logFiles.Senddata(com.lnt.chthp00109.servicesapp.Sceduletask.TAG + "-->Api level  >= 23!");
            calendar.set(calendar.get(java.util.Calendar.YEAR), calendar.get(java.util.Calendar.MONTH), calendar.get(java.util.Calendar.DAY_OF_MONTH), timePicker.getHour(), timePicker.getMinute(), 0);
            amorpm = (timePicker.getHour() >= 12) ? "PM" : "AM";
            times = (((java.lang.String.valueOf(TimES = (timePicker.getHour() > 12) ? timePicker.getHour() - 12 : timePicker.getHour()) + ":") + java.lang.String.valueOf(timePicker.getMinute() < 10 ? java.lang.String.valueOf("0" + timePicker.getMinute()) : timePicker.getMinute())) + " ") + amorpm;
        } else {
            logFiles.Senddata(com.lnt.chthp00109.servicesapp.Sceduletask.TAG + "-->:API LESS THAN 23!");
            calendar.set(calendar.get(java.util.Calendar.YEAR), calendar.get(java.util.Calendar.MONTH), calendar.get(java.util.Calendar.DAY_OF_MONTH), timePicker.getCurrentHour(), timePicker.getMinute(), 0);
            amorpm = (timePicker.getCurrentHour() >= 12) ? "PM" : "AM";
            times = ((java.lang.String.valueOf(TimES = (timePicker.getCurrentHour() > 12) ? timePicker.getCurrentHour() - 12 : timePicker.getCurrentHour()) + java.lang.String.valueOf(timePicker.getCurrentMinute() < 10 ? java.lang.String.valueOf("0" + timePicker.getCurrentMinute()) : timePicker.getCurrentMinute())) + " ") + amorpm;
        }
        setTask(calendar.getTimeInMillis(), times);
    }

    public void setTask(long time, java.lang.String s) {
        // getting the alarm manager
        // am.setTime(time);
        am.setRepeating(android.app.AlarmManager.RTC, time, android.app.AlarmManager.INTERVAL_DAY, pi);
        logFiles.Senddata(com.lnt.chthp00109.servicesapp.Sceduletask.TAG + "-->Alarmset using alarmmanager!");
        scdullertime.setText(s);
    }

    public void disablealarm() {
        if (am != null) {
            am.cancel(pi);
            scdullertime.setText("--:--");
            logFiles.Senddata(com.lnt.chthp00109.servicesapp.Sceduletask.TAG + "-->canceled alarm!");
            android.util.Log.d("SceduleTask", "Disable successfully");
        } else
            android.widget.Toast.makeText(this, "NO alarm is set", android.widget.Toast.LENGTH_SHORT).show();

    }
}