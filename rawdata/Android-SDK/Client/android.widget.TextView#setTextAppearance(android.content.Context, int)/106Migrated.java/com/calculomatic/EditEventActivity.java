package com.calculomatic;
public class EditEventActivity extends android.app.Activity {
    private com.calculomatic.ContributorsDataSource contributorsdatasource;

    private com.calculomatic.UsersDataSource usersdatasource;

    private com.calculomatic.ParticipantsDataSource participantsdatasource;

    private com.calculomatic.EventsDataSource eventsdatasource;

    public java.util.List<com.calculomatic.Contributor> contributors = new java.util.ArrayList<com.calculomatic.Contributor>();

    public java.util.List<com.calculomatic.Participant> participants = new java.util.ArrayList<com.calculomatic.Participant>();

    public java.lang.String eventName;

    public java.lang.String eventPlace;

    public static final java.lang.String LOG_TAG = "Calculomatic";

    public java.lang.Integer id = 0;

    public void onCreate(android.os.Bundle savedInstanceState) {
        __SmPLUnsupported__(0).onCreate(savedInstanceState);
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
        android.widget.TextView te = new android.widget.TextView(this);
        TextView textView = new android.widget.TextView(context);
        int resid = android.R.style.TextAppearance_Large;
        textView.setTextAppearance(resid);
        te.setText(e.getEvent().toString());
        layout.addView(te);
        android.widget.TextView tp = new android.widget.TextView(this);
        tp.setTextAppearance(this, android.R.style.TextAppearance_Medium);
        tp.setText(e.getPlace().toString());
        layout.addView(tp);
        android.widget.TextView tv = new android.widget.TextView(this);
        tv.setTextAppearance(this, android.R.style.TextAppearance_Large);
        tv.setText("List Of Contributors");
        layout.addView(tv);
        for (com.calculomatic.Contributor c : contributors) {
            usersdatasource = new com.calculomatic.UsersDataSource(this);
            usersdatasource.open();
            android.widget.TextView t1 = new android.widget.TextView(this);
            t1.setTextAppearance(this, android.R.style.TextAppearance_Large);
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
        android.widget.TextView tpc = new android.widget.TextView(this);
        tpc.setTextAppearance(this, android.R.style.TextAppearance_Large);
        tpc.setText("List Of Participants");
        layout.addView(tpc);
        for (com.calculomatic.Participant p : participants) {
            usersdatasource = new com.calculomatic.UsersDataSource(this);
            usersdatasource.open();
            android.widget.TextView t1 = new android.widget.TextView(this);
            t1.setTextAppearance(this, android.R.style.TextAppearance_Large);
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
        save.setOnClickListener(__SmPLUnsupported__(1));
        sv.addView(layout);
        setContentView(sv);
    }

    public boolean UpdateContributor(com.calculomatic.Contributor c) {
        contributorsdatasource = new com.calculomatic.ContributorsDataSource(this);
        contributorsdatasource.open();
        int result = contributorsdatasource.updateContributor(c);
        contributorsdatasource.close();
        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean UpdateParticipant(com.calculomatic.Participant p) {
        participantsdatasource = new com.calculomatic.ParticipantsDataSource(this);
        participantsdatasource.open();
        int result = participantsdatasource.updateParticipant(p);
        participantsdatasource.close();
        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }
}