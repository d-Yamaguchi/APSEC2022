public void editvalues() {
    beans.SingleDatabase sd = new beans.SingleDatabase(getApplicationContext());
    int score = sd.getData(30);
    sec30.setText("Your High Score is : " + score);
    score = sd.getData(20);
    sec20.setText("Your High Score is : " + score);
    score = sd.getData(10);
    sec10.setText("Your High Score is : " + score);
    // 
    // 30 SECONDS
    // 
    beans.DoubleDatabase d = new beans.DoubleDatabase(getApplicationContext());
    java.util.ArrayList<beans.Player> list = d.getData(30);
    // ScrollView s=new ScrollView(this);
    android.widget.TableLayout t1 = ((android.widget.TableLayout) (findViewById(R.id.tl3)));
    if (list.size() > 0) {
        for (int i = 0; i < list.size(); i += 2) {
            android.widget.TableRow newRow = new android.widget.TableRow(this);
            android.content.Context _CVAR0 = getApplicationContext();
            android.widget.TextView na = new android.widget.TextView(_CVAR0);
            android.widget.TextView _CVAR1 = na;
            com.example.test1.ScoresActivity _CVAR2 = this;
            int _CVAR3 = android.R.attr.textAppearanceLarge;
            _CVAR1.setTextAppearance(_CVAR2, _CVAR3);
            na.setTextColor(getResources().getColor(android.R.color.black));
            na.setText(list.get(i).getPlayerName());
            na.setGravity(0x11);
            newRow.addView(na);
            android.content.Context _CVAR4 = getApplicationContext();
            android.widget.TextView s1 = new android.widget.TextView(_CVAR4);
            android.widget.TextView _CVAR5 = s1;
            com.example.test1.ScoresActivity _CVAR6 = this;
            int _CVAR7 = android.R.attr.textAppearanceLarge;
            _CVAR5.setTextAppearance(_CVAR6, _CVAR7);
            s1.setTextColor(getResources().getColor(android.R.color.black));
            s1.setText(java.lang.String.valueOf(list.get(i).getPlayerScore()));
            s1.setGravity(0x11);
            newRow.addView(s1);
            com.example.test1.ScoresActivity _CVAR8 = this;
            android.widget.TextView na2 = new android.widget.TextView(_CVAR8);
            android.widget.TextView _CVAR9 = na2;
            com.example.test1.ScoresActivity _CVAR10 = this;
            int _CVAR11 = android.R.attr.textAppearanceLarge;
            _CVAR9.setTextAppearance(_CVAR10, _CVAR11);
            na2.setTextColor(getResources().getColor(android.R.color.black));
            na2.setText(list.get(i + 1).getPlayerName());
            na2.setGravity(0x11);
            newRow.addView(na2);
            com.example.test1.ScoresActivity _CVAR12 = this;
            android.widget.TextView s2 = new android.widget.TextView(_CVAR12);
            android.widget.TextView _CVAR13 = s2;
            com.example.test1.ScoresActivity _CVAR14 = this;
            int _CVAR15 = android.R.attr.textAppearanceLarge;
            _CVAR13.setTextAppearance(_CVAR14, _CVAR15);
            s2.setTextColor(getResources().getColor(android.R.color.black));
            s2.setText(java.lang.String.valueOf(list.get(i + 1).getPlayerScore()));
            s2.setGravity(0x11);
            newRow.addView(s2);
            t1.addView(newRow);
        }
    } else {
        t1.removeAllViews();
    }
    // 
    // 10 SECONDS
    // 
    list = d.getData(10);
    android.widget.TableLayout t3 = ((android.widget.TableLayout) (findViewById(R.id.tl1)));
    if (list.size() > 0) {
        for (int i = 0; i < list.size(); i += 2) {
            android.widget.TableRow newRow = new android.widget.TableRow(this);
            android.content.Context _CVAR16 = getApplicationContext();
            android.widget.TextView na = new android.widget.TextView(_CVAR16);
            android.widget.TextView _CVAR17 = na;
            com.example.test1.ScoresActivity _CVAR18 = this;
            int _CVAR19 = android.R.attr.textAppearanceLarge;
            _CVAR17.setTextAppearance(_CVAR18, _CVAR19);
            na.setTextColor(getResources().getColor(android.R.color.black));
            na.setText(list.get(i).getPlayerName());
            na.setGravity(0x11);
            newRow.addView(na);
            android.content.Context _CVAR20 = getApplicationContext();
            android.widget.TextView s1 = new android.widget.TextView(_CVAR20);
            android.widget.TextView _CVAR21 = s1;
            com.example.test1.ScoresActivity _CVAR22 = this;
            int _CVAR23 = android.R.attr.textAppearanceLarge;
            _CVAR21.setTextAppearance(_CVAR22, _CVAR23);
            s1.setTextColor(getResources().getColor(android.R.color.black));
            s1.setText(java.lang.String.valueOf(list.get(i).getPlayerScore()));
            s1.setGravity(0x11);
            newRow.addView(s1);
            com.example.test1.ScoresActivity _CVAR24 = this;
            android.widget.TextView na2 = new android.widget.TextView(_CVAR24);
            android.widget.TextView _CVAR25 = na2;
            com.example.test1.ScoresActivity _CVAR26 = this;
            int _CVAR27 = android.R.attr.textAppearanceLarge;
            _CVAR25.setTextAppearance(_CVAR26, _CVAR27);
            na2.setTextColor(getResources().getColor(android.R.color.black));
            na2.setText(list.get(i + 1).getPlayerName());
            na2.setGravity(0x11);
            newRow.addView(na2);
            com.example.test1.ScoresActivity _CVAR28 = this;
            android.widget.TextView s2 = new android.widget.TextView(_CVAR28);
            android.widget.TextView _CVAR29 = s2;
            com.example.test1.ScoresActivity _CVAR30 = this;
            int _CVAR31 = android.R.attr.textAppearanceLarge;
            _CVAR29.setTextAppearance(_CVAR30, _CVAR31);
            s2.setTextColor(getResources().getColor(android.R.color.black));
            s2.setText(java.lang.String.valueOf(list.get(i + 1).getPlayerScore()));
            s2.setGravity(0x11);
            newRow.addView(s2);
            t3.addView(newRow);
        }
    } else {
        t3.removeAllViews();
    }
    // 
    // 20 SECONDS
    // 
    list = d.getData(20);
    android.widget.TableLayout t2 = ((android.widget.TableLayout) (findViewById(R.id.tl2)));
    if (list.size() > 0) {
        for (int i = 0; i < list.size(); i += 2) {
            android.widget.TableRow newRow = new android.widget.TableRow(this);
            android.content.Context _CVAR32 = getApplicationContext();
            android.widget.TextView na = new android.widget.TextView(_CVAR32);
            android.widget.TextView _CVAR33 = na;
            com.example.test1.ScoresActivity _CVAR34 = this;
            int _CVAR35 = android.R.attr.textAppearanceLarge;
            _CVAR33.setTextAppearance(_CVAR34, _CVAR35);
            na.setTextColor(getResources().getColor(android.R.color.black));
            na.setText(list.get(i).getPlayerName());
            na.setGravity(0x11);
            newRow.addView(na);
            android.content.Context _CVAR36 = getApplicationContext();
            android.widget.TextView s1 = new android.widget.TextView(_CVAR36);
            android.widget.TextView _CVAR37 = s1;
            com.example.test1.ScoresActivity _CVAR38 = this;
            int _CVAR39 = android.R.attr.textAppearanceLarge;
            _CVAR37.setTextAppearance(_CVAR38, _CVAR39);
            s1.setTextColor(getResources().getColor(android.R.color.black));
            s1.setText(java.lang.String.valueOf(list.get(i).getPlayerScore()));
            s1.setGravity(0x11);
            newRow.addView(s1);
            com.example.test1.ScoresActivity _CVAR40 = this;
            android.widget.TextView na2 = new android.widget.TextView(_CVAR40);
            android.widget.TextView _CVAR41 = na2;
            com.example.test1.ScoresActivity _CVAR42 = this;
            int _CVAR43 = android.R.attr.textAppearanceLarge;
            _CVAR41.setTextAppearance(_CVAR42, _CVAR43);
            na2.setTextColor(getResources().getColor(android.R.color.black));
            na2.setText(list.get(i + 1).getPlayerName());
            na2.setGravity(0x11);
            newRow.addView(na2);
            com.example.test1.ScoresActivity _CVAR44 = this;
            android.widget.TextView s2 = new android.widget.TextView(_CVAR44);
            android.widget.TextView _CVAR45 = s2;
            com.example.test1.ScoresActivity _CVAR46 = this;
            int _CVAR47 = android.R.attr.textAppearanceLarge;
            _CVAR45.setTextAppearance(_CVAR46, _CVAR47);
            s2.setTextColor(getResources().getColor(android.R.color.black));
            s2.setText(java.lang.String.valueOf(list.get(i + 1).getPlayerScore()));
            s2.setGravity(0x11);
            newRow.addView(s2);
            t2.addView(newRow);
        }
    } else {
        t2.removeAllViews();
    }
}