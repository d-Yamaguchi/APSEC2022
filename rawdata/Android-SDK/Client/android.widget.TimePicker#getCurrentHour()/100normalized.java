@java.lang.Override
public void onClick(android.content.DialogInterface dialogInterface, int i) {
    android.content.Context _CVAR0 = context;
    final android.widget.TimePicker timePicker = new android.widget.TimePicker(_CVAR0);
    android.widget.TimePicker _CVAR1 = timePicker;
    java.lang.Integer _CVAR2 = _CVAR1.getCurrentHour();
    java.lang.String _CVAR3 = "Time is from View " + _CVAR2;
    java.lang.String _CVAR4 = _CVAR3 + ":";
    java.lang.String _CVAR5 = _CVAR4 + timePicker.getCurrentMinute();
    com.personal.poryto.poryto.Ulti.Ulti.Loger(_CVAR5);
    java.lang.String _CVAR7 = ":";
    java.lang.String _CVAR8 = timePicker.getCurrentHour() + _CVAR7;
    android.widget.TextView _CVAR6 = txt_workTime;
    java.lang.String _CVAR9 = _CVAR8 + timePicker.getCurrentMinute();
    _CVAR6.setText(_CVAR9);
}