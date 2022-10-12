package com.esquel.epass.ui;
public class QuarterTimePicker extends android.widget.TimePicker {
    private static final int TIME_PICKER_MINUTE_INTERVAL = 15;

    private android.widget.TimePicker.OnTimeChangedListener timeChangedListener;

    public QuarterTimePicker(android.content.Context context) {
        this(context, null);
    }

    public QuarterTimePicker(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
        try {
            java.lang.Class<?> classForId = java.lang.Class.forName("com.android.internal.R$id");
            java.lang.reflect.Field field = classForId.getField("minute");
            android.widget.NumberPicker minuteSpinner = ((android.widget.NumberPicker) (this.findViewById(field.getInt(null))));
            boolean isSupportAPI10 = android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.GINGERBREAD_MR1;
            if (isSupportAPI10) {
                minuteSpinner.setMaxValue((60 / com.esquel.epass.ui.QuarterTimePicker.TIME_PICKER_MINUTE_INTERVAL) - 1);
            }
            java.util.List<java.lang.String> displayedValues = new java.util.ArrayList<java.lang.String>();
            for (int i = 0; i < 60; i += com.esquel.epass.ui.QuarterTimePicker.TIME_PICKER_MINUTE_INTERVAL) {
                displayedValues.add(java.lang.String.format("%02d", i));
            }
            if (isSupportAPI10) {
                minuteSpinner.setDisplayedValues(displayedValues.toArray(new java.lang.String[displayedValues.size()]));
            } else {
                java.lang.reflect.Method method = minuteSpinner.getClass().getMethod("setRange", new java.lang.Class[]{ int.class, int.class, java.lang.String[].class });
                method.invoke(minuteSpinner, new java.lang.Object[]{ 0, (60 / com.esquel.epass.ui.QuarterTimePicker.TIME_PICKER_MINUTE_INTERVAL) - 1, displayedValues.toArray(new java.lang.String[displayedValues.size()]) });
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        } catch (java.lang.Error e) {
            e.printStackTrace();
        }
    }

    private int maxMinuteIndex() {
        return (60 / com.esquel.epass.ui.QuarterTimePicker.TIME_PICKER_MINUTE_INTERVAL) - 1;
    }

    @java.lang.Override
    public void setOnTimeChangedListener(android.widget.TimePicker.OnTimeChangedListener onTimeChangedListener) {
        super.setOnTimeChangedListener(onTimeChangedListener);
        this.timeChangedListener = onTimeChangedListener;
    }

    @java.lang.Override
    public java.lang.Integer getCurrentMinute() {
        return super.getCurrentMinute() * com.esquel.epass.ui.QuarterTimePicker.TIME_PICKER_MINUTE_INTERVAL;
    }

    @java.lang.Override
    public void setCurrentMinute(java.lang.Integer currentMinute) {
        if ((currentMinute % com.esquel.epass.ui.QuarterTimePicker.TIME_PICKER_MINUTE_INTERVAL) > 0) {
            if (cleanMinute == maxMinuteIndex()) {
                cleanMinute = 0;
                setCurrentHour(getCurrentHour() + 1);
            } else {
                cleanMinute++;
            }
        }
        int cleanMinute = currentMinute / com.esquel.epass.ui.QuarterTimePicker.TIME_PICKER_MINUTE_INTERVAL;
        TimePicker picker = null;
        picker.setMinute(TIME_PICKER_MINUTE_INTERVAL);
    }

    // We want to proxy all the calls to our member variable
    // OnTimeChangedListener with our own
    // internal listener in order to make sure our overridden getCurrentMinute
    // is called. Without
    // this some versions of android return the underlying minute index.
    private android.widget.TimePicker.OnTimeChangedListener internalTimeChangedListener = new android.widget.TimePicker.OnTimeChangedListener() {
        @java.lang.Override
        public void onTimeChanged(android.widget.TimePicker view, int hourOfDay, int minute) {
            timeChangedListener.onTimeChanged(view, getCurrentHour(), getCurrentMinute());
        }
    };
}