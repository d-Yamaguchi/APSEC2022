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
- _target.unlock(_CVAR0);
+ TrackingLockFactory _VAR0 = lockFactory;
+  DefaultIndexingContext.unlockForcibly(_VAR0, indexDirectory);

