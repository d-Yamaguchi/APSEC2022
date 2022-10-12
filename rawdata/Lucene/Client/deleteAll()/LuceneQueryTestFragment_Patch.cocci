@API migration edit:_target.deleteAll(...)->_target.deleteDocuments(...)@
identifier _VAR9;
expression _target;
identifier _VAR3;
identifier writer;
identifier _VAR0;
LuceneQueryTestFragment LuceneQueryTestFragment;
identifier _VAR1;
LuceneQueryTestFragment LuceneQueryTestFragment;
identifier _VAR2;
MaxFieldLength MaxFieldLength;
identifier _VAR8;
identifier _VAR6;
identifier _VAR4;
LuceneQueryTestFragment LuceneQueryTestFragment;
identifier _VAR5;
LuceneQueryTestFragment LuceneQueryTestFragment;
identifier _VAR7;
@@
- _target.deleteAll();
+ Analyzer _VAR1 = de.cosmocode.lucene.fragments.LuceneQueryTestFragment.ANALYZER;
+ MaxFieldLength _VAR2 = org.apache.lucene.index.IndexWriter.MaxFieldLength.UNLIMITED;
+ IndexWriter writer = new org.apache.lucene.index.IndexWriter(DIRECTORY, _VAR1, _VAR2);
+ IndexWriter _VAR3 = writer;
+ QueryParser _VAR6 = new org.apache.lucene.queryParser.QueryParser(WILDCARD2, ANALYZER);
+ Query _VAR8 = _VAR6.parse(WILDCARD1);
+  _VAR3.deleteDocuments(_VAR8);

