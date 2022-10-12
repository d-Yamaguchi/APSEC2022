@API migration edit:_target.setAmount(...)->_target.getAmount(...)@
identifier amount;
expression _target;
expression _DVAR0;
identifier _VAR0;
ItemStack itemStack;
@@
- _target.setAmount(_CVAR3);
+  item.getAmount();

