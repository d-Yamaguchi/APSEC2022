package newest.box.smart.com.smartchargeboxnewest.modals;
import android.support.v4.app.DialogFragment;
import newest.box.smart.com.smartchargeboxnewest.R;
import newest.box.smart.com.smartchargeboxnewest.events.TimeRangeSelectedEvent;
import org.greenrobot.eventbus.EventBus;
/**
 * Created by Deividas on 2017-09-10.
 */
public class TimeRangePickerDialog extends android.support.v4.app.DialogFragment implements android.view.View.OnClickListener , android.widget.TabHost.OnTabChangeListener {
    private android.widget.TimePicker startTimePicker;

    private android.widget.TimePicker endTimePicker;

    private boolean is24HourMode = true;

    private boolean isSecondSelected = false;

    private int startDay;

    private int startMonth;

    private int startYear;

    private int endDay;

    private int endMonth;

    private int endYear;

    private boolean secondDay = false;

    private java.lang.String id;

    private boolean validTime = true;

    java.util.Calendar startCalendar;

    java.util.Calendar endCalendar;

    private android.widget.TextView startTime;

    private android.widget.TextView endTime;

    public static newest.box.smart.com.smartchargeboxnewest.modals.TimeRangePickerDialog newInstance() {
        newest.box.smart.com.smartchargeboxnewest.modals.TimeRangePickerDialog ret = new newest.box.smart.com.smartchargeboxnewest.modals.TimeRangePickerDialog();
        ret.initialize();
        return ret;
    }

    public void initialize() {
        this.is24HourMode = true;
    }

    public void setCalendarValues(java.lang.String year, java.lang.String month, java.lang.String day, java.lang.String connectorId) {
        this.startYear = java.lang.Integer.valueOf(year);
        this.startMonth = java.lang.Integer.valueOf(month);
        this.startDay = java.lang.Integer.valueOf(day);
        android.util.Log.d("SDSIGDSD", (((startYear + " ") + startMonth) + " ") + startDay);
        endYear = this.startYear;
        endMonth = this.startMonth;
        endDay = this.startDay;
        this.id = connectorId;
    }

    @java.lang.Override
    public void onTabChanged(java.lang.String s) {
        dateColor(s);
    }

    private void dateColor(java.lang.String s) {
        if ("1".equals(s)) {
            startTime.setTextColor(android.graphics.Color.RED);
            endTime.setTextColor(android.graphics.Color.GRAY);
        } else if ("2".equals(s)) {
            endTime.setTextColor(android.graphics.Color.RED);
            startTime.setTextColor(android.graphics.Color.GRAY);
            isSecondSelected = true;
        }
    }

    @java.lang.Override
    public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
        android.view.View root = inflater.inflate(R.layout.fragment_time_range_picker_dialog, container, false);
        getDialog().getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        startTimePicker = ((android.widget.TimePicker) (root.findViewById(R.id.startTimePicker)));
        endTimePicker = ((android.widget.TimePicker) (root.findViewById(R.id.endTimePicker)));
        android.widget.TabHost tabs = ((android.widget.TabHost) (root.findViewById(R.id.tabHost)));
        final android.widget.Button setTimeRange = ((android.widget.Button) (root.findViewById(R.id.set_time)));
        android.widget.Button cancelButton = ((android.widget.Button) (root.findViewById(R.id.cancel)));
        startTimePicker.setIs24HourView(is24HourMode);
        endTimePicker.setIs24HourView(is24HourMode);
        setTimeRange.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        tabs.findViewById(R.id.tabHost);
        tabs.setup();
        android.widget.TabHost.TabSpec tabpage1 = tabs.newTabSpec("1");
        tabpage1.setContent(R.id.startTimeGroup);
        tabpage1.setIndicator("Pradžios laikas");
        android.widget.TabHost.TabSpec tabpage2 = tabs.newTabSpec("2");
        tabpage2.setContent(R.id.endTimeGroup);
        tabpage2.setIndicator("Pabaigos laikas");
        tabs.addTab(tabpage1);
        tabs.addTab(tabpage2);
        tabs.setOnTabChangedListener(this);
        final java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
        startCalendar = java.util.Calendar.getInstance();
        endCalendar = java.util.Calendar.getInstance();
        startCalendar.set(startYear, startMonth - 1, startDay, startTimePicker.getHour(), startTimePicker.getCurrentMinute());
        startTime = ((android.widget.TextView) (root.findViewById(R.id.stat_time)));
        endTime = ((android.widget.TextView) (root.findViewById(R.id.end_time)));
        dateColor("1");
        // startTime.setText(dateFormat.format(startCalendar.getTime().getTime() - 60000));
        startTime.setText(dateFormat.format(startCalendar.getTime().getTime()));
        startTimePicker.setOnTimeChangedListener(__SmPLUnsupported__(0));
        endCalendar.set(endYear, endMonth - 1, endDay, startTimePicker.getHour(), endTimePicker.getCurrentMinute());
        endTimePicker.setOnTimeChangedListener(__SmPLUnsupported__(1));
        return root;
    }

    @java.lang.Override
    public void onStart() {
        super.onStart();
        if (getDialog() == null)
            return;

        getDialog().getWindow().setLayout(950, android.view.WindowManager.LayoutParams.WRAP_CONTENT);
    }

    @java.lang.Override
    public void onClick(android.view.View v) {
        if (v.getId() == R.id.set_time) {
            if (!validTime) {
                android.widget.Toast.makeText(getContext(), "Pasirinktas blogas laikas", android.widget.Toast.LENGTH_SHORT).show();
                return;
            }
            if (isSecondSelected) {
                long endTime = endCalendar.getTimeInMillis();
                if (secondDay) {
                    endTime += 86400000;
                }
                if (java.lang.Math.abs(endTime - startCalendar.getTimeInMillis()) < 900000) {
                    android.widget.Toast.makeText(getContext(), "Krovimo laikas turi būti didesnis už 15 min.", android.widget.Toast.LENGTH_SHORT).show();
                    return;
                }
                dismiss();
                int startHour = startTimePicker.getCurrentHour();
                int startMin = startTimePicker.getCurrentMinute();
                int endHour = endTimePicker.getCurrentHour();
                int endMin = endTimePicker.getCurrentMinute();
                if (secondDay) {
                    endDay += 1;
                }
                android.util.Log.d("SDSUYDSDSDSDDSDSD", (((((((startYear + " ") + startMonth) + " ") + startDay) + " ") + startHour) + " ") + startMin);
                org.greenrobot.eventbus.EventBus.getDefault().post(new newest.box.smart.com.smartchargeboxnewest.events.TimeRangeSelectedEvent(java.lang.String.valueOf(startYear), java.lang.String.valueOf(startMonth), java.lang.String.valueOf(startDay), startHour, startMin, java.lang.String.valueOf(endYear), java.lang.String.valueOf(endMonth), java.lang.String.valueOf(endDay), endHour, endMin, id));
            } else {
                android.widget.Toast.makeText(getContext(), "Pabaigos laikas nenustatytas!", android.widget.Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.cancel) {
            getDialog().dismiss();
        }
    }
}