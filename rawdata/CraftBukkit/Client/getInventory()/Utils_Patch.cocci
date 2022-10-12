@API migration edit:_target.getInventory(...)->_target.getName(...)@
identifier _VAR2;
expression _target;
identifier _VAR1;
Player player;
@@
- Inventory _VAR2 =_target.getInventory();
+ Inventory _VAR2 = _CVAR0.getName();

