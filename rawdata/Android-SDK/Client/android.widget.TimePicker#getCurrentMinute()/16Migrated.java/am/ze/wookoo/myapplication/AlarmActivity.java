package am.ze.wookoo.myapplication;
import android.support.v7.app.AppCompatActivity;
public class AlarmActivity extends android.support.v7.app.AppCompatActivity {
    public static android.app.AlarmManager alarm_manager;

    android.widget.TimePicker alarm_timepicker;

    android.content.Context context;

    public static android.app.PendingIntent pendingIntent;

    public static android.content.Intent my_intent;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        alarm_timepicker = findViewById(R.id.picker_date);
        alarm_timepicker.setDescendantFocusability(android.widget.TimePicker.FOCUS_BLOCK_DESCENDANTS);
        this.context = this;
        am.ze.wookoo.myapplication.AlarmActivity.alarm_manager = ((android.app.AlarmManager) (getSystemService(am.ze.wookoo.myapplication.ALARM_SERVICE)));
        final java.util.Calendar calendar = java.util.Calendar.getInstance();
        am.ze.wookoo.myapplication.AlarmActivity.my_intent = new android.content.Intent(this.context, am.ze.wookoo.myapplication.Alarm_Reciver.class);
        java.util.ArrayList<android.widget.Button> week_btns = new java.util.ArrayList<android.widget.Button>();
        week_btns.add(((android.widget.Button) (findViewById(R.id.alarm_sun))));
        week_btns.add(((android.widget.Button) (findViewById(R.id.alarm_mon))));
        week_btns.add(((android.widget.Button) (findViewById(R.id.alarm_tue))));
        week_btns.add(((android.widget.Button) (findViewById(R.id.alarm_wen))));
        week_btns.add(((android.widget.Button) (findViewById(R.id.alarm_thu))));
        week_btns.add(((android.widget.Button) (findViewById(R.id.alarm_fri))));
        week_btns.add(((android.widget.Button) (findViewById(R.id.alarm_sat))));
        for (final android.widget.Button btn : week_btns) {
            final android.graphics.Typeface type = btn.getTypeface();
            btn.setOnClickListener(new android.view.View.OnClickListener() {
                @java.lang.Override
                public void onClick(android.view.View v) {
                    if (btn.isSelected()) {
                        btn.setSelected(false);
                        btn.setTypeface(type, android.graphics.Typeface.BOLD);
                    } else {
                        btn.setSelected(true);
                        btn.setTypeface(type, android.graphics.Typeface.NORMAL);
                    }
                }
            });
        }
        android.widget.Button save_btn = findViewById(R.id.alarm_save);
        save_btn.setOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View v) {
                calendar.set(java.util.Calendar.HOUR_OF_DAY, alarm_timepicker.getCurrentHour());
                final java.util.Calendar calendar = java.util.Calendar.getInstance();
                calendar.set(java.util.Calendar.MINUTE, alarm_timepicker.getMinute());
                calendar.set(java.util.Calendar.SECOND, 0);
                int hour = alarm_timepicker.getCurrentHour();
                int minute = alarm_timepicker.getCurrentMinute();
                android.widget.Toast.makeText(AlarmActivity.this, ((hour + "시 ") + minute) + "분 에 알람이 울립니다.", android.widget.Toast.LENGTH_SHORT).show();
                am.ze.wookoo.myapplication.AlarmActivity.my_intent.putExtra("state", "alarm on");
                am.ze.wookoo.myapplication.AlarmActivity.pendingIntent = android.app.PendingIntent.getBroadcast(AlarmActivity.this, 0, am.ze.wookoo.myapplication.AlarmActivity.my_intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT);
                am.ze.wookoo.myapplication.AlarmActivity.alarm_manager.set(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), am.ze.wookoo.myapplication.AlarmActivity.pendingIntent);
                android.content.Intent mIntent = new android.content.Intent(AlarmActivity.this, am.ze.wookoo.myapplication.RecordActivity.class);
                java.lang.StringBuffer sb = new java.lang.StringBuffer();
                sb.append("UPDATE user SET ALARM = ");
                java.lang.String data;
                if (alarm_timepicker.getCurrentHour() > 12) {
                    data = java.lang.String.format(" Alarm %d : %02d PM", alarm_timepicker.getCurrentHour() - 12, alarm_timepicker.getMinute());
                } else if (alarm_timepicker.getCurrentHour() == 12) {
                    data = java.lang.String.format(" Alarm %d : %02d PM", alarm_timepicker.getCurrentHour(), alarm_timepicker.getMinute());
                } else if (alarm_timepicker.getCurrentHour() == 0) {
                    data = java.lang.String.format(" Alarm %d : %02d AM", 12, alarm_timepicker.getMinute());
                } else {
                    data = java.lang.String.format(" Alarm %d : %02d AM", alarm_timepicker.getCurrentHour(), alarm_timepicker.getMinute());
                }
                startActivity(mIntent);
                finish();
            }
        });
    }
}