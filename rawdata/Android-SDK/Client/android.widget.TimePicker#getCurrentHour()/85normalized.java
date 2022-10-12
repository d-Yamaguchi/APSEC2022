@java.lang.Override
public void onClick(android.view.View v) {
    android.content.Context _CVAR10 = mContext;
    android.view.LayoutInflater _CVAR11 = android.view.LayoutInflater.from(_CVAR10);
    void _CVAR12 = R.layout.charger_method_option_item_timepicker;
    <nulltype> _CVAR13 = null;
    android.view.View view = _CVAR11.inflate(_CVAR12, _CVAR13);
    android.view.View _CVAR14 = view;
    void _CVAR15 = R.id.time_picker;
    final android.widget.TimePicker timePicker = ((android.widget.TimePicker) (_CVAR14.findViewById(_CVAR15)));
    android.view.View _CVAR17 = _CVAR14;
    void _CVAR18 = _CVAR15;
    final android.widget.TimePicker timePicker = ((android.widget.TimePicker) (_CVAR17.findViewById(_CVAR18)));
    android.view.View _CVAR21 = _CVAR17;
    void _CVAR22 = _CVAR18;
    final android.widget.TimePicker timePicker = ((android.widget.TimePicker) (_CVAR21.findViewById(_CVAR22)));
    if (onTimeChangedListener != null) {
        int _CVAR0 = 0;
        boolean _CVAR1 = timePicker.getCurrentHour() == _CVAR0;
        boolean _CVAR2 = _CVAR1 && (timePicker.getCurrentMinute() == 0);
        if () {
            com.pinnet.chargerapp.utils.ToastUtils.getInstance().showMessage(getString(R.string.charger_method_option_useless_value));
            return;
        }
        int _CVAR3 = 16;
        boolean _CVAR4 = timePicker.getCurrentHour() > _CVAR3;
        int _CVAR6 = 16;
        boolean _CVAR7 = timePicker.getCurrentHour() == _CVAR6;
        boolean _CVAR8 = _CVAR7 && (timePicker.getCurrentMinute() > 0);
        boolean _CVAR5 = _CVAR4 || _CVAR8;
        if () {
            com.pinnet.chargerapp.utils.ToastUtils.getInstance().showMessage(getString(R.string.charger_method_option_useless_max_value));
            return;
        }
        android.widget.TimePicker _CVAR19 = timePicker;
        android.widget.TimePicker _CVAR23 = timePicker;
        android.widget.TimePicker.OnTimeChangedListener _CVAR9 = onTimeChangedListener;
        android.widget.TimePicker _CVAR16 = timePicker;
        java.lang.Integer _CVAR20 = _CVAR19.getCurrentHour();
        java.lang.Integer _CVAR24 = _CVAR23.getCurrentMinute();
        _CVAR9.onTimeChanged(_CVAR16, _CVAR20, _CVAR24);
        dismiss();
    }
}