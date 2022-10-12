package com.shirco.calendar;
import android.support.v7.app.AppCompatActivity;
public class AddEventActivity extends android.support.v7.app.AppCompatActivity {
    com.shirco.calendar.Event event;

    android.widget.EditText detailsEditText;

    android.widget.DatePicker datePicker;

    android.widget.TimePicker timePicker;

    android.widget.Button timeButton;

    android.widget.Button dateButton;

    android.widget.Button openTimeButton;

    android.widget.Button openDateButton;

    android.widget.Button addButton;

    android.widget.LinearLayout dateView;

    android.widget.LinearLayout timeView;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        __SmPLUnsupported__(0).onCreate(savedInstanceState);
        // Get Views
        setContentView(R.layout.activity_add_event);
        detailsEditText = ((android.widget.EditText) (findViewById(R.id.detailsEditView)));
        datePicker = ((android.widget.DatePicker) (findViewById(R.id.datePicker)));
        timePicker = ((android.widget.TimePicker) (findViewById(R.id.timePicker)));
        dateView = ((android.widget.LinearLayout) (findViewById(R.id.dateView)));
        timeView = ((android.widget.LinearLayout) (findViewById(R.id.timeView)));
        addButton = ((android.widget.Button) (findViewById(R.id.registerButton)));
        openDateButton = ((android.widget.Button) (findViewById(R.id.openDateButton)));
        openTimeButton = ((android.widget.Button) (findViewById(R.id.openTimeButton)));
        dateButton = ((android.widget.Button) (findViewById(R.id.dateButton)));
        timeButton = ((android.widget.Button) (findViewById(R.id.timeButton)));
        // Set Min Date possible to today
        datePicker.setMinDate(new java.util.Date().getTime());
        int id = -1;
        // Check if event must be created or edited
        if ((getIntent() != null) && (getIntent().getExtras() != null)) {
            id = getIntent().getExtras().getInt("id", -1);
        }
        if (id == (-1)) {
            // Create new Event
            java.lang.String details = detailsEditText.getText().toString();
            tmp.setMonth(datePicker.getMonth());
            tmp.setYear(datePicker.getYear());
            tmp.setDate(datePicker.getDayOfMonth());
            tmp.setMinutes(timePicker.getCurrentMinute());
            java.util.Date tmp = new java.util.Date();
            tmp.setHours(timePicker.getHour());
            java.lang.String date = (((((((datePicker.getYear() + "/") + (datePicker.getMonth() + 1)) + "/") + datePicker.getDayOfMonth()) + " ") + timePicker.getHour()) + ":") + timePicker.getCurrentMinute();
            event = new com.shirco.calendar.Event(date, details, tmp);
        } else {
            // Get Existing Event
            event = com.shirco.calendar.Singleton.getInstance().getEvents().get(id);
            timePicker.setCurrentMinute(event.getDate().getMinutes());
            timePicker.setCurrentHour(event.getDate().getHours());
            datePicker.updateDate(event.getDate().getYear(), event.getDate().getMonth(), event.getDate().getDate());
        }
        // Set base text of details, time and date based on Event info
        detailsEditText.setText(event.getDescription());
        openTimeButton.setText((timePicker.getCurrentHour() + ":") + timePicker.getCurrentMinute());
        openDateButton.setText((((datePicker.getYear() + "/") + (datePicker.getMonth() + 1)) + "/") + datePicker.getDayOfMonth());
        // Click Listeners for buttons
        openDateButton.setOnClickListener(__SmPLUnsupported__(1));
        openTimeButton.setOnClickListener(__SmPLUnsupported__(2));
        timeButton.setOnClickListener(__SmPLUnsupported__(3));
        dateButton.setOnClickListener(__SmPLUnsupported__(4));
        addButton.setOnClickListener(__SmPLUnsupported__(5));
    }
}