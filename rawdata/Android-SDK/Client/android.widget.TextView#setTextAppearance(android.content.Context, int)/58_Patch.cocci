@API migration edit:_target.setTextAppearance(...)->_target.setTextAppearance(...)@
identifier _VAR3;
expression _target;
expression _DVAR0;
expression _DVAR1;
identifier _VAR1;
identifier textView;
identifier _VAR0;
Context context;
identifier _VAR2;
identifier resid;
style style;
@@
- _target.setTextAppearance(_CVAR2,_CVAR3);
+ TextView textView = new android.widget.TextView(context);
+ TextView _VAR1 = textView;
+  _VAR1.setTextAppearance(ch_id);

