@API migration edit:_target.getBlock(...)->_target.getRelativeTopFallables(...)@
identifier _VAR0;
expression _target;
BukkitUtils BukkitUtils;
@@
- Block _VAR0 =_target.getBlock();
+ Block _VAR0 = BukkitUtils.getRelativeTopFallables();

