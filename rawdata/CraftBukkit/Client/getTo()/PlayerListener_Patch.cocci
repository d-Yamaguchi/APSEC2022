@API migration edit:_target.getTo(...)->_target.getSign(...)@
identifier _VAR5;
expression _target;
identifier _VAR4;
identifier stone;
@@
- Location _VAR5 =_target.getTo();
+ MemoryStone _VAR4 = stone;
+ Location _VAR5 = _VAR4.getSign();

