@java.lang.Override
public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
    android.view.View view = inflater.inflate(R.layout.fragment_time_start, container, false);
    // Inflate the layout for this fragment
    timePicker = ((android.widget.TimePicker) (view.findViewById(R.id.timepicker_start)));
    timePicker.setIs24HourView(true);
    android.widget.TimePicker _CVAR0 = timePicker;
    java.lang.Integer _CVAR1 = _CVAR0.getCurrentHour();
    selectedHour = _CVAR1;
    android.widget.TimePicker _CVAR2 = timePicker;
    java.lang.Integer _CVAR3 = _CVAR2.getCurrentHour();
    selectedMin = _CVAR3;
    timePicker.setOnTimeChangedListener(new android.widget.TimePicker.OnTimeChangedListener() {
        @java.lang.Override
        public void onTimeChanged(android.widget.TimePicker view, int hourOfDay, int minute) {
            java.lang.Integer hour = hourOfDay;
            java.lang.Integer min = minute;
            selectedHour = hour;
            selectedMin = min;
        }
    });
    buttonTimeStart = view.findViewById(R.id.btn_time_s);
    android.widget.Button _CVAR4 = buttonTimeStart;
    android.view.View.OnClickListener _CVAR5 = new android.view.View.OnClickListener() {
        @java.lang.Override
        public void onClick(android.view.View v) {
            selectedHour = timePicker.getCurrentHour();
            com.kjw.twentyhour.data.Time time = new com.kjw.twentyhour.data.Time();
            time.setHour(selectedHour);
            time.setMin(selectedMin);
            mListener.myStartTimeStart(selectedHour, selectedMin);
            mListener.transferTab();
        }
    };
    _CVAR4.setOnClickListener(_CVAR5);
    return view;
}