@API migration edit:_target.getType(...)->_target.getX(...)@
identifier _VAR5;
expression _target;
identifier _VAR4;
Block chest;
@@
- InventoryType _VAR5 =_target.getType();
+? Block _VAR4 = chest;
+ InventoryType _VAR5 = _VAR4.getX();

