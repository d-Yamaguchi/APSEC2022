@API migration edit:new DutchStemFilter(...)->new DutchStemFilter(...)@
identifier _VAR3;
expression _target;
expression _DVAR0;
@@
- DutchStemFilter _VAR3 =new DutchStemFilter(_DVAR0);
+ DutchStemFilter _VAR3 = new org.apache.lucene.analysis.nl.DutchStemFilter(_DVAR0);

