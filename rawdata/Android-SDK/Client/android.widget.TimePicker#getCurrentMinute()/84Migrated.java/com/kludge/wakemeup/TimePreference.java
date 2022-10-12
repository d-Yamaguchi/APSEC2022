package com.kludge.wakemeup;
/**
 * Created by Zex on 15/6/2016.]
 * custom preference dialog
 */
public class TimePreference extends android.preference.DialogPreference {
    private android.content.SharedPreferences sharedPrefs;

    private android.content.SharedPreferences.Editor editor;

    android.widget.TimePicker timePicker;

    public TimePreference(android.content.Context context, android.util.AttributeSet attrSet) {
        super(context, attrSet);
        sharedPrefs = android.preference.PreferenceManager.getDefaultSharedPreferences(getContext());
    }

    @java.lang.Override
    protected android.view.View onCreateDialogView() {
        timePicker = new android.widget.TimePicker(getContext());
        return timePicker;
    }

    @java.lang.Override
    protected void onDialogClosed(boolean positiveResult) {
        __SmPLUnsupported__(0).onDialogClosed(positiveResult);
        editor = sharedPrefs.edit();
        if (positiveResult) {
            editor.putInt("preference_alarm_hour", timePicker.getCurrentHour());
            editor.putInt("preference_alarm_minute", timePicker.getMinute());
            editor.putInt("preference_alarm_time", (timePicker.getCurrentHour() * 3600000) + (timePicker.getCurrentMinute() * 60000));// placeholder just so onSharedPrefChange is called? idk

            editor.putInt("preference_alarm_time", (timePicker.getCurrentHour() * 3600000) + (timePicker.getCurrentMinute() * 60000));
            editor.apply();
        }
    }

    // access timePicker here
    @java.lang.Override
    protected void onBindDialogView(android.view.View view) {
        super.onBindDialogView(view);
    }
}