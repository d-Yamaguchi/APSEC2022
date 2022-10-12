@API migration edit:_target.getInventory(...)->_target.getName(...)@
identifier _VAR12;
expression _target;
identifier _VAR11;
LivingEntity target;
@@
- Inventory _VAR12 =_target.getInventory();
+? LivingEntity _VAR11 = target;
+ Inventory _VAR12 = _VAR11.getName();

