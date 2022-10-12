@API migration edit:_target.getBlockZ(...)->_target.getBlock(...)@
identifier b;
expression _target;
@@
- int b =_target.getBlockZ();
+ int b = getBlock();

