package com.vinh.alarmclockandroid.activities;
import android.support.v7.app.AppCompatActivity;
import com.vinh.alarmclockandroid.R;
import com.vinh.alarmclockandroid.activities.AlarmReceiver;
import com.vinh.alarmclockandroid.database.QuizModel;
import com.vinh.alarmclockandroid.database_giobaothuc.DatabaseHandle;
import com.vinh.alarmclockandroid.database_giobaothuc.GioBaoThucModel;
public class MainActivity extends android.support.v7.app.AppCompatActivity implements android.view.View.OnClickListener {
    android.widget.Button btnStart;

    android.widget.Button btnStop;

    android.widget.TextView tvTimePicker;

    android.widget.TimePicker timePicker;

    java.util.Calendar calendar;

    android.app.AlarmManager alarmManager;

    android.widget.Spinner spSubject;

    android.widget.ImageView iv_acceptAlarm1;

    android.widget.ImageView iv_acceptAlarm2;

    android.widget.ImageView iv_backAlarm1;

    android.widget.ImageView iv_backAlarm2;

    android.content.Intent intent;

    android.content.Intent intentDatBaoThucThanhCong;

    private int topic;

    private com.vinh.alarmclockandroid.database_giobaothuc.GioBaoThucModel quizModel;

    java.util.List<com.vinh.alarmclockandroid.database_giobaothuc.GioBaoThucModel> quizList = new java.util.ArrayList<>();

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // DatabaseHandle.getInstance(this).insertTable();
        // Log.d("list", "onCreate: " + DatabaseHandle.getInstance(this).getListGioBaoThuc().size());
        loadUI();
        addSpiner();
        timePicker.setIs24HourView(true);
        calendar = java.util.Calendar.getInstance();
        // cho phep truy cap va he thong bao dong
        alarmManager = ((android.app.AlarmManager) (getSystemService(com.vinh.alarmclockandroid.activities.ALARM_SERVICE)));
        intent = new android.content.Intent(this, com.vinh.alarmclockandroid.activities.AlarmReceiver.class);
        btnStart.setOnClickListener(this);
        iv_acceptAlarm1.setOnClickListener(this);
        iv_acceptAlarm2.setOnClickListener(this);
        iv_backAlarm1.setOnClickListener(this);
        iv_backAlarm2.setOnClickListener(this);
        final android.app.DatePickerDialog.OnDateSetListener date = new android.app.DatePickerDialog.OnDateSetListener() {
            @java.lang.Override
            public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(java.util.Calendar.YEAR, year);
                calendar.set(java.util.Calendar.MONTH, month);
                calendar.set(java.util.Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        // intentDatBaoThucThanhCong = new Intent(getApplicationContext(), DatBaoThucThanhCong.class);
    }

    private void addSpiner() {
        final java.lang.String[] arrSubject = new java.lang.String[]{ "Java", "Đố vui", "IQ" };
        spSubject = ((android.widget.Spinner) (findViewById(R.id.spinner_topic)));
        android.widget.ArrayAdapter<java.lang.String> adapter = new android.widget.ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrSubject);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spSubject.setAdapter(adapter);
        spSubject.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @java.lang.Override
            public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                if (arrSubject[position].equals("Java")) {
                    topic = 1;
                } else if (arrSubject[position].equals("Đố vui")) {
                    topic = 2;
                } else {
                    topic = 3;
                    android.util.Log.d("topic", "topic: " + topic);
                }
                android.util.Log.d("topic", "topic: " + topic);
            }

            @java.lang.Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
                topic = 1;
            }
        });
    }

    private void setAlarmDate() {
        int ngaycach = 1;
        com.vinh.alarmclockandroid.activities.AlarmHandle.setAlarm(timePicker.getCurrentHour(), timePicker.getMinute(), this, ngaycach);
        android.util.Log.d("abcd", (("setAlarmDate: " + timePicker.getCurrentHour()) + " : ") + timePicker.getMinute());
    }

    private void updateLabel() {
        java.lang.String myFormat = "dd/MM/yy";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(myFormat, java.util.Locale.US);
    }

    private void loadUI() {
        btnStart = ((android.widget.Button) (findViewById(R.id.bt_start)));
        tvTimePicker = ((android.widget.TextView) (findViewById(R.id.tv_timePicker)));
        timePicker = ((android.widget.TimePicker) (findViewById(R.id.timepicker)));
        iv_acceptAlarm1 = ((android.widget.ImageView) (findViewById(R.id.bt_acceptAlarm1)));
        iv_acceptAlarm2 = ((android.widget.ImageView) (findViewById(R.id.bt_acceptAlarm2)));
        iv_backAlarm1 = ((android.widget.ImageView) (findViewById(R.id.bt_backAlarm1)));
        iv_backAlarm2 = ((android.widget.ImageView) (findViewById(R.id.bt_backAlarm2)));
        // btnStop = (Button) findViewById(R.id.bt_stop);
        quizList = com.vinh.alarmclockandroid.database_giobaothuc.DatabaseHandle.getInstance(this).getListGioBaoThuc();
        java.lang.String gio = "";
        java.lang.String phut = "";
        if (quizList.size() != 0) {
            gio = java.lang.String.valueOf(quizList.get(0).getHour());
            phut = java.lang.String.valueOf(quizList.get(0).getMinute());
            if (quizList.get(0).getHour() < 10) {
                gio = "0" + gio;
            }
            if (quizList.get(0).getMinute() < 10) {
                phut = "0" + phut;
            }
            if (quizList.size() != 0) {
                tvTimePicker.setText((gio + " : ") + phut);
            }
        }
    }

    @java.lang.Override
    public void onClick(android.view.View view) {
        switch (view.getId()) {
            case R.id.bt_start :
                calendar.set(java.util.Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                calendar.set(java.util.Calendar.MINUTE, timePicker.getCurrentMinute());
                int gio = timePicker.getCurrentHour();
                int phut = timePicker.getCurrentMinute();
                java.lang.String gio_sting = java.lang.String.valueOf(gio);
                java.lang.String phut_string = java.lang.String.valueOf(phut);
                if (phut < 10) {
                    phut_string = "0" + phut_string;
                }
                if (gio < 10) {
                    gio_sting = "0" + gio_sting;
                }
                // khac voi intent thuong la no se luon ton tai ke ca khi ung dung ket thuc
                tvTimePicker.setText((gio_sting + " : ") + phut_string);
                // toast ra man hinh
                // xu li lay time to wake up:
                int realHours = java.util.Calendar.getInstance().getTime().getHours();
                int realMinute = java.util.Calendar.getInstance().getTime().getMinutes();
                int realTime = (realHours * 60) + realMinute;
                int alarmTime = (gio * 60) + phut;
                int totalMinuteTimewakeup = alarmTime - realTime;
                if (totalMinuteTimewakeup < 0) {
                    totalMinuteTimewakeup += 24 * 60;
                }
                int hourTimewakeup = totalMinuteTimewakeup / 60;
                int minuteTimewakeup = totalMinuteTimewakeup % 60;
                java.lang.String hourTimewakeup_string = java.lang.String.valueOf(hourTimewakeup);
                java.lang.String minuteTimewakeup_string = java.lang.String.valueOf(minuteTimewakeup);
                if (hourTimewakeup < 10) {
                    hourTimewakeup_string = "0" + hourTimewakeup_string;
                }
                if (minuteTimewakeup < 10) {
                    minuteTimewakeup_string = "0" + minuteTimewakeup_string;
                }
                if (totalMinuteTimewakeup == 0) {
                    android.widget.Toast.makeText(this, "Time to Wake up: 24 hours!", android.widget.Toast.LENGTH_LONG).show();
                } else {
                    android.widget.Toast.makeText(this, ((("Time to Wake up: " + hourTimewakeup_string) + " hours, ") + minuteTimewakeup_string) + " minutes ", android.widget.Toast.LENGTH_SHORT).show();
                }
                // setAlarmDate();
                // DatabaseHandle.getInstance(this).deleteTable();
                // DatabaseHandle.getInstance(this).insertTable(timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 1, topic);
                // 
                // startActivity(intentDatBaoThucThanhCong);
                break;
                // case R.id.bt_stop:
                // DatabaseHandle.getInstance(this).deleteTable();
                // break;
            case R.id.bt_acceptAlarm1 :
            case R.id.bt_acceptAlarm2 :
                setAlarmDate();
                com.vinh.alarmclockandroid.database_giobaothuc.DatabaseHandle.getInstance(this).deleteTable();
                com.vinh.alarmclockandroid.database_giobaothuc.DatabaseHandle.getInstance(this).insertTable(timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 1, topic);
                super.onBackPressed();
                // startActivity(intentDatBaoThucThanhCong);
                break;
            case R.id.bt_backAlarm1 :
            case R.id.bt_backAlarm2 :
                super.onBackPressed();
                break;
            default :
                break;
        }
    }
}