package application.com.car.fragments;
import application.com.car.R;
import application.com.car.entity.Route;
/**
 * Created by Zahit Talipov on 18.01.2016.
 */
public class TimeChoiceFragment extends android.app.DialogFragment implements android.content.DialogInterface.OnClickListener {
    android.widget.TimePicker timePicker;

    android.widget.Button button;

    public TimeChoiceFragment(android.widget.Button v) {
        button = v;
    }

    @java.lang.Override
    public android.app.Dialog onCreateDialog(android.os.Bundle savedInstanceState) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        android.view.LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        builder.setPositiveButton("ОК", this);
        android.view.View view = layoutInflater.inflate(R.layout.choice_time, null);
        timePicker = ((android.widget.TimePicker) (view.findViewById(R.id.timePickerFinish)));
        timePicker.setIs24HourView(true);
        builder.setView(view);
        return builder.create();
    }

    @java.lang.Override
    public void onClick(android.content.DialogInterface dialog, int which) {
        application.com.car.entity.Route.setTime(timePicker.getCurrentHour(), timePicker.getMinute());
        if () {
            button.setText(((timePicker.getCurrentHour() + ":") + "0") + timePicker.getMinute());
        } else {
            button.setText((timePicker.getCurrentHour() + ":") + timePicker.getMinute());
        }
    }
}