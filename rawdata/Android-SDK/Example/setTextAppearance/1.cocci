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
- void _VAR3 =_target.setTextAppearance(_DVAR0,_DVAR1);
+? Context _VAR0 = context;
+? TextView textView = new android.widget.TextView(_VAR0);
+? TextView _VAR1 = textView;
+? int resid = android.R.style.TextAppearance_Large;
+? int _VAR2 = resid;
+ void _VAR3 = _VAR1.setTextAppearance(_VAR2);

