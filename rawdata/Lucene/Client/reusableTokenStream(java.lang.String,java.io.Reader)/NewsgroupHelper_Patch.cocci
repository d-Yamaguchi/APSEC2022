@API migration edit:_target.reusableTokenStream(...)->_target.tokenStream(...)@
identifier _VAR4;
expression _target;
expression _DVAR0;
expression _DVAR1;
identifier _VAR0;
Analyzer analyzer;
@@
- TokenStream _VAR4 =_target.reusableTokenStream(_CVAR1,_CVAR2);
+ TokenStream _VAR4 = _CVAR0.tokenStream(_CVAR1, _CVAR2);

