@API migration edit:new GermanStemFilter(...)->new GermanStemFilter(...)@
identifier _VAR3;
expression _target;
expression _DVAR0;
@@
- GermanStemFilter _VAR3 =new GermanStemFilter(_DVAR0);
+ GermanStemFilter _VAR3 = new org.apache.lucene.analysis.de.GermanStemFilter(_DVAR0);

