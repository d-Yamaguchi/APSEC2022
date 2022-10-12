@API migration edit:_target.getTypeId(...)->_target.getType(...)@
identifier _VAR8;
expression _target;
identifier _VAR7;
identifier placedItem;
identifier _VAR6;
BlockPlaceEvent event;
@@
- int _VAR8 =_target.getTypeId();
+? BlockPlaceEvent _VAR6 = event;
+? ItemStack placedItem = _VAR6.getItemInHand();
+? ItemStack _VAR7 = placedItem;
+ int _VAR8 = _VAR7.getType();

