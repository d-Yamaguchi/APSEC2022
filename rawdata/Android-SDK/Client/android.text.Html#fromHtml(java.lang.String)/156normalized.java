public void run() {
    app.android.pmdlocker.com.pmd_locker.utils.Utility.dialogMessage = new android.app.Dialog(activity, R.style.CustomDialog);
    app.android.pmdlocker.com.pmd_locker.utils.Utility.dialogMessage.setContentView(root);
    if (isShowTitle) {
        tvTitle.setText(app.android.pmdlocker.com.pmd_locker.utils.Utility.getText(R.string.text_title_message, activity));
        tvTitle.setVisibility(android.view.View.VISIBLE);
    } else {
        tvTitle.setVisibility(android.view.View.GONE);
    }
    android.view.View _CVAR4 = root;
    void _CVAR5 = R.id.textVTitle;
    android.widget.TextView tvTitle = ((android.widget.TextView) (_CVAR4.findViewById(_CVAR5)));
    if (!android.text.TextUtils.isEmpty(title)) {
        java.lang.String _CVAR7 = title;
        android.widget.TextView _CVAR6 = tvTitle;
        android.text.Spanned _CVAR8 = android.text.Html.fromHtml(_CVAR7);
        _CVAR6.setText(_CVAR8);
    }
    android.view.View _CVAR13 = root;
    void _CVAR14 = R.id.textVMessage;
    android.widget.TextView tvMessage = ((android.widget.TextView) (_CVAR13.findViewById(_CVAR14)));
    java.lang.String _CVAR16 = mess;
    android.widget.TextView _CVAR15 = tvMessage;
    android.text.Spanned _CVAR17 = android.text.Html.fromHtml(_CVAR16);
    _CVAR15.setText(_CVAR17);
    java.lang.String _CVAR25 = nameButton2;
    android.widget.TextView _CVAR24 = btn;
    android.text.Spanned _CVAR26 = android.text.Html.fromHtml(_CVAR25);
    _CVAR24.setText(_CVAR26);
    btn.setOnClickListener(new android.view.View.OnClickListener() {
        @java.lang.Override
        public void onClick(android.view.View v) {
            if (icallback != null) {
                icallback.onChooseYes();
            }
            app.android.pmdlocker.com.pmd_locker.utils.Utility.closeDialogMessage();
        }
    });
    android.app.Activity _CVAR0 = activity;
    android.app.Activity _CVAR9 = _CVAR0;
    android.app.Activity _CVAR18 = _CVAR9;
    android.app.Activity _CVAR27 = _CVAR18;
    android.view.LayoutInflater _CVAR1 = android.view.LayoutInflater.from(_CVAR27);
    android.view.LayoutInflater _CVAR10 = _CVAR1;
    android.view.LayoutInflater _CVAR19 = _CVAR10;
    void _CVAR2 = R.layout.dialog_message;
    void _CVAR11 = _CVAR2;
    void _CVAR20 = _CVAR11;
    <nulltype> _CVAR3 = null;
    <nulltype> _CVAR12 = _CVAR3;
    <nulltype> _CVAR21 = _CVAR12;
    android.view.LayoutInflater _CVAR28 = _CVAR19;
    void _CVAR29 = _CVAR20;
    <nulltype> _CVAR30 = _CVAR21;
    android.view.View root = _CVAR28.inflate(_CVAR29, _CVAR30);
    android.view.View _CVAR22 = root;
    void _CVAR23 = R.id.btnOk;
    android.view.View _CVAR31 = _CVAR22;
    void _CVAR32 = _CVAR23;
    android.widget.TextView btn = ((android.widget.TextView) (_CVAR31.findViewById(_CVAR32)));
    if (nameButton1 == null) {
        android.view.View v = ((android.view.View) (root.findViewById(R.id.vHCenter)));
        v.setVisibility(android.view.View.GONE);
        btn = ((android.widget.TextView) (root.findViewById(R.id.btnCancel)));
        btn.setVisibility(android.view.View.GONE);
    } else {
        btn = ((android.widget.TextView) (root.findViewById(R.id.btnCancel)));
        java.lang.String _CVAR34 = nameButton1;
        android.widget.TextView _CVAR33 = btn;
        android.text.Spanned _CVAR35 = android.text.Html.fromHtml(_CVAR34);
        _CVAR33.setText(_CVAR35);
        btn.setOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View v) {
                if (icallback != null) {
                    icallback.onChooseNo();
                }
                app.android.pmdlocker.com.pmd_locker.utils.Utility.closeDialogMessage();
            }
        });
    }
    app.android.pmdlocker.com.pmd_locker.utils.Utility.dialogMessage.setCancelable(true);
    app.android.pmdlocker.com.pmd_locker.utils.Utility.dialogMessage.show();
}