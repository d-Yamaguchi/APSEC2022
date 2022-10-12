@java.lang.Override
public void onClick(android.view.View v) {
    dialog1.setContentView(R.layout.edit_popup1);
    dialog1.setTitle("Edit Event Information");
    android.content.Context _CVAR0 = context;
    android.content.Context _CVAR4 = _CVAR0;
    final android.app.Dialog dialog1 = new android.app.Dialog(_CVAR4);
    try {
        final android.widget.EditText tname = ((android.widget.EditText) (dialog1.findViewById(R.id.event123)));
        final android.widget.EditText tdesc = ((android.widget.EditText) (dialog1.findViewById(R.id.desc123)));
        tname.setText(state1[1]);
        tdesc.setText(state1[2]);
        java.lang.String[] tiime = state1[state1.length - 1].split(":");
        timePicker.setCurrentHour(java.lang.Integer.parseInt(tiime[0]));
        android.widget.TimePicker _CVAR3 = timePicker;
        final int hour = _CVAR3.getCurrentHour();
        timePicker.setCurrentMinute(java.lang.Integer.parseInt(tiime[1].substring(0, 2)));
        final int min = timePicker.getCurrentMinute();
        android.app.Dialog _CVAR1 = dialog1;
        void _CVAR2 = R.id.tp1;
        android.app.Dialog _CVAR5 = _CVAR1;
        void _CVAR6 = _CVAR2;
        final android.widget.TimePicker timePicker = ((android.widget.TimePicker) (_CVAR5.findViewById(_CVAR6)));
        android.widget.TimePicker _CVAR7 = timePicker;
        android.widget.TimePicker.OnTimeChangedListener _CVAR8 = new android.widget.TimePicker.OnTimeChangedListener() {
            int hour = timePicker.getCurrentHour();

            int min = timePicker.getCurrentMinute();

            @java.lang.Override
            public void onTimeChanged(android.widget.TimePicker view, int hourOfDay, int minute) {
                if ((hour != timePicker.getCurrentHour()) || (min != timePicker.getCurrentMinute())) {
                    hour = timePicker.getCurrentHour();
                    min = timePicker.getCurrentMinute();
                    if (min > 9) {
                        if (hour >= 12) {
                            time = (((hour - 12) + ":") + min) + " PM";
                        } else {
                            time = ((hour + ":") + min) + " AM";
                        }
                    } else if (hour >= 12) {
                        time = (((hour - 12) + ":0") + min) + " PM";
                    } else {
                        time = ((hour + ":0") + min) + " AM";
                    }
                }
            }
        };
        _CVAR7.setOnTimeChangedListener(_CVAR8);
        android.widget.Button saveBut = ((android.widget.Button) (dialog1.findViewById(R.id.save)));
        saveBut.setOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View v) {
                try {
                    java.io.FileOutputStream fs1;
                    if (tname.getText() != null) {
                        state1[1] = "" + tname.getText().toString();
                        temp2 = state1[1];
                    }
                    if (tdesc.getText() != null) {
                        state1[2] = "" + tdesc.getText().toString();
                    }
                    // time=hour+":"+min;
                    if (!time.equals("x")) {
                        state1[state1.length - 1] = time;
                    }
                    android.widget.Toast.makeText(getApplicationContext(), "Edit Successful!", android.widget.Toast.LENGTH_LONG).show();
                    if (temp2.equals(word)) {
                        fs1 = context.openFileOutput(word + ".txt", android.content.Context.MODE_PRIVATE);
                    } else {
                        fs1 = context.openFileOutput(temp2 + ".txt", android.content.Context.MODE_PRIVATE);
                        test = false;
                    }
                    java.io.OutputStreamWriter myOutWriter1 = new java.io.OutputStreamWriter(fs1);
                    for (final java.lang.String recopy : state1) {
                        myOutWriter1.append(recopy);
                        myOutWriter1.append("\n");
                    }
                    myOutWriter1.close();
                    fs1.close();
                    if (java.util.Arrays.asList(state).contains(temp1)) {
                        java.util.Arrays.asList(state).set(java.util.Arrays.asList(state).indexOf(temp1), temp2);
                    }
                    try {
                        java.io.FileOutputStream fs2 = context.openFileOutput("database.txt", android.content.Context.MODE_PRIVATE);
                        java.io.OutputStreamWriter myOutWriter2 = new java.io.OutputStreamWriter(fs2);
                        for (final java.lang.String recopy1 : state) {
                            myOutWriter2.append(recopy1);
                            myOutWriter2.append("\n");
                        }
                        myOutWriter2.close();
                        fs2.close();
                    } catch (java.lang.Exception e) {
                    }
                    if (test == false) {
                        java.io.File file = new java.io.File(("/data/data/com.example.retentionscheduler/files/" + temp1) + ".txt");
                        boolean deleted = file.delete();
                    }
                    dialog1.dismiss();
                    dialog.dismiss();
                    finish();
                    startActivity(getIntent());
                } catch (java.lang.Exception e) {
                }
            }
        });
    } catch (java.lang.Exception e) {
    }
    dialog1.show();
}