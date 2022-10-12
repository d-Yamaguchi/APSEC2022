package alberto.hugo.ezzit;
import com.sunil.timepickerdemo.R;
public class EzzitActivity extends android.app.Activity {
    protected static final java.lang.String TAG = null;

    /**
     * Called when the activity is first created.
     */
    android.widget.TimePicker timepickerIn;

    android.widget.TimePicker timepickerOut;

    android.widget.TimePicker timepickerIn2;

    android.widget.TimePicker timepickerOut2;

    android.widget.DatePicker datePicker;

    android.widget.CheckBox exitNotification;

    @java.lang.Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ezzitmain);
        final android.widget.EditText editTxtWorkload = ((android.widget.EditText) (findViewById(R.id.editTxtWorkload)));
        editTxtWorkload.setText("8");
        timepickerIn = ((android.widget.TimePicker) (findViewById(R.id.timePickerIn)));
        timepickerIn.setDescendantFocusability(android.widget.TimePicker.FOCUS_BLOCK_DESCENDANTS);
        timepickerIn.setIs24HourView(true);
        timepickerIn.setCurrentHour(8);
        timepickerIn.setCurrentMinute(0);
        timepickerOut = ((android.widget.TimePicker) (findViewById(R.id.timePickerOut)));
        timepickerOut.setDescendantFocusability(android.widget.TimePicker.FOCUS_BLOCK_DESCENDANTS);
        timepickerOut.setIs24HourView(true);
        timepickerOut.setCurrentHour(12);
        timepickerOut.setCurrentMinute(0);
        timepickerIn2 = ((android.widget.TimePicker) (findViewById(R.id.timePickerIn2)));
        timepickerIn2.setDescendantFocusability(android.widget.TimePicker.FOCUS_BLOCK_DESCENDANTS);
        timepickerIn2.setIs24HourView(true);
        timepickerIn2.setCurrentHour(13);
        timepickerIn2.setCurrentMinute(0);
        timepickerOut2 = ((android.widget.TimePicker) (findViewById(R.id.timePickerOut2)));
        timepickerOut2.setDescendantFocusability(android.widget.TimePicker.FOCUS_BLOCK_DESCENDANTS);
        timepickerOut2.setIs24HourView(true);
        timepickerOut2.setCurrentMinute(0);
        timepickerOut2.setCurrentHour(0);
        timepickerOut2.setEnabled(false);
        exitNotification = ((android.widget.CheckBox) (findViewById(R.id.ckBoxExit)));
        // Button View
        android.widget.Button button = ((android.widget.Button) (findViewById(R.id.btn)));
        button.setOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View v) {
                java.lang.String workload = editTxtWorkload.getText().toString();
                if (workload.trim().length() == 0) {
                    android.widget.Toast.makeText(getBaseContext(), "Error: Without workload!", android.widget.Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    int workloadInt = java.lang.Integer.parseInt(workload);
                    int inHour = timepickerIn.getCurrentHour() * 60;
                    int inMin = timepickerIn.getCurrentMinute();
                    int outHour = timepickerOut.getCurrentHour() * 60;
                    int outMin = timepickerOut.getCurrentMinute();
                    int in2Hour = timepickerIn2.getCurrentHour() * 60;
                    int in2Min = timepickerIn2.getCurrentMinute();
                    int out2Hour;
                    int out2Min;
                    workloadInt = workloadInt * 60;
                    int workOut2 = -((((outHour + outMin) - (inHour + inMin)) - workloadInt) - (in2Hour + in2Min));
                    out2Hour = workOut2 / 60;
                    out2Min = workOut2 % 60;
                    if ((inHour > outHour) || (outHour > in2Hour))
                        android.widget.Toast.makeText(getBaseContext(), "Error: Problem with hours!", android.widget.Toast.LENGTH_SHORT).show();
                    else {
                        timepickerOut2.setCurrentHour(out2Hour);
                        timepickerOut2.setCurrentMinute(out2Min);
                    }
                    if (exitNotification.isChecked()) {
                        // ---use the AlarmManager to trigger an alarm---
                        android.app.AlarmManager alarmManager = ((android.app.AlarmManager) (getSystemService(android.content.Context.ALARM_SERVICE)));
                        java.sql.Date dat = new java.sql.Date(1);
                        java.util.Calendar now_calendar = java.util.Calendar.getInstance();
                        now_calendar.setTime(dat);
                        // ---sets the time for the alarm to trigger---
                        calendar.set(java.util.Calendar.HOUR_OF_DAY, timepickerOut2.getCurrentHour());
                        // ---get current date and time---
                        java.util.Calendar calendar = java.util.Calendar.getInstance();
                        calendar.set(java.util.Calendar.MINUTE, timepickerIn.getMinute());
                        calendar.set(java.util.Calendar.SECOND, 0);
                        if (calendar.before(now_calendar)) {
                            calendar.add(java.util.Calendar.DATE, 1);
                        }
                        android.content.Intent i = new android.content.Intent(EzzitActivity.this, alberto.hugo.ezzit.DisplayNotifications.class);
                        i.putExtra("NotifID", 1);
                        android.app.PendingIntent displayIntent = android.app.PendingIntent.getActivity(getBaseContext(), 0, i, android.app.PendingIntent.FLAG_UPDATE_CURRENT);
                        alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), displayIntent);
                    }
                }
            }
        });
    }
}