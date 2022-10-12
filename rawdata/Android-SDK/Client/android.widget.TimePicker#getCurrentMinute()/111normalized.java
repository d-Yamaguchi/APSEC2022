@java.lang.Override
public void onClick(android.view.View view) {
    try {
        // check if coming from editing option
        if (editflag) {
            try {
                food.set_name(txtName.getText().toString());
                food.set_comment(txtComment.getText().toString());
                int cm = datePicker.getMonth() + 1;
                java.lang.String corrmonth = java.lang.String.valueOf(cm);
                int _CVAR0 = 10;
                boolean _CVAR1 = timePicker.getCurrentMinute() < _CVAR0;
                if () {
                    android.widget.TimePicker _CVAR2 = timePicker;
                    java.lang.Integer _CVAR3 = _CVAR2.getCurrentMinute();
                    java.lang.String newMin = "0" + _CVAR3;
                    java.lang.String dateAndTime = (((((((datePicker.getYear() + "-") + corrmonth) + "-") + datePicker.getDayOfMonth()) + " ") + timePicker.getCurrentHour()) + ":") + newMin;
                    food.set_time(dateAndTime);
                } else {
                    android.widget.TimePicker _CVAR4 = timePicker;
                    java.lang.Integer _CVAR5 = _CVAR4.getCurrentMinute();
                    java.lang.String dateAndTime = (((((((datePicker.getYear() + "-") + corrmonth) + "-") + datePicker.getDayOfMonth()) + " ") + timePicker.getCurrentHour()) + ":") + _CVAR5;
                    food.set_time(dateAndTime);
                }
                if (switchType.isChecked()) {
                    food.set_type(0);
                } else {
                    food.set_type(1);
                }
                db.updateFood(food);
                android.content.Intent intent = new android.content.Intent(getActivity(), nozagleh.org.gluttony.MainActivity.class);
                startActivity(intent);
            } catch (java.lang.Exception e) {
                android.widget.Toast.makeText(getActivity(), "Update failed", android.widget.Toast.LENGTH_SHORT).show();
            }
        } else if (txtName.length() != 0) {
            // Set date and time in one string
            int cm = datePicker.getMonth() + 1;
            java.lang.String corrmonth = java.lang.String.valueOf(cm);
            java.lang.String dateAndTime;
            android.widget.TimePicker _CVAR6 = timePicker;
            java.lang.Integer _CVAR7 = _CVAR6.getCurrentMinute();
            java.lang.String newMin = "0" + _CVAR7;
            java.lang.String newHour = "0" + timePicker.getCurrentHour();
            int _CVAR8 = 10;
            boolean _CVAR9 = timePicker.getCurrentMinute() < _CVAR8;
            if () {
                if (timePicker.getCurrentHour() < 10) {
                    dateAndTime = (((((((datePicker.getYear() + "-") + corrmonth) + "-") + datePicker.getDayOfMonth()) + " ") + newHour) + ":") + newMin;
                } else {
                    dateAndTime = (((((((datePicker.getYear() + "-") + corrmonth) + "-") + datePicker.getDayOfMonth()) + " ") + timePicker.getCurrentHour()) + ":") + newMin;
                }
            } else if (timePicker.getCurrentHour() < 10) {
                android.widget.TimePicker _CVAR10 = timePicker;
                java.lang.Integer _CVAR11 = _CVAR10.getCurrentMinute();
                java.lang.String _CVAR12 = (((((((datePicker.getYear() + "-") + corrmonth) + "-") + datePicker.getDayOfMonth()) + " ") + newHour) + ":") + _CVAR11;
                dateAndTime = _CVAR12;
            } else {
                android.widget.TimePicker _CVAR13 = timePicker;
                java.lang.Integer _CVAR14 = _CVAR13.getCurrentMinute();
                java.lang.String _CVAR15 = (((((((datePicker.getYear() + "-") + corrmonth) + "-") + datePicker.getDayOfMonth()) + " ") + timePicker.getCurrentHour()) + ":") + _CVAR14;
                dateAndTime = _CVAR15;
            }
            java.lang.Integer type = 0;
            if (!switchType.isChecked()) {
                type = 1;
            }
            // add the food to the database
            if (txtComment.length() != 0) {
                db.addFood(new nozagleh.org.gluttony.Food(txtName.getText().toString(), dateAndTime, txtComment.getText().toString(), type));
            } else {
                db.addFood(new nozagleh.org.gluttony.Food(txtName.getText().toString(), dateAndTime, "No description", type));
            }
            // simple message to the user that the food has been added
            // Toast.makeText(getActivity(), txtName.getText().toString() + " has been added", Toast.LENGTH_SHORT).show();
            new android.os.CountDownTimer(1000, 500) {
                android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getActivity());

                final android.app.Dialog dialog = alert.show();

                @java.lang.Override
                public void onTick(long l) {
                    dialog.setContentView(R.layout.insert_message);
                    dialog.setTitle(getString(R.string.txt_success));
                    dialog.setCancelable(false);
                    // there are a lot of settings, for dialog, check them all out!
                    final android.widget.ImageView imgDone = ((android.widget.ImageView) (dialog.findViewById(R.id.imgDone)));
                    imgDone.setImageResource(R.drawable.add_big);
                    // now that the dialog is set up, it's time to show it
                    dialog.show();
                }

                @java.lang.Override
                public void onFinish() {
                    dialog.dismiss();
                }
            }.start();
        } else {
            // give an error if name is not supplied
            txtName.setError(getString(R.string.error_no_name));
        }
        txtComment.setText("");
        txtName.setText("");
    } catch (java.lang.Exception e) {
        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getActivity());
        alert.setIcon(R.drawable.error);
        alert.setTitle(getString(R.string.alert_error_delete));
        alert.setMessage(getString(R.string.alert_error_inserting));
        alert.setNegativeButton(getString(R.string.btn_ok), new android.content.DialogInterface.OnClickListener() {
            @java.lang.Override
            public void onClick(android.content.DialogInterface dialogInterface, int i) {
            }
        });
        alert.show();
    }
}