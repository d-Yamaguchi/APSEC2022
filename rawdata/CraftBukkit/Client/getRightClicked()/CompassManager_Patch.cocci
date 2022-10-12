@API migration edit:_target.getRightClicked(...)->_target.getOldCursor(...)@
identifier cursor;
expression _target;
identifier _VAR0;
InventoryDragEvent event;
@@
- Entity cursor =_target.getRightClicked();
+ InventoryDragEvent _VAR0 = event;
+ Entity cursor = _VAR0.getOldCursor();

