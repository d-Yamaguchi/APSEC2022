package com.voice.widget;
public class timePreference extends android.preference.DialogPreference {
    public android.widget.LinearLayout ll;

    public android.content.Context context;

    android.widget.TimePicker timePicker;

    public timePreference(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        this.context = context;
    }

    @java.lang.Override
    protected void onPrepareDialogBuilder(android.app.AlertDialog.Builder builder) {
        // TODO Auto-generated method stub
        super.onPrepareDialogBuilder(builder);
        ll = new android.widget.LinearLayout(context);
        ll.setLayoutParams(new android.widget.LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
        ll.setGravity(android.view.Gravity.CENTER);
        timePicker = new android.widget.TimePicker(context);
        timePicker.setIs24HourView(true);
        ll.addView(timePicker);
        builder.setView(ll);
    }

    @java.lang.Override
    protected void onBindDialogView(android.view.View view) {
        // TODO Auto-generated method stub
        super.onBindDialogView(view);
    }

    @java.lang.Override
    protected void onDialogClosed(boolean positiveResult) {
        // TODO Auto-generated method stub
        if (positiveResult) {
            java.util.HashMap<java.lang.String, java.lang.Integer> map = new java.util.HashMap<java.lang.String, java.lang.Integer>();
            map.put("hour", timePicker.getHour());
            map.put("minute", timePicker.getCurrentMinute());
            java.lang.String ifAm = " ����";
            if ()
                ifAm = " ����";

            int minute = map.get("minute");
            java.lang.String mi = java.lang.String.valueOf(minute);
            if (minute < 10)
                mi = "0" + minute;

            this.persistString(((("" + timePicker.getHour()) + ":") + mi) + ifAm);
            this.getOnPreferenceChangeListener().onPreferenceChange(this, map);
        }
        __SmPLUnsupported__(0).onDialogClosed(positiveResult);
    }
}