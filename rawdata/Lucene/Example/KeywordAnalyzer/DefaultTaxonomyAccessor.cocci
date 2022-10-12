@API migration edit:new KeywordAnalyzer(...)->new KeywordAnalyzer(...)@
identifier _VAR1;
expression _target;
@@
- KeywordAnalyzer _VAR1 =new KeywordAnalyzer();
+ KeywordAnalyzer _VAR1 = new org.apache.lucene.analysis.core.KeywordAnalyzer();

