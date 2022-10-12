public void onCreate(android.os.Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    android.widget.ScrollView sv = new android.widget.ScrollView(this);
    final android.widget.LinearLayout layout = new android.widget.LinearLayout(this);
    layout.setOrientation(1);
    layout.setLayoutParams(new android.view.ViewGroup.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
    final android.widget.LinearLayout contributorLayout = new android.widget.LinearLayout(this);
    contributorLayout.setOrientation(1);
    contributorLayout.setLayoutParams(new android.view.ViewGroup.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
    final android.widget.LinearLayout participantLayout = new android.widget.LinearLayout(this);
    participantLayout.setOrientation(1);
    participantLayout.setLayoutParams(new android.view.ViewGroup.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
    android.content.Intent intent = getIntent();
    android.os.Bundle b = intent.getExtras();
    final java.lang.String eventid = b.get("event").toString();
    android.util.Log.v(com.calculomatic.EditEventActivity.LOG_TAG, eventid);
    contributorsdatasource = new com.calculomatic.ContributorsDataSource(this);
    contributorsdatasource.open();
    contributors = contributorsdatasource.contributorsForEvent(eventid);
    contributorsdatasource.close();
    participantsdatasource = new com.calculomatic.ParticipantsDataSource(this);
    participantsdatasource.open();
    participants = participantsdatasource.participantsForEvent(eventid);
    participantsdatasource.close();
    eventsdatasource = new com.calculomatic.EventsDataSource(this);
    eventsdatasource.open();
    com.calculomatic.Event e = eventsdatasource.getEventById(eventid);
    eventsdatasource.close();
    com.calculomatic.EditEventActivity _CVAR0 = this;
    android.widget.TextView te = new android.widget.TextView(_CVAR0);
    android.widget.TextView _CVAR1 = te;
    com.calculomatic.EditEventActivity _CVAR2 = this;
    int _CVAR3 = android.R.style.TextAppearance_Large;
    _CVAR1.setTextAppearance(_CVAR2, _CVAR3);
    te.setText(e.getEvent().toString());
    layout.addView(te);
    com.calculomatic.EditEventActivity _CVAR4 = this;
    android.widget.TextView tp = new android.widget.TextView(_CVAR4);
    android.widget.TextView _CVAR5 = tp;
    com.calculomatic.EditEventActivity _CVAR6 = this;
    int _CVAR7 = android.R.style.TextAppearance_Medium;
    _CVAR5.setTextAppearance(_CVAR6, _CVAR7);
    tp.setText(e.getPlace().toString());
    layout.addView(tp);
    com.calculomatic.EditEventActivity _CVAR8 = this;
    android.widget.TextView tv = new android.widget.TextView(_CVAR8);
    android.widget.TextView _CVAR9 = tv;
    com.calculomatic.EditEventActivity _CVAR10 = this;
    int _CVAR11 = android.R.style.TextAppearance_Large;
    _CVAR9.setTextAppearance(_CVAR10, _CVAR11);
    tv.setText("List Of Contributors");
    layout.addView(tv);
    for (com.calculomatic.Contributor c : contributors) {
        usersdatasource = new com.calculomatic.UsersDataSource(this);
        usersdatasource.open();
        com.calculomatic.EditEventActivity _CVAR12 = this;
        android.widget.TextView t1 = new android.widget.TextView(_CVAR12);
        android.widget.TextView _CVAR13 = t1;
        com.calculomatic.EditEventActivity _CVAR14 = this;
        int _CVAR15 = android.R.style.TextAppearance_Large;
        _CVAR13.setTextAppearance(_CVAR14, _CVAR15);
        t1.setText(usersdatasource.getUserFromUid(c.getuid()).getUsername());
        t1.setId(id);
        id++;
        android.widget.EditText e1 = new android.widget.EditText(this);
        e1.setLayoutParams(new android.view.ViewGroup.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
        e1.setText(c.getamount().toString());
        e1.setId(id);
        id++;
        usersdatasource.close();
        contributorLayout.addView(t1);
        contributorLayout.addView(e1);
    }
    layout.addView(contributorLayout);
    com.calculomatic.EditEventActivity _CVAR16 = this;
    android.widget.TextView tpc = new android.widget.TextView(_CVAR16);
    android.widget.TextView _CVAR17 = tpc;
    com.calculomatic.EditEventActivity _CVAR18 = this;
    int _CVAR19 = android.R.style.TextAppearance_Large;
    _CVAR17.setTextAppearance(_CVAR18, _CVAR19);
    tpc.setText("List Of Participants");
    layout.addView(tpc);
    for (com.calculomatic.Participant p : participants) {
        usersdatasource = new com.calculomatic.UsersDataSource(this);
        usersdatasource.open();
        com.calculomatic.EditEventActivity _CVAR20 = this;
        android.widget.TextView t1 = new android.widget.TextView(_CVAR20);
        android.widget.TextView _CVAR21 = t1;
        com.calculomatic.EditEventActivity _CVAR22 = this;
        int _CVAR23 = android.R.style.TextAppearance_Large;
        _CVAR21.setTextAppearance(_CVAR22, _CVAR23);
        t1.setText(usersdatasource.getUserFromUid(p.getuid()).getUsername());
        t1.setId(id);
        id++;
        android.widget.EditText e1 = new android.widget.EditText(this);
        e1.setLayoutParams(new android.view.ViewGroup.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
        e1.setText(p.getamount().toString());
        e1.setId(id);
        id++;
        usersdatasource.close();
        participantLayout.addView(t1);
        participantLayout.addView(e1);
    }
    layout.addView(participantLayout);
    android.widget.Button save = new android.widget.Button(this);
    save.setLayoutParams(new android.view.ViewGroup.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
    save.setText("Save");
    layout.addView(save);
    save.setOnClickListener(new android.view.View.OnClickListener() {
        public void onClick(android.view.View v) {
            java.lang.Integer contributorCount = contributorLayout.getChildCount();
            java.lang.Integer participantCount = participantLayout.getChildCount();
            int amount;
            com.calculomatic.Contributor c;
            com.calculomatic.Participant p;
            java.lang.String username;
            java.lang.Boolean b = true;
            usersdatasource = new com.calculomatic.UsersDataSource(getApplicationContext());
            usersdatasource.open();
            for (int i = 0; i < contributorCount; i = i + 2) {
                int id = contributorLayout.getChildAt(i).getId();
                int id_editext = contributorLayout.getChildAt(i + 1).getId();
                c = new com.calculomatic.Contributor();
                android.widget.TextView view = ((android.widget.TextView) (findViewById(contributorLayout.getChildAt(i).getId())));
                username = view.getText().toString();
                c.setuid(usersdatasource.getUserFromUsername(username).getId());
                android.widget.EditText et = ((android.widget.EditText) (findViewById(contributorLayout.getChildAt(i + 1).getId())));
                amount = java.lang.Integer.parseInt(et.getText().toString());
                c.setamount(amount);
                c.setEid(java.lang.Long.parseLong(eventid));
                b = b && UpdateContributor(c);
                c.setEid(0);
                c.setuid(0);
                c.setamount(0);
            }
            for (int i = 0; i < participantCount; i = i + 2) {
                p = new com.calculomatic.Participant();
                android.widget.TextView tv = ((android.widget.TextView) (findViewById(participantLayout.getChildAt(i).getId())));
                username = tv.getText().toString();
                p.setuid(usersdatasource.getUserFromUsername(username).getId());
                android.widget.EditText ev = ((android.widget.EditText) (findViewById(participantLayout.getChildAt(i + 1).getId())));
                amount = java.lang.Integer.parseInt(ev.getText().toString());
                p.setamount(amount);
                p.setEid(java.lang.Long.parseLong(eventid));
                b = b && UpdateParticipant(p);
                p.setEid(0);
                p.setuid(0);
                p.setamount(0);
            }
            usersdatasource.close();
            if (b.equals(true)) {
                android.content.Intent intent = new android.content.Intent(getApplicationContext(), com.calculomatic.EventsActivity.class);
                startActivity(intent);
            } else {
                android.widget.Toast.makeText(getApplicationContext(), "Error occurred while updating -- Please try again", 10).show();
            }
        }
    });
    sv.addView(layout);
    setContentView(sv);
}