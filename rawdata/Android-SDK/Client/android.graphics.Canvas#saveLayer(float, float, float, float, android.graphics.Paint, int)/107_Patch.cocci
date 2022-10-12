@API migration edit:_target.saveLayer(...)->_target.saveLayer(...)@
identifier _VAR8;
expression _target;
expression _DVAR0;
expression _DVAR1;
expression _DVAR2;
expression _DVAR3;
expression _DVAR4;
expression _DVAR5;
identifier _VAR0;
Canvas canvas;
@@
- int _VAR8 =_target.saveLayer(_CVAR1,_CVAR2,_CVAR3,_CVAR4,_CVAR6,_CVAR7);
+ int _VAR8 = canvas.saveLayer(_CVAR1, _CVAR2, _CVAR3, _CVAR4, _CVAR6);

