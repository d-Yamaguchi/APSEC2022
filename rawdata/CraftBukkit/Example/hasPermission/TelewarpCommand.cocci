@API migration edit:_target.hasPermission(...)->_target.equalsIgnoreCase(...)@
identifier _VAR3;
expression _target;
expression _DVAR0;
identifier _VAR1;
identifier _VAR0;
LinkedList args;
@@
- boolean _VAR3 =_target.hasPermission(_DVAR0);
+? LinkedList _VAR0 = args;
+? String _VAR1 = _VAR0.peekFirst();
+ boolean _VAR3 = _VAR1.equalsIgnoreCase(_DVAR0);

