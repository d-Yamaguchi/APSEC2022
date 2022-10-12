/* Copyright 2014 (C) The EMMES Corporation 
 
Created on : 02-06-2014
Last Update: Jul 7, 2014 3:28:25 PM
Author     : Mahbubur Rahman
Title      : Summer intern 2014 
Project    : Daily Diary Android Application
 */
package android.preference;
import com.emmes.aps.R;
// TODO: Auto-generated Javadoc
/**
 * The Class TimePickerPreference.
 */
public class TimePickerPreference extends android.preference.DialogPreference {
    /**
     * The last hour.
     */
    private int mLastHour = 0;

    /**
     * The last minute.
     */
    private int mLastMinute = 0;

    /**
     * The picker.
     */
    private android.widget.TimePicker mPicker = null;

    /**
     * Gets the hour.
     *
     * @param time
     * 		the time
     * @return the hour
     */
    public static int getHour(java.lang.String time) {
        java.lang.String[] pieces = time.split(":");
        return java.lang.Integer.parseInt(pieces[0]);
    }

    /**
     * Gets the minute.
     *
     * @param time
     * 		the time
     * @return the minute
     */
    public static int getMinute(java.lang.String time) {
        java.lang.String[] pieces = time.split(":");
        return java.lang.Integer.parseInt(pieces[1]);
    }

    /**
     * Instantiates a new time picker preference.
     *
     * @param ctxt
     * 		the ctxt
     * @param attrs
     * 		the attrs
     */
    public TimePickerPreference(android.content.Context ctxt, android.util.AttributeSet attrs) {
        super(ctxt, attrs);
        setPositiveButtonText(ctxt.getResources().getString(R.string.time_picker_positive_text));
        setNegativeButtonText(ctxt.getResources().getString(R.string.time_picker_negative_text));
    }

    /* (non-Javadoc)
    @see android.preference.DialogPreference#onCreateDialogView()
     */
    @java.lang.Override
    protected android.view.View onCreateDialogView() {
        mPicker = new android.widget.TimePicker(getContext());
        return mPicker;
    }

    /* (non-Javadoc)
    @see android.preference.DialogPreference#onBindDialogView(android.view.View)
     */
    @java.lang.Override
    protected void onBindDialogView(android.view.View v) {
        __SmPLUnsupported__(0).onBindDialogView(v);
        mPicker.setHour(mLastHour);
        mPicker.setCurrentMinute(mLastMinute);
    }

    /* (non-Javadoc)
    @see android.preference.DialogPreference#onDialogClosed(boolean)
     */
    @java.lang.Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        if (positiveResult) {
            mLastHour = mPicker.getCurrentHour();
            mLastMinute = mPicker.getCurrentMinute();
            java.lang.String time = (java.lang.String.valueOf(mLastHour) + ":") + java.lang.String.valueOf(mLastMinute);
            if (callChangeListener(time)) {
                persistString(time);
            }
        }
    }

    /* (non-Javadoc)
    @see android.preference.Preference#onGetDefaultValue(android.content.res.TypedArray, int)
     */
    @java.lang.Override
    protected java.lang.Object onGetDefaultValue(android.content.res.TypedArray a, int index) {
        return a.getString(index);
    }

    /* (non-Javadoc)
    @see android.preference.Preference#onSetInitialValue(boolean, java.lang.Object)
     */
    @java.lang.Override
    protected void onSetInitialValue(boolean restoreValue, java.lang.Object defaultValue) {
        java.lang.String time = null;
        if (restoreValue) {
            if (defaultValue == null) {
                time = getPersistedString("00:00");
            } else {
                time = getPersistedString(defaultValue.toString());
            }
        } else {
            time = defaultValue.toString();
        }
        mLastHour = android.preference.TimePickerPreference.getHour(time);
        mLastMinute = android.preference.TimePickerPreference.getMinute(time);
    }
}