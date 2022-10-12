@java.lang.Override
protected void onCreate(android.os.Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile);
    android.widget.Button viewProjects = ((android.widget.Button) (findViewById(R.id.login_button)));
    student_id = ((android.widget.TextView) (findViewById(R.id.student_id_label)));
    email = ((android.widget.TextView) (findViewById(R.id.email_label)));
    summary = ((android.widget.TextView) (findViewById(R.id.summary_label)));
    first_name = ((android.widget.TextView) (findViewById(R.id.firstName_label)));
    last_name = ((android.widget.TextView) (findViewById(R.id.lastName_label)));
    experience = ((android.widget.TextView) (findViewById(R.id.experience_label)));
    android.content.Intent _CVAR1 = intent;
    java.lang.String _CVAR2 = "student_id";
    java.lang.String Mid = _CVAR1.getStringExtra(_CVAR2);
    java.lang.String _CVAR3 = Mid;
    android.widget.TextView _CVAR0 = student_id;
    android.text.Spanned _CVAR4 = android.text.Html.fromHtml(_CVAR3);
    _CVAR0.setText(_CVAR4);
    android.content.Intent _CVAR6 = intent;
    java.lang.String _CVAR7 = "email";
    java.lang.String Memail = _CVAR6.getStringExtra(_CVAR7);
    java.lang.String _CVAR8 = Memail;
    android.widget.TextView _CVAR5 = email;
    android.text.Spanned _CVAR9 = android.text.Html.fromHtml(_CVAR8);
    _CVAR5.setText(_CVAR9);
    android.content.Intent _CVAR11 = intent;
    java.lang.String _CVAR12 = "summary";
    java.lang.String Msummary = _CVAR11.getStringExtra(_CVAR12);
    int _CVAR15 = 6;
    java.lang.String _CVAR13 = Msummary;
    int _CVAR14 = 3;
    int _CVAR16 = Msummary.length() - _CVAR15;
    java.lang.String _CVAR17 = _CVAR13.substring(_CVAR14, _CVAR16);
    android.widget.TextView _CVAR10 = summary;
    android.text.Spanned _CVAR18 = android.text.Html.fromHtml(_CVAR17);
    _CVAR10.setText(_CVAR18);
    android.content.Intent _CVAR20 = intent;
    java.lang.String _CVAR21 = "first_name";
    java.lang.String MfirstName = _CVAR20.getStringExtra(_CVAR21);
    java.lang.String _CVAR22 = MfirstName;
    android.widget.TextView _CVAR19 = first_name;
    android.text.Spanned _CVAR23 = android.text.Html.fromHtml(_CVAR22);
    _CVAR19.setText(_CVAR23);
    android.content.Intent _CVAR25 = intent;
    java.lang.String _CVAR26 = "last_name";
    java.lang.String MlastName = _CVAR25.getStringExtra(_CVAR26);
    java.lang.String _CVAR27 = MlastName;
    android.widget.TextView _CVAR24 = last_name;
    android.text.Spanned _CVAR28 = android.text.Html.fromHtml(_CVAR27);
    _CVAR24.setText(_CVAR28);
    android.content.Intent intent = getIntent();
    android.content.Intent _CVAR30 = intent;
    java.lang.String _CVAR31 = "experience";
    java.lang.String Mexperience = _CVAR30.getStringExtra(_CVAR31);
    int _CVAR34 = 6;
    java.lang.String _CVAR32 = Mexperience;
    int _CVAR33 = 3;
    int _CVAR35 = Mexperience.length() - _CVAR34;
    java.lang.String _CVAR36 = _CVAR32.substring(_CVAR33, _CVAR35);
    android.widget.TextView _CVAR29 = experience;
    android.text.Spanned _CVAR37 = android.text.Html.fromHtml(_CVAR36);
    _CVAR29.setText(_CVAR37);
    // This line errors out, not sure why.......
    /* viewProjects.setOnClickListener(new OnClickListener() {
    @Override
    public void onClick(View v) {
    Intent intent = new Intent(Profile.this,AllProjects.class);
    Profile.this.startActivity(intent);
    }
    });
     */
}