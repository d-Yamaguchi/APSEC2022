@API migration edit:_target.sendMessage(...)->_target.send(...)@
identifier _VAR3;
expression _target;
expression _DVAR0;
identifier _VAR0;
MessageLevel MessageLevel;
identifier _VAR1;
CommandSender sender;
identifier _VAR2;
@@
- _target.sendMessage(_CVAR1);
+ MessageLevel _VAR0 = com.titankingdoms.nodinchan.titanchat.TitanChat.MessageLevel.INFO;
+ String _VAR2 = "\"/titanchat commands [page]\" for command list";
+  send(_VAR0, sender, _VAR2);

