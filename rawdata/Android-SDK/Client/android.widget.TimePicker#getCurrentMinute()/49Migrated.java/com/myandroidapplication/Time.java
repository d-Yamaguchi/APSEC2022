package com.myandroidapplication;
public class Time extends android.app.Activity implements android.view.View.OnClickListener {
    private android.widget.Chronometer chronometer;

    private android.widget.TimePicker timePicker;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        chronometer = ((android.widget.Chronometer) (findViewById(R.id.chronometer)));
        ((android.widget.Button) (findViewById(R.id.start_button))).setOnClickListener(this);
        // ((Button) findViewById(R.id.stop_button)).setOnClickListener(this);
        timePicker = ((android.widget.TimePicker) (findViewById(R.id.timePicker1)));
    }

    @java.lang.Override
    public void onClick(android.view.View v) {
        // switch (v.getId()) {
        // case R.id.start_button:
        long difference = 0;
        int pickedTime = timePicker.getCurrentHour();
        int currentTime = new java.sql.Time(java.lang.System.currentTimeMillis()).getHours();
        if (pickedTime < currentTime) {
            difference = ((long) (currentTime - pickedTime)) * 3600000;
            pickedTime = timePicker.getMinute();
            currentTime = new java.sql.Time(java.lang.System.currentTimeMillis()).getMinutes();
            difference += ((long) (currentTime - pickedTime)) * 60000;
        } else if (pickedTime == currentTime) {
            pickedTime = timePicker.getMinute();
            currentTime = new java.sql.Time(java.lang.System.currentTimeMillis()).getMinutes();
            if (pickedTime <= currentTime) {
                difference = ((long) (currentTime - pickedTime)) * 60000;
            } else {
                difference = ((long) ((24 * 3600000) - ((pickedTime - currentTime) * 60000)));
            }
        } else {
            difference = ((long) (((24 - pickedTime) * 3600000) + (currentTime * 3600000)));
            pickedTime = timePicker.getMinute();
            currentTime = new java.sql.Time(java.lang.System.currentTimeMillis()).getMinutes();
            difference += ((long) (currentTime - pickedTime)) * 60000;
        }
        difference += new java.sql.Time(java.lang.System.currentTimeMillis()).getSeconds();
        chronometer.setBase(android.os.SystemClock.elapsedRealtime() - difference);
        chronometer.start();
        // break;
        // case R.id.stop_button:
        // chronometer.stop();
        // break;
    }
}