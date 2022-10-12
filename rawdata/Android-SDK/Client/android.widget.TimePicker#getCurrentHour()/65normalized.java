@java.lang.Override
protected void onCreate(android.os.Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.edit_schedule2);
    timePicker1 = ((android.widget.TimePicker) (findViewById(R.id.tp1)));
    android.widget.TimePicker _CVAR0 = timePicker1;
    int hourtp1 = _CVAR0.getCurrentHour();
    int mintp1 = timePicker1.getCurrentMinute();
    timePicker2 = ((android.widget.TimePicker) (findViewById(R.id.tp2)));
    android.widget.TimePicker _CVAR1 = timePicker2;
    int hourtp2 = _CVAR1.getCurrentHour();
    int mintp2 = timePicker2.getCurrentMinute();
    timePicker3 = ((android.widget.TimePicker) (findViewById(R.id.tp3)));
    android.widget.TimePicker _CVAR2 = timePicker3;
    int hourtp3 = _CVAR2.getCurrentHour();
    int mintp3 = timePicker3.getCurrentMinute();
    timePicker4 = ((android.widget.TimePicker) (findViewById(R.id.tp4)));
    android.widget.TimePicker _CVAR3 = timePicker4;
    int hourtp4 = _CVAR3.getCurrentHour();
    int mintp4 = timePicker4.getCurrentMinute();
}