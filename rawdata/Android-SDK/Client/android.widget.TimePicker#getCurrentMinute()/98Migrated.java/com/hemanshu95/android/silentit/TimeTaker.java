package com.hemanshu95.android.silentit;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
public class TimeTaker extends android.support.v7.app.ActionBarActivity {
    android.widget.Button ok;

    java.lang.String pos;

    android.os.Bundle data;

    android.widget.TimePicker tp;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_taker);
        ok = ((android.widget.Button) (findViewById(R.id.bokt)));
        tp = ((android.widget.TimePicker) (findViewById(R.id.timePicker)));
        android.content.Intent intent = getIntent();
        // ok=(Button)findViewById(R.id.bokt);
        if ((intent != null) && intent.hasExtra("data")) {
            data = intent.getParcelableExtra("data");
        }
        ok.setOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View v) {
                // String x=tp.getCurrentHour()+":"+tp.getCurrentMinute();
                if (data.getString("position").equals("1")) {
                    data.putString("timestarth", java.lang.Integer.toString(tp.getCurrentHour()));
                    data.putString("timestartm", java.lang.Integer.toString(tp.getMinute()));
                } else {
                    data.putString("timeendh", java.lang.Integer.toString(tp.getCurrentHour()));
                    data.putString("timeendm", java.lang.Integer.toString(tp.getMinute()));
                }
                android.content.Intent intent = new android.content.Intent(getApplication(), com.hemanshu95.android.silentit.Input.class).putExtra("data", data);
                // .putExtra("hours",tp.getCurrentHour())
                // .putExtra("minutes",tp.getCurrentMinute());
                startActivity(intent);
            }
        });
    }

    @java.lang.Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_time_taker, menu);
        return true;
    }

    @java.lang.Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        // noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}