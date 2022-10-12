private void createMeeting() {
    android.text.Editable name = ((android.widget.EditText) (findViewById(R.id.editTextName))).getText();
    if ((name == null) || (name.length() == 0)) {
        android.widget.Toast toast = android.widget.Toast.makeText(getApplicationContext(), "Name must not be empty!", android.widget.Toast.LENGTH_SHORT);
        toast.show();
        return;
    }
    timePicker1.clearFocus();
    timePicker2.clearFocus();
    android.widget.TimePicker _CVAR1 = timePicker2;
    android.widget.TimePicker _CVAR6 = _CVAR1;
    java.lang.Integer _CVAR2 = _CVAR6.getCurrentHour();
    boolean _CVAR3 = timePicker1.getCurrentHour() > _CVAR2;
    void _CVAR7 = R.id.timePicker1;
    android.widget.TimePicker timePicker1 = ((android.widget.TimePicker) (findViewById(_CVAR7)));
    android.widget.TimePicker _CVAR8 = timePicker1;
    java.lang.Integer _CVAR9 = _CVAR8.getCurrentHour();
    void _CVAR0 = R.id.timePicker2;
    void _CVAR5 = _CVAR0;
    void _CVAR10 = _CVAR5;
    void _CVAR15 = _CVAR10;
    android.widget.TimePicker timePicker2 = ((android.widget.TimePicker) (findViewById(_CVAR15)));
    android.widget.TimePicker _CVAR11 = timePicker2;
    android.widget.TimePicker _CVAR16 = _CVAR11;
    java.lang.Integer _CVAR12 = _CVAR16.getCurrentHour();
    boolean _CVAR13 = java.util.Objects.equals(_CVAR9, _CVAR12);
    boolean _CVAR14 = _CVAR13 && (timePicker1.getCurrentMinute() >= timePicker2.getCurrentMinute());
    boolean _CVAR4 = _CVAR3 || _CVAR14;
    if () {
        android.widget.Toast toast = android.widget.Toast.makeText(getApplicationContext(), "Starting time must be earlier than ending time!", android.widget.Toast.LENGTH_SHORT);
        toast.show();
        return;
    }
    com.iglin.lab2rest.model.Meeting meeting = new com.iglin.lab2rest.model.Meeting();
    meeting.setName(name.toString());
    android.widget.EditText editTextDescr = ((android.widget.EditText) (findViewById(R.id.editTextDescr)));
    if ((editTextDescr.getText() != null) && (editTextDescr.getText().length() > 0)) {
        meeting.setDescription(editTextDescr.getText().toString());
    }
    meeting.setPriority(java.lang.String.valueOf(((android.widget.Spinner) (findViewById(R.id.spinner))).getSelectedItem()));
    android.widget.DatePicker datePicker = ((android.widget.DatePicker) (findViewById(R.id.datePicker)));
    java.lang.String startTime = (((datePicker.getYear() + "-") + (datePicker.getMonth() + 1)) + "-") + datePicker.getDayOfMonth();
    java.lang.String endTime = startTime;
    startTime += ((("T" + timePicker1.getCurrentHour()) + ":") + timePicker1.getCurrentMinute()) + ":00";
    endTime += ((("T" + timePicker2.getCurrentHour()) + ":") + timePicker2.getCurrentMinute()) + ":00";
    try {
        meeting.setStartTime(com.iglin.lab2rest.model.DateTimeFormatter.getDate(startTime));
        meeting.setEndTime(com.iglin.lab2rest.model.DateTimeFormatter.getDate(endTime));
    } catch (java.text.ParseException e) {
        android.widget.Toast toast = android.widget.Toast.makeText(getApplicationContext(), "Couldn't parse date!", android.widget.Toast.LENGTH_SHORT);
        toast.show();
        return;
    }
    try {
        com.google.firebase.database.DatabaseReference database = com.google.firebase.database.FirebaseDatabase.getInstance().getReference();
        database.child("meetings").push().setValue(meeting);
        android.widget.Toast toast = android.widget.Toast.makeText(getApplicationContext(), "Meeting successfully created.", android.widget.Toast.LENGTH_SHORT);
        toast.show();
    } catch (java.lang.Throwable e) {
        android.widget.Toast toast = android.widget.Toast.makeText(getApplicationContext(), "Error during saving data to the server! Check your network connection.", android.widget.Toast.LENGTH_SHORT);
        toast.show();
    }
}