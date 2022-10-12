@API migration edit:_target.getInventory(...)->_target.getName(...)@
identifier _VAR2;
expression _target;
identifier _VAR1;
CommandSender sender;
@@
- Inventory _VAR2 =_target.getInventory();
+? CommandSender _VAR1 = sender;
+ Inventory _VAR2 = _VAR1.getName();

