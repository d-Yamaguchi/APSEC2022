@API migration edit:new Analyzer(...)->new ThaiAnalyzer(...)@
identifier _VAR1;
expression _target;
identifier _VAR0;
Analyzers Analyzers;
@@
- new Analyzer();
+ Version _VAR0 = com.github.rnewson.couchdb.lucene.util.Analyzers.VERSION;
+  new org.apache.lucene.analysis.th.ThaiAnalyzer(_VAR0);

