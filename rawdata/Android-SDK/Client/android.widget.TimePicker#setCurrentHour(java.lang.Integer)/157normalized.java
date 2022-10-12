@java.lang.Override
public android.app.Dialog onCreateDialog(android.os.Bundle savedInstanceState) {
    int minute = 0;
    dpStartDate.setIs24HourView(true);
    android.view.View _CVAR4 = view;
    void _CVAR5 = R.id.dpStartDate;
    final android.widget.TimePicker dpStartDate = ((android.widget.TimePicker) (_CVAR4.findViewById(_CVAR5)));
    // Use the current time as the default values for the picker
    final java.util.Calendar c = java.util.Calendar.getInstance();
    java.util.Calendar _CVAR7 = c;
    int _CVAR8 = java.util.Calendar.HOUR_OF_DAY;
    int hour = _CVAR7.get(_CVAR8);
    android.widget.TimePicker _CVAR6 = dpStartDate;
    int _CVAR9 = hour;
    _CVAR6.setCurrentHour(_CVAR9);
    dpStartDate.setCurrentMinute(minute);
    android.widget.NumberPicker startMinSpiner = getMinuteSpinner(dpStartDate);
    if (null != startMinSpiner) {
        startMinSpiner.setMinValue(0);
        startMinSpiner.setMaxValue(foodcenter.android.activities.coworkers.RangeTimePickerFragment.DISPLAYED_MINS.length - 1);
        startMinSpiner.setDisplayedValues(foodcenter.android.activities.coworkers.RangeTimePickerFragment.DISPLAYED_MINS);
    }
    dpEndDate.setIs24HourView(true);
     _CVAR0 = getActivity();
     _CVAR10 = _CVAR0;
    android.view.LayoutInflater inflater = ((android.view.LayoutInflater) (_CVAR10.getLayoutInflater()));
    android.view.LayoutInflater _CVAR1 = inflater;
    void _CVAR2 = R.layout.range_time_picker;
    <nulltype> _CVAR3 = null;
    android.view.LayoutInflater _CVAR11 = _CVAR1;
    void _CVAR12 = _CVAR2;
    <nulltype> _CVAR13 = _CVAR3;
    android.view.View view = _CVAR11.inflate(_CVAR12, _CVAR13);
    android.view.View _CVAR14 = view;
    void _CVAR15 = R.id.dpEndDate;
    final android.widget.TimePicker dpEndDate = ((android.widget.TimePicker) (_CVAR14.findViewById(_CVAR15)));
    int _CVAR17 = 1;
    android.widget.TimePicker _CVAR16 = dpEndDate;
    int _CVAR18 = hour + _CVAR17;
    _CVAR16.setCurrentHour(_CVAR18);
    dpEndDate.setCurrentMinute(minute);
    android.widget.NumberPicker endMinSpiner = getMinuteSpinner(dpEndDate);
    if (null != endMinSpiner) {
        endMinSpiner.setMinValue(0);
        endMinSpiner.setMaxValue(foodcenter.android.activities.coworkers.RangeTimePickerFragment.DISPLAYED_MINS.length - 1);
        endMinSpiner.setDisplayedValues(foodcenter.android.activities.coworkers.RangeTimePickerFragment.DISPLAYED_MINS);
    }
    // Build the dialog
    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
    builder.setView(view);// Set the view of the dialog to your custom layout

    builder.setTitle("Select start and end date");
    builder.setPositiveButton(R.string.reserve_button, new android.content.DialogInterface.OnClickListener() {
        @java.lang.Override
        public void onClick(android.content.DialogInterface dialog, int which) {
            startHr = dpStartDate.getCurrentHour();
            startMin = java.lang.Integer.parseInt(foodcenter.android.activities.coworkers.RangeTimePickerFragment.DISPLAYED_MINS[dpStartDate.getCurrentMinute()]);
            endHr = dpEndDate.getCurrentHour();
            endMin = java.lang.Integer.parseInt(foodcenter.android.activities.coworkers.RangeTimePickerFragment.DISPLAYED_MINS[dpEndDate.getCurrentMinute()]);
            listener.onReserveClick(RangeTimePickerFragment.this);
        }
    });
    builder.setNegativeButton(R.string.cancel, new android.content.DialogInterface.OnClickListener() {
        @java.lang.Override
        public void onClick(android.content.DialogInterface dialog, int which) {
            listener.onCancelClick(RangeTimePickerFragment.this);
        }
    });
    android.app.Dialog res = builder.create();
    res.setCanceledOnTouchOutside(false);
    return res;
}