@java.lang.Override
public void onClick(android.view.View view) {
    if (ed_pass_new.getText().length() == 0) {
        ed_pass_new.requestFocus();
        if (lang.equals("2")) {
            java.lang.String _CVAR1 = "<font color='red'>رجاءا أدخل بريدك الإلكتروني</font>";
            android.widget.EditText _CVAR0 = ed_pass_new;
            android.text.Spanned _CVAR2 = android.text.Html.fromHtml(_CVAR1);
            _CVAR0.setError(_CVAR2);
        } else {
            java.lang.String _CVAR4 = "<font color='red'>Please Enter Your Email</font>";
            android.widget.EditText _CVAR3 = ed_pass_new;
            android.text.Spanned _CVAR5 = android.text.Html.fromHtml(_CVAR4);
            _CVAR3.setError(_CVAR5);
        }
    } else if (!ed_pass_new.getText().toString().equals(ed_confirm_pass.getText().toString())) {
        if (lang.equals("2")) {
            java.lang.String _CVAR7 = "<font color='red'>كلمة المرور لا تتطابق</font>";
            android.widget.EditText _CVAR6 = ed_confirm_pass;
            android.text.Spanned _CVAR8 = android.text.Html.fromHtml(_CVAR7);
            _CVAR6.setError(_CVAR8);
        } else {
            java.lang.String _CVAR10 = "<font color='red'>Password Don't Match</font>";
            android.widget.EditText _CVAR9 = ed_confirm_pass;
            android.text.Spanned _CVAR11 = android.text.Html.fromHtml(_CVAR10);
            _CVAR9.setError(_CVAR11);
        }
    } else if (view == btn_reset_new) {
        // RegisterOrder();
        // PlaceorderFuc();
        SendingNewPassword();
    }
}