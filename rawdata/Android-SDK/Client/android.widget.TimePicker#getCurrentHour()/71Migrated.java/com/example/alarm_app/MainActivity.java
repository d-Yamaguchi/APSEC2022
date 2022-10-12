package com.example.alarm_app;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends androidx.appcompat.app.AppCompatActivity {
    android.widget.TextView timetve;

    android.widget.TimePicker timepicker;

    android.widget.Button alarm;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timepicker = findViewById(R.id.timePicker);
        alarm = findViewById(R.id.button1);
        timetve = findViewById(R.id.timetv);
        alarm.setOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View view) {
                java.util.Calendar cal = java.util.Calendar.getInstance();
                cal.set(timepicker.getHour(), timepicker.getCurrentMinute(), 00);
                android.widget.Toast.makeText(MainActivity.this, (("Alarm set at:" + timepicker.getHour()) + ":") + timepicker.getCurrentMinute(), android.widget.Toast.LENGTH_LONG).show();
                android.content.Intent intent = new android.content.Intent(MainActivity.this, com.example.alarm_app.Mybroadcastreceiver.class);
                android.app.PendingIntent pi = android.app.PendingIntent.getBroadcast(MainActivity.this, 1234, intent, android.app.PendingIntent.FLAG_ONE_SHOT);
                android.app.AlarmManager amp = ((android.app.AlarmManager) (getSystemService(android.content.Context.ALARM_SERVICE)));
                amp.set(android.app.AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
                java.lang.String currentTime = (("Alarm Set: " + timepicker.getHour()) + ":") + timepicker.getCurrentMinute();
                timetve.setText(currentTime);
            }
        });
    }
}