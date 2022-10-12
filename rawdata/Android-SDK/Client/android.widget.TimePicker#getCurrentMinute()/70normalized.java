public void onClick(android.view.View v) {
    java.lang.System.out.println("inside button click");
    int hour;
    int minute;
    java.lang.System.out.println("Selected time h: " + tp1.getCurrentHour());
    android.widget.TimePicker _CVAR1 = tp1;
    java.lang.Integer _CVAR2 = _CVAR1.getCurrentMinute();
    java.io.PrintStream _CVAR0 = java.lang.System.out;
    java.lang.String _CVAR3 = "Selected time m: " + _CVAR2;
    _CVAR0.println(_CVAR3);
    java.lang.String sres = itv.getText().toString();
    android.content.Intent i = new android.content.Intent(getApplicationContext(), com.ibm.bluelist.lastscreen.class);
    i.putExtra("a", sres);
    int a = tp1.getCurrentHour();
    java.lang.String ti;
    if (a >= 12) {
        android.widget.TimePicker _CVAR4 = tp1;
        java.lang.Integer _CVAR5 = _CVAR4.getCurrentMinute();
        java.lang.String _CVAR6 = _CVAR5.toString();
        java.lang.String _CVAR7 = (new java.lang.Integer(a).toString() + ":") + _CVAR6;
        java.lang.String _CVAR8 = _CVAR7 + "PM";
        ti = _CVAR8;
    } else {
        android.widget.TimePicker _CVAR9 = tp1;
        java.lang.Integer _CVAR10 = _CVAR9.getCurrentMinute();
        java.lang.String _CVAR11 = _CVAR10.toString();
        java.lang.String _CVAR12 = (new java.lang.Integer(a).toString() + ":") + _CVAR11;
        java.lang.String _CVAR13 = _CVAR12 + "AM";
        ti = _CVAR13;
    }
    i.putExtra("b", ti);
    startActivity(i);
}