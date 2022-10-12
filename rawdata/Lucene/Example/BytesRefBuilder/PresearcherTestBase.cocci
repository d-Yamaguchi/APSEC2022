@API migration edit:new BytesRef(...)->new BytesRefBuilder(...)@
identifier brb;
expression _target;
expression _DVAR0;
@@
- BytesRef brb =new BytesRef(_DVAR0);
+ BytesRef brb = new BytesRefBuilder();

