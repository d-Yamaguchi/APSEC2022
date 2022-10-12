@API migration edit:_target.addAnalyzer(...)->_target.put(...)@
identifier _VAR12;
expression _target;
expression _DVAR0;
expression _DVAR1;
identifier _VAR0;
identifier analyzerMap;
identifier _VAR4;
identifier fieldname;
identifier _VAR2;
identifier analyzerconfig;
identifier _VAR1;
identifier entry;
identifier _VAR3;
LuceneAnalyzerFactory LuceneAnalyzerFactory;
identifier _VAR11;
identifier analyzerInstance;
LuceneAnalyzerFactory LuceneAnalyzerFactory;
identifier _VAR8;
identifier analyzerclass;
identifier _VAR6;
identifier _VAR7;
LuceneAnalyzerFactory LuceneAnalyzerFactory;
identifier _VAR10;
@@
- _target.addAnalyzer(_CVAR6,_CVAR13);
+ Analyzer analyzerInstance = LuceneAnalyzerFactory.createAnalyzer(STOP_WORD_FILE_KEY, config);
+ Analyzer _VAR11 = analyzerInstance;
+  configuredAnalyzerMap.put(FIELD_NAME_KEY, _VAR11);

