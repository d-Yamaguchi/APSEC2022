@API migration edit:_target.reusableTokenStream(...)->_target.tokenStream(...)@
identifier stream;
expression _target;
expression _DVAR0;
expression _DVAR1;
identifier _VAR0;
Analyzer analyzer;
@@
- TokenStream stream =_target.reusableTokenStream(_DVAR0,_DVAR1);
+? Analyzer _VAR0 = analyzer;
+ TokenStream stream = _VAR0.tokenStream(_DVAR0, _DVAR1);

