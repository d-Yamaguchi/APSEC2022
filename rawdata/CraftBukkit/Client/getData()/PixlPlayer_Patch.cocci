@API migration edit:_target.getData(...)->_target.getDyeData(...)@
identifier _VAR3;
expression _target;
identifier _VAR2;
identifier dye;
@@
- byte _VAR3 =_target.getData();
+ DyeColor _VAR2 = dye;
+ byte _VAR3 = _VAR2.getDyeData();

