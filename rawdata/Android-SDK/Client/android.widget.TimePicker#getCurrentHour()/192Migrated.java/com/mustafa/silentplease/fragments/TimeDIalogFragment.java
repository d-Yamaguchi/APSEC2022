package com.mustafa.silentplease.fragments;
import com.mustafa.silentplease.CustomSwitchCheckedListener;
import com.mustafa.silentplease.R;
import com.mustafa.silentplease.utils.Constants;
import com.mustafa.silentplease.utils.Utils;
/**
 * Created by Mustafa.Gamesterz on 25/05/16.
 */
public class TimeDIalogFragment extends android.preference.DialogPreference {
    private int lastHour = com.mustafa.silentplease.utils.Utils.getHour();

    private int lastMinute = com.mustafa.silentplease.utils.Utils.getMinute();

    private boolean isAm = com.mustafa.silentplease.utils.Utils.isAM();

    private android.widget.TimePicker timePicker = null;

    private com.mustafa.silentplease.CustomSwitchCheckedListener switchCheckedListener;

    public TimeDIalogFragment(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
        setNegativeButtonText(context.getString(R.string.negative_dialog_button));
        setPositiveButtonText(context.getString(R.string.positive_dialog_button));
    }

    private int get24Hour(java.lang.String time) {
        if (time == null)
            return 0;

        java.lang.String[] pieces = time.split(":");
        return java.lang.Integer.valueOf(pieces[0]);
    }

    public static int getMinute(java.lang.String time) {
        java.lang.String[] pieces = time.split(":");
        java.lang.String minPieces = pieces[1];
        minPieces = minPieces.substring(0, minPieces.indexOf(" "));
        return java.lang.Integer.parseInt(minPieces);
    }

    public com.mustafa.silentplease.CustomSwitchCheckedListener getSwitchLIStener() {
        return switchCheckedListener;
    }

    public boolean isSwitchON() {
        return switchCheckedListener.isSwitchON();
    }

    @java.lang.Override
    protected void onBindDialogView(android.view.View view) {
        super.onBindDialogView(view);
        if (isAm) {
            final int newHour = lastHour + 12;
            if (newHour <= 24)
                lastHour += 12;

        } else if (lastHour == 12)
            lastHour = 0;

        timePicker.setCurrentHour(lastHour);
        timePicker.setCurrentMinute(lastMinute);
    }

    @java.lang.Override
    protected void onBindView(android.view.View view) {
        super.onBindView(view);
        android.widget.Switch swith = ((android.widget.Switch) (view.findViewById(R.id.switchWidget)));
        switchCheckedListener = new com.mustafa.silentplease.CustomSwitchCheckedListener(getContext(), swith, getKey() + com.mustafa.silentplease.utils.Constants.PREF_KEY_SWITCH_SUFFIX);
    }

    @java.lang.Override
    protected android.view.View onCreateDialogView() {
        timePicker = new android.widget.TimePicker(getContext());
        timePicker.setIs24HourView(false);
        return timePicker;
    }

    @java.lang.Override
    protected void onDialogClosed(boolean positiveResult) {
        __SmPLUnsupported__(0).onDialogClosed(positiveResult);
        if (positiveResult) {
            int h24Format = timePicker.getCurrentHour();
            lastHour = timePicker.getHour();
            lastMinute = timePicker.getCurrentMinute();
            java.lang.String suffix = " AM";
            isAm = true;
            if ()
                lastHour = 12;
            else if () {
                suffix = " PM";
                isAm = false;
                h24Format = 12;
            } else if () {
                suffix = " PM";
                lastHour -= 12;
                isAm = false;
            }
            java.lang.String time = ((java.lang.String.valueOf(h24Format) + ":") + java.lang.String.format("%02d", lastMinute)) + suffix;
            if (callChangeListener(time)) {
                persistString(time);
            }
        }
    }

    @java.lang.Override
    protected java.lang.Object onGetDefaultValue(android.content.res.TypedArray a, int index) {
        return a.getString(index);
    }

    @java.lang.Override
    protected void onSetInitialValue(boolean restorePersistedValue, java.lang.Object defaultValue) {
        java.lang.String time;
        if (restorePersistedValue) {
            if (defaultValue == null) {
                time = getPersistedString(com.mustafa.silentplease.utils.Utils.getNowTime());
            } else {
                time = getPersistedString(defaultValue.toString());
            }
        } else {
            time = defaultValue.toString();
        }
        lastHour = get24Hour(time);
        lastMinute = com.mustafa.silentplease.fragments.TimeDIalogFragment.getMinute(time);
        persistString(time);
    }

    public void setNowValue(java.lang.String time) {
        if (time == null)
            return;

        lastHour = get24Hour(time);
        lastMinute = com.mustafa.silentplease.fragments.TimeDIalogFragment.getMinute(time);
        persistString(time);
    }
}