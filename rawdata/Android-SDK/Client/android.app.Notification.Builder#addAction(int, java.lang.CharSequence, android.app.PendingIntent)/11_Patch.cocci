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
- _target.addAction(_CVAR2,_CVAR4,_CVAR6);
+ Builder _VAR0 = mBuilder;
+  _VAR0.addAction(action);

