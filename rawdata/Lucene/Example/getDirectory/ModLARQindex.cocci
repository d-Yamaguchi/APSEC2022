@API migration edit:_target.getDirectory(...)->_target.open(...)@
identifier dir;
expression _target;
FSDirectory FSDirectory;
identifier _VAR0;
File luceneDir;
@@
- File dir =_target.getDirectory();
+? File _VAR0 = luceneDir;
+ File dir = FSDirectory.open(_VAR0);

