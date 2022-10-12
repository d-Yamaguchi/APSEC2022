@API migration edit:_target.getName(...)->_target.loadAchievements(...)@
identifier achievements;
expression _target;
identifier _VAR0;
DataStore store;
identifier _VAR1;
OfflinePlayer mPlayer;
@@
- String achievements =_target.getName();
+ String achievements = _CVAR0.loadAchievements(_CVAR1);

