@java.lang.Override
protected void onCreate(android.os.Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.edit_schedule2);
    timePicker1 = ((android.widget.TimePicker) (findViewById(R.id.tp1)));
    int hourtp1 = timePicker1.getCurrentHour();
    android.widget.TimePicker _CVAR0 = timePicker1;
    int mintp1 = _CVAR0.getCurrentMinute();
    timePicker2 = ((android.widget.TimePicker) (findViewById(R.id.tp2)));
    int hourtp2 = timePicker2.getCurrentHour();
    android.widget.TimePicker _CVAR1 = timePicker2;
    int mintp2 = _CVAR1.getCurrentMinute();
    timePicker3 = ((android.widget.TimePicker) (findViewById(R.id.tp3)));
    int hourtp3 = timePicker3.getCurrentHour();
    android.widget.TimePicker _CVAR2 = timePicker3;
    int mintp3 = _CVAR2.getCurrentMinute();
    timePicker4 = ((android.widget.TimePicker) (findViewById(R.id.tp4)));
    int hourtp4 = timePicker4.getCurrentHour();
    android.widget.TimePicker _CVAR3 = timePicker4;
    int mintp4 = _CVAR3.getCurrentMinute();
}