@java.lang.Override
public void onClick(android.view.View v) {
    final int godzina = timePicker.getCurrentHour();
    void _CVAR0 = R.id.Zegar;
    final android.widget.TimePicker timePicker = ((android.widget.TimePicker) (findViewById(_CVAR0)));
    android.widget.TimePicker _CVAR1 = timePicker;
    final int minuta = _CVAR1.getCurrentMinute();
    android.content.Intent intent = getIntent();
    java.lang.String login = intent.getStringExtra("login");
    com.android.volley.Response.Listener<java.lang.String> responseListener = new com.android.volley.Response.Listener<java.lang.String>() {
        @java.lang.Override
        public void onResponse(java.lang.String response) {
            java.lang.String message = "Błąd";
            try {
                org.json.JSONObject jsonResponse = new org.json.JSONObject(response);
                boolean success = jsonResponse.getBoolean("success");
                if (success) {
                    if ((godzina > 0) && (minuta > 0)) {
                        message = ((("Komputer zostanie wyłączony za " + java.lang.Integer.toString(timePicker.getCurrentHour())) + "h i ") + java.lang.Integer.toString(timePicker.getCurrentMinute())) + "min";
                    } else if ((godzina > 0) && (minuta == 0)) {
                        message = ("Komputer zostanie wyłączony za " + java.lang.Integer.toString(timePicker.getCurrentHour())) + "h";
                    } else if ((godzina == 0) && (minuta > 0)) {
                        message = ("Komputer zostanie wyłączony za " + java.lang.Integer.toString(timePicker.getCurrentMinute())) + "min";
                    } else if ((godzina == 0) && (minuta == 0)) {
                        message = "Komputer zostanie wyłączony natychmiast";
                    } else {
                        message = "Błąd połączenia";
                    }
                    android.widget.Toast toast = android.widget.Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Register Failed").setNegativeButton("Retry", null).create().show();
                }
            } catch (org.json.JSONException e) {
                com.example.stas.shutdown.e.printStackTrace();
            }
        }
    };
    com.example.stas.shutdown.SetTimeRequest loginRequest = new com.example.stas.shutdown.SetTimeRequest(login, ((godzina * 360) + (minuta * 60)) + 1, responseListener);
    com.android.volley.RequestQueue queue = com.android.volley.toolbox.Volley.newRequestQueue(this);
    queue.add(loginRequest);
    // Animation animacja = new AlphaAnimation(1.0f, 0.0f);
    // animacja.setDuration(1500);
    // buttonWylacz.startAnimation(animacja);
}