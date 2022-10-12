@API migration edit:_target.release(...)->_target.close(...)@
identifier _VAR1;
expression _target;
identifier _VAR0;
identifier contactsCursor;
@@
- void _VAR1 =_target.release();
+? Cursor contactsCursor = null;
+? Cursor _VAR0 = contactsCursor;
+ void _VAR1 = _VAR0.close();

