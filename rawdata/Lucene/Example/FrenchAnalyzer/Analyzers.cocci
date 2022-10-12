@API migration edit:new Analyzer(...)->new FrenchAnalyzer(...)@
identifier _VAR1;
expression _target;
identifier _VAR0;
Analyzers Analyzers;
@@
- Analyzer _VAR1 =new Analyzer();
+? Version _VAR0 = com.github.rnewson.couchdb.lucene.util.Analyzers.VERSION;
+ Analyzer _VAR1 = new org.apache.lucene.analysis.fr.FrenchAnalyzer(_VAR0);

