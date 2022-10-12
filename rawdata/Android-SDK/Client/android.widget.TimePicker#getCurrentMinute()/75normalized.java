public void addParalelo(android.view.View v) {
    c.put("Nombre", paralelo.getText().toString().trim());
    c.put("Cupo", java.lang.Integer.parseInt(cupo.getText().toString().trim()));
    android.widget.TimePicker _CVAR2 = horario1;
    java.lang.Integer _CVAR3 = _CVAR2.getCurrentMinute();
    java.lang.String _CVAR4 = (dia1.getText().toString() + horario1.getCurrentHour()) + _CVAR3;
    android.content.ContentValues _CVAR0 = c;
    java.lang.String _CVAR1 = "Horario1";
    java.lang.String _CVAR5 = _CVAR4 + aula1.getText().toString().trim();
    _CVAR0.put(_CVAR1, _CVAR5);
    android.content.ContentValues c = new android.content.ContentValues();
    android.widget.TimePicker _CVAR8 = horario2;
    java.lang.Integer _CVAR9 = _CVAR8.getCurrentMinute();
    java.lang.String _CVAR10 = (dia2.getText().toString() + horario2.getCurrentHour()) + _CVAR9;
    android.content.ContentValues _CVAR6 = c;
    java.lang.String _CVAR7 = "Horario2";
    java.lang.String _CVAR11 = _CVAR10 + aula2.getText().toString().trim();
    _CVAR6.put(_CVAR7, _CVAR11);
    if (item != null) {
        c.put("Sigla", item[0]);
    }
    android.widget.TimePicker _CVAR14 = horario1;
    java.lang.Integer _CVAR15 = _CVAR14.getCurrentMinute();
    java.lang.String _CVAR16 = (dia1.getText().toString() + horario1.getCurrentHour()) + _CVAR15;
    android.content.ContentValues _CVAR12 = ch1;
    java.lang.String _CVAR13 = "idHorario";
    java.lang.String _CVAR17 = _CVAR16 + aula1.getText().toString().trim();
    _CVAR12.put(_CVAR13, _CVAR17);
    ch1.put("Dia", dia1.getText().toString());
    android.content.ContentValues ch1 = new android.content.ContentValues();
    android.widget.TimePicker _CVAR20 = horario1;
    java.lang.Integer _CVAR21 = _CVAR20.getCurrentMinute();
    android.content.ContentValues _CVAR18 = ch1;
    java.lang.String _CVAR19 = "Horario";
    int _CVAR22 = horario1.getCurrentHour() + _CVAR21;
    _CVAR18.put(_CVAR19, _CVAR22);
    ch1.put("Duracion", java.lang.Integer.parseInt(duracion1.getText().toString().trim()));
    ch1.put("Aula", aula1.getText().toString().trim());
    android.widget.TimePicker _CVAR25 = horario2;
    java.lang.Integer _CVAR26 = _CVAR25.getCurrentMinute();
    java.lang.String _CVAR27 = (dia2.getText().toString() + horario2.getCurrentHour()) + _CVAR26;
    android.content.ContentValues _CVAR23 = ch2;
    java.lang.String _CVAR24 = "idHorario";
    java.lang.String _CVAR28 = _CVAR27 + aula2.getText().toString().trim();
    _CVAR23.put(_CVAR24, _CVAR28);
    ch2.put("Dia", dia2.getText().toString());
    android.content.ContentValues ch2 = new android.content.ContentValues();
    android.widget.TimePicker _CVAR31 = horario2;
    java.lang.Integer _CVAR32 = _CVAR31.getCurrentMinute();
    android.content.ContentValues _CVAR29 = ch2;
    java.lang.String _CVAR30 = "Horario";
    int _CVAR33 = horario2.getCurrentHour() + _CVAR32;
    _CVAR29.put(_CVAR30, _CVAR33);
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