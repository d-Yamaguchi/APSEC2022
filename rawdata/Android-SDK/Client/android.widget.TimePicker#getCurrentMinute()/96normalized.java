@java.lang.Override
public void onClick(android.view.View v) {
    android.content.Context _CVAR8 = mContext;
    android.view.LayoutInflater _CVAR9 = android.view.LayoutInflater.from(_CVAR8);
    void _CVAR10 = R.layout.charger_method_option_item_timepicker;
    <nulltype> _CVAR11 = null;
    android.view.View view = _CVAR9.inflate(_CVAR10, _CVAR11);
    android.view.View _CVAR12 = view;
    void _CVAR13 = R.id.time_picker;
    final android.widget.TimePicker timePicker = ((android.widget.TimePicker) (_CVAR12.findViewById(_CVAR13)));
    android.view.View _CVAR15 = _CVAR12;
    void _CVAR16 = _CVAR13;
    final android.widget.TimePicker timePicker = ((android.widget.TimePicker) (_CVAR15.findViewById(_CVAR16)));
    android.view.View _CVAR19 = _CVAR15;
    void _CVAR20 = _CVAR16;
    final android.widget.TimePicker timePicker = ((android.widget.TimePicker) (_CVAR19.findViewById(_CVAR20)));
    if (onTimeChangedListener != null) {
        int _CVAR0 = 0;
        boolean _CVAR1 = timePicker.getCurrentMinute() == _CVAR0;
        boolean _CVAR2 = (timePicker.getCurrentHour() == 0) && _CVAR1;
        if () {
            com.pinnet.chargerapp.utils.ToastUtils.getInstance().showMessage(getString(R.string.charger_method_option_useless_value));
            return;
        }
        int _CVAR3 = 0;
        boolean _CVAR4 = timePicker.getCurrentMinute() > _CVAR3;
        boolean _CVAR5 = (timePicker.getCurrentHour() == 16) && _CVAR4;
        boolean _CVAR6 = (timePicker.getCurrentHour() > 16) || _CVAR5;
        if () {
            com.pinnet.chargerapp.utils.ToastUtils.getInstance().showMessage(getString(R.string.charger_method_option_useless_max_value));
            return;
        }
        android.widget.TimePicker _CVAR17 = timePicker;
        android.widget.TimePicker _CVAR21 = timePicker;
        android.widget.TimePicker.OnTimeChangedListener _CVAR7 = onTimeChangedListener;
        android.widget.TimePicker _CVAR14 = timePicker;
        java.lang.Integer _CVAR18 = _CVAR17.getCurrentHour();
        java.lang.Integer _CVAR22 = _CVAR21.getCurrentMinute();
        _CVAR7.onTimeChanged(_CVAR14, _CVAR18, _CVAR22);
        dismiss();
    }
}