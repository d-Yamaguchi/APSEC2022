@java.lang.Override
public void onTimeChanged(android.widget.TimePicker view, int hourOfDay, int minute) {
    android.util.Log.d("deb", "activity.updatesettings");
    android.widget.Switch _CVAR1 = s;
    android.widget.TimePicker _CVAR3 = tp;
    android.widget.TimePicker _CVAR5 = tp;
    android.widget.TimePicker _CVAR7 = tp2;
    boolean _CVAR2 = _CVAR1.isChecked();
    java.lang.Integer _CVAR4 = _CVAR3.getCurrentHour();
    java.lang.Integer _CVAR6 = _CVAR5.getCurrentMinute();
    java.lang.Integer _CVAR8 = _CVAR7.getCurrentHour();
    android.widget.TimePicker _CVAR9 = tp2;
    android.widget.TimePicker _CVAR12 = _CVAR9;
    java.lang.Integer _CVAR10 = _CVAR12.getCurrentMinute();
    com.google.firebase.database.DatabaseReference _CVAR0 = doorSettingsRef;
    ca.jamesreeve.smarthome.DoorSettingsHelper _CVAR11 = new ca.jamesreeve.smarthome.DoorSettingsHelper(_CVAR2, _CVAR4, _CVAR6, _CVAR8, _CVAR10);
    _CVAR0.setValue(_CVAR11);
}