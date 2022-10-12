@API migration edit:_target.getLogger(...)->_target.getServer(...)@
identifier _VAR1;
expression _target;
identifier _VAR0;
TitanChat plugin;
@@
- Logger _VAR1 =_target.getLogger();
+ TitanChat _VAR0 = plugin;
+ Logger _VAR1 = _VAR0.getServer();

