@API migration edit:_target.getScheduler(...)->_target.getInstance(...)@
identifier _VAR0;
expression _target;
Utils Utils;
@@
- BukkitScheduler _VAR0 =_target.getScheduler();
+ BukkitScheduler _VAR0 = Utils.getInstance();

