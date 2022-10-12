@API migration edit:_target.getServer(...)->_target.get(...)@
identifier _VAR0;
expression _target;
ChannelsPlugin ChannelsPlugin;
@@
- Server _VAR0 =_target.getServer();
+ Server _VAR0 = ChannelsPlugin.get();

