package me.shyboy.mengma;
import me.shyboy.mengma.Common.Sign;
import me.shyboy.mengma.Common.SignConfig;
import me.shyboy.mengma.Common.User;
import me.shyboy.mengma.database.UserHelper;
import me.shyboy.mengma.methods.OkHttpUtil;
public class NewSignActivity extends android.app.Activity {
    private android.widget.ImageButton bt_out;

    private android.widget.Button bt_commit;

    private android.widget.TimePicker tp_start;

    private android.widget.TimePicker tp_end;

    private android.widget.EditText et_decription;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_new_sign);
        bt_commit = ((android.widget.Button) (findViewById(R.id.newsign_commit)));
        et_decription = ((android.widget.EditText) (findViewById(R.id.newsign_et_description)));
        bt_out = ((android.widget.ImageButton) (findViewById(R.id.newsign_out)));
        bt_out.setOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View v) {
                finish();
            }
        });
        initTimePicker();
        bt_commit.setOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View v) {
                if (me.shyboy.mengma.Common.SignConfig.isNetworkConnected(NewSignActivity.this) == false) {
                    android.widget.Toast.makeText(NewSignActivity.this, "凑 ~ ~ 没联网", android.widget.Toast.LENGTH_SHORT).show();
                    return;
                }
                java.lang.String description = et_decription.getText().toString();
                if (description.length() == 0) {
                    android.widget.Toast.makeText(NewSignActivity.this, "描述内容不能为空.", android.widget.Toast.LENGTH_SHORT).show();
                    return;
                }
                java.lang.String start_at = ((reFormatTime(tp_start.getCurrentHour()) + ":") + reFormatTime(tp_start.getMinute())) + ":00";
                java.lang.String end_at = ((reFormatTime(tp_end.getCurrentHour()) + ":") + reFormatTime(tp_start.getMinute())) + ":00";
                me.shyboy.mengma.Common.User user = new me.shyboy.mengma.database.UserHelper(NewSignActivity.this).getUser();
                me.shyboy.mengma.Common.Sign sign = new me.shyboy.mengma.Common.Sign(user.getSno(), start_at, end_at, description, user.getAccess_token());
                new me.shyboy.mengma.methods.OkHttpUtil(NewSignActivity.this).newSign(sign);
            }
        });
    }

    private java.lang.String reFormatTime(int t) {
        if (t < 10)
            return "0" + t;

        return "" + t;
    }

    private void initTimePicker() {
        tp_start = ((android.widget.TimePicker) (findViewById(R.id.newsign_time_start)));
        tp_end = ((android.widget.TimePicker) (findViewById(R.id.newsign_time_end)));
        tp_start.setIs24HourView(true);
        tp_end.setIs24HourView(true);
        tp_end.setOnTimeChangedListener(new android.widget.TimePicker.OnTimeChangedListener() {
            @java.lang.Override
            public void onTimeChanged(android.widget.TimePicker view, int hourOfDay, int minute) {
                int h_start = tp_start.getCurrentHour();
                int m_start = tp_start.getCurrentMinute();
                int timestart = (h_start * 100) + m_start;
                int hour = tp_end.getCurrentHour();
                int m = tp_end.getCurrentMinute();
                int timeend = (hour * 100) + m;
                if (timestart > timeend) {
                    android.widget.Toast.makeText(NewSignActivity.this, "截止时间不合理", android.widget.Toast.LENGTH_SHORT).show();
                    tp_end.setCurrentHour(h_start);
                    tp_end.setCurrentMinute(m_start);
                }
            }
        });
    }
}