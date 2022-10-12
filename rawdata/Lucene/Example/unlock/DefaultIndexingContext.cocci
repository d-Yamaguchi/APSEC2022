@API migration edit:_target.unlock(...)->_target.unlockForcibly(...)@
identifier _VAR2;
expression _target;
expression _DVAR0;
DefaultIndexingContext DefaultIndexingContext;
identifier _VAR0;
TrackingLockFactory lockFactory;
identifier _VAR1;
Directory indexDirectory;
@@
- void _VAR2 =_target.unlock(_DVAR0);
+? TrackingLockFactory _VAR0 = lockFactory;
+? Directory _VAR1 = indexDirectory;
+ void _VAR2 = DefaultIndexingContext.unlockForcibly(_VAR0, _VAR1);

