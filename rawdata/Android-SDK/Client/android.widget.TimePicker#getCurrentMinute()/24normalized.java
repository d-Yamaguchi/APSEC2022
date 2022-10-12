@java.lang.Override
protected void onCreate(android.os.Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_set_reminder);
    timePicker = ((android.widget.TimePicker) (findViewById(R.id.timepicker)));
    reminderBt1 = ((android.widget.Button) (findViewById(R.id.reminderbt1)));
    reminderBt2 = ((android.widget.Button) (findViewById(R.id.reminderbt2)));
    reminderBt3 = ((android.widget.Button) (findViewById(R.id.reminderbt3)));
    reminderBt4 = ((android.widget.Button) (findViewById(R.id.reminderbt4)));
    doneBt = ((android.widget.Button) (findViewById(R.id.doneBt)));
    // backBt.setVisibility(View.INVISIBLE);
    CurrentDay = java.util.Calendar.DAY_OF_MONTH;
    CurrentH = timePicker.getCurrentHour();
    android.widget.TimePicker _CVAR0 = timePicker;
    java.lang.Integer _CVAR1 = _CVAR0.getCurrentMinute();
    CurrentM = _CVAR1;
    dayChoice = getIntent().getStringExtra("dayChoice");
    ProName = getIntent().getStringExtra("ProName");
    userIdd = getIntent().getStringExtra("userId");
    startProhour = getIntent().getIntExtra("sHour", 0);
    startProminute = getIntent().getIntExtra("sMinute", 0);
    endProhour = getIntent().getIntExtra("eHour", 0);
    endProminute = getIntent().getIntExtra("eMinute", 0);
    startProDate = getIntent().getIntExtra("startProDate", 0);
    // Toast.makeText(this, "SProDate: "+startProDate, Toast.LENGTH_SHORT).show();
    // Toast.makeText(this, "Sh : "+startProhour, Toast.LENGTH_SHORT).show();
    // Toast.makeText(this, "Sm : "+startProminute, Toast.LENGTH_SHORT).show();
    // Toast.makeText(this, "Eh: "+endProhour, Toast.LENGTH_SHORT).show();
    // Toast.makeText(this, "Em: "+endProminute, Toast.LENGTH_SHORT).show();
    coming = getIntent().getStringExtra("coming");
    // Toast.makeText(this, "ProName: "+ProName, Toast.LENGTH_SHORT).show();
    // Toast.makeText(this, "Day ch: "+dayChoice, Toast.LENGTH_SHORT).show();
    reminderBt1.setOnClickListener(new android.view.View.OnClickListener() {
        @java.lang.Override
        public void onClick(android.view.View v) {
            AlarmCode = 1;
            getTimeFromCalendar(AlarmCode);
        }
    });
    reminderBt2.setOnClickListener(new android.view.View.OnClickListener() {
        @java.lang.Override
        public void onClick(android.view.View v) {
            if (remOneSel == 1) {
                AlarmCode = 2;
                getTimeFromCalendar(AlarmCode);
            } else {
                android.widget.Toast.makeText(setReminderAct.this, "Set Previous Reminder First..!", android.widget.Toast.LENGTH_SHORT).show();
            }
        }
    });
    reminderBt3.setOnClickListener(new android.view.View.OnClickListener() {
        @java.lang.Override
        public void onClick(android.view.View v) {
            if ((remOneSel == 1) && (remTwoSel == 1)) {
                AlarmCode = 3;
                getTimeFromCalendar(AlarmCode);
            } else {
                android.widget.Toast.makeText(setReminderAct.this, "Set Previous Reminder First..!", android.widget.Toast.LENGTH_SHORT).show();
            }
        }
    });
    reminderBt4.setOnClickListener(new android.view.View.OnClickListener() {
        @java.lang.Override
        public void onClick(android.view.View v) {
            if (((remOneSel == 1) && (remTwoSel == 1)) && (remThreeSel == 1)) {
                AlarmCode = 4;
                getTimeFromCalendar(AlarmCode);
            } else {
                android.widget.Toast.makeText(setReminderAct.this, "Set Previous Reminder First..!", android.widget.Toast.LENGTH_SHORT).show();
            }
        }
    });
    doneBt.setOnClickListener(new android.view.View.OnClickListener() {
        @java.lang.Override
        public void onClick(android.view.View v) {
            setArrTimeAndAlarm();
            isReminderSet = 1;
            android.content.Intent intent = new android.content.Intent();
            intent.putExtra("isReminderSet", isReminderSet);
            intent.putExtra("TimeList", alarmTimeArr);
            intent.putExtra("rem1H", rem1H);
            intent.putExtra("rem1M", rem1M);
            intent.putExtra("rem2H", rem2H);
            intent.putExtra("rem2M", rem2M);
            intent.putExtra("rem3H", rem3H);
            intent.putExtra("rem3M", rem3M);
            intent.putExtra("rem4H", rem4H);
            intent.putExtra("rem4M", rem4M);
            setResult(android.app.Activity.RESULT_OK, intent);
            finish();
        }
    });
}