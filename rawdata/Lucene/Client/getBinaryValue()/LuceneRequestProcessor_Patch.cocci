@API migration edit:_target.getBinaryValue(...)->_target.binaryValue(...)@
identifier _VAR10;
expression _target;
identifier _VAR9;
identifier field;
@@
- byte[] _VAR10 =_target.getBinaryValue();
+ IndexableField _VAR9 = field;
+ byte[] _VAR10 = _VAR9.binaryValue();

