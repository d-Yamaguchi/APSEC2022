@java.lang.Override
protected void onCreate(android.os.Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    rand = new java.util.Random();
    // init clock faces
    timePicker1 = ((android.widget.TimePicker) (findViewById(R.id.timePicker1)));
    timePicker1.setIs24HourView(true);
    timePicker1.setOnTimeChangedListener(new android.widget.TimePicker.OnTimeChangedListener() {
        @java.lang.Override
        public void onTimeChanged(android.widget.TimePicker view, int hourOfDay, int minute) {
            updateCurrentPasswordText();
        }
    });
    timePicker2 = ((android.widget.TimePicker) (findViewById(R.id.timePicker2)));
    timePicker2.setIs24HourView(true);
    timePicker2.setVisibility(android.view.View.INVISIBLE);
    timePicker2.setOnTimeChangedListener(new android.widget.TimePicker.OnTimeChangedListener() {
        @java.lang.Override
        public void onTimeChanged(android.widget.TimePicker view, int hourOfDay, int minute) {
            updateCurrentPasswordText();
        }
    });
    timePicker3 = ((android.widget.TimePicker) (findViewById(R.id.timePicker3)));
    timePicker3.setIs24HourView(true);
    timePicker3.setVisibility(android.view.View.INVISIBLE);
    timePicker3.setOnTimeChangedListener(new android.widget.TimePicker.OnTimeChangedListener() {
        @java.lang.Override
        public void onTimeChanged(android.widget.TimePicker view, int hourOfDay, int minute) {
            updateCurrentPasswordText();
        }
    });
    timePicker4 = ((android.widget.TimePicker) (findViewById(R.id.timePicker4)));
    timePicker4.setIs24HourView(true);
    timePicker4.setVisibility(android.view.View.INVISIBLE);
    timePicker4.setOnTimeChangedListener(new android.widget.TimePicker.OnTimeChangedListener() {
        @java.lang.Override
        public void onTimeChanged(android.widget.TimePicker view, int hourOfDay, int minute) {
            updateCurrentPasswordText();
        }
    });
    // init buttons for selecting clock faces
    clock1 = ((android.widget.Button) (findViewById(R.id.button)));
    clock1.setBackgroundColor(android.graphics.Color.RED);
    clock1.setOnClickListener(new android.view.View.OnClickListener() {
        @java.lang.Override
        public void onClick(android.view.View v) {
            if (timePicker1.getVisibility() == android.view.View.INVISIBLE) {
                clock1.setBackgroundColor(android.graphics.Color.RED);
                clock2.setBackgroundColor(android.graphics.Color.WHITE);
                clock3.setBackgroundColor(android.graphics.Color.WHITE);
                clock4.setBackgroundColor(android.graphics.Color.WHITE);
                timePicker1.setVisibility(android.view.View.VISIBLE);
                timePicker2.setVisibility(android.view.View.INVISIBLE);
                timePicker3.setVisibility(android.view.View.INVISIBLE);
                timePicker4.setVisibility(android.view.View.INVISIBLE);
            }
        }
    });
    clock2 = ((android.widget.Button) (findViewById(R.id.button2)));
    clock2.setOnClickListener(new android.view.View.OnClickListener() {
        @java.lang.Override
        public void onClick(android.view.View v) {
            if (timePicker2.getVisibility() == android.view.View.INVISIBLE) {
                clock1.setBackgroundColor(android.graphics.Color.WHITE);
                clock2.setBackgroundColor(android.graphics.Color.RED);
                clock3.setBackgroundColor(android.graphics.Color.WHITE);
                clock4.setBackgroundColor(android.graphics.Color.WHITE);
                timePicker1.setVisibility(android.view.View.INVISIBLE);
                timePicker2.setVisibility(android.view.View.VISIBLE);
                timePicker3.setVisibility(android.view.View.INVISIBLE);
                timePicker4.setVisibility(android.view.View.INVISIBLE);
            }
        }
    });
    clock3 = ((android.widget.Button) (findViewById(R.id.button3)));
    clock3.setOnClickListener(new android.view.View.OnClickListener() {
        @java.lang.Override
        public void onClick(android.view.View v) {
            if (timePicker3.getVisibility() == android.view.View.INVISIBLE) {
                clock1.setBackgroundColor(android.graphics.Color.WHITE);
                clock2.setBackgroundColor(android.graphics.Color.WHITE);
                clock3.setBackgroundColor(android.graphics.Color.RED);
                clock4.setBackgroundColor(android.graphics.Color.WHITE);
                timePicker1.setVisibility(android.view.View.INVISIBLE);
                timePicker2.setVisibility(android.view.View.INVISIBLE);
                timePicker3.setVisibility(android.view.View.VISIBLE);
                timePicker4.setVisibility(android.view.View.INVISIBLE);
            }
        }
    });
    clock4 = ((android.widget.Button) (findViewById(R.id.button4)));
    clock4.setOnClickListener(new android.view.View.OnClickListener() {
        @java.lang.Override
        public void onClick(android.view.View v) {
            if (timePicker4.getVisibility() == android.view.View.INVISIBLE) {
                clock1.setBackgroundColor(android.graphics.Color.WHITE);
                clock2.setBackgroundColor(android.graphics.Color.WHITE);
                clock3.setBackgroundColor(android.graphics.Color.WHITE);
                clock4.setBackgroundColor(android.graphics.Color.RED);
                timePicker1.setVisibility(android.view.View.INVISIBLE);
                timePicker2.setVisibility(android.view.View.INVISIBLE);
                timePicker3.setVisibility(android.view.View.INVISIBLE);
                timePicker4.setVisibility(android.view.View.VISIBLE);
            }
        }
    });
    setTime();
    // init current password display
    current_password = ((android.widget.TextView) (findViewById(R.id.CurrentPassword)));
    android.widget.TimePicker _CVAR0 = timePicker1;
    java.lang.Integer _CVAR1 = _CVAR0.getCurrentHour();
    java.lang.String _CVAR2 = "Current: " + _CVAR1;
    java.lang.String _CVAR3 = _CVAR2 + ":";
    java.lang.String _CVAR4 = _CVAR3 + timePicker1.getCurrentMinute();
    java.lang.String _CVAR5 = _CVAR4 + ",    ";
    android.widget.TimePicker _CVAR16 = timePicker2;
    java.lang.Integer _CVAR17 = _CVAR16.getCurrentHour();
    java.lang.String _CVAR6 = _CVAR5 + _CVAR17;
    java.lang.String _CVAR7 = _CVAR6 + ":";
    java.lang.String _CVAR8 = _CVAR7 + timePicker2.getCurrentMinute();
    java.lang.String _CVAR9 = _CVAR8 + ",    ";
    android.widget.TimePicker _CVAR18 = timePicker3;
    java.lang.Integer _CVAR19 = _CVAR18.getCurrentHour();
    java.lang.String _CVAR10 = _CVAR9 + _CVAR19;
    java.lang.String _CVAR11 = _CVAR10 + ":";
    java.lang.String _CVAR12 = _CVAR11 + timePicker3.getCurrentMinute();
    java.lang.String _CVAR13 = _CVAR12 + ",    ";
    android.widget.TimePicker _CVAR20 = timePicker4;
    java.lang.Integer _CVAR21 = _CVAR20.getCurrentHour();
    java.lang.String _CVAR14 = _CVAR13 + _CVAR21;
    java.lang.String _CVAR15 = _CVAR14 + ":";
    java.lang.String new_output = _CVAR15 + timePicker4.getCurrentMinute();
    current_password.setText(new_output, android.widget.TextView.BufferType.SPANNABLE);
    // T or F password checker
    password_check = ((android.widget.TextView) (findViewById(R.id.PasswordCheck)));
    password_check.setText("", android.widget.TextView.BufferType.SPANNABLE);
    // password to learn
    password = ((android.widget.TextView) (findViewById(R.id.passwordTextView)));
    com.opticnerve.nccc.User data = new com.opticnerve.nccc.User();
    java.lang.String type = "";
    int[] goal_password = new int[8];
    if (com.opticnerve.nccc.MainActivity.proceedCounter == 0) {
        type = "Gmail";
        goal_password = data.getGmail_pass();
    }
    if (com.opticnerve.nccc.MainActivity.proceedCounter == 1) {
        type = "Facebook";
        goal_password = data.getFacebook_pass();
    }
    if (com.opticnerve.nccc.MainActivity.proceedCounter == 2) {
        type = "Bank";
        goal_password = data.getBank_pass();
    }
    java.lang.String goal_output = (((((((((((((("Goal: " + goal_password[0]) + ":") + goal_password[1]) + ",    ") + goal_password[2]) + ":") + goal_password[3]) + ",    ") + goal_password[4]) + ":") + goal_password[5]) + ",    ") + goal_password[6]) + ":") + goal_password[7];
    password.setText(goal_output, android.widget.TextView.BufferType.SPANNABLE);
    // init password type
    password_type = ((android.widget.TextView) (findViewById(R.id.PasswordType)));
    password_type.setText(type, android.widget.TextView.BufferType.SPANNABLE);
    // init action buttons
    check_button = ((android.widget.Button) (findViewById(R.id.CheckButton)));
    check_button.setOnClickListener(new android.view.View.OnClickListener() {
        @java.lang.Override
        public void onClick(android.view.View v) {
            java.lang.String temp;
            if (comparePassword()) {
                temp = "true";
                password_check.setTextColor(android.graphics.Color.GREEN);
            } else {
                temp = "false";
                password_check.setTextColor(android.graphics.Color.RED);
            }
            password_check.setText(temp, android.widget.TextView.BufferType.SPANNABLE);
        }
    });
    proceed_button = ((android.widget.Button) (findViewById(R.id.ProceedButton)));
    proceed_button.setOnClickListener(new android.view.View.OnClickListener() {
        @java.lang.Override
        public void onClick(android.view.View v) {
            if (com.opticnerve.nccc.MainActivity.proceedCounter < 2) {
                com.opticnerve.nccc.MainActivity.proceedCounter += 1;
                startActivity(new android.content.Intent(MainActivity.this, com.opticnerve.nccc.MainActivity.class));
            } else {
                com.opticnerve.nccc.MainActivity.proceedCounter = 0;
                startActivity(new android.content.Intent(MainActivity.this, com.opticnerve.nccc.HomePage.class));
            }
        }
    });
}