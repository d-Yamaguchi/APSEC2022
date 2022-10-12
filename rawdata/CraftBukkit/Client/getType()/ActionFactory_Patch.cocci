@API migration edit:_target.getType(...)->_target.getX(...)@
identifier _VAR4;
expression _target;
identifier _VAR141;
identifier _VAR116;
identifier _VAR92;
identifier _VAR72;
identifier _VAR52;
identifier _VAR32;
identifier _VAR12;
identifier _VAR3;
identifier costBlock;
@@
- InventoryType _VAR4 =_target.getType();
+ Block costBlock = getCostBlock();
+ Block _VAR141 = costBlock;
+ InventoryType _VAR4 = _VAR141.getX();

