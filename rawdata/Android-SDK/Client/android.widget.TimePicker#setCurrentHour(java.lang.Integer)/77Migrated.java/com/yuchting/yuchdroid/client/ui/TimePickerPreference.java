/**
 * Dear developer:
 *
 *   If you want to modify this file of project and re-publish this please visit:
 *
 *     http://code.google.com/p/yuchberry/wiki/Project_files_header
 *
 *   to check your responsibility and my humble proposal. Thanks!
 *
 *  --
 *  Yuchs' Developer
 *
 *
 *
 *
 *  尊敬的开发者：
 *
 *    如果你想要修改这个项目中的文件，同时重新发布项目程序，请访问一下：
 *
 *      http://code.google.com/p/yuchberry/wiki/Project_files_header
 *
 *    了解你的责任，还有我卑微的建议。 谢谢！
 *
 *  --
 *  语盒开发者
 */
package com.yuchting.yuchdroid.client.ui;
/**
 * A preference type that allows a user to choose a time
 *
 * copy from
 * http://www.ebessette.com/d/TimePickerPreference
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
        // BUG this method is never called if you use the 'android:defaultValue' attribute in your XML preference file, not sure why it isn't
        super.setDefaultValue(defaultValue);
        if (!(defaultValue instanceof java.lang.String)) {
            return;
        }
        if (!((java.lang.String) (defaultValue)).matches(com.yuchting.yuchdroid.client.ui.TimePickerPreference.VALIDATION_EXPRESSION)) {
            return;
        }
        this.defaultValue = ((java.lang.String) (defaultValue));
    }

    /**
     * Get the hour value (in 24 hour time)
     *
     * @return The hour value, will be 0 to 23 (inclusive)
     */
    public int getHour() {
        java.lang.String time = getPersistedString(this.defaultValue);
        if ((time == null) || (!time.matches(com.yuchting.yuchdroid.client.ui.TimePickerPreference.VALIDATION_EXPRESSION))) {
            return -1;
        }
        return java.lang.Integer.valueOf(time.split(":")[0]);
    }

    /**
     * Get the minute value
     *
     * @return the minute value, will be 0 to 59 (inclusive)
     */
    public int getMinute() {
        java.lang.String time = getPersistedString(this.defaultValue);
        if ((time == null) || (!time.matches(com.yuchting.yuchdroid.client.ui.TimePickerPreference.VALIDATION_EXPRESSION))) {
            return -1;
        }
        return java.lang.Integer.valueOf(time.split(":")[1]);
    }
}