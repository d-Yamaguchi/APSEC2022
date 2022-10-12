@java.lang.Override
public void onClick(android.view.View v) {
    if (((com.example.smartalarm.AlarmContentFragment.ContentAdapter.alarms.get(position).getTriggerHour() != (-1)) && (com.example.smartalarm.AlarmContentFragment.ContentAdapter.alarms.get(position).getTriggerMinute() != (-1))) && com.example.smartalarm.AlarmContentFragment.ContentAdapter.alarms.get(position).isChecked()) {
        com.example.smartalarm.AlarmContentFragment.ContentAdapter.alarms.get(position).cancelAlarm(view.getContext());
    }
    if (holder.smartBtn.isChecked()) {
        java.util.ArrayList<com.example.smartalarm.MyAlarmManager> _CVAR0 = com.example.smartalarm.AlarmContentFragment.ContentAdapter.alarms;
        int _CVAR1 = position;
        android.view.View _CVAR3 = view;
        android.widget.TimePicker _CVAR5 = holder.timePicker;
        android.widget.TimePicker _CVAR7 = holder.timePicker;
         _CVAR2 = _CVAR0.get(_CVAR1);
        android.content.Context _CVAR4 = _CVAR3.getContext();
        java.lang.Integer _CVAR6 = _CVAR5.getCurrentHour();
        java.lang.Integer _CVAR8 = _CVAR7.getCurrentMinute();
        _CVAR2.setSmartAlarm(_CVAR4, _CVAR6, _CVAR8);
        int _CVAR9 = 10;
        boolean _CVAR10 = holder.timePicker.getCurrentMinute() < _CVAR9;
        if () {
            android.widget.TimePicker _CVAR12 = holder.timePicker;
            java.lang.Integer _CVAR13 = _CVAR12.getCurrentMinute();
            android.widget.TextView _CVAR11 = holder.time;
            java.lang.String _CVAR14 = (holder.timePicker.getCurrentHour() + ":0") + _CVAR13;
            _CVAR11.setText(_CVAR14);
            android.widget.TimePicker _CVAR16 = holder.timePicker;
            java.lang.Integer _CVAR17 = _CVAR16.getCurrentMinute();
            android.widget.TextView _CVAR15 = holder.settingsTime;
            java.lang.String _CVAR18 = (holder.timePicker.getCurrentHour() + ":0") + _CVAR17;
            _CVAR15.setText(_CVAR18);
        } else {
            android.widget.TimePicker _CVAR20 = holder.timePicker;
            java.lang.Integer _CVAR21 = _CVAR20.getCurrentMinute();
            android.widget.TextView _CVAR19 = holder.time;
            java.lang.String _CVAR22 = (holder.timePicker.getCurrentHour() + ":") + _CVAR21;
            _CVAR19.setText(_CVAR22);
            android.widget.TimePicker _CVAR24 = holder.timePicker;
            java.lang.Integer _CVAR25 = _CVAR24.getCurrentMinute();
            android.widget.TextView _CVAR23 = holder.settingsTime;
            java.lang.String _CVAR26 = (holder.timePicker.getCurrentHour() + ":") + _CVAR25;
            _CVAR23.setText(_CVAR26);
        }
    } else if (holder.everyDayBtn.isChecked()) {
        java.util.ArrayList<com.example.smartalarm.MyAlarmManager> _CVAR27 = com.example.smartalarm.AlarmContentFragment.ContentAdapter.alarms;
        int _CVAR28 = position;
        android.view.View _CVAR30 = view;
        android.widget.TimePicker _CVAR32 = holder.timePicker;
        android.widget.TimePicker _CVAR34 = holder.timePicker;
         _CVAR29 = _CVAR27.get(_CVAR28);
        android.content.Context _CVAR31 = _CVAR30.getContext();
        java.lang.Integer _CVAR33 = _CVAR32.getCurrentHour();
        java.lang.Integer _CVAR35 = _CVAR34.getCurrentMinute();
        _CVAR29.setRepareAlarm(_CVAR31, _CVAR33, _CVAR35);
        int _CVAR36 = 10;
        boolean _CVAR37 = holder.timePicker.getCurrentMinute() < _CVAR36;
        if () {
            android.widget.TimePicker _CVAR39 = holder.timePicker;
            java.lang.Integer _CVAR40 = _CVAR39.getCurrentMinute();
            android.widget.TextView _CVAR38 = holder.time;
            java.lang.String _CVAR41 = (holder.timePicker.getCurrentHour() + ":0") + _CVAR40;
            _CVAR38.setText(_CVAR41);
            android.widget.TimePicker _CVAR43 = holder.timePicker;
            java.lang.Integer _CVAR44 = _CVAR43.getCurrentMinute();
            android.widget.TextView _CVAR42 = holder.settingsTime;
            java.lang.String _CVAR45 = (holder.timePicker.getCurrentHour() + ":0") + _CVAR44;
            _CVAR42.setText(_CVAR45);
        } else {
            android.widget.TimePicker _CVAR47 = holder.timePicker;
            java.lang.Integer _CVAR48 = _CVAR47.getCurrentMinute();
            android.widget.TextView _CVAR46 = holder.time;
            java.lang.String _CVAR49 = (holder.timePicker.getCurrentHour() + ":") + _CVAR48;
            _CVAR46.setText(_CVAR49);
            android.widget.TimePicker _CVAR51 = holder.timePicker;
            java.lang.Integer _CVAR52 = _CVAR51.getCurrentMinute();
            android.widget.TextView _CVAR50 = holder.settingsTime;
            java.lang.String _CVAR53 = (holder.timePicker.getCurrentHour() + ":") + _CVAR52;
            _CVAR50.setText(_CVAR53);
        }
    } else {
        java.util.ArrayList<com.example.smartalarm.MyAlarmManager> _CVAR54 = com.example.smartalarm.AlarmContentFragment.ContentAdapter.alarms;
        int _CVAR55 = position;
        android.view.View _CVAR57 = view;
        android.widget.TimePicker _CVAR59 = holder.timePicker;
        android.widget.TimePicker _CVAR61 = holder.timePicker;
         _CVAR56 = _CVAR54.get(_CVAR55);
        android.content.Context _CVAR58 = _CVAR57.getContext();
        java.lang.Integer _CVAR60 = _CVAR59.getCurrentHour();
        java.lang.Integer _CVAR62 = _CVAR61.getCurrentMinute();
        _CVAR56.setAlarm(_CVAR58, _CVAR60, _CVAR62);
        int _CVAR63 = 10;
        boolean _CVAR64 = holder.timePicker.getCurrentMinute() < _CVAR63;
        if () {
            android.widget.TimePicker _CVAR66 = holder.timePicker;
            java.lang.Integer _CVAR67 = _CVAR66.getCurrentMinute();
            android.widget.TextView _CVAR65 = holder.time;
            java.lang.String _CVAR68 = (holder.timePicker.getCurrentHour() + ":0") + _CVAR67;
            _CVAR65.setText(_CVAR68);
            android.widget.TimePicker _CVAR70 = holder.timePicker;
            java.lang.Integer _CVAR71 = _CVAR70.getCurrentMinute();
            android.widget.TextView _CVAR69 = holder.settingsTime;
            java.lang.String _CVAR72 = (holder.timePicker.getCurrentHour() + ":0") + _CVAR71;
            _CVAR69.setText(_CVAR72);
        } else {
            android.widget.TimePicker _CVAR74 = holder.timePicker;
            java.lang.Integer _CVAR75 = _CVAR74.getCurrentMinute();
            android.widget.TextView _CVAR73 = holder.time;
            java.lang.String _CVAR76 = (holder.timePicker.getCurrentHour() + ":") + _CVAR75;
            _CVAR73.setText(_CVAR76);
            android.widget.TimePicker _CVAR78 = holder.timePicker;
            java.lang.Integer _CVAR79 = _CVAR78.getCurrentMinute();
            android.widget.TextView _CVAR77 = holder.settingsTime;
            java.lang.String _CVAR80 = (holder.timePicker.getCurrentHour() + ":") + _CVAR79;
            _CVAR77.setText(_CVAR80);
        }
    }
    if (((com.example.smartalarm.AlarmContentFragment.ContentAdapter.alarms.get(position).getTriggerHour() == (-1)) || (com.example.smartalarm.AlarmContentFragment.ContentAdapter.alarms.get(position).getTriggerHour() > 19)) || (com.example.smartalarm.AlarmContentFragment.ContentAdapter.alarms.get(position).getTriggerHour() < 7)) {
        holder.alarmLayout.setBackgroundResource(R.drawable.alarm_dark_background);
    } else {
        holder.alarmLayout.setBackgroundResource(R.drawable.alarm_background);
    }
    holder.switchBtn.setChecked(true);
    holder.timeDialog.dismiss();
}