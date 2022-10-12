@API migration edit:_target.getOfflinePlayer(...)->_target.getPlayer(...)@
identifier player;
expression _target;
expression _DVAR0;
identifier _VAR0;
TeamMember member;
@@
- OfflinePlayer player =_target.getOfflinePlayer(_DVAR0);
+? TeamMember _VAR0 = member;
+ OfflinePlayer player = _VAR0.getPlayer();

