public TimeSelectDialog(android.content.Context context) {
    super(context);
    requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
    setContentView(R.layout.custom_time_select_dialog);
    timePicker = ((android.widget.TimePicker) (findViewById(R.id.timePicker1)));
    datePicker = ((android.widget.DatePicker) (findViewById(R.id.datePicker1)));
    android.content.SharedPreferences settings = getContext().getSharedPreferences("veg", 0);
    java.lang.String savedList = settings.getString("list", "");
    java.lang.String[] listSplit = savedList.split(" ");
    for (int i = 0; i < listSplit.length; i++) {
        for (int y = 0; y < listSplit[i].length(); y++) {
            if (listSplit[i].charAt(y) == ',') {
                java.lang.String holdNumber = listSplit[i].substring(0, y);
                recipes.add(data.recipes.get(java.lang.Integer.parseInt(holdNumber)));
            }
        }
    }
    // Get longest recipe
    for (int i = 0; i < recipes.size(); i++) {
        int recipeLength = 0;
        for (int y = 0; y < recipes.get(i).steps.size(); y++) {
            if (recipes.get(i).section.equals("main")) {
                if (recipes.get(i).steps.get(y).time == 0.0F) {
                    java.lang.String holdNumber = listSplit[i].substring(listSplit[i].lastIndexOf(',') + 1);
                    recipes.get(i).steps.get(y).time = java.lang.Integer.parseInt(holdNumber);
                    recipeLength = recipeLength + recipes.get(i).steps.get(y).time;
                } else {
                    recipeLength = recipeLength + recipes.get(i).steps.get(y).time;
                }
            } else {
                recipeLength = recipeLength + recipes.get(i).steps.get(y).time;
            }
        }
        if (recipeLength > longestRecipe) {
            longestRecipe = recipeLength;
        }
    }
    java.lang.System.out.println("LONGEST: " + longestRecipe);
    longestRecipe = longestRecipe + 20;
    int minutes = longestRecipe % 60;
    java.lang.System.out.println(hours);
    java.lang.System.out.println(minutes);
    int _CVAR1 = 60;
    int hours = longestRecipe / _CVAR1;// since both are ints, you get an int

    int _CVAR2 = hours;
    android.widget.TimePicker _CVAR0 = timePicker;
    int _CVAR3 = timePicker.getCurrentHour() + _CVAR2;
    _CVAR0.setCurrentHour(_CVAR3);
    if ((timePicker.getCurrentMinute() + minutes) >= 60) {
        int _CVAR4 = 23;
        boolean _CVAR5 = timePicker.getCurrentHour() == _CVAR4;
        if () {
            timePicker.setCurrentMinute(timePicker.getCurrentMinute() + minutes);
            timePicker.setCurrentHour(0);
        } else {
            timePicker.setCurrentMinute(timePicker.getCurrentMinute() + minutes);
            int _CVAR7 = 1;
            android.widget.TimePicker _CVAR6 = timePicker;
            int _CVAR8 = timePicker.getCurrentHour() + _CVAR7;
            _CVAR6.setCurrentHour(_CVAR8);
        }
    } else {
        timePicker.setCurrentMinute(timePicker.getCurrentMinute() + minutes);
    }
    datePicker.clearFocus();
    timePicker.clearFocus();
    android.widget.TimePicker _CVAR11 = timePicker;
    java.util.Calendar _CVAR9 = mustStart;
    int _CVAR10 = java.util.Calendar.HOUR_OF_DAY;
    java.lang.Integer _CVAR12 = _CVAR11.getCurrentHour();
    _CVAR9.set(_CVAR10, _CVAR12);
    mustStart.set(java.util.Calendar.MINUTE, timePicker.getCurrentMinute());
    mustStart.set(java.util.Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
    mustStart.set(java.util.Calendar.MONTH, datePicker.getMonth());
    mustStart.set(java.util.Calendar.YEAR, datePicker.getYear());
    android.widget.Button dialogButton = ((android.widget.Button) (findViewById(R.id.dialogButtonOK)));
    dialogButton.setOnClickListener(new android.view.View.OnClickListener() {
        public void onClick(android.view.View v) {
            dismiss();
        }
    });
    void _CVAR13 = R.id.button1;
    android.widget.Button cookButton = ((android.widget.Button) (findViewById(_CVAR13)));
    android.widget.Button _CVAR14 = cookButton;
    android.view.View.OnClickListener _CVAR15 = new android.view.View.OnClickListener() {
        public void onClick(android.view.View v) {
            timePicker.clearFocus();
            hour = timePicker.getCurrentHour();
            minute = timePicker.getCurrentMinute();
            datePicker.clearFocus();
            day = datePicker.getDayOfMonth();
            month = datePicker.getMonth();
            year = datePicker.getYear();
            java.util.Calendar selected = java.util.Calendar.getInstance();
            selected.set(java.util.Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
            selected.set(java.util.Calendar.MINUTE, timePicker.getCurrentMinute());
            selected.set(java.util.Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
            selected.set(java.util.Calendar.MONTH, datePicker.getMonth());
            selected.set(java.util.Calendar.YEAR, datePicker.getYear());
            if (selected.before(mustStart)) {
                android.widget.Toast.makeText(getContext(), "Oh no! The meal won't be ready if you start cooking now. Choose a later time to eat.", android.widget.Toast.LENGTH_SHORT).show();
            } else {
                android.content.Intent intent = new android.content.Intent(getContext(), com.dontburnthe.turkey.Timer.class);
                intent.putExtra("minute", minute);
                intent.putExtra("hour", hour);
                intent.putExtra("day", day);
                intent.putExtra("month", month);
                intent.putExtra("year", year);
                getContext().startActivity(intent);
            }
        }
    };
    _CVAR14.setOnClickListener(_CVAR15);
}