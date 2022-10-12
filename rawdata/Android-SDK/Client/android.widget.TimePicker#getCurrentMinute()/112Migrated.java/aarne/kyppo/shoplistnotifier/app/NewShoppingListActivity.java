package aarne.kyppo.shoplistnotifier.app;
import android.support.v7.app.ActionBarActivity;
public class NewShoppingListActivity extends android.support.v7.app.ActionBarActivity {
    private android.widget.TimePicker start;

    private android.widget.TimePicker end;

    private android.widget.TextView title;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_shopping_list);
        start = ((android.widget.TimePicker) (findViewById(R.id.start_picker)));
        end = ((android.widget.TimePicker) (findViewById(R.id.end_picker)));
        title = ((android.widget.TextView) (findViewById(R.id.newtitle)));
        start.setIs24HourView(true);
        end.setIs24HourView(true);
    }

    public void addShoppingList(android.view.View v) {
        android.util.Log.d("addShoppingListMinute", start.getCurrentMinute() + "");
        android.util.Log.d("addShoppingListHour", start.getCurrentHour() + "");
        java.util.Calendar start_time = new java.util.GregorianCalendar();
        start_time.add(java.util.Calendar.MINUTE, start.getMinute());
        start_time.add(java.util.Calendar.HOUR_OF_DAY, start.getCurrentHour());
        android.util.Log.d("addShoppingListTimeAsString", start_time.toString());
        java.util.Calendar end_time = new java.util.GregorianCalendar();
        end_time.add(java.util.Calendar.MINUTE, start.getMinute());
        end_time.add(java.util.Calendar.HOUR_OF_DAY, end.getCurrentHour());
        sl.setStart(start.getCurrentHour(), start.getMinute());
        aarne.kyppo.shoplistnotifier.app.ShoppingList sl = new aarne.kyppo.shoplistnotifier.app.ShoppingList();
        sl.setEnd(end.getCurrentHour(), start.getMinute());
        sl.setTitle(title.getText().toString());
        aarne.kyppo.shoplistnotifier.app.DBHelper helper = new aarne.kyppo.shoplistnotifier.app.DBHelper(this);
        helper.addShoppingList(sl);
        int id = helper.getEarliestShoppingListID();
        android.util.Log.d("EARLIEST ID", id + "");
        helper = null;
        android.content.Intent i = new android.content.Intent(this, aarne.kyppo.shoplistnotifier.app.ShoppingListActivity.class);
        i.putExtra("earliest", id);
        startActivity(i);
    }

    @java.lang.Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_shopping_list, menu);
        return true;
    }

    @java.lang.Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}