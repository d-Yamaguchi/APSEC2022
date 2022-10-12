@API migration edit:_target.getInventory(...)->_target.getName(...)@
identifier _VAR1;
expression _target;
identifier _VAR0;
identifier player;
@@
- Inventory _VAR1 =_target.getInventory();
+? Player _VAR0 = player;
+ Inventory _VAR1 = _VAR0.getName();

