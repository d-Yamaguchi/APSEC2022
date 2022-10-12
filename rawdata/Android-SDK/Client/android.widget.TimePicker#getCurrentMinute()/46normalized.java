public void btnClick(android.view.View v) {
    android.widget.TimePicker _CVAR3 = timePicker;
    java.text.NumberFormat _CVAR2 = formatter;
    java.lang.Integer _CVAR4 = _CVAR3.getCurrentMinute();
    java.lang.String _CVAR5 = _CVAR2.format(_CVAR4);
     _CVAR0 = getBaseContext();
    java.lang.String _CVAR6 = (("Time selected:" + timePicker.getCurrentHour()) + ":") + _CVAR5;
    int _CVAR7 = android.widget.Toast.LENGTH_SHORT;
    android.widget.Toast _CVAR8 = android.widget.Toast.makeText(_CVAR0, _CVAR6, _CVAR7);
    _CVAR8.show();
    com.example.john.oneway.Driver driver = new com.example.john.oneway.Driver();
    java.lang.String _CVAR1 = "00";
    java.lang.String _CVAR10 = _CVAR1;
    java.text.NumberFormat formatter = new java.text.DecimalFormat(_CVAR10);
    android.widget.TimePicker _CVAR12 = timePicker;
    java.text.NumberFormat _CVAR11 = formatter;
    java.lang.Integer _CVAR13 = _CVAR12.getCurrentMinute();
    java.lang.String _CVAR14 = _CVAR11.format(_CVAR13);
    java.lang.String _CVAR15 = java.lang.String.valueOf(_CVAR14);
    com.example.john.oneway.Driver _CVAR9 = driver;
    java.lang.String _CVAR16 = (("Service time is: " + java.lang.String.valueOf(timePicker.getCurrentHour())) + ":") + _CVAR15;
    _CVAR9.setmTimePicker(_CVAR16);
    android.content.Intent returnIntent = new android.content.Intent();
    returnIntent.putExtra(com.example.john.oneway.Filter.TimeActivity.EXTRA, driver);
    // returnIntent.putExtra(EXTRA,timePicker.getCurrentMinute());
    setResult(com.example.john.oneway.Filter.RESULT_OK, returnIntent);
    finish();
}