@API migration edit:_target.runTaskLater(...)->_target.scheduleSyncDelayedTask(...)@
identifier id;
expression _target;
expression _DVAR0;
expression _DVAR1;
expression _DVAR2;
identifier _VAR2;
identifier sched;
identifier _VAR1;
identifier _VAR0;
CommandSender sender;
@@
- BukkitTask id =_target.runTaskLater(_DVAR0,_DVAR1,_DVAR2);
+? CommandSender _VAR0 = sender;
+? Server _VAR1 = _VAR0.getServer();
+? BukkitScheduler sched = _VAR1.getScheduler();
+? BukkitScheduler _VAR2 = sched;
+ BukkitTask id = _VAR2.scheduleSyncDelayedTask(_DVAR0, _DVAR1, _DVAR2);

