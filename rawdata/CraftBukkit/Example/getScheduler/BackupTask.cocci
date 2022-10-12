@API migration edit:_target.getScheduler(...)->_target.getInstance(...)@
identifier calendar;
expression _target;
Calendar Calendar;
@@
- BukkitScheduler calendar =_target.getScheduler();
+ BukkitScheduler calendar = Calendar.getInstance();

