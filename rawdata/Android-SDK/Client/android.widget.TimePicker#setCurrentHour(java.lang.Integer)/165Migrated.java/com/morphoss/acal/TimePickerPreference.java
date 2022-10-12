/* Copyright (C) 2011 Morphoss Ltd

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

The original (Licence Free) code for this class was downloaded from http://www.ebessette.com/d/TimePickerPreference
 */
package com.morphoss.acal;
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
    private java.lang.String defaultValue = "12:00";

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
        initialize(context);
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
        initialize(context);
    }

    /**
     * Initialize this preference
     */
    private void initialize(android.content.Context context) {
        setPersistent(true);
        android.content.SharedPreferences prefs = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        defaultValue = prefs.getString(getKey(), defaultValue);
    }

    /* (non-Javadoc)

    @see android.preference.DialogPreference#onCreateDialogView()
     */
    @java.lang.Override
    protected android.view.View onCreateDialogView() {
        android.content.SharedPreferences prefs = android.preference.PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean is24Hour = prefs.getBoolean(getContext().getString(R.string.prefTwelveTwentyfour), false);
        tp.setIs24HourView(is24Hour);
        tp.setOnTimeChangedListener(this);
        android.util.Log.d("TimePicker", "Current default =" + getPersistedString(this.defaultValue));
        int m = getMinute();
        android.widget.TimePicker tp = new android.widget.TimePicker(getContext());
        int h = getHour();
        if ((h >= 0) && (h < 24)) {
            TimePicker picker = null;
            int lastHour = 0;
            picker.setHour(lastHour);
        }
        if ((m >= 0) && (m < 60))
            tp.setCurrentMinute(m);

        return tp;
    }

    /* (non-Javadoc)

    @see
    android.widget.TimePicker.OnTimeChangedListener#onTimeChanged(android
    .widget.TimePicker, int, int)
     */
    @java.lang.Override
    public void onTimeChanged(android.widget.TimePicker view, int hour, int minute) {
        persistString(java.lang.String.format("%02d:%02d", hour, minute));
    }

    /* (non-Javadoc)

    @see android.preference.Preference#setDefaultValue(java.lang.Object)
     */
    @java.lang.Override
    public void setDefaultValue(java.lang.Object defaultValue) {
        // BUG this method is never called if you use the 'android:defaultValue' attribute in your XML preference file, not sure why it isn't
        super.setDefaultValue(defaultValue);
        if (!(defaultValue instanceof java.lang.String)) {
            return;
        }
        if (!((java.lang.String) (defaultValue)).matches(com.morphoss.acal.TimePickerPreference.VALIDATION_EXPRESSION)) {
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
        if ((time == null) || (!time.matches(com.morphoss.acal.TimePickerPreference.VALIDATION_EXPRESSION))) {
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
        if ((time == null) || (!time.matches(com.morphoss.acal.TimePickerPreference.VALIDATION_EXPRESSION))) {
            return -1;
        }
        return java.lang.Integer.valueOf(time.split(":")[1]);
    }
}