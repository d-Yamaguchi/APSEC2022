@API migration edit:_target.getTypeId(...)->_target.getType(...)@
identifier type;
expression _target;
Block b;
@@
- int type =_target.getTypeId();
+ int type = b.getType();

