@API migration edit:_target.reopen(...)->_target.openIfChanged(...)@
identifier newReader;
expression _target;
DirectoryReader DirectoryReader;
identifier _VAR3;
identifier oldReader;
identifier _VAR2;
identifier searcher;
identifier _VAR0;
identifier _VAR1;
identifier key;
@@
- IndexReader newReader =_target.reopen();
+? WarmingIndexAccessor _VAR0 = cachedSearchers;
+? Similarity key = cachedSearchers;
+? Similarity _VAR1 = key;
+? IndexSearcher searcher = _VAR0.get(_VAR1);
+? IndexSearcher _VAR2 = searcher;
+? DirectoryReader oldReader = ((org.apache.lucene.index.DirectoryReader) (_VAR2.getIndexReader()));
+? DirectoryReader _VAR3 = oldReader;
+ IndexReader newReader = DirectoryReader.openIfChanged(_VAR3);

