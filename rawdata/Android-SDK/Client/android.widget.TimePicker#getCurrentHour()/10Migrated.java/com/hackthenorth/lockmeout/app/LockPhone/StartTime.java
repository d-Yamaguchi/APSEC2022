package com.hackthenorth.lockmeout.app.LockPhone;
import com.hackthenorth.lockmeout.app.R;
/**
 * Created by Berries on 2014-09-19.
 */
public class StartTime extends com.hackthenorth.lockmeout.app.LockPhone.LockPhoneFragment {
    public static final java.lang.String EXTRA_MESSAGE = "EXTRA_MESSAGE";

    public static final java.lang.String HOUR = "HOUR";

    public static final java.lang.String MINUTE = "MINUTE";

    public static final java.lang.String DAY = "DAY";

    public static final java.lang.String MONTH = "MONTH";

    public java.lang.String startTimeFile = "com.hackthenorth.lockmeout.startTime";

    public java.lang.String iFile = "com.hackthenorth.lockmeout.i";

    private int hour;

    private int minute;

    private int day;

    private int month;

    int i = 0;

    int result = 0;

    private android.widget.TimePicker timePicker;

    private android.widget.DatePicker datePicker;

    private android.widget.Button nextButton;

    private android.app.AlertDialog.Builder pickDateDialog;

    private android.app.AlertDialog.Builder pickTimeDialog;

    private android.view.View timeLayout;

    private android.view.View dateLayout;

    private android.widget.Button timeBtnDialog;

    private android.widget.Button dateBtnDialog;

    private android.app.Activity mainActivity;

    private android.content.Context ctx;

    @java.lang.Override
    public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        android.view.View view = ((android.view.View) (inflater.inflate(R.layout.fragment_starttime, container, false)));
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
        android.widget.Button setDateBtn = ((android.widget.Button) (view.findViewById(R.id.btn_set_date_start)));
        setDateBtn.setOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View view) {
                pickDateDialog.show();
            }
        });
        android.widget.Button setTimeBtn = ((android.widget.Button) (view.findViewById(R.id.btn_set_time_start)));
        setTimeBtn.setOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View view) {
                pickTimeDialog.show();
            }
        });
        nextButton = ((android.widget.Button) (view.findViewById(R.id.next_button)));
        nextButton.setOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View v) {
                saveClicked(minute, hour, day, month, "next");
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                android.content.SharedPreferences prefs = ctx.getSharedPreferences("com.hackthenorth.lockmeout", android.content.Context.MODE_PRIVATE);
                __SmPLUnsupported__(0);
                long startTime = calendar.getTimeInMillis();
                java.lang.String a = calendar.toString();
                prefs.edit().putLong(startTimeFile, startTime).apply();
                saveClicked(timePicker.getCurrentMinute(), _CVAR8.getHour(), datePicker.getDayOfMonth(), datePicker.getMonth(), "next");
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                __SmPLUnsupported__(1);
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                __SmPLUnsupported__(2);
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                __SmPLUnsupported__(3);
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                __SmPLUnsupported__(4);
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                __SmPLUnsupported__(5);
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                __SmPLUnsupported__(6);
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                __SmPLUnsupported__(7);
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                __SmPLUnsupported__(8);
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                __SmPLUnsupported__(9);
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                __SmPLUnsupported__(10);
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                __SmPLUnsupported__(11);
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                __SmPLUnsupported__(12);
                saveClicked(timePicker.getCurrentMinute(), _CVAR8.getHour(), datePicker.getDayOfMonth(), datePicker.getMonth(), "next");
            }
        });
        return view;
    }

    @java.lang.Override
    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
        mainActivity = activity;
        ctx = activity.getApplicationContext();
        if (activity instanceof com.hackthenorth.lockmeout.app.LockPhone.OnSaveSelectedListener) {
            listener = ((com.hackthenorth.lockmeout.app.LockPhone.OnSaveSelectedListener) (activity));
        } else {
            throw new java.lang.ClassCastException(activity.toString() + " must implement MyListFragment.OnItemSelectedListener");
        }
        pickDateDialog = new android.app.AlertDialog.Builder(activity).setTitle("Set Date").setMessage("Select the date you want to start.").setPositiveButton(android.R.string.yes, new android.content.DialogInterface.OnClickListener() {
            public void onClick(android.content.DialogInterface dialog, int which) {
                day = datePicker.getDayOfMonth();
                month = datePicker.getMonth();
                android.widget.Toast toast = android.widget.Toast.makeText(mainActivity.getApplicationContext(), (("Set Month: " + month) + " Days: ") + day, android.widget.Toast.LENGTH_LONG);
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
                android.widget.Toast toast = android.widget.Toast.makeText(mainActivity.getApplicationContext(), (("Set Hour: " + hour) + " Minutes: ") + minute, android.widget.Toast.LENGTH_LONG);
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
        outState.putInt(com.hackthenorth.lockmeout.app.LockPhone.StartTime.HOUR, hour);
        outState.putInt(com.hackthenorth.lockmeout.app.LockPhone.StartTime.MINUTE, minute);
        outState.putInt(com.hackthenorth.lockmeout.app.LockPhone.StartTime.DAY, day);
        outState.putInt(com.hackthenorth.lockmeout.app.LockPhone.StartTime.MONTH, month);
    }
}