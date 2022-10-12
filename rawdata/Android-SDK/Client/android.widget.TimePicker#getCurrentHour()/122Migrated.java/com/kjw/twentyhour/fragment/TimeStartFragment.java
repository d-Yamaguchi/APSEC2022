package com.kjw.twentyhour.fragment;
import android.support.v4.app.Fragment;
import com.kjw.twentyhour.R;
import com.kjw.twentyhour.StateActivity;
import com.kjw.twentyhour.data.Time;
import com.kjw.twentyhour.fragmenttofragmentinterface.FragmentDataSendInterface;
/**
 * A simple {@link Fragment} subclass.
 */
public class TimeStartFragment extends android.support.v4.app.Fragment {
    android.widget.TimePicker timePicker;

    android.widget.Button buttonTimeStart;

    int selectedHour;

    int selectedMin;

    com.kjw.twentyhour.fragmenttofragmentinterface.FragmentDataSendInterface mListener;

    public TimeStartFragment() {
    }

    @java.lang.Override
    public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
        android.view.View view = inflater.inflate(R.layout.fragment_time_start, container, false);
        // Inflate the layout for this fragment
        timePicker = ((android.widget.TimePicker) (view.findViewById(R.id.timepicker_start)));
        timePicker.setIs24HourView(true);
        selectedHour = timePicker.getHour();
        selectedMin = timePicker.getHour();
        timePicker.setOnTimeChangedListener(__SmPLUnsupported__(0));
        buttonTimeStart = view.findViewById(R.id.btn_time_s);
        buttonTimeStart.setOnClickListener(__SmPLUnsupported__(1));
        return view;
    }

    @java.lang.Override
    public void onAttach(android.content.Context context) {
        super.onAttach(context);
        mListener = ((com.kjw.twentyhour.StateActivity) (context));
    }
}