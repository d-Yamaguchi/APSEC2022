@API migration edit:_target.reusableTokenStream(...)->_target.tokenStream(...)@
identifier ts;
expression _target;
expression _DVAR0;
expression _DVAR1;
identifier _VAR0;
Analyzer analyzer;
@@
- TokenStream ts =_target.reusableTokenStream(_DVAR0,_DVAR1);
+? Analyzer _VAR0 = analyzer;
+ TokenStream ts = _VAR0.tokenStream(_DVAR0, _DVAR1);

