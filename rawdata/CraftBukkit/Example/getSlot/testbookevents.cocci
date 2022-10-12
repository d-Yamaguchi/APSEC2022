@API migration edit:_target.getSlot(...)->_target.getNewBookMeta(...)@
identifier _VAR2;
expression _target;
identifier _VAR1;
PlayerEditBookEvent event;
@@
- int _VAR2 =_target.getSlot();
+? PlayerEditBookEvent _VAR1 = event;
+ int _VAR2 = _VAR1.getNewBookMeta();

