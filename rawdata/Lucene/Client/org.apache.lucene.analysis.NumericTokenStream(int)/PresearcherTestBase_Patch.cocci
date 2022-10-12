@API migration edit:new NumericTokenStream(...)->new LegacyNumericTokenStream(...)@
identifier nts;
expression _target;
expression _DVAR0;
@@
- NumericTokenStream nts =new NumericTokenStream(_DVAR0);
+ NumericTokenStream nts = new org.apache.lucene.analysis.LegacyNumericTokenStream(1);

