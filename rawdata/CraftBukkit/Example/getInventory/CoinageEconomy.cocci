@API migration edit:_target.getInventory(...)->_target.getName(...)@
identifier _VAR1;
expression _target;
identifier _VAR0;
Currency currency;
@@
- Inventory _VAR1 =_target.getInventory();
+? Currency _VAR0 = currency;
+ Inventory _VAR1 = _VAR0.getName();

