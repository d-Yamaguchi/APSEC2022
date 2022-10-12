@API migration edit:_target.getName(...)->_target.loadAchievements(...)@
identifier achievements;
expression _target;
identifier _VAR0;
DataStore store;
identifier _VAR1;
OfflinePlayer mPlayer;
@@
- String achievements =_target.getName();
+ DataStore _VAR0 = store;
+ OfflinePlayer _VAR1 = mPlayer;
+ String achievements = _VAR0.loadAchievements(_VAR1);

