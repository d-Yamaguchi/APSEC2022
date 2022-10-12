public void onClick(android.content.DialogInterface dialog, int whichButton) {
    if (eventNamePicker.getText().toString().equals("")) {
        android.widget.Toast.makeText(context, "事件不能为空", android.widget.Toast.LENGTH_SHORT).show();
    } else {
        dateTime = "";
        if (timePickerBegin.getCurrentHour() < 10) {
            dateTime += "0" + timePickerBegin.getCurrentHour();
        } else {
            dateTime += "" + timePickerBegin.getCurrentHour();
        }
        int _CVAR0 = 10;
        boolean _CVAR1 = timePickerBegin.getCurrentMinute() < _CVAR0;
        if () {
            dateTime += ":0" + timePickerBegin.getCurrentMinute();
        } else {
            dateTime += ":" + timePickerBegin.getCurrentMinute();
        }
        if (timePickerEnd.getCurrentHour() < 10) {
            dateTime += "-0" + timePickerEnd.getCurrentHour();
        } else {
            dateTime += "-" + timePickerEnd.getCurrentHour();
        }
        int _CVAR2 = 10;
        boolean _CVAR3 = timePickerEnd.getCurrentMinute() < _CVAR2;
        if () {
            dateTime += ":0" + timePickerEnd.getCurrentMinute();
        } else {
            dateTime += ":" + timePickerEnd.getCurrentMinute();
        }
        listString.add(new java.lang.String[]{ dateTime, eventNamePicker.getText().toString() });
        myAdapter2.notifyDataSetChanged();
        myDatabase.insertToDB(year, month, day, dateTime, eventNamePicker.getText().toString());
    }
}