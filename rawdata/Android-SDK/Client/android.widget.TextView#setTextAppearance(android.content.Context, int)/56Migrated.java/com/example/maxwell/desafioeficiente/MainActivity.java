package com.example.maxwell.desafioeficiente;
import android.support.v7.app.ActionBarActivity;
public class MainActivity extends android.app.Activity {
    java.util.List<com.example.maxwell.desafioeficiente.Cars> cars = new java.util.ArrayList<com.example.maxwell.desafioeficiente.Cars>();

    com.example.maxwell.desafioeficiente.Cars car1;

    com.example.maxwell.desafioeficiente.Cars car2;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        __SmPLUnsupported__(0).onCreate(savedInstanceState);
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
        btn.setOnClickListener(__SmPLUnsupported__(1));
        android.widget.TableRow tr;
        android.widget.TextView tv;
        for (int i = 0; i < cars.size(); i++) {
            tr = new android.widget.TableRow(this);
            tv = new android.widget.TextView(this);
            tr.setLayoutParams(new android.widget.TableRow.LayoutParams(android.widget.TableRow.LayoutParams.FILL_PARENT, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
            android.widget.TableRow.LayoutParams paramsExample = new android.widget.TableRow.LayoutParams(android.widget.TableRow.LayoutParams.WRAP_CONTENT, android.widget.TableRow.LayoutParams.WRAP_CONTENT, 1.0F);
            tv.setText(cars.get(i).getModel().toString());
            tv.setLayoutParams(paramsExample);
            TextView textView = new android.widget.TextView(context);
            int resid = android.R.style.TextAppearance_Large;
            textView.setTextAppearance(resid);
            tr.addView(tv);
            tr.setClickable(true);
            tl.addView(tr, new android.widget.TableLayout.LayoutParams(android.widget.TableLayout.LayoutParams.FILL_PARENT, android.widget.TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }

    @java.lang.Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @java.lang.Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        // noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void preencheTable(android.widget.TableLayout t2) {
        android.widget.TableRow tr = new android.widget.TableRow(this);
        android.widget.TableRow tr2 = new android.widget.TableRow(this);
        android.widget.TableRow tr3 = new android.widget.TableRow(this);
        android.widget.TableRow tr4 = new android.widget.TableRow(this);
        android.widget.TableRow tr5 = new android.widget.TableRow(this);
        android.widget.TableRow tr6 = new android.widget.TableRow(this);
        android.widget.TableRow tr7 = new android.widget.TableRow(this);
        android.widget.TableRow tr8 = new android.widget.TableRow(this);
        tr.setLayoutParams(new android.widget.TableRow.LayoutParams(android.widget.TableRow.LayoutParams.MATCH_PARENT, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
        android.widget.TableRow.LayoutParams paramsExample = new android.widget.TableRow.LayoutParams(android.widget.TableRow.LayoutParams.WRAP_CONTENT, android.widget.TableRow.LayoutParams.WRAP_CONTENT, 1.0F);
        android.widget.TextView tv1 = new android.widget.TextView(this);
        android.widget.TextView tv2 = new android.widget.TextView(this);
        android.widget.TextView tv3 = new android.widget.TextView(this);
        tv1.setLayoutParams(paramsExample);
        tv1.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Medium);
        tv2.setLayoutParams(paramsExample);
        tv2.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Medium);
        tv3.setLayoutParams(paramsExample);
        tv3.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Medium);
        tv1.setText("MODELO");
        tv2.setText(car1.getModel().toString());
        tv3.setText(car2.getModel().toString());
        tr.addView(tv1);
        tr.addView(tv2);
        tr.addView(tv3);
        t2.addView(tr, new android.widget.TableLayout.LayoutParams(android.widget.TableLayout.LayoutParams.MATCH_PARENT, android.widget.TableLayout.LayoutParams.WRAP_CONTENT));
        android.widget.TextView tv4 = new android.widget.TextView(this);
        android.widget.TextView tv5 = new android.widget.TextView(this);
        android.widget.TextView tv6 = new android.widget.TextView(this);
        tv4.setLayoutParams(paramsExample);
        tv4.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Medium);
        tv5.setLayoutParams(paramsExample);
        tv5.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Medium);
        tv6.setLayoutParams(paramsExample);
        tv6.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Medium);
        tv4.setText("PRECO");
        tv5.setText("R$ " + car1.getPrice().toString());
        tv6.setText("R$ " + car2.getPrice().toString());
        tr2.addView(tv4);
        tr2.addView(tv5);
        tr2.addView(tv6);
        t2.addView(tr2, new android.widget.TableLayout.LayoutParams(android.widget.TableLayout.LayoutParams.MATCH_PARENT, android.widget.TableLayout.LayoutParams.WRAP_CONTENT));
        android.widget.TextView tv7 = new android.widget.TextView(this);
        android.widget.TextView tv8 = new android.widget.TextView(this);
        android.widget.TextView tv9 = new android.widget.TextView(this);
        tv7.setLayoutParams(paramsExample);
        tv7.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Medium);
        tv8.setLayoutParams(paramsExample);
        tv8.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Medium);
        tv9.setLayoutParams(paramsExample);
        tv9.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Medium);
        tv7.setText("CAVALOS");
        tv8.setText(car1.getHp().toString());
        tv9.setText(car2.getHp().toString());
        tr3.addView(tv7);
        tr3.addView(tv8);
        tr3.addView(tv9);
        t2.addView(tr3, new android.widget.TableLayout.LayoutParams(android.widget.TableLayout.LayoutParams.MATCH_PARENT, android.widget.TableLayout.LayoutParams.WRAP_CONTENT));
        android.widget.TextView tv10 = new android.widget.TextView(this);
        android.widget.TextView tv11 = new android.widget.TextView(this);
        android.widget.TextView tv12 = new android.widget.TextView(this);
        tv10.setLayoutParams(paramsExample);
        tv10.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Medium);
        tv11.setLayoutParams(paramsExample);
        tv11.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Medium);
        tv12.setLayoutParams(paramsExample);
        tv12.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Medium);
        tv10.setText("CONSUMO GAS");
        tv11.setText(car1.getConsumptionGas().toString());
        tv12.setText(car2.getConsumptionGas().toString());
        tr4.addView(tv10);
        tr4.addView(tv11);
        tr4.addView(tv12);
        t2.addView(tr4, new android.widget.TableLayout.LayoutParams(android.widget.TableLayout.LayoutParams.MATCH_PARENT, android.widget.TableLayout.LayoutParams.WRAP_CONTENT));
        android.widget.TextView tv13 = new android.widget.TextView(this);
        android.widget.TextView tv14 = new android.widget.TextView(this);
        android.widget.TextView tv15 = new android.widget.TextView(this);
        tv13.setLayoutParams(paramsExample);
        tv13.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Medium);
        tv14.setLayoutParams(paramsExample);
        tv14.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Medium);
        tv15.setLayoutParams(paramsExample);
        tv15.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Medium);
        tv13.setText("CONSUMO ETAN");
        tv14.setText(car1.getConsumptionEth().toString());
        tv15.setText(car2.getConsumptionEth().toString());
        tr5.addView(tv13);
        tr5.addView(tv14);
        tr5.addView(tv15);
        t2.addView(tr5, new android.widget.TableLayout.LayoutParams(android.widget.TableLayout.LayoutParams.MATCH_PARENT, android.widget.TableLayout.LayoutParams.WRAP_CONTENT));
        android.widget.TextView tv16 = new android.widget.TextView(this);
        android.widget.TextView tv17 = new android.widget.TextView(this);
        android.widget.TextView tv18 = new android.widget.TextView(this);
        tv16.setLayoutParams(paramsExample);
        tv16.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Medium);
        tv17.setLayoutParams(paramsExample);
        tv17.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Medium);
        tv18.setLayoutParams(paramsExample);
        tv18.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Medium);
        tv16.setText("REVISAO");
        tv17.setText("R$ " + car1.getRevisionAVG().toString());
        tv18.setText("R$ " + car2.getRevisionAVG().toString());
        tr6.addView(tv16);
        tr6.addView(tv17);
        tr6.addView(tv18);
        t2.addView(tr6, new android.widget.TableLayout.LayoutParams(android.widget.TableLayout.LayoutParams.MATCH_PARENT, android.widget.TableLayout.LayoutParams.WRAP_CONTENT));
        android.widget.TextView tv19 = new android.widget.TextView(this);
        android.widget.TextView tv20 = new android.widget.TextView(this);
        android.widget.TextView tv21 = new android.widget.TextView(this);
        tv19.setLayoutParams(paramsExample);
        tv19.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Medium);
        tv20.setLayoutParams(paramsExample);
        tv20.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Medium);
        tv21.setLayoutParams(paramsExample);
        tv21.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Medium);
        tv19.setText("SEGURO");
        tv20.setText("R$ " + car1.getRevisionAVG().toString());
        tv21.setText("R$ " + car2.getRevisionAVG().toString());
        tr7.addView(tv19);
        tr7.addView(tv20);
        tr7.addView(tv21);
        t2.addView(tr7, new android.widget.TableLayout.LayoutParams(android.widget.TableLayout.LayoutParams.MATCH_PARENT, android.widget.TableLayout.LayoutParams.WRAP_CONTENT));
        android.widget.TextView tv22 = new android.widget.TextView(this);
        android.widget.TextView tv23 = new android.widget.TextView(this);
        android.widget.TextView tv24 = new android.widget.TextView(this);
        tv22.setLayoutParams(paramsExample);
        tv22.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Medium);
        tv23.setLayoutParams(paramsExample);
        tv23.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Medium);
        tv24.setLayoutParams(paramsExample);
        tv24.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Medium);
        tv22.setText("PONTOS");
        tv23.setText(car1.getPoints().toString());
        tv24.setText(car2.getPoints().toString());
        tr8.addView(tv22);
        tr8.addView(tv23);
        tr8.addView(tv24);
        t2.addView(tr8, new android.widget.TableLayout.LayoutParams(android.widget.TableLayout.LayoutParams.MATCH_PARENT, android.widget.TableLayout.LayoutParams.WRAP_CONTENT));
    }

    private android.database.sqlite.SQLiteDatabase DbCreate() {
        return openOrCreateDatabase("desafioEficiente.db", android.content.Context.MODE_PRIVATE, null);
    }

    private void TableCreate(android.database.sqlite.SQLiteDatabase database) {
        database.execSQL("create table if not exists car (model text, hp text, price text, consumptiongas text, consumptioneht text, revisionavg text, " + "insuranceavg text, points text)");
    }

    private void InsertCar(android.database.sqlite.SQLiteDatabase database) {
        database.execSQL("insert into car values ('VW Gol 1.0 2015', '68', '29500', '15', '13', '365', '2700', '4')");
        database.execSQL("insert into car values ('Fiat Palio 1.0 2015', '65', '28500', '14', '12', '360', '2500', '3')");
        database.execSQL("insert into car values ('FordKa 1.0 2015', '60', '27000', '17', '15', '300', '1700', '5')");
        database.execSQL("insert into car values ('Chevrolet Vectra 2.0 2015', '80', '40500', '10', '12', '500', '3500', '6')");
        database.execSQL("insert into car values ('Chevrolet Onix 1.6 2015', '70', '35500', '15', '12', '365', '2700', '4')");
        database.execSQL("insert into car values ('VW Fusca 1.0 1990', '50', '10000', '14', '12', '360', '2500', '3')");
    }

    private void DropTable(android.database.sqlite.SQLiteDatabase database) {
        database.execSQL("drop table if exists car");
    }

    private com.example.maxwell.desafioeficiente.Cars GetCarByModel(android.database.sqlite.SQLiteDatabase database, java.lang.String modelo) {
        android.database.Cursor cursor = database.rawQuery((("select * from car where model = " + "'") + modelo) + "'", null);
        com.example.maxwell.desafioeficiente.Cars car = new com.example.maxwell.desafioeficiente.Cars();
        if (cursor.moveToFirst()) {
            car = new com.example.maxwell.desafioeficiente.Cars(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
        }
        return car;
    }

    private java.util.List<com.example.maxwell.desafioeficiente.Cars> GetAllCars(android.database.sqlite.SQLiteDatabase database) {
        android.database.Cursor cursor = database.rawQuery("select * from car", null);
        java.util.List<com.example.maxwell.desafioeficiente.Cars> cars = new java.util.ArrayList<com.example.maxwell.desafioeficiente.Cars>();
        if (cursor != null) {
            // move cursor to first row
            if (cursor.moveToFirst()) {
                do {
                    // Get version from Cursor
                    // String bookName = cursor.getString(cursor.getColumnIndex("bookTitle"));
                    cars.add(new com.example.maxwell.desafioeficiente.Cars(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7)));
                    // add the bookName into the bookTitles ArrayList
                    // move to next row
                } while (cursor.moveToNext() );
            }
        }
        return cars;
    }
}