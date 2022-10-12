@API migration edit:_target.getPluginManager(...)->_target.getInstance(...)@
identifier _VAR0;
expression _target;
ZArena ZArena;
@@
- PluginManager _VAR0 =_target.getPluginManager();
+ PluginManager _VAR0 = ZArena.getInstance();

