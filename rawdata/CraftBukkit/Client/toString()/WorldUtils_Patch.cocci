@API migration edit:_target.toString(...)->_target.getName(...)@
identifier _VAR5;
expression _target;
identifier _VAR4;
identifier w;
identifier _VAR3;
Block b;
@@
- String _VAR5 =_target.toString();
+ World w = b.getWorld();
+ World _VAR4 = w;
+ String _VAR5 = _VAR4.getName();

