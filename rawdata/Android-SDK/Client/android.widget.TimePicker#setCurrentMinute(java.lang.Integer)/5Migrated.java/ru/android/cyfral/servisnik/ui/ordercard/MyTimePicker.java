package ru.android.cyfral.servisnik.ui.ordercard;
public class MyTimePicker extends android.widget.TimePicker {
    private static final int TIME_PICKER_MINUTE_INTERVAL = 5;

    private android.widget.TimePicker.OnTimeChangedListener timeChangedListener;

    public MyTimePicker(android.content.Context context) {
        super(context);
        try {
            java.lang.Class<?> classForId = java.lang.Class.forName("com.android.internal.R$id");
            java.lang.reflect.Field field = classForId.getField("minute");
            android.widget.NumberPicker minuteSpinner = ((android.widget.NumberPicker) (this.findViewById(field.getInt(null))));
            minuteSpinner.setMaxValue((60 / ru.android.cyfral.servisnik.ui.ordercard.MyTimePicker.TIME_PICKER_MINUTE_INTERVAL) - 1);
            java.util.List<java.lang.String> displayedValues = new java.util.ArrayList<>();
            for (int i = 0; i < 60; i += ru.android.cyfral.servisnik.ui.ordercard.MyTimePicker.TIME_PICKER_MINUTE_INTERVAL)
                displayedValues.add(java.lang.String.format("%02d", i));

            minuteSpinner.setDisplayedValues(displayedValues.toArray(new java.lang.String[displayedValues.size()]));
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    private int maxMinuteIndex() {
        return (60 / ru.android.cyfral.servisnik.ui.ordercard.MyTimePicker.TIME_PICKER_MINUTE_INTERVAL) - 1;
    }

    @java.lang.Override
    public void setOnTimeChangedListener(android.widget.TimePicker.OnTimeChangedListener onTimeChangedListener) {
        super.setOnTimeChangedListener(internalTimeChangedListener);
        this.timeChangedListener = onTimeChangedListener;
    }

    @java.lang.Override
    public java.lang.Integer getCurrentMinute() {
        return super.getCurrentMinute() * ru.android.cyfral.servisnik.ui.ordercard.MyTimePicker.TIME_PICKER_MINUTE_INTERVAL;
    }

    @java.lang.Override
    public void setCurrentMinute(java.lang.Integer currentMinute) {
        if ((currentMinute % ru.android.cyfral.servisnik.ui.ordercard.MyTimePicker.TIME_PICKER_MINUTE_INTERVAL) > 0) {
            if (cleanMinute == maxMinuteIndex()) {
                cleanMinute = 0;
                setCurrentHour(getCurrentHour() + 1);
            } else {
                cleanMinute++;
            }
        }
        int cleanMinute = currentMinute / ru.android.cyfral.servisnik.ui.ordercard.MyTimePicker.TIME_PICKER_MINUTE_INTERVAL;
        TimePicker picker = null;
        picker.setMinute(TIME_PICKER_MINUTE_INTERVAL);
    }

    // We want to proxy all the calls to our member variable OnTimeChangedListener with our own
    // internal listener in order to make sure our overridden getCurrentMinute is called. Without
    // this some versions of android return the underlying minute index.
    private android.widget.TimePicker.OnTimeChangedListener internalTimeChangedListener = new android.widget.TimePicker.OnTimeChangedListener() {
        @java.lang.Override
        public void onTimeChanged(android.widget.TimePicker view, int hourOfDay, int minute) {
            timeChangedListener.onTimeChanged(view, getCurrentHour(), getCurrentMinute());
        }
    };
}