package com.example.nz.timepicker;
import android.support.v7.app.AppCompatActivity;
public class MainActivity extends android.support.v7.app.AppCompatActivity {
    android.widget.TimePicker timepicker;

    android.widget.Button button;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        __SmPLUnsupported__(0).onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timepicker = ((android.widget.TimePicker) (findViewById(R.id.timePicker)));
        button = ((android.widget.Button) (findViewById(R.id.btn)));
        timepicker.setIs24HourView(true);
        timepicker.getCurrentHour();
        timepicker.getMinute();
        button.setOnClickListener(__SmPLUnsupported__(1));
    }
}