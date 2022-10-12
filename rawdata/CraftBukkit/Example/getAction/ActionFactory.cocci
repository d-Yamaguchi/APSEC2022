@API migration edit:_target.getAction(...)->_target.isAir(...)@
identifier _VAR2;
expression _target;
identifier _VAR1;
identifier _VAR0;
InventoryClickEvent event;
@@
- Action _VAR2 =_target.getAction();
+? InventoryClickEvent _VAR0 = event;
+? ItemStack _VAR1 = _VAR0.getCurrentItem();
+ Action _VAR2 = isAir(_VAR1);

