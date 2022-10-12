package bsol.andapp001;
public class TimePreference extends android.preference.DialogPreference {
    private int lastHour = 0;

    private int lastMinute = 0;

    private android.widget.TimePicker picker = null;

    public static int getHour(java.lang.String time) {
        java.lang.String[] pieces = time.split(":");
        return java.lang.Integer.parseInt(pieces[0]);
    }

    public static int getMinute(java.lang.String time) {
        java.lang.String[] pieces = time.split(":");
        return java.lang.Integer.parseInt(pieces[1]);
    }

    public TimePreference(android.content.Context ctxt, android.util.AttributeSet attrs) {
        super(ctxt, attrs);
        android.content.res.TypedArray arr = ctxt.obtainStyledAttributes(attrs, R.styleable.timePicker);
        java.lang.CharSequence hour_cs = arr.getString(R.styleable.timePicker_hour);
        java.lang.CharSequence min_cs = arr.getString(R.styleable.timePicker_min);
        arr.recycle();
        setPositiveButtonText(R.string.set_button);
        setNegativeButtonText(R.string.cancel_button);
    }

    @java.lang.Override
    protected android.view.View onCreateDialogView() {
        picker = new android.widget.TimePicker(getContext());
        return picker;
    }

    @java.lang.Override
    protected void onBindDialogView(android.view.View v) {
        __SmPLUnsupported__(0).onBindDialogView(v);
        picker.setHour(lastMinute);
        picker.setCurrentMinute(lastMinute);
    }

    @java.lang.Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        if (positiveResult) {
            lastHour = picker.getCurrentHour();
            lastMinute = picker.getCurrentMinute();
            java.lang.String time = (java.lang.String.valueOf(lastHour) + ":") + java.lang.String.valueOf(lastMinute);
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
        lastHour = bsol.andapp001.TimePreference.getHour(time);
        lastMinute = bsol.andapp001.TimePreference.getMinute(time);
    }
}