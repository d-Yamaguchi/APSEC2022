package example.codeclan.com.alfor3;
import android.support.v7.app.AppCompatActivity;
public class Alarm extends android.support.v7.app.AppCompatActivity {
    android.app.AlarmManager alarmManager;

    android.widget.TextView alarmReason;

    android.app.PendingIntent pendingIntent;

    java.util.Calendar calendar;

    android.widget.TimePicker timePicker;

    android.widget.Switch repeat;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        // Initialize everything on the page.
        timePicker = ((android.widget.TimePicker) (findViewById(R.id.timePicker)));
        alarmManager = ((android.app.AlarmManager) (getSystemService(example.codeclan.com.alfor3.ALARM_SERVICE)));
        alarmReason = ((android.widget.TextView) (findViewById(R.id.alarmReason)));
        android.widget.Button setAlarm = ((android.widget.Button) (findViewById(R.id.setAlarm)));
        calendar = java.util.Calendar.getInstance();
        repeat = ((android.widget.Switch) (findViewById(R.id.repeatSwitch)));
        repeat.setChecked(false);
        final android.content.Intent sender = new android.content.Intent(this, example.codeclan.com.alfor3.AlarmReceiver.class);
        java.lang.System.out.println(sender);
        repeat.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
            @java.lang.Override
            public void onCheckedChanged(android.widget.CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    android.widget.Toast.makeText(Alarm.this, "This alarm will repeat daily", android.widget.Toast.LENGTH_SHORT).show();
                } else {
                    android.widget.Toast.makeText(Alarm.this, "This one day only alarm", android.widget.Toast.LENGTH_SHORT).show();
                }
            }
        });
        setAlarm.setOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View v) {
                calendar.set(java.util.Calendar.HOUR_OF_DAY, timePicker.getHour());
                calendar.set(java.util.Calendar.MINUTE, timePicker.getCurrentMinute());
                java.lang.System.out.println(timePicker.getHour());
                java.lang.System.out.println(timePicker.getCurrentMinute());
                int hour = timePicker.getCurrentHour();
                int min = timePicker.getCurrentMinute();
                java.lang.String hour_string = java.lang.String.valueOf(hour);
                java.lang.String minute_string = java.lang.String.valueOf(min);
                if (min < 10) {
                    minute_string = "0" + java.lang.String.valueOf(min);
                }
                android.widget.Toast.makeText(Alarm.this, (((("You have set alarm reminder " + alarmReason.getText().toString()) + " ") + hour_string) + ":") + minute_string, android.widget.Toast.LENGTH_LONG).show();
                // its probable that the 0 below will need to be a variable that keeps changing if you want to add multiple alarms.
                pendingIntent = android.app.PendingIntent.getBroadcast(Alarm.this, 0, sender, android.app.PendingIntent.FLAG_UPDATE_CURRENT);
                if (repeat.isChecked()) {
                    alarmManager.setRepeating(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), android.app.AlarmManager.INTERVAL_DAY, pendingIntent);
                } else {
                    alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
            }
        });
    }
}