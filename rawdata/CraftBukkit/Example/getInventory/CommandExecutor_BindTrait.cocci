@API migration edit:_target.getInventory(...)->_target.getName(...)@
identifier _VAR1;
expression _target;
identifier _VAR0;
identifier trait;
@@
- Inventory _VAR1 =_target.getInventory();
+? Trait _VAR0 = trait;
+ Inventory _VAR1 = _VAR0.getName();

