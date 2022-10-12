@API migration edit:new BytesRef(...)->new BytesRefBuilder(...)@
identifier brb;
expression _target;
@@
- BytesRef brb =new BytesRef();
+ BytesRef brb = new BytesRefBuilder();

