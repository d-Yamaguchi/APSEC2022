@java.lang.Override
protected void onCreate(android.os.Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_alarm_creator);
    timePicker = ((android.widget.TimePicker) (findViewById(R.id.timePicker)));
    int hour = timePicker.getCurrentHour();
    android.widget.TimePicker _CVAR0 = timePicker;
    int minute = _CVAR0.getCurrentMinute();
    findViewById(R.id.creationMainLayout).requestFocus();
    android.widget.Button cancelCreationButton = ((android.widget.Button) (findViewById(R.id.cancelCreationButton)));
    mEditText = ((android.widget.EditText) (findViewById(R.id.nameInput)));
    void _CVAR1 = R.id.creationButton;
    android.widget.Button creationButton = ((android.widget.Button) (findViewById(_CVAR1)));
    android.widget.Button _CVAR2 = creationButton;
    android.view.View.OnClickListener _CVAR3 = new android.view.View.OnClickListener() {
        @java.lang.Override
        public void onClick(android.view.View view) {
            android.app.PendingIntent id = setAlarm(timePicker.getCurrentHour(), timePicker.getCurrentMinute());
            prepareIntent(id);
            finish();
        }
    };
    _CVAR2.setOnClickListener(_CVAR3);
    cancelCreationButton.setOnClickListener(new android.view.View.OnClickListener() {
        @java.lang.Override
        public void onClick(android.view.View view) {
            finish();
        }
    });
    // Create a new PendingIntent and add it to the AlarmManager
}