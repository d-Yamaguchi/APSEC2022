package com.thinkgeniux.focusi.Activities_English;
import android.support.v7.app.AppCompatActivity;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.thinkgeniux.focusi.Config;
import com.thinkgeniux.focusi.R;
public class ResetPassword extends android.support.v7.app.AppCompatActivity {
    android.widget.EditText ed_pass_new;

    android.widget.EditText ed_confirm_pass;

    android.widget.Button btn_reset_new;

    java.lang.String lang;

    android.widget.TextView title;

    private android.app.ProgressDialog loading;

    java.lang.String s_email;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.content.SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, android.content.Context.MODE_PRIVATE);
        lang = sharedPreferences.getString(Config.SHARED_PREF_LANG, null);
        if (lang.equals(Config.ARABIC_SYMBOL_LANG)) {
            setContentView(R.layout.activity_reset_password_arabic);
            title = ((android.widget.TextView) (findViewById(R.id.title)));
            title.setText("Forget Password");
        } else {
            setContentView(R.layout.activity_reset_password);
            title = ((android.widget.TextView) (findViewById(R.id.title)));
            title.setText("Forget Password");
        }
        android.content.Intent intent = getIntent();
        s_email = intent.getStringExtra("email");
        ed_pass_new = ((android.widget.EditText) (findViewById(R.id.ed_pass_new)));
        ed_confirm_pass = ((android.widget.EditText) (findViewById(R.id.ed_confirm_pass)));
        btn_reset_new = ((android.widget.Button) (findViewById(R.id.btn_reset_new)));
        btn_reset_new.setOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View view) {
                if (ed_pass_new.getText().length() == 0) {
                    ed_pass_new.requestFocus();
                    if (lang.equals("2")) {
                        ed_pass_new.setError(Html.fromHtml("<font color='red'>رجاءا أدخل بريدك الإلكتروني</font>", 0));
                    } else {
                        ed_pass_new.setError(android.text.Html.fromHtml("<font color='red'>Please Enter Your Email</font>"));
                    }
                } else if (!ed_pass_new.getText().toString().equals(ed_confirm_pass.getText().toString())) {
                    if (lang.equals("2")) {
                        ed_confirm_pass.setError(android.text.Html.fromHtml("<font color='red'>كلمة المرور لا تتطابق</font>"));
                    } else {
                        ed_confirm_pass.setError(android.text.Html.fromHtml("<font color='red'>Password Don't Match</font>"));
                    }
                } else if (view == btn_reset_new) {
                    // RegisterOrder();
                    // PlaceorderFuc();
                    SendingNewPassword();
                }
            }
        });
    }

    private void SendingNewPassword() {
        loading = android.app.ProgressDialog.show(this, "Loading...", "Please wait...", false, false);
        com.android.volley.toolbox.StringRequest request = new com.android.volley.toolbox.StringRequest(Request.Method.POST, com.thinkgeniux.focusi.Config.URL_SENDING_NEWPASS, new com.android.volley.Response.Listener<java.lang.String>() {
            @java.lang.Override
            public void onResponse(java.lang.String response) {
                loading.dismiss();
                org.json.JSONArray jsonObject = null;
                try {
                    jsonObject = new org.json.JSONArray(response);
                    org.json.JSONObject jsonObject1 = jsonObject.getJSONObject(0);
                    if (jsonObject1.getString("status").equals("Password updated.")) {
                        android.widget.Toast.makeText(ResetPassword.this.getApplicationContext(), jsonObject1.getString("status"), Toast.LENGTH_SHORT).show();
                        android.content.Intent intent = new android.content.Intent(ResetPassword.this, .class);
                        intent.putExtra("cart", "main");
                        startActivity(intent);
                    } else {
                        android.widget.Toast.makeText(ResetPassword.this.getApplicationContext(), jsonObject1.getString("status"), Toast.LENGTH_SHORT).show();
                    }
                } catch (org.json.JSONException e) {
                    android.widget.Toast.makeText(ResetPassword.this.getApplicationContext(), "Check Your PasswordXk;l", Toast.LENGTH_SHORT).show();
                    com.thinkgeniux.focusi.Activities_English.e.printStackTrace();
                }
                // tvSurah.setText("Response is: "+ response.substring(0,500));
            }
        }, new com.android.volley.Response.ErrorListener() {
            @java.lang.Override
            public void onErrorResponse(com.android.volley.VolleyError error) {
                loading.dismiss();
                // Log.e("Error",error.printStackTrace());
                android.widget.Toast.makeText(ResetPassword.this.getApplicationContext(), "Network Error", android.widget.Toast.LENGTH_SHORT).show();
            }
        }) {
            @java.lang.Override
            protected java.util.Map<java.lang.String, java.lang.String> getParams() {
                java.util.Map<java.lang.String, java.lang.String> params = new java.util.HashMap<java.lang.String, java.lang.String>();
                params.put("email", s_email);
                params.put("password", ed_pass_new.getText().toString().trim());
                return params;
            }
        };
        request.setRetryPolicy(new com.android.volley.DefaultRetryPolicy(0, com.android.volley.DefaultRetryPolicy.DEFAULT_MAX_RETRIES, com.android.volley.DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        com.android.volley.RequestQueue requestQueue = com.android.volley.toolbox.Volley.newRequestQueue(this.getApplicationContext());
        requestQueue.add(request);
    }
}