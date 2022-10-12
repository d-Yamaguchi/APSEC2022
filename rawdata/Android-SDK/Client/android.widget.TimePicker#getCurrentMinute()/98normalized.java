@java.lang.Override
public void onClick(android.view.View v) {
    // String x=tp.getCurrentHour()+":"+tp.getCurrentMinute();
    if (data.getString("position").equals("1")) {
        data.putString("timestarth", java.lang.Integer.toString(tp.getCurrentHour()));
        android.widget.TimePicker _CVAR2 = tp;
        java.lang.Integer _CVAR3 = _CVAR2.getCurrentMinute();
        android.os.Bundle _CVAR0 = data;
        java.lang.String _CVAR1 = "timestartm";
        java.lang.String _CVAR4 = java.lang.Integer.toString(_CVAR3);
        _CVAR0.putString(_CVAR1, _CVAR4);
    } else {
        data.putString("timeendh", java.lang.Integer.toString(tp.getCurrentHour()));
        android.widget.TimePicker _CVAR7 = tp;
        java.lang.Integer _CVAR8 = _CVAR7.getCurrentMinute();
        android.os.Bundle _CVAR5 = data;
        java.lang.String _CVAR6 = "timeendm";
        java.lang.String _CVAR9 = java.lang.Integer.toString(_CVAR8);
        _CVAR5.putString(_CVAR6, _CVAR9);
    }
    android.content.Intent intent = new android.content.Intent(getApplication(), com.hemanshu95.android.silentit.Input.class).putExtra("data", data);
    // .putExtra("hours",tp.getCurrentHour())
    // .putExtra("minutes",tp.getCurrentMinute());
    startActivity(intent);
}