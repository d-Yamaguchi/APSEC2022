@API migration edit:_target.reset(...)->_target.setReader(...)@
identifier _VAR6;
expression _target;
expression _DVAR0;
identifier _VAR3;
identifier tokenizer;
identifier _VAR0;
identifier _VAR2;
identifier _VAR1;
identifier _VAR5;
identifier _VAR4;
@@
- _target.reset(_CVAR5);
+ EdgeNGram2TokenFilterTest _VAR0 = TEST_VERSION_CURRENT;
+ String _VAR1 = "abcde";
+ StringReader _VAR2 = new java.io.StringReader(_VAR1);
+ WhitespaceTokenizer tokenizer = new org.apache.lucene.analysis.core.WhitespaceTokenizer(_VAR0, _VAR2);
+ WhitespaceTokenizer _VAR3 = tokenizer;
+ String _VAR4 = "ABCDE";
+ StringReader _VAR5 = new java.io.StringReader(_VAR4);
+  _VAR3.setReader(_VAR5);

