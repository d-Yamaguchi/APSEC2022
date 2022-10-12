package com.example.test1;
import beans.DoubleDatabase;
import beans.Player;
import beans.SingleDatabase;
public class ScoresActivity extends android.app.Activity {
    android.widget.TextView sec10;

    android.widget.TextView sec20;

    android.widget.TextView sec30;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        sec30 = ((android.widget.TextView) (findViewById(R.id.sec30)));
        sec20 = ((android.widget.TextView) (findViewById(R.id.sec20)));
        sec10 = ((android.widget.TextView) (findViewById(R.id.sec10)));
        editvalues();
        initTabs();
        // Typeface titleFont = Typeface.createFromAsset(this.getAssets(),"ROBOTOCONDENSED-REGULAR.TTF");
        // sec30.setTypeface(titleFont);
        // sec20.setTypeface(titleFont);
        // sec10.setTypeface(titleFont);
    }

    @java.lang.Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add(android.view.Menu.NONE, android.view.Menu.FIRST, android.view.Menu.NONE, "Clear Scores");
        return super.onCreateOptionsMenu(menu);
    }

    @java.lang.Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        // TODO Auto-generated method stub
        if (item.getItemId() == android.view.Menu.FIRST) {
            beans.SingleDatabase sd = new beans.SingleDatabase(getApplicationContext());
            beans.DoubleDatabase dd = new beans.DoubleDatabase(getApplicationContext());
            sd.delete();
            dd.delete();
            editvalues();
        }
        return super.onOptionsItemSelected(item);
    }

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
                android.widget.TextView na = new android.widget.TextView(getApplicationContext());
                int resid = android.R.style.TextAppearance_Large;
                sec10.setTextAppearance(resid);
                na.setTextColor(getResources().getColor(android.R.color.black));
                na.setText(list.get(i).getPlayerName());
                na.setGravity(0x11);
                newRow.addView(na);
                android.widget.TextView s1 = new android.widget.TextView(getApplicationContext());
                s1.setTextAppearance(this, android.R.attr.textAppearanceLarge);
                s1.setTextColor(getResources().getColor(android.R.color.black));
                s1.setText(java.lang.String.valueOf(list.get(i).getPlayerScore()));
                s1.setGravity(0x11);
                newRow.addView(s1);
                android.widget.TextView na2 = new android.widget.TextView(this);
                na2.setTextAppearance(this, android.R.attr.textAppearanceLarge);
                na2.setTextColor(getResources().getColor(android.R.color.black));
                na2.setText(list.get(i + 1).getPlayerName());
                na2.setGravity(0x11);
                newRow.addView(na2);
                android.widget.TextView s2 = new android.widget.TextView(this);
                s2.setTextAppearance(this, android.R.attr.textAppearanceLarge);
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
                android.widget.TextView na = new android.widget.TextView(getApplicationContext());
                na.setTextAppearance(this, android.R.attr.textAppearanceLarge);
                na.setTextColor(getResources().getColor(android.R.color.black));
                na.setText(list.get(i).getPlayerName());
                na.setGravity(0x11);
                newRow.addView(na);
                android.widget.TextView s1 = new android.widget.TextView(getApplicationContext());
                s1.setTextAppearance(this, android.R.attr.textAppearanceLarge);
                s1.setTextColor(getResources().getColor(android.R.color.black));
                s1.setText(java.lang.String.valueOf(list.get(i).getPlayerScore()));
                s1.setGravity(0x11);
                newRow.addView(s1);
                android.widget.TextView na2 = new android.widget.TextView(this);
                na2.setTextAppearance(this, android.R.attr.textAppearanceLarge);
                na2.setTextColor(getResources().getColor(android.R.color.black));
                na2.setText(list.get(i + 1).getPlayerName());
                na2.setGravity(0x11);
                newRow.addView(na2);
                android.widget.TextView s2 = new android.widget.TextView(this);
                s2.setTextAppearance(this, android.R.attr.textAppearanceLarge);
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
                android.widget.TextView na = new android.widget.TextView(getApplicationContext());
                na.setTextAppearance(this, android.R.attr.textAppearanceLarge);
                na.setTextColor(getResources().getColor(android.R.color.black));
                na.setText(list.get(i).getPlayerName());
                na.setGravity(0x11);
                newRow.addView(na);
                android.widget.TextView s1 = new android.widget.TextView(getApplicationContext());
                s1.setTextAppearance(this, android.R.attr.textAppearanceLarge);
                s1.setTextColor(getResources().getColor(android.R.color.black));
                s1.setText(java.lang.String.valueOf(list.get(i).getPlayerScore()));
                s1.setGravity(0x11);
                newRow.addView(s1);
                android.widget.TextView na2 = new android.widget.TextView(this);
                na2.setTextAppearance(this, android.R.attr.textAppearanceLarge);
                na2.setTextColor(getResources().getColor(android.R.color.black));
                na2.setText(list.get(i + 1).getPlayerName());
                na2.setGravity(0x11);
                newRow.addView(na2);
                android.widget.TextView s2 = new android.widget.TextView(this);
                s2.setTextAppearance(this, android.R.attr.textAppearanceLarge);
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

    public void initTabs() {
        android.widget.TabHost tab_host = ((android.widget.TabHost) (findViewById(android.R.id.tabhost)));
        // don't forget this setup before adding tabs from a tabhost using a xml view or you'll get an nullpointer exception
        tab_host.setup();
        android.widget.TabHost.TabSpec ts1 = tab_host.newTabSpec("TAB_DATE");
        ts1.setIndicator("10Sec");
        ts1.setContent(R.id.tab1);
        tab_host.addTab(ts1);
        android.widget.TabHost.TabSpec ts2 = tab_host.newTabSpec("TAB_GEO");
        ts2.setIndicator("20Sec");
        ts2.setContent(R.id.tab2);
        tab_host.addTab(ts2);
        android.widget.TabHost.TabSpec ts3 = tab_host.newTabSpec("TAB_TEXT");
        ts3.setIndicator("30Sec");
        ts3.setContent(R.id.tab3);
        tab_host.addTab(ts3);
        tab_host.setCurrentTab(2);
    }
}