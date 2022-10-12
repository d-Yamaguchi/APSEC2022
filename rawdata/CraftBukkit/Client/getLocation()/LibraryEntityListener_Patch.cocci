@API migration edit:_target.getLocation(...)->_target.toString(...)@
identifier _VAR3;
expression _target;
Integer Integer;
identifier _VAR2;
identifier _VAR1;
identifier floorBlocks;
@@
- Location _VAR3 =_target.getLocation();
+ Location _VAR3 = Integer.toString(i);

