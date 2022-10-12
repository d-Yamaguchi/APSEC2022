@java.lang.Override
public void onCreate(android.os.Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    details_array = getIntent().getStringArrayExtra("DETAILS");
    incoming_text = getIntent().getStringArrayExtra("FLOW_LEVEL_DETAILS");
    if (incoming_text[0].equals("HOUR")) {
        setContentView(R.layout.from_time_hour);
        android.widget.NumberPicker num_pick = ((android.widget.NumberPicker) (findViewById(R.id.to_hour_value)));
        num_pick.setMaxValue(24);
    } else {
        setContentView(R.layout.from_time);
    }
    android.app.ActionBar mActionBar = getActionBar();
    mActionBar.setDisplayShowHomeEnabled(false);
    mActionBar.setDisplayShowTitleEnabled(false);
    android.view.LayoutInflater mInflater = android.view.LayoutInflater.from(this);
    android.view.View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
    mActionBar.setCustomView(mCustomView);
    mActionBar.setDisplayShowCustomEnabled(true);
    timePicker = ((android.widget.TimePicker) (findViewById(R.id.timePicker1)));
    time_selected = ((android.widget.TextView) (findViewById(R.id.time_selected)));
    int _CVAR0 = (timePicker.getCurrentHour().intValue() > 12) ? timePicker.getCurrentHour().intValue() - 12 : timePicker.getCurrentHour().intValue();
    java.lang.String hour = java.lang.Integer.toString(_CVAR0);
    android.widget.TimePicker _CVAR1 = timePicker;
    java.lang.Integer _CVAR2 = _CVAR1.getCurrentHour();
    java.lang.String am_pm = get_am_pm(_CVAR2);
    int minute = timePicker.getCurrentMinute();
    if (hour.length() == 1) {
        hour = "0" + hour;
    }
    if (minute < 10) {
        min_str = "0" + java.lang.String.valueOf(minute);
    } else {
        min_str = java.lang.String.valueOf(minute);
    }
    time_selected.setText(((((" " + java.lang.String.valueOf(hour)) + ":") + min_str) + " ") + am_pm);
    details_array[1] = java.lang.String.valueOf(time_selected.getText());
    imageButton_from = ((android.widget.ImageView) (findViewById(R.id.imageButton_from)));
    addListenerOnButton();
}