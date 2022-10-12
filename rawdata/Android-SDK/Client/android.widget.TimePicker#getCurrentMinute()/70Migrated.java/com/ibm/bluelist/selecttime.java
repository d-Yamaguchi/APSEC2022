package com.ibm.bluelist;
public class selecttime extends android.app.Activity implements android.view.View.OnClickListener {
    android.widget.TimePicker tp1;

    android.widget.TextView itv;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selecttime);
        java.lang.System.out.println("Getting Text view");
        itv = ((android.widget.TextView) (findViewById(R.id.game)));
        android.widget.Button b = ((android.widget.Button) (findViewById(R.id.ne)));
        tp1 = ((android.widget.TimePicker) (findViewById(R.id.timePicker1)));
        android.content.Intent i = getIntent();
        java.lang.String product = i.getStringExtra("product");
        java.lang.System.out.println("Selected product: " + product);
        itv.setText(product);
        final java.util.Calendar c = java.util.Calendar.getInstance();
        int hour = c.get(java.util.Calendar.HOUR_OF_DAY);
        int minute = c.get(java.util.Calendar.MINUTE);
        tp1.setCurrentHour(hour);
        tp1.setCurrentMinute(minute);
        b.setOnClickListener(this);
    }

    public void onClick(android.view.View v) {
        java.lang.System.out.println("inside button click");
        int hour;
        int minute;
        java.lang.System.out.println("Selected time h: " + tp1.getCurrentHour());
        java.lang.System.out.println("Selected time m: " + tp1.getMinute());
        java.lang.String sres = itv.getText().toString();
        android.content.Intent i = new android.content.Intent(getApplicationContext(), com.ibm.bluelist.lastscreen.class);
        i.putExtra("a", sres);
        int a = tp1.getCurrentHour();
        java.lang.String ti;
        if (a >= 12) {
            ti = ((new java.lang.Integer(a).toString() + ":") + tp1.getMinute().toString()) + "PM";
        } else {
            ti = ((new java.lang.Integer(a).toString() + ":") + tp1.getMinute().toString()) + "AM";
        }
        i.putExtra("b", ti);
        startActivity(i);
    }
}