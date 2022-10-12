package com.greenhouse.widget;
import com.greenhouse.R;
import com.greenhouse.ui.Timer;
public class CustomDatePicker implements android.widget.DatePicker.OnDateChangedListener , android.widget.TimePicker.OnTimeChangedListener {
    private android.widget.DatePicker datePicker;

    private android.widget.TimePicker timePicker;

    private android.app.AlertDialog ad;

    private java.lang.String start_time = "0";

    public static java.lang.String Init_Start_time;

    public static java.lang.String ZZ = "0";

    private android.app.Activity activity;

    public static int Year;

    public static int Month;

    public static int Day;

    public static int Hour;

    // public static String Year="0",Month="0",Day="0",Hour="0",Minute="0";
    public static int Minute;

    public CustomDatePicker(android.app.Activity activity) {
        this.activity = activity;
    }

    public void init(android.widget.DatePicker datePicker, android.widget.TimePicker timePicker) {
        datePicker.init(calendar.get(java.util.Calendar.YEAR), calendar.get(java.util.Calendar.MONDAY), calendar.get(java.util.Calendar.DAY_OF_MONTH), this);
        datePicker.setCalendarViewShown(false);
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(calendar.get(java.util.Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(calendar.get(java.util.Calendar.MINUTE));
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getMinute());
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd  HH:mm");
        com.greenhouse.widget.CustomDatePicker.Init_Start_time = sdf.format(calendar.getTime());
        android.util.Log.i("init start_time", "init start time" + com.greenhouse.widget.CustomDatePicker.Init_Start_time);
    }

    public android.app.AlertDialog DatePickDialog(final android.widget.TextView inputData) {
        android.widget.LinearLayout TimeLayout = ((android.widget.LinearLayout) (activity.getLayoutInflater().inflate(R.layout.date_picker, null)));
        datePicker = ((android.widget.DatePicker) (TimeLayout.findViewById(R.id.datepicker)));
        timePicker = ((android.widget.TimePicker) (TimeLayout.findViewById(R.id.timepicker)));
        init(datePicker, timePicker);
        timePicker.setOnTimeChangedListener(this);
        ad = new android.app.AlertDialog.Builder(activity).setTitle(" :").setView(TimeLayout).setCancelable(false).setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
            public void onClick(android.content.DialogInterface dialog, int which) {
                inputData.setText(start_time);
                com.greenhouse.ui.Timer.startFlag = true;
            }
        }).setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
            public void onClick(android.content.DialogInterface dialog, int which) {
                inputData.setText("请设置时间");
            }
        }).show();
        onDateChanged(null, 0, 0, 0);
        return ad;
    }

    public void onTimeChanged(android.widget.TimePicker view, int hourOfDay, int minute) {
        onDateChanged(null, 0, 0, 0);
    }

    @java.lang.Override
    public void onDateChanged(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // TODO Auto-generated method stub
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute());
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd  HH:mm");
        start_time = sdf.format(calendar.getTime());
        int a = datePicker.getYear();
        int b = datePicker.getMonth();
        int c = datePicker.getDayOfMonth();
        int d = timePicker.getCurrentHour();
        int e = timePicker.getCurrentMinute();
        com.greenhouse.widget.CustomDatePicker.Year = a;
        com.greenhouse.widget.CustomDatePicker.Month = b + 1;
        com.greenhouse.widget.CustomDatePicker.Day = c;
        com.greenhouse.widget.CustomDatePicker.Hour = d;
        com.greenhouse.widget.CustomDatePicker.Minute = e;
        com.greenhouse.widget.CustomDatePicker.ZZ = start_time;
        ad.setTitle(start_time);
    }
}