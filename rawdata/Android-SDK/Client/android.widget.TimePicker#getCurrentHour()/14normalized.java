public void onClick(android.content.DialogInterface dialog, int whichButton) {
    if (eventNamePicker.getText().toString().equals("")) {
        android.widget.Toast.makeText(context, "事件不能为空", android.widget.Toast.LENGTH_SHORT).show();
    } else {
        dateTime = "";
        int _CVAR0 = 10;
        boolean _CVAR1 = timePickerBegin.getCurrentHour() < _CVAR0;
        if () {
            dateTime += "0" + timePickerBegin.getCurrentHour();
        } else {
            dateTime += "" + timePickerBegin.getCurrentHour();
        }
        if (timePickerBegin.getCurrentMinute() < 10) {
            dateTime += ":0" + timePickerBegin.getCurrentMinute();
        } else {
            dateTime += ":" + timePickerBegin.getCurrentMinute();
        }
        int _CVAR2 = 10;
        boolean _CVAR3 = timePickerEnd.getCurrentHour() < _CVAR2;
        if () {
            dateTime += "-0" + timePickerEnd.getCurrentHour();
        } else {
            dateTime += "-" + timePickerEnd.getCurrentHour();
        }
        if (timePickerEnd.getCurrentMinute() < 10) {
            dateTime += ":0" + timePickerEnd.getCurrentMinute();
        } else {
            dateTime += ":" + timePickerEnd.getCurrentMinute();
        }
        listString.add(new java.lang.String[]{ dateTime, eventNamePicker.getText().toString() });
        myAdapter2.notifyDataSetChanged();
        myDatabase.insertToDB(year, month, day, dateTime, eventNamePicker.getText().toString());
    }
}