@API migration edit:_target.setAmount(...)->_target.getAmount(...)@
identifier amount;
expression _target;
expression _DVAR0;
identifier _VAR0;
ItemStack itemStack;
@@
- void amount =_target.setAmount(_DVAR0);
+? ItemStack _VAR0 = itemStack;
+ void amount = _VAR0.getAmount();

