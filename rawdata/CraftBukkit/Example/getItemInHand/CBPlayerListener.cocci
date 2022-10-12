@API migration edit:_target.getItemInHand(...)->_target.getInventory(...)@
identifier _VAR2;
expression _target;
identifier _VAR1;
identifier player;
identifier _VAR0;
PlayerJoinEvent event;
@@
- ItemStack _VAR2 =_target.getItemInHand();
+? PlayerJoinEvent _VAR0 = event;
+? Player player = _VAR0.getPlayer();
+? Player _VAR1 = player;
+ ItemStack _VAR2 = _VAR1.getInventory();

