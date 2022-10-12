@java.lang.Override
protected void onViewCreate(android.content.Context context, android.view.View view) {
    android.widget.LinearLayout.LayoutParams lp = new android.widget.LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
    lp.gravity = android.view.Gravity.CENTER;
    android.widget.LinearLayout ll = ((android.widget.LinearLayout) (view));
    ll.setOrientation(android.widget.LinearLayout.VERTICAL);
    ll.setGravity(android.view.Gravity.CENTER);
    android.widget.ProgressBar progressBar = new android.widget.ProgressBar(context);
    ll.addView(progressBar, lp);
    if (!android.text.TextUtils.isEmpty(title)) {
        tvTitle.setText(title);
        android.content.Context _CVAR0 = context;
        android.content.Context _CVAR4 = _CVAR0;
        android.widget.TextView tvTitle = new android.widget.TextView(_CVAR4);
        if (titleStyleRes == (-1)) {
            android.widget.TextView _CVAR1 = tvTitle;
            android.content.Context _CVAR2 = context;
            int _CVAR3 = android.R.style.TextAppearance_Large;
            _CVAR1.setTextAppearance(_CVAR2, _CVAR3);
        } else {
            android.widget.TextView _CVAR5 = tvTitle;
            android.content.Context _CVAR6 = context;
            int _CVAR7 = titleStyleRes;
            _CVAR5.setTextAppearance(_CVAR6, _CVAR7);
        }
        ll.addView(tvTitle, lp);
    }
    if (!android.text.TextUtils.isEmpty(subTitle)) {
        tvSubtitle.setText(subTitle);
        android.content.Context _CVAR8 = context;
        android.content.Context _CVAR12 = _CVAR8;
        android.widget.TextView tvSubtitle = new android.widget.TextView(_CVAR12);
        if (subTitleStyleRes == (-1)) {
            android.widget.TextView _CVAR9 = tvSubtitle;
            android.content.Context _CVAR10 = context;
            int _CVAR11 = android.R.style.TextAppearance_Medium;
            _CVAR9.setTextAppearance(_CVAR10, _CVAR11);
        } else {
            android.widget.TextView _CVAR13 = tvSubtitle;
            android.content.Context _CVAR14 = context;
            int _CVAR15 = subTitleStyleRes;
            _CVAR13.setTextAppearance(_CVAR14, _CVAR15);
        }
        ll.addView(tvSubtitle, lp);
    }
}