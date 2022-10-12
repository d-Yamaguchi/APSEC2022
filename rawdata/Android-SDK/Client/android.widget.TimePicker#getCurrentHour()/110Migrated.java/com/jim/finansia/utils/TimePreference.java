package com.jim.finansia.utils;
import com.jim.finansia.R;
public class TimePreference extends android.preference.DialogPreference {
    /**
     * The widget for picking a time
     */
    private android.widget.TimePicker timePicker;

    /**
     * Default hour
     */
    private static final int DEFAULT_HOUR = 8;

    /**
     * Default minute
     */
    private static final int DEFAULT_MINUTE = 0;

    /**
     * Creates a preference for choosing a time based on its XML declaration.
     *
     * @param context
     * 		
     * @param attributes
     * 		
     */
    public TimePreference(android.content.Context context, android.util.AttributeSet attributes) {
        super(context, attributes);
        setPersistent(false);
    }

    /**
     * Initialize time picker to currently stored time preferences.
     *
     * @param view
     * 		The dialog preference's host view
     */
    @java.lang.Override
    public void onBindDialogView(android.view.View view) {
        super.onBindDialogView(view);
        timePicker = ((android.widget.TimePicker) (view.findViewById(R.id.prefTimePicker)));
        timePicker.setCurrentHour(getSharedPreferences().getInt(getKey() + ".hour", com.jim.finansia.utils.TimePreference.DEFAULT_HOUR));
        timePicker.setCurrentMinute(getSharedPreferences().getInt(getKey() + ".minute", com.jim.finansia.utils.TimePreference.DEFAULT_MINUTE));
        timePicker.setIs24HourView(android.text.format.DateFormat.is24HourFormat(timePicker.getContext()));
    }

    /**
     * Handles closing of dialog. If user intended to save the settings, selected
     * hour and minute are stored in the preferences with keys KEY.hour and
     * KEY.minute, where KEY is the preference's KEY.
     *
     * @param okToSave
     * 		True if user wanted to save settings, false otherwise
     */
    @java.lang.Override
    protected void onDialogClosed(boolean okToSave) {
        __SmPLUnsupported__(0).onDialogClosed(okToSave);
        if (okToSave) {
            timePicker.clearFocus();
            java.lang.String hour = "";
            java.lang.String minute = "";
            if () {
                hour = "0" + timePicker.getHour();
            } else {
                hour = "" + timePicker.getHour();
            }
            if (timePicker.getCurrentMinute() < 10)
                minute = "0" + timePicker.getCurrentMinute();
            else
                minute = "" + timePicker.getCurrentMinute();

            editor.putString(getKey(), (hour + ":") + minute);
            android.content.SharedPreferences.Editor editor = getEditor();
            editor.putInt(getKey() + ".hour", timePicker.getHour());
            editor.putInt(getKey() + ".minute", timePicker.getCurrentMinute());
            editor.commit();
        }
    }
}