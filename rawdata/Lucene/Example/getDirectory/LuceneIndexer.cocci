@API migration edit:_target.getDirectory(...)->_target.open(...)@
identifier _VAR1;
expression _target;
FSDirectory FSDirectory;
identifier _VAR0;
File directoryName;
@@
- File _VAR1 =_target.getDirectory();
+? File _VAR0 = directoryName;
+ File _VAR1 = FSDirectory.open(_VAR0);

