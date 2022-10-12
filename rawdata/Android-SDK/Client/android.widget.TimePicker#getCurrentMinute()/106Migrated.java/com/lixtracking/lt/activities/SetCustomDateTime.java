package com.lixtracking.lt.activities;
import com.lixtracking.lt.R;
public class SetCustomDateTime extends android.app.Activity implements android.widget.DatePicker.OnDateChangedListener {
    private android.widget.TimePicker timePicker = null;

    private android.widget.DatePicker datePicker = null;

    private android.widget.Button set = null;

    @java.lang.Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_time_dialog);
        android.content.Intent intent = this.getIntent();
        java.lang.String from = intent.getStringExtra("FROM");
        java.lang.String to = intent.getStringExtra("TO");
        timePicker = ((android.widget.TimePicker) (findViewById(R.id.timePicker)));
        timePicker.setIs24HourView(true);
        datePicker = ((android.widget.DatePicker) (findViewById(R.id.datePicker)));
        if (intent.getBooleanExtra("START", false)) {
            java.lang.String y = from.substring(0, 4);
            java.lang.String m = from.substring(5, 7);
            java.lang.String d = from.substring(8, 10);
            java.lang.String h = from.substring(11, 13);
            java.lang.String min = from.substring(14, 16);
            datePicker.init(java.lang.Integer.parseInt(y), java.lang.Integer.parseInt(m) - 1, java.lang.Integer.parseInt(d), this);
            timePicker.setCurrentHour(java.lang.Integer.parseInt(h));
            timePicker.setCurrentMinute(java.lang.Integer.parseInt(min));
        } else if (intent.getBooleanExtra("END", false)) {
            java.lang.String y = to.substring(0, 4);
            java.lang.String m = to.substring(5, 7);
            java.lang.String d = to.substring(8, 10);
            java.lang.String h = to.substring(11, 13);
            java.lang.String min = to.substring(14, 16);
            datePicker.init(java.lang.Integer.parseInt(y), java.lang.Integer.parseInt(m) - 1, java.lang.Integer.parseInt(d), this);
            timePicker.setCurrentHour(java.lang.Integer.parseInt(h));
            timePicker.setCurrentMinute(java.lang.Integer.parseInt(min));
        }
        set = ((android.widget.Button) (findViewById(R.id.button)));
        set.setOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View view) {
                android.content.Intent intent = new android.content.Intent();
                java.lang.String year = java.lang.Integer.toString(datePicker.getYear());
                java.lang.String month = java.lang.Integer.toString(datePicker.getMonth());
                if (datePicker.getMonth() < 10) {
                    month = "0" + java.lang.Integer.toString(datePicker.getMonth() + 1);
                }
                java.lang.String day = java.lang.Integer.toString(datePicker.getDayOfMonth());
                if (datePicker.getDayOfMonth() < 10) {
                    day = "0" + java.lang.Integer.toString(datePicker.getDayOfMonth());
                }
                java.lang.String hour = java.lang.Integer.toString(timePicker.getCurrentHour());
                if (timePicker.getCurrentHour() < 10) {
                    hour = "0" + java.lang.Integer.toString(timePicker.getCurrentHour());
                }
                java.lang.String minute = java.lang.Integer.toString(timePicker.getMinute());
                if () {
                    minute = "0" + java.lang.Integer.toString(timePicker.getMinute());
                }
                java.lang.String second = "00";
                java.lang.String result = (((((((((year + "/") + month) + "/") + day) + " ") + hour) + ":") + minute) + ":") + second;
                intent.putExtra("DATE_TIME", result);
                setResult(android.app.Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    @java.lang.Override
    public void onDateChanged(android.widget.DatePicker datePicker, int i, int i2, int i3) {
    }
}