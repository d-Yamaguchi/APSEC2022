@java.lang.Override
protected void executeSuccess(org.json.JSONObject result) throws org.json.JSONException {
    android.widget.TableLayout tl = ((android.widget.TableLayout) (hashelements.get("vendorpageScrollTable")));
    tl.removeAllViews();
    org.json.JSONObject vendorprofile = result.getJSONObject("vendorprofile");
    if (vendorprofile == null) {
        android.widget.TextView non = new android.widget.TextView(context);
        non.setText("This vendor didn't set up profile.");
    } else {
        android.widget.TableRow lv = new android.widget.TableRow(context);
        lv.setLayoutParams(new android.widget.TableLayout.LayoutParams(android.widget.TableLayout.LayoutParams.MATCH_PARENT, height / 5));
        android.widget.LinearLayout ll = new android.widget.LinearLayout(context);
        ll.setOrientation(android.widget.LinearLayout.VERTICAL);
        com.mahoneydev.usdafmexchange.pages.page_016_vendorpage.1 _CVAR0 = context;
        // Business Name
        android.widget.TextView pn = new android.widget.TextView(_CVAR0);
        android.widget.TextView _CVAR1 = pn;
        com.mahoneydev.usdafmexchange.pages.page_016_vendorpage.1 _CVAR2 = context;
        void _CVAR3 = R.style.Bold;
        _CVAR1.setTextAppearance(_CVAR2, _CVAR3);
        pn.setTextSize(width / 33);
        pn.setText(vendorprofile.getString("business_name"));
        pn.setGravity(android.view.Gravity.CENTER);
        ll.addView(pn);
        // 
        android.widget.TextView br1 = new android.widget.TextView(context);
        br1.setText("");
        ll.addView(br1);
        // TableLayout
        android.widget.TableLayout tl_in = new android.widget.TableLayout(context);
        // Email
        android.widget.TableRow tr1 = new android.widget.TableRow(context);
        tr1.setPadding(0, 10, 0, 0);
        com.mahoneydev.usdafmexchange.pages.page_016_vendorpage.1 _CVAR4 = context;
        android.widget.TextView email1 = new android.widget.TextView(_CVAR4);
        android.widget.TextView _CVAR5 = email1;
        com.mahoneydev.usdafmexchange.pages.page_016_vendorpage.1 _CVAR6 = context;
        void _CVAR7 = R.style.Bold;
        _CVAR5.setTextAppearance(_CVAR6, _CVAR7);
        email1.setTextSize(width / 45);
        email1.setText("Email:");
        tr1.addView(email1);
        email.setLayoutParams(new android.widget.TableRow.LayoutParams(((int) ((width * 6) / 10)), android.widget.TableRow.LayoutParams.WRAP_CONTENT));
        com.mahoneydev.usdafmexchange.pages.page_016_vendorpage.1 _CVAR8 = context;
        android.widget.TextView email = new android.widget.TextView(_CVAR8);
        android.widget.TextView _CVAR9 = email;
        com.mahoneydev.usdafmexchange.pages.page_016_vendorpage.1 _CVAR10 = context;
        void _CVAR11 = R.style.Normal;
        _CVAR9.setTextAppearance(_CVAR10, _CVAR11);
        email.setTextSize(width / 45);
        if (vendorprofile.has("business_email")) {
            email.setText(vendorprofile.getString("business_email"));
        }
        tr1.addView(email);
        tl_in.addView(tr1);
        // Phone
        android.widget.TableRow tr2 = new android.widget.TableRow(context);
        tr2.setPadding(0, 10, 0, 0);
        com.mahoneydev.usdafmexchange.pages.page_016_vendorpage.1 _CVAR12 = context;
        android.widget.TextView phone1 = new android.widget.TextView(_CVAR12);
        android.widget.TextView _CVAR13 = phone1;
        com.mahoneydev.usdafmexchange.pages.page_016_vendorpage.1 _CVAR14 = context;
        void _CVAR15 = R.style.Bold;
        _CVAR13.setTextAppearance(_CVAR14, _CVAR15);
        phone1.setTextSize(width / 45);
        phone1.setText("Phone:");
        tr2.addView(phone1);
        phone.setLayoutParams(new android.widget.TableRow.LayoutParams(((int) ((width * 6) / 10)), android.widget.TableRow.LayoutParams.WRAP_CONTENT));
        com.mahoneydev.usdafmexchange.pages.page_016_vendorpage.1 _CVAR16 = context;
        android.widget.TextView phone = new android.widget.TextView(_CVAR16);
        android.widget.TextView _CVAR17 = phone;
        com.mahoneydev.usdafmexchange.pages.page_016_vendorpage.1 _CVAR18 = context;
        void _CVAR19 = R.style.Normal;
        _CVAR17.setTextAppearance(_CVAR18, _CVAR19);
        phone.setTextSize(width / 45);
        if (vendorprofile.has("business_phone")) {
            phone.setText(vendorprofile.getString("business_phone"));
        }
        tr2.addView(phone);
        tl_in.addView(tr2);
        // Address
        android.widget.TableRow tr3 = new android.widget.TableRow(context);
        tr3.setPadding(0, 10, 0, 0);
        com.mahoneydev.usdafmexchange.pages.page_016_vendorpage.1 _CVAR20 = context;
        android.widget.TextView address1 = new android.widget.TextView(_CVAR20);
        android.widget.TextView _CVAR21 = address1;
        com.mahoneydev.usdafmexchange.pages.page_016_vendorpage.1 _CVAR22 = context;
        void _CVAR23 = R.style.Bold;
        _CVAR21.setTextAppearance(_CVAR22, _CVAR23);
        address1.setTextSize(width / 45);
        address1.setText("Address:");
        tr3.addView(address1);
        address.setLayoutParams(new android.widget.TableRow.LayoutParams(((int) ((width * 6) / 10)), android.widget.TableRow.LayoutParams.WRAP_CONTENT));
        com.mahoneydev.usdafmexchange.pages.page_016_vendorpage.1 _CVAR24 = context;
        android.widget.TextView address = new android.widget.TextView(_CVAR24);
        android.widget.TextView _CVAR25 = address;
        com.mahoneydev.usdafmexchange.pages.page_016_vendorpage.1 _CVAR26 = context;
        void _CVAR27 = R.style.Normal;
        _CVAR25.setTextAppearance(_CVAR26, _CVAR27);
        address.setTextSize(width / 45);
        address.setText(((((((vendorprofile.getString("business_street") + vendorprofile.getString("business_street")) + ", ") + vendorprofile.getString("business_city")) + ", ") + vendorprofile.getString("business_state")) + ", ") + vendorprofile.getString("business_zip"));
        tr3.addView(address);
        tl_in.addView(tr3);
        // Website
        android.widget.TableRow tr4 = new android.widget.TableRow(context);
        tr4.setPadding(0, 10, 0, 0);
        com.mahoneydev.usdafmexchange.pages.page_016_vendorpage.1 _CVAR28 = context;
        android.widget.TextView web1 = new android.widget.TextView(_CVAR28);
        android.widget.TextView _CVAR29 = web1;
        com.mahoneydev.usdafmexchange.pages.page_016_vendorpage.1 _CVAR30 = context;
        void _CVAR31 = R.style.Bold;
        _CVAR29.setTextAppearance(_CVAR30, _CVAR31);
        web1.setTextSize(width / 45);
        web1.setText("Website:");
        tr4.addView(web1);
        web.setLayoutParams(new android.widget.TableRow.LayoutParams(((int) ((width * 6) / 10)), android.widget.TableRow.LayoutParams.WRAP_CONTENT));
        com.mahoneydev.usdafmexchange.pages.page_016_vendorpage.1 _CVAR32 = context;
        android.widget.TextView web = new android.widget.TextView(_CVAR32);
        android.widget.TextView _CVAR33 = web;
        com.mahoneydev.usdafmexchange.pages.page_016_vendorpage.1 _CVAR34 = context;
        void _CVAR35 = R.style.Normal;
        _CVAR33.setTextAppearance(_CVAR34, _CVAR35);
        web.setTextSize(width / 45);
        web.setText(vendorprofile.getString("business_website"));
        tr4.addView(web);
        tl_in.addView(tr4);
        // Social media
        android.widget.TableRow tr5 = new android.widget.TableRow(context);
        tr5.setPadding(0, 10, 0, 0);
        com.mahoneydev.usdafmexchange.pages.page_016_vendorpage.1 _CVAR36 = context;
        android.widget.TextView media1 = new android.widget.TextView(_CVAR36);
        android.widget.TextView _CVAR37 = media1;
        com.mahoneydev.usdafmexchange.pages.page_016_vendorpage.1 _CVAR38 = context;
        void _CVAR39 = R.style.Bold;
        _CVAR37.setTextAppearance(_CVAR38, _CVAR39);
        media1.setTextSize(width / 45);
        media1.setText("Facebook:");
        tr5.addView(media1);
        media.setLayoutParams(new android.widget.TableRow.LayoutParams(((int) ((width * 6) / 10)), android.widget.TableRow.LayoutParams.WRAP_CONTENT));
        com.mahoneydev.usdafmexchange.pages.page_016_vendorpage.1 _CVAR40 = context;
        android.widget.TextView media = new android.widget.TextView(_CVAR40);
        android.widget.TextView _CVAR41 = media;
        com.mahoneydev.usdafmexchange.pages.page_016_vendorpage.1 _CVAR42 = context;
        void _CVAR43 = R.style.Normal;
        _CVAR41.setTextAppearance(_CVAR42, _CVAR43);
        media.setTextSize(width / 45);
        media.setText(vendorprofile.getString("business_facebook"));
        tr5.addView(media);
        tl_in.addView(tr5);
        android.widget.TableRow tr51 = new android.widget.TableRow(context);
        tr51.setPadding(0, 10, 0, 0);
        com.mahoneydev.usdafmexchange.pages.page_016_vendorpage.1 _CVAR44 = context;
        android.widget.TextView media11 = new android.widget.TextView(_CVAR44);
        android.widget.TextView _CVAR45 = media11;
        com.mahoneydev.usdafmexchange.pages.page_016_vendorpage.1 _CVAR46 = context;
        void _CVAR47 = R.style.Bold;
        _CVAR45.setTextAppearance(_CVAR46, _CVAR47);
        media11.setTextSize(width / 45);
        media11.setText("Twitter:");
        tr51.addView(media11);
        media12.setLayoutParams(new android.widget.TableRow.LayoutParams(((int) ((width * 6) / 10)), android.widget.TableRow.LayoutParams.WRAP_CONTENT));
        com.mahoneydev.usdafmexchange.pages.page_016_vendorpage.1 _CVAR48 = context;
        android.widget.TextView media12 = new android.widget.TextView(_CVAR48);
        android.widget.TextView _CVAR49 = media12;
        com.mahoneydev.usdafmexchange.pages.page_016_vendorpage.1 _CVAR50 = context;
        void _CVAR51 = R.style.Normal;
        _CVAR49.setTextAppearance(_CVAR50, _CVAR51);
        media12.setTextSize(width / 45);
        media12.setText(vendorprofile.getString("business_twitter"));
        tr51.addView(media12);
        tl_in.addView(tr51);
        // Organic
        android.widget.TableRow tr6 = new android.widget.TableRow(context);
        tr6.setPadding(0, 10, 0, 10);
        org1.setLayoutParams(new android.widget.TableRow.LayoutParams(((int) ((width * 3) / 10)), android.widget.TableRow.LayoutParams.WRAP_CONTENT));
        com.mahoneydev.usdafmexchange.pages.page_016_vendorpage.1 _CVAR52 = context;
        android.widget.TextView org1 = new android.widget.TextView(_CVAR52);
        android.widget.TextView _CVAR53 = org1;
        com.mahoneydev.usdafmexchange.pages.page_016_vendorpage.1 _CVAR54 = context;
        void _CVAR55 = R.style.Bold;
        _CVAR53.setTextAppearance(_CVAR54, _CVAR55);
        org1.setTextSize(width / 45);
        org1.setText("Organic:");
        tr6.addView(org1);
        android.widget.LinearLayout ll_in = new android.widget.LinearLayout(context);
        android.widget.ImageView org = new android.widget.ImageView(context);
        org.setLayoutParams(new android.widget.TableRow.LayoutParams(((int) ((width * 6) / 10)), android.widget.TableRow.LayoutParams.WRAP_CONTENT));
        java.lang.String data = "business_usdaorganic";
        java.lang.Object json = new JSONTokener(data).nextValue();
        if (json instanceof JSONArray) {
            JSONArray organic = vendorprofile.getJSONArray("business_usdaorganic");
            if (organic.getString(0).equals("yes")) {
                org.setImageResource(R.drawable.usda_organic);
                android.widget.LinearLayout.LayoutParams parms = new android.widget.LinearLayout.LayoutParams(50, 50);
                parms.gravity = android.view.Gravity.START;
                org.setLayoutParams(parms);
            }
            ll_in.addView(org);
        }
        tr6.addView(ll_in);
        tl_in.addView(tr6);
        ll.addView(tl_in);
        // 
        android.widget.TextView br2 = new android.widget.TextView(context);
        br2.setText("");
        ll.addView(br2);
        android.widget.RelativeLayout ll_bt = new android.widget.RelativeLayout(context);
        android.widget.RelativeLayout.LayoutParams lparam = new android.widget.RelativeLayout.LayoutParams(android.widget.RelativeLayout.LayoutParams.MATCH_PARENT, ((int) (width / 10)));
        ll_bt.setLayoutParams(lparam);
        android.widget.RelativeLayout.LayoutParams rfparams = new android.widget.RelativeLayout.LayoutParams(((int) (width / 3)), android.widget.RelativeLayout.LayoutParams.MATCH_PARENT);
        rfparams.addRule(android.widget.RelativeLayout.ALIGN_PARENT_LEFT);
        rf.setLayoutParams(rfparams);
        rf.setBackgroundColor(android.graphics.Color.parseColor("#A2D25A"));
        com.mahoneydev.usdafmexchange.pages.page_016_vendorpage.1 _CVAR56 = context;
        // Request Friend
        android.widget.Button rf = new android.widget.Button(_CVAR56);
        android.widget.Button _CVAR57 = rf;
        com.mahoneydev.usdafmexchange.pages.page_016_vendorpage.1 _CVAR58 = context;
        void _CVAR59 = R.style.White;
        _CVAR57.setTextAppearance(_CVAR58, _CVAR59);
        rf.setTextSize(width / 50);
        rf.setText("Request friend");
        rf.setTransformationMethod(null);
        ll_bt.addView(rf);
        rf.setOnClickListener(new com.mahoneydev.usdafmexchange.pages.page_016_vendorpage.requestFriendListener(rf));
        android.widget.RelativeLayout.LayoutParams alparams = new android.widget.RelativeLayout.LayoutParams(((int) (width / 3)), android.widget.RelativeLayout.LayoutParams.MATCH_PARENT);
        alparams.addRule(android.widget.RelativeLayout.ALIGN_PARENT_RIGHT);
        al.setLayoutParams(alparams);
        al.setBackgroundColor(android.graphics.Color.parseColor("#A2D25A"));
        com.mahoneydev.usdafmexchange.pages.page_016_vendorpage.1 _CVAR60 = context;
        // Add to list
        android.widget.Button al = new android.widget.Button(_CVAR60);
        android.widget.Button _CVAR61 = al;
        com.mahoneydev.usdafmexchange.pages.page_016_vendorpage.1 _CVAR62 = context;
        void _CVAR63 = R.style.White;
        _CVAR61.setTextAppearance(_CVAR62, _CVAR63);
        al.setTextSize(width / 50);
        al.setText("Add to list");
        al.setTransformationMethod(null);
        ll_bt.addView(al);
        ll.addView(ll_bt);
        // 
        android.widget.TextView br3 = new android.widget.TextView(context);
        br3.setText("");
        ll.addView(br3);
        ll.setLayoutParams(new android.widget.TableRow.LayoutParams(0, android.widget.TableLayout.LayoutParams.WRAP_CONTENT, 1.0F));
        lv.addView(ll);
        tl.addView(lv);
    }
    setupUI(playout);
}