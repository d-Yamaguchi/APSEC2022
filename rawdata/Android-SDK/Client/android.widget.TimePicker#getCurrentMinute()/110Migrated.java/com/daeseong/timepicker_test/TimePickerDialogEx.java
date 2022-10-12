package com.daeseong.timepicker_test;
public class TimePickerDialogEx extends android.app.Dialog {
    private static final java.lang.String TAG = com.daeseong.timepicker_test.TimePickerDialogEx.class.getSimpleName();

    public int BUTTON_TYPE = android.app.Dialog.BUTTON_NEGATIVE;

    private android.widget.Button button1;

    private android.widget.Button button2;

    private android.widget.TimePicker timepicker1;

    private int nhour;

    private int nMinute;

    public TimePickerDialogEx(android.content.Context context) {
        super(context);
    }

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timepicker_dialog);
        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View v) {
                BUTTON_TYPE = android.app.Dialog.BUTTON_NEGATIVE;
                dismiss();
            }
        });
        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View v) {
                BUTTON_TYPE = android.app.Dialog.BUTTON_POSITIVE;
                dismiss();
            }
        });
        timepicker1 = ((android.widget.TimePicker) (findViewById(R.id.timepicker1)));
        timepicker1.setIs24HourView(true);
        timepicker1.setOnTimeChangedListener(new android.widget.TimePicker.OnTimeChangedListener() {
            @java.lang.Override
            public void onTimeChanged(android.widget.TimePicker view, int hourOfDay, int minute) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    nhour = timepicker1.getHour();
                    nMinute = timepicker1.getMinute();
                } else {
                    nhour = timepicker1.getCurrentHour();
                    nMinute = timepicker1.getMinute();
                }
                android.util.Log.e(com.daeseong.timepicker_test.TimePickerDialogEx.TAG, (("nhour:" + nhour) + " nMinute:") + nMinute);
                android.util.Log.e(com.daeseong.timepicker_test.TimePickerDialogEx.TAG, (("hourOfDay:" + hourOfDay) + " minute:") + minute);
            }
        });
    }

    public java.lang.String getHour() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            nhour = timepicker1.getHour();
            nMinute = timepicker1.getMinute();
        } else {
            nhour = timepicker1.getCurrentHour();
            nMinute = timepicker1.getCurrentMinute();
        }
        return java.lang.Integer.toString(nhour);
    }

    public java.lang.String getMinute() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            nhour = timepicker1.getHour();
            nMinute = timepicker1.getMinute();
        } else {
            nhour = timepicker1.getCurrentHour();
            nMinute = timepicker1.getCurrentMinute();
        }
        return java.lang.Integer.toString(nMinute);
    }
}