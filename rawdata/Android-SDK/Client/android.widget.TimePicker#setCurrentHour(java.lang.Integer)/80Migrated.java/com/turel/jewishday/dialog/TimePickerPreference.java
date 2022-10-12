package com.turel.jewishday.dialog;
/**
 * A preference type that allows a user to choose a time
 */
public class TimePickerPreference extends android.preference.DialogPreference implements android.widget.TimePicker.OnTimeChangedListener {
    /**
     * The validation expression for this preference
     */
    private static final java.lang.String VALIDATION_EXPRESSION = "[0-2]*[0-9]:[0-5]*[0-9]";

    /**
     * The default value for this preference
     */
    private java.lang.String defaultValue;

    /**
     *
     *
     * @param context
     * 		
     * @param attrs
     * 		
     */
    public TimePickerPreference(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    /**
     *
     *
     * @param context
     * 		
     * @param attrs
     * 		
     * @param defStyle
     * 		
     */
    public TimePickerPreference(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    /**
     * Initialize this preference
     */
    private void initialize() {
        setPersistent(true);
    }

    /* (non-Javadoc)

    @see android.preference.DialogPreference#onCreateDialogView()
     */
    @java.lang.Override
    protected android.view.View onCreateDialogView() {
        tp.setOnTimeChangedListener(this);
        int m = getMinute();
        android.widget.TimePicker tp = new android.widget.TimePicker(getContext());
        int h = getHour();
        if ((h >= 0) && (m >= 0)) {
            TimePicker picker = null;
            int lastHour = 0;
            picker.setHour(lastHour);
            tp.setCurrentMinute(m);
        }
        return tp;
    }

    /* (non-Javadoc)

    @see
    android.widget.TimePicker.OnTimeChangedListener#onTimeChanged(android
    .widget.TimePicker, int, int)
     */
    @java.lang.Override
    public void onTimeChanged(android.widget.TimePicker view, int hour, int minute) {
        persistString((hour + ":") + minute);
    }

    /* (non-Javadoc)

    @see android.preference.Preference#setDefaultValue(java.lang.Object)
     */
    @java.lang.Override
    public void setDefaultValue(java.lang.Object defaultValue) {
        // BUG this method is never called if you use the 'android:defaultValue'
        // attribute in your XML preference file, not sure why it isn't
        super.setDefaultValue(defaultValue);
        if (!(defaultValue instanceof java.lang.String)) {
            return;
        }
        if (!((java.lang.String) (defaultValue)).matches(com.turel.jewishday.dialog.TimePickerPreference.VALIDATION_EXPRESSION)) {
            return;
        }
        this.defaultValue = ((java.lang.String) (defaultValue));
    }

    /**
     * Get the hour value (in 24 hour time)
     *
     * @return The hour value, will be 0 to 23 (inclusive)
     */
    private int getHour() {
        java.lang.String time = getPersistedString(this.defaultValue);
        if ((time == null) || (!time.matches(com.turel.jewishday.dialog.TimePickerPreference.VALIDATION_EXPRESSION))) {
            return -1;
        }
        return java.lang.Integer.valueOf(time.split(":")[0]);
    }

    /**
     * Get the minute value
     *
     * @return the minute value, will be 0 to 59 (inclusive)
     */
    private int getMinute() {
        java.lang.String time = getPersistedString(this.defaultValue);
        if ((time == null) || (!time.matches(com.turel.jewishday.dialog.TimePickerPreference.VALIDATION_EXPRESSION))) {
            return -1;
        }
        return java.lang.Integer.valueOf(time.split(":")[1]);
    }
}