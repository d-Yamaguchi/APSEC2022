@API migration edit:_target.getWorlds(...)->_target.getServerConfigurationManager(...)@
identifier _VAR1;
expression _target;
identifier _VAR0;
MinecraftServer minecraftServer;
@@
- List _VAR1 =_target.getWorlds();
+? MinecraftServer _VAR0 = this.minecraftServer;
+ List _VAR1 = _VAR0.getServerConfigurationManager();

