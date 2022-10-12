@API migration edit:_target.getFieldable(...)->_target.getField(...)@
identifier _VAR5;
expression _target;
expression _DVAR0;
identifier _VAR3;
identifier doc;
identifier _VAR1;
IndexSearcher searcher;
identifier _VAR2;
identifier currentDoc;
identifier hits;
identifier tdocs;
TopDocsCollector ttcollector;
int start;
int count;
identifier i;
@@
- Fieldable _VAR5 =_target.getFcounteldable(_CVAR4);
+ Fieldable _VAR5 = _CVAR3.getFcounteld(_CVAR4);

