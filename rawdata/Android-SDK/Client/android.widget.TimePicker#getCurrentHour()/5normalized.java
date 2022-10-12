@java.lang.Override
public void onClick(android.view.View v) {
    int selectedID = Day.getCheckedRadioButtonId();
    WhichDay = ((android.widget.RadioButton) (findViewById(selectedID)));
    if (isFulfillCorrectly()) {
        intent.putExtra("ClassName", ClassName.getText().toString());
        android.widget.TimePicker _CVAR2 = StartTime;
        android.content.Intent _CVAR0 = intent;
        java.lang.String _CVAR1 = "StartTimeH";
        java.lang.Integer _CVAR3 = _CVAR2.getCurrentHour();
        _CVAR0.putExtra(_CVAR1, _CVAR3);
        android.content.Intent intent = new android.content.Intent();
        android.widget.TimePicker _CVAR6 = EndTime;
        android.content.Intent _CVAR4 = intent;
        java.lang.String _CVAR5 = "EndTimeH";
        java.lang.Integer _CVAR7 = _CVAR6.getCurrentHour();
        _CVAR4.putExtra(_CVAR5, _CVAR7);
        intent.putExtra("StartTimeM", StartTime.getCurrentMinute());
        intent.putExtra("EndTimeM", EndTime.getCurrentMinute());
        intent.putExtra("Day", WhichDay.getText().toString());
        setResult(android.app.Activity.RESULT_OK, intent);
        finish();
    } else {
        android.widget.Toast.makeText(getApplicationContext(), "Wrong input parameters. Correct it!", android.widget.Toast.LENGTH_LONG).show();
    }
}