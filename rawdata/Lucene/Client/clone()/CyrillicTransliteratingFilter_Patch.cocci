@API migration edit:_target.clone(...)->_target.deepCopyOf(...)@
identifier _VAR2;
expression _target;
BytesRef BytesRef;
identifier _VAR1;
BytesRef hash;
@@
- Object _VAR2 =_target.clone();
+ BytesRef _VAR1 = hash;
+ Object _VAR2 = BytesRef.deepCopyOf(_VAR1);

