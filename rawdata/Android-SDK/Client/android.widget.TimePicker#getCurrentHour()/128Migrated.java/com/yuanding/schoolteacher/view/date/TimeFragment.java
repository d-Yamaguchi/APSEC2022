package com.yuanding.schoolteacher.view.date;
import android.support.v4.app.Fragment;
import com.yuanding.schoolteacher.R;
/**
 * The fragment for the second page in the ViewPager that holds
 * the TimePicker.
 *
 * @author jjobes
 */
public class TimeFragment extends android.support.v4.app.Fragment {
    /**
     * Used to communicate back to the parent fragment as the user
     * is changing the time spinners so we can dynamically update
     * the tab text.
     */
    public interface TimeChangedListener {
        void onTimeChanged(int hour, int minute);
    }

    private com.yuanding.schoolteacher.view.date.TimeFragment.TimeChangedListener mCallback;

    private android.widget.TimePicker mTimePicker;

    public TimeFragment() {
        // Required empty public constructor for fragment.
    }

    /**
     * Cast the reference to {@link SlideDateTimeDialogFragment} to a
     * {@link TimeChangedListener}.
     */
    @java.lang.Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mCallback = ((com.yuanding.schoolteacher.view.date.TimeFragment.TimeChangedListener) (getTargetFragment()));
        } catch (java.lang.ClassCastException e) {
            throw new java.lang.ClassCastException("Calling fragment must implement " + "TimeFragment.TimeChangedListener interface");
        }
    }

    /**
     * Return an instance of TimeFragment with its bundle filled with the
     * constructor arguments. The values in the bundle are retrieved in
     * {@link #onCreateView()} below to properly initialize the TimePicker.
     *
     * @param theme
     * 		
     * @param hour
     * 		
     * @param minute
     * 		
     * @param isClientSpecified24HourTime
     * 		
     * @param is24HourTime
     * 		
     * @return 
     */
    public static final com.yuanding.schoolteacher.view.date.TimeFragment newInstance(int theme, int hour, int minute, boolean isClientSpecified24HourTime, boolean is24HourTime) {
        com.yuanding.schoolteacher.view.date.TimeFragment f = new com.yuanding.schoolteacher.view.date.TimeFragment();
        android.os.Bundle b = new android.os.Bundle();
        b.putInt("theme", theme);
        b.putInt("hour", hour);
        b.putInt("minute", minute);
        b.putBoolean("isClientSpecified24HourTime", isClientSpecified24HourTime);
        b.putBoolean("is24HourTime", is24HourTime);
        f.setArguments(b);
        return f;
    }

    /**
     * Create and return the user interface view for this fragment.
     */
    @java.lang.Override
    public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
        int theme = getArguments().getInt("theme");
        int initialHour = getArguments().getInt("hour");
        int initialMinute = getArguments().getInt("minute");
        boolean isClientSpecified24HourTime = getArguments().getBoolean("isClientSpecified24HourTime");
        boolean is24HourTime = getArguments().getBoolean("is24HourTime");
        // Unless we inflate using a cloned inflater with a Holo theme,
        // on Lollipop devices the TimePicker will be the new-style
        // radial TimePicker, which is not what we want. So we will
        // clone the inflater that we're given but with our specified
        // theme, then inflate the layout with this new inflater.
        android.content.Context contextThemeWrapper = new android.view.ContextThemeWrapper(getActivity(), theme == SlideDateTimePicker.HOLO_DARK ? android.R.style.Theme_Holo : android.R.style.Theme_Holo_Light);
        android.view.LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        android.view.View v = localInflater.inflate(R.layout.date_fragment_time, container, false);
        mTimePicker = ((android.widget.TimePicker) (v.findViewById(R.id.timePicker)));
        // block keyboard popping up on touch
        mTimePicker.setDescendantFocusability(android.widget.DatePicker.FOCUS_BLOCK_DESCENDANTS);
        mTimePicker.setOnTimeChangedListener(new android.widget.TimePicker.OnTimeChangedListener() {
            @java.lang.Override
            public void onTimeChanged(android.widget.TimePicker view, int hourOfDay, int minute) {
                mCallback.onTimeChanged(hourOfDay, minute);
            }
        });
        // If the client specifies a 24-hour time format, set it on
        // the TimePicker.
        if (isClientSpecified24HourTime) {
            mTimePicker.setIs24HourView(is24HourTime);
        } else {
            // If the client does not specify a 24-hour time format, use the
            // device default.
            mTimePicker.setIs24HourView(android.text.format.DateFormat.is24HourFormat(getTargetFragment().getActivity()));
        }
        mTimePicker.setCurrentHour(initialHour);
        mTimePicker.setCurrentMinute(initialMinute);
        // Fix for the bug where a TimePicker's onTimeChanged() is not called when
        // the user toggles the AM/PM button. Only applies to 4.0.0 and 4.0.3.
        if ((android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) && (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)) {
            fixTimePickerBug18982();
        }
        return v;
    }

    /**
     * Workaround for bug in Android TimePicker where the onTimeChanged() callback
     * is not invoked when the user toggles between AM/PM. But we need to be able
     * to detect this in order to dynamically update the tab title properly when
     * the user toggles between AM/PM.
     *
     * Registered as Issue 18982:
     *
     * https://code.google.com/p/android/issues/detail?id=18982
     */
    private void fixTimePickerBug18982() {
        android.view.View amPmView = ((android.view.ViewGroup) (mTimePicker.getChildAt(0))).getChildAt(3);
        if (amPmView instanceof android.widget.NumberPicker) {
            ((android.widget.NumberPicker) (amPmView)).setOnValueChangedListener(new android.widget.NumberPicker.OnValueChangeListener() {
                @java.lang.Override
                public void onValueChange(android.widget.NumberPicker picker, int oldVal, int newVal) {
                    mTimePicker.setCurrentHour(mTimePicker.getHour());
                    /* if (picker.getValue() == 1)  // PM
                    {
                    if (mTimePicker.getCurrentHour() < 12)
                    mTimePicker.setCurrentHour(mTimePicker.getCurrentHour() + 12);
                    }
                    else  // AM
                    {
                    if (mTimePicker.getCurrentHour() >= 12)
                    mTimePicker.setCurrentHour(mTimePicker.getCurrentHour() - 12);
                    }
                     */
                    mCallback.onTimeChanged(mTimePicker.getHour(), mTimePicker.getCurrentMinute());
                }
            });
        }
    }
}