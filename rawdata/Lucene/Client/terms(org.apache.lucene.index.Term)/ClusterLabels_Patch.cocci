@API migration edit:_target.terms(...)->_target.getTerms(...)@
identifier t;
expression _target;
expression _DVAR0;
MultiFields MultiFields;
identifier _VAR3;
identifier reader;
DirectoryReader DirectoryReader;
identifier _VAR2;
identifier dir;
FSDirectory FSDirectory;
identifier _VAR1;
identifier _VAR0;
String indexDir;
identifier _VAR4;
String contentField;
@@
- TermEnum t =_target.terms(_CVAR7);
+ TermEnum t = MultiFields.getTerms(_CVAR4, _CVAR6);

