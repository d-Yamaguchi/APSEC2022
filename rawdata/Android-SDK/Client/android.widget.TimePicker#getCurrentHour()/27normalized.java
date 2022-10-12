public void calculateIntent(android.view.View view) {
    android.widget.EditText wage = ((android.widget.EditText) (findViewById(R.id.wage)));
    edit.clear();
    android.widget.TimePicker _CVAR2 = beg;
    java.lang.Integer _CVAR3 = _CVAR2.getCurrentHour();
    java.lang.String _CVAR4 = "beg: " + _CVAR3;
    java.lang.String _CVAR5 = _CVAR4 + " end: ";
    java.io.PrintStream _CVAR0 = java.lang.System.out;
    android.widget.TimePicker _CVAR8 = end;
    java.lang.Integer _CVAR9 = _CVAR8.getCurrentHour();
    java.lang.String _CVAR6 = _CVAR5 + _CVAR9;
    _CVAR0.println(_CVAR6);
    void _CVAR1 = R.id.timePicker1;
    void _CVAR15 = _CVAR1;
    android.widget.TimePicker beg = ((android.widget.TimePicker) (findViewById(_CVAR15)));
    android.widget.TimePicker _CVAR16 = beg;
    android.content.SharedPreferences.Editor _CVAR13 = edit;
    java.lang.String _CVAR14 = "beginHour";
    java.lang.Integer _CVAR17 = _CVAR16.getCurrentHour();
    _CVAR13.putInt(_CVAR14, _CVAR17);
    edit.putInt("beginMinute", beg.getCurrentMinute());
    java.lang.String _CVAR10 = "shiftDetails";
    void _CVAR11 = com.mycompany.workpercent.MODE_PRIVATE;
    java.lang.String _CVAR18 = _CVAR10;
    void _CVAR19 = _CVAR11;
    android.content.SharedPreferences shiftDetails = getSharedPreferences(_CVAR18, _CVAR19);
    android.content.SharedPreferences _CVAR12 = shiftDetails;
    android.content.SharedPreferences _CVAR20 = _CVAR12;
    android.content.SharedPreferences.Editor edit = _CVAR20.edit();
    void _CVAR7 = R.id.timePicker2;
    void _CVAR23 = _CVAR7;
    android.widget.TimePicker end = ((android.widget.TimePicker) (findViewById(_CVAR23)));
    android.widget.TimePicker _CVAR24 = end;
    android.content.SharedPreferences.Editor _CVAR21 = edit;
    java.lang.String _CVAR22 = "endHour";
    java.lang.Integer _CVAR25 = _CVAR24.getCurrentHour();
    _CVAR21.putInt(_CVAR22, _CVAR25);
    edit.putInt("endMinute", end.getCurrentMinute());
    if (wage.getText().toString().equals("")) {
        edit.putFloat("hourlyWage", 5);
    } else {
        edit.putFloat("hourlyWage", java.lang.Float.valueOf(wage.getText().toString()));
    }
    edit.commit();
    android.widget.Toast.makeText(getApplicationContext(), "Shift details are saved..", android.widget.Toast.LENGTH_LONG).show();
    android.content.Intent in = new android.content.Intent(getApplicationContext(), com.mycompany.workpercent.DisplayActivity.class);
    startActivity(in);
}