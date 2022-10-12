@API migration edit:_target.getInt(...)->_target.getClickedBlock(...)@
identifier b;
expression _target;
expression _DVAR0;
identifier _VAR2;
PlayerInteractEvent e;
@@
- int b =_target.getInt(_CVAR2);
+ PlayerInteractEvent _VAR2 = e;
+ int b = _VAR2.getClickedBlock();

