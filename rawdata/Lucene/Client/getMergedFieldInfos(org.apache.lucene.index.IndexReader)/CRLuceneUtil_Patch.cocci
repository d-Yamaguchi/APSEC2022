@API migration edit:_target.getMergedFieldInfos(...)->_target.getFieldInfos(...)@
identifier _VAR2;
expression _target;
expression _DVAR0;
identifier _VAR1;
identifier aReader;
identifier _VAR0;
identifier aRC;
@@
- FieldInfos _VAR2 =_target.getMergedFieldInfos(_CVAR0);
+ AtomicReaderContext _VAR0 = aRC;
+ AtomicReader aReader = _VAR0.reader();
+ AtomicReader _VAR1 = aReader;
+ FieldInfos _VAR2 = _VAR1.getFieldInfos();

