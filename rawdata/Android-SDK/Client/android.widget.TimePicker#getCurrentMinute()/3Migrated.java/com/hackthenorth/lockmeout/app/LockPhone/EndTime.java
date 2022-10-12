package com.hackthenorth.lockmeout.app.LockPhone;
import com.hackthenorth.lockmeout.app.R;
import com.hackthenorth.lockmeout.app.Timer.TimerService;
/**
 * Created by Berries on 2014-09-19.
 */
public class EndTime extends com.hackthenorth.lockmeout.app.LockPhone.LockPhoneFragment {
    android.widget.RelativeLayout layout;

    private com.hackthenorth.lockmeout.app.LockPhone.EndTime.OnLockSelectedListener listener;

    private int hour;

    private int minute;

    private int day;

    private int month;

    public long startTime;

    public android.content.Context ctx;

    public java.lang.String startTimeFile = "com.hackthenorth.lockmeout.startTime";

    public java.lang.String endTimeFile = "com.hackthenorth.lockmeout.endTime";

    public android.content.SharedPreferences prefs;

    private android.widget.TimePicker timePicker;

    private android.widget.DatePicker datePicker;

    private android.widget.Button nextButton;

    private android.app.AlertDialog.Builder pickDateDialog;

    private android.app.AlertDialog.Builder pickTimeDialog;

    private android.view.View timeLayout;

    private android.view.View dateLayout;

    private android.widget.Button timeBtnDialog;

    private android.widget.Button dateBtnDialog;

    @java.lang.Override
    public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        android.view.View view = ((android.view.View) (inflater.inflate(R.layout.fragment_endtime, container, false)));
        prefs = ctx.getSharedPreferences("com.hackthenorth.lockmeout", android.content.Context.MODE_PRIVATE);
        if (timeLayout == null) {
            timeLayout = inflater.inflate(R.layout.time_dialog, null);
        }
        if (dateLayout == null) {
            dateLayout = inflater.inflate(R.layout.date_dialog, null);
        }
        pickTimeDialog.setView(timeLayout);
        pickDateDialog.setView(dateLayout);
        datePicker = ((android.widget.DatePicker) (dateLayout.findViewById(R.id.date_picker)));
        timePicker = ((android.widget.TimePicker) (timeLayout.findViewById(R.id.time_picker)));
        try {
            java.lang.reflect.Field[] f = datePicker.getClass().getDeclaredFields();
            for (java.lang.reflect.Field field : f) {
                if (field.getName().equals("mYearPicker") || field.getName().equals("mYearSpinner")) {
                    field.setAccessible(true);
                    java.lang.Object yearPicker = new java.lang.Object();
                    yearPicker = field.get(datePicker);
                    ((android.view.View) (yearPicker)).setVisibility(android.view.View.GONE);
                }
            }
        } catch (java.lang.SecurityException e) {
            android.util.Log.d("ERROR", e.getMessage());
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Log.d("ERROR", e.getMessage());
        } catch (java.lang.IllegalAccessException e) {
            android.util.Log.d("ERROR", e.getMessage());
        }
        android.widget.Button setDateBtn = ((android.widget.Button) (view.findViewById(R.id.btn_set_date_end)));
        setDateBtn.setOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View view) {
                pickDateDialog.show();
            }
        });
        android.widget.Button setTimeBtn = ((android.widget.Button) (view.findViewById(R.id.btn_set_time_end)));
        setTimeBtn.setOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View view) {
                pickTimeDialog.show();
            }
        });
        android.widget.Button backButton = ((android.widget.Button) (view.findViewById(R.id.back_button)));
        backButton.setOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View v) {
                saveClicked(timePicker.getMinute(), timePicker.getCurrentHour(), datePicker.getDayOfMonth(), datePicker.getMonth(), "back");
                saveClicked(minute, hour, day, month, "back");
            }
        });
        android.widget.Button lockButton = ((android.widget.Button) (view.findViewById(R.id.lock_button)));
        lockButton.setOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View v) {
                saveClicked(minute, hour, day, month, "");
                android.widget.Toast.makeText(ctx, "TESTING", android.widget.Toast.LENGTH_LONG).show();
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                java.util.Calendar cl = java.util.Calendar.getInstance();
                java.util.Calendar cl2 = java.util.Calendar.getInstance();
                switch (month) {
                    case 0 :
                        calendar.set(datePicker.getYear(), java.util.Calendar.JANUARY, datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                        break;
                    case 1 :
                        calendar.set(datePicker.getYear(), java.util.Calendar.FEBRUARY, datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                        break;
                    case 2 :
                        calendar.set(datePicker.getYear(), java.util.Calendar.MARCH, datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                        break;
                    case 3 :
                        calendar.set(datePicker.getYear(), java.util.Calendar.APRIL, datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                        break;
                    case 4 :
                        calendar.set(datePicker.getYear(), java.util.Calendar.MAY, datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                        break;
                    case 5 :
                        calendar.set(datePicker.getYear(), java.util.Calendar.JUNE, datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                        break;
                    case 6 :
                        calendar.set(datePicker.getYear(), java.util.Calendar.JULY, datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                        break;
                    case 7 :
                        calendar.set(datePicker.getYear(), java.util.Calendar.AUGUST, datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                        break;
                    case 8 :
                        calendar.set(datePicker.getYear(), java.util.Calendar.SEPTEMBER, datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                        break;
                    case 9 :
                        calendar.set(datePicker.getYear(), java.util.Calendar.OCTOBER, datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                        break;
                    case 10 :
                        calendar.set(datePicker.getYear(), java.util.Calendar.NOVEMBER, datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                        break;
                    case 11 :
                        calendar.set(datePicker.getYear(), java.util.Calendar.DECEMBER, datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                        break;
                    default :
                        break;
                }
                long endTime = calendar.getTimeInMillis();
                android.util.Log.d("endTime", "The very first endTIme: " + endTime);
                long startTime = prefs.getLong(startTimeFile, 0);
                if (endTime < startTime) {
                    switch (datePicker.getMonth()) {
                        case 0 :
                            calendar.set(datePicker.getYear() + 1, java.util.Calendar.JANUARY, datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                            break;
                        case 1 :
                            calendar.set(datePicker.getYear() + 1, java.util.Calendar.FEBRUARY, datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                            break;
                        case 2 :
                            calendar.set(datePicker.getYear() + 1, java.util.Calendar.MARCH, datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                            break;
                        case 3 :
                            calendar.set(datePicker.getYear() + 1, java.util.Calendar.APRIL, datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                            break;
                        case 4 :
                            calendar.set(datePicker.getYear() + 1, java.util.Calendar.MAY, datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                            break;
                        case 5 :
                            calendar.set(datePicker.getYear() + 1, java.util.Calendar.JUNE, datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                            break;
                        case 6 :
                            calendar.set(datePicker.getYear() + 1, java.util.Calendar.JULY, datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                            break;
                        case 7 :
                            calendar.set(datePicker.getYear() + 1, java.util.Calendar.AUGUST, datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                            break;
                        case 8 :
                            calendar.set(datePicker.getYear() + 1, java.util.Calendar.SEPTEMBER, datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                            break;
                        case 9 :
                            calendar.set(datePicker.getYear() + 1, java.util.Calendar.OCTOBER, datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                            break;
                        case 10 :
                            calendar.set(datePicker.getYear() + 1, java.util.Calendar.NOVEMBER, datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                            break;
                        case 11 :
                            calendar.set(datePicker.getYear() + 1, java.util.Calendar.DECEMBER, datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                            break;
                        default :
                            break;
                    }
                    endTime = calendar.getTimeInMillis();
                    android.util.Log.d("endTime", "The very second endTIme: " + endTime);
                }
                prefs.edit().putLong(endTimeFile, endTime).apply();
                long tmpEndTime = prefs.getLong(endTimeFile, 0);
                listener.promptLock();
            }
        });
        return view;
    }

    public interface OnLockSelectedListener {
        public void promptLock();
    }

    @java.lang.Override
    public void onAttach(final android.app.Activity activity) {
        super.onAttach(activity);
        ctx = activity.getApplicationContext();
        if (activity instanceof com.hackthenorth.lockmeout.app.LockPhone.EndTime.OnLockSelectedListener) {
            listener = ((com.hackthenorth.lockmeout.app.LockPhone.EndTime.OnLockSelectedListener) (activity));
        } else {
            throw new java.lang.ClassCastException(activity.toString() + " must implement MyListFragment.OnItemSelectedListener");
        }
        pickDateDialog = new android.app.AlertDialog.Builder(activity).setTitle("Set Date").setMessage("Select the date you want to start.").setPositiveButton(android.R.string.yes, new android.content.DialogInterface.OnClickListener() {
            public void onClick(android.content.DialogInterface dialog, int which) {
                day = datePicker.getDayOfMonth();
                month = datePicker.getMonth();
                android.widget.Toast toast = android.widget.Toast.makeText(activity.getApplicationContext(), (("Set Month: " + month) + " Days: ") + day, android.widget.Toast.LENGTH_LONG);
                toast.show();
            }
        }).setNegativeButton(android.R.string.no, new android.content.DialogInterface.OnClickListener() {
            public void onClick(android.content.DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        pickTimeDialog = new android.app.AlertDialog.Builder(activity).setTitle("Set Time").setMessage("Select the time you want to start.").setPositiveButton(android.R.string.ok, new android.content.DialogInterface.OnClickListener() {
            public void onClick(android.content.DialogInterface dialog, int which) {
                minute = timePicker.getCurrentMinute();
                hour = timePicker.getCurrentHour();
                android.widget.Toast toast = android.widget.Toast.makeText(activity.getApplicationContext(), (("Set Hour: " + hour) + " Minutes: ") + minute, android.widget.Toast.LENGTH_LONG);
                toast.show();
            }
        }).setNegativeButton(android.R.string.cancel, new android.content.DialogInterface.OnClickListener() {
            public void onClick(android.content.DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }

    @java.lang.Override
    public void onSaveInstanceState(android.os.Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}