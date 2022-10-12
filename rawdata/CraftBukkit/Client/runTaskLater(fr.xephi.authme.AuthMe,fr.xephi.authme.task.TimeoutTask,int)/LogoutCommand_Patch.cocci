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
- BukkitTask id =_target.runTaskLater(_CVAR3,_CVAR8,_CVAR10);
+ BukkitTask id = _CVAR2.scheduleSyncDelayedTask(_CVAR3, _CVAR8, _CVAR10);

