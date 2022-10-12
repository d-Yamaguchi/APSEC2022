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
- int _VAR8 =_target.saveLayer(_DVAR0,_DVAR1,_DVAR2,_DVAR3,_DVAR4,_DVAR5);
+? Canvas _VAR0 = canvas;
+ int _VAR8 = _VAR0.saveLayer(_DVAR0, _DVAR1, _DVAR2, _DVAR3, _DVAR4);

