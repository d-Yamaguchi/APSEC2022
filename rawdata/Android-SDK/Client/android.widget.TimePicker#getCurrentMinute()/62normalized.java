@java.lang.Override
protected void onCreate(android.os.Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // Get Views
    setContentView(R.layout.activity_add_event);
    detailsEditText = ((android.widget.EditText) (findViewById(R.id.detailsEditView)));
    datePicker = ((android.widget.DatePicker) (findViewById(R.id.datePicker)));
    timePicker = ((android.widget.TimePicker) (findViewById(R.id.timePicker)));
    dateView = ((android.widget.LinearLayout) (findViewById(R.id.dateView)));
    timeView = ((android.widget.LinearLayout) (findViewById(R.id.timeView)));
    addButton = ((android.widget.Button) (findViewById(R.id.registerButton)));
    openDateButton = ((android.widget.Button) (findViewById(R.id.openDateButton)));
    openTimeButton = ((android.widget.Button) (findViewById(R.id.openTimeButton)));
    dateButton = ((android.widget.Button) (findViewById(R.id.dateButton)));
    timeButton = ((android.widget.Button) (findViewById(R.id.timeButton)));
    // Set Min Date possible to today
    datePicker.setMinDate(new java.util.Date().getTime());
    int id = -1;
    // Check if event must be created or edited
    if ((getIntent() != null) && (getIntent().getExtras() != null)) {
        id = getIntent().getExtras().getInt("id", -1);
    }
    if (id == (-1)) {
        // Create new Event
        java.lang.String details = detailsEditText.getText().toString();
        tmp.setMonth(datePicker.getMonth());
        tmp.setYear(datePicker.getYear());
        tmp.setDate(datePicker.getDayOfMonth());
        java.util.Date tmp = new java.util.Date();
        android.widget.TimePicker _CVAR1 = timePicker;
        java.util.Date _CVAR0 = tmp;
        java.lang.Integer _CVAR2 = _CVAR1.getCurrentMinute();
        _CVAR0.setMinutes(_CVAR2);
        tmp.setHours(timePicker.getCurrentHour());
        android.widget.TimePicker _CVAR3 = timePicker;
        java.lang.Integer _CVAR4 = _CVAR3.getCurrentMinute();
        java.lang.String date = (((((((datePicker.getYear() + "/") + (datePicker.getMonth() + 1)) + "/") + datePicker.getDayOfMonth()) + " ") + timePicker.getCurrentHour()) + ":") + _CVAR4;
        event = new com.shirco.calendar.Event(date, details, tmp);
    } else {
        // Get Existing Event
        event = com.shirco.calendar.Singleton.getInstance().getEvents().get(id);
        timePicker.setCurrentMinute(event.getDate().getMinutes());
        timePicker.setCurrentHour(event.getDate().getHours());
        datePicker.updateDate(event.getDate().getYear(), event.getDate().getMonth(), event.getDate().getDate());
    }
    // Set base text of details, time and date based on Event info
    detailsEditText.setText(event.getDescription());
    android.widget.TimePicker _CVAR6 = timePicker;
    java.lang.Integer _CVAR7 = _CVAR6.getCurrentMinute();
    android.widget.Button _CVAR5 = openTimeButton;
    java.lang.String _CVAR8 = (timePicker.getCurrentHour() + ":") + _CVAR7;
    _CVAR5.setText(_CVAR8);
    openDateButton.setText((((datePicker.getYear() + "/") + (datePicker.getMonth() + 1)) + "/") + datePicker.getDayOfMonth());
    // Click Listeners for buttons
    openDateButton.setOnClickListener(new android.view.View.OnClickListener() {
        @java.lang.Override
        public void onClick(android.view.View v) {
            dateView.setVisibility(android.view.View.VISIBLE);
        }
    });
    openTimeButton.setOnClickListener(new android.view.View.OnClickListener() {
        @java.lang.Override
        public void onClick(android.view.View v) {
            timeView.setVisibility(android.view.View.VISIBLE);
        }
    });
    android.widget.Button _CVAR9 = timeButton;
    android.view.View.OnClickListener _CVAR10 = new android.view.View.OnClickListener() {
        @java.lang.Override
        public void onClick(android.view.View v) {
            timeView.setVisibility(android.view.View.GONE);
            openTimeButton.setText((timePicker.getCurrentHour() + ":") + timePicker.getCurrentMinute());
        }
    };
    _CVAR9.setOnClickListener(_CVAR10);
    dateButton.setOnClickListener(new android.view.View.OnClickListener() {
        @java.lang.Override
        public void onClick(android.view.View v) {
            dateView.setVisibility(android.view.View.GONE);
            openDateButton.setText((((datePicker.getYear() + "/") + (datePicker.getMonth() + 1)) + "/") + datePicker.getDayOfMonth());
        }
    });
    android.widget.Button _CVAR11 = addButton;
    android.view.View.OnClickListener _CVAR12 = new android.view.View.OnClickListener() {
        @java.lang.Override
        public void onClick(android.view.View v) {
            // Save event infos into tmp variables
            java.lang.String details = detailsEditText.getText().toString();
            java.util.Date tmp = new java.util.Date();
            tmp.setMonth(datePicker.getMonth());
            tmp.setYear(datePicker.getYear());
            tmp.setDate(datePicker.getDayOfMonth());
            tmp.setMinutes(timePicker.getCurrentMinute());
            tmp.setHours(timePicker.getCurrentHour());
            java.lang.String date = (((((((datePicker.getYear() + "/") + (datePicker.getMonth() + 1)) + "/") + datePicker.getDayOfMonth()) + " ") + timePicker.getCurrentHour()) + ":") + timePicker.getCurrentMinute();
            // Set event infos
            event.setDate(tmp);
            event.setDescription(details);
            event.setStrDate(date);
            // If event doesn't exist, add to Events list
            if (!com.shirco.calendar.Singleton.getInstance().getEvents().contains(event)) {
                com.shirco.calendar.Singleton.getInstance().getEvents().add(event);
            }
            com.shirco.calendar.Singleton.getInstance().sortEvents();
            onBackPressed();
        }
    };
    _CVAR11.setOnClickListener(_CVAR12);
}