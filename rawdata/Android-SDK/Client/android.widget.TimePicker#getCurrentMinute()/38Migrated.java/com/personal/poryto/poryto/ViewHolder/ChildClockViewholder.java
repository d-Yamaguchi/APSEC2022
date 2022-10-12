package com.personal.poryto.poryto.ViewHolder;
import android.support.v7.app.AlertDialog;
import com.personal.poryto.poryto.R;
import com.personal.poryto.poryto.Ulti.Ulti;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
/**
 * Created by user on 9/10/2017.
 */
public class ChildClockViewholder extends com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder implements android.view.View.OnClickListener {
    private android.widget.TextView txt_workTime;

    private android.widget.TextView txt_breakTime;

    private android.widget.CheckBox cn_viberation;

    private android.widget.Spinner sp_rington;

    private android.content.Context context;

    private android.content.SharedPreferences sharedPreferences;

    public ChildClockViewholder(android.view.View itemView, android.content.Context context) {
        super(itemView);
        this.context = context;
        txt_workTime = itemView.findViewById(R.id.txt_work_time);
        txt_breakTime = itemView.findViewById(R.id.txt_break_time);
        cn_viberation = itemView.findViewById(R.id.cb_viberation);
        txt_workTime.setOnClickListener(this);
        txt_breakTime.setOnClickListener(this);
        // sp_rington = itemView.findViewById(R.id.ringtone);
    }

    @java.lang.Override
    public void onClick(android.view.View view) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        final android.widget.TimePicker timePicker = new android.widget.TimePicker(_CVAR0);
        switch (view.getId()) {
            case R.id.txt_work_time :
                builder.setTitle("Set Work Time");
                timePicker.setIs24HourView(true);
                builder.setView(timePicker);
                builder.setPositiveButton("Set", new android.content.DialogInterface.OnClickListener() {
                    @java.lang.Override
                    public void onClick(android.content.DialogInterface dialogInterface, int i) {
                        com.personal.poryto.poryto.Ulti.Ulti.Loger((("Time is from View " + timePicker.getCurrentHour()) + ":") + timePicker.getMinute());
                        final android.widget.TimePicker timePicker = new android.widget.TimePicker(context);
                        txt_workTime.setText((timePicker.getCurrentHour() + ":") + timePicker.getMinute());
                    }
                });
                builder.show();
                break;
            case R.id.txt_break_time :
                builder.setTitle("Set Break Time");
                timePicker.setIs24HourView(true);
                builder.setView(timePicker);
                builder.setPositiveButton("Set", new android.content.DialogInterface.OnClickListener() {
                    @java.lang.Override
                    public void onClick(android.content.DialogInterface dialogInterface, int i) {
                        com.personal.poryto.poryto.Ulti.Ulti.Loger((("Time is from View " + timePicker.getCurrentHour()) + ":") + timePicker.getCurrentMinute());
                        txt_breakTime.setText((timePicker.getCurrentHour() + ":") + timePicker.getCurrentMinute());
                    }
                });
                builder.show();
                break;
        }
    }
}