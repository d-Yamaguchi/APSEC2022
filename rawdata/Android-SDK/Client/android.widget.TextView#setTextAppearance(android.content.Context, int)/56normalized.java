@java.lang.Override
protected void onCreate(android.os.Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    final android.database.sqlite.SQLiteDatabase database = DbCreate();
    DropTable(database);
    TableCreate(database);
    InsertCar(database);
    cars = GetAllCars(database);
    android.widget.TabHost tabhost = ((android.widget.TabHost) (findViewById(R.id.tabHost)));
    final android.widget.TableLayout t2 = ((android.widget.TableLayout) (findViewById(R.id.TableLayout2)));
    tabhost.setup();
    android.widget.TabHost.TabSpec tabspec = tabhost.newTabSpec("carros");
    tabspec.setContent(R.id.Carros);
    tabspec.setIndicator("Carros");
    tabhost.addTab(tabspec);
    tabspec = tabhost.newTabSpec("comparar");
    tabspec.setContent(R.id.Comparar);
    tabspec.setIndicator("Comparar");
    tabhost.addTab(tabspec);
    java.util.List<java.lang.String> spinnerArray = new java.util.ArrayList<java.lang.String>();
    for (int i = 0; i < cars.size(); i++) {
        spinnerArray.add(cars.get(i).getModel().toString());
    }
    android.widget.ArrayAdapter<java.lang.String> adapter = new android.widget.ArrayAdapter<java.lang.String>(this, android.R.layout.simple_spinner_item, spinnerArray);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    final android.widget.Spinner sItems = ((android.widget.Spinner) (findViewById(R.id.spinner)));
    final android.widget.Spinner sItems2 = ((android.widget.Spinner) (findViewById(R.id.spinner2)));
    sItems.setAdapter(adapter);
    sItems2.setAdapter(adapter);
    android.widget.TableLayout tl = ((android.widget.TableLayout) (findViewById(R.id.TableLayout)));
    final android.widget.Button btn = ((android.widget.Button) (findViewById(R.id.button)));
    btn.setOnClickListener(new android.view.View.OnClickListener() {
        @java.lang.Override
        public void onClick(android.view.View v) {
            t2.removeAllViews();
            car1 = GetCarByModel(database, sItems.getSelectedItem().toString());
            car2 = GetCarByModel(database, sItems2.getSelectedItem().toString());
            preencheTable(t2);
        }
    });
    android.widget.TableRow tr;
    android.widget.TextView tv;
    for (int i = 0; i < cars.size(); i++) {
        tr = new android.widget.TableRow(this);
        tv = new android.widget.TextView(this);
        tr.setLayoutParams(new android.widget.TableRow.LayoutParams(android.widget.TableRow.LayoutParams.FILL_PARENT, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
        android.widget.TableRow.LayoutParams paramsExample = new android.widget.TableRow.LayoutParams(android.widget.TableRow.LayoutParams.WRAP_CONTENT, android.widget.TableRow.LayoutParams.WRAP_CONTENT, 1.0F);
        tv.setText(cars.get(i).getModel().toString());
        tv.setLayoutParams(paramsExample);
        android.widget.TextView _CVAR0 = tv;
        android.content.Context _CVAR1 = getApplicationContext();
        int _CVAR2 = android.R.style.TextAppearance_Medium;
        _CVAR0.setTextAppearance(_CVAR1, _CVAR2);
        tr.addView(tv);
        tr.setClickable(true);
        tl.addView(tr, new android.widget.TableLayout.LayoutParams(android.widget.TableLayout.LayoutParams.FILL_PARENT, android.widget.TableLayout.LayoutParams.WRAP_CONTENT));
    }
}