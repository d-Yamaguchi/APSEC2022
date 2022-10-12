@API migration edit:_target.commit(...)->_target.setRootGroups(...)@
identifier _VAR2;
expression _target;
identifier _VAR0;
identifier result;
identifier _VAR1;
identifier rootGroups;
@@
- _target.commit();
+ IndexDataReadResult result = new org.apache.maven.index.updater.IndexDataReader.IndexDataReadResult();
+ IndexDataReadResult _VAR0 = result;
+ Set rootGroups = new java.util.LinkedHashSet<>();
+ Set _VAR1 = rootGroups;
+  _VAR0.setRootGroups(_VAR1);

