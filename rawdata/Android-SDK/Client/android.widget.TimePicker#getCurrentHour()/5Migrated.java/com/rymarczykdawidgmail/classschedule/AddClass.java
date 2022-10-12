package com.rymarczykdawidgmail.classschedule;
/**
 * Created by JAN on 2015-11-07.
 */
public class AddClass extends android.app.Activity {
    android.widget.Button Confirm;

    android.widget.EditText ClassName;

    android.widget.TimePicker StartTime;

    android.widget.TimePicker EndTime;

    android.widget.RadioGroup Day;

    android.widget.RadioButton WhichDay;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_class);
        Confirm = ((android.widget.Button) (findViewById(R.id.Confirm)));
        ClassName = ((android.widget.EditText) (findViewById(R.id.classNameIn)));
        StartTime = ((android.widget.TimePicker) (findViewById(R.id.startTimeIN)));
        EndTime = ((android.widget.TimePicker) (findViewById(R.id.endTimeIn)));
        Day = ((android.widget.RadioGroup) (findViewById(R.id.Day)));
        Confirm.setOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View v) {
                int selectedID = Day.getCheckedRadioButtonId();
                WhichDay = ((android.widget.RadioButton) (findViewById(selectedID)));
                if (isFulfillCorrectly()) {
                    intent.putExtra("ClassName", ClassName.getText().toString());
                    intent.putExtra("StartTimeH", StartTime.getHour());
                    android.content.Intent intent = new android.content.Intent();
                    intent.putExtra("EndTimeH", StartTime.getHour());
                    intent.putExtra("StartTimeM", StartTime.getCurrentMinute());
                    intent.putExtra("EndTimeM", EndTime.getCurrentMinute());
                    intent.putExtra("Day", WhichDay.getText().toString());
                    setResult(android.app.Activity.RESULT_OK, intent);
                    finish();
                } else {
                    android.widget.Toast.makeText(getApplicationContext(), "Wrong input parameters. Correct it!", android.widget.Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean isFulfillCorrectly() {
        return (((((WhichDay.getText().toString() != null) && (ClassName.getText().toString() != null)) && (StartTime.getCurrentHour() <= EndTime.getCurrentHour())) && (StartTime.getCurrentHour() >= 8)) && (EndTime.getCurrentHour() <= 20)) && ((StartTime.getCurrentHour() == EndTime.getCurrentHour()) && (StartTime.getCurrentMinute() >= EndTime.getCurrentMinute()) ? 0 == 1 : 1 == 1);
    }
}