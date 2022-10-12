package com.travel.flybooking;
import com.travel.common_handlers.HttpHandler;
import com.travel.common_handlers.UrlParameterBuilder;
import com.travel.flybooking.support.CommonFunctions;
import com.travel.flybooking.support.CustomDatePickerDialog;
public class MyProfileActivity extends android.app.Activity {
    final java.util.Calendar c = java.util.Calendar.getInstance();

    public java.lang.String[] arrTitle = new java.lang.String[]{ "Title", "Mr", "Miss", "Mrs" };

    android.widget.Spinner SpnTitle;

    android.widget.Spinner SpnGender;

    android.widget.Spinner spCountrycode;

    android.widget.Spinner SpnNationality;

    android.widget.EditText EdtFname;

    android.widget.EditText EdtLname;

    android.widget.EditText EdtemailId;

    android.widget.EditText edt_dob;

    android.widget.EditText edt_mob;

    android.widget.EditText pass;

    android.widget.EditText cpass;

    android.widget.TextView txt_Title;

    android.widget.TextView txt_fname;

    android.widget.TextView txt_lname;

    android.widget.TextView txt_DOB;

    android.widget.TextView txt_gender;

    android.widget.TextView txt_mobileno;

    android.widget.TextView txt_nationality;

    java.lang.String[] Countrycode;

    android.content.res.AssetManager am;

    android.widget.Button Update;

    android.widget.Button Updatedetails;

    java.util.ArrayList<java.lang.String> arrayCountry;

    java.util.ArrayList<java.lang.String> arrayCountryisocode;

    java.lang.String Titles;

    java.lang.String FirstName;

    java.lang.String MiddleName;

    java.lang.String LastName;

    java.lang.String DateOfBirth;

    java.lang.String Gender;

    java.lang.String MobileCode;

    java.lang.String MobileNumber;

    java.lang.String PassportNumber;

    java.lang.String Citizenship;

    java.lang.String PassengerId;

    android.app.Dialog loaderDialog;

    int mYear = c.get(java.util.Calendar.YEAR);

    int mMonth = c.get(java.util.Calendar.MONTH);

    int mDay = c.get(java.util.Calendar.DAY_OF_MONTH);

    int maxYear = mYear - 12;

    int maxMonth = mMonth;

    int maxDay = mDay + 1;

    int minYear = mYear - 100;

    int minMonth = mMonth;

    int minDay = mDay;

    int cday;

    int cmonth;

    int cyear;

    java.lang.String EmailAddress;

    java.lang.String ccode;

    java.lang.String Ncode;

    java.lang.String gCode;

    android.content.SharedPreferences pref;

    android.widget.ArrayAdapter<java.lang.CharSequence> adapter1;

    java.util.HashMap<java.lang.String, java.lang.String> map;

    com.travel.common_handlers.UrlParameterBuilder urlObj;

    private java.util.Locale myLocale;

    private android.app.DatePickerDialog datePickerDialog;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        getActionBar().hide();
        loadLocale();
        setContentView(R.layout.activity_myprofile);
        urlObj = new com.travel.common_handlers.UrlParameterBuilder();
        pref = getApplicationContext().getSharedPreferences("MyLoginPref", android.content.Context.MODE_PRIVATE);
        EmailAddress = pref.getString("Email", null);
        loadAssets();
        initilize();
        new com.travel.flybooking.MyProfileActivity.AccountDetails().execute();
    }

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
        SpnGender.setOnItemSelectedListener(__SmPLUnsupported__(0));
        adapter1 = android.widget.ArrayAdapter.createFromResource(this, R.array.my_profile_Title, R.layout.tv_spinner);
        adapter1.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        SpnTitle.setAdapter(adapter1);
        SpnTitle.setOnItemSelectedListener(__SmPLUnsupported__(1));
        Countrycode = __SmPLUnsupported__(2);
        android.widget.ArrayAdapter<java.lang.String> adapter2 = new android.widget.ArrayAdapter<java.lang.String>(this, R.layout.tv_spinner, Countrycode);
        adapter2.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        spCountrycode.setAdapter(adapter2);
        spCountrycode.setOnItemSelectedListener(__SmPLUnsupported__(3));
        android.widget.ArrayAdapter<java.lang.String> adapter3 = new android.widget.ArrayAdapter<java.lang.String>(this, R.layout.tv_spinner, arrayCountry);
        adapter3.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        SpnNationality.setAdapter(adapter3);
        SpnNationality.setOnItemSelectedListener(__SmPLUnsupported__(4));
        SpnNationality.setSelection(((android.widget.ArrayAdapter<java.lang.String>) (SpnNationality.getAdapter())).getPosition("UNITED STATES - US"));
        java.lang.String textTitle = "<font color=#000000>Title</font><font color=#e32c18> *</font>";
        txt_Title.setText(Html.fromHtml(textTitle, mYear));
        java.lang.String textfname = "<font color=#000000>First Name</font><font color=#e32c18> *</font>";
        txt_fname.setText(android.text.Html.fromHtml(textfname));
        java.lang.String textlname = "<font color=#000000>Last Name</font><font color=#e32c18> *</font>";
        txt_lname.setText(android.text.Html.fromHtml(textlname));
        java.lang.String textDob = "<font color=#000000>Date of Birth</font><font color=#e32c18> *</font>";
        txt_DOB.setText(android.text.Html.fromHtml(textDob));
        java.lang.String textgender = "<font color=#000000>Gender</font><font color=#e32c18> *</font>";
        txt_gender.setText(android.text.Html.fromHtml(textgender));
        java.lang.String textmobilno = "<font color=#000000>Phone Number</font><font color=#e32c18> *</font>";
        txt_mobileno.setText(android.text.Html.fromHtml(textmobilno));
        java.lang.String textnationality = "<font color=#000000>Nationality</font><font color=#e32c18> *</font>";
        txt_nationality.setText(android.text.Html.fromHtml(textnationality));
        if (!CommonFunctions.lang.equals("en")) {
            java.lang.String textTitlear = "<font color=#000000>اللقب</font><font color=#e32c18> *</font>";
            txt_Title.setText(android.text.Html.fromHtml(textTitlear));
            java.lang.String textfnamear = "<font color=#000000>الاسم الأول</font><font color=#e32c18> *</font>";
            txt_fname.setText(android.text.Html.fromHtml(textfnamear));
            java.lang.String textlnamear = "<font color=#000000>اسم العائلة</font><font color=#e32c18> *</font>";
            txt_lname.setText(android.text.Html.fromHtml(textlnamear));
            java.lang.String textDobar = "<font color=#000000>تاريخ الميلاد</font><font color=#e32c18> *</font>";
            txt_DOB.setText(android.text.Html.fromHtml(textDobar));
            java.lang.String textgenderar = "<font color=#000000>الجنس</font><font color=#e32c18> *</font>";
            txt_gender.setText(android.text.Html.fromHtml(textgenderar));
            java.lang.String textmobilnoar = "<font color=#000000>رقم الهاتف النقال</font><font color=#e32c18> *</font>";
            txt_mobileno.setText(android.text.Html.fromHtml(textmobilnoar));
            java.lang.String textnationalityar = "<font color=#000000>الجنسية</font><font color=#e32c18> *</font>";
            txt_nationality.setText(android.text.Html.fromHtml(textnationalityar));
        }
    }

    public void clicker(android.view.View v) {
        switch (v.getId()) {
            case R.id.btn_update_t_info :
                if (validateDetails()) {
                    new com.travel.flybooking.MyProfileActivity.SaveAccountDetails().execute();
                }
                break;
            case R.id.btn_update_a_info :
                if (validate()) {
                    new com.travel.flybooking.MyProfileActivity.UpdatePasswordService().execute();
                }
                break;
            case R.id.edt_dob :
                DateofBirth();
                break;
            case R.id.iv_back :
                finish();
                break;
            default :
                break;
        }
    }

    private void loadAssets() {
        // TODO Auto-generated method stub
        am = getAssets();
        java.lang.String countrylist = null;
        java.io.InputStream file1 = null;
        try {
            file1 = am.open("countrylist.txt");
        } catch (java.io.IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        java.io.BufferedReader reader1 = null;
        try {
            reader1 = new java.io.BufferedReader(new java.io.InputStreamReader(file1));
            java.lang.StringBuilder builder1 = new java.lang.StringBuilder();
            java.lang.String line1 = null;
            while ((line1 = reader1.readLine()) != null) {
                builder1.append(line1);
            } 
            countrylist = builder1.toString();
            arrayCountryisocode = new java.util.ArrayList<java.lang.String>();
            arrayCountry = new java.util.ArrayList<java.lang.String>();
            if (countrylist != null) {
                org.json.JSONObject json1 = new org.json.JSONObject(countrylist);
                org.json.JSONArray airlinelist = json1.getJSONArray("countrylist");
                org.json.JSONObject c1 = null;
                for (int i = 0; i < airlinelist.length(); i++) {
                    c1 = airlinelist.getJSONObject(i);
                    arrayCountry.add((c1.getString("CountryName") + " - ") + c1.getString("CountryCode"));
                    arrayCountryisocode.add(c1.getString("CountryCode"));
                    // Log.e("CountryName ", arrayCountryisocode.toString());
                }
                airlinelist = null;
            }
            countrylist = null;
            file1.close();
            reader1.close();
            builder1 = null;
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        } finally {
            if (reader1 != null) {
                try {
                    reader1.close();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void DateofBirth() {
        // TODO Auto-generated method stub
        datePickerDialog = new com.travel.flybooking.support.CustomDatePickerDialog(this, new android.app.DatePickerDialog.OnDateSetListener() {
            @java.lang.Override
            public void onDateSet(android.widget.DatePicker datepicker, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                try {
                    java.lang.String str_day = java.lang.String.valueOf(dayOfMonth);
                    if (str_day.length() == 1) {
                        str_day = "0" + java.lang.String.valueOf(dayOfMonth);
                    }
                    java.lang.String str_month = java.lang.String.valueOf(monthOfYear + 1);
                    if (str_month.length() == 1) {
                        str_month = "0" + java.lang.String.valueOf(monthOfYear + 1);
                    }
                    java.lang.String str_year = java.lang.String.valueOf(year);
                    java.lang.System.out.println((((str_day + "/") + str_month) + "/") + str_year);
                    edt_dob.setText((((str_day + "/") + str_month) + "/") + str_year);
                } catch (java.lang.Exception e) {
                }
            }
        }, minYear, minMonth, minDay, maxYear, maxMonth, maxDay);
        datePickerDialog.updateDate(cyear, cmonth - 1, cday);
        datePickerDialog.show();
    }

    public boolean validate() {
        boolean valid = true;
        java.lang.String spass = pass.getText().toString();
        java.lang.String scpass = cpass.getText().toString();
        if (spass.isEmpty()) {
            pass.setError(getResources().getString(R.string.error_invalid_pass));
            valid = false;
        } else {
            pass.setError(null);
        }
        if (scpass.isEmpty() && (!scpass.equals(cpass))) {
            cpass.setError(getResources().getString(R.string.error_invalid_pass));
            valid = false;
        } else {
            cpass.setError(null);
        }
        if (valid && (!spass.matches(scpass))) {
            cpass.setError(getString(R.string.error_pass_mismatch));
            valid = false;
        } else if (valid) {
            pass.setError(null);
            cpass.setError(null);
        }
        return valid;
    }

    public boolean validateDetails() {
        boolean valid = true;
        java.lang.String SpnTitile = SpnTitle.getSelectedItem().toString();
        if (SpnTitile.equals("Title")) {
            android.widget.Toast.makeText(getApplicationContext(), getResources().getString(R.string.err_title_req), android.widget.Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            // pass.setError(null);
        }
        if (EdtFname.getText().toString().isEmpty()) {
            EdtFname.setError(getResources().getString(R.string.error_first_name_req));
            valid = false;
        } else {
            EdtFname.setError(null);
        }
        if (EdtLname.getText().toString().isEmpty()) {
            EdtLname.setError(getResources().getString(R.string.error_last_name_req));
            valid = false;
        } else {
            EdtLname.setError(null);
        }
        if (edt_dob.getText().toString().isEmpty()) {
            edt_dob.setError(getResources().getString(R.string.error_dob_req));
            valid = false;
        }
        if (edt_mob.getText().toString().isEmpty()) {
            edt_mob.setError(getResources().getString(R.string.error_phn_no_req));
            valid = false;
        } else {
            edt_mob.setError(null);
        }
        if (valid) {
            map = new java.util.HashMap<java.lang.String, java.lang.String>();
            map.put("Tittle", Titles);
            map.put("FirstName", EdtFname.getText().toString());
            map.put("MiddleName", "");
            map.put("LastName", EdtLname.getText().toString());
            map.put("DateOfBirth", edt_dob.getText().toString());
            map.put("Gender", gCode);
            map.put("MobileCode", ccode);
            map.put("MobileNumber", edt_mob.getText().toString());
            map.put("PassportNumber", "");
            map.put("Citizenship", Ncode);
            map.put("PassengerId", PassengerId);
            map.put("PassportExpiryDate", "18/07/2016");
            map.put("PassportPlaceOfIssue", "");
        }
        return valid;
    }

    private void loadLocale() {
        // TODO Auto-generated method stub
        android.content.SharedPreferences sharedpreferences = this.getSharedPreferences("CommonPrefs", android.content.Context.MODE_PRIVATE);
        java.lang.String lang = sharedpreferences.getString("Language", "en");
        java.lang.System.out.println("Default lang: " + lang);
        if (lang.equalsIgnoreCase("ar")) {
            myLocale = new java.util.Locale(lang);
            saveLocale(lang);
            java.util.Locale.setDefault(myLocale);
            android.content.res.Configuration config = new android.content.res.Configuration();
            config.locale = myLocale;
            this.getBaseContext().getResources().updateConfiguration(config, this.getBaseContext().getResources().getDisplayMetrics());
            com.travel.flybooking.support.CommonFunctions.lang = "ar";
        } else {
            myLocale = new java.util.Locale(lang);
            saveLocale(lang);
            java.util.Locale.setDefault(myLocale);
            android.content.res.Configuration config = new android.content.res.Configuration();
            config.locale = myLocale;
            this.getBaseContext().getResources().updateConfiguration(config, this.getBaseContext().getResources().getDisplayMetrics());
            com.travel.flybooking.support.CommonFunctions.lang = "en";
        }
    }

    public void saveLocale(java.lang.String lang) {
        com.travel.flybooking.support.CommonFunctions.lang = lang;
        java.lang.String langPref = "Language";
        android.content.SharedPreferences prefs = this.getSharedPreferences("CommonPrefs", android.app.Activity.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.commit();
    }

    public class AccountDetails extends android.os.AsyncTask<java.lang.Void, java.lang.Void, java.lang.String> {
        java.lang.Boolean blIsloggedIn = false;

        java.lang.String sessionResult = "";

        org.json.JSONObject json1 = null;

        @java.lang.Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            loaderDialog.show();
            super.onPreExecute();
        }

        @java.lang.Override
        protected java.lang.String doInBackground(java.lang.Void... params) {
            // TODO Auto-generated method stub
            try {
                sessionResult = new com.travel.common_handlers.HttpHandler().makeServiceCall((com.travel.flybooking.support.CommonFunctions.main_url + com.travel.flybooking.support.CommonFunctions.lang) + urlObj.getGetProfileDetailsUrl());
                java.lang.System.out.println("result" + sessionResult);
                json1 = new org.json.JSONObject(sessionResult);
                if (json1.getBoolean("IsValid")) {
                    blIsloggedIn = json1.getBoolean("IsValid");
                    android.util.Log.e("IsValid", "True");
                    return sessionResult;
                }
            } catch (java.net.MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (java.io.IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (org.json.JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (java.lang.Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @java.lang.SuppressWarnings("unchecked")
        @java.lang.Override
        protected void onPostExecute(java.lang.String result) {
            // TODO Auto-generated method stub
            try {
                if (loaderDialog.isShowing())
                    loaderDialog.dismiss();

                if ((result != null) && blIsloggedIn) {
                    if (((json1.getString("Title") != null) && (!json1.getString("Title").equals(""))) && (!json1.getString("Title").equals("null"))) {
                        int pos = 0;
                        for (pos = 0; pos < arrTitle.length; ++pos) {
                            if (arrTitle[pos].equalsIgnoreCase(json1.getString("Title"))) {
                                break;
                            }
                        }
                        SpnTitle.setSelection(pos);
                    }
                    FirstName = json1.getString("FirstName");
                    if (((FirstName != null) && (!FirstName.equals(""))) && (!FirstName.equals("null")))
                        EdtFname.setText(FirstName);

                    MiddleName = json1.getString("MiddleName");
                    LastName = json1.getString("LastName");
                    if (((LastName != null) && (!LastName.equals(""))) && (!LastName.equals("null")))
                        EdtLname.setText(LastName);

                    DateOfBirth = json1.getString("DateOfBirth");
                    if (((DateOfBirth != null) && (!DateOfBirth.equals(""))) && (!DateOfBirth.equals("null"))) {
                        edt_dob.setText(DateOfBirth);
                        java.lang.String[] out = DateOfBirth.split("/");
                        cday = java.lang.Integer.parseInt(out[0]);
                        cmonth = java.lang.Integer.parseInt(out[1]);
                        cyear = java.lang.Integer.parseInt(out[2]);
                    } else {
                        cday = 01;
                        cmonth = 01;
                        cyear = 1980;
                        edt_dob.setText("");
                    }
                    PassengerId = json1.getString("PassengerId");
                    if (((json1.getString("Gender") != null) && (!json1.getString("Gender").equals(""))) && (!json1.getString("Gender").equals("null"))) {
                        Gender = json1.getString("Gender");
                        SpnGender.setSelection(Gender.contains("M") ? 1 : 2);
                    }
                    MobileCode = json1.getString("MobileCode");
                    if (((MobileCode != null) && (!MobileCode.equals(""))) && (!MobileCode.equals("null"))) {
                        java.lang.String s = MobileCode;
                        s = s.replace(" ", "");
                        java.lang.System.out.println(s);
                        spCountrycode.setSelection(((android.widget.ArrayAdapter<java.lang.String>) (spCountrycode.getAdapter())).getPosition(s));
                    }
                    MobileNumber = json1.getString("MobileNumber");
                    if (((MobileNumber != null) && (!MobileNumber.equals(""))) && (!MobileNumber.equals("null"))) {
                        edt_mob.setText(MobileNumber);
                    }
                    PassportNumber = json1.getString("PassportNumber");
                    java.lang.String S = json1.getString("Citizenship");
                    if (((S != null) && (!S.equals(""))) && (!S.equals("null"))) {
                        SpnNationality.setSelection(arrayCountryisocode.indexOf(S));
                    }
                    PassengerId = json1.getString("PassengerId");
                }
            } catch (org.json.JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            super.onPostExecute(result);
        }
    }

    private class SaveAccountDetails extends android.os.AsyncTask<java.lang.Void, java.lang.Void, java.lang.String> {
        @java.lang.Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            loaderDialog.show();
            super.onPreExecute();
        }

        @java.lang.Override
        protected java.lang.String doInBackground(java.lang.Void... params) {
            // TODO Auto-generated method stub
            try {
                java.lang.String UrlParams = urlObj.getProfileParams(map);
                java.lang.System.out.println("UrlParams " + UrlParams);
                java.lang.String urlParameters = "accountObject=" + UrlParams.toString();
                java.lang.String request = (com.travel.flybooking.support.CommonFunctions.main_url + com.travel.flybooking.support.CommonFunctions.lang) + "/MyAccountApi/SaveAccountProfile";
                java.lang.String res = new com.travel.common_handlers.HttpHandler().makeServiceCallWithParams(request, urlParameters);
                java.lang.System.out.println("res" + res);
                org.json.JSONObject json = new org.json.JSONObject(res);
                if (json.getBoolean("IsValid") && json.getBoolean("IsSuccess")) {
                    return json.getString("IsValid");
                }
            } catch (java.net.MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (java.io.IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (java.lang.Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            return null;
        }

        @java.lang.Override
        protected void onPostExecute(java.lang.String result) {
            // TODO Auto-generated method stub
            if (loaderDialog.isShowing())
                loaderDialog.dismiss();

            if ((result != null) && result.equals("true")) {
                android.widget.Toast.makeText(getApplicationContext(), getResources().getString(R.string.update_succes_msg_myprofile), android.widget.Toast.LENGTH_SHORT).show();
                new com.travel.flybooking.MyProfileActivity.AccountDetails().execute();
            } else {
                android.widget.Toast.makeText(getApplicationContext(), getResources().getString(R.string.update_succes_msg_myprofile), android.widget.Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

    private class UpdatePasswordService extends android.os.AsyncTask<java.lang.Void, java.lang.Void, java.lang.String> {
        boolean blSuccess = false;

        java.lang.String cPass = null;

        @java.lang.Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            loaderDialog.show();
            cPass = cpass.getText().toString();
        }

        @java.lang.Override
        protected java.lang.String doInBackground(java.lang.Void... params) {
            // TODO Auto-generated method stub
            try {
                com.travel.common_handlers.UrlParameterBuilder urlObj = new com.travel.common_handlers.UrlParameterBuilder();
                java.lang.String urlParameters = urlObj.getUpdatePassParams(cPass);
                java.lang.String request = (com.travel.flybooking.support.CommonFunctions.main_url + com.travel.flybooking.support.CommonFunctions.lang) + urlObj.getChangePasswordUrl();
                java.lang.String res = new com.travel.common_handlers.HttpHandler().makeServiceCallWithParams(request, urlParameters);
                java.lang.System.out.println("ChangePassword res" + res);
                org.json.JSONObject json = new org.json.JSONObject(res);
                if (json.getBoolean("IsValid") && json.getBoolean("IsSuccess")) {
                    blSuccess = true;
                    return json.getString("Error");
                } else {
                    return json.getString("Error");
                }
            } catch (java.net.MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (java.io.IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (java.lang.Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            return null;
        }

        @java.lang.Override
        protected void onPostExecute(java.lang.String result) {
            // TODO Auto-generated method stub
            if (loaderDialog.isShowing())
                loaderDialog.dismiss();

            if (result != null) {
                if (blSuccess) {
                    pass.setText("");
                    cpass.setText("");
                    blSuccess = false;
                }
                android.widget.Toast.makeText(getApplicationContext(), result, android.widget.Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }
}