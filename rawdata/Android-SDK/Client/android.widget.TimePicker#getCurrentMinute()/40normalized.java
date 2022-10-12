@java.lang.Override
public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
    android.view.View root = inflater.inflate(R.layout.fragment_time_range_picker_dialog, container, false);
    getDialog().getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
    startTimePicker = ((android.widget.TimePicker) (root.findViewById(R.id.startTimePicker)));
    endTimePicker = ((android.widget.TimePicker) (root.findViewById(R.id.endTimePicker)));
    android.widget.TabHost tabs = ((android.widget.TabHost) (root.findViewById(R.id.tabHost)));
    final android.widget.Button setTimeRange = ((android.widget.Button) (root.findViewById(R.id.set_time)));
    android.widget.Button cancelButton = ((android.widget.Button) (root.findViewById(R.id.cancel)));
    startTimePicker.setIs24HourView(is24HourMode);
    endTimePicker.setIs24HourView(is24HourMode);
    setTimeRange.setOnClickListener(this);
    cancelButton.setOnClickListener(this);
    tabs.findViewById(R.id.tabHost);
    tabs.setup();
    android.widget.TabHost.TabSpec tabpage1 = tabs.newTabSpec("1");
    tabpage1.setContent(R.id.startTimeGroup);
    tabpage1.setIndicator("Pradžios laikas");
    android.widget.TabHost.TabSpec tabpage2 = tabs.newTabSpec("2");
    tabpage2.setContent(R.id.endTimeGroup);
    tabpage2.setIndicator("Pabaigos laikas");
    tabs.addTab(tabpage1);
    tabs.addTab(tabpage2);
    tabs.setOnTabChangedListener(this);
    final java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
    startCalendar = java.util.Calendar.getInstance();
    endCalendar = java.util.Calendar.getInstance();
    int _CVAR2 = 1;
    android.widget.TimePicker _CVAR5 = startTimePicker;
    android.widget.TimePicker _CVAR7 = startTimePicker;
    java.util.Calendar _CVAR0 = startCalendar;
    int _CVAR1 = startYear;
    int _CVAR3 = startMonth - _CVAR2;
    int _CVAR4 = startDay;
    java.lang.Integer _CVAR6 = _CVAR5.getCurrentHour();
    java.lang.Integer _CVAR8 = _CVAR7.getCurrentMinute();
    _CVAR0.set(_CVAR1, _CVAR3, _CVAR4, _CVAR6, _CVAR8);
    startTime = ((android.widget.TextView) (root.findViewById(R.id.stat_time)));
    endTime = ((android.widget.TextView) (root.findViewById(R.id.end_time)));
    dateColor("1");
    // startTime.setText(dateFormat.format(startCalendar.getTime().getTime() - 60000));
    startTime.setText(dateFormat.format(startCalendar.getTime().getTime()));
    android.widget.TimePicker _CVAR9 = startTimePicker;
    android.widget.TimePicker.OnTimeChangedListener _CVAR10 = new android.widget.TimePicker.OnTimeChangedListener() {
        @java.lang.Override
        public void onTimeChanged(android.widget.TimePicker timePicker, int i, int i1) {
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            startCalendar.set(startYear, startMonth - 1, startDay, timePicker.getCurrentHour(), timePicker.getCurrentMinute());
            startTime.setText(dateFormat.format(startCalendar.getTime().getTime()));
            if (startCalendar.getTimeInMillis() > calendar.getTimeInMillis()) {
                setTimeRange.setTextColor(android.graphics.Color.parseColor("#2e9df2"));
                validTime = true;
            } else {
                setTimeRange.setTextColor(android.graphics.Color.RED);
                validTime = false;
            }
        }
    };
    _CVAR9.setOnTimeChangedListener(_CVAR10);
    int _CVAR13 = 1;
    android.widget.TimePicker _CVAR16 = endTimePicker;
    android.widget.TimePicker _CVAR18 = endTimePicker;
    java.util.Calendar _CVAR11 = endCalendar;
    int _CVAR12 = endYear;
    int _CVAR14 = endMonth - _CVAR13;
    int _CVAR15 = endDay;
    java.lang.Integer _CVAR17 = _CVAR16.getCurrentHour();
    java.lang.Integer _CVAR19 = _CVAR18.getCurrentMinute();
    _CVAR11.set(_CVAR12, _CVAR14, _CVAR15, _CVAR17, _CVAR19);
    android.widget.TimePicker _CVAR20 = endTimePicker;
    android.widget.TimePicker.OnTimeChangedListener _CVAR21 = new android.widget.TimePicker.OnTimeChangedListener() {
        @java.lang.Override
        public void onTimeChanged(android.widget.TimePicker timePicker, int i, int i1) {
            // endCalendar.set(endYear, endMonth - 1, tmpDay, timePicker.getCurrentHour(), timePicker.getCurrentMinute() + 1);
            // if(startCalendar.getTime().getTime() > endCalendar.getTime().getTime()) {
            // tmpDay += 1;
            // endCalendar.set(endYear, endMonth - 1, tmpDay, timePicker.getCurrentHour(), timePicker.getCurrentMinute() + 1);
            // } else {
            // if(Math.abs(tmpDay - endDay) == 1) {
            // tmpDay -= 1;
            // } else {
            // tmpDay = endDay;
            // }
            // endCalendar.set(endYear, endMonth - 1, tmpDay, timePicker.getCurrentHour(), timePicker.getCurrentMinute() + 1);
            // }
            endCalendar.set(endYear, endMonth - 1, endDay, endTimePicker.getCurrentHour(), endTimePicker.getCurrentMinute());
            if (startCalendar.getTime().getTime() > endCalendar.getTime().getTime()) {
                secondDay = true;
            } else {
                secondDay = false;
            }
            if (secondDay) {
                endTime.setText(dateFormat.format(endCalendar.getTime().getTime() + 86400000));
            } else {
                endTime.setText(dateFormat.format(endCalendar.getTime().getTime()));
            }
        }
    };
    _CVAR20.setOnTimeChangedListener(_CVAR21);
    return root;
}