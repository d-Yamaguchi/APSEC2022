@java.lang.Override
public void bindView(android.view.View view, android.content.Context context, android.database.Cursor cursor) {
    org.imogene.android.database.ImogBeanCursor<?> c = ((org.imogene.android.database.ImogBeanCursor<?>) (cursor));
    view.findViewById(android.R.id.background).setBackgroundDrawable(mColor);
    main.setText(null);
    secondary.setText(null);
    android.view.View _CVAR0 = view;
    int _CVAR1 = android.R.id.text1;
    android.view.View _CVAR10 = _CVAR0;
    int _CVAR11 = _CVAR1;
    android.widget.TextView main = ((android.widget.TextView) (_CVAR10.findViewById(_CVAR11)));
    android.view.View _CVAR5 = view;
    int _CVAR6 = android.R.id.text2;
    android.view.View _CVAR15 = _CVAR5;
    int _CVAR16 = _CVAR6;
    android.widget.TextView secondary = ((android.widget.TextView) (_CVAR15.findViewById(_CVAR16)));
    if (c.getFlagRead()) {
        view.setBackgroundResource(android.R.drawable.list_selector_background);
        android.widget.TextView _CVAR2 = main;
        android.content.Context _CVAR3 = context;
        int _CVAR4 = android.R.style.TextAppearance_Medium;
        _CVAR2.setTextAppearance(_CVAR3, _CVAR4);
        main.setTypeface(android.graphics.Typeface.DEFAULT);
        android.widget.TextView _CVAR7 = secondary;
        android.content.Context _CVAR8 = context;
        int _CVAR9 = android.R.style.TextAppearance_Small;
        _CVAR7.setTextAppearance(_CVAR8, _CVAR9);
        secondary.setTypeface(android.graphics.Typeface.DEFAULT);
    } else {
        view.setBackgroundResource(R.drawable.imog__list_selector_background_inverse);
        android.widget.TextView _CVAR12 = main;
        android.content.Context _CVAR13 = context;
        int _CVAR14 = android.R.style.TextAppearance_Medium_Inverse;
        _CVAR12.setTextAppearance(_CVAR13, _CVAR14);
        main.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
        android.widget.TextView _CVAR17 = secondary;
        android.content.Context _CVAR18 = context;
        int _CVAR19 = android.R.style.TextAppearance_Small_Inverse;
        _CVAR17.setTextAppearance(_CVAR18, _CVAR19);
        secondary.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
    }
    android.widget.ImageView icon = ((android.widget.ImageView) (view.findViewById(android.R.id.icon)));
    if (icon != null) {
        icon.setImageResource(android.R.drawable.stat_notify_sync);
        icon.setVisibility(c.getFlagSynchronized() ? android.view.View.GONE : android.view.View.VISIBLE);
    }
    main.setText(c.getMainDisplay(context));
    java.lang.String sec = c.getSecondaryDisplay(context);
    if (android.text.TextUtils.isEmpty(sec.trim())) {
        secondary.setVisibility(android.view.View.GONE);
    } else {
        secondary.setVisibility(android.view.View.VISIBLE);
        secondary.setText(sec);
    }
}