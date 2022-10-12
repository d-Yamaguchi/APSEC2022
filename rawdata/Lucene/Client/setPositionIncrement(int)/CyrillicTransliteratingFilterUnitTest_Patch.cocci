@API migration edit:_target.setPositionIncrement(...)->_target.assertAttributes(...)@
identifier _VAR7;
expression _target;
expression _DVAR0;
identifier _VAR2;
identifier filter;
identifier _VAR1;
identifier _VAR0;
identifier asdf;
identifier _VAR3;
identifier _VAR4;
identifier _VAR5;
identifier _VAR6;
@@
- _target.setPositionIncrement(_CVAR1);
+ Token asdf = new org.apache.lucene.analysis.Token();
+ Token _VAR0 = asdf;
+ FakeTokenStream _VAR1 = new au.com.miskinhill.search.analysis.CyrillicTransliteratingFilterUnitTest.FakeTokenStream(_VAR0);
+ TokenFilter filter = new CyrillicTransliteratingFilter(_VAR1);
+ TokenFilter _VAR2 = filter;
+ String _VAR3 = "asdf";
+ int _VAR4 = 1;
+ int _VAR5 = 4;
+ int _VAR6 = 1;
+  assertAttributes(_VAR2, _VAR3, _VAR4, _VAR5, _VAR6);

