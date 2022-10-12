@API migration edit:_target.getItemInHand(...)->_target.getInventory(...)@
identifier _VAR1;
expression _target;
identifier _VAR0;
Player player;
@@
- ItemStack _VAR1 =_target.getItemInHand();
+ ItemStack _VAR1 = _CVAR1.getInventory();

