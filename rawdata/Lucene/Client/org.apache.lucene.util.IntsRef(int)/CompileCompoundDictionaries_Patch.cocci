@API migration edit:new IntsRef(...)->new IntsRefBuilder(...)@
identifier intsRef;
expression _target;
expression _DVAR0;
@@
- IntsRef intsRef =new IntsRef(_DVAR0);
+ IntsRef intsRef = new org.apache.lucene.util.IntsRefBuilder();

