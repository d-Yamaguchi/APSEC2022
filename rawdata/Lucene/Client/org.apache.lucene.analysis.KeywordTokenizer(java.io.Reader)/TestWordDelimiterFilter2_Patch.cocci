@API migration edit:new KeywordTokenizer(...)->new KeywordTokenizer(...)@
identifier src;
expression _target;
expression _DVAR0;
@@
- KeywordTokenizer src =new KeywordTokenizer(_DVAR0);
+ KeywordTokenizer src = new org.apache.lucene.analysis.KeywordTokenizer(_DVAR0);

