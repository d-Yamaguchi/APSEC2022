package com.example.alarmapp;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends androidx.appcompat.app.AppCompatActivity {
    android.widget.TextView timetv;

    android.widget.TimePicker timePicker;

    android.widget.Button alarm;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timePicker = findViewById(R.id.timepk);
        alarm = findViewById(R.id.btnAlarm);
        timetv = findViewById(R.id.timetv);
        alarm.setOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View v) {
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                calendar.set(timePicker.getCurrentHour(), timePicker.getMinute(), 0);
                android.widget.Toast.makeText(MainActivity.this, (("Alarm set at :" + timePicker.getCurrentHour()) + ":") + timePicker.getMinute(), android.widget.Toast.LENGTH_SHORT).show();
                android.content.Intent alarmIntent = new android.content.Intent(MainActivity.this, com.example.alarmapp.MyAlarmBroadcastReceiver.class);
                android.app.PendingIntent pendingIntent = android.app.PendingIntent.getBroadcast(MainActivity.this, 8092, alarmIntent, android.app.PendingIntent.FLAG_ONE_SHOT);
                android.app.AlarmManager alarmManager = ((android.app.AlarmManager) (getSystemService(com.example.alarmapp.ALARM_SERVICE)));
                alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                java.lang.String ct = (("Alarm set at :" + timePicker.getCurrentHour()) + ":") + timePicker.getMinute();
                timetv.setText(ct);
            }
        });
    }
}