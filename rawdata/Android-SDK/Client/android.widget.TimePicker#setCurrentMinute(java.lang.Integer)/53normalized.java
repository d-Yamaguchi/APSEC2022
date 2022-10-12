@java.lang.Override
public void onTimeChanged(android.widget.TimePicker view, int hourOfDay, int minute) {
    android.util.Log.d("onTimeChanged", (hourOfDay + " ") + minute);
    if (!isEnter) {
        switch (hourOfDay) {
            case 0 :
            case 1 :
                android.widget.TimePicker _CVAR0 = view;
                int _CVAR1 = 1;
                _CVAR0.setCurrentMinute(_CVAR1);
                break;
            case 2 :
                android.widget.TimePicker _CVAR2 = view;
                int _CVAR3 = 0;
                _CVAR2.setCurrentMinute(_CVAR3);
                break;
            default :
                break;
        }
    }
}