@java.lang.Override
protected void onCreate(android.os.Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create1);
    final android.content.Intent intent = getIntent();
    name = intent.getStringExtra("eventTitle");
    description = intent.getStringExtra("description");
    files = intent.getStringExtra("files");
    button = ((android.widget.Button) (findViewById(R.id.button1)));
    button.setOnClickListener(this);
    calendarView = ((android.widget.CalendarView) (findViewById(R.id.calendarView1)));
    calendarView.setDate(java.util.Calendar.getInstance().getTimeInMillis(), false, true);
    calendarView.setOnDateChangeListener(new android.widget.CalendarView.OnDateChangeListener() {
        long selected = calendarView.getDate();

        @java.lang.Override
        public void onSelectedDayChange(android.widget.CalendarView view, int year, int month, int dayOfMonth) {
            // Toast.makeText(getApplicationContext(), ""+selected, 0).show();// TODO Auto-generated method stub
            // System.out.println(String.valueOf(year));
            // System.out.println(String.valueOf(month));
            // System.out.println(String.valueOf(dayOfMonth));
            if (selected != view.getDate()) {
                selected = view.getDate();
                month = month + 1;
                calendar = (((month + "A") + dayOfMonth) + "A") + year;
            }
        }
    });
    java.text.DateFormat dateFormat1 = new java.text.SimpleDateFormat("MM");
    java.text.DateFormat dateFormat2 = new java.text.SimpleDateFormat("dd");
    java.text.DateFormat dateFormat3 = new java.text.SimpleDateFormat("yyyy");
    java.util.Date date = new java.util.Date();
    java.lang.String month = dateFormat1.format(date);
    java.lang.String day = dateFormat2.format(date);
    java.lang.String year = dateFormat3.format(date);
    if (month.charAt(0) == '0') {
        month = "" + month.charAt(1);
    }
    if (day.charAt(0) == '0') {
        day = "" + day.charAt(1);
    }
    calendar = (((month + "A") + day) + "A") + year;
    java.lang.System.out.println(calendar);
    timePicker = ((android.widget.TimePicker) (findViewById(R.id.timePicker1)));
    int hour = timePicker.getCurrentHour();
    android.widget.TimePicker _CVAR0 = timePicker;
    int min = _CVAR0.getCurrentMinute();
    android.widget.TimePicker _CVAR1 = timePicker;
    android.widget.TimePicker.OnTimeChangedListener _CVAR2 = new android.widget.TimePicker.OnTimeChangedListener() {
        int hour = timePicker.getCurrentHour();

        int min = timePicker.getCurrentMinute();

        public void onTimeChanged(android.widget.TimePicker view, int hourOfDay, int minute) {
            if ((hour != timePicker.getCurrentHour()) || (min != timePicker.getCurrentMinute())) {
                hour = timePicker.getCurrentHour();
                min = timePicker.getCurrentMinute();
                time = (hour + ":") + min;
            }
        }
    };
    _CVAR1.setOnTimeChangedListener(_CVAR2);
    time = (hour + ":") + min;
    // dao.writeFile(name, description, calendar, time, files);
}