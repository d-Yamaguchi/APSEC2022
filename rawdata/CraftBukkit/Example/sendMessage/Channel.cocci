@API migration edit:_target.sendMessage(...)->_target.send(...)@
identifier _VAR4;
expression _target;
expression _DVAR0;
identifier _VAR0;
TitanChat plugin;
identifier _VAR1;
WARNING WARNING;
identifier _VAR2;
Player player;
identifier _VAR3;
String message;
@@
- void _VAR4 =_target.sendMessage(_DVAR0);
+? TitanChat _VAR0 = plugin;
+? void _VAR1 = WARNING;
+? Player _VAR2 = player;
+? String _VAR3 = message;
+ void _VAR4 = _VAR0.send(_VAR1, _VAR2, _VAR3);

