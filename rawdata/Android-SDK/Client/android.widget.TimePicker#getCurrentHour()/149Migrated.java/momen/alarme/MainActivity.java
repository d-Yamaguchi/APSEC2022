package momen.alarme;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
public class MainActivity extends android.support.v7.app.AppCompatActivity {
    android.widget.TimePicker timePicker;

    android.app.AlarmManager alarmManager;

    java.util.Calendar calendar1;

    java.util.Calendar calendar2;

    android.content.Intent intent;

    android.app.PendingIntent pendingIntent1;

    android.app.PendingIntent pendingIntent2;

    android.widget.Spinner spinner;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarmManager = ((android.app.AlarmManager) (getSystemService(momen.alarme.ALARM_SERVICE)));
        timePicker = ((android.widget.TimePicker) (findViewById(R.id.timePicker)));
        spinner = ((android.widget.Spinner) (findViewById(R.id.spinner)));
        calendar1 = java.util.Calendar.getInstance();
        calendar2 = java.util.Calendar.getInstance();
        android.app.NotificationManager notificationManager = ((android.app.NotificationManager) (getSystemService(momen.alarme.NOTIFICATION_SERVICE)));
        notificationManager.cancel(0);
    }

    public void a_on(android.view.View view) {
        int getHour = timePicker.getCurrentHour();
        int getMinute = timePicker.getCurrentMinute();
        if (java.lang.String.valueOf(spinner.getSelectedItem()).matches("التنبيه الاول")) {
            calendar1.set(java.util.Calendar.HOUR_OF_DAY, timePicker.getHour());
            calendar1.set(java.util.Calendar.MINUTE, timePicker.getCurrentMinute());
            calendar1.set(java.util.Calendar.SECOND, 0);
            android.widget.Toast.makeText(this, java.lang.String.valueOf(spinner.getSelectedItem()), android.widget.Toast.LENGTH_SHORT).show();
            intent = new android.content.Intent(this, momen.alarme.Alarm_Receiver.class);
            pendingIntent1 = android.app.PendingIntent.getBroadcast(this, 0, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), pendingIntent1);
        } else if (java.lang.String.valueOf(spinner.getSelectedItem()).matches("التنبيه الثانى")) {
            calendar2.set(java.util.Calendar.HOUR_OF_DAY, timePicker.getHour());
            calendar2.set(java.util.Calendar.MINUTE, timePicker.getCurrentMinute());
            calendar2.set(java.util.Calendar.SECOND, 0);
            android.widget.Toast.makeText(this, java.lang.String.valueOf(spinner.getSelectedItem()), android.widget.Toast.LENGTH_SHORT).show();
            intent = new android.content.Intent(this, momen.alarme.Alarm_Receiver.class);
            pendingIntent2 = android.app.PendingIntent.getBroadcast(this, 1, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), pendingIntent2);
        }
    }

    public void a_off(android.view.View view) {
        alarmManager.cancel(pendingIntent1);
        alarmManager.cancel(pendingIntent2);
    }
}