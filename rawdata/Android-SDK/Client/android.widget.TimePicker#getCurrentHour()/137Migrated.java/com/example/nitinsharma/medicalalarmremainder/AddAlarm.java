package com.example.nitinsharma.medicalalarmremainder;
import android.support.v7.app.AppCompatActivity;
public class AddAlarm extends android.support.v7.app.AppCompatActivity {
    android.widget.TimePicker timePicker;

    android.widget.ImageButton back;

    android.widget.Button done;

    android.widget.EditText title;

    java.lang.String alarmTitle;

    android.database.sqlite.SQLiteDatabase database;

    com.example.nitinsharma.medicalalarmremainder.AlarmDatabase alarmDatabase;

    android.app.AlarmManager alarmManager;

    android.app.PendingIntent pendingIntent;

    com.example.nitinsharma.medicalalarmremainder.List list;

    int position;

    private java.util.Calendar calendar;

    android.content.Intent i;

    boolean update;

    java.util.ArrayList<com.example.nitinsharma.medicalalarmremainder.Data> arrayList;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.view.Window window = getWindow();
        android.graphics.drawable.Drawable background = getResources().getDrawable(R.drawable.bg);
        window.addFlags(android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
        window.setNavigationBarColor(getResources().getColor(android.R.color.transparent));
        window.setBackgroundDrawable(background);
        setContentView(R.layout.activity_add_alarm);
        timePicker = findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        back = findViewById(R.id.back);
        done = findViewById(R.id.done);
        arrayList = new java.util.ArrayList<>();
        list = new com.example.nitinsharma.medicalalarmremainder.List(getBaseContext(), arrayList);
        alarmDatabase = new com.example.nitinsharma.medicalalarmremainder.AlarmDatabase(getApplicationContext(), "Alarm Database", null, 1);
        database = alarmDatabase.getWritableDatabase();
        database = alarmDatabase.getReadableDatabase();
        alarmManager = ((android.app.AlarmManager) (getSystemService(android.content.Context.ALARM_SERVICE)));
        back.setOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View v) {
                com.example.nitinsharma.medicalalarmremainder.AddAlarm.super.onBackPressed();
            }
        });
        android.content.Intent i = getIntent();
        if (((i.hasExtra("title") && i.hasExtra("hour")) && i.hasExtra("minute")) && i.hasExtra("position")) {
            android.util.Log.v("intent", "hit");
            title.setText(i.getStringExtra("title").trim());
            timePicker.setCurrentHour(i.getIntExtra("hour", 0));
            timePicker.setCurrentMinute(i.getIntExtra("minute", 0));
            position = i.getIntExtra("position", 0);
            update = true;
        }
        done.setOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View v) {
                alarmTitle = title.getText().toString().trim();
                if (alarmTitle.isEmpty()) {
                    title.setError("Pls Enter Title!!");
                } else {
                    calendar = java.util.Calendar.getInstance();
                    calendar.set(java.util.Calendar.HOUR_OF_DAY, timePicker.getHour());
                    calendar.set(java.util.Calendar.MINUTE, timePicker.getCurrentMinute());
                    calendar.set(java.util.Calendar.SECOND, 0);
                    calendar.set(java.util.Calendar.MILLISECOND, 0);
                    long milliseconds = java.util.concurrent.TimeUnit.SECONDS.toMillis(java.util.concurrent.TimeUnit.HOURS.toSeconds(timePicker.getHour()) + java.util.concurrent.TimeUnit.MINUTES.toSeconds(timePicker.getCurrentMinute()));
                    android.util.Log.v("millisecond", (calendar.getTimeInMillis() + "M") + milliseconds);
                    long ab = calendar.getTimeInMillis();
                    int seconds = ((int) (milliseconds / 1000)) % 60;
                    int minutes = ((int) ((milliseconds / (1000 * 60)) % 60));
                    int hours = ((int) ((milliseconds / ((1000 * 60) * 60)) % 24));
                    android.util.Log.v("sec", "" + seconds);
                    android.util.Log.v("min", "" + minutes);
                    android.util.Log.v("hours", "" + hours);
                    if (update) {
                        values.put("Name", alarmTitle);
                        android.content.ContentValues values = new android.content.ContentValues();
                        values.put("Hour", timePicker.getHour());
                        values.put("Minute", timePicker.getCurrentMinute());
                        database.update("Alarm", values, "Column_Id=" + position, null);
                        android.util.Log.v("update", "" + position);
                    } else {
                        values.put("Name", alarmTitle);
                        android.content.ContentValues values = new android.content.ContentValues();
                        values.put("Hour", timePicker.getHour());
                        values.put("Minute", timePicker.getCurrentMinute());
                        values.put("Status", "true");
                        values.put("Time_Status", "true");
                        values.put("Title_Status", "true");
                        database.insert("Alarm", null, values);
                    }
                    android.content.Intent j = new android.content.Intent(getBaseContext(), com.example.nitinsharma.medicalalarmremainder.AlarmReceiver.class);
                    pendingIntent = android.app.PendingIntent.getBroadcast(getBaseContext(), 1, j, android.app.PendingIntent.FLAG_UPDATE_CURRENT);
                    if (android.os.Build.VERSION.SDK_INT >= 23) {
                        alarmManager.setExactAndAllowWhileIdle(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    } else if (android.os.Build.VERSION.SDK_INT >= 19) {
                        alarmManager.setExact(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    } else {
                        alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    }
                    if (update) {
                        o.putExtra("Title", alarmTitle);
                        android.content.Intent o = new android.content.Intent();
                        o.putExtra("Hour", timePicker.getHour());
                        o.putExtra("Min", timePicker.getCurrentMinute());
                        setResult(101, o);
                        finish();
                    } else {
                        f.putExtra("Title", alarmTitle);
                        android.content.Intent f = new android.content.Intent();
                        f.putExtra("Hour", timePicker.getHour());
                        f.putExtra("Min", timePicker.getCurrentMinute());
                        setResult(com.example.nitinsharma.medicalalarmremainder.RESULT_OK, f);
                        finish();
                    }
                }
            }
        });
        list.onItemclick(new List.onClick() {
            @java.lang.Override
            public void switchClick(int pos, int bc, boolean ab) {
                if (ab) {
                    /* Intent  i = new Intent(getBaseContext(), AlarmReceiver.class);
                    pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 1, i, 0);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                     */
                } else {
                    android.util.Log.v("cancel", "hit");
                    /* alarmManager.cancel(pendingIntent); */
                }
            }

            @java.lang.Override
            public void itemClick(int pos) {
            }
        });
    }

    public void setIntent() {
        i = new android.content.Intent(getBaseContext(), com.example.nitinsharma.medicalalarmremainder.AlarmReceiver.class);
        pendingIntent = android.app.PendingIntent.getBroadcast(getBaseContext(), 1, i, 0);
        alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}