@java.lang.Override
public void onClick(android.view.View v) {
    android.widget.TimePicker _CVAR2 = start;
    com.example.autoreply.GlobalVariables _CVAR0 = g;
    java.lang.Integer _CVAR3 = _CVAR2.getCurrentHour();
    _CVAR0.setStartHour(_CVAR3);
    g.setStartMin(start.getCurrentMinute());
    com.example.autoreply.GlobalVariables g = com.example.autoreply.GlobalVariables.getInstance();
    void _CVAR1 = R.id.timestart;
    void _CVAR5 = _CVAR1;
    final android.widget.TimePicker start = ((android.widget.TimePicker) (findViewById(_CVAR5)));
    android.widget.TimePicker _CVAR6 = start;
    com.example.autoreply.GlobalVariables _CVAR4 = g;
    java.lang.Integer _CVAR7 = _CVAR6.getCurrentHour();
    _CVAR4.setStopHour(_CVAR7);
    g.setStopMin(stop.getCurrentMinute());
    // System.out.println(start.getCurrentHour());
    // System.out.println(stop.getCurrentHour());
}