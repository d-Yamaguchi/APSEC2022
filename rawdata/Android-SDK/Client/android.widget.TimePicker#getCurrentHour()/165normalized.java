public void addParalelo(android.view.View v) {
    c.put("Nombre", paralelo.getText().toString().trim());
    c.put("Cupo", java.lang.Integer.parseInt(cupo.getText().toString().trim()));
    android.widget.TimePicker _CVAR2 = horario1;
    java.lang.Integer _CVAR3 = _CVAR2.getCurrentHour();
    java.lang.String _CVAR4 = dia1.getText().toString() + _CVAR3;
    java.lang.String _CVAR5 = _CVAR4 + horario1.getCurrentMinute();
    android.content.ContentValues _CVAR0 = c;
    java.lang.String _CVAR1 = "Horario1";
    java.lang.String _CVAR6 = _CVAR5 + aula1.getText().toString().trim();
    _CVAR0.put(_CVAR1, _CVAR6);
    android.content.ContentValues c = new android.content.ContentValues();
    android.widget.TimePicker _CVAR9 = horario2;
    java.lang.Integer _CVAR10 = _CVAR9.getCurrentHour();
    java.lang.String _CVAR11 = dia2.getText().toString() + _CVAR10;
    java.lang.String _CVAR12 = _CVAR11 + horario2.getCurrentMinute();
    android.content.ContentValues _CVAR7 = c;
    java.lang.String _CVAR8 = "Horario2";
    java.lang.String _CVAR13 = _CVAR12 + aula2.getText().toString().trim();
    _CVAR7.put(_CVAR8, _CVAR13);
    if (item != null) {
        c.put("Sigla", item[0]);
    }
    android.widget.TimePicker _CVAR16 = horario1;
    java.lang.Integer _CVAR17 = _CVAR16.getCurrentHour();
    java.lang.String _CVAR18 = dia1.getText().toString() + _CVAR17;
    java.lang.String _CVAR19 = _CVAR18 + horario1.getCurrentMinute();
    android.content.ContentValues _CVAR14 = ch1;
    java.lang.String _CVAR15 = "idHorario";
    java.lang.String _CVAR20 = _CVAR19 + aula1.getText().toString().trim();
    _CVAR14.put(_CVAR15, _CVAR20);
    ch1.put("Dia", dia1.getText().toString());
    android.content.ContentValues ch1 = new android.content.ContentValues();
    android.widget.TimePicker _CVAR23 = horario1;
    java.lang.Integer _CVAR24 = _CVAR23.getCurrentMinute();
    android.content.ContentValues _CVAR21 = ch1;
    java.lang.String _CVAR22 = "Horario";
    int _CVAR25 = horario1.getCurrentHour() + _CVAR24;
    _CVAR21.put(_CVAR22, _CVAR25);
    ch1.put("Duracion", java.lang.Integer.parseInt(duracion1.getText().toString().trim()));
    ch1.put("Aula", aula1.getText().toString().trim());
    android.widget.TimePicker _CVAR28 = horario2;
    java.lang.Integer _CVAR29 = _CVAR28.getCurrentHour();
    java.lang.String _CVAR30 = dia2.getText().toString() + _CVAR29;
    java.lang.String _CVAR31 = _CVAR30 + horario2.getCurrentMinute();
    android.content.ContentValues _CVAR26 = ch2;
    java.lang.String _CVAR27 = "idHorario";
    java.lang.String _CVAR32 = _CVAR31 + aula2.getText().toString().trim();
    _CVAR26.put(_CVAR27, _CVAR32);
    ch2.put("Dia", dia2.getText().toString());
    android.content.ContentValues ch2 = new android.content.ContentValues();
    android.widget.TimePicker _CVAR35 = horario2;
    java.lang.Integer _CVAR36 = _CVAR35.getCurrentMinute();
    android.content.ContentValues _CVAR33 = ch2;
    java.lang.String _CVAR34 = "Horario";
    int _CVAR37 = horario2.getCurrentHour() + _CVAR36;
    _CVAR33.put(_CVAR34, _CVAR37);
    ch2.put("Duracion", java.lang.Integer.parseInt(duracion2.getText().toString().trim()));
    ch2.put("Aula", aula2.getText().toString().trim());
    try {
        android.database.sqlite.SQLiteDatabase db = android.database.sqlite.SQLiteDatabase.openDatabase("/mnt/sdcard/kardex", null, android.database.sqlite.SQLiteDatabase.CREATE_IF_NECESSARY);
        // if (modo.equals("edit")) {
        // if (item != null) {
        // String id = item[0];
        // db.update("Materia", c, "Sigla like " + "'" + id
        // + "'", null);
        // Toast.makeText(getApplicationContext(),
        // "Datos actualizados correctamente",
        // Toast.LENGTH_SHORT).show();
        // }
        // } else {
        db.insert("Horario", null, ch1);
        db.insert("Horario", null, ch2);
        db.insert("Paralelo", null, c);
        android.widget.Toast.makeText(getApplicationContext(), "Datos insertados correctamente", android.widget.Toast.LENGTH_SHORT).show();
        // }
        finish();
    } catch (java.lang.Exception e) {
        android.widget.Toast.makeText(getApplicationContext(), "error", android.widget.Toast.LENGTH_SHORT).show();
    }
}