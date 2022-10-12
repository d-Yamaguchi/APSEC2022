@java.lang.Override
protected void onCreate(android.os.Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add);
    android.app.ActionBar actionBar = getActionBar();
    actionBar.setHomeButtonEnabled(true);
    actionBar.setDisplayHomeAsUpEnabled(true);
    setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    daySwitches = getIntent().getIntegerArrayListExtra("daySwitches");
    nightSwitches = getIntent().getIntegerArrayListExtra("nightSwitches");
    currentDay = getIntent().getStringExtra("day");
    dorn = getIntent().getStringExtra("type");
    day = ((android.widget.RadioButton) (findViewById(R.id.radioButton)));
    night = ((android.widget.RadioButton) (findViewById(R.id.radioButton2)));
    timePicker = ((android.widget.TimePicker) (findViewById(R.id.timePicker)));
    timePicker.setIs24HourView(true);
    if (dorn.equals("day") || dorn.equals("night")) {
        getActionBar().setTitle("Add Switch for " + currentDay);
        day.setVisibility(android.view.View.INVISIBLE);
        night.setVisibility(android.view.View.INVISIBLE);
        lastHour = getIntent().getIntExtra("lastHour", 11);
        lastMinute = getIntent().getIntExtra("lastMinute", 00);
        if (lastHour != 23) {
            int _CVAR1 = 1;
            android.widget.TimePicker _CVAR0 = timePicker;
            int _CVAR2 = lastHour + _CVAR1;
            _CVAR0.setCurrentHour(_CVAR2);
        } else {
            android.widget.TimePicker _CVAR3 = timePicker;
            int _CVAR4 = 23;
            _CVAR3.setCurrentHour(_CVAR4);
        }
    } else if (dorn.equals("editday")) {
        getActionBar().setTitle("Edit Switch");
        day.setVisibility(android.view.View.VISIBLE);
        night.setVisibility(android.view.View.VISIBLE);
        day.setChecked(true);
        night.setChecked(false);
        index = java.lang.Integer.valueOf(getIntent().getStringExtra("pressedSwitch").substring(1, 2)) - 1;
        lastHour = daySwitches.get(index) / 100;
        lastMinute = daySwitches.get(index) % 100;
        android.widget.TimePicker _CVAR5 = timePicker;
        int _CVAR6 = lastHour;
        _CVAR5.setCurrentHour(_CVAR6);
    } else if (dorn.equals("editnight")) {
        getActionBar().setTitle("Edit Switch");
        day.setVisibility(android.view.View.VISIBLE);
        night.setVisibility(android.view.View.VISIBLE);
        day.setChecked(false);
        night.setChecked(true);
        index = java.lang.Integer.valueOf(getIntent().getStringExtra("pressedSwitch").substring(1, 2)) - 1;
        lastHour = nightSwitches.get(index) / 100;
        lastMinute = nightSwitches.get(index) % 100;
        android.widget.TimePicker _CVAR7 = timePicker;
        int _CVAR8 = lastHour;
        _CVAR7.setCurrentHour(_CVAR8);
    } else if (dorn.equals("edittime")) {
        getActionBar().setTitle("Change time to...");
        day.setVisibility(android.view.View.INVISIBLE);
        night.setVisibility(android.view.View.INVISIBLE);
        lastHour = getIntent().getIntExtra("lastHour", 12);
        lastMinute = getIntent().getIntExtra("lastMinute", 00);
        android.widget.TimePicker _CVAR9 = timePicker;
        int _CVAR10 = lastHour;
        _CVAR9.setCurrentHour(_CVAR10);
    } else if (dorn.equals("add")) {
        getActionBar().setTitle("Add Switch for " + currentDay);
        day.setVisibility(android.view.View.VISIBLE);
        night.setVisibility(android.view.View.VISIBLE);
        day.setChecked(true);
        night.setChecked(false);
        lastHour = getIntent().getIntExtra("lastHour", 00);
        lastMinute = getIntent().getIntExtra("lastMinute", 00);
        android.widget.TimePicker _CVAR11 = timePicker;
        int _CVAR12 = lastHour;
        _CVAR11.setCurrentHour(_CVAR12);
    }
    timePicker.setCurrentMinute(lastMinute);
    confirm = ((android.widget.Button) (findViewById(R.id.button4)));
    confirm.setOnClickListener(new android.view.View.OnClickListener() {
        @java.lang.Override
        public void onClick(android.view.View v) {
            int pickedHour = timePicker.getCurrentHour();
            int pickedMinute = timePicker.getCurrentMinute();
            int time = (pickedHour * 100) + pickedMinute;
            if (dorn.equals("day") || dorn.equals("night")) {
                boolean match = false;
                if (dorn.equals("day") && (daySwitches.size() == 5)) {
                    android.widget.Toast.makeText(getApplicationContext(), "Maximum number of day switches reached", android.widget.Toast.LENGTH_LONG).show();
                    return;
                } else if (dorn.equals("night") && (nightSwitches.size() == 5)) {
                    android.widget.Toast.makeText(getApplicationContext(), "Maximum number of night switches reached", android.widget.Toast.LENGTH_LONG).show();
                    return;
                }
                for (int i : daySwitches) {
                    if (time == i) {
                        match = true;
                    }
                }
                for (int i : nightSwitches) {
                    if (time == i) {
                        match = true;
                    }
                }
                if (match) {
                    android.widget.Toast.makeText(getApplicationContext(), "Duplicate switch found", android.widget.Toast.LENGTH_LONG).show();
                    return;
                }
                android.content.Intent returnIntent = new android.content.Intent(getApplicationContext(), group28.thermostat.DayActivity.class);
                returnIntent.putExtra("lastHour", pickedHour);
                returnIntent.putExtra("lastMinute", pickedMinute);
                returnIntent.putExtra("type", dorn);
                returnIntent.putExtra("time", java.lang.Integer.toString(time));
                setResult(android.app.Activity.RESULT_OK, returnIntent);
                finish();
            } else if (dorn.equals("editday")) {
                if (day.isChecked()) {
                    int currentTime = daySwitches.get(index);
                    if (time == currentTime) {
                        android.content.Intent returnIntent = new android.content.Intent(getApplicationContext(), group28.thermostat.DayActivity.class);
                        setResult(android.app.Activity.RESULT_CANCELED, returnIntent);
                        finish();
                        return;
                    } else {
                        boolean match = false;
                        for (int i : daySwitches) {
                            if (time == i) {
                                match = true;
                            }
                        }
                        if (match) {
                            android.widget.Toast.makeText(getApplicationContext(), "Duplicate switch found", android.widget.Toast.LENGTH_LONG).show();
                            return;
                        }
                        android.content.Intent returnIntent = new android.content.Intent(getApplicationContext(), group28.thermostat.DayActivity.class);
                        returnIntent.putExtra("lastHour", pickedHour);
                        returnIntent.putExtra("lastMinute", pickedMinute);
                        returnIntent.putExtra("type", dorn);
                        returnIntent.putExtra("time", java.lang.Integer.toString(time));
                        returnIntent.putExtra("index", index);
                        setResult(android.app.Activity.RESULT_OK, returnIntent);
                        finish();
                    }
                } else {
                    boolean match = false;
                    if (nightSwitches.size() == 5) {
                        android.widget.Toast.makeText(getApplicationContext(), "Maximum number of night switches reached", android.widget.Toast.LENGTH_LONG).show();
                        return;
                    }
                    for (int i : nightSwitches) {
                        if (time == i) {
                            match = true;
                        }
                    }
                    if (match) {
                        android.widget.Toast.makeText(getApplicationContext(), "Duplicate switch found", android.widget.Toast.LENGTH_LONG).show();
                        return;
                    }
                    android.content.Intent returnIntent = new android.content.Intent(getApplicationContext(), group28.thermostat.DayActivity.class);
                    returnIntent.putExtra("lastHour", pickedHour);
                    returnIntent.putExtra("lastMinute", pickedMinute);
                    returnIntent.putExtra("type", "editday2");
                    returnIntent.putExtra("time", java.lang.Integer.toString(time));
                    returnIntent.putExtra("index", index);
                    setResult(android.app.Activity.RESULT_OK, returnIntent);
                    finish();
                }
            } else if (dorn.equals("editnight")) {
                if (night.isChecked()) {
                    int currentTime = nightSwitches.get(index);
                    if (time == currentTime) {
                        android.content.Intent returnIntent = new android.content.Intent(getApplicationContext(), group28.thermostat.DayActivity.class);
                        setResult(android.app.Activity.RESULT_CANCELED, returnIntent);
                        finish();
                        return;
                    } else {
                        boolean match = false;
                        for (int i : nightSwitches) {
                            if (time == i) {
                                match = true;
                            }
                        }
                        if (match) {
                            android.widget.Toast.makeText(getApplicationContext(), "Duplicate switch found", android.widget.Toast.LENGTH_LONG).show();
                            return;
                        }
                        android.content.Intent returnIntent = new android.content.Intent(getApplicationContext(), group28.thermostat.DayActivity.class);
                        returnIntent.putExtra("lastHour", pickedHour);
                        returnIntent.putExtra("lastMinute", pickedMinute);
                        returnIntent.putExtra("type", dorn);
                        returnIntent.putExtra("time", java.lang.Integer.toString(time));
                        returnIntent.putExtra("index", index);
                        setResult(android.app.Activity.RESULT_OK, returnIntent);
                        finish();
                    }
                } else {
                    boolean match = false;
                    if (daySwitches.size() == 5) {
                        android.widget.Toast.makeText(getApplicationContext(), "Maximum number of night switches reached", android.widget.Toast.LENGTH_LONG).show();
                        return;
                    }
                    for (int i : daySwitches) {
                        if (time == i) {
                            match = true;
                        }
                    }
                    if (match) {
                        android.widget.Toast.makeText(getApplicationContext(), "Duplicate switch found", android.widget.Toast.LENGTH_LONG).show();
                        return;
                    }
                    android.content.Intent returnIntent = new android.content.Intent(getApplicationContext(), group28.thermostat.DayActivity.class);
                    returnIntent.putExtra("lastHour", pickedHour);
                    returnIntent.putExtra("lastMinute", pickedMinute);
                    returnIntent.putExtra("type", "editnight2");
                    returnIntent.putExtra("time", java.lang.Integer.toString(time));
                    returnIntent.putExtra("index", index);
                    setResult(android.app.Activity.RESULT_OK, returnIntent);
                    finish();
                }
            } else if (dorn.equals("edittime")) {
                android.content.Intent returnIntent = new android.content.Intent(getApplicationContext(), group28.thermostat.DayActivity.class);
                returnIntent.putExtra("newHour", pickedHour);
                returnIntent.putExtra("newMinute", pickedMinute);
                returnIntent.putExtra("pickedDay", currentDay);
                setResult(android.app.Activity.RESULT_OK, returnIntent);
                finish();
            } else if (dorn.equals("add")) {
                boolean match = false;
                if (day.isChecked() && (daySwitches.size() == 5)) {
                    android.widget.Toast.makeText(getApplicationContext(), "Maximum number of day switches reached", android.widget.Toast.LENGTH_LONG).show();
                    return;
                } else if (night.isChecked() && (nightSwitches.size() == 5)) {
                    android.widget.Toast.makeText(getApplicationContext(), "Maximum number of night switches reached", android.widget.Toast.LENGTH_LONG).show();
                    return;
                }
                for (int i : daySwitches) {
                    if (time == i) {
                        match = true;
                    }
                }
                for (int i : nightSwitches) {
                    if (time == i) {
                        match = true;
                    }
                }
                if (match) {
                    android.widget.Toast.makeText(getApplicationContext(), "Duplicate switch found", android.widget.Toast.LENGTH_LONG).show();
                    return;
                }
                if (day.isChecked()) {
                    dorn = "day";
                } else if (night.isChecked()) {
                    dorn = "night";
                }
                android.content.Intent returnIntent = new android.content.Intent(getApplicationContext(), group28.thermostat.MainActivity.class);
                returnIntent.putExtra("lastHour", pickedHour);
                returnIntent.putExtra("lastMinute", pickedMinute);
                returnIntent.putExtra("type", dorn);
                returnIntent.putExtra("time", java.lang.Integer.toString(time));
                returnIntent.putExtra("day", currentDay);
                setResult(android.app.Activity.RESULT_OK, returnIntent);
                finish();
            }
        }
    });
    cancel = ((android.widget.Button) (findViewById(R.id.button5)));
    cancel.setOnClickListener(new android.view.View.OnClickListener() {
        @java.lang.Override
        public void onClick(android.view.View v) {
            android.content.Intent returnIntent = new android.content.Intent(getApplicationContext(), group28.thermostat.DayActivity.class);
            setResult(android.app.Activity.RESULT_CANCELED, returnIntent);
            finish();
        }
    });
}