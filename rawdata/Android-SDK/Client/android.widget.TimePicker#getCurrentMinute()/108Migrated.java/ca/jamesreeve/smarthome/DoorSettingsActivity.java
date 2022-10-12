package ca.jamesreeve.smarthome;
import android.support.v7.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class DoorSettingsActivity extends android.support.v7.app.AppCompatActivity {
    android.widget.TimePicker tp;

    android.widget.TimePicker tp2;

    android.widget.Switch s;

    android.widget.TimePicker.OnTimeChangedListener timePickerListener;

    android.widget.CompoundButton.OnCheckedChangeListener switchListener;

    com.google.firebase.database.FirebaseDatabase database;

    com.google.firebase.database.DatabaseReference doorSettingsRef;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_settings);
        s = ((android.widget.Switch) (findViewById(R.id.active)));
        tp = ((android.widget.TimePicker) (findViewById(R.id.lockTime)));
        tp2 = ((android.widget.TimePicker) (findViewById(R.id.unlockTime)));
        database = com.google.firebase.database.FirebaseDatabase.getInstance();
        doorSettingsRef = database.getReference("doorsettings");
        doorSettingsRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @java.lang.Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                android.util.Log.e("db", "dbok in doorsettings.java");
                tp.setOnTimeChangedListener(null);
                tp2.setOnTimeChangedListener(null);
                s.setOnCheckedChangeListener(null);
                tp.setCurrentHour(((java.lang.Number) (dataSnapshot.child("lockH").getValue())).intValue());
                tp.setCurrentMinute(((java.lang.Number) (dataSnapshot.child("lockM").getValue())).intValue());
                tp2.setCurrentHour(((java.lang.Number) (dataSnapshot.child("unlockH").getValue())).intValue());
                tp2.setCurrentMinute(((java.lang.Number) (dataSnapshot.child("unlockM").getValue())).intValue());
                s.setChecked(((boolean) (dataSnapshot.child("active").getValue())));
                tp.setOnTimeChangedListener(timePickerListener);
                tp2.setOnTimeChangedListener(timePickerListener);
                s.setOnCheckedChangeListener(switchListener);
            }

            @java.lang.Override
            public void onCancelled(com.google.firebase.database.DatabaseError databaseError) {
                android.util.Log.e("db", databaseError.toString());
            }
        });
        timePickerListener = new android.widget.TimePicker.OnTimeChangedListener() {
            @java.lang.Override
            public void onTimeChanged(android.widget.TimePicker view, int hourOfDay, int minute) {
                android.util.Log.d("deb", "activity.updatesettings");
                doorSettingsRef.setValue(new ca.jamesreeve.smarthome.DoorSettingsHelper(s.isChecked(), tp.getCurrentHour(), tp2.getMinute(), tp2.getCurrentHour(), tp2.getMinute()));
            }
        };
        switchListener = new android.widget.CompoundButton.OnCheckedChangeListener() {
            @java.lang.Override
            public void onCheckedChanged(android.widget.CompoundButton buttonView, boolean isChecked) {
                doorSettingsRef.setValue(new ca.jamesreeve.smarthome.DoorSettingsHelper(s.isChecked(), tp.getCurrentHour(), tp.getCurrentMinute(), tp2.getCurrentHour(), tp2.getCurrentMinute()));
            }
        };
        tp.setOnTimeChangedListener(timePickerListener);
        tp2.setOnTimeChangedListener(timePickerListener);
        s.setOnCheckedChangeListener(switchListener);
    }
}