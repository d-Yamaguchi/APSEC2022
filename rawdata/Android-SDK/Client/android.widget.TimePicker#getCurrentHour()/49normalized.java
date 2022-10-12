@java.lang.Override
public void onClick(android.view.View view) {
    int _CVAR0 = 12;
    boolean _CVAR1 = timePicker.getCurrentHour() < _CVAR0;
    if () {
        sb.append(mContext.getString(R.string.jmui_morning) + " ");
    } else {
        int _CVAR2 = 18;
        boolean _CVAR3 = timePicker.getCurrentHour() < _CVAR2;
        if () {
            sb.append(mContext.getString(R.string.jmui_afternoon) + " ");
        } else {
            sb.append(mContext.getString(R.string.jmui_night) + " ");
        }
    }
    int _CVAR4 = 10;
    boolean _CVAR5 = timePicker.getCurrentHour() < _CVAR4;
    if () {
        sb.append("0");
    }
    java.lang.StringBuffer sb = new java.lang.StringBuffer();
    android.content.Context _CVAR7 = mContext;
    void _CVAR8 = R.layout.dialog_time_picker;
    <nulltype> _CVAR9 = null;
    android.view.View view = android.view.View.inflate(_CVAR7, _CVAR8, _CVAR9);
    android.view.View _CVAR10 = view;
    void _CVAR11 = R.id.time_picker;
    final android.widget.TimePicker timePicker = ((android.widget.TimePicker) (_CVAR10.findViewById(_CVAR11)));
    android.widget.TimePicker _CVAR12 = timePicker;
    java.lang.StringBuffer _CVAR6 = sb;
    java.lang.Integer _CVAR13 = _CVAR12.getCurrentHour();
    java.lang.StringBuffer _CVAR14 = _CVAR6.append(_CVAR13);
    java.lang.String _CVAR15 = ":";
    _CVAR14.append(_CVAR15);
    if (timePicker.getCurrentMinute() < 10) {
        sb.append("0");
    }
    sb.append(timePicker.getCurrentMinute());
    mBeginTime.setText(sb);
    dialog.cancel();
}