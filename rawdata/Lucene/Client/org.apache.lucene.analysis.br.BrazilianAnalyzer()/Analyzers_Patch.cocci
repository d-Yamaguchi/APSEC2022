@API migration edit:new Analyzer(...)->new BrazilianAnalyzer(...)@
identifier _VAR1;
expression _target;
identifier _VAR0;
Analyzers Analyzers;
@@
- new Analyzer();
+ Version _VAR0 = com.github.rnewson.couchdb.lucene.util.Analyzers.VERSION;
+  new org.apache.lucene.analysis.br.BrazilianAnalyzer(_VAR0);

