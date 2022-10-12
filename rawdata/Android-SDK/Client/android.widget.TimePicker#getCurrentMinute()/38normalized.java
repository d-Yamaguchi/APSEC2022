@java.lang.Override
public void onClick(android.content.DialogInterface dialogInterface, int i) {
    android.widget.TimePicker _CVAR1 = timePicker;
    java.lang.Integer _CVAR2 = _CVAR1.getCurrentMinute();
    java.lang.String _CVAR3 = (("Time is from View " + timePicker.getCurrentHour()) + ":") + _CVAR2;
    com.personal.poryto.poryto.Ulti.Ulti.Loger(_CVAR3);
    android.content.Context _CVAR0 = context;
    android.content.Context _CVAR5 = _CVAR0;
    final android.widget.TimePicker timePicker = new android.widget.TimePicker(_CVAR5);
    android.widget.TimePicker _CVAR6 = timePicker;
    java.lang.Integer _CVAR7 = _CVAR6.getCurrentMinute();
    android.widget.TextView _CVAR4 = txt_workTime;
    java.lang.String _CVAR8 = (timePicker.getCurrentHour() + ":") + _CVAR7;
    _CVAR4.setText(_CVAR8);
}