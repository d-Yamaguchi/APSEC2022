@java.lang.Override
public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
    android.view.View view = inflater.inflate(R.layout.fragment_time_start, container, false);
    // Inflate the layout for this fragment
    timePicker = ((android.widget.TimePicker) (view.findViewById(R.id.timepicker_start)));
    android.widget.TimePicker _CVAR0 = timePicker;
    java.lang.Integer _CVAR1 = _CVAR0.getCurrentHour();
    java.lang.String _CVAR2 = _CVAR1.toString();
    selectedHour = _CVAR2;
    android.widget.TimePicker _CVAR3 = timePicker;
    java.lang.Integer _CVAR4 = _CVAR3.getCurrentHour();
    java.lang.String _CVAR5 = _CVAR4.toString();
    selectedMin = _CVAR5;
    timePicker.setOnTimeChangedListener(new android.widget.TimePicker.OnTimeChangedListener() {
        @java.lang.Override
        public void onTimeChanged(android.widget.TimePicker view, int hourOfDay, int minute) {
            java.lang.Integer hour = hourOfDay;
            java.lang.Integer min = minute;
            selectedHour = hour.toString();
            selectedMin = min.toString();
        }
    });
    buttonTimeStart = view.findViewById(R.id.btn_time_s);
    android.widget.Button _CVAR6 = buttonTimeStart;
    android.view.View.OnClickListener _CVAR7 = new android.view.View.OnClickListener() {
        @java.lang.Override
        public void onClick(android.view.View v) {
            selectedHour = timePicker.getCurrentHour().toString();
            com.example.joongwonkim.somulbo2.data.Time time = new com.example.joongwonkim.somulbo2.data.Time();
            time.setHour(selectedHour);
            time.setMin(selectedMin);
            mListener.myStartTimeStart(selectedHour, selectedMin);
            mListener.transferTab();
        }
    };
    _CVAR6.setOnClickListener(_CVAR7);
    return view;
}