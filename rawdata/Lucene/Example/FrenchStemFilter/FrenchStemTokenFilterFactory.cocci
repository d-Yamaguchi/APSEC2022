@API migration edit:new FrenchStemFilter(...)->new FrenchStemFilter(...)@
identifier _VAR3;
expression _target;
expression _DVAR0;
@@
- FrenchStemFilter _VAR3 =new FrenchStemFilter(_DVAR0);
+ FrenchStemFilter _VAR3 = new org.apache.lucene.analysis.fr.FrenchStemFilter(_DVAR0);

