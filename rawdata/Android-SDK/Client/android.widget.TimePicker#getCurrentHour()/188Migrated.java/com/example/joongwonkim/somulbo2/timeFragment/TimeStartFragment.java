package com.example.joongwonkim.somulbo2.timeFragment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import com.example.joongwonkim.somulbo2.R;
import com.example.joongwonkim.somulbo2.StateActivity;
import com.example.joongwonkim.somulbo2.adapter.TabPageAdapter;
import com.example.joongwonkim.somulbo2.data.Time;
import com.example.joongwonkim.somulbo2.fragmenttofragmentinterface.FragmentDataSendInterface;
/**
 * A simple {@link Fragment} subclass.
 */
public class TimeStartFragment extends android.support.v4.app.Fragment {
    android.widget.TimePicker timePicker;

    android.widget.Button buttonTimeStart;

    java.lang.String selectedHour;

    java.lang.String selectedMin;

    com.example.joongwonkim.somulbo2.fragmenttofragmentinterface.FragmentDataSendInterface mListener;

    public TimeStartFragment() {
    }

    @java.lang.Override
    public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
        android.view.View view = inflater.inflate(R.layout.fragment_time_start, container, false);
        // Inflate the layout for this fragment
        timePicker = ((android.widget.TimePicker) (view.findViewById(R.id.timepicker_start)));
        selectedHour = timePicker.getHour().toString();
        selectedMin = timePicker.getHour().toString();
        timePicker.setOnTimeChangedListener(__SmPLUnsupported__(0));
        buttonTimeStart = view.findViewById(R.id.btn_time_s);
        buttonTimeStart.setOnClickListener(__SmPLUnsupported__(1));
        return view;
    }

    @java.lang.Override
    public void onAttach(android.content.Context context) {
        super.onAttach(context);
        mListener = ((com.example.joongwonkim.somulbo2.StateActivity) (context));
    }
}