@API migration edit:_target.getBlockY(...)->_target.getBlock(...)@
identifier b;
expression _target;
@@
- int b =_target.getBlockY();
+ int b = getBlock();

