@java.lang.Override
public void onClick(android.view.View v) {
    java.util.Calendar cal = java.util.Calendar.getInstance();
    // TODO Auto-generated method stub
    switch (v.getId()) {
        case R.id.notification :
            // Toast.makeText(getApplicationContext(), strDate, Toast.LENGTH_LONG).show();
            if (cal.get(java.util.Calendar.MONTH) < 7) {
                java.util.Calendar _CVAR2 = cal;
                int _CVAR3 = java.util.Calendar.YEAR;
                android.widget.TimePicker _CVAR5 = pk;
                android.widget.TimePicker _CVAR7 = pk;
                int _CVAR0 = 28;
                int _CVAR1 = 06;
                int _CVAR4 = _CVAR2.get(_CVAR3);
                java.lang.Integer _CVAR6 = _CVAR5.getCurrentHour();
                java.lang.Integer _CVAR8 = _CVAR7.getCurrentMinute();
                int _CVAR9 = 0;
                notification(_CVAR0, _CVAR1, _CVAR4, _CVAR6, _CVAR8, _CVAR9);
            }
            if (cal.get(java.util.Calendar.MONTH) < 6) {
                java.util.Calendar _CVAR12 = cal;
                int _CVAR13 = java.util.Calendar.YEAR;
                android.widget.TimePicker _CVAR15 = pk;
                android.widget.TimePicker _CVAR17 = pk;
                int _CVAR10 = 14;
                int _CVAR11 = 05;
                int _CVAR14 = _CVAR12.get(_CVAR13);
                java.lang.Integer _CVAR16 = _CVAR15.getCurrentHour();
                java.lang.Integer _CVAR18 = _CVAR17.getCurrentMinute();
                int _CVAR19 = 0;
                notification(_CVAR10, _CVAR11, _CVAR14, _CVAR16, _CVAR18, _CVAR19);
            }
            if (cal.get(java.util.Calendar.MONTH) < 5) {
                java.util.Calendar _CVAR22 = cal;
                int _CVAR23 = java.util.Calendar.YEAR;
                android.widget.TimePicker _CVAR25 = pk;
                android.widget.TimePicker _CVAR27 = pk;
                int _CVAR20 = 8;
                int _CVAR21 = 04;
                int _CVAR24 = _CVAR22.get(_CVAR23);
                java.lang.Integer _CVAR26 = _CVAR25.getCurrentHour();
                java.lang.Integer _CVAR28 = _CVAR27.getCurrentMinute();
                int _CVAR29 = 0;
                notification(_CVAR20, _CVAR21, _CVAR24, _CVAR26, _CVAR28, _CVAR29);
            }
            break;
        default :
            break;
    }
}