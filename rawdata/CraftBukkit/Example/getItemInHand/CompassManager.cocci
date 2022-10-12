@API migration edit:_target.getItemInHand(...)->_target.getInventory(...)@
identifier _VAR2;
expression _target;
identifier _VAR1;
@@
- ItemStack _VAR2 =_target.getItemInHand();
+? Player _VAR1 = p;
+ ItemStack _VAR2 = _VAR1.getInventory();

