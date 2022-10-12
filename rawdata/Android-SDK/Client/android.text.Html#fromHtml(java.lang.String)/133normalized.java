@java.lang.SuppressWarnings("unchecked")
public void initilize() {
    loaderDialog = new android.app.Dialog(this, android.R.style.Theme_Translucent);
    loaderDialog.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
    loaderDialog.getWindow().setGravity(android.view.Gravity.TOP);
    loaderDialog.setContentView(R.layout.dialog_loader);
    loaderDialog.setCancelable(false);
    SpnGender = ((android.widget.Spinner) (findViewById(R.id.Spn_gender)));
    spCountrycode = ((android.widget.Spinner) (findViewById(R.id.spn_countrycode)));
    SpnTitle = ((android.widget.Spinner) (findViewById(R.id.Spn_title)));
    SpnNationality = ((android.widget.Spinner) (findViewById(R.id.spn_nationality)));
    EdtemailId = ((android.widget.EditText) (findViewById(R.id.edt_emailid)));
    EdtFname = ((android.widget.EditText) (findViewById(R.id.edt_fname)));
    EdtLname = ((android.widget.EditText) (findViewById(R.id.edt_lname)));
    edt_dob = ((android.widget.EditText) (findViewById(R.id.edt_dob)));
    edt_mob = ((android.widget.EditText) (findViewById(R.id.edt_Mobileno)));
    pass = ((android.widget.EditText) (findViewById(R.id.edt_new_pass)));
    cpass = ((android.widget.EditText) (findViewById(R.id.edt_c_pass)));
    txt_Title = ((android.widget.TextView) (findViewById(R.id.txt_title)));
    txt_fname = ((android.widget.TextView) (findViewById(R.id.txt_fname)));
    txt_lname = ((android.widget.TextView) (findViewById(R.id.txt_lname)));
    txt_DOB = ((android.widget.TextView) (findViewById(R.id.txt_dob)));
    txt_gender = ((android.widget.TextView) (findViewById(R.id.txt_gender)));
    txt_mobileno = ((android.widget.TextView) (findViewById(R.id.txt_phno)));
    txt_nationality = ((android.widget.TextView) (findViewById(R.id.txt_nationality)));
    EdtemailId.setFocusable(false);
    EdtemailId.setText(EmailAddress);
    android.widget.ArrayAdapter<java.lang.CharSequence> adapter = android.widget.ArrayAdapter.createFromResource(this, R.array.title_spinner_items, R.layout.tv_spinner);
    adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
    SpnGender.setAdapter(adapter);
    SpnGender.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
        @java.lang.Override
        public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
            if (position == 1) {
                gCode = "M";
            } else if (position == 2) {
                gCode = "F";
            }
            // Log.e("SpnGender", gCode);
        }

        @java.lang.Override
        public void onNothingSelected(android.widget.AdapterView<?> parent) {
        }
    });
    adapter1 = android.widget.ArrayAdapter.createFromResource(this, R.array.my_profile_Title, R.layout.tv_spinner);
    adapter1.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
    SpnTitle.setAdapter(adapter1);
    SpnTitle.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
        @java.lang.Override
        public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
            Titles = arrTitle[position];
            android.util.Log.e("SpnTitle", Titles);
        }

        @java.lang.Override
        public void onNothingSelected(android.widget.AdapterView<?> parent) {
        }
    });
    Countrycode = new java.lang.String[]{ "+1", "+1 284", "+1 340", "+1 345", "+1 649", "+1 670", "+1 758", "+1 784", "+1 869", "+1242", "+1246", "+1264", "+1268", "+1441", "+1473", "+1664", "+1671", "+1684", "+1767", "+1809", "+1876", "+20", "+212", "+213", "+216", "+218", "+220", "+221", "+222", "+223", "+224", "+225", "+226", "+227", "+228", "+229", "+230", "+231", "+232", "+233", "+234", "+235", "+236", "+237", "+238", "+239", "+240", "+241", "+242", "+243", "+244", "+245", "+246", "+248", "+249", "+250", "+251", "+252", "+253", "+254", "+255", "+256", "+257", "+258", "+260", "+261", "+262", "+263", "+264", "+265", "+266", "+267", "+268", "+269", "+27", "+290", "+291", "+297", "+298", "+299", "+30", "+31", "+32", "+33", "+34", "+350", "+351", "+352", "+353", "+354", "+355", "+356", "+357", "+358", "+359", "+36", "+370", "+371", "+372", "+373", "+374", "+375", "+376", "+378", "+380", "+381", "+382", "+385", "+386", "+387", "+389", "+39", "+40", "+41", "+420", "+421", "+423", "+43", "+44", "+45", "+46", "+47", "+48", "+49", "+500", "+501", "+502", "+503", "+504", "+505", "+506", "+507", "+508", "+509", "+51", "+52", "+53", "+54", "+55", "+56", "+57", "+58", "+590", "+591", "+592", "+593", "+594", "+595", "+596", "+597", "+598", "+599", "+60", "+61", "+62", "+63", "+64", "+65", "+66", "+670", "+672", "+673", "+674", "+675", "+676", "+677", "+678", "+679", "+680", "+681", "+682", "+683", "+685", "+686", "+687", "+688", "+689", "+690", "+691", "+692", "+699", "+7", "+81", "+82", "+84", "+850", "+852", "+853", "+855", "+856", "+86", "+880", "+886", "+90", "+91", "+92", "+93", "+94", "+95", "+960", "+961", "+962", "+963", "+964", "+965", "+966", "+967", "+968", "+970", "+971", "+972", "+973", "+974", "+975", "+976", "+977", "+98", "+992", "+993", "+994", "+995", "+996", "+998" };
    android.widget.ArrayAdapter<java.lang.String> adapter2 = new android.widget.ArrayAdapter<java.lang.String>(this, R.layout.tv_spinner, Countrycode);
    adapter2.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
    spCountrycode.setAdapter(adapter2);
    spCountrycode.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
        @java.lang.Override
        public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
            ccode = spCountrycode.getSelectedItem().toString();
            android.util.Log.e("Countrycode", ccode);
        }

        @java.lang.Override
        public void onNothingSelected(android.widget.AdapterView<?> parent) {
        }
    });
    android.widget.ArrayAdapter<java.lang.String> adapter3 = new android.widget.ArrayAdapter<java.lang.String>(this, R.layout.tv_spinner, arrayCountry);
    adapter3.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
    SpnNationality.setAdapter(adapter3);
    SpnNationality.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
        @java.lang.Override
        public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
            Ncode = SpnNationality.getSelectedItem().toString();
            Ncode = Ncode.substring(java.lang.Math.max(Ncode.length() - 2, 0));
            android.util.Log.e("SpnNationality", Ncode);
        }

        @java.lang.Override
        public void onNothingSelected(android.widget.AdapterView<?> parent) {
        }
    });
    SpnNationality.setSelection(((android.widget.ArrayAdapter<java.lang.String>) (SpnNationality.getAdapter())).getPosition("UNITED STATES - US"));
    java.lang.String textTitle = "<font color=#000000>Title</font><font color=#e32c18> *</font>";
    java.lang.String _CVAR1 = textTitle;
    android.widget.TextView _CVAR0 = txt_Title;
    android.text.Spanned _CVAR2 = android.text.Html.fromHtml(_CVAR1);
    _CVAR0.setText(_CVAR2);
    java.lang.String textfname = "<font color=#000000>First Name</font><font color=#e32c18> *</font>";
    java.lang.String _CVAR4 = textfname;
    android.widget.TextView _CVAR3 = txt_fname;
    android.text.Spanned _CVAR5 = android.text.Html.fromHtml(_CVAR4);
    _CVAR3.setText(_CVAR5);
    java.lang.String textlname = "<font color=#000000>Last Name</font><font color=#e32c18> *</font>";
    java.lang.String _CVAR7 = textlname;
    android.widget.TextView _CVAR6 = txt_lname;
    android.text.Spanned _CVAR8 = android.text.Html.fromHtml(_CVAR7);
    _CVAR6.setText(_CVAR8);
    java.lang.String textDob = "<font color=#000000>Date of Birth</font><font color=#e32c18> *</font>";
    java.lang.String _CVAR10 = textDob;
    android.widget.TextView _CVAR9 = txt_DOB;
    android.text.Spanned _CVAR11 = android.text.Html.fromHtml(_CVAR10);
    _CVAR9.setText(_CVAR11);
    java.lang.String textgender = "<font color=#000000>Gender</font><font color=#e32c18> *</font>";
    java.lang.String _CVAR13 = textgender;
    android.widget.TextView _CVAR12 = txt_gender;
    android.text.Spanned _CVAR14 = android.text.Html.fromHtml(_CVAR13);
    _CVAR12.setText(_CVAR14);
    java.lang.String textmobilno = "<font color=#000000>Phone Number</font><font color=#e32c18> *</font>";
    java.lang.String _CVAR16 = textmobilno;
    android.widget.TextView _CVAR15 = txt_mobileno;
    android.text.Spanned _CVAR17 = android.text.Html.fromHtml(_CVAR16);
    _CVAR15.setText(_CVAR17);
    java.lang.String textnationality = "<font color=#000000>Nationality</font><font color=#e32c18> *</font>";
    java.lang.String _CVAR19 = textnationality;
    android.widget.TextView _CVAR18 = txt_nationality;
    android.text.Spanned _CVAR20 = android.text.Html.fromHtml(_CVAR19);
    _CVAR18.setText(_CVAR20);
    if (!CommonFunctions.lang.equals("en")) {
        java.lang.String textTitlear = "<font color=#000000>اللقب</font><font color=#e32c18> *</font>";
        java.lang.String _CVAR22 = textTitlear;
        android.widget.TextView _CVAR21 = txt_Title;
        android.text.Spanned _CVAR23 = android.text.Html.fromHtml(_CVAR22);
        _CVAR21.setText(_CVAR23);
        java.lang.String textfnamear = "<font color=#000000>الاسم الأول</font><font color=#e32c18> *</font>";
        java.lang.String _CVAR25 = textfnamear;
        android.widget.TextView _CVAR24 = txt_fname;
        android.text.Spanned _CVAR26 = android.text.Html.fromHtml(_CVAR25);
        _CVAR24.setText(_CVAR26);
        java.lang.String textlnamear = "<font color=#000000>اسم العائلة</font><font color=#e32c18> *</font>";
        java.lang.String _CVAR28 = textlnamear;
        android.widget.TextView _CVAR27 = txt_lname;
        android.text.Spanned _CVAR29 = android.text.Html.fromHtml(_CVAR28);
        _CVAR27.setText(_CVAR29);
        java.lang.String textDobar = "<font color=#000000>تاريخ الميلاد</font><font color=#e32c18> *</font>";
        java.lang.String _CVAR31 = textDobar;
        android.widget.TextView _CVAR30 = txt_DOB;
        android.text.Spanned _CVAR32 = android.text.Html.fromHtml(_CVAR31);
        _CVAR30.setText(_CVAR32);
        java.lang.String textgenderar = "<font color=#000000>الجنس</font><font color=#e32c18> *</font>";
        java.lang.String _CVAR34 = textgenderar;
        android.widget.TextView _CVAR33 = txt_gender;
        android.text.Spanned _CVAR35 = android.text.Html.fromHtml(_CVAR34);
        _CVAR33.setText(_CVAR35);
        java.lang.String textmobilnoar = "<font color=#000000>رقم الهاتف النقال</font><font color=#e32c18> *</font>";
        java.lang.String _CVAR37 = textmobilnoar;
        android.widget.TextView _CVAR36 = txt_mobileno;
        android.text.Spanned _CVAR38 = android.text.Html.fromHtml(_CVAR37);
        _CVAR36.setText(_CVAR38);
        java.lang.String textnationalityar = "<font color=#000000>الجنسية</font><font color=#e32c18> *</font>";
        java.lang.String _CVAR40 = textnationalityar;
        android.widget.TextView _CVAR39 = txt_nationality;
        android.text.Spanned _CVAR41 = android.text.Html.fromHtml(_CVAR40);
        _CVAR39.setText(_CVAR41);
    }
}