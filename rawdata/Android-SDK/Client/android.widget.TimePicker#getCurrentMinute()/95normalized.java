private void createMeeting() {
    android.text.Editable name = ((android.widget.EditText) (findViewById(R.id.editTextName))).getText();
    if ((name == null) || (name.length() == 0)) {
        android.widget.Toast toast = android.widget.Toast.makeText(getApplicationContext(), "Name must not be empty!", android.widget.Toast.LENGTH_SHORT);
        toast.show();
        return;
    }
    android.widget.TimePicker timePicker1 = ((android.widget.TimePicker) (findViewById(R.id.timePicker1)));
    timePicker1.clearFocus();
    timePicker2.clearFocus();
    void _CVAR0 = R.id.timePicker2;
    void _CVAR6 = _CVAR0;
    android.widget.TimePicker timePicker2 = ((android.widget.TimePicker) (findViewById(_CVAR6)));
    android.widget.TimePicker _CVAR1 = timePicker2;
    android.widget.TimePicker _CVAR7 = _CVAR1;
    java.lang.Integer _CVAR2 = _CVAR7.getCurrentMinute();
    boolean _CVAR3 = timePicker1.getCurrentMinute() >= _CVAR2;
    boolean _CVAR4 = java.util.Objects.equals(timePicker1.getCurrentHour(), timePicker2.getCurrentHour()) && _CVAR3;
    boolean _CVAR5 = (timePicker1.getCurrentHour() > timePicker2.getCurrentHour()) || _CVAR4;
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