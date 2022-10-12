package com.polozaur.testing.alarms;
public class NewTaskActivity extends android.app.Activity {
    android.widget.DatePicker datePicker;

    android.widget.TimePicker timePicker;

    android.widget.Button setTaskBtn;

    android.app.PendingIntent pendingIntent;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        setContentView(R.layout.newtask_layout);
        this.datePicker = ((android.widget.DatePicker) (findViewById(R.id.datePicker)));
        this.timePicker = ((android.widget.TimePicker) (findViewById(R.id.timePicker)));
        this.setTaskBtn = ((android.widget.Button) (findViewById(R.id.setTaskBtn)));
        this.setTaskBtn.setOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View v) {
                android.content.Intent wakeUpIntent = new android.content.Intent(NewTaskActivity.this, com.polozaur.testing.alarms.TaskService.class);
                pendingIntent = android.app.PendingIntent.getService(NewTaskActivity.this, 0, wakeUpIntent, 0);
                android.app.AlarmManager alarmManager = ((android.app.AlarmManager) (getSystemService(android.content.Context.ALARM_SERVICE)));
                calendar.setTimeInMillis(java.lang.System.currentTimeMillis());
                calendar.clear();
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getHour(), timePicker.getCurrentMinute());
                alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                android.widget.Toast.makeText(NewTaskActivity.this, (((((((("Year: " + datePicker.getYear()) + "\nMonth: ") + datePicker.getMonth()) + "\nDay: ") + datePicker.getDayOfMonth()) + "\nHour: ") + timePicker.getHour()) + "\nMinute: ") + timePicker.getCurrentMinute(), android.widget.Toast.LENGTH_LONG).show();
            }
        });
    }
}