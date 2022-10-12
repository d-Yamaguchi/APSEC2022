package org.jorge.lolin1.func.custom;
import com.crashlytics.android.Crashlytics;
import org.jorge.lolin1.utils.LoLin1Utils;
/**
 * This file is part of LoLin1.
 * <p/>
 * LoLin1 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * LoLin1 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with LoLin1. If not, see <http://www.gnu.org/licenses/>.
 * <p/>
 * Created by Jorge Antonio Diaz-Benito Soriano on 07/01/14.
 */
public class TimePickerPreference extends android.preference.DialogPreference implements android.widget.TimePicker.OnTimeChangedListener {
    private static final java.lang.String SANITY_EXPRESSION = "[0-5]*[0-9]:[0-5]*[0-9]";

    private static final java.lang.String DEFAULT_VALUE = "00:00";

    private static final java.lang.Integer ERROR_CODE = -1;

    private static final java.lang.Integer COUNTER_AMOUNT = 3;

    private static java.lang.Integer TYPE_COUNTER = 0;

    private java.lang.String value;

    public TimePickerPreference(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
        setPersistent(java.lang.Boolean.TRUE);
        java.lang.String instanceType;
        switch (org.jorge.lolin1.func.custom.TimePickerPreference.TYPE_COUNTER % org.jorge.lolin1.func.custom.TimePickerPreference.COUNTER_AMOUNT) {
            case 0 :
                instanceType = "baron";
                break;
            case 1 :
                instanceType = "dragon";
                break;
            case 2 :
                instanceType = "buff";
                break;
            default :
                com.crashlytics.android.Crashlytics.log(android.util.Log.ERROR, "debug", "Unsupported timer type - TYPE_COUNTER: " + org.jorge.lolin1.func.custom.TimePickerPreference.TYPE_COUNTER);
                instanceType = "ERROR";
        }
        value = ("pref_default_" + instanceType) + "_respawn";
        org.jorge.lolin1.func.custom.TimePickerPreference.TYPE_COUNTER++;
    }

    @java.lang.Override
    protected void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            persistString(value);
        }
    }

    @java.lang.Override
    protected android.view.View onCreateDialogView() {
        timePicker.setOnTimeChangedListener(this);
        int ss = getSeconds();
        timePicker.setIs24HourView(java.lang.Boolean.TRUE);
        android.widget.TimePicker timePicker = new android.widget.TimePicker(getContext());
        int mm = getMinutes();
        if ((mm != org.jorge.lolin1.func.custom.TimePickerPreference.ERROR_CODE) && (ss != org.jorge.lolin1.func.custom.TimePickerPreference.ERROR_CODE)) {
            TimePicker picker = null;
            int lastHour = 0;
            picker.setHour(lastHour);
            timePicker.setCurrentMinute(ss);
        }
        return timePicker;
    }

    @java.lang.Override
    public void onTimeChanged(android.widget.TimePicker view, int minutes, int seconds) {
        java.lang.String result = (minutes + ":") + seconds;
        callChangeListener(result);
        value = result;
    }

    /**
     *
     *
     * @return {@link java.lang.Integer} The minutes, which will be 0 to 59 (inclusive)
     */
    private java.lang.Integer getMinutes() {
        java.lang.String time = getPersistedString(this.value);
        if ((time == null) || (!time.matches(org.jorge.lolin1.func.custom.TimePickerPreference.SANITY_EXPRESSION))) {
            return org.jorge.lolin1.func.custom.TimePickerPreference.ERROR_CODE;
        }
        return java.lang.Integer.valueOf(time.split(":")[0]);
    }

    /**
     *
     *
     * @return {@link java.lang.Integer} The seconds, which will be from 0 to 59 (inclusive)
     */
    private java.lang.Integer getSeconds() {
        java.lang.String time = getPersistedString(this.value);
        if ((time == null) || (!time.matches(org.jorge.lolin1.func.custom.TimePickerPreference.SANITY_EXPRESSION))) {
            return org.jorge.lolin1.func.custom.TimePickerPreference.ERROR_CODE;
        }
        return java.lang.Integer.valueOf(time.split(":")[1]);
    }

    @java.lang.Override
    protected java.lang.Object onGetDefaultValue(android.content.res.TypedArray a, int index) {
        return a.getString(index);
    }

    @java.lang.Override
    protected void onSetInitialValue(boolean restorePersistedValue, java.lang.Object defaultValue) {
        if (restorePersistedValue) {
            // Restore existing state
            value = this.getPersistedString(org.jorge.lolin1.func.custom.TimePickerPreference.DEFAULT_VALUE);
        } else {
            // Set default state from the XML attribute
            value = ((java.lang.String) (defaultValue));
            persistString(value);
        }
    }
}