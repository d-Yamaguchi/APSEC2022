package com.example.a85625.seoultour;
public class InfoActivity extends android.support.v7.app.AppCompatActivity {
    java.util.List<com.example.a85625.seoultour.Info_item> items;

    android.widget.ImageView image;

    android.widget.TextView title_tv;

    android.widget.TextView zipcode_tv;

    android.widget.TextView tel_tv;

    android.widget.TextView telname_tv;

    android.widget.TextView homepage_tv;

    android.widget.TextView overview_tv;

    java.lang.String systemLang = "";

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        __SmPLUnsupported__(0).onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        init();
        android.content.Intent intent = getIntent();
        int id = intent.getExtras().getInt("id");
        items = com.example.a85625.seoultour.DB.selectCommonInfo(getBaseContext(), id);
        com.example.a85625.seoultour.Info_item item = items.get(0);
        systemLang = getResources().getConfiguration().locale.getDefault().getLanguage();// 시스템설정 언어 가져오기

        if (systemLang.equals("en")) {
            title_tv.setTextSize(25);
        }
        title_tv.setText(item.title);
        if (item.firstImage.equals("null")) {
            com.bumptech.glide.Glide.with(this).load(R.drawable.no_image).into(image);
        } else {
            com.bumptech.glide.Glide.with(this).load(item.firstImage).into(image);
        }
        if (item.zipcode.equals("null")) {
            if (item.addr1.equals("null")) {
                zipcode_tv.setText("(NO DATA)");
            } else {
                zipcode_tv.setText(item.addr1);
            }
        } else {
            zipcode_tv.setText(((item.addr1 + " (") + item.zipcode) + ")");
        }
        if (item.tel.equals("null")) {
            tel_tv.setText("(NO DATA)");
        } else {
            tel_tv.setText(item.tel);
        }
        if (item.telname.equals("null")) {
            telname_tv.setText("(NO DATA)");
        } else {
            telname_tv.setText(item.telname);
        }
        if (item.homepage.equals("null")) {
            homepage_tv.setText(Html.fromHtml("(NO DATA)", 0));
        } else {
            homepage_tv.setText(android.text.Html.fromHtml(item.homepage));
        }
        homepage_tv.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
        if (item.overview.equals("null")) {
            overview_tv.setText("(NO DATA)");
        } else {
            overview_tv.setText(android.text.Html.fromHtml(item.overview));
        }
    }

    void init() {
        items = new java.util.ArrayList<com.example.a85625.seoultour.Info_item>();
        image = ((android.widget.ImageView) (findViewById(R.id.imageView1)));
        title_tv = ((android.widget.TextView) (findViewById(R.id.title_tv)));
        zipcode_tv = ((android.widget.TextView) (findViewById(R.id.zipcode_tv)));
        tel_tv = ((android.widget.TextView) (findViewById(R.id.tel_tv)));
        telname_tv = ((android.widget.TextView) (findViewById(R.id.telname_tv)));
        homepage_tv = ((android.widget.TextView) (findViewById(R.id.homepage_tv)));
        overview_tv = ((android.widget.TextView) (findViewById(R.id.overview_tv)));
    }
}