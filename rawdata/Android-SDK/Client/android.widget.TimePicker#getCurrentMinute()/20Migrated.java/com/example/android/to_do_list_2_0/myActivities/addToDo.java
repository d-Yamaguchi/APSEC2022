package com.example.android.to_do_list_2_0.myActivities;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import com.example.android.to_do_list_2_0.R;
import com.example.android.to_do_list_2_0.myViewModel.myViewModel;
import com.example.android.to_do_list_2_0.room.Task;
public class addToDo extends android.support.v7.app.AppCompatActivity {
    private com.example.android.to_do_list_2_0.myViewModel.myViewModel viewModel;

    private java.util.ArrayList<com.example.android.to_do_list_2_0.room.Task> todoTask = new java.util.ArrayList<com.example.android.to_do_list_2_0.room.Task>();

    @java.lang.Override
    protected void onCreate(@android.support.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);
        // Force the keyboard to pop up
        android.view.inputmethod.InputMethodManager imm = ((android.view.inputmethod.InputMethodManager) (getSystemService(android.content.Context.INPUT_METHOD_SERVICE)));
        imm.toggleSoftInput(android.view.inputmethod.InputMethodManager.SHOW_FORCED, android.view.inputmethod.InputMethodManager.HIDE_IMPLICIT_ONLY);
        viewModel = android.arch.lifecycle.ViewModelProviders.of(this).get(com.example.android.to_do_list_2_0.myViewModel.myViewModel.class);
    }

    // User hit the "Add ToDo" button. Add in the new Task to the database and display task on screen
    public void addToDo(android.view.View view) {
        // Get the text the user entered
        android.widget.EditText editText = findViewById(R.id.edit_text_add_todo);
        editText.requestFocus();
        android.content.Intent replyIntent = new android.content.Intent();
        // replyIntent.putExtra("todoTask", editText.getText().toString());
        // Get time fro m the button
        android.widget.Button button = findViewById(R.id.button_add_time);
        java.lang.String[] array = new java.lang.String[2];
        array[0] = editText.getText().toString();
        array[1] = button.getText().toString();
        // Check to see if user entered a time or not
        if (array[1].equals("Add a Time")) {
            array[1] = "11:59PM";
        }
        replyIntent.putExtra("todoTask", array);
        // Get rid of the onscreen keyboard
        com.example.android.to_do_list_2_0.myActivities.addToDo.hideKeyboard(this);
        // Create toast saying todoTask added
        android.widget.Toast.makeText(this, "ToDo Created!", android.widget.Toast.LENGTH_SHORT).show();
        setResult(com.example.android.to_do_list_2_0.myActivities.RESULT_OK, replyIntent);
        finish();
    }

    // Hide keyboard
    public static void hideKeyboard(android.app.Activity activity) {
        android.view.inputmethod.InputMethodManager imm = ((android.view.inputmethod.InputMethodManager) (activity.getSystemService(android.app.Activity.INPUT_METHOD_SERVICE)));
        // Find the currently focused view, so we can grab the correct window token from it.
        android.view.View view = activity.getCurrentFocus();
        // If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new android.view.View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void addTime(android.view.View view) {
        // Hide Keyboard
        com.example.android.to_do_list_2_0.myActivities.addToDo.hideKeyboard(this);
        android.view.LayoutInflater layoutInflater = ((android.view.LayoutInflater) (this.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE)));
        android.view.View v = layoutInflater.inflate(R.layout.time_layout, null);
        final android.widget.TimePicker timePicker = v.findViewById(R.id.time_picker);
        android.widget.Button button = v.findViewById(R.id.button_test_time);
        final android.widget.PopupWindow popupWindow = new android.widget.PopupWindow(v, RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT, true);
        button.setOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View view) {
                checkTime(popupWindow, timePicker);
            }
        });
        timePicker.setOnTimeChangedListener(new android.widget.TimePicker.OnTimeChangedListener() {
            @java.lang.Override
            public void onTimeChanged(android.widget.TimePicker timePicker, int i, int i1) {
                // Have this minute variable because getCurrentMinute doesnt have "01", its only "1"
                java.lang.String minute = null;
                boolean apoveAPI23 = false;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    minute = java.lang.String.valueOf(timePicker.getMinute());
                    apoveAPI23 = true;
                } else {
                    minute = java.lang.String.valueOf(timePicker.getMinute());
                }
                if (apoveAPI23) {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        if (timePicker.getMinute() < 10) {
                            minute = "0" + java.lang.String.valueOf(timePicker.getMinute());
                        }
                    }
                } else {
                    minute = "0" + java.lang.String.valueOf(timePicker.getMinute());
                }
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    android.util.Log.d("timePicker", (("hour = " + java.lang.String.valueOf(timePicker.getHour())) + " minute = ") + java.lang.String.valueOf(i1));
                } else {
                    java.lang.String amOrPm = "PM";
                    if (apoveAPI23) {
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                            if (timePicker.getHour() < 12) {
                                amOrPm = "AM";
                            }
                        }
                    } else {
                        if (timePicker.getCurrentHour() < 12) {
                            amOrPm = "AM";
                        }
                        android.util.Log.d("timePicker", (((("hour = " + java.lang.String.valueOf(timePicker.getCurrentHour())) + "minute = ") + minute) + " ") + amOrPm);
                    }
                }
            }
        });
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(android.graphics.Color.TRANSPARENT));
        popupWindow.showAtLocation(v, android.view.Gravity.CENTER, 0, 0);
    }

    public void checkTime(android.widget.PopupWindow popupWindow, android.widget.TimePicker timePicker) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        // Have to check if the user entered hour is less than the current hour &&
        // If the hour is equal to the current hour check the minute time
        // getCurrentHour is in 24 hour time. ie 1pm = 13
        if ((timePicker.getCurrentHour() < calendar.get(java.util.Calendar.HOUR_OF_DAY)) || ((timePicker.getCurrentHour() == calendar.get(java.util.Calendar.HOUR_OF_DAY)) && (timePicker.getCurrentMinute() < calendar.get(java.util.Calendar.MINUTE)))) {
            android.util.Log.d("timePicker", (("hour = " + java.lang.String.valueOf(timePicker.getCurrentHour())) + " actual hour = ") + java.lang.String.valueOf(calendar.get(java.util.Calendar.HOUR_OF_DAY)));
            android.util.Log.d("timePicker", "minute = " + java.lang.String.valueOf((timePicker.getCurrentMinute() + "actual minute = ") + java.lang.String.valueOf(calendar.get(java.util.Calendar.MINUTE))));
            android.widget.Toast.makeText(this, "Time has to be after the current time. We can't time travel yet", android.widget.Toast.LENGTH_LONG).show();
            android.os.Vibrator vibrator = ((android.os.Vibrator) (getSystemService(android.content.Context.VIBRATOR_SERVICE)));
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                vibrator.vibrate(android.os.VibrationEffect.createOneShot(500, android.os.VibrationEffect.DEFAULT_AMPLITUDE));
            }
        } else // Time is valid
        {
            android.widget.Button button = findViewById(R.id.button_add_time);
            // Have this minute variable because getCurrentMinute doesnt have "01", its only "1"
            java.lang.String minute = java.lang.String.valueOf(timePicker.getCurrentMinute());
            if (timePicker.getCurrentMinute() < 10) {
                minute = "0" + java.lang.String.valueOf(timePicker.getCurrentMinute());
            }
            if (timePicker.getCurrentHour() > 12) {
                button.setText(((java.lang.String.valueOf(timePicker.getCurrentHour() - 12) + ":") + minute) + "PM");
            } else {
                button.setText(java.lang.String.valueOf(((timePicker.getCurrentHour() + ":") + minute) + "AM"));
            }
            popupWindow.dismiss();
        }
    }
}