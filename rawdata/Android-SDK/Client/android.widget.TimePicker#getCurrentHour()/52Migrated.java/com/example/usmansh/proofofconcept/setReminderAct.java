package com.example.usmansh.proofofconcept;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
public class setReminderAct extends android.support.v7.app.AppCompatActivity {
    android.widget.Button reminderBt1;

    android.widget.Button reminderBt2;

    android.widget.Button reminderBt3;

    android.widget.Button reminderBt4;

    android.widget.Button doneBt;

    android.widget.TimePicker timePicker;

    int AlarmCode = 0;

    int remOneSel;

    int remTwoSel;

    int remThreeSel;

    int remFourSel;

    int isReminderSet = 0;

    int rem1H = 00;

    int rem1M = 00;

    int rem2H = 00;

    int rem2M = 00;

    int rem3H = 00;

    int rem3M = 00;

    int rem4H = 00;

    int rem4M = 00;

    int CurrentH;

    int CurrentM;

    int CurrentDay;

    java.lang.String ProName;

    java.lang.String dayChoice;

    java.lang.String userIdd;

    java.lang.String coming;

    int year;

    int month;

    int day;

    int minut;

    int hour;

    int yearFinal;

    int monthFinal;

    int dayFinal;

    int minutFinal;

    int hourFinal;

    java.util.Calendar calendar;

    java.util.Calendar c;

    java.util.ArrayList<java.lang.Long> alarmTimeArr = new java.util.ArrayList<>();

    long Rem1Time = 0;

    long Rem2Time = 0;

    long Rem3Time = 0;

    long Rem4Time = 0;

    int startProDate;

    int startProhour;

    int endProhour;

    int startProminute;

    int endProminute;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        __SmPLUnsupported__(0).onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_reminder);
        timePicker = ((android.widget.TimePicker) (findViewById(R.id.timepicker)));
        reminderBt1 = ((android.widget.Button) (findViewById(R.id.reminderbt1)));
        reminderBt2 = ((android.widget.Button) (findViewById(R.id.reminderbt2)));
        reminderBt3 = ((android.widget.Button) (findViewById(R.id.reminderbt3)));
        reminderBt4 = ((android.widget.Button) (findViewById(R.id.reminderbt4)));
        doneBt = ((android.widget.Button) (findViewById(R.id.doneBt)));
        // backBt.setVisibility(View.INVISIBLE);
        CurrentDay = java.util.Calendar.DAY_OF_MONTH;
        CurrentH = timePicker.getHour();
        CurrentM = timePicker.getCurrentMinute();
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
        reminderBt1.setOnClickListener(__SmPLUnsupported__(1));
        reminderBt2.setOnClickListener(__SmPLUnsupported__(2));
        reminderBt3.setOnClickListener(__SmPLUnsupported__(3));
        reminderBt4.setOnClickListener(__SmPLUnsupported__(4));
        doneBt.setOnClickListener(__SmPLUnsupported__(5));
    }

    private void setArrTimeAndAlarm() {
        if (Rem1Time != 0) {
            alarmTimeArr.add(Rem1Time);
        }
        if (Rem2Time != 0) {
            alarmTimeArr.add(Rem2Time);
        }
        if (Rem3Time != 0) {
            alarmTimeArr.add(Rem3Time);
        }
        if (Rem4Time != 0) {
            alarmTimeArr.add(Rem4Time);
        }
        if (alarmTimeArr.size() > 0) {
            android.content.Intent intent = null;
            android.app.PendingIntent pendingIntent = null;
            android.app.AlarmManager alarmManager = ((android.app.AlarmManager) (getSystemService(android.content.Context.ALARM_SERVICE)));
            intent = new android.content.Intent(getApplicationContext(), com.example.usmansh.proofofconcept.MyAlarm1.class);
            intent.putExtra("key", 1);
            // Toast.makeText(this, "Reminder 1 is set", Toast.LENGTH_SHORT).show();
            intent.putExtra("userId", userIdd);
            intent.putExtra("ProName", ProName);
            intent.putExtra("TimeList", alarmTimeArr);
            intent.putExtra("arrIndex", 0);
            pendingIntent = android.app.PendingIntent.getBroadcast(this, 1, intent, android.app.PendingIntent.FLAG_CANCEL_CURRENT);
            assert alarmManager != null;
            if (android.os.Build.VERSION.SDK_INT >= 23) {
                android.widget.Toast.makeText(this, "arrSize: " + alarmTimeArr.size(), android.widget.Toast.LENGTH_SHORT).show();
                alarmManager.setExactAndAllowWhileIdle(android.app.AlarmManager.RTC_WAKEUP, alarmTimeArr.get(0), pendingIntent);
                // Toast.makeText(this, "setExactAndAllowWhileIdle", Toast.LENGTH_SHORT).show();
            } else {
                android.widget.Toast.makeText(this, "arrSize: " + alarmTimeArr.size(), android.widget.Toast.LENGTH_SHORT).show();
                alarmManager.setExact(android.app.AlarmManager.RTC_WAKEUP, alarmTimeArr.get(0), pendingIntent);
                // Toast.makeText(this, "setExact", Toast.LENGTH_SHORT).show();
            }
        } else {
            android.widget.Toast.makeText(this, "Your All Alarms are Empty..!", android.widget.Toast.LENGTH_SHORT).show();
        }
    }

    private void getTimeFromCalendar(int alarmCodee) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if ((dayChoice != null) && dayChoice.equals("MD")) {
                // Toast.makeText(this, "Multi-Day-1", Toast.LENGTH_SHORT).show();
                calendar.set(calendar.get(java.util.Calendar.YEAR), calendar.get(java.util.Calendar.MONTH), startProDate, timePicker.getHour(), timePicker.getMinute(), 0);
                setAlarm(calendar.getTimeInMillis(), alarmCodee);
                // Toast.makeText(this, "MD-1-Day: "+startProDate, Toast.LENGTH_SHORT).show();
            } else {
                // Toast.makeText(this, "Single-Day-1", Toast.LENGTH_SHORT).show();
                calendar.set(calendar.get(java.util.Calendar.YEAR), calendar.get(java.util.Calendar.MONTH), calendar.get(java.util.Calendar.DAY_OF_MONTH), timePicker.getHour(), timePicker.getMinute(), 0);
                if (timePicker.getHour() >= CurrentH) {
                    setAlarm(calendar.getTimeInMillis(), alarmCodee);
                } else {
                    android.widget.Toast.makeText(this, "Error: You are selecting wrong hour..!\nDon\'t Select  from the previous hours..!", android.widget.Toast.LENGTH_LONG).show();
                }
            }
        } else if ((dayChoice != null) && dayChoice.equals("MD")) {
            calendar.set(calendar.get(java.util.Calendar.YEAR), calendar.get(java.util.Calendar.MONTH), startProDate, timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
            setAlarm(calendar.getTimeInMillis(), alarmCodee);
            // Toast.makeText(this, "MD-2-Day: "+startProDate, Toast.LENGTH_SHORT).show();
        } else {
            calendar.set(calendar.get(java.util.Calendar.YEAR), calendar.get(java.util.Calendar.MONTH), calendar.get(java.util.Calendar.DAY_OF_MONTH), timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
            if (timePicker.getCurrentHour() >= CurrentH) {
                setAlarm(calendar.getTimeInMillis(), alarmCodee);
            } else {
                android.widget.Toast.makeText(this, "Error: You are selecting wrong hour..!\nDon\'t Select  from the previous hours..!", android.widget.Toast.LENGTH_LONG).show();
            }
        }
    }

    @android.annotation.SuppressLint("SetTextI18n")
    private void setAlarm(long timeInMillis, int alarmCodee) {
        if (alarmCodee == 1) {
            if ((dayChoice != null) && dayChoice.equals("MD")) {
                if ((timePicker.getCurrentHour() == startProhour) && (timePicker.getCurrentMinute() == startProminute)) {
                    android.widget.Toast.makeText(this, "Error: Rem1 Selectig time is equal to Start Project Time..!", android.widget.Toast.LENGTH_SHORT).show();
                } else {
                    reminderBt1.setText(((("Time Set: " + timePicker.getCurrentHour()) + ":") + timePicker.getCurrentMinute()) + "");
                    rem1H = timePicker.getCurrentHour();
                    rem1M = timePicker.getCurrentMinute();
                    Rem1Time = timeInMillis;
                    remOneSel = 1;
                    android.widget.Toast.makeText(this, "Reminder 1 is set", android.widget.Toast.LENGTH_SHORT).show();
                }
            } else if ((timePicker.getCurrentHour() == startProhour) && (timePicker.getCurrentMinute() == startProminute)) {
                android.widget.Toast.makeText(this, "Error: Rem1 Selectig time is equal to Start Project Time..!", android.widget.Toast.LENGTH_SHORT).show();
            } else {
                reminderBt1.setText(((("Time Set: " + timePicker.getCurrentHour()) + ":") + timePicker.getCurrentMinute()) + "");
                rem1H = timePicker.getCurrentHour();
                rem1M = timePicker.getCurrentMinute();
                Rem1Time = timeInMillis;
                remOneSel = 1;
                android.widget.Toast.makeText(this, "Reminder 1 is set", android.widget.Toast.LENGTH_SHORT).show();
            }
        } else if (alarmCodee == 2) {
            if ((dayChoice != null) && dayChoice.equals("MD")) {
                if (timeInMillis > Rem1Time) {
                    reminderBt2.setText(((("Time Set: " + timePicker.getCurrentHour()) + ":") + timePicker.getCurrentMinute()) + "");
                    rem2H = timePicker.getCurrentHour();
                    rem2M = timePicker.getCurrentMinute();
                    Rem2Time = timeInMillis;
                    android.widget.Toast.makeText(this, "Reminder 2 is set", android.widget.Toast.LENGTH_SHORT).show();
                    remTwoSel = 1;
                } else {
                    android.widget.Toast.makeText(this, "Rem2 Time must be greater than Rem1 Time..!", android.widget.Toast.LENGTH_SHORT).show();
                }
            } else if (timeInMillis > Rem1Time) {
                reminderBt2.setText(((("Time Set: " + timePicker.getCurrentHour()) + ":") + timePicker.getCurrentMinute()) + "");
                rem2H = timePicker.getCurrentHour();
                rem2M = timePicker.getCurrentMinute();
                Rem2Time = timeInMillis;
                android.widget.Toast.makeText(this, "Reminder 2 is set", android.widget.Toast.LENGTH_SHORT).show();
                remTwoSel = 1;
            } else {
                android.widget.Toast.makeText(this, "Rem2 Time must be greater than Rem1 Time..!", android.widget.Toast.LENGTH_SHORT).show();
            }
        } else if (alarmCodee == 3) {
            if ((dayChoice != null) && dayChoice.equals("MD")) {
                if (timeInMillis > Rem2Time) {
                    reminderBt3.setText(((("Time Set: " + timePicker.getCurrentHour()) + ":") + timePicker.getCurrentMinute()) + "");
                    rem3H = timePicker.getCurrentHour();
                    rem3M = timePicker.getCurrentMinute();
                    Rem3Time = timeInMillis;
                } else {
                    android.widget.Toast.makeText(this, "Rem3 Time must be greater than Rem2 Time..!", android.widget.Toast.LENGTH_SHORT).show();
                }
            } else if (timeInMillis > Rem2Time) {
                reminderBt3.setText(((("Time Set: " + timePicker.getCurrentHour()) + ":") + timePicker.getCurrentMinute()) + "");
                rem3H = timePicker.getCurrentHour();
                rem3M = timePicker.getCurrentMinute();
                Rem3Time = timeInMillis;
            } else {
                android.widget.Toast.makeText(this, "Rem3 Time must be greater than Rem2 Time..!", android.widget.Toast.LENGTH_SHORT).show();
            }
            remThreeSel = 1;
        } else if (alarmCodee == 4) {
            if ((dayChoice != null) && dayChoice.equals("MD")) {
                if (timeInMillis > Rem3Time) {
                    reminderBt4.setText(((("Time Set: " + timePicker.getCurrentHour()) + ":") + timePicker.getCurrentMinute()) + "");
                    rem4H = timePicker.getCurrentHour();
                    rem4M = timePicker.getCurrentMinute();
                    Rem4Time = timeInMillis;
                } else {
                    android.widget.Toast.makeText(this, "Rem4 Time must be greater than Rem3 Time..!", android.widget.Toast.LENGTH_SHORT).show();
                }
            } else if (timeInMillis > Rem3Time) {
                reminderBt4.setText(((("Time Set: " + timePicker.getCurrentHour()) + ":") + timePicker.getCurrentMinute()) + "");
                rem4H = timePicker.getCurrentHour();
                rem4M = timePicker.getCurrentMinute();
                Rem4Time = timeInMillis;
            } else {
                android.widget.Toast.makeText(this, "Rem4 Time must be greater than Rem3 Time..!", android.widget.Toast.LENGTH_SHORT).show();
            }
            remFourSel = 1;
        }
    }

    @java.lang.Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
        if ((coming != null) && coming.equalsIgnoreCase("ProSet")) {
            finish();
        } else {
            android.widget.Toast.makeText(this, "Press the Done button..!", android.widget.Toast.LENGTH_SHORT).show();
        }
    }
}