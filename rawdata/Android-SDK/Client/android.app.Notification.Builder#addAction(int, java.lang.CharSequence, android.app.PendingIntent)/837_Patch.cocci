@API migration edit:_target.addAction(...)->_target.addAction(...)@
identifier _VAR8;
expression _target;
expression _DVAR0;
expression _DVAR1;
expression _DVAR2;
identifier _VAR0;
Builder mBuilder;
identifier _VAR7;
identifier action;
identifier _VAR6;
identifier _VAR3;
Icon Icon;
identifier _VAR1;
Context mContext;
identifier _VAR2;
int icon;
identifier _VAR4;
CharSequence title;
identifier _VAR5;
PendingIntent intent;
@@
- _target.addAction(_CVAR2,_CVAR3,_CVAR10);
+ Builder _VAR0 = mBuilder;
+ Context _VAR1 = mContext;
+ Icon _VAR3 = Icon.createWithResource(_VAR1, NOTE_ID);
+ CharSequence _VAR4 = title;
+ PendingIntent _VAR5 = intent;
+ Builder _VAR6 = new android.app.Notification.Action.Builder(_VAR3, _VAR4, _VAR5);
+ Action action = _VAR6.build();
+ Action _VAR7 = action;
+  _VAR0.addAction(_VAR7);

