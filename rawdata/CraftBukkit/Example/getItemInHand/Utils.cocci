@API migration edit:_target.getItemInHand(...)->_target.getInventory(...)@
identifier inventory;
expression _target;
identifier _VAR0;
Player player;
@@
- ItemStack inventory =_target.getItemInHand();
+? Player _VAR0 = player;
+ ItemStack inventory = _VAR0.getInventory();

