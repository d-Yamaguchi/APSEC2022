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
    int hours = longestRecipe / 60;// since both are ints, you get an int

    java.lang.System.out.println(hours);
    java.lang.System.out.println(minutes);
    timePicker.setCurrentHour(timePicker.getCurrentHour() + hours);
    int _CVAR1 = minutes;
    int _CVAR2 = timePicker.getCurrentMinute() + _CVAR1;
    boolean _CVAR3 = _CVAR2 >= 60;
    int _CVAR0 = 60;
    int _CVAR5 = _CVAR0;
    int _CVAR9 = _CVAR5;
    int _CVAR13 = _CVAR9;
    int minutes = longestRecipe % _CVAR13;
    if () {
        if (timePicker.getCurrentHour() == 23) {
            int _CVAR6 = minutes;
            android.widget.TimePicker _CVAR4 = timePicker;
            int _CVAR7 = timePicker.getCurrentMinute() + _CVAR6;
            _CVAR4.setCurrentMinute(_CVAR7);
            timePicker.setCurrentHour(0);
        } else {
            int _CVAR10 = minutes;
            android.widget.TimePicker _CVAR8 = timePicker;
            int _CVAR11 = timePicker.getCurrentMinute() + _CVAR10;
            _CVAR8.setCurrentMinute(_CVAR11);
            timePicker.setCurrentHour(timePicker.getCurrentHour() + 1);
        }
    } else {
        int _CVAR14 = minutes;
        android.widget.TimePicker _CVAR12 = timePicker;
        int _CVAR15 = timePicker.getCurrentMinute() + _CVAR14;
        _CVAR12.setCurrentMinute(_CVAR15);
    }
    datePicker.clearFocus();
    timePicker.clearFocus();
    mustStart.set(java.util.Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
    android.widget.TimePicker _CVAR18 = timePicker;
    java.util.Calendar _CVAR16 = mustStart;
    int _CVAR17 = java.util.Calendar.MINUTE;
    java.lang.Integer _CVAR19 = _CVAR18.getCurrentMinute();
    _CVAR16.set(_CVAR17, _CVAR19);
    mustStart.set(java.util.Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
    mustStart.set(java.util.Calendar.MONTH, datePicker.getMonth());
    mustStart.set(java.util.Calendar.YEAR, datePicker.getYear());
    android.widget.Button dialogButton = ((android.widget.Button) (findViewById(R.id.dialogButtonOK)));
    dialogButton.setOnClickListener(new android.view.View.OnClickListener() {
        public void onClick(android.view.View v) {
            dismiss();
        }
    });
    void _CVAR20 = R.id.button1;
    android.widget.Button cookButton = ((android.widget.Button) (findViewById(_CVAR20)));
    android.widget.Button _CVAR21 = cookButton;
    android.view.View.OnClickListener _CVAR22 = new android.view.View.OnClickListener() {
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
    _CVAR21.setOnClickListener(_CVAR22);
}