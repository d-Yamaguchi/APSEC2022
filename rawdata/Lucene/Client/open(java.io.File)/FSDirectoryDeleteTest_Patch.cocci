@API migration edit:_target.open(...)->_target.getDirectory(...)@
identifier _VAR3;
expression _target;
expression _DVAR0;
FSDirectory FSDirectory;
@@
- FSDirectory _VAR3 =_target.open(_CVAR0);
+ FSDirectory _VAR3 = FSDirectory.getDirectory();

