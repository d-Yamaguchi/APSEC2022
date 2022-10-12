@java.lang.Override
protected void onCreate(android.os.Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    timepicker = ((android.widget.TimePicker) (findViewById(R.id.timePicker)));
    button = ((android.widget.Button) (findViewById(R.id.btn)));
    timepicker.setIs24HourView(true);
    timepicker.getCurrentHour();
    android.widget.TimePicker _CVAR0 = timepicker;
    _CVAR0.getCurrentMinute();
    android.widget.Button _CVAR1 = button;
    android.view.View.OnClickListener _CVAR2 = new android.view.View.OnClickListener() {
        @java.lang.Override
        public void onClick(android.view.View v) {
            android.widget.Toast.makeText(MainActivity.this, (("Current time : " + timepicker.getCurrentHour()) + " : ") + timepicker.getCurrentMinute(), android.widget.Toast.LENGTH_SHORT).show();
        }
    };
    _CVAR1.setOnClickListener(_CVAR2);
}