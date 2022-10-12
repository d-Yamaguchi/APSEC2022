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
- int _VAR8 =_target.saveLayer(_CVAR5,_CVAR9,_CVAR14,_CVAR17,_CVAR18,_CVAR19);
+ int _VAR8 = canvas.saveLayer(_CVAR5, _CVAR9, _CVAR14, _CVAR17, _CVAR18);

