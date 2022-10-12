@java.lang.Override
public void onCreate(android.os.Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.from_time);
    to_time = ((android.widget.TextView) (findViewById(R.id.start_time_q)));
    to_time.setText("On what time would you want the service to end?");
    details_array = getIntent().getStringArrayExtra("DETAILS");
    incoming_text = getIntent().getStringArrayExtra("FLOW_LEVEL_DETAILS");
    startTime = details_array[1].trim();
    try {
        d = sdf.parse(startTime);
    } catch (java.text.ParseException e) {
        e.printStackTrace();
    }
    maxTime = d.getTime() + (1 * 24);
    to = ((android.widget.TextView) (findViewById(R.id.from_text)));
    to.setText("To: ");
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
    try {
        d = sdf.parse(java.lang.String.valueOf(time_selected.getText()).trim());
    } catch (java.text.ParseException e) {
        e.printStackTrace();
    }
    if (d.getTime() > maxTime) {
        android.widget.Toast.makeText(this, "Time of service is exceeding 24 Hrs", android.widget.Toast.LENGTH_SHORT).show();
    } else {
        details_array[3] = java.lang.String.valueOf(time_selected.getText());
    }
    imageButton_from = ((android.widget.ImageView) (findViewById(R.id.imageButton_from)));
    addListenerOnButton();
}