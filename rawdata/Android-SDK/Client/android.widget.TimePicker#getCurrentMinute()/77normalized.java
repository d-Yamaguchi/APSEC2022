@java.lang.Override
public void onClick(android.view.View v) {
    switch (v.getId()) {
        case R.id.confirmBtn :
            if (checkTimeValid()) {
                startHour = timePickerStart.getCurrentHour();
                android.widget.TimePicker _CVAR0 = timePickerStart;
                java.lang.Integer _CVAR1 = _CVAR0.getCurrentMinute();
                startMinute = _CVAR1;
                endHour = timePickerEnd.getCurrentHour();
                android.widget.TimePicker _CVAR2 = timePickerEnd;
                java.lang.Integer _CVAR3 = _CVAR2.getCurrentMinute();
                endMinute = _CVAR3;
                leaveWithConfirm = true;
                this.dismiss();
            } else {
                android.widget.Toast.makeText(context, context.getString(R.string.toast_starttime_grater_than_endtime), android.widget.Toast.LENGTH_SHORT).show();
            }
            break;
        case R.id.cancelBtn :
            leaveWithConfirm = false;
            this.dismiss();
            break;
    }
}