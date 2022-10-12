@API migration edit:_target.getLocation(...)->_target.toString(...)@
identifier xmlString;
expression _target;
identifier _VAR0;
identifier sw;
@@
- Location xmlString =_target.getLocation();
+? StringWriter sw = new java.io.StringWriter();
+? StringWriter _VAR0 = sw;
+ Location xmlString = _VAR0.toString();

