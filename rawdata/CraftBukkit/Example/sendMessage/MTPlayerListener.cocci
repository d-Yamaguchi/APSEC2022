@API migration edit:_target.sendMessage(...)->_target.send(...)@
identifier _VAR3;
expression _target;
expression _DVAR0;
Messaging Messaging;
identifier _VAR1;
identifier player;
identifier _VAR0;
PlayerInteractEvent event;
identifier _VAR2;
@@
- void _VAR3 =_target.sendMessage(_DVAR0);
+? PlayerInteractEvent _VAR0 = event;
+? Player player = _VAR0.getPlayer();
+? Player _VAR1 = player;
+? String _VAR2 = "`gSelected transmitter torch.";
+ void _VAR3 = Messaging.send(_VAR1, _VAR2);

