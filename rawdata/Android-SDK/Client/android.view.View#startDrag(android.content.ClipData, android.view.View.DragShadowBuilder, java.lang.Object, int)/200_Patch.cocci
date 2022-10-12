@API migration edit:_target.startDrag(...)->_target.startDragAndDrop(...)@
identifier _VAR8;
expression _target;
expression _DVAR0;
expression _DVAR1;
expression _DVAR2;
expression _DVAR3;
identifier _VAR0;
View view;
@@
- boolean _VAR8 =_target.startDrag(_CVAR1,_CVAR2,_CVAR3,_CVAR4);
+ boolean _VAR8 = _CVAR0.startDragAndDrop(_CVAR1, _CVAR2, _CVAR3, _CVAR4);

