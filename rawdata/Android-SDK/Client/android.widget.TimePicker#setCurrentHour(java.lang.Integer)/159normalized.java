@java.lang.Override
public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
    android.util.Log.i("SW", "RESUME");
    f.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
    java.text.SimpleDateFormat m = new java.text.SimpleDateFormat("mm");
    m.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
    timeselect.setIs24HourView(true);
    android.view.LayoutInflater _CVAR0 = inflater;
    void _CVAR1 = R.layout.time;
    android.view.ViewGroup _CVAR2 = container;
    boolean _CVAR3 = false;
    // Inflate the layout for this fragment
    android.view.View v = _CVAR0.inflate(_CVAR1, _CVAR2, _CVAR3);
    android.view.View _CVAR4 = v;
    void _CVAR5 = R.id.timepickertime;
    android.widget.TimePicker timeselect = ((android.widget.TimePicker) (_CVAR4.findViewById(_CVAR5)));
    java.lang.String _CVAR7 = "HH";
    java.text.SimpleDateFormat f = new java.text.SimpleDateFormat(_CVAR7);
    java.text.SimpleDateFormat _CVAR8 = f;
    java.util.Date _CVAR9 = new java.util.Date();
    java.lang.String _CVAR10 = _CVAR8.format(_CVAR9);
    android.widget.TimePicker _CVAR6 = timeselect;
    java.lang.Integer _CVAR11 = java.lang.Integer.valueOf(_CVAR10);
    _CVAR6.setCurrentHour(_CVAR11);
    timeselect.setCurrentMinute(java.lang.Integer.valueOf(m.format(new java.util.Date())));
    f = new java.text.SimpleDateFormat("HHmm");
    f.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
    selhour = f.format(new java.util.Date()).substring(0, 2);
    selmin = f.format(new java.util.Date()).substring(2, 4);
    searchbutton = ((android.widget.Button) (v.findViewById(R.id.bsearchtime)));
    searchbutton.setOnClickListener(new android.view.View.OnClickListener() {
        public void onClick(android.view.View v) {
            android.widget.TimePicker timeselect = ((android.widget.TimePicker) (getActivity().findViewById(R.id.timepickertime)));
            java.lang.String strmin = java.lang.Integer.toString(timeselect.getCurrentMinute());
            java.lang.String strhour = java.lang.Integer.toString(timeselect.getCurrentHour());
            if (strmin.length() == 1) {
                strmin = "0" + strmin;
            }
            if (strhour.length() == 1) {
                strhour = "0" + strhour;
            }
            selmin = strmin;
            selhour = strhour;
            android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
            android.support.v4.app.Fragment mFragmentR = new com.msi.shortwave.results();
            android.os.Bundle bund1 = new android.os.Bundle();
            bund1.putString("station", "");
            android.widget.Spinner mySpn = ((android.widget.Spinner) (getActivity().findViewById(R.id.targetspin)));
            int spnItem = mySpn.getSelectedItemPosition();
            seltarget = targetids.get(spnItem);
            mySpn = ((android.widget.Spinner) (getActivity().findViewById(R.id.langspin)));
            spnItem = mySpn.getSelectedItemPosition();
            sellang = langids.get(spnItem);
            sellangid = sellang;
            seltargetid = seltarget;
            bund1.putString("time", selhour + selmin);
            bund1.putString("target", seltarget);
            bund1.putString("language", sellang);
            bund1.putString("freq", "");
            mFragmentR.setArguments(bund1);
            if ((getResources().getConfiguration().screenLayout & android.content.res.Configuration.SCREENLAYOUT_SIZE_MASK) == android.content.res.Configuration.SCREENLAYOUT_SIZE_LARGE) {
                ft.replace(R.id.fragment_content2, mFragmentR, null);
            } else if ((getResources().getConfiguration().screenLayout & android.content.res.Configuration.SCREENLAYOUT_SIZE_MASK) == android.content.res.Configuration.SCREENLAYOUT_SIZE_XLARGE) {
                ft.replace(R.id.fragment_content2, mFragmentR, null);
            } else {
                ft.replace(R.id.fragment_content, mFragmentR, null);
            }
            ft.addToBackStack(null);
            ft.commit();
        }
    });
    targetids.add("999");
    android.widget.Spinner spinner = ((android.widget.Spinner) (v.findViewById(R.id.targetspin)));
    android.widget.ArrayAdapter<java.lang.String> adapterForSpinner = new android.widget.ArrayAdapter<java.lang.String>(getActivity(), android.R.layout.simple_spinner_item);
    adapterForSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    adapterForSpinner.add(getString(R.string.alltargets));
    spinner.setAdapter(adapterForSpinner);
    android.database.sqlite.SQLiteDatabase myDB = null;
    myDB = getActivity().openOrCreateDatabase("shortwave", getActivity().MODE_PRIVATE, null);
    android.database.Cursor c = myDB.rawQuery("SELECT target, code FROM targets;", null);
    if (c.moveToFirst()) {
        do {
            adapterForSpinner.add(c.getString(0));
            targetids.add(c.getString(1));
        } while (c.moveToNext() );
    }
    c.close();
    spinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
        public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int pos, long id) {
            seltarget = java.lang.Integer.toString(pos);
        }

        public void onNothingSelected(android.widget.AdapterView<?> parent) {
            // Do nothing.
        }
    });
    langids.add("999");
    langids.add("");
    android.widget.Spinner langspinner = ((android.widget.Spinner) (v.findViewById(R.id.langspin)));
    android.widget.ArrayAdapter<java.lang.String> adapterForLangSpinner = new android.widget.ArrayAdapter<java.lang.String>(getActivity(), android.R.layout.simple_spinner_item);
    adapterForSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    adapterForLangSpinner.add(getString(R.string.anylanguage));
    adapterForLangSpinner.add(getString(R.string.notvoicelanguage));
    langspinner.setAdapter(adapterForLangSpinner);
    c = myDB.rawQuery("SELECT language, code FROM languages", null);
    if (c.moveToFirst()) {
        do {
            adapterForLangSpinner.add(c.getString(0));
            langids.add(c.getString(1));
        } while (c.moveToNext() );
    }
    c.close();
    myDB.close();
    langspinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
        public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int pos, long id) {
            sellang = java.lang.Integer.toString(pos);
        }

        public void onNothingSelected(android.widget.AdapterView<?> parent) {
            // Do nothing.
        }
    });
    return v;
}